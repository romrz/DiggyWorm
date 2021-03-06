/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package diggyworm;


import java.awt.BufferCapabilities;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import diggyworm.level.Level;
import diggyworm.math.Vector;
import diggyworm.entity.Entity;
import diggyworm.entity.Worm;
import diggyworm.gfx.Screen;
import diggyworm.gfx.ScreenManager;
import diggyworm.gui.Button;
import diggyworm.gui.GameOverMenu;
import diggyworm.gui.MainMenu;
import diggyworm.gui.PauseMenu;
import diggyworm.input.Input;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private int width = 1200; // Default width
    private int height = 700; // Defualt height

    private Screen screen; // Renderer
    private ScreenManager screenManager; // Manages the screen modes

    private Input input; // Handles the user input

    // Contains all the game entities
    private List<Entity> entities;

    // Player (Worm)
    private Worm worm;
    // It's the relative velocity for all the entities
    private Vector wormVelocity;

    private Level level; // Game level

    // Entities' images
    public static BufferedImage WORM_IMAGE;
    public static BufferedImage WORM_SEGMENT_IMAGE;
    public static BufferedImage RED_APPLE_IMAGE;
    public static BufferedImage GREEN_APPLE_IMAGE;
    public static BufferedImage ROCK_IMAGE;
    public static BufferedImage MOLE_IMAGE;
    public static BufferedImage STICK_IMAGE;

    // Variables for the game loop thread 
    private Thread gameThread; // Game loop thread
    private boolean running = false; // Running state
    private int frameCount = 0; // Auxiliary frame counter
    private int frameRate = 0; // FPS

    // HUD
    private HUD hud;
    private long lifeTime;

    // Game state
    public static enum STATE {
        MAIN_MENU, WAITING_START, PLAYING, PAUSE, GAME_OVER
    };
    public static STATE state;

    // Menus
    public MainMenu mainMenu;
    public PauseMenu pauseMenu;
    public GameOverMenu gameOverMenu;

    // Pause Button
    public Button pauseBtn;

    // Images for the different panels
    private BufferedImage readyImage;
    
    private int appleSequence;

    // Testing
    private boolean showCollisionBounds = false;

    public Game(JFrame frame) {
        
        // Objects initialization
        screenManager = new ScreenManager(frame);
        screen = new Screen();
        entities = new LinkedList<Entity>();
        wormVelocity = new Vector();
        state = STATE.MAIN_MENU;

        // Canvas configuration
        setPreferredSize(new Dimension(width, height));
        setIgnoreRepaint(true);
        setFocusable(true);
        requestFocus();

        try {
            // Loads the entities' images
            WORM_IMAGE = ImageIO.read(Game.class.getResource("/entities/worm.png"));
            WORM_SEGMENT_IMAGE = ImageIO.read(Game.class.getResource("/entities/worm_segment.png"));
            RED_APPLE_IMAGE = ImageIO.read(Game.class.getResource("/entities/red_apple.png"));
            GREEN_APPLE_IMAGE = ImageIO.read(Game.class.getResource("/entities/green_apple.png"));
            ROCK_IMAGE = ImageIO.read(Game.class.getResource("/entities/rock.png"));
            MOLE_IMAGE = ImageIO.read(Game.class.getResource("/entities/mole.png"));
            STICK_IMAGE = ImageIO.read(Game.class.getResource("/entities/stick.png"));

            readyImage = ImageIO.read(Game.class.getResource("/ready_img.png"));
        } catch (IOException e) {
            System.out.println("Error loading the images");
            e.printStackTrace();
        }

        // Adds the input listeners
        input = new Input(this);
        addKeyListener(input);
        addMouseListener(input);
    }

    // Enter in full screen mode
    public void setFullScreen() {
        width = screenManager.getDisplayMode().getWidth();
        height = screenManager.getDisplayMode().getHeight();

        screenManager.setFullScreen();
    }

    // Set the running game state
    public void setRunning(boolean r) {
        running = r;
    }

    // Set the game state
    public void setState(STATE s) {
        state = s;
    }

    // Set the worm velocity
    public void setWormVelocity(float x, float y) {
        wormVelocity.x = x;
        wormVelocity.y = y;
    }

    // Returns the current level
    public Level getLevel() {
        return level;
    }

    // Returns the screen object
    public Screen getScreen() {
        return screen;
    }

    // Returns the screen manager
    public ScreenManager getScreenManager() {
        return screenManager;
    }

    // Returns the current worm
    public Worm getWorm() {
        return worm;
    }

    // Returns whether the game is running
    public boolean isRunning() {
        return running;
    }

    // Returns the worm velocity
    public Vector getWormVelocity() {
        return wormVelocity;
    }

    // Returns the current game state
    public STATE getState() {
        return state;
    }

    // Returns whether the level is started
    public boolean levelStarted() {
        return (level != null) && (worm != null);
    }

    // Initializes the game
    public void init() {
        graphicsProfiling();

        // Creates the levels
        level = new Level(this);

        // Create the worms
        worm = new Worm();
        worm.setImage(WORM_IMAGE, WORM_SEGMENT_IMAGE);
        worm.addToEntities(this);
        worm.setDefaultPosition();
        worm.setDefaultVelocity();
        wormVelocity.x = worm.DEFAULT_VELOCITY_X;
        wormVelocity.y = worm.DEFAULT_VELOCITY_Y;

        // Menus initialization
        mainMenu = new MainMenu(this);
        pauseMenu = new PauseMenu(this);
        gameOverMenu = new GameOverMenu(this);

        // Pause button
        pauseBtn = new Button("Pausa", Button.ACTION.PAUSE);
        pauseBtn.setPosition(width - pauseBtn.getWidth() / 2 - 20, pauseBtn.getHeight() / 2 + 20);
        
        // HUD
        hud = new HUD();

    }

    public void graphicsProfiling() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        BufferCapabilities bc = gc.getBufferCapabilities();

        BufferCapabilities.FlipContents fp = bc.getFlipContents();

        System.out.println("Page Flipping: " + bc.isPageFlipping());
        System.out.println("Full Screen Required: " + bc.isFullScreenRequired());
        System.out.println("MultiBuffer Availavle: " + bc.isMultiBufferAvailable());

        if (fp == BufferCapabilities.FlipContents.BACKGROUND) {
            System.out.println("Flip Contents: BACKGROUND");
        } else if (fp == BufferCapabilities.FlipContents.COPIED) {
            System.out.println("Flip Contents: COPIED");
        } else if (fp == BufferCapabilities.FlipContents.PRIOR) {
            System.out.println("Flip Contents: PRIOR");
        } else if (fp == BufferCapabilities.FlipContents.UNDEFINED) {
            System.out.println("Flip Contents: UNDEFINED");
        }
    }

    // Start a new level
    public void startLevel() {
        level.start();	// Starts the level
        input.reset();

        state = STATE.WAITING_START;
    }

    // Starts the game loop
    public void start() {
        running = true;
        gameThread = new Thread(this, "Game Loop");
        gameThread.start();
    }

    // Resets the game
    public void restart() {
        level.restart();
        entities.clear();
        worm.setLife(100);
        worm.addToEntities(this);
        worm.setDefaultPosition();
        worm.setDefaultVelocity();
        wormVelocity.x = worm.DEFAULT_VELOCITY_X;
        wormVelocity.y = worm.DEFAULT_VELOCITY_Y;
        startLevel();
    }

    public void stop() {
        level.restart();
        entities.clear();
        worm.setLife(100);
        worm.addToEntities(this);
        worm.setDefaultPosition();
        worm.setDefaultVelocity();
        wormVelocity.x = worm.DEFAULT_VELOCITY_X;
        wormVelocity.y = worm.DEFAULT_VELOCITY_Y;
    }

    public void end() {
    }

    // Adds a new entity to the game
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    // Checks collisions
    public void collisionHandling() {
        if (worm == null) {
            return;
        }

        // Iterates through the entities' list
        for (Entity e : entities) {
            int type = e.getType(); // Gets the entity's type

            // Only checks collision with alive, visible and collidable entities
            if (e.getCollidable() && e.isAlive() && e.isVisible()) {
                // Check collisions between worm and an entity
                if (collision(e)) {
                    e.isCollisioned = true;

                    // Increments the worm's life when it eats an apple
                    if (type == Entity.TYPE_RED_APPLE || type == Entity.TYPE_GREEN_APPLE) {
                        e.setAlive(false);
                        e.setVisible(false);
                        if(worm.getScale() == 1)
                            worm.setLife(worm.getLife() + 20);
                        
                        appleSequence = appleSequence < 9 ? appleSequence + 1 : 9;
                    } else if (type == Entity.TYPE_ROCK || type == Entity.TYPE_MOLE || type == Entity.TYPE_STICK) {
                        if(worm.getScale() > 1) {
                            e.setAlive(false);
                            e.setVisible(false);
                        }
                        else {
                            worm.setLife(0); // Kills the worm
                        }
                    }

                    if(type == Entity.TYPE_GREEN_APPLE) {
                        worm.setScale(2);
                    }
                }
            }

            // The entities are only shown if they're in the screen section
            if (type != Entity.TYPE_WORM && type != Entity.TYPE_WORM_HEAD && type != Entity.TYPE_WORM_SEGMENT) {
                if (e.getX() - e.getCtoW() <= getWidth() && !e.isVisible()) {
                    e.setVisible(true);
                }
                if (e.getX() <= -e.getWidth()) {
                    e.setAlive(false);
                }
            }

            // Keeps the mole within the level bounds
            if (type == Entity.TYPE_MOLE) {
                if (e.getY() - e.getCtoH() < level.getTopBound()) {
                    e.setY(level.getTopBound() + e.getCtoH());
                    e.setVY(-e.getVY());
                    e.setRotation(e.getRotation() + Math.PI);
                } else if (e.getY() + e.getCtoH() > level.getBottomBound()) {
                    e.setY(level.getBottomBound() - e.getCtoH());
                    e.setVY(-e.getVY());
                    e.setRotation(e.getRotation() + Math.PI);
                }
            }
            
            if(e.getType() == Entity.TYPE_RED_APPLE) {
                if(e.getX() + e.getWidth() < worm.getX()) {
                    appleSequence = 0;
                }
            }
        }

        // Checks worm collision with level bounds
        if (worm.getY() - worm.head.getCtoH() <= level.getTopBound()) {
            worm.setY(level.getTopBound() + worm.head.getCtoH());
            worm.setVY(0);
        }
        if (worm.getY() + worm.head.getCtoH() >= level.getBottomBound()) {
            worm.setY(level.getBottomBound() - worm.head.getCtoH());
            worm.setVY(0);
        }
    }

    // Checks for a collision between the worm and an entity
    public boolean collision(Entity e) {
        if (e.getCollisionType() == Entity.CIRCLE_COLLISION) {
            double d2 = Math.pow(e.getX() - worm.head.getX(), 2) + Math.pow(e.getY() - worm.head.getY(), 2);
            double dr2 = Math.pow(e.getCtoW() + worm.head.getCtoW(), 2);

            return d2 <= dr2;
        } else {
            double r = Math.sqrt((e.getX() - worm.head.getX()) * (e.getX() - worm.head.getX())
                    + (worm.head.getY() - e.getY()) * (worm.head.getY() - e.getY()));

            double angle = e.getRotation();
            double t1 = Math.atan2(worm.head.getY() - e.getY(), e.getX() - worm.head.getX());
            double t2 = t1 + angle;

            float xb = e.getX() - (float) (r * Math.cos(t2));
            float yb = e.getY() - (float) (r * Math.sin(t2));

            return xb - worm.head.getCtoW() < e.getX() + e.getCtoW()
                   && xb + worm.head.getCtoW() > e.getX() - e.getCtoW()
                   && yb + worm.head.getCtoW() > e.getY() - e.getCtoH()
                   && yb - worm.head.getCtoW() < e.getY() + e.getCtoH();

        }
    }

    // Removes death entities from the list
    public void removeEntities() {
        // Iterates through the entities list
        ListIterator<Entity> iterator = entities.listIterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();

            if (entity.isAlive() == false) {
                iterator.remove();
            }
        }
    }

    // Updates all the game entities based in a delta time
    public void updateGame(double dt) {
        if (state != STATE.PLAYING && state != STATE.WAITING_START && state != STATE.MAIN_MENU) {
            return;
        }

        if (state == STATE.MAIN_MENU) {
            level.update(dt);
            return;
        }

        if (worm.getLife() <= 0) {
            if(worm.getScale() > 1) {
                worm.setLife(100);
                worm.setScale(1);
            }
            else {
                state = STATE.GAME_OVER;
            }
        }

        // Iterates through the list
        for (Entity e : entities) {
            if (e.isAlive()) {
                e.move(dt);
                e.animate();
            }
        }

        level.update(dt);

        // Reduce worm's life by 1 each 100 ms
        if (lifeTime + 150 < System.currentTimeMillis() && state == STATE.PLAYING) {
            lifeTime = System.currentTimeMillis();
            worm.setLife(worm.getLife() - 1);
        }

        collisionHandling();
        removeEntities();
    }

    // Game rendering
    public void render() {
        Toolkit.getDefaultToolkit().sync();

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        screen.setGraphics(g);

		//g.clearRect(0, 0, width, height);
        level.draw(screen);

        if (state != STATE.MAIN_MENU) {
            // Iterates through the entities list
            for (Entity e : entities) {
                if (e.isVisible() && e.isAlive()) {
                    e.draw(screen); // Draw the entity on the screen

                    // Show collision bounds of the entities (testing only)
                    if (showCollisionBounds) {
                        g.setColor(Color.WHITE);

                        if (e.getType() == Entity.TYPE_WORM_HEAD) {
                            g.drawOval((int) (e.getX() - e.getCtoW()), (int) (e.getY() - e.getCtoH()),
                                    (int) (e.getWidth() * e.getScale()), (int) (e.getHeight() * e.getScale()));
                        }

                        if (e.isCollisioned) {
                            if (e.getCollisionType() == Entity.RECT_COLLISION) {
                                g.rotate(e.getRotation(), e.getX(), e.getY());
                                g.drawRect((int) (e.getX() - e.getCtoW()), (int) (e.getY() - e.getCtoH()),
                                        (int) (e.getWidth() * e.getScale()), (int) (e.getHeight() * e.getScale()));
                                g.rotate(-e.getRotation(), e.getX(), e.getY());
                            } else {
                                g.drawOval((int) (e.getX() - e.getCtoW()), (int) (e.getY() - e.getCtoH()),
                                        (int) (e.getWidth() * e.getScale()), (int) (e.getHeight() * e.getScale()));
                            }
                        }
                    }
                }
            }
        }

        // Main menu screen
        if (state == STATE.MAIN_MENU) {
            mainMenu.draw(screen);
        } else {
            if (state == STATE.WAITING_START) {
                screen.drawImage(readyImage, width / 2 - 200, height / 2 - 200, 400, 400);
            } else if (state == STATE.GAME_OVER) {
                gameOverMenu.draw(screen);
            } else if (state == STATE.PAUSE) {
                pauseMenu.draw(screen);
            }
        }
        
        // Draws the HUD
        hud.draw(screen);
        
        // Testing information
        screen.setColor(Color.WHITE);
        screen.drawText("FPS: " + frameRate, 10, 60);
        screen.drawText("Position: " + (int) level.getPosition(), 10, 80);
        screen.drawText("Entities: " + entities.size(), 10, 100);

        g.dispose();
        bs.show();
    }

    // Runs the game loop
    public void run() {
        init();

        long previousFrameTime = System.nanoTime();
        long startTime = 0;

        while (running) {
            long t111 = System.nanoTime();

            long currentTime = System.nanoTime();
            double elapsedTime = (currentTime - previousFrameTime) / 1000000000.0;
            previousFrameTime = System.nanoTime();

            // long t1 = System.nanoTime();
            updateGame(elapsedTime);
            // long t2 = System.nanoTime();

            // long t11 = System.nanoTime();
            render();
            // long t22 = System.nanoTime();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Game loop interruption: " + e);
            }

            long t222 = System.nanoTime();

            frameCount++;
            if (startTime + 1000 < System.currentTimeMillis()) {
                startTime = System.currentTimeMillis();
                frameRate = frameCount;
                frameCount = 0;
            }

            System.out.printf("%.2f\t", (t222 - t111) / 1000000.0);
            // System.out.printf("%.2f\t", (t2 - t1) / 1000000.0);
            // System.out.printf("%.2f\t", (t22 - t11) / 1000000.0);
            System.out.printf("%d\n", frameRate);
        }
    }
    
    /**
     * This class manages and displays the HUD
     */
    private class HUD {
        
        //
        private Entity applesImage;
        
        // Life Bar
        private int widthLifeBar = 200;
        private int heightLifeBar = 30;
        private int lifeBarX;
        private int lifeBarY;
        
        public HUD() {
            applesImage = new Entity();
            applesImage.setWidth(40);
            applesImage.setHeight(40);
            applesImage.setScale(1.5);
            applesImage.setColumns(10);
            applesImage.setTotalFrames(30);
            applesImage.setPosition(width / 2, applesImage.getCtoH() + 5);
            applesImage.setVisible(true);
            
            try {
                applesImage.setImage(ImageIO.read(new File("res/hud/apples_image.png")));
            } catch (IOException e) {}
        
            lifeBarX = (width - widthLifeBar) / 2;
            lifeBarY = 70;
        }
        
        public void draw(Screen screen) {
            // Life Bar
            if(state != STATE.MAIN_MENU) {
                if(worm.getScale() > 1)
                    screen.setColor(Color.GREEN);
                else
                    screen.setColor(Color.RED);
                screen.fillRect(lifeBarX, lifeBarY, widthLifeBar * worm.getLife() / 100, heightLifeBar);
                screen.setColor(Color.BLACK);
                screen.drawRect(lifeBarX, lifeBarY, widthLifeBar, heightLifeBar);
                
                applesImage.setCurrentFrame(appleSequence);
                applesImage.draw(screen);
            }
            
            // Pause button
            if(state == STATE.PLAYING)
                pauseBtn.draw(screen);
        }
    }
}

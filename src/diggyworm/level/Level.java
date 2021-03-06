/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package diggyworm.level;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import diggyworm.Game;
import diggyworm.entity.EntityGenerator;
import diggyworm.gfx.Screen;

/**
 * This class handles updates the level environment and generates
 * game entities
 * 
 */

public class Level {

    private Game game;
    private List<MapImage> images; // Background images
    private EntityGenerator generator;

    private float position;
    private boolean started = false;

    // Level bounds
    private final int topBound;
    private final int bottomBound;

    public int entitiesLong = 0;

    public Level(Game game) {
        // Initialization
        this.game = game;
        images = new LinkedList<MapImage>();
        generator = new EntityGenerator(game, this);

        setImages(); // Loads the images

        // Set the level bounds
        topBound = 150;
        bottomBound = game.getHeight();
    }

    public void setImages() {
        MapImage auxMapImage;
        BufferedImage auxImage = null;

        auxImage = game.getScreenManager().createCompatibleImage("/MountainMap/MountainMap_Tierra.png", 2000, 768);
        auxMapImage = new MapImage(auxImage, this);
        auxMapImage.setRelativeVelocity(game.getWormVelocity());
        images.add(auxMapImage);

        auxImage = game.getScreenManager().createCompatibleImage("/MountainMap/MountainMap_Pasto.png", 2000, 768);
        auxMapImage = new MapImage(auxImage, this);
        auxMapImage.setRelativeVelocity(game.getWormVelocity());
        images.add(auxMapImage);

        auxImage = game.getScreenManager().createCompatibleImage("/MountainMap/MountainMap_Cielo.png", 2000, 768);
        auxMapImage = new MapImage(auxImage, this);
        auxMapImage.setRelativeVelocity(game.getWormVelocity());
        auxMapImage.setRelativeVelocityFactor(0.1f);
        images.add(auxMapImage);

        auxImage = game.getScreenManager().createCompatibleImage("/MountainMap/MountainMap_Mont2.png", 2000, 768);
        auxMapImage = new MapImage(auxImage, this);
        auxMapImage.setRelativeVelocity(game.getWormVelocity());
        auxMapImage.setRelativeVelocityFactor(0.1f);
        images.add(auxMapImage);

        auxImage = game.getScreenManager().createCompatibleImage("/MountainMap/MountainMap_Mont1.png", 2000, 768);
        auxMapImage = new MapImage(auxImage, this);
        auxMapImage.setRelativeVelocity(game.getWormVelocity());
        auxMapImage.setRelativeVelocityFactor(0.2f);
        images.add(auxMapImage);
    }

    public Game getGame() {
        return game;
    }

    public float getPosition() {
        return position;
    }

    public int getTopBound() {
        return topBound;
    }

    public int getBottomBound() {
        return bottomBound;
    }

    public boolean started() {
        return started;
    }

    public void start() {
        started = true;
    }

    // Resets the level
    public void restart() {
        position = 0;
        entitiesLong = 0;
        for (MapImage m : images) {
            m.setX(0);
        }
    }

    public void finish() {
        started = false;
    }

    // Updates the level
    public void update(double dt) {
        // Updates each image
        for (MapImage img : images) {
            img.move(dt);
        }

        // Generates entities only when the state is PLAYING
        if (game.getState() == Game.STATE.PLAYING) {
            position += game.getWormVelocity().x * dt;

            // Generates entities avery 1500 pixels
            if (entitiesLong <= position) {
                entitiesLong += generator.populate(1500);
            }
        }
    }

    // Draws the level images
    public void draw(Screen screen) {
        for (MapImage img : images) {
            img.draw(screen);
        }
    }
}

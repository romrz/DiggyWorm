/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package diggyworm.gui;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import diggyworm.Game;
import diggyworm.gfx.Screen;
import diggyworm.math.Vector;

/**
 * This class creates game menus, with images and buttons
 * 
 */

public class Menu {

    private Game game;
    protected BufferedImage image; // Background Image (optional)
    private List<Button> buttons; // Menu Buttons
    protected Vector position;
    protected int width;
    protected int height;

    public Menu(Game game) {
        buttons = new LinkedList<Button>();

        width = game.getWidth() / 2;
        height = game.getHeight() / 2;
        position = new Vector(game.getWidth() / 2, game.getHeight() / 2);

        this.game = game;
    }

    public void addButton(Button b) {
        buttons.add(b);
    }

    // Handles the user input on this menu
    public void inputHandling(MouseEvent e) {
        
        for (Button b : buttons) {
            
            if (b.isPressed(e.getX(), e.getY())) {
                
                switch (b.getAction()) {
                    case PLAY:
                        game.setState(Game.STATE.WAITING_START);
                        return;
                    case RESUME:
                        game.setState(Game.STATE.PLAYING);
                        return;
                    case RESTART:
                        game.restart();
                        game.setState(Game.STATE.WAITING_START);
                        return;
                    case MAIN_MENU:
                        game.stop();
                        game.setState(Game.STATE.MAIN_MENU);
                        return;
                    case EXIT:
                        System.exit(0);
                    default:
                }
                
            }
            
        }
        
    }

    // Draws the menu
    public void draw(Screen screen) {
        if (image != null) {
            screen.drawImage(image, (int) position.x - width / 2, (int) position.y - height / 2, width, height);
        }

        for (Button b : buttons) {
            b.draw(screen);
        }
    }
}

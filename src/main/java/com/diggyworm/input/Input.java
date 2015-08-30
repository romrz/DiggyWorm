/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.diggyworm.Game;

/**
 * This class handles the user input
 */
public class Input implements KeyListener, MouseListener {

    public Game game;

    // Auxiliary to check if the worm is digging
    public boolean digging = false;

    public Input(Game game) {
        this.game = game;
    }

    public void reset() {
        digging = false;
    }

    public void keyTyped(KeyEvent event) {
    }

    public void keyPressed(KeyEvent event) {
        // if the space bar is pressed, the worm is digging
        if (event.getKeyCode() == KeyEvent.VK_SPACE && digging == false) {
            if (game.getState() == Game.STATE.WAITING_START) {
                game.setState(Game.STATE.PLAYING);
            }

            game.getWorm().digDown();
            digging = true;
        }
    }

    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (game.getState() == Game.STATE.PLAYING) {
            switch (keyCode) {
                // Dig up
                case KeyEvent.VK_SPACE:
                    game.getWorm().digUp();
                    digging = false;
                    break;
                // Scale up the worm
                case KeyEvent.VK_PLUS:
                    game.getWorm().setScale(game.getWorm().getScale() + 0.1);
                    break;
                // Scale down the worm
                case KeyEvent.VK_MINUS:
                    game.getWorm().setScale(game.getWorm().getScale() - 0.1);
                    break;
                // Set the worm visible or invisible
                case KeyEvent.VK_V:
                    game.getWorm().setVisible(!game.getWorm().isVisible());
                    break;
                default:
                    break;
                }
        }

        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {

        // Hendles the input depending on which state it is 
        if (game.getState() == Game.STATE.PLAYING) {
            if (game.pauseBtn.isPressed(e.getX(), e.getY())) {
                game.setState(Game.STATE.PAUSE);
            }
        } else if (game.getState() == Game.STATE.MAIN_MENU) {
            game.mainMenu.inputHandling(e);
        } else if (game.getState() == Game.STATE.PAUSE) {
            game.pauseMenu.inputHandling(e);
        } else if (game.getState() == Game.STATE.GAME_OVER) {
            game.gameOverMenu.inputHandling(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
    }
}

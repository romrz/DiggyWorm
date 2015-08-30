/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.gui;

import java.awt.Color;

import com.diggyworm.gfx.Screen;
import com.diggyworm.math.Vector;

/**
 * This class implements the functionality of a button
 * 
 */

public class Button {
    // Button Action
    public static enum ACTION {
        PLAY, RESTART, RESUME, PAUSE, MAIN_MENU, EXIT
    };

    private String text; // Text shown in the button
    private ACTION action; // Action of this button
    private Vector position;
    private int width;
    private int height;

    public Button(String t, ACTION a) {
        position = new Vector();
        text = t;
        action = a;
        width = 110;
        height = 30;
    }

    public void setAction(ACTION a) {
        action = a;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public String getText() {
        return text;
    }

    public ACTION getAction() {
        return action;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Checks if the button was pressed
    public boolean isPressed(int x, int y) {
        return x >= position.x - width / 2 && x <= position.x + width / 2
                && y >= position.y - height / 2 && y <= position.y + height / 2;
    }

    // Draws the button
    public void draw(Screen screen) {
        int x = (int) position.x - width / 2;
        int y = (int) position.y - height / 2;

        screen.setColor(new Color(2, 99, 0));
        screen.fillRect(x, y, width, height);
        screen.setColor(new Color(3, 130, 0));
        screen.fillRect(x + 5, y + 5, width - 10, height - 10);

        screen.setColor(Color.WHITE);
        screen.drawText(text, x + 20, y + 20);
    }
}

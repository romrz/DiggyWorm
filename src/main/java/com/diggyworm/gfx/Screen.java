/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.gfx;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This class is made to support different graphic engines.
 * With this implementation it will be easier to change the graphic engine
 * in the future without making a lot of changes
 *
 */
public class Screen {

    private Graphics2D g;

    public void setGraphics(Graphics2D g) {
        this.g = g;
    }

    public Graphics2D getGraphics() {
        return g;
    }

    public void rotate(double angle, double x, double y) {
        g.rotate(angle, x, y);
    }

    public void scale(double x, double y) {
        g.scale(x, y);
    }

    public void setColor(Color color) {
        g.setColor(color);
    }

    public void fillRect(float x, float y, int width, int height) {
        fillRect(x, y, width, height);
    }

    public void fillRect(int x, int y, int width, int height) {
        g.fillRect(x, y, width, height);
    }
    
    public void drawRect(int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
    }

    public void drawImage(BufferedImage image, double dx1, double dy1, double dx2, double dy2, double sx1, double sy1, double sx2, double sy2) {
        g.drawImage(image, (int) dx1, (int) dy1, (int) dx2, (int) dy2, (int) sx1, (int) sy1, (int) sx2, (int) sy2, null);
    }

    public void drawImage(BufferedImage image, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void drawText(String string, int x, int y) {
        g.drawString(string, x, y);
    }
}

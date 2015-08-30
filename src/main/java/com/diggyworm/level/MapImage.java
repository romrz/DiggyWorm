/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.level;

import java.awt.image.BufferedImage;

import com.diggyworm.gfx.Screen;
import com.diggyworm.math.Vector;

/**
 * A MapImage is a background image for the level It is able to do infinite
 * scrolling
 *
 */
public class MapImage {

    private Level level;
    private BufferedImage image;
    private int width;
    private int height;
    private Vector position;
    private Vector velocity;
    // It moves based on a relative velocity, which is the one of the worm
    private Vector relativeVelocity;
    // This is the amount of the relative velocity applied to this MapImage
    private float relativeVelocityFactor = 1f;
    private boolean visible;
    private boolean loop; // True for infinite looping

    public MapImage(BufferedImage img, Level level) {
        // Initialization
        this.level = level;
        image = img;
        width = img.getWidth();
        height = img.getHeight();
        position = new Vector();
        velocity = new Vector();
        relativeVelocity = new Vector();
        visible = true;
        loop = true;
    }

    // Setters
    public void setImage(BufferedImage img) {
        image = img;
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }

    public void setVX(float vx) {
        velocity.x = vx;
    }

    public void setVY(float vy) {
        velocity.y = vy;
    }

    public void setVelocity(Vector v) {
        velocity = v;
    }

    public void setRelativeVelocity(Vector relVel) {
        relativeVelocity = relVel;
    }

    public void setRelativeVelocityFactor(float f) {
        relativeVelocityFactor = f;
    }

    public void setVisible(boolean v) {
        visible = v;
    }

    public void setLooping(boolean looping) {
        loop = looping;
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean getLooping() {
        return loop;
    }

    public float getRelativeVelocityFactor() {
        return relativeVelocityFactor;
    }

    // Moves this map image
    public void move(double dt) {
        position.x += (velocity.x - relativeVelocity.x * relativeVelocityFactor) * dt;
        position.y += velocity.y * dt;

        if (position.x < -width && loop) {
            position.x = position.x + width;
        }
    }

    // Draws the map image
    public void draw(Screen screen) {
        if (!visible) {
            return;
        }

        float x = position.x;

        screen.drawImage(image, (int) position.x, (int) position.y, width, height);

        if (loop && position.x < -width + level.getGame().getWidth()) {
            position.x = x + width;
            screen.drawImage(image, (int) position.x, (int) position.y, width, height);
        }

        position.x = x;
    }
}

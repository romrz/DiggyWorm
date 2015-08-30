/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.entity;

import java.awt.image.BufferedImage;

import com.diggyworm.gfx.Screen;
import com.diggyworm.math.Vector;

/**
 * This class implements the common behavior of all the game entities
 *
 */
public class Entity {

    // Constants for different entities types
    public static final int TYPE_WORM = 1;
    public static final int TYPE_WORM_HEAD = 2;
    public static final int TYPE_WORM_SEGMENT = 3;
    public static final int TYPE_RED_APPLE = 4;
    public static final int TYPE_GREEN_APPLE = 5;
    public static final int TYPE_ROCK = 6;
    public static final int TYPE_IMAGE = 7;
    public static final int TYPE_MOLE = 8;
    public static final int TYPE_STICK = 9;
    public static final int TYPE_BUTTON = 10;

    // Type of collision
    public static final int CIRCLE_COLLISION = 1;
    public static final int RECT_COLLISION = 2;

    private BufferedImage image; // Entity's image
    private int width; // Entity's width
    private int height; // Entity's height
    private Vector position;
    private Vector velocity;
    private Vector relativeVelocity; // Relative speed of this entity's moving 
    private float relativeVelocityFactor; // Factor of the relative speed
    private double scale;
    private double rotation;
    private boolean visible;
    private boolean collidable;
    private int collisionType;
    private boolean alive;
    private int type;
    private long lifeTime; // Time for the entity to die
    private int frameTime; // FPS for animation
    private int animationDirection; // 1 to animate forward. -1 to backward
    private int frameCount; // Animation frame counter
    private int currentFrame; // Current frame in the animation
    private int totalFrames; // Total frames of the animation
    private int columns; // Columns of the sprite sheet for animation
    private float CtoW; // Pre-calculated distance of width / 2 (Center to Width)
    private float CtoH; // Pre-calculated distance of height / 2 (Center to Height)

    // Testing variable
    public boolean isCollisioned = false;

    public Entity() {
        // Initialization
        width = 0;
        height = 0;
        position = new Vector();
        velocity = new Vector();
        relativeVelocity = new Vector();
        relativeVelocityFactor = 1f;
        scale = 1.0;
        rotation = 0;
        visible = false;
        collidable = false;
        collisionType = RECT_COLLISION;
        alive = true;
        animationDirection = 1;
        totalFrames = 1;
        columns = 1;
    }

    // Setters
    public void setImage(BufferedImage img) {
        image = img;
    }

    public void setWidth(int w) {
        width = w;
        CtoWH();
    }

    public void setHeight(int h) {
        height = h;
        CtoWH();
    }

    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void setPosition(Vector p) {
        position = p;
    }

    public void setVX(float vx) {
        velocity.x = vx;
    }

    public void setVY(float vy) {
        velocity.y = vy;
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public void setVelocity(Vector v) {
        velocity = v;
    }

    public void setRelativeVelocity(Vector rv) {
        relativeVelocity = rv;
    }

    public void setRelativeVelocityFactor(float f) {
        relativeVelocityFactor = f;
    }

    public void setScale(double s) {
        scale = s;
        CtoWH();
    }

    public void setRotation(double r) {
        rotation = r;
    }

    public void setVisible(boolean v) {
        visible = v;
    }

    public void setCollidable(boolean c) {
        collidable = c;
    }

    public void setCollisionType(int c) {
        collisionType = c;
    }

    public void setAlive(boolean a) {
        alive = a;
    }

    public void setType(int t) {
        type = t;
    }

    public void setLifeTime(long lt) {
        lifeTime = lt;
    }

    public void setFrameTime(int ft) {
        frameTime = ft;
    }

    public void setAnimationDirection(int d) {
        animationDirection = d;
    }

    public void setTotalFrames(int f) {
        totalFrames = f;
    }

    public void setCurrentFrame(int cf) {
        currentFrame = cf;
    }

    public void setColumns(int c) {
        columns = c;
    }

    public void CtoWH() {
        CtoW = width * (float) scale / 2;
        CtoH = height * (float) scale / 2;
    }

    // Getters
    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector getPosition() {
        return position;
    }

    public float getVX() {
        return velocity.x;
    }

    public float getVY() {
        return velocity.y;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getRelativeVelocity() {
        return relativeVelocity;
    }

    public float getRelativeVelocityFactor() {
        return relativeVelocityFactor;
    }

    public double getScale() {
        return scale;
    }

    public double getRotation() {
        return rotation;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean getCollidable() {
        return collidable;
    }

    public int getCollisionType() {
        return collisionType;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getType() {
        return type;
    }

    public long getLifeTime() {
        return lifeTime;
    }

    public int getAnimationDirection() {
        return animationDirection;
    }

    public float getCtoW() {
        return CtoW;
    }

    public float getCtoH() {
        return CtoH;
    }

    // Animates this entity frame by frame
    public void animate() {
        if (totalFrames > 1) {
            frameCount++;

            if (frameCount > frameTime) {
                frameCount = 0;
                currentFrame += animationDirection;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                } else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }
    }

    // Moves this entity
    public void move(double dt) {
        if (lifeTime > 0) {
            lifeTime--;
            if (lifeTime == 0) {
                setAlive(false);
            }
        }

        position.x += (velocity.x - relativeVelocity.x * relativeVelocityFactor) * dt;
        position.y += velocity.y * dt;
    }

    // Draws this entity
    public void draw(Screen screen) {
        if (image == null) {
            return;
        }

        int frameX = (currentFrame % columns) * width;
        int frameY = (currentFrame / columns) * height;

        double x1 = position.x - getCtoW();
        double y1 = position.y - getCtoH();

        screen.rotate(rotation, position.x, position.y);
        //screen.scale(scale, scale);
        screen.drawImage(image, x1, y1, x1 + getCtoW() * 2, y1 + getCtoH() * 2,
                frameX, frameY, frameX + width, frameY + height);
        //screen.scale(1 / scale, 1 / scale);
        screen.rotate(-rotation, position.x, position.y);
    }
}
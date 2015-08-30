/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.entity;

import java.awt.image.BufferedImage;

import com.diggyworm.Game;
import com.diggyworm.math.Vector;

public class Worm extends Entity {

    public final int DEFAULT_POSITION_X = 400;
    public final int DEFAULT_POSITION_Y = 400;
    public final float DEFAULT_VELOCITY_X = 400f;
    public final float DEFAULT_VELOCITY_Y = -250f;

    private int length = 15; // Amount of worm segments
    public WormSegment head;
    private WormSegment segments[] = new WormSegment[length];

    private int life = 100; // worm's life

    public Worm() {
        // Initializes the segments
        for (int i = 0; i < length; i++) {
            segments[i] = new WormSegment();
        }

        setDefaultPosition();

        head = segments[0];
        head.setType(Entity.TYPE_WORM_HEAD);
        head.setCollisionType(CIRCLE_COLLISION);

        setType(Entity.TYPE_WORM);
        setVelocity(DEFAULT_VELOCITY_X, DEFAULT_VELOCITY_Y);
    }

    // Adds this worm to the game entities list
    public void addToEntities(Game game) {
        game.addEntity(this);
        for (WormSegment segment : segments) {
            game.addEntity(segment);
        }
    }

    // Set the segments and head images 
    public void setImage(BufferedImage wormImage, BufferedImage wormSegmentImage) {
        for (int i = 1; i < length; i++) {
            segments[i].setImage(wormSegmentImage);
        }

        head.setImage(wormImage);
    }

    // Scales the worm
    @Override
    public void setScale(double scale) {
        super.setScale(scale);

        for (int i = 0; i < length; i++) {
            segments[i].setScale(scale);
        }
    }

    // Change worm´s visibility
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        for (int i = 0; i < length; i++) {
            segments[i].setVisible(visible);
        }
    }

    @Override
    public void setCollidable(boolean collidable) {
        super.setCollidable(collidable);

        head.setCollidable(collidable);
    }

    @Override
    public void setAlive(boolean alive) {
        super.setAlive(alive);

        for (int i = 0; i < length; i++) {
            segments[i].setAlive(alive);
        }
    }

    // Sets the worm to its initial position
    public void setDefaultPosition() {
        for (int i = 0; i < length; i++) {
            segments[i].setPosition(DEFAULT_POSITION_X - segments[i].getWidth() * i, DEFAULT_POSITION_Y);
            segments[i].setVisible(true);
        }
    }

    public void setDefaultVelocity() {
        setVelocity(DEFAULT_VELOCITY_X, 0);
    }

    @Override
    public void setX(float x) {
        head.setY(x);
    }

    @Override
    public void setY(float y) {
        head.setY(y);
    }

    public void setLife(int life) {
        this.life = life >= 0 ? life <= 100 ? life : 100 : 0;
    }
    
    @Override
    public float getX() {
        return head.getX();
    }
    
    @Override
    public float getY() {
        return head.getY();
    }
    
    @Override
    public Vector getPosition() {
        return head.getPosition();
    }

    public int getLife() {
        return life;
    }

    public void digDown() {
        setVY(-DEFAULT_VELOCITY_Y);
    }

    public void digUp() {
        setVY(DEFAULT_VELOCITY_Y);
    }

    // Move the worm and adjust its segments
    @Override
    public void move(double dt) {
        setY(getY() + getVY() * (float) dt);
        head.setRotation(Math.atan2(getVY(), getVX()));

        for (int i = 1; i < length; i++) {
            // Moves the segments backwards
            segments[i].setX(segments[i].getX() - getVX() * (float) dt);

            double distance = Math.sqrt(Math.pow(segments[i - 1].getPosition().x - segments[i].getPosition().x, 2)
                            + Math.pow(segments[i - 1].getPosition().y - segments[i].getPosition().y, 2));
            
            double angle = Math.atan2(segments[i - 1].getPosition().y - segments[i].getPosition().y,
                            segments[i - 1].getPosition().x - segments[i].getPosition().x);

            segments[i].setX(segments[i].getX() + (float) ((distance - head.getWidth() * head.getScale()) * Math.cos(angle)));
            segments[i].setY(segments[i].getY() + (float) ((distance - head.getWidth() * head.getScale()) * Math.sin(angle)));

            segments[i].setRotation(angle);
        }
    }
}

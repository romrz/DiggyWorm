/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.entity;

import com.diggyworm.math.Vector;

public class WormSegment extends Entity {

    public WormSegment() {
        setWidth(40);
        setHeight(40);
        setType(TYPE_WORM_SEGMENT);
        CtoWH();
    }

    public void move(double dt, Vector wormVel) {
    }
}

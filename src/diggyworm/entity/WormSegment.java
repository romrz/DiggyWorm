/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package diggyworm.entity;

import diggyworm.math.Vector;

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

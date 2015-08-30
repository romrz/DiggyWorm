/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package com.diggyworm.entity;

public class Mole extends Entity {

    public Mole() {
        setWidth(40);
        setHeight(60);
        setCollidable(true);
        setType(TYPE_MOLE);
        setCollisionType(RECT_COLLISION);
    }

}

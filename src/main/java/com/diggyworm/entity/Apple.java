//
//  @ Project : DiggyWorm
//  @ File Name : Apple.java
//  @ Date : 12/04/2014
//  @ Author : Romario Ramirez
//
package com.diggyworm.entity;

public class Apple extends Entity {

    public Apple(int type) {
        setWidth(40);
        setHeight(40);
        setType(type);
        setCollidable(true);
        setCollisionType(CIRCLE_COLLISION);
        CtoWH(); // Calculates the distance form entity´s center to width an height
    }
}

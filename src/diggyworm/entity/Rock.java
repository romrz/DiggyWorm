/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */
package diggyworm.entity;

public class Rock extends Entity {

    public Rock() {
        setWidth(40);
        setHeight(40);
        setCollidable(true);
        setType(TYPE_ROCK);
        setCollisionType(CIRCLE_COLLISION);
        CtoWH();
    }
}

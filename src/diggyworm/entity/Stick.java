/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package diggyworm.entity;

public class Stick extends Entity {

    public Stick() {
        setWidth(140);
        setHeight(40);
        setCollidable(true);
        setType(TYPE_STICK);
        setCollisionType(RECT_COLLISION);
        CtoWH();
    }

}

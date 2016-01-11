/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package diggyworm.entity;

import java.util.Random;

import diggyworm.Game;
import diggyworm.level.Level;

/**
 * This class generates entities on the fly each certain distance
 *
 */
public class EntityGenerator {

    private Game game;
    private Level level;
    private Random random;

    // Entities groups
    private final int GROUP_LINE_3 = 1; // Line of 3 entities
    private final int GROUP_HEART = 3; // Line of 3 entities
    private final int GROUP_1 = 2; // One entity

    public EntityGenerator(Game game, Level level) {
        this.game = game;
        this.level = level;

        random = new Random();
    }

    // Contains all the templates of the groups
    public char[][] groupTemplate(int group) {
        char[][] temp = null;
        switch (group) {
            case GROUP_LINE_3:
                temp = new char[][]{
                    {'+'},
                    {'+'},
                    {'+'}};
                break;
            case GROUP_HEART:
                temp = new char[][]{
                    {' ', '+', ' ', ' '},
                    {'+', '+', '+', ' '},
                    {' ', '+', '+', '+'},
                    {'+', '+', '+', ' '},
                    {' ', '+', ' ', ' '}
                };
                break;
            case GROUP_1:
                temp = new char[][]{
                    {'+'}};
                break;
            default:
                break;
        }

        return temp;
    }

    // Generates one entity
    public void generateEntity(int x, int y, int e) {
        Entity entity = null;

        if (e == Entity.TYPE_RED_APPLE) {
            entity = (Entity) new Apple(e);
            entity.setImage(Game.RED_APPLE_IMAGE);
        } else if (e == Entity.TYPE_GREEN_APPLE) {
            entity = (Entity) new Apple(e);
            entity.setImage(Game.GREEN_APPLE_IMAGE);
        } else if (e == Entity.TYPE_ROCK) {
            entity = (Entity) new Rock();
            entity.setImage(Game.ROCK_IMAGE);
        } else if (e == Entity.TYPE_MOLE) {
            entity = (Entity) new Mole();
            entity.setImage(Game.MOLE_IMAGE);
            entity.setScale(1.5);
            entity.setVY(80 * (random.nextBoolean() ? -1 : 1));
            entity.setRotation(entity.getVY() > 0 ? 0 : Math.PI);
        } else if (e == Entity.TYPE_STICK) {
            entity = (Entity) new Stick();
            entity.setImage(Game.STICK_IMAGE);
            entity.setRotation(random.nextDouble() * Math.PI * 2);
        }

        entity.setPosition(x, y);
        entity.setRelativeVelocity(game.getWormVelocity());
        game.addEntity(entity);
    }

    // Generates a group of rocks
    public int generateRockGroup(int posX, int posY) {
        if (posY < level.getTopBound()) {
            posY = level.getTopBound();
        }

        int distance = 0;

        int l1 = random.nextInt(9);
        int l2 = 8 - l1;

        int top = level.getTopBound() + 20;
        int bottom = level.getBottomBound() + 20;

        for (int y = 0; y < l1; y++) {
            for (int x = 0; x < 4; x++) {
                generateEntity(posX + x * 40, top + y * 40, Entity.TYPE_ROCK);
            }
        }

        for (int y = 0; y < l2; y++) {
            for (int x = 0; x < 4; x++) {
                generateEntity(posX + x * 40, bottom - (y + 1) * 40, Entity.TYPE_ROCK);
            }
        }

        return 4 * 40; // Group's width
    }

    // Generates a given group of entities
    public int generateGroup(int x, int y, int group, int e) {
        char template[][] = groupTemplate(group);

        if (y + 20 > level.getBottomBound() - template[0].length * 40) {
            y = level.getBottomBound() - template[0].length * 40 - 20;
        } else if (y - 20 < level.getTopBound()) {
            y = level.getTopBound() + 20;
        }

        for (int i = 0; i < template.length; i++) {
            for (int j = 0; j < template[0].length; j++) {
                if (template[i][j] == '+') {
                    generateEntity(x + i * 40, y + j * 40, e);
                }
            }
        }

        return template.length * 40;  // Group's width
    }

    // Populates at a given position
    public int populate(int x) {
        int distance = 0;
        int r = random.nextInt(6);

        switch (r) {
            case 0:
                distance += generateRockGroup(x, random.nextInt(game.getHeight()));
                break;
            case 1:
                distance += generateGroup(x, random.nextInt(game.getHeight()), GROUP_1, Entity.TYPE_MOLE);
                break;
            case 2:
                distance += generateGroup(x, random.nextInt(game.getHeight()), GROUP_1, Entity.TYPE_STICK);
                break;
            case 3:
                distance += generateGroup(x, random.nextInt(game.getHeight()), GROUP_LINE_3, Entity.TYPE_RED_APPLE);
                break;
            case 4:
                distance += generateGroup(x, random.nextInt(game.getHeight()), GROUP_HEART, Entity.TYPE_RED_APPLE);
                break;
            case 5:
                distance += generateGroup(x, random.nextInt(game.getHeight()), GROUP_1, Entity.TYPE_GREEN_APPLE);
                break;
            default:
                break;
        }

        // Distance until next population
        return distance + 8 * 40;
    }
}

/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package diggyworm.gui;

import diggyworm.Game;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameOverMenu extends Menu {

    public GameOverMenu(Game game) {
        super(game);
        
        try {
            image = ImageIO.read(Game.class.getResource("/menus/gameovermenu_img.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	

        Button btn;

        btn = new Button("Volver a jugar", Button.ACTION.RESTART);
        btn.setPosition(position.x + width / 2 - btn.getWidth(), position.y + height / 2 - btn.getHeight());
        addButton(btn);

        btn = new Button("Menu Principal", Button.ACTION.MAIN_MENU);
        btn.setPosition(position.x - width / 2 + btn.getWidth(), position.y + height / 2 - btn.getHeight());
        addButton(btn);
    }
}

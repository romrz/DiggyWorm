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

public class MainMenu extends Menu {

    public MainMenu(Game game) {

        super(game);
        
        try {
            image = ImageIO.read(Game.class.getResource("/menus/mainmenu_img.png"));
        } catch (IOException e) {
            System.out.println("Error in MainMenu");
            e.printStackTrace();
        }

        Button btn;

        btn = new Button("Jugar", Button.ACTION.PLAY);
        btn.setPosition(position.x + width / 2 - btn.getWidth(), position.y + height / 2 - btn.getHeight());
        addButton(btn);

        btn = new Button("Salir", Button.ACTION.EXIT);
        btn.setPosition(position.x - width / 2 + btn.getWidth(), position.y + height / 2 - btn.getHeight());
        addButton(btn);

    }
}

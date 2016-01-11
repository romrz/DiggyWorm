/**
 *
 * @ Project : DiggyWorm
 * @ File Name : Game.java
 * @ Author : Romario Ramirez
 *
 */

package diggyworm.gfx;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * This class handles all the screen modes and resolutions available
 *
 */
public class ScreenManager {

    private GraphicsEnvironment graphicsEnvironment;
    private GraphicsDevice graphicsDevice;
    private JFrame frame;
    private boolean fullScreen = false;

    public ScreenManager(JFrame frame) {
        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        this.frame = frame;
    }

    public void setFullScreen() {
        try {
            if (graphicsDevice.isFullScreenSupported()) {
                graphicsDevice.setFullScreenWindow(frame);
                fullScreen = true;
            }

            if (graphicsDevice.isDisplayChangeSupported()) {
                graphicsDevice.setDisplayMode(graphicsDevice.getDisplayMode());
            }
        } finally {
        }
    }

    public void exitFullScreen() {
        graphicsDevice.setFullScreenWindow(null);
        fullScreen = false;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public DisplayMode getDisplayMode() {
        return graphicsDevice.getDisplayMode();
    }

    public GraphicsConfiguration getGraphicsConfiguration() {
        return graphicsDevice.getDefaultConfiguration();
    }

    public BufferedImage createCompatibleImage(String path, int width, int height) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(ScreenManager.class.getResource(path));
        } catch (IOException e) {
        }

        BufferedImage imgComp
                = getGraphicsConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

        imgComp.getGraphics().drawImage(img, 0, 0, null);

        return imgComp;
    }

}

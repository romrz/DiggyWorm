//
//  @ Project : DiggyWorm
//  @ File Name : DiggyWorm.java
//  @ Date : 12/04/2014
//  @ Author : Romario Ramirez
//

package com.diggyworm;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class DiggyWorm {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Diggy Worm");

        Game game = new Game(frame);

        /* Uncomment this line for full screen */
	frame.setUndecorated(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setIgnoreRepaint(true);
        frame.setVisible(true);

        /* Uncomment this line for full screen */
	game.setFullScreen();

        game.start();

    }
}

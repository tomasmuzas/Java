package lt.vu.mif.pacman.GUI;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

import lt.vu.mif.pacman.IO.Parameters;

public class GUI extends JLayeredPane{
	private static GUI instance;
	public static final long serialVersionUID = 1L;
	/**
	 * Singleton pattern, only one graphics engine can be used at a time
	 */
	private GUI(){
		
	}
	
	/**
	 * Creates GUI instance (if it is not created) and returns it
	 * @return [GUI] instance of GUI
	 */
	public static GUI getInstance(){
		if(instance == null){
			instance = new GUI();
			instance.setPreferredSize(new Dimension(Parameters.WIDTH_IN_PIXELS,Parameters.HEIGHT_IN_PIXELS));
			instance.setLayout(null);
		}
		return instance;
	}
	
	
	/**
	 * Paints all the components added to it
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	/**
	 * Draws text on the screen
	 * @param text[String] - text to be drawn
	 */
    public static void drawText(String text){
    	if(instance != null){
        	Graphics g = instance.getGraphics();
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 50));
            g.drawString(text, 300, 300);
    	}

    }
}

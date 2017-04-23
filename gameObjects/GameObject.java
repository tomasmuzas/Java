package lt.vu.mif.pacman.gameObjects;

import java.awt.*;
import javax.swing.JLabel;

import lt.vu.mif.pacman.IO.Parameters;


public class GameObject extends JLabel{
	/**
	 * Getters and setters
	 */
	private int facing = 1;
	// 4
	//3 1
	// 2
	private int posX;
	private int posY;
	private Color color;
	public static final long serialVersionUID = 1L;
	
	public GameObject(int x, int y, Color color){
		this.posX = x;
		this.posY = y;
		this.color = color;
		this.setVerticalAlignment(JLabel.TOP);
		this.setHorizontalAlignment(JLabel.LEFT);
		this.setOpaque(false);
		this.setSize(new Dimension(800,800));
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	
	public void setPosX(int x){
		this.posX = x;
	}
	
	public void setPosY(int y){
		this.posY = y;
	}
	
	public Color getColor(){
		return color;
	}

	/**
	 * Draw the creature
	 * @param [Graphics] g - graphics component to draw the shape
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	    g.setColor(Color.BLACK);
	    int lineStartX = 0;
	    int lineEndX = 0;
	    int lineStartY = 0;
	    int lineEndY = 0;
	    int rect1X = 0;
	    int rect1Y = 0;
	    int rect1Width = 5;
	    int rect2X = 0;
	    int rect2Y = 0;
	    int rect2Width = 5;
	    switch(facing){
	    	case 1:
	    		lineStartX = this.getPosX()+(Parameters.TILE_SIZE)/2+5;
	    		lineStartY = this.getPosY()+(Parameters.TILE_SIZE/2)+3;
	    		lineEndX = this.getPosX()+Parameters.TILE_SIZE;
	    		lineEndY = this.getPosY()+(Parameters.TILE_SIZE/2)+3;
	    		
	    		rect1X = this.getPosX()+(Parameters.TILE_SIZE)/2;
	    		rect1Y = this.getPosY()+(Parameters.TILE_SIZE)/4;
	    		rect1Width = 5;
	    		
	    		rect2X = 0;
	    		rect2Y = 0;
	    		rect2Width = 0;
	    		break;
	    	
	    	case 3:
	    		lineStartX = this.getPosX();
	    		lineStartY = this.getPosY()+(Parameters.TILE_SIZE/2)+3;
	    		lineEndX = this.getPosX()+(Parameters.TILE_SIZE)/2-3;
	    		lineEndY = this.getPosY()+(Parameters.TILE_SIZE/2)+3;
	    		
	    		rect1X = this.getPosX()+(Parameters.TILE_SIZE)/2-4;
	    		rect1Y = this.getPosY()+(Parameters.TILE_SIZE)/4;
	    		rect1Width = 5;
	    		
	    		rect2X = 0;
	    		rect2Y = 0;
	    		rect2Width = 0;
	    		break;
	    	case 4:
	    		lineStartX = 0;
	    		lineEndX = 0;
	    		lineStartY = 0;
	    		lineEndY = 0;
	    		
	    		rect1X = this.getPosX()+(Parameters.TILE_SIZE/4);
	    		rect1Y= this.getPosY()+(Parameters.TILE_SIZE/4)-1;
	    		rect1Width = 5;
	    		
	    		rect2X = this.getPosX()+(Parameters.TILE_SIZE/4)+(Parameters.TILE_SIZE/2-3);
	    		rect2Y = this.getPosY()+(Parameters.TILE_SIZE/4)-1;
	    		rect2Width = 5;
	    		break;
	    	case 2:
	    		lineStartX = 0;
	    		lineEndX = 0;
	    		lineStartY = 0;
	    		lineEndY = 0;
	    		
	    		rect1X = this.getPosX()+(Parameters.TILE_SIZE/4);
	    		rect1Y= this.getPosY()+(Parameters.TILE_SIZE/4)-1+(Parameters.TILE_SIZE/2-3);
	    		rect1Width = 5;
	    		
	    		rect2X = this.getPosX()+(Parameters.TILE_SIZE/4)+(Parameters.TILE_SIZE/2-3);
	    		rect2Y = this.getPosY()+(Parameters.TILE_SIZE/4)-1+(Parameters.TILE_SIZE/2-3);
	    		rect2Width = 5;
	    		break;
	    }
	    g.drawLine(lineStartX, lineStartY , lineEndX, lineEndY);
	    g.fillRect(rect1X,rect1Y, rect1Width, rect1Width);
	    g.fillRect(rect2X,rect2Y, rect2Width, rect2Width);
	}
	/**
	 * Redraw the creature
	 * @param [Graphics] g - graphics component to draw the shape
	 */
	
	public void repaint(){
		super.repaint();
	}
	
    
}




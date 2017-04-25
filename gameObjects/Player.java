package lt.vu.mif.pacman.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import lt.vu.mif.pacman.PacmanGame;
import lt.vu.mif.pacman.IO.Parameters;

public class Player extends GameObject implements KeyListener{
	
	public static final long serialVersionUID = 1L;
	private GameMap map; // Map instance
	private PacmanGame gameInstance; // Game instance

	/**
	 * Player Constructor
	 * @param x[int] - initial position of X
	 * @param y[int] - initial position of Y
	 * @param color - color of Player
	 */
	public Player(int x, int y, Color color){
		super(x,y,color);
	}
	
	/**
	 * Sets the instance of Map, needed to be able to interact with map array of Tiles
	 * @param mapInstance[Map]
	 */
	public void setMapInstance(GameMap mapInstance){
		map = mapInstance;
	}
	/**
	 * Sets the instance of Game, needed to check the state of the game
	 * @param gameInstance[Game]
	 */
	public void setGameInstance(PacmanGame gameInstance){
		this.gameInstance = gameInstance;
	}
	
	
	public void paintComponent(Graphics g){
		g.setColor(this.getColor());
		g.fillOval(this.getPosX(), this.getPosY(), 30, 30);
		super.paintComponent(g);
	}
	
	/**
	 * Additional movement to the player, not available to Enemy
	 * 1) Moves only if the Tile is not a wall
	 * 2) eats food, if the Tile is not empty
	 * 3) Checks for collisions
	 * 4) Redraws Player
	 * @param direction[String] - direction of movement
	 */
    
    private void move(String direction){
    	switch(direction){
    		case "left":
    			this.setFacing(3);
    			if(this.getPosX()/Parameters.TILE_SIZE-1 >=0 && !map.getTileAt(this.getPosY()/Parameters.TILE_SIZE,this.getPosX()/Parameters.TILE_SIZE-1).isWall()){
    				this.setPosX(this.getPosX()- Parameters.TILE_SIZE);
    			}
    			break;
    		case "right":
    			this.setFacing(1);
    			if(this.getPosX()/Parameters.TILE_SIZE+1 < map.getWidth() && !map.getTileAt(this.getPosY()/Parameters.TILE_SIZE,this.getPosX()/Parameters.TILE_SIZE+1).isWall()){
    				this.setPosX(this.getPosX() + Parameters.TILE_SIZE);
    			}
    			break;
    		case "up":
    			this.setFacing(4);
    			if(this.getPosY()/Parameters.TILE_SIZE-1 >=0 && !map.getTileAt(this.getPosY()/Parameters.TILE_SIZE-1,this.getPosX()/Parameters.TILE_SIZE).isWall()){
    				this.setPosY(this.getPosY() - Parameters.TILE_SIZE);
    			}
    			break;
    		case "down":
    			this.setFacing(2);
    			if(this.getPosY()/Parameters.TILE_SIZE+1 < map.getHeight() && !map.getTileAt(this.getPosY()/Parameters.TILE_SIZE+1,this.getPosX()/Parameters.TILE_SIZE).isWall()){
    	    		this.setPosY(this.getPosY() + Parameters.TILE_SIZE);
    			}
    			break;
    	}
		this.repaint();
		map.eatTileAt(this.getPosY()/Parameters.TILE_SIZE,this.getPosX()/Parameters.TILE_SIZE);
		gameInstance.checkIfGameWon();
		gameInstance.checkForCollision();
		gameInstance.stateChanged();
    	
    }
    /**
     * Detects pressed buttons and reacts to certain buttons by corresponding functions
     * @param e[KeyEvent]
     */
    public void keyPressed(KeyEvent e)
    {
	        switch(e.getKeyCode())
	        {
	           case KeyEvent.VK_A:
	           case KeyEvent.VK_LEFT:
	              this.move("left");
	              break;
	           case KeyEvent.VK_D:
	           case KeyEvent.VK_RIGHT:
	              this.move("right");
	              break;
	           case KeyEvent.VK_W:   
	           case KeyEvent.VK_UP:
	               this.move("up");
	               break;
	           case KeyEvent.VK_S:
	           case KeyEvent.VK_DOWN:
	               this.move("down");
	               break;
	              
	        }

    }
	
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    /**
     * Checks for collision with other GameObject
     * @param g[GameObject] - another GameObject to check collision with
     * @return boolean true - if Player collides with other GameObject, false - otherwise
     */
    public boolean collisionWith(GameObject g){
    	return this.getPosX() == g.getPosX() && this.getPosY() == g.getPosY();
    }
}
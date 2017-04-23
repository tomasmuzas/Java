package lt.vu.mif.pacman.gameObjects;

import lt.vu.mif.pacman.PacmanGame;

public class Tile implements Cloneable{
	private int x;
	private int y;
	private boolean food = true;
	private boolean wall = false;
	public double gScore = Double.MAX_VALUE;
	public double fScore = Double.MAX_VALUE;
	public Tile cameFrom = null;
	
	/**
	 * Constructor of a class Tile
	 * @param [int] startingX - starting position of a tile, X coordinate
	 * @param [int] startingY - starting position of a tile, X coordinate
	 * @param [boolean] hasFood - whether the Tile has food on it
	 * @param [boolean] isWall - is it a wall
	 */
	public Tile(int x, int y, boolean food, boolean wall){
		this.x = x;
		this.y = y;
		this.food = food;
		this.wall = wall;
	}
	
	public Tile(Tile t){
		this.x = t.getX();
		this.y = t.getY();
		this.food = t.hasFood();
		this.wall = t.isWall();
	}
	
	public Tile clone() throws CloneNotSupportedException{
		return (Tile) super.clone();
	}

	/**
	 * Getters of private fields
	 * 
	 */
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public boolean isWall(){
		return wall;
	}
	
	public boolean hasFood(){
		return food;
	}
	/**
	 * Removes food from a Tile
	 */
	public void eat(){
		if(this.food){
			this.food = false;
			PacmanGame.score += 1;
		}
	}

}
package lt.vu.mif.pacman.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import lt.vu.mif.pacman.PacmanGame;
import lt.vu.mif.pacman.IO.Parameters;

public class Enemy extends GameObject{
	public static final long serialVersionUID = 1L;
	private GameObject mainTarget = null;
	private ArrayList<Tile> shortestPath = new ArrayList<Tile>();
	private GameMap mapInstance;
	private ArrayList<ArrayList<Tile>> map;
	private PacmanGame gameInstance;
	private int moveTime = 400; // 0.4s
	private int searchTime = 3000; // 3s
	private Enemy thisInstance = this;
	
	/**
	 * Constructor of a class Enemy, inherits fields from Creature
	 * @param [int] startingX - initial starting position, coordinate X
	 * @param [int] startingY - initial starting position, coordinate Y
	 * @param [int] startingSpeed - initial moving speed
	 * @param [Color] color - initial color of a creature 
	 */
	
	public Enemy(int x, int y, Color color){
		super(x,y,color);
	}
	
	/**
	 * Sets the instance of Map, needed to be able to interact with map array of Tiles
	 * @param mapInstance[Map]
	 */
	public void setMapInstance(GameMap mapInstance){
		this.mapInstance = mapInstance;
		map = mapInstance.getMapCopy();
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
		g.fillRect(this.getPosX(), this.getPosY(), 30, 30);
		super.paintComponent(g);
	}
	

	/**
	 * Thread, used for enemy to find the shortest path, called each [searchTime] milliseconds
	 */
	private Thread pathFindingThread = new Thread(new Runnable(){
		public void run(){
			while(pathFindingThread != null){
				thisInstance.findPathAStar();
				try{
					Thread.sleep(searchTime);
				}
				catch(Exception e){
					
				}
				
			}
			
		}
	});

	/**
	 * Thread, used for enemy to follow the recently found shortest path, called each [moveTime] milliseconds
	 */
	private Thread pathFollowingThread = new Thread(new Runnable(){
		public void run(){
			while(pathFollowingThread != null){

				
				try{				
					if(thisInstance.shortestPath.size() == 0){
						thisInstance.findPathAStar();
						
					}
					thisInstance.followPath();
					gameInstance.checkForCollision();
					gameInstance.stateChanged();
					Thread.sleep(moveTime);
				}
				catch(Exception e){
					
				}
				
			}
			
		}
	});
	
	/**
	 * Starts path following and finding threads
	 */
	public void startLookingForPath(){
		
		pathFindingThread.start();
		pathFollowingThread.start();
	}
	
	/**
	 * Ends path following and finding threads
	 */
	public void stopLookingForPath(){
		
		pathFindingThread = null;
		pathFollowingThread = null;
	}

	/**
	 * Sets targeted GameObject
	 * @param [GameObject] target - set the target of this enemy
	 */
	public void setTarget(GameObject mainTarget){
		this.mainTarget = mainTarget;
	}
	/**
	 * Shortest path algorithm A*
	 * @return best path stored in the private field shortestPath
	 */
	
	private void findPathAStar(){
		PriorityQueue<Tile> closed = new PriorityQueue<Tile>(new fScoreComparator());
		PriorityQueue<Tile> open = new PriorityQueue<Tile>(new fScoreComparator());
		
		int currentX = this.getPosX()/Parameters.TILE_SIZE;
		int currentY = this.getPosY()/Parameters.TILE_SIZE;
		Tile start = map.get(currentY).get(currentX);
		
		open.add(start);
		start.gScore = 0;
		start.fScore = heuristicEstimate(start);
		Tile current;
		while(open.size() > 0){
			current = open.remove();
			currentX = current.getX()/Parameters.TILE_SIZE;
			currentY = current.getY()/Parameters.TILE_SIZE;
			if(current.getX() == mainTarget.getPosX() && current.getY() == mainTarget.getPosY()){
				shortestPath = reconstructPath(current);
				mapInstance.resetScore();
				return;
			}
			else{
				closed.add(current);

				double tentativeGScore;
				ArrayList<Tile> neighbors = this.getNeighbors(current);
				for(int i = 0; i<neighbors.size(); i++){
					Tile neighbor = neighbors.get(i);
					if(!closed.contains(neighbor) && !neighbor.isWall()){
						 
						 tentativeGScore = current.gScore + distanceBetween(current, neighbor);
						 if(!open.contains(neighbor)){
							 open.add(neighbor);
							 neighbor.cameFrom = current;
							 neighbor.gScore = tentativeGScore;
							 neighbor.fScore = neighbor.gScore + heuristicEstimate(neighbor);
						 }
						 else if(! (tentativeGScore >= neighbor.gScore)){
							 neighbor.cameFrom = current;
							 neighbor.gScore = tentativeGScore;
							 neighbor.fScore = neighbor.gScore + heuristicEstimate(neighbor);
						 }
					}
				}
				
			}
			
		}
		return;
		
		
	}
	
	/**
	 * Gets neighbors of specific Tile, used in pathfinding
	 * @param t[Tile] - Tile which neighbors are being looked for
	 * @return ArrayList<Tile> - all neighbors of the Tile
	 */
    private ArrayList<Tile> getNeighbors(Tile t){
    	ArrayList<Tile> neighbors = new ArrayList<Tile>();
    	int tileX = t.getX()/Parameters.TILE_SIZE;
    	int tileY = t.getY()/Parameters.TILE_SIZE;
    	if(tileY - 1 >= 0){
    		neighbors.add(map.get(tileY-1).get(tileX));
    	}
    	if(tileY + 1 < map.size()){
    		neighbors.add(map.get(tileY+1).get(tileX));
    	}
    	if(tileX- 1 >= 0){
    		neighbors.add(map.get(tileY).get(tileX-1));
    	}
    	if(tileX + 1 < map.get(0).size()){
    		neighbors.add(map.get(tileY).get(tileX+1));
    	}
    	return neighbors;
    }
	
	
	/**
	 * Approximation of the distance to the target
	 * @param [Tile] from - Tile to search the distance from
	 * @return [float] distance to the Target from the current Tile 
	 */
	private double heuristicEstimate(Tile from){
		return (Math.sqrt((double)((from.getX()-mainTarget.getPosX()) * (from.getX()-mainTarget.getPosX()) + (from.getY()-mainTarget.getPosY())* (from.getY()-mainTarget.getPosY()))));
	}
	/**
	 * find the distance between two tiles
	 * @param [Tile] a - Tile from
	 * @param [Tile] b - Tile to
	 * @return [float] Distance between two tiles
	 */
	private double distanceBetween(Tile from, Tile to){
		return (Math.sqrt((double)((from.getX()-to.getX()) * (from.getX()-to.getX()) + (from.getY()-to.getY())* (from.getY()-to.getY()))));
	}
	/**
	 * Reconstruct the shortest path from the beginning
	 * @param [Tile] a - Tile to start reconstructing path from
	 * @return [ArrayList<Tile>] - shortest path
	 */
	private ArrayList<Tile> reconstructPath(Tile startingTile){
		ArrayList<Tile> path = new ArrayList<Tile>();
		while(startingTile.cameFrom != null && startingTile != startingTile.cameFrom.cameFrom){
			path.add(startingTile);
			startingTile = startingTile.cameFrom;
		}
		return path;
	}
	/**
	 * Follow directions using Tiles in shortestPath
	 */
	public void followPath(){
		Tile nextTile = shortestPath.remove(shortestPath.size() -1);
		if(thisInstance.getPosX() == nextTile.getX()){
			// moving in Y direction
			if(nextTile.getY() - thisInstance.getPosY() > 0){
				this.setFacing(2); // moving down
			}
			else{
				this.setFacing(4); // moving up
			}
		}
		else{
			//moving in X direction
			if(nextTile.getX() - thisInstance.getPosX() > 0){
				this.setFacing(1); // moving right
			}
			else{
				this.setFacing(3); // moving left
			}
		}
		thisInstance.setPosX(nextTile.getX());
		thisInstance.setPosY(nextTile.getY());
		thisInstance.repaint();
	}
}

/**
 * Comparator of two Tiles
 */
class fScoreComparator implements Comparator<Tile>
{
    @Override
    public int compare(Tile x, Tile y)
    {
       return (int)(x.fScore - y.fScore);
    }
}
package lt.vu.mif.pacman.gameObjects;


import javax.swing.*;

import lt.vu.mif.pacman.PacmanGame;
import lt.vu.mif.pacman.GUI.Drawable;
import lt.vu.mif.pacman.IO.Parameters;
import java.awt.*;
import java.io.*;
import java.util.*;


public class GameMap extends JLabel implements Drawable{
	public static final long serialVersionUID = 1L;
	private ArrayList< ArrayList<Tile> > mainArray = new ArrayList< ArrayList<Tile> >();
	private PacmanGame gameInstance;
	
	public GameMap(){
		
	}
	/**
	 *  Map parser, creates Player or Enemy if found
	 *  @param [String] name of a file to load the map from 
	 */
	public void parseMap(String fileName){
		String line = null;
		try{
    		FileReader inputFile = new FileReader(fileName);
    		BufferedReader input = new BufferedReader(inputFile);
    		int arrayIndex = 0;
    		int charCount = 0;
            while((line = input.readLine()) != null) {
            	mainArray.add(new ArrayList<Tile>());
            	for(int i = 0; i<line.length(); i++){
            		if(line.charAt(i) == Parameters.WALL_CHAR){
            			mainArray.get(arrayIndex).add(new Tile(i*Parameters.TILE_SIZE,arrayIndex*Parameters.TILE_SIZE,false,true));
            		}
            		else if(line.charAt(i) == Parameters.FOOD_CHAR){
            			mainArray.get(arrayIndex).add(new Tile(i*Parameters.TILE_SIZE,arrayIndex*Parameters.TILE_SIZE,true,false));
            			PacmanGame.maxScore++;
            		}
            		else if(line.charAt(i) == Parameters.PATH_CHAR){
            			mainArray.get(arrayIndex).add(new Tile(i*Parameters.TILE_SIZE,arrayIndex*Parameters.TILE_SIZE,false,false));
            			PacmanGame.maxScore++;
            			PacmanGame.score++;
            		}
            		else if(line.charAt(i) == Parameters.PLAYER_CHAR){
            			gameInstance.createPlayer(charCount*Parameters.TILE_SIZE, arrayIndex*Parameters.TILE_SIZE);
            			mainArray.get(arrayIndex).add(new Tile(i*Parameters.TILE_SIZE,arrayIndex*Parameters.TILE_SIZE,false,false));
            		}
            		else if(line.charAt(i) == Parameters.ENEMY_CHAR){
            			gameInstance.createEnemy(charCount*Parameters.TILE_SIZE,arrayIndex*Parameters.TILE_SIZE);
            			mainArray.get(arrayIndex).add(new Tile(i*Parameters.TILE_SIZE,arrayIndex*Parameters.TILE_SIZE,true,false));
            			PacmanGame.maxScore++;
            		}
            		charCount++;
            	}
            	arrayIndex++;
            	charCount = 0;
            	
            }
            input.close();
    	}
		catch(FileNotFoundException ex) {
	          System.out.println("Unable to open map file '" + fileName + "'");
	          System.exit(0);
		}
        catch(IOException ex) {
            System.out.println("Error reading map file '" + fileName + "'");
            System.exit(0);
        }		
		this.setVerticalAlignment(JLabel.TOP);
		this.setHorizontalAlignment(JLabel.LEFT);
		this.setOpaque(false);
		this.setSize(new Dimension(Parameters.WIDTH_IN_PIXELS,Parameters.HEIGHT_IN_PIXELS));
    }  

	public void setGameInstance(PacmanGame gameInstance){
		this.gameInstance = gameInstance;
	}
	
	/**
	 * Paint map
	 * @param g - Graphics
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        for(int i = 0; i < mainArray.size(); i++){
        	for(int j = 0; j< mainArray.get(i).size(); j++){
            	if(mainArray.get(i).get(j).isWall()){
            		g.setColor(Color.black);
            	}
            	else{
                    g.setColor(Color.white);
            	}
        		int tileX = mainArray.get(i).get(j).getX();
        		int tileY = mainArray.get(i).get(j).getY();
            	g.fillRect(tileX, tileY, Parameters.TILE_SIZE, Parameters.TILE_SIZE);
            	if(mainArray.get(i).get(j).hasFood()){
            		g.setColor(Color.pink);
            		g.fillOval(tileX+(Parameters.TILE_SIZE-5)/2,tileY+(Parameters.TILE_SIZE-5)/2, 5, 5);
            	}
        	}
        }
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.drawString(Integer.toString(PacmanGame.score)+"/"+Integer.toString(PacmanGame.maxScore), 20, 20);
    }
    public void repaint(){
    	super.repaint();
    }
    
    /**
     * Draw text on the screen
     * @param g[Graphics] - Graphics
     * @param text[String] - text to be drawn
     */
    public void drawText(Graphics g, String text){
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.drawString(text, 300, 300);
    }
    
    
    public ArrayList<ArrayList<Tile>> getMapCopy(){
    	ArrayList<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();
    	for(int i = 0; i < mainArray.size(); i++){
    		map.add(new ArrayList<Tile>());
    	}
    	
    	for(int i = 0; i < map.size(); i++){
    		for(int j = 0; j < mainArray.get(0).size(); j++){
    			map.get(i).add(new Tile(mainArray.get(i).get(j)));
    		}
    	}
    	return map;
    }
    
    public ArrayList<ArrayList<Tile>> getMap(){
    	return mainArray;
    }

    /**
     * Resets f-and-g-scores of all the tiles
     */
    public void resetScore(){
    	for(int i = 0; i < mainArray.size(); i++){
        	for(int j = 0; j< mainArray.get(i).size(); j++){
            	mainArray.get(i).get(j).gScore = Double.MAX_VALUE;
            	mainArray.get(i).get(j).fScore = Double.MAX_VALUE;
            	mainArray.get(i).get(j).cameFrom = null;
        	}
        }
    }
}


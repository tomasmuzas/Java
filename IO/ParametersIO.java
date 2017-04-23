package lt.vu.mif.pacman.IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import lt.vu.mif.pacman.gameObjects.Enemy;
import lt.vu.mif.pacman.gameObjects.GameMap;
import lt.vu.mif.pacman.gameObjects.Player;
import lt.vu.mif.pacman.gameObjects.Tile;

public class ParametersIO {
	
	/**
	 * Reads parameters from the certain file
	 * @param fileName[String] - path of the parameter file
	 */
	public static void readSettings(String fileName){
		String line = null;
		try{
    		FileReader inputFile = new FileReader(fileName);
    		BufferedReader input = new BufferedReader(inputFile);
    		Parameters.INPUT_PATH = fileName;
    		while((line = input.readLine()) != null){
    			line = line.replaceAll("[^0-9#\\:\\\\.\\w=_]", ""); // replace everything that does not match allowed chars
    			String[] parameter = line.split("=");
    			switch(parameter[0]){
    				case "tile_size":
    					Parameters.TILE_SIZE = Integer.parseInt(parameter[1]);
    					break;
    				case "width":
    					Parameters.WIDTH_IN_PIXELS = Integer.parseInt(parameter[1]);
    					break;
    				case "height":
    					Parameters.HEIGHT_IN_PIXELS = Integer.parseInt(parameter[1]);
    					break;
    				case "player_char":
    					Parameters.PLAYER_CHAR = parameter[1].charAt(0);
    					break;
    				case "enemy_char":
    					Parameters.ENEMY_CHAR = parameter[1].charAt(0);
    					break;
    				case "wall_char":
    					Parameters.WALL_CHAR = parameter[1].charAt(0);
    					break;
    				case "path_char":
    					Parameters.PATH_CHAR = parameter[1].charAt(0);
    					break;
    				case "food_char":
    					Parameters.FOOD_CHAR = parameter[1].charAt(0);
    					break;	
    				case "output_file":
    					Parameters.OUTPUT_PATH = parameter[1];
    					break;

    				case "input_file":
    					Parameters.INPUT_PATH = parameter[1];
    					break;
    				default:
    					break;
    			}
    		}
            input.close();
    	}
		catch(FileNotFoundException ex) {
          System.out.println("Unable to open config file '" + fileName + "'");
          System.exit(0);
		}
        catch(IOException ex) {
            System.out.println("Error reading config file '" + fileName + "'");
            System.exit(0);
        }		
	}
	
	/**
	 * Writes data to the default location
	 * @param output[String] - text to be written to the file
	 */
	public static void writeLevel(GameMap map, Player mainPlayer, ArrayList<Enemy> enemies){
		String output = "";
		ArrayList<ArrayList<Tile>> mapArray = map.getMap();
		for(int i = 0; i < mapArray.size();i++){
			for(int j = 0; j < mapArray.get(i).size();j++){
				boolean enemyFound = false;
				for(int k = 0; k < enemies.size(); k++){
					if(enemies.get(k).getPosY()/Parameters.TILE_SIZE == i && enemies.get(k).getPosX()/Parameters.TILE_SIZE == j){
						enemyFound = true;
						break;
					}
				}
				if(enemyFound){
					output+=Parameters.ENEMY_CHAR;
				}
				else if(mainPlayer.getPosY()/Parameters.TILE_SIZE == i && mainPlayer.getPosX()/Parameters.TILE_SIZE == j){
					output+=Parameters.PLAYER_CHAR;
				}
				else if(mapArray.get(i).get(j).hasFood()){
					output+=Parameters.FOOD_CHAR;
				}
				else if(!mapArray.get(i).get(j).hasFood() && !mapArray.get(i).get(j).isWall()){
					output+=Parameters.PATH_CHAR;
				}
				else if(!mapArray.get(i).get(j).hasFood() && mapArray.get(i).get(j).isWall()){
					output+=Parameters.WALL_CHAR;
				}
			}
			output+=System.lineSeparator();
		}
		if(Parameters.OUTPUT_PATH != null){
			try{
			    PrintWriter outputFile = new PrintWriter(Parameters.OUTPUT_PATH, "UTF-8");
			    outputFile.print(output);
			    outputFile.close();
			}
			catch(FileNotFoundException ex) {
		          System.out.println("Unable to open output file '" + Parameters.OUTPUT_PATH + "'");
		          System.exit(0);
				}
	        catch(IOException ex) {
	            System.out.println("Error reading output file '" + Parameters.OUTPUT_PATH + "'");
	            System.exit(0);
	        }
			
		}
	}
}

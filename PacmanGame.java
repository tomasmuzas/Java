package lt.vu.mif.pacman;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lt.vu.mif.pacman.GUI.GUI;
import lt.vu.mif.pacman.IO.FormatException;
import lt.vu.mif.pacman.IO.Parameters;
import lt.vu.mif.pacman.IO.ParametersIO;
import lt.vu.mif.pacman.gameObjects.Enemy;
import lt.vu.mif.pacman.gameObjects.GameMap;
import lt.vu.mif.pacman.gameObjects.GameObjectCreator;
import lt.vu.mif.pacman.gameObjects.Player;

public class PacmanGame {
	public static int score = 0; // current score of the game
	public static int maxScore = 0; // maximum score of the game
	private static ArrayList<Enemy> allEnemies = new ArrayList<Enemy>(); // list of all enemies
	private JFrame window = new JFrame("Pac-man game"); // main Frame of the game, or "window" that will be displayed
	private Player mainPlayer; // Player
	private GameMap map; // Game Map
	private GUI renderer; // graphics' engine
	private int enemyCount = 0;
	

	/**
	 * Prepares all the needed game objects, starts their threads, if existent, and launches the game
	 */
	public void start(String parametersPath){
		
		try{
			ParametersIO.readSettings(parametersPath); // read the settings from the file
		}
		catch(FormatException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		renderer = GUI.getInstance();
		window.add(renderer);
		
		map = new GameMap();
		map.setGameInstance(this);
		try{
			map.parseMap(Parameters.INPUT_PATH);
		}
		catch(FormatException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
		
		

		
		renderer.add(map);
		renderer.setLayer(map, 0);
		renderer.add(mainPlayer);
		mainPlayer.setActive(true);
		renderer.setLayer(mainPlayer, 1);
		for(int i = 0; i < allEnemies.size(); i++){
			renderer.add(allEnemies.get(i));
			renderer.setLayer(allEnemies.get(i), 2);
		}
		
		

	    
		for(int i = 0; i < allEnemies.size(); i++){

			allEnemies.get(i).setMapInstance(map);
			allEnemies.get(i).setGameInstance(this);
			allEnemies.get(i).setTarget(mainPlayer);
			allEnemies.get(i).setActive(true);
		}
		
		
	    window.addKeyListener(mainPlayer);
		window.pack();
	    window.setVisible(true);
	}
	/**
	 * Ends the game
	 * @param status[String] the end status of a game, either "win" or "lose"
	 */
	public void end(String status){
		
		for(int i = 0; i < allEnemies.size(); i++){
			allEnemies.get(i).stopLookingForPath();
		}
		window.removeKeyListener(mainPlayer);
		switch(status){
			case "win":
				GUI.drawText("You've won!");
				break;
			case "lose":
				GUI.drawText("You've lost! :(");
				break;
				
		}
		
	}
	/**
	 * Creates player with specific starting position, used when reading the map
	 * @param posX[int] - initial position of Player, X coordinate
	 * @param posY[int] - initial position of Player, Y coordinate
	 */
	public void createPlayer(int posX, int posY){
		
		mainPlayer =  (Player) GameObjectCreator.instantiateGameObject("Player", posX,posY, Color.yellow);
		mainPlayer.setMapInstance(map);
		mainPlayer.setGameInstance(this);
	}
	/**
	 * Creates Enemy with specific starting position, used when reading the map
	 * @param posX[int] - initial position of Enemy, X coordinate
	 * @param posY[int] - initial position of Enemy, Y coordinate
	 */
	public void createEnemy(int posX, int posY){
		Enemy enemy  = (Enemy) GameObjectCreator.instantiateGameObject("Enemy",posX, posY, Parameters.enemyColors[enemyCount++]);
		allEnemies.add(enemy);
	}
	
	/**
	 * checks if the game is won, if it is, ends the game
	 */
	public void checkIfGameWon(){
		if(score == maxScore){
			this.end("win");
		}
	}
	
	/**
	 * Checks whether the Player collides with enemies, called both from Player and Enemies
	 */
	public void checkForCollision(){
		for(int i = 0; i < allEnemies.size(); i++){
			if(mainPlayer.collisionWith(allEnemies.get(i))){
				this.end("lose");
				break;
			}
		}
	}
	

	
	/**
	 * tells the writer to "save" the map
	 */
	public void stateChanged(){
		ParametersIO.writeLevel(map,mainPlayer,allEnemies);
	}
	
}

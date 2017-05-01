package lt.vu.mif.pacman.gameObjects;

import java.awt.Color;

public class GameObjectCreator {
	
	/**
	 * Creates new instance of an object of selected type
	 * @param type[String] - either Player or Enemy
	 * @param posX - starting position X
	 * @param posY - starting position Y
	 * @param color - starting color
	 * @return instance of a selected object
	 */
	public static GameObject instantiateGameObject(String type, int posX, int posY, Color color){
		switch(type){
			case "Player":
				return new Player(posX,posY,color);
			case "Enemy":
				return new Enemy(posX,posY,color);
			default:
				return null;
		}
	}
}

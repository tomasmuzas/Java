/**
 * @author Tomas Muzas, PS 2 grupe
 * Zaidimas, paremtas arcade zaidimu Pac-Man 
 * (supaprastinta versija)
 * zaidimas valdomas rodykliu mygtukais arba WASD mygtukais
 * Tikslas: surinkti visa maista esanti ant zemelapio
 * Zaidimo pabaiga: priesas tave pagauna
 */

package lt.vu.mif.pacman;

public class Main {
	public static int score = 0;
	
	
	public static void main(String args[]){
		
		
		PacmanGame game = new PacmanGame();
		game.start(args[0]);
		
	}
}

package map;

import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.WhiteWalkers;
import character.Wildings;

public class Box {
	//enum content {faction, marcheur blanc, obstacle, libre}
	private Character character;
//	private Obstacle obstacle; Obstacle remplaçé par le booléen non ? :) En plus pas besoin d'avoir une classe obstacle, si ?
	private boolean isObstacle;
	private int x;
	private int y;
	
	public Character getCharacter() {
		return character;
	}



	public void setCharacter(Character character) {
		this.character = character;
	}



	public boolean isObstacle() {
		return isObstacle;
	}



	public void setObstacle(boolean isObstacle) {
		this.isObstacle = isObstacle;
	}



	public int getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public void displayBox() {
		if(!isObstacle) {
			System.out.print("X");
		}
		else {
			if(character.getClass().equals(Lannister.class)) {
				System.out.print("L");
			}
			else if (character.getClass().equals(Stark.class)) {
				  System.out.print("S");
			}
			else if (character.getClass().equals(Targaryen.class)) {
				System.out.print("T");
			}
			else if(character.getClass().equals(Wildings.class)) {
				System.out.print("W");
			}
			else if(character.getClass().equals(WhiteWalkers.class)) {
				System.out.print("M");
			}
		}		//Afficher le contenu d'une case

		//abreviation de 4 lettres (Star, Targ, Sauv, Lani, March ou Blan, obst, S st, S ta, S sa, S la) ou espace vide de 4 lettres
	}
}

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



	public String displayBox() {
		if(!isObstacle) {
			return "X";
		}
		else if(character.getClass().equals(Lannister.class)) {
				return "L";
			}
		else if (character.getClass().equals(Stark.class)) {
				  return "S";
			}
		else if (character.getClass().equals(Targaryen.class)) {
				return "T";
			}
		else if(character.getClass().equals(Wildings.class)) {
				return "W";
			}
		else if(character.getClass().equals(WhiteWalkers.class)) {
				return "M";
			}
		else {
			return " ";
		}

			//Afficher le contenu d'une case

		//abreviation de 4 lettres (Star, Targ, Sauv, Lani, March ou Blan, obst, S st, S ta, S sa, S la) ou espace vide de 4 lettres
	}
	
	public boolean isEmpty() {
		return character==null && !isObstacle;
	}
}

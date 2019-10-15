package character;

import java.util.ArrayList;

import gameplay.DiceResult;
import gameplay.Safezone;
import map.Box;
import map.Direction;

public abstract class Human extends Character {
	//protected int level = 0; //augmentation des stats  
	
	protected int xp = 0;
	protected Safezone safezone;
	protected String name;
	
	static protected int MAX_POWER;
	static protected int MAX_STAMINA;
	
	public static int getMaxStamina() {
		return MAX_STAMINA;
	}

	public static int getMaxPower() {
		return MAX_POWER;
	}

	
	public Human() {
		super();
		setDodge(5);
	}
	
	protected void move(Box currentBox, int maxStep, int maxXMap, int maxYMap) {
		//recuperer mon enum de direction
		ArrayList<Direction>possibleDirections = this.possibleDirections(maxXMap, maxYMap);
						
		//si etre vivant a endurance basse, prend la direction de safezone
		if (this.getStamina() < 0.10*Human.getMaxStamina()) {
			//Safezone direction
		}
		
		//sinon, tire aleatoirement directions
		else {
			int randomIndex = (int) (Math.random() * (possibleDirections.size() - 1));
			Direction takenDirection = possibleDirections.get(randomIndex);
		}
		
		DiceResult result = this.rollDice();
		
		//lancer des des pour influer sur portee
		
		//tant que case libre dans direction et port�e ok, "avancer" (actualiser futures coordonn�es)
			//Si en dehors de sa SafeZone --> perte de PE
			//Sinon --> r�cup�re 3PE par case 
			//Si PV pas au max, r�cup�re 1PV
		
		//d�placer le personnage et changer ses coordonn�es + actualiser ancienne et nouvelle case
		
		//scanner les alentours et interagir si autre perso trouv�
		
		//Fin de tour XP++
	}
	
	protected abstract void superAttack(Character character);
}

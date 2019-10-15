package character;

import java.util.ArrayList;

import gameplay.DiceResult;
import map.Box;
import map.Direction;

public abstract class Character {
	static final private int MAX_POWER = 100;
	static protected int CRITIC_SUCCESS_LEVEL;
	static protected int FAILURE_LEVEL;
	static protected int MAX_STEP_NUMBER;

	public static int getCriticSuccessLevel() {
		return CRITIC_SUCCESS_LEVEL;
	}

	public static int getFailureLevel() {
		return FAILURE_LEVEL;
	}

	public static int getMaxStepNumber() {
		return MAX_STEP_NUMBER;
	}

	protected int life = 100;
	protected int power;
	protected int stamina;
	protected int dodge;
	protected int maxStepNumber;

	private Box currentBox;
	private Direction lastDirection;
	
	public Character() {
	}
	
	protected ArrayList<Direction> possibleDirections(int maxXMap, int maxYMap) {
		//renvoie l'arraylist de directions possibles pour un personnage
		
		ArrayList<Direction> directions = new ArrayList<Direction>();
		
		if(currentBox.getX() == 0 && currentBox.getY()==0) {
			directions.add(Direction.East);
			directions.add(Direction.North);
			directions.add(Direction.NorthEast);
		}
		else if (currentBox.getX() == maxXMap && currentBox.getY() ==0) {
			directions.add(Direction.NorthWest);
			directions.add(Direction.West);
			directions.add(Direction.North);
		}
		else if(currentBox.getY() == maxYMap && currentBox.getX() == 0) {
			directions.add(Direction.South);
			directions.add(Direction.SouthEast);
			directions.add(Direction.East);
		}
		else if(currentBox.getY() == maxYMap && currentBox.getX() == maxXMap) {
			directions.add(Direction.South);
			directions.add(Direction.SouthWest);
			directions.add(Direction.West);
		}
		else if(currentBox.getX()==maxXMap) {
			directions.add(Direction.South);
			directions.add(Direction.SouthWest);
			directions.add(Direction.West);
			directions.add(Direction.North);
			directions.add(Direction.NorthWest);			
		}
		else if (currentBox.getX() == 0) {
			directions.add(Direction.South);
			directions.add(Direction.SouthEast);
			directions.add(Direction.East);
			directions.add(Direction.North);
			directions.add(Direction.NorthEast);			
		}
		else if(currentBox.getY()==0) {
			directions.add(Direction.West);
			directions.add(Direction.NorthWest);	
			directions.add(Direction.East);
			directions.add(Direction.North);
			directions.add(Direction.NorthEast);
		}
		else if(currentBox.getY() == maxYMap) {
			directions.add(Direction.South);
			directions.add(Direction.SouthWest);
			directions.add(Direction.West);
			directions.add(Direction.SouthEast);
			directions.add(Direction.East);
		}
		else {
			directions.add(Direction.South);
			directions.add(Direction.SouthWest);
			directions.add(Direction.West);
			directions.add(Direction.SouthEast);
			directions.add(Direction.East);
			directions.add(Direction.North);
			directions.add(Direction.NorthEast);	
			directions.add(Direction.NorthWest);
		}
		
		return directions;
	}
	
	protected abstract void move(Box currentBox, int maxStep, int maxXMap, int maxYMap);
	
	
	protected abstract void meet(Character character);
	//Si m�me famille, ajout de PV �quitablement en fonction du nombre de cases qu'il reste � parcourir pour la personne en mouvement
	
	//Si m�me faction, ajout de XP distribu� �quitablement en fct nb cases
	
	//Si faction diff�rente et en dehors de safezone, combat tour par tour => method attaquer (d�pend des familles)
	
	//SI plus de PE : 
	
		//Si != factions : individu sans PE meurt
		
		//Si mm faction : 1 seul PE d'aide transf�r�

		//Si mm famille : moiti� des PE partag�
	
	
	protected int determineStepNumbers() {
		switch(this.rollDice()) {
			
			case CRITIC_SUCCESS:
				return(Human.getMaxStepNumber());
		
			case SUCCESS: 
				return (int) (0.5*Human.getMaxStepNumber());
				
			default: 
				return (int) (0.25*Human.getMaxStepNumber());
		}
		
		
	}
	
	
	protected abstract void attack(Character character);
	// Puissance d'attaque et chance d'esquive de l'adversaire � prendre en compte
	
	//Lancer de d�s (seuils de % en fonction de la famille du personnage lancant l'attaque)
	
		//Si SUCCES CRITIC : attaque sp�ciale 
	
		//Si SUCCES : adversaire perd des points de vie en fonction de attaquant (adversaire n'essaye pas d'esquiver (dodge) ?)
	
			//Si PV adversaire tombe � 0 et attaquant != marcheur blanc, adversaire meurt et est supprime de la map et XP adversaire transf�r� � attquant 
	
			//Si PV adversaire tombe � 0 et attaquant == marcheur blanc, adversaire meurt et arrivee d'un nouveau marcheur blanc
	
			//Sinon, adversaire attack et ainsi de suite 
	
		//Si ECHEC : 
	
			//L'attaquant a rat� son attaque, rien ne se passe

		//echec critique (?) perd pv ou exp�erience?
	
	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = (this.stamina < 0) ? 0 : stamina;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = (this.power < 0) ? 0 : power;
	}

	public int getDodge() {
		return dodge;
	}

	public void setDodge(int dodge) {
		this.dodge = (this.dodge < 0) ? 0 : dodge;
	}

	
	/** 
     * Rolls a die
     * @return diceResult   result of the throw
     */ 
    public  DiceResult rollDice() {
    	//Plus on est proche de 0, plus la chance d'avoir un echec est grand
    	
        //roll a dice
        int diceValue = (int) (Math.random() * 100) + 1; // gives value between 1 and 100
        
        //determine the score
        DiceResult result;
        
        //more frequent
        if (diceValue < Character.getCriticSuccessLevel() && diceValue > Character.getFailureLevel()) result = DiceResult.SUCCESS;
        
        //less frequent
        else if (diceValue < Character.getFailureLevel()) result = DiceResult.FAILURE;
        else result = DiceResult.CRITIC_SUCCESS;
       
        return result;
    }

	
	 
}

package character;

import java.util.ArrayList;

import map.Box;
import map.Direction;

public abstract class Character {
	static final private int MAX_POWER = 100;
	private int life = 100;

	private int power;
	private int stamina;
	private int dodge;
	private int maxStepNumber;

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
	
	protected  abstract void move(Box currentBox, int maxStep);
		//recuperer mon enum de direction
		
		//getPossibleBoxes()
		
		//rï¿½cupï¿½rer le max de pas et endurance du personnage 
			
		//si ï¿½tre vivant est endurance basse, prend la direction de safezone 
		//sinon, tire alï¿½atoirement directions

		//lancer des dï¿½s pour influer sur portï¿½e
		
		//tant que case libre dans direction et portï¿½e ok, "avancer" (actualiser futures coordonnï¿½es)
			//Si en dehors de sa SafeZone --> perte de PE
			//Sinon --> rï¿½cupï¿½re 3PE par case 
			//Si PV pas au max, rï¿½cupï¿½re 1PV
		
		//dï¿½placer le personnage et changer ses coordonnï¿½es + actualiser ancienne et nouvelle case
		
		//scanner les alentours et interagir si autre perso trouvï¿½
		
		//Fin de tour XP++
		
	
	
	protected abstract void meet(Character character);
	//Si mï¿½me famille, ajout de PV ï¿½quitablement en fonction du nombre de cases qu'il reste ï¿½ parcourir pour la personne en mouvement
	
	//Si mï¿½me faction, ajout de XP distribuï¿½ ï¿½quitablement en fct nb cases
	
	//Si faction diffï¿½rente et en dehors de safezone, combat tour par tour => method attaquer (dï¿½pend des familles)
	
	//SI plus de PE : 
	
		//Si != factions : individu sans PE meurt
		
		//Si mm faction : 1 seul PE d'aide transfï¿½rï¿½

		//Si mm famille : moitiï¿½ des PE partagï¿½
	
	
	protected abstract void attack(Character character);
	// Puissance d'attaque et chance d'esquive de l'adversaire ï¿½ prendre en compte
	
	//Lancer de dï¿½s (seuils de % en fonction de la famille du personnage lancant l'attaque)
	
		//Si SUCCES CRITIC : attaque spï¿½ciale 
	
		//Si SUCCES : adversaire perd des points de vie en fonction de attaquant (adversaire n'essaye pas d'esquiver (dodge) ?)
	
			//Si PV adversaire tombe ï¿½ 0 et attaquant != marcheur blanc, adversaire meurt et est supprime de la map et XP adversaire transfï¿½rï¿½ ï¿½ attquant 
	
			//Si PV adversaire tombe ï¿½ 0 et attaquant == marcheur blanc, adversaire meurt et arrivee d'un nouveau marcheur blanc
	
			//Sinon, adversaire attack et ainsi de suite 
	
		//Si ECHEC : 
	
			//L'attaquant a ratï¿½ son attaque, rien ne se passe

		//echec critique (?) perd pv ou expïerience?
	
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

	public int getMaxStepNumber() {
		return maxStepNumber;
	}

	public void setMaxStepNumber(int maxStepNumber) {
		this.maxStepNumber = maxStepNumber;
	}
	
	 
}

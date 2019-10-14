package character;

import java.io.IOException;
import java.util.ArrayList;

import map.Box;
import map.Direction;

public abstract class Character {
	static final private int MAX_POWER = 100;
	protected int life = 100;

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
		
		//r�cup�rer le max de pas et endurance du personnage 
			
		//si �tre vivant est endurance basse, prend la direction de safezone 
		//sinon, tire al�atoirement directions

		//lancer des d�s pour influer sur port�e
		
		//tant que case libre dans direction et port�e ok, "avancer" (actualiser futures coordonn�es)
			//Si en dehors de sa SafeZone --> perte de PE
			//Sinon --> r�cup�re 3PE par case 
			//Si PV pas au max, r�cup�re 1PV
		
		//d�placer le personnage et changer ses coordonn�es + actualiser ancienne et nouvelle case
		
		//scanner les alentours et interagir si autre perso trouv�
		
		//Fin de tour XP++
		
	
	
	protected abstract void meet(WhiteWalkers character, int remainingBoxes);
	//Si m�me famille, ajout de PV �quitablement en fonction du nombre de cases qu'il reste � parcourir pour la personne en mouvement
	
	//Si m�me faction, ajout de XP distribu� �quitablement en fct nb cases
	
	//Si faction diff�rente et en dehors de safezone, combat tour par tour => method attaquer (d�pend des familles)
	
	//SI plus de PE : 
	
		//Si != factions : individu sans PE meurt
		
		//Si mm faction : 1 seul PE d'aide transf�r�

		//Si mm famille : moiti� des PE partag�
	
	
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

	public int getMaxStepNumber() {
		return maxStepNumber;
	}

	public void setMaxStepNumber(int maxStepNumber) {
		this.maxStepNumber = maxStepNumber;
	}

	protected abstract void meet(Human character, int remainingBoxes) throws IOException;
	
	 
}

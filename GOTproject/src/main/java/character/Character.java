package character;

import map.Box;
import map.Direction;

public abstract class Character {
	static final private int MAX_POWER = 100;

	private int power = 0;
	private int stamina;
	private Box currentBox;
	private int life = 100;
	private int dodge;
	//port�e/rayon d'action/nbre de cases par tour

	private Direction lastDirection;
	
	protected void possibleDirections() {
		//renvoie l'arraylist de directions possibles pour un personnage
	}
	
	protected  void move(Box currentBox) {
		//r�cup�rer mon �num de direction
		
		//je retire ceux o� je suis bloqu� � une ou deux cases de distances = possibleBoxes
		
		//r�cup�rer la port�e max et endurance du personnage 
		
		//si �tre vivant est endurance basse, prend la direction de safezone 
		
		//lancer des d�s pour influer sur le d�placement
		
		//tant que case libre dans direction et port�e ok, "avancer" (actualiser futures coordonn�es)
		
		//d�placer le personnage et changer ses coordonn�es + actualiser ancienne et nouvelle case
		
		//scanner les alentours et interagir si autre perso trouv�
	}
	
	
	
	protected abstract void meet(Character character);

	
	
}

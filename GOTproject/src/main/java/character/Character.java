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
	//portée/rayon d'action/nbre de cases par tour

	private Direction lastDirection;
	
	protected void possibleDirections() {
		//renvoie l'arraylist de directions possibles pour un personnage
	}
	
	protected  void move(Box currentBox) {
		//récupérer mon énum de direction
		
		//je retire ceux où je suis bloqué à une ou deux cases de distances = possibleBoxes
		
		//récupérer la portée max et endurance du personnage 
		
		//si être vivant est endurance basse, prend la direction de safezone 
		
		//lancer des dès pour influer sur le déplacement
		
		//tant que case libre dans direction et portée ok, "avancer" (actualiser futures coordonnées)
		
		//déplacer le personnage et changer ses coordonnées + actualiser ancienne et nouvelle case
		
		//scanner les alentours et interagir si autre perso trouvé
	}
	
	
	
	protected abstract void meet(Character character);

	
	
}

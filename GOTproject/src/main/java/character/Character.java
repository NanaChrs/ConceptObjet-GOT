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
	
	protected  abstract void move(Box currentBox, int maxStep);
		//récupérer mon énum de direction
		
		//getPossibleBoxes()
		
		//récupérer le max de pas et endurance du personnage 
			
		//si être vivant est endurance basse, prend la direction de safezone 
		//sinon, tire aléatoirement directions

		//lancer des dès pour influer sur portée
		
		//tant que case libre dans direction et portée ok, "avancer" (actualiser futures coordonnées)
			//Si en dehors de sa SafeZone --> perte de PE
			//Sinon --> récupère 3PE par case 
			//Si PV pas au max, récupère 1PV
		
		//déplacer le personnage et changer ses coordonnées + actualiser ancienne et nouvelle case
		
		//scanner les alentours et interagir si autre perso trouvé
		
		//Fin de tour XP++
		
	
	
	protected abstract void meet(Character character);
	//Si même famille, ajout de PV équitablement en fonction du nombre de cases qu'il reste à parcourir pour la personne en mouvement
	
	//Si même faction, ajout de XP distribué équitablement en fct nb cases
	
	//Si faction différente et en dehors de safezone, combat tour par tour => method attaquer (dépend des familles)
	
	//SI plus de PE : 
	
		//Si != factions : individu sans PE meurt
		
		//Si mm faction : 1 seul PE d'aide transféré

		//Si mm famille : moitié des PE partagé
	
	
	protected abstract void attack(Character character);
	// Puissance d'attaque et chance d'esquive de l'adversaire à prendre en compte
	
	
	//Lancer de dés (seuils de % en fonction de la famille du personnage lancant l'attaque)
	
		//Si SUCCES CRITIC : attaque spéciale 
	
		//Si SUCCES : adversaire perd des points de vie en fonction de attaquant (adversaire n'essaye pas d'esquiver (dodge) ?)
	
			//Si PV adversaire tombe à 0 et attaquant != marcheur blanc, adversaire meurt et est supprimé de la map et XP adversaire transféré à attquant 
	
			//Si PV adversaire tombe à 0 et attaquant == marcheur blanc, adversaire meurt et arrivé d'un nouveau marcheur blanc
	
			//Sinon, adversaire attack et ainsi de suite 
	
		//Si ECHEC : 
	
			//L'attaquant a raté son attaque, rien ne se passe

		//echec critique (?) perd pv ou expérience?
	 
}

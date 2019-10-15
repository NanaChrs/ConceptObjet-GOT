package app;

import java.io.IOException;
import java.util.ArrayList;

import character.Human;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.Wildings;
import gameplay.FileManager;
import map.Box;
import map.Westeros;

public class Application {
	//map
	//factions

	public static void main(String[] args) throws IOException {
		
		Human cersei = new Lannister("Cersei");
		Human jaime = new Lannister("Jaime");
		Human arya = new Stark("Arya");
		Human sansa = new Stark("Sansa");
		Human jon = new Targaryen("Jon");
		Human dany = new Targaryen("Daenerys");
		Human gilly = new Wildings("Gilly");
		Human tor = new Wildings("Tormund");
		
		System.out.println(tor.getClass().getSuperclass().getSuperclass().getSuperclass());
		
		ArrayList<Character> humans = new ArrayList<Character>();
		
//		humans.add(cersei);
//		humans.add(jaime);
//		humans.add(arya);
//		humans.add(sansa);
//		humans.add(jon);
//		humans.add(dany);
//		humans.add(gilly);
//		humans.add(tor);
		
		Westeros map = new Westeros();
		
		Box[][] boxes = new Box[Westeros.WIDTH][Westeros.HEIGHT];
		
		for(int i =0; i < Westeros.WIDTH; i++) {
			for (int j=0; j < Westeros.HEIGHT; j++) {
				if(Math.random()>0.9 && humans.size()>0) {
					boxes[i][j].setCharacter(humans.get(0));
				}
				else if (Math.random()>0.8) {
					
				}
			}
		}
		map.mapDisplay();
		// TODO Auto-generated method stub

		//run
	}
	
	/*
	run {
		faire :
			optionnel : demande a� user niveau de detail (pour un tour : n'afficher que carte, afficher aussi les actions ou afficher aussi les stats)

			génère les 4 familles

			optionnel : demande a user taille de map
			affiche map

			demande a user nombre de tour max

			faire :
				affiche compteur de tour

				met factions (y compris marcheurs blancs) dans tableau
				tant que tableau non vide :
					fait jouer familles tirée aléatoirement du tableau
					retire faction du tableau

				si nb tour % x == 0 :
					ajoute marcheur blanc à faction correspondante
				
				affiche map

				vérifie conditions de fin (famille/faction gagnante, paix ou toutes les factions mortes)
			tant que !fin et que nb tour++ < tour max

			demander à user nouvelle partie ou quitter
		tant que !quitter
	}*/
}

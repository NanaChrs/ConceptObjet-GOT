package app;

import java.io.IOException;

import gameplay.FileManager;

public class Application {
	//map
	//factions

	public static void main(String[] args) throws IOException {
		FileManager.writeToLogFile("Un combat a commencé");
		FileManager.writeToLogFile("Daenerys est morte");

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

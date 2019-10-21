package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import character.Character;
import character.Human;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.WhiteWalker;
import character.Wilding;
import character.Wildings;
import gameplay.FileManager;
import gameplay.UserInterface;
import map.Westeros;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
       
        //Génère les 4 familles
        ArrayList<Character> population = new ArrayList();
        population.add(new Lannister("Cersei"));
        population.add(new Lannister("Jaime"));
        population.add(new Stark("Arya"));
        population.add(new Stark("Sansa"));
        population.add(new Targaryen("Jon"));
        population.add(new Targaryen("Daenerys"));
        population.add(new Wilding("Gilly"));
        population.add(new Wilding("Tormund"));

        Westeros westeros = Westeros.getInstance(population);
        	
        //Affiche map
        westeros.mapDisplay();
        TimeUnit.SECONDS.sleep(5);
        
        UserInterface.cleanUI();
        westeros.mapDisplay();
        
        final int NUMBER_OF_TURNS = 100;
        
        for (int turn = 0; turn < NUMBER_OF_TURNS; turn++) {
        	
        	Collections.shuffle(population);
        	
        	if (turn%10 == 0) {
        		population.add(new WhiteWalker());
        	}

        	for (Character character : population) {
        		
        		//Affiche compteur de tour
            	System.out.println("Tour n° " + turn);
            	
            	character.move();

            	if (!(character.isAlive())) {
            		population.remove(character);
            	}
            	
            	
        		
        	}
        	

        

        	/*tant que tableau non vide :
                    fait jouer familles tirée aléatoirement du tableau
                    retire faction du tableau

            si nb tour % x == 0 :
                    ajoute marcheur blanc à faction correspondante

            affiche map

            vérifie conditions de fin (famille/faction gagnante, paix ou toutes les factions mortes)
    		tant que !fin et que nb tour++ < tour max

    		demander à user nouvelle partie ou quitter
			tant que !quitter */
        }                       
    }
}
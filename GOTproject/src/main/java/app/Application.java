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
import gameplay.FileManager;
import gameplay.UserInterface;
import map.Box;
import map.Westeros;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        Westeros westeros = Westeros.getInstance();
        westeros.mapDisplay();
        TimeUnit.SECONDS.sleep(2);
        
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
        
        westeros.addCharacters(population);
        
        int turn = 0;
        final int NUMBER_OF_TURNS = 15;
        while (turn < NUMBER_OF_TURNS && !population.isEmpty()) {//remplacer par fonction cond victoires
            UserInterface.cleanUI();
            System.out.println("Tour n° " + turn++);
            westeros.mapDisplay();
            TimeUnit.SECONDS.sleep(2);
            
            Collections.shuffle(population);

            if (turn%10 == 0) {
                population = new ArrayList();
                population.add(new WhiteWalker());
                westeros.addCharacters(population);
            }

            for (Character character : population) {
                //character.move();
                
                if (!character.isAlive()) {
                    population.remove(character);
                }
            }
            
            
/*
            vérifie conditions de fin (famille/faction gagnante, paix ou toutes les factions mortes)
    		tant que !fin et que nb tour++ < tour max

    		demander à user nouvelle partie ou quitter
			tant que !quitter */
                
        }
    }
}
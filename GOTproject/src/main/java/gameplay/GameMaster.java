package gameplay;

import character.Character;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.WhiteWalker;
import character.Wilding;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import map.GameBoard;

//gère les instances de jeu
public class GameMaster {
    private static GameMaster uniqueInstance;
    private ArrayList<Character> population;
    private GameBoard westeros;
    
    private final int LATENCY = 1;
    private final int MAX_TURN = 10;
    private final int WHITEWALKER_FREQUENCY = 10;
    private int turn = 0;
    
    
    public static GameMaster getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GameMaster();
        }
        return uniqueInstance;
    }
    
    private GameMaster() {
        UserInterface.cleanUI();
        westeros = GameBoard.getInstance();
        System.out.println("Génération du plateau");
        westeros.displayMap();
    }

    public void initialize(/*int populationByFaction, int maxTurn, int latency, int WWfrequency...*/) throws InterruptedException {
        //lance une nouvelle partie - vider les logs 
        
        //Génère les familles avec génération de nom aléatoire
        population = new ArrayList();
        population.add(new Lannister(FactionLannister.Cersei.toString()));
        population.add(new Lannister(FactionLannister.Jaime.toString()));
        population.add(new Stark(FactionStark.Arya.toString()));
        population.add(new Stark(FactionStark.Sansa.toString()));
        population.add(new Targaryen(FactionTargaryen.John.toString()));
        population.add(new Targaryen(FactionTargaryen.Daenerys.toString()));
        population.add(new Wilding(FactionWildings.Gilly.toString()));
        population.add(new Wilding(FactionWildings.Tormund.toString()));
        
        westeros.addCharacters(population);
        
        TimeUnit.SECONDS.sleep(LATENCY);
        UserInterface.cleanUI();
        System.out.println("Positionnement des personnages");
        westeros.displayMap();
    }
    
    public void run() throws InterruptedException, IOException {
        //exécution de la simulation tour par tour
        turn = 0;
        while (turn < MAX_TURN && !isFinished()) {
            Collections.shuffle(population);

            ArrayList<Character> populationAlive = new ArrayList<>(population);
            
            for (Character character : population) {
            	if (!character.isAlive()) {
            		populationAlive.remove(character);
            		westeros.removeBody(character);
            	}
            	else {
            		character.move();
            	}
            	if (!character.isAlive()) {
            		populationAlive.remove(character);
            		westeros.removeBody(character);
            	}
            	/*
                if (character.isAlive()) { 
                	character.move();
                	if (character.isAlive()) { 
                		populationAlive.add(character);
                	}
                }
                if (!character.isAlive()) {
                	westeros.removeBody(character);
                }
                */
            }
            Collections.copy(population, populationAlive);
            
            
            //doit être vu (affiché) au moins une fois avant de bouger et interagir avec les gens
            if (turn%WHITEWALKER_FREQUENCY == 0) {
                Character white = new WhiteWalker();
                westeros.addCharacter(white);
                population.add(white);
            }
            
            TimeUnit.SECONDS.sleep(LATENCY);
            UserInterface.cleanUI();
            System.out.println("Fin du tour n°" + ++turn);
            westeros.displayMap();
        }
    }
    
    private boolean isFinished() {
        //vérifie conditions de fin (famille/faction gagnante, paix ou toutes les factions mortes)
        return population.isEmpty();
    }
    
    public void displayEnd() {
        //affiche fin correcte
        System.out.println("Fin de la démo en "+turn+" tours - merci d'avoir joué");
    }
}

package gameplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import factions.WildingsName;
import factions.TargaryenName;
import factions.LannisterName;
import factions.StarkName;
import character.Character;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.WhiteWalker;
import character.Wilding;
import factions.Faction;
import map.GameBoard;


public class GameMaster {
    private static GameMaster uniqueInstance;
    private ArrayList<Character> population;
    private final GameBoard westeros;

    private final static int MAX_TURN = 15;
    private final static int WHITEWALKER_FREQUENCY = 4;
    private final static int POP_BY_FACTION = 5;
    private static int turn;
    
    
    public static GameMaster getInstance() throws InterruptedException {
        if (uniqueInstance == null) {
            uniqueInstance = new GameMaster();
        }
        return uniqueInstance;
    }
    
    private GameMaster() throws InterruptedException {
        UserInterface.generateSwipe();
        UserInterface.cleanConsole();
        FileManager.createLogFile();
        westeros = GameBoard.getInstance();
        UserInterface.displayConsole("Génération du plateau", westeros, 1);
    }
    
    public static int getTurn() {
        return turn;
    }

    protected void addFaction(Faction faction) {
        //load enum content
        ArrayList<String> names = new ArrayList<>();
        switch(faction) {
            case Lannister :
                for (LannisterName name : LannisterName.values()) names.add(name.toString());
                break;
            case Stark :
                for (StarkName name : StarkName.values()) names.add(name.toString());
                break;
            case Targaryen :
                for (TargaryenName name : TargaryenName.values()) names.add(name.toString());
                break;
            case Wilding :
                for (WildingsName name : WildingsName.values()) names.add(name.toString());
                break;
        }
        
        //normalize
        while (names.size() > POP_BY_FACTION) names.remove((int)(Math.random() * names.size()));
        
        //generate factions
        switch(faction) {
            case Lannister :
                for (String perso : names) population.add(new Lannister(perso));
                break;
            case Stark :
                for (String perso : names) population.add(new Stark(perso));
                break;
            case Targaryen :
                for (String perso : names) population.add(new Targaryen(perso));
                break;
            case Wilding :
                for (String perso : names) population.add(new Wilding(perso));
                break;
        }
    }
    
    public void initialize(/*int popByFaction, int maxTurn, int latency, int WWfrequency...*/) throws InterruptedException, IOException {
        //lance une nouvelle partie
    	FileManager.cleanLogFile();
        FileManager.writeToLogFile("[GAME] New simulation");
        turn = 0;
        
        //Génère les familles avec génération de noms aléatoires
        population = new ArrayList<>();
        addFaction(Faction.Lannister);
        addFaction(Faction.Stark);
        addFaction(Faction.Targaryen);
        addFaction(Faction.Wilding);
        westeros.addCharacters(population);
        
        UserInterface.displayConsole("Positionnement des personnages", westeros, 1);
    }
    
    public void run() throws InterruptedException, IOException {
        //exécution de la simulation tour par tour
        turn = 0;
        while (turn++ < MAX_TURN && !isFinished()) {
            UserInterface.displayConsole("Nouveau tour", westeros, 1);
            FileManager.writeToLogFile("\n[GAME] TURN N°" + turn + " BEGIN");
            
            Collections.shuffle(population);

            //1 seule opération si vivant (pas add + remove)
            ArrayList<Character> populationAlive = new ArrayList<>();
            for (Character character : population) {
                if (character.isAlive()) {//n'a pas été tué pendant un combat
                    character.move();
                    
                    if (character.isAlive()) {//n'est pas mort dans son combat
                        populationAlive.add(character);
                    }
                }
            }
            Collections.copy(population, populationAlive);
            
            //doit être vu (affiché) au moins une fois avant de bouger et interagir avec les gens
            if (turn%WHITEWALKER_FREQUENCY == 0) {
                Character white = new WhiteWalker();
                westeros.addCharacter(white);
                population.add(white);
                
                FileManager.writeToLogFile("\n[GAME] New WhiteWalker");
                UserInterface.displayConsole("Un white walker arrive !", westeros, 2);
            }
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

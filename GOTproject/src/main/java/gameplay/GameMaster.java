package gameplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import factions.WildingsName;
import factions.TargaryenName;
import factions.LannisterName;
import factions.StarkName;
import character.Character;
import character.Human;
import character.Lannister;
import character.Northerner;
import character.Southerner;
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
    private final static int WHITEWALKER_COMING = 2;
    private final static int WHITEWALKER_FREQUENCY = 5;
    private final static int POP_BY_FACTION = 4;
    private static int turn;
    private static String endReason;
    
    
    public static GameMaster getInstance() throws InterruptedException {
        if (uniqueInstance == null) {
            uniqueInstance = new GameMaster();
        }
        return uniqueInstance;
    }
    
    private GameMaster() throws InterruptedException {
        FileManager.createLogFile();
        westeros = GameBoard.getInstance();
        UserInterface.displayConsole("Génération du plateau", westeros, 2);
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
        for (Faction faction : Faction.values()) {
            addFaction(faction);
        }
        westeros.addCharacters(population);
        
        UserInterface.displayConsole("Positionnement des personnages", westeros, 2);
    }
    
    public void run() throws InterruptedException, IOException {
        //exécution de la simulation tour par tour
        turn = 0;
        boolean end = false;
        while (++turn < MAX_TURN && !end) {
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
            population = populationAlive;
            
            //doit être vu (affiché) au moins une fois avant de bouger et interagir avec les gens
            if ((turn - WHITEWALKER_COMING >= 0 && (turn - WHITEWALKER_COMING) % WHITEWALKER_FREQUENCY == 0) ||//decalage de cycle
                    turn == WHITEWALKER_COMING) {//suffit d'un vrai : plus fréquent en premier
                Character white = new WhiteWalker();
                westeros.addCharacter(white);
                population.add(white);
                
                FileManager.writeToLogFile("\n[GAME] New WhiteWalker");
                UserInterface.displayConsole("Un white walker arrive !", westeros, 3);
            }
            end = this.isFinished();
        }
    }
    
    private boolean isFinished() {
        //vérifie conditions de fin (famille/faction gagnante, paix ou toutes les factions mortes)
    	Map<String, Integer> dic = new HashMap<>();
    	dic.put("Human", 0);
    	dic.put("Southerner", 0);
    	dic.put("Northerner", 0);
    	dic.put("WhiteWalker", 0);
    	
    	for (Character character : population) {
    		if(character.isAlive()) {
	    		if(character instanceof Human) {
	    			dic.put("Human", dic.get("Human")+1);
	    			if(character instanceof Southerner) {
	    				dic.put("Southerner", dic.get("Southerner")+1);
	    				
	    			}
	    			if(character instanceof Northerner) {
	    				dic.put("Northerner", dic.get("Northerner")+1);		
	    		}
	    		else {
	    			dic.put("WhiteWalker", dic.get("WhiteWalker")+1);
	    		}
    		}
		}
    	}
    
    	if(dic.get("Human") == 0) {
    		endReason = "Plus aucun humains sur le terrain. Les marcheurs blancs ont gagné !";
    		return true;
    	}
    	if(dic.get("Southerner") == population.size()) {
    		endReason = "Il ne reste plus que des gens du sud sur le plateau. La guerre est finie : les sudistes ont gagné !";
    		return true;
    	}
    	if(dic.get("Northerner") == population.size()) {
    		endReason = "Il ne reste plus que des gens du nord sur le plateau. La guerre est finie : les nordistes ont gagné !";
    		return true;
    	}
    	return false;
    		
    }
    
    public void displayEnd() {
        //affiche fin correcte
    	if(endReason != null) {
    	System.out.println(endReason);}
        System.out.println("Fin de la démo en "+turn+" tours - merci d'avoir joué");
    }
    
}

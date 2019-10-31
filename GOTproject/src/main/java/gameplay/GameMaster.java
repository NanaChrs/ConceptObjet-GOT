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
    private GameBoard westeros;
    private static int turn;
    private static String endReason;
    private static int displayLevel;//0 : rien, 1 à 4 : comme frequency
    
    
    public static GameMaster getInstance(int mapSize, int safezoneSize) throws InterruptedException {
        if (uniqueInstance == null) {
            uniqueInstance = new GameMaster(mapSize, safezoneSize);
        }
        return uniqueInstance;
    }
    
    private GameMaster(int mapSize, int safezoneSize) throws InterruptedException {
        FileManager.createLogFile();       
        westeros = GameBoard.getInstance(mapSize, safezoneSize);
        UserInterface.displayConsole("Génération du plateau", westeros, 2);
    }
    
    public static int getTurn() {
        return turn;
    }

    private void addFaction(Faction faction, int popByFaction) {
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
        while (names.size() > popByFaction) names.remove((int)(Math.random() * names.size()));
        
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
    
    private void initialize(int popByFaction) throws InterruptedException, IOException {
        turn = 0;
        
        //lance une nouvelle partie
    	FileManager.cleanLogFile();
        FileManager.writeToLogFile("[GAME] New simulation");
        
        //Génère les familles avec génération de noms aléatoires
        population = new ArrayList<>();
        for (Faction faction : Faction.values()) {
            addFaction(faction, popByFaction);
        }
        westeros.addCharacters(population);
        
        UserInterface.displayConsole("Positionnement des personnages", westeros, 2);
    }
    
    private boolean isFinished() throws IOException {
        //vérifie conditions de fin (famille/faction gagnante, paix ou toutes les factions mortes)
    	Map<String, Integer> dic = new HashMap<>();
    	dic.put("Human", 0);
    	dic.put("Lannister", 0);
    	dic.put("Targaryen", 0);
    	dic.put("Stark", 0);
    	dic.put("Wildings", 0);
    	dic.put("Southerner", 0);
    	dic.put("Northerner", 0);
    	dic.put("WhiteWalker", 0);
    	
    	for (Character character : population) {
            if(character.isAlive()) {
                if(character instanceof Human) {
                    dic.put("Human", dic.get("Human")+1);
                    if(character instanceof Southerner) {
                        dic.put("Southerner", dic.get("Southerner")+1);
                        if (character instanceof Lannister) {
                            dic.put("Lannister", dic.get("Lannister")+1);
                        }
                        else {
                            dic.put("Targaryen", dic.get("Targaryen")+1);
                        }
                    }
                    else if(character instanceof Northerner) {
                        dic.put("Northerner", dic.get("Northerner")+1);	
                        if (character instanceof Stark) {
                            dic.put("Stark", dic.get("Stark")+1);
                        }
                        else {
                            dic.put("Wildings", dic.get("Wildings")+1);
                        }	
                    }
                }
                else {
                    dic.put("WhiteWalker", dic.get("WhiteWalker")+1);
                }
            }
    	}
    
    	if(dic.get("Human") == 0) {
            endReason = "Plus aucun humains sur le terrain. Les marcheurs blancs ont gagné !";
            
            FileManager.writeToLogFile("\n[GAME] Victory of WhiteWalker");
            return true;
    	}
    	if(dic.get("Southerner") == population.size()) {
            endReason = "Il ne reste plus que des gens du sud sur le plateau.\nLa guerre est finie : les sudistes ont gagné et les ";
            
            if (dic.get("Lannister") > dic.get("Targaryen")) {
                endReason += "Lannister sont sur le trône !";
                FileManager.writeToLogFile("\n[GAME] Victory of Lannister");
            }
            else if (dic.get("Lannister") < dic.get("Targaryen")) {
                endReason += "Targaryen sont sur le trône !";
                FileManager.writeToLogFile("\n[GAME] Victory of Targaryen");
            }
            else {
                endReason += "deux familles se disputent le trône !";
                FileManager.writeToLogFile("\n[GAME] Victory of South");
            }
            return true;
    	}
    	if(dic.get("Northerner") == population.size()) {
            endReason = "Il ne reste plus que des gens du nord sur le plateau.\nLa guerre est finie : les nordistes ont gagné et les ";
            if (dic.get("Stark") > dic.get("Wildings")) {
                endReason += "Stark sont sur le trône !";
                FileManager.writeToLogFile("\n[GAME] Victory of Stark");
            }
            else if (dic.get("Stark") < dic.get("Wildings")) {
                endReason += "Sauvageons sont sur le trône !";
                FileManager.writeToLogFile("\n[GAME] Victory of Wildings");
            }
            else {
                endReason += "deux familles se disputent le trône !";
                FileManager.writeToLogFile("\n[GAME] Victory of North");
            }
            return true;
    	}
    	return false;
    		
    }

    public void runSimulation(int displayLevel, int maxTurn, int popByFaction, int firstWW, int wwFrequency) throws InterruptedException, IOException {
        GameMaster.displayLevel = displayLevel;
        
        //prépare jeu
        this.initialize(popByFaction);
        
        //exécution de la simulation tour par tour
        do {
            FileManager.writeToLogFile("\n[GAME] TURN N°" + ++turn + " BEGIN");
            UserInterface.displayConsole("Nouveau tour", westeros, 1);
            
            //fait jouer chaque personnage dans un ordre aléatoire s'il est vivant
            ArrayList<Character> populationAlive = new ArrayList<>();
            Collections.shuffle(population);
            for (Character character : population) {
                if (character.isAlive()) {//n'a pas été tué pendant un combat
                    character.move();
                    
                    if (character.isAlive()) {//n'est pas mort dans son combat
                        populationAlive.add(character);
                    }
                }
            }
            population = populationAlive;
            
            //affiche l'arrivée du white walker
            if (turn == firstWW ||//premier arrivé
                (turn - firstWW >= 0 && (turn - firstWW) % wwFrequency == 0)) {//suivants (cyclique)
                population.add(new WhiteWalker());
                westeros.addCharacter(population.get(population.size()-1));
                
                FileManager.writeToLogFile("\n[GAME] New WhiteWalker");
                UserInterface.displayConsole("Un marcheur blanc arrive !", westeros, 3);
            }
        } while (turn < maxTurn && !this.isFinished());
        
        //fin du jeu
        if (endReason == null) {
            endReason = "Et la guerre continua indéfiniment...";
            FileManager.writeToLogFile("\n[GAME] No victory");
        }
        UserInterface.displayConsole(endReason + "\nFin de la simulation",this.westeros,1);
    }
    
}

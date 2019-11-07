package gameplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
        westeros = GameBoard.getInstance(); //composition
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
    
    private void initialize(String mode, int mapSize, int safezoneSize, int popByFaction) throws InterruptedException, IOException {
        //lance une nouvelle partie
        turn = 0;
        endReason = null;
    	FileManager.cleanLogFile();
        FileManager.writeToLogFile("GAME","New simulation : "+ mode);
        
        //crée plateau
        westeros.initialize(mapSize, safezoneSize);
        UserInterface.displayConsole("Génération du plateau", westeros, 2);
        
        //Génère les familles avec noms aléatoires
        population = new ArrayList<>();
        for (Faction faction : Faction.values()) {
            addFaction(faction, popByFaction);
        }
        westeros.addCharacters(population);
        UserInterface.displayConsole("Positionnement des personnages", westeros, 2);
    }
    
    private boolean isFinished(boolean lastTurn) throws IOException {
        int Lannister = Statistics.LannisterAlive(), Targaryen = Statistics.TargaryenAlive();
        int LannisterKill = Statistics.LannisterKill(), TargaryenKill = Statistics.TargaryenKill();
        int Stark = Statistics.StarkAlive(), Wildings = Statistics.WildingsAlive();
        int StarkKill = Statistics.StarkKill(), WildingsKill = Statistics.WildingsKill();
        int Southerner = Lannister + Targaryen, Northerner = Stark + Wildings;
        int Walkers = Statistics.WWAlive();
        
        if(Northerner + Southerner == 0) {
            endReason = "Plus aucun humains sur le terrain. Les marcheurs blancs ont gagné !";

            FileManager.writeToLogFile(1,"GAME","Victory of WhiteWalker");
            return true;
        }
        if((Lannister > 0 || Targaryen > 0) && Northerner + Walkers == 0) {
            endReason = "Il ne reste plus que les familles du sud sur le plateau.\nLa guerre est finie : ";

            if (Lannister > Targaryen || (Lannister == Targaryen && LannisterKill > TargaryenKill)) {
                endReason += "les Lannister sont sur le trône !";
                FileManager.writeToLogFile(1,"GAME","Victory of Lannister");
            }
            else if (Lannister < Targaryen || (Lannister == Targaryen && LannisterKill < TargaryenKill)) {
                endReason += "Les Targaryen sont sur le trône !";
                FileManager.writeToLogFile(1,"GAME","Victory of Targaryen");
            }
            else {
                endReason += "Les deux familles font la paix (égalité)";
                FileManager.writeToLogFile(1,"GAME","Victory of South");
            }
            return true;
        }
        if((Stark > 0 || Wildings > 0) && Southerner + Walkers == 0) {
            endReason = "Il ne reste plus que les familles du nord sur le plateau.\nLa guerre est finie : ";
            if (Stark > Wildings || (Stark == Wildings && StarkKill > WildingsKill)) {
                endReason += "Les Stark sont sur le trône !";
                FileManager.writeToLogFile(1,"GAME","Victory of Stark");
            }
            else if (Stark < Wildings || (Stark == Wildings && StarkKill < WildingsKill)) {
                endReason += "Les Sauvageons sont sur le trône !";
                FileManager.writeToLogFile(1,"GAME","Victory of Wildings");
            }
            else {
                endReason += "Les deux familles font la paix (égalité)";
                FileManager.writeToLogFile(1,"GAME","Victory of North");
            }
            return true;
        }
 
    	if (lastTurn) {	
            endReason = "Et la guerre continua jusque la probable domination des ";
            String house;
            
            if (Statistics.WWAlive() > (Southerner + Northerner)/2) {
                endReason += "Marcheurs Blancs";
                house = "WhiteWalkers";
            }
            else if (Southerner > Northerner) {
                house = Statistics.LannisterKill() > Statistics.TargaryenKill() ? "Lannister" : "Targaryen";
                endReason += "familles du sud, avec les " + house + " à leur tête";
            }
            else {
                house = Statistics.StarkKill() > Statistics.WildingsKill() ? "Stark" : "Wildings";
                endReason += "familles du nord, avec les " + house + " à leur tête";
            }
            FileManager.writeToLogFile(1,"GAME", "Probable victory of "+house);
            
            return true;
        }
    	
        return false;
    }
    
    public void runSimulation(String mode, int maxTurn, int mapSize, int safezoneSize, int popByFaction, int firstWW, int wwFrequency) throws InterruptedException, IOException {
    	//prépare jeu
    	Statistics.initialize(maxTurn);
        this.initialize(mode, mapSize, safezoneSize, popByFaction);
        
        //affiche attributs statiques des classes character
        Character.displayStatics();
        WhiteWalker.displayStatics();
        Human.displayStatics();
        Southerner.displayStatics();
        Northerner.displayStatics();
        Lannister.displayStatics();
        Targaryen.displayStatics();
        Stark.displayStatics();
        Wilding.displayStatics();
        UserInterface.blockingMessage("Attributs statiques des classes", "lancer la simulation en "+maxTurn+" tours du mode "+mode);
        
        //exécution de la simulation tour par tour
        do {
            FileManager.writeToLogFile(2,"GAME","TURN N°" + ++turn + " BEGIN");
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
                if (!westeros.addCharacter(population.get(population.size()-1))) {
                    population.remove(population.size()-1);//plus de place
                }
                else {
                    FileManager.writeToLogFile(1,"GAME","New WhiteWalker");
                    UserInterface.displayConsole("Un marcheur blanc arrive !", westeros, 3);
                }
            }
        } while (!this.isFinished(turn >= maxTurn));
        
        //fin du jeu
        UserInterface.blockingMessage(endReason, "continuer");
        Statistics.displayStats();

        UserInterface.blockingMessage("Fin de la simulation : retrouvez les détails de ce qui s'est passé dans logs.txt avant de lancer une autre simulation!", "revenir au menu");
    }
    
}

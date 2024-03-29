package map;

import character.Character;
import character.Human;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import factions.Faction;
import gameplay.Statistics;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard {
    private static GameBoard uniqueInstance;
    protected static int SIZE;//carré uniquement, plus simple
    protected ArrayList<SafeZone> towns;
    private Box[][] map;

    private static final double PROBA_OBSTACLE = 5; //en pourcentage

    private GameBoard() {
    }
    
    public static GameBoard getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GameBoard();
        }
        return uniqueInstance;
    }
    
    public final void initialize(int mapSize, int safezoneSize) {
        //déclare support
        GameBoard.SIZE = mapSize;
        map = new Box[GameBoard.SIZE][GameBoard.SIZE];
        
        //génère support
        Weather weather = Weather.Warm;
        for (int y=SIZE-1; y >= 0; --y) {
        	
            if (y+1 == SIZE * 2 / 3) {
            	weather = Weather.Tempered;
            }
            
            else if (y+1 == SIZE / 3) {
            	weather = Weather.Cool;
            }
            
            for(int x = 0; x < SIZE; ++x) {
                map[x][y] = new Box(x, y, weather, Math.random() > (1-(PROBA_OBSTACLE/100)));
            }
        }
        
        //ajoute zones spécifiques (affectation des safezones aléatoirement)
        towns = new ArrayList<>();
        ArrayList<Direction> corners = new ArrayList<>(Arrays.asList(Direction.NorthEast, Direction.NorthWest,
                                                                     Direction.SouthEast, Direction.SouthWest));
        for (Faction population : Faction.values()) {
            towns.add(new SafeZone(safezoneSize, this, population, corners.get((int) (Math.random() * corners.size()))));
            corners.remove(towns.get(towns.size()-1).getCorner());
        }
    }
    
    public static int getSize() {
        return SIZE;
    }
    
    public Box[][] getMap() {
        return this.map;
    }
    
    public SafeZone getSafeZone(String population) {
        for (SafeZone town : towns) {
            if ((town.getSafeFor().toString()).equals(population)) {
                return town;
            }
        }
        return null;
    }
    
    public ArrayList<SafeZone> getTowns() {
		return towns;
	}

	public boolean addCharacter(Character character) {
        int x, y;
        int nbCases = SIZE*SIZE;
        
        do {//lui trouve une place
            x = (int) (Math.random() * SIZE);
            y = (int) (Math.random() * SIZE);
        } while (!map[x][y].isEmpty() && --nbCases > 0);

        if (!map[x][y].isEmpty()) return false;//plus de place
        
        //ajoute le personnage a la map et lui dit ou il est
        map[x][y].setCharacter(character);
        character.setBox(map[x][y]);//agrégation
        character.setMap(this);//composition
        
        if (character instanceof Human) {
            if (character instanceof Lannister) {
                Statistics.LannisterAdded();
            }
            else if (character instanceof Targaryen) {
                Statistics.TargaryenAdded();
            }
            else if (character instanceof Stark) {
                Statistics.StarkAdded();
            }
            else /*if (this instanceof Wilding)*/ {
                Statistics.WildingsAdded();
            }
        }
        else {
            Statistics.WWAdded();
        }
        return true;
    }
    
    public void addCharacters(ArrayList<Character> population) {
        for (Character character : population) {
            this.addCharacter(character);
        }
    }
    
    public String displayMap() {
        String cool = "X", warm = "O", tempered = "~";
        String display = " ";
        
        //limite haute de la map
        for(int x = 0; x < SIZE; x++) { 
        	display += " " + warm;
        }
        display += "\n";
        
        String sides = warm;
        for (int y = SIZE-1; y >= 0; y-- ) {
            
        	if (y+1 == SIZE * 2 / 3) {
            	sides = tempered;
            }
            
            else if (y+1 == SIZE / 3) {
            	sides = cool;
            }
            
            display += sides + " "; //limites gauches de la map
            
            //contenu de la map
            for(int x = 0; x < SIZE; x++) {
                display += map[x][y].displayBox() + " ";//double la largeur pour equivaloir la hauteur
            }

            display += sides + "\n"; //limites droites de la map
        }
        
        //limite basse de la map
        display += " ";
        for(int x = 0; x < SIZE; x++) {
        	display += " " + cool;
        }
        display += "\n";
        
        return (display);
    }
}

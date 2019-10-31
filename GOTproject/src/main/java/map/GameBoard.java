package map;

import character.Character;
import character.Human;
import factions.Faction;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard {
    private static GameBoard uniqueInstance;
    protected static int SIZE;//carré uniquement, plus simple
    protected ArrayList<SafeZone> towns;
    private final Box[][] map;

    private static final double PROBA_OBSTACLE = 5;

    private GameBoard(int mapSize, int safezoneSize) {
        GameBoard.SIZE = mapSize;
        //initialise map
        map = new Box[GameBoard.SIZE][GameBoard.SIZE];
        
        Weather weather = Weather.Warm;
        for (int y=SIZE-1; y >= 0; --y) {
            if (y+1 == SIZE * 2 / 3) weather = Weather.Tempered;
            else if (y+1 == SIZE / 3) weather = Weather.Cool;
            
            for(int x = 0; x < SIZE; ++x) {
                map[x][y] = new Box(x,y,weather,Math.random() > (1-(PROBA_OBSTACLE/100)));
            }
        }
        
        towns = new ArrayList<>();
        ArrayList<Direction> corners = new ArrayList<>(Arrays.asList(Direction.NorthEast, Direction.NorthWest,
                                                                     Direction.SouthEast, Direction.SouthWest));
        for (Faction population : Faction.values()) {
            towns.add(new SafeZone(safezoneSize, this, population, corners.get((int) (Math.random() * corners.size()))));
            corners.remove(towns.get(towns.size()-1).getCorner());
        }
    }
    
    public static GameBoard getInstance(int mapSize, int safezoneSize) {
        if (uniqueInstance == null) {
            uniqueInstance = new GameBoard(mapSize, safezoneSize);
        }
        return uniqueInstance;
    }
    
    public static int getSize() {
        return SIZE;
    }
    
    public Box[][] getMap() {
        return this.map;
    }
    
    public void addCharacter(Character character) {
        int x,y;
        do {//lui trouve une place
            x = (int) (Math.random() * SIZE);
            y = (int) (Math.random() * SIZE);
        } while (!map[x][y].isEmpty());

        //l'ajoute a la map et lui dit ou il est
        map[x][y].setCharacter(character);
        character.setBox(map[x][y]);//agrégation
        character.setMap(this);//agrégation
        
        if (character instanceof Human) {
            this.getSafeZone(character.getClass().getSimpleName()).addFactionMember();
        }
    }
    
    public void addCharacters(ArrayList<Character> population) {
        for (Character character : population) {
            this.addCharacter(character);
        }
    }
    
    public SafeZone getSafeZone(String population) {
        for (SafeZone town : towns) {
            if ((town.getSafeFor().toString()).equals(population)) {
                return town;
            }
        }
        return null;
    }
    
    public String displayMap() {
        String cool = "X", warm = "O", tempered = "~";
        String result = " ";
        
        for(int x = 0; x < SIZE; x++) result += " " + warm;
        result += "\n";
        
        String sides = warm;
        for (int y = SIZE-1; y >= 0; y-- ) {
            if (y+1 == SIZE * 2 / 3) sides = tempered;
            else if (y+1 == SIZE / 3) sides = cool;
            
            result += sides + " ";
            for(int x = 0; x < SIZE; x++) {
                result += map[x][y].displayBox() + " ";//double la largeur pour equivaloir la hauteur
            }
            //result += (map[0][y].getWeather()==Weather.Cool? cool : map[0][y].getWeather()==Weather.Warm? warm : tempered) + "\n";//ligne de vérif
            result += sides + "\n";
        }
        
        result += " ";
        for(int x = 0; x < SIZE; x++) result += " " + cool;
        result += "\n";
        
        return (result + "\n");
    }
}

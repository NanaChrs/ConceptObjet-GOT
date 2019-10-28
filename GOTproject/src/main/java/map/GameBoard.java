package map;

import character.Character;
import factions.Faction;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard {
    private static GameBoard uniqueInstance;
    protected static final int HEIGHT = 20;
    protected static final int WIDTH = 20;
    protected ArrayList<SafeZone> towns;
    protected Box[][] map;

    private static final double PROBA_OBSTACLE = 5;

    private GameBoard() {
        //initialise map
        map = new Box[GameBoard.WIDTH][GameBoard.HEIGHT];
        
        for(int i = 0; i < WIDTH; ++i) {
            for (int j=0; j < HEIGHT; ++j) {
                map[i][j] = new Box(i,j);
                if (Math.random() > (1-(PROBA_OBSTACLE/100))) {
                    map[i][j].setObstacle();
                }
            }
        }
        
        towns = new ArrayList<>();
        ArrayList<Direction> corners = new ArrayList<>(Arrays.asList(Direction.NorthEast, Direction.NorthWest,
                                                                     Direction.SouthEast, Direction.SouthWest));
        for (Faction population : Faction.values()) {
            towns.add(new SafeZone(this, population, corners.get((int) (Math.random() * corners.size()))));
            corners.remove(towns.get(towns.size()-1).getCorner());
        }
    }

    public static GameBoard getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GameBoard();
        }
        return uniqueInstance;
    }
    
    public void addCharacter(Character character) {
        int x,y;
        do {//lui trouve une place
            x = (int) (Math.random() * WIDTH);
            y = (int) (Math.random() * HEIGHT);
        } while (!map[x][y].isEmpty());

        //l'ajoute a la map et lui dit ou il est
        map[x][y].setCharacter(character);
        character.setBox(map[x][y]);//agrégation
        character.setMap(this);//agrégation
    }
    
    public void addCharacters(ArrayList<Character> population) {
        for (Character character : population) {
            this.addCharacter(character);
        }
    }
    
    public SafeZone getSafeZone(String population) {//buggé?
        switch(population) {
            case "Lannister":
                return towns.get(towns.indexOf(Faction.Lannister));
            case "Stark":
                return towns.get(towns.indexOf(Faction.Stark));
            case "Targaryen":
                return towns.get(towns.indexOf(Faction.Targaryen));
            case "Wilding":
                return towns.get(towns.indexOf(Faction.Wilding));
        }
        return null;
    }
    
    public void removeBody(Character character) {
    	map[character.getBox().getX()][character.getBox().getY()].free();
    }
    
    public static int getHeight() {
        return HEIGHT;
    }
    
    public static int getWidth() {
        return WIDTH;
    }
    
    public Box[][] getMap() {
        return this.map;
    }
    
    public void displayMap() {
        String vert = "-", horiz = "|";
        String result = "";
        
        for(int x = 0; x < WIDTH*2 + 2; x++) result+=vert;
        result += "\n";
        
        for (int y = HEIGHT-1; y >= 0; y-- ) {
            result += horiz;
            for(int x = 0; x < WIDTH; x++) {
                result += map[x][y].displayBox() + " ";
                //result += (!map[x][y].isEmpty())? map[x][y].displayBox() : " ";//remplacer pour avoir les safeZone
                //result += " ";//double la largeur pour equivaloir la hauteur
            }
            result += horiz + "\n";
        }
        
        for(int x = 0; x < WIDTH*2 + 2; x++) result+=vert;
        System.out.println(result);
    }
}

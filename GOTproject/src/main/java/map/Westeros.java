package map;

import character.Character;
import java.util.ArrayList;

public class Westeros {
    private static Westeros uniqueInstance;
    protected static final int HEIGHT = 20;
    protected static final int WIDTH = 20;
    protected Box[][] map;
    ArrayList<Character> population;
    
    //private static final double PROBA_HUMAN = 0.9;
    private static final double PROBA_OBSTACLE = 0.95;

    /*
    public Westeros(ArrayList<Character> population) {
        this.population = population;
        int populationNumber = population.size();
        
        map = new Box[Westeros.WIDTH][Westeros.HEIGHT];
        for(int i = 0; i < WIDTH; ++i) {
            for (int j=0; j < HEIGHT; ++j) {
                map[i][j] = new Box();
                if(populationNumber > 0 && Math.random() > PROBA_HUMAN) {
                    map[i][j].setCharacter(this.population.get(--populationNumber));
                }
                else if (Math.random() > PROBA_OBSTACLE) {
                    map[i][j].setObstacle(true);
                }
            }
        }
    }

    public static Westeros getInstance(ArrayList<Character> population) {
        if (uniqueInstance == null) {
            uniqueInstance = new Westeros(population);
        }
        return uniqueInstance;
    }
*/
    private Westeros() {
        map = new Box[Westeros.WIDTH][Westeros.HEIGHT];
        for(int i = 0; i < WIDTH; ++i) {
            for (int j=0; j < HEIGHT; ++j) {
                map[i][j] = new Box();
                if (Math.random()%1 > PROBA_OBSTACLE) {
                    map[i][j].setObstacle(true);
                }
            }
        }
    }

    public static Westeros getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Westeros();
        }
        return uniqueInstance;
    }
    
    public void addCharacters(ArrayList<Character> population) {
        int x,y;
        
        for (Character character : population) {
            do {
                x = (int) (Math.random() * WIDTH);
                y = (int) (Math.random() * HEIGHT);
            } while (!map[x][y].isEmpty());
            
            map[x][y].setCharacter(character);
//            character.setMap(this);//agrégation
        }
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
    
    public void mapDisplay() {
        String vert = "-", horiz = "|";
        String result = "";
        
        for(int x = 0; x < WIDTH*2 + 2; x++) result+=vert;
        result += "\n";
        
        //haut gauche: (0,0) ; bas droite : (max,max) => inversion des y
        for (int y = HEIGHT-1; y >= 0; y-- ) {
            result += horiz;
            for(int x = 0; x < WIDTH; x++) {
                result += (!map[x][y].isEmpty())? map[x][y].displayBox() : " ";
                result += " ";//double la largeur pour equivaloir la hauteur
            }
            result += horiz + "\n";
        }
        
        for(int x = 0; x < WIDTH*2 + 2; x++) result+=vert;
        System.out.println(result);
    }


}

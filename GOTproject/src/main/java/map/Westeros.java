package map;

import character.Character;
import java.util.ArrayList;

public class Westeros {
    private static Westeros uniqueInstance;
    protected static final int HEIGHT = 20;
    protected static final int WIDTH = 20;
    protected /*static*/ Box[][] boxes = new Box[Westeros.WIDTH][Westeros.HEIGHT];
    ArrayList<Character> population;
    
    private static final double PROBA_HUMAN = 0.9;
    private static final double PROBA_OBSTACLE = 0.95;

    public Westeros(ArrayList<Character> population) {
        this.population = population;
        int populationNumber = population.size();
        
        for(int i = 0; i < WIDTH; ++i) {
            for (int j=0; j < HEIGHT; ++j) {
                boxes[i][j] = new Box();
                if(populationNumber > 0 && Math.random()%1 > PROBA_HUMAN) {
                    boxes[i][j].setCharacter(this.population.get(--populationNumber));
                }
                else if (Math.random()%1 > PROBA_OBSTACLE) {
                    boxes[i][j].setObstacle(true);
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

    public static int getHeight() {
        return HEIGHT;
    }
    
    public static int getWidth() {
        return WIDTH;
    }
    
    public void getMap(Box[][] map) {
        for (int x = 0; x < WIDTH; x++) {
            System.arraycopy(boxes[x], 0, map[x], 0, HEIGHT);
        }
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
                result += (!boxes[x][y].isEmpty())? boxes[x][y].displayBox() : " ";
                result += " ";//double la largeur pour equivaloir la hauteur
            }
            result += horiz + "\n";
        }
        
        for(int x = 0; x < WIDTH*2 + 2; x++) result+=vert;
        System.out.println(result);
    }


}

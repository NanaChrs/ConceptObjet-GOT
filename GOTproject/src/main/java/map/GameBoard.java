package map;

import character.Character;
import java.util.ArrayList;

public class GameBoard {
    private static GameBoard uniqueInstance;
    protected static final int HEIGHT = 20;
    protected static final int WIDTH = 20;
    protected Box[][] map;

    private static final double PROBA_OBSTACLE = 0.95;

    private GameBoard() {
        //initialise map
        map = new Box[GameBoard.WIDTH][GameBoard.HEIGHT];
        for(int i = 0; i < WIDTH; ++i) {
            for (int j=0; j < HEIGHT; ++j) {
                map[i][j] = new Box(i,j);
                if (Math.random()%1 > PROBA_OBSTACLE) {
                    map[i][j].setObstacle(true);
                }
            }
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

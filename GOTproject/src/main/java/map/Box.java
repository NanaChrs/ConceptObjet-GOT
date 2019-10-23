package map;

import character.Character;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.WhiteWalker;
import character.Wilding;

public class Box {
    private Character character;
    private boolean isObstacle;
    private int x;
    private int y;


    public Box() {
    }
    
    public boolean isEmpty() {
        return character==null && !isObstacle;
    }
    
    public Character getCharacter() {
        return character;
    }

    public void setCharacter(character.Character character2) {
        this.character = character2;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String displayBox() {
        if(isObstacle) {
            return "+";
        }
        else if(character.getClass().equals(Lannister.class)) {
            return "L";
        }
        else if (character.getClass().equals(Stark.class)) {
            return "S";
        }
        else if (character.getClass().equals(Targaryen.class)) {
            return "T";
        }
        else if(character.getClass().equals(Wilding.class)) {
            return "W";
        }
        else if(character.getClass().equals(WhiteWalker.class)) {
            return "M";
        }
        else {
            return " ";
        }
    }

}

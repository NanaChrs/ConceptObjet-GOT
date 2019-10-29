package map;

import character.Character;
import character.Human;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.Wilding;
import factions.Faction;

public class Box {
    private Character character;
    private boolean isObstacle;
    private int x;
    private int y;
    private Faction safeFor;
    private boolean limitSafeZone;


    public Box(int x, int y) {
        this.x = x;
        this.y = y;
        isObstacle = false;
        character = null;
        safeFor = null;
        limitSafeZone = false;
    }
    
    public void setLimitSafeZone() {
        limitSafeZone = true;
    }

    public Faction getSafeFor() {
        return safeFor;
    }
    
    public boolean isSafe() {
        return safeFor != null;
    }

    public void setSafeFor(Faction population) {
        this.safeFor = population;
    }
    
    public void free() {
    	character = null;
    }
    
    public boolean isEmpty() {
        return character==null && !isObstacle;
    }
    
    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
    
    public void removeCharacter() {
        this.character = null;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle() {
        this.isObstacle = true;
    }

    public void removeObstacle() {
        this.isObstacle = false;
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

    public char displayBox() {
        //plus au moins probable / lourd en calcul
        if (this.isEmpty() && !limitSafeZone) {
            return ' ';
        }
        if(isObstacle) {
            return '+';
        }
        if (character != null) {
            return (character instanceof Human)? character.getClass().getSimpleName().charAt(0) : 'M';
        }
        return safeFor.toString().charAt(0);
    }

    public char displayUnicodeBox() {
        if (this.isEmpty() && !limitSafeZone) {
            return ' ';
        }
        if(isObstacle) {
            return '+';
        }
        if (character != null) {
            if(character.getClass().equals(Lannister.class)) {
                return 'L';
            }
            if (character.getClass().equals(Stark.class)) {
                return 'S';
            }
            if (character.getClass().equals(Targaryen.class)) {
                return 'T';
            }
            if(character.getClass().equals(Wilding.class)) {
                return 'W';
            }
            return 'M';
        }
        switch (safeFor) {
            case Lannister:
                return 'l';
            case Stark:
                return 's';
            case Targaryen:
                return 't';
            case Wilding:
                return 'w';
            default:
                return '?';
        }
    }
}

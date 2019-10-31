package map;

import character.Character;
import character.Human;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.WhiteWalker;
import character.Wilding;
import factions.Faction;
import static gameplay.GameMaster.getTurn;
import static java.lang.Character.toLowerCase;

public class Box {
    //Attributs - Instance définie par :
    //  sa position
    private final int x;
    private final int y;
    
    //  son climat
    private final Weather weather;
    private final static int NB_TURN_WW_EFFECT = 4;
    private int beginTurn;
    private final static int BAD_WEATHER_DAMAGES = 2;
    
    //  son occupant
    private Character character;
    private boolean isObstacle;
    
    //  son appartenance à une safezone
    private Faction safeFor;
    private boolean limitSafeZone;

    //Constructeur - naissance de l'instance
    public Box(int x, int y, Weather weather, boolean obstacle) {
        this.x = x;
        this.y = y;
        this.weather = weather;
        isObstacle = obstacle;
        beginTurn = -NB_TURN_WW_EFFECT;
    }
    
    //Getters & setters utiles
    //  position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    //  weather
    public boolean isFrozen() {
        return getTurn() < beginTurn + BAD_WEATHER_DAMAGES;
    }
    public Weather getWeather() {
        return isFrozen() ? Weather.Frozen : weather;
    }
    
    public int getWeatherDamages() {
        if (character instanceof WhiteWalker) return 0;//ont leur propre microclimat
        if (isFrozen()) return BAD_WEATHER_DAMAGES * 2;//humain subit microclimat de ww
        if (weather.equals(Weather.Tempered)) return 0;//humain supporte climat
        
        //humain supporte climat extrême de sa capitale
        Direction corner = ((Human)character).safeZoneDirection();
        if (weather.equals(Weather.Cool) && (corner.equals(Direction.SouthEast) || corner.equals(Direction.SouthWest)) ||
            weather.equals(Weather.Warm) && (corner.equals(Direction.NorthEast) || corner.equals(Direction.NorthWest))) {
            return 0;
        }

        return BAD_WEATHER_DAMAGES;//climat extrême contraire à celui de sa capitale implique dégâts
    }
    
    public void setWhiteWalkerWeather() {
        beginTurn = getTurn();
    }
    
    //  character & obstacle
    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
    
    public void removeCharacter() {
        this.character = null;
    }
    
    public void removeObstacle() {
        isObstacle = false;
    }
    
    public boolean isObstacle() {
        return isObstacle;
    }
    
    public boolean isEmpty() {
        return character==null && !isObstacle;
    }
    
    //  safezone
    public void setLimitSafeZone(boolean setSafeZone) {
        limitSafeZone = setSafeZone;
    }
    
    public void setSafeFor(Faction population) {
        this.safeFor = population;
    }

    public Faction getSafeFor() {
        return safeFor;
    }
    
    
    public boolean isSafe() {
        return safeFor != null;
    }

    //Méthodes public - actions que peut réaliser l'instance
    public char displayBox() {
        //plus au moins probable / lourd en calcul / prioritaire
        if (isFrozen() && character == null) {
            return '@';
        }
        if (this.isEmpty() && !limitSafeZone) {
            return ' ';
        }
        if(isObstacle) {
            return '+';
        }
        if (character != null) {
            return (character instanceof Human)? character.getClass().getSimpleName().charAt(0) : 'M';
        }
        return toLowerCase(safeFor.toString().charAt(0));
    }

    public char displayUnicodeBox() {
        if (isFrozen() && character == null) {
            return '@';
        }
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

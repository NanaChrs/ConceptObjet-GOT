package map;

import factions.Faction;
import static gameplay.UserInterface.displayConsole;

public class SafeZone {
    protected int factionSize;
    protected Faction safeFor;
    protected Direction corner;//par rapport au centre
    protected GameBoard westeros;//aggrégation
    
    protected final static int SIZE = GameBoard.getSize()/4;
    protected final static int RECOVERY = 10;//stamina récupérée
    
    public SafeZone(int size, GameBoard map, Faction population, Direction orientation) {
        this.westeros = map;
        this.corner = orientation;
        this.safeFor = population;
        applySafeZone(true);
    }
    
    public void addFactionMember() {
        factionSize++;
    }
    
    public void removeFactionMember() throws InterruptedException {
        if (--factionSize == 0) {
            String name = safeFor.toString();
            safeFor = null;
            applySafeZone(false);//affichage carte
            westeros.towns.remove(this);//enregistrement safezone
            displayConsole("Les " + name + " sont morts : leur zone d'influence disparaît", westeros, 2);
        }
    }
    
    public Direction getCorner() {
        return corner;
    }
    
    public static int getSize() {
        return SIZE;
    }
    
    public static int getRecovery() {
        return RECOVERY;
    }
    
    public Faction getSafeFor() {
        return safeFor;
    }
    
    private void applySafeZone(boolean setSafeZone) {
        int xMax = GameBoard.getSize()-1, yMax = GameBoard.getSize()-1;
        switch(corner) {
            case NorthWest:
                for (int x = 0; x < SIZE; ++x) {
                    for (int y = yMax; y > yMax - SIZE; --y) {
                        westeros.getMap()[x][y].removeObstacle();
                        westeros.getMap()[x][y].setSafeFor(safeFor);
                        if (x == SIZE-1 || y == yMax-SIZE+1) {
                            westeros.getMap()[x][y].setLimitSafeZone(setSafeZone);
                        }
                    }
                }
                break;
            case NorthEast:
                for (int x = xMax; x > xMax - SIZE; --x) {
                    for (int y = yMax; y > yMax - SIZE; --y) {
                        westeros.getMap()[x][y].removeObstacle();
                        westeros.getMap()[x][y].setSafeFor(safeFor);
                        if (x == xMax-SIZE+1 || y == yMax-SIZE+1) {
                            westeros.getMap()[x][y].setLimitSafeZone(setSafeZone);
                        }
                    }
                }
                break;
            case SouthEast:
                for (int x = xMax; x > xMax - SIZE; --x) {
                    for (int y = 0; y < SIZE; ++y) {
                        westeros.getMap()[x][y].removeObstacle();
                        westeros.getMap()[x][y].setSafeFor(safeFor);
                        if (x == xMax-SIZE+1 || y == SIZE-1) {
                            westeros.getMap()[x][y].setLimitSafeZone(setSafeZone);
                        }
                    }
                }
                break;
            case SouthWest:
                for (int x = 0; x < SIZE; ++x) {
                    for (int y = 0; y < SIZE; ++y) {
                        westeros.getMap()[x][y].removeObstacle();
                        westeros.getMap()[x][y].setSafeFor(safeFor);
                        if (x == SIZE-1 || y == SIZE-1) {
                            westeros.getMap()[x][y].setLimitSafeZone(setSafeZone);
                        }
                    }
                }
                break;
        }
    }
    
}

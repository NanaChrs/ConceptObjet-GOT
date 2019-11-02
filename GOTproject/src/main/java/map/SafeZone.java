package map;

import factions.Faction;
import gameplay.GameMaster;
import gameplay.Statistics;
import gameplay.UserInterface;

public class SafeZone {
    protected Faction safeFor;
    protected Direction corner; //coin par rapport au centre (NO, NE, SO ou SE)
    protected GameBoard westeros;//composition
    
    protected static int SIZE;
    protected final static int RECOVERY = 10;//stamina récupérée
    
    public SafeZone(int size, GameBoard map, Faction population, Direction orientation) {
        SafeZone.SIZE = size;
        this.westeros = map;
        this.corner = orientation;
        this.safeFor = population;
        applySafeZone(true);
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
    
    public void removeEmptyFaction() throws InterruptedException {
        int remaining = 1;
        switch(safeFor) {
            case Lannister:
                if ((remaining = Statistics.LannisterAlive()) == 0) {
                    Statistics.endOfLannister(GameMaster.getTurn());
                }
                break;
            case Targaryen:
                if ((remaining = Statistics.TargaryenAlive()) == 0) {
                    Statistics.endOfTargaryen(GameMaster.getTurn());
                }
                break;
            case Stark:
                if ((remaining = Statistics.StarkAlive()) == 0) {
                    Statistics.endOfStark(GameMaster.getTurn());
                }
                break;
            case Wilding:
                if ((remaining = Statistics.WildingsAlive()) == 0) {
                    Statistics.endOfWildings(GameMaster.getTurn());
                }
                break;
        }
            
        if (remaining == 0) {
            String name = safeFor.toString();
            safeFor = null;
            applySafeZone(false);//affichage carte
            westeros.getTowns().remove(this);//enregistrement safezone
            UserInterface.displayConsole("Les " + name + " sont morts : leur zone d'influence disparaît", westeros, 2);
        }
    }
    
}

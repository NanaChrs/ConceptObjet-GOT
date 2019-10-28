package map;

import factions.Faction;

public class SafeZone {
    protected Faction safeFor;
    protected Direction corner;//par rapport au centre
    protected GameBoard westeros;//aggrégation
    
    protected final static int SIZE = 5;
    protected final static int RECOVERY = 10;//stamina récupérée
    
    public SafeZone(GameBoard map, Faction population, Direction orientation) {
        this.westeros = map;
        this.corner = orientation;
        this.safeFor = population;
        applySafeZone();
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
    
    public void setSafeFor(Faction population) {
        this.safeFor = population;
        applySafeZone();
    }
    
    private void applySafeZone() {
        int xMax = GameBoard.getWidth()-1, yMax = GameBoard.getHeight()-1;
        switch(corner) {
            case NorthWest:
                for (int x = 0; x < SIZE; ++x) {
                    for (int y = yMax; y > yMax - SIZE; --y) {
                        westeros.getMap()[x][y].removeObstacle();
                        westeros.getMap()[x][y].setSafeFor(safeFor);
                        if (x == SIZE-1 || y == yMax-SIZE+1) {
                            westeros.getMap()[x][y].setLimitSafeZone();
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
                            westeros.getMap()[x][y].setLimitSafeZone();
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
                            westeros.getMap()[x][y].setLimitSafeZone();
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
                            westeros.getMap()[x][y].setLimitSafeZone();
                        }
                    }
                }
                break;
        }
    }
}

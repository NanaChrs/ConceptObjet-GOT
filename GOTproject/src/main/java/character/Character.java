
package character;

import java.io.IOException;
import java.util.ArrayList;

import gameplay.DiceResult;
import static gameplay.UserInterface.displayConsole;
import map.Box;
import map.Direction;
import map.GameBoard;
import map.SafeZone;

public abstract class Character {
    //Attributs - Instance définie par :
    //  sa position sur la map et la possibilité de s'y mouvoir
    protected GameBoard westeros;//agrégation
    protected Box currentBox;//agrégation
    protected final static int MAX_RANGE = 5; //Attribut statique qui a du sens
    
    //  sa vie et les dégâts qu'il fait
    protected final static int DEFAULT_STAT_VALUE = 100;
    protected int maxLife;//ww op car + de vie que les autres
    protected int life;
    protected int maxPower;//ww op car + de power que les autres & systeme de niveau
    protected int power;
    //protected int dodge;//ajouter complexité aux combats
    
    //  sa chance
    private final static int DEFAULT_THRESHOLD_VALUE = 50;
    private final static int THRESHOLD_MAX = 100; //Attribut statique qui a du sens
    private final int criticalSuccessThreshold;
    private final int failureThreshold;
    
    //Constructeur & destructeur - naissance et mort de l'instance
    public Character(int maxLife, int maxPower, int power, int criticalSuccessThreshold, int failureThreshold) {
        this.life = this.maxLife    = maxLife > 0   ? maxLife   : DEFAULT_STAT_VALUE;
        this.maxPower               = maxPower > 0  ? maxPower  : DEFAULT_STAT_VALUE;
        
        this.power = power < 0 ? 0 : (power > this.maxPower ? DEFAULT_STAT_VALUE/4 : power);
        
        this.criticalSuccessThreshold   = criticalSuccessThreshold < 0  ? DEFAULT_THRESHOLD_VALUE   : (criticalSuccessThreshold > THRESHOLD_MAX ? THRESHOLD_MAX : criticalSuccessThreshold);
        this.failureThreshold           = failureThreshold < 0          ? DEFAULT_THRESHOLD_VALUE   : (failureThreshold         > THRESHOLD_MAX ? THRESHOLD_MAX : failureThreshold);
    }
    
    public void death() throws InterruptedException {
        westeros.removeBody(this);
        displayConsole((this instanceof Human? ((Human)this).getFullName() : "Le marcheur blanc") + 
                " meurt", westeros, 3);
    }
    
    //Getters & setters utiles
    //  position
    public void setMap(GameBoard map) {
        this.westeros = map;
    }

    public void setBox(Box position) {
        this.currentBox = position;
    }
    
    public Box getBox() {
        return this.currentBox;
    }
    
    //  attributs
    public void setLife(int life) {
        this.life = (life > maxLife)? maxLife : life;
    }
    
    public void addLife(int life) {
        this.setLife(this.life + life);
    }
    
    public void reduceLife(int life) throws InterruptedException {
        this.setLife(this.life - life);
        if (this.life <= 0) this.death();
    }
    
    public boolean isAlive() {
        return this.life > 0;
    }

    public void setPower(int power) {
        this.power = (power < 0) ? 0 : (power > maxPower ? maxPower : power);
    }

    public void addPower(int power) {
        this.setPower(this.power + power);
    }

/*
    public void setDodge(int dodge) {
            this.dodge = (this.dodge < 0) ? 0 : dodge;
    }
*/
    
    //Méthodes private - actions spécifiques
    private int currentRange() {
        //si humain à court d'energie, bouge plus
        if (this instanceof Human && ((Human)this).stamina == 0) {
            return 0;
        }
        
        switch(this.rollDice()) {
            case CRITICAL_SUCCESS:
                return MAX_RANGE;
            case SUCCESS: 
                return (int) (0.6*MAX_RANGE);
            default: 
                return (int) (0.3*MAX_RANGE);
        }	
    }
    
    private boolean isNextFree(Direction takenDirection) {
        int posX = this.currentBox.getX(), posY = this.currentBox.getY();
        int xMax = GameBoard.getWidth(), yMax = GameBoard.getHeight();
        
        switch(takenDirection) {//bas gauche: (0,0) ; haut droite : (max,max)
            case NorthWest :
                return (posX-1 >= 0 &&      posY+1 < yMax &&    westeros.getMap()[posX-1][posY+1].isEmpty());
            case North :
                return (                    posY+1 < yMax &&    westeros.getMap()[posX][posY+1].isEmpty());
            case NorthEast :
                return (posX+1 < xMax &&    posY+1 < yMax &&    westeros.getMap()[posX+1][posY+1].isEmpty());
            case East :
                return (posX+1 < xMax &&                        westeros.getMap()[posX+1][posY].isEmpty());
            case SouthEast :
                return (posX+1 < xMax &&    posY-1 >= 0 &&      westeros.getMap()[posX+1][posY-1].isEmpty());
            case South :
                return (                    posY-1 >= 0 &&      westeros.getMap()[posX][posY-1].isEmpty());
            case SouthWest :
                return (posX-1 >= 0 &&      posY-1 >= 0 &&      westeros.getMap()[posX-1][posY-1].isEmpty());
            case West :
                return (posX-1 >= 0 &&                          westeros.getMap()[posX-1][posY].isEmpty());
            default:
                return false;
        }
    }
    
    private Direction diagoRight(Direction dir) {
        switch (dir) {
            case North:
                return Direction.NorthEast;
            case NorthEast:
                return Direction.East;
            case East:
                return Direction.SouthEast;
            case SouthEast:
                return Direction.South;
            case South:
                return Direction.SouthWest;
            case SouthWest:
                return Direction.West;
            case West:
                return Direction.NorthWest;
            case NorthWest:
                return Direction.North;
            default: 
                return null;
        }
    }
    
    private Direction diagoLeft(Direction dir) {
        switch (dir) {
            case North:
                return Direction.NorthWest;
            case NorthWest:
                return Direction.West;
            case West:
                return Direction.SouthWest;
            case SouthWest:
                return Direction.South;
            case South:
                return Direction.SouthEast;
            case SouthEast:
                return Direction.East;
            case East:
                return Direction.NorthEast;
            case NorthEast:
                return Direction.North;
            default: 
                return null;
        }
    }
    
    private ArrayList<Direction> potentialDirections() {
        ArrayList<Direction> list = new ArrayList<>();
        
        //si humain bientot à court d'énergie, cherche safezone
        if (this instanceof Human && ((Human)this).stamina <= Human.LOW_STAMINA) {
            //les 3 directions qui rapprochent de la safezone en verifiant qu'elles ne sont pas bouchées
            //si bouchées, ne se déplace pas (économise ses forces)
            
            //corner : duo de directions cardinales
            Direction corner = ((Human)this).westeros.getSafeZone(this.getClass().getSimpleName()).getCorner();
            if (isNextFree(corner)) {
                list.add(corner);
            }
            
            int posX = this.currentBox.getX(), posY = this.currentBox.getY();
            
            //une coordonnée au niveau de safezone : déplacement en "+"
            if ((corner.equals(Direction.NorthWest) && posX < SafeZone.getSize()) || 
                (corner.equals(Direction.SouthWest) && posY < SafeZone.getSize()) || 
                (corner.equals(Direction.SouthEast) && posX > GameBoard.getWidth() - SafeZone.getSize()) || 
                (corner.equals(Direction.NorthEast) && posY > GameBoard.getHeight() - SafeZone.getSize())) {
                Direction diagoRight = diagoRight(corner);
                if (isNextFree(diagoRight)) {
                    list.add(diagoRight);
                }
                diagoRight = diagoRight(diagoRight);
                if (isNextFree(diagoRight)) {
                    list.add(diagoRight);
                }
            }
            //l'autre coordonnée au niveau de safezone : déplacement en "+"
            else if ((corner.equals(Direction.SouthWest) && posX < SafeZone.getSize()) || 
                    (corner.equals(Direction.SouthEast) && posY < SafeZone.getSize()) || 
                    (corner.equals(Direction.NorthEast) && posX > GameBoard.getWidth() - SafeZone.getSize()) || 
                    (corner.equals(Direction.NorthWest) && posY > GameBoard.getHeight() - SafeZone.getSize())) {
                Direction diagoLeft = diagoLeft(corner);
                if (isNextFree(diagoLeft)) {
                    list.add(diagoLeft);
                }
                diagoLeft = diagoLeft(diagoLeft);
                if (isNextFree(diagoLeft)) {
                    list.add(diagoLeft);
                }
            }
            //aucune coordonnée au niveau de safezone : déplacement en "x"
            else {
                Direction diagoRight = diagoRight(corner);
                if (isNextFree(diagoRight)) {
                    list.add(diagoRight);
                }
                Direction diagoLeft = diagoLeft(corner);
                if (isNextFree(diagoLeft)) {
                    list.add(diagoLeft);
                }
            }
        }
        else {
            //ajoute directions dégagées à une case de distance
            for (Direction dir : Direction.values()) {
                if(isNextFree(dir)) {
                    list.add(dir);
                }
            }
        }
        
        return list;
    }

    private Box makeStep(Direction direction) {
        //complémentaire de isNextFree
        int posX = this.currentBox.getX(), posY = this.currentBox.getY();
        
        switch(direction) {
            case NorthWest :
                return westeros.getMap()[posX-1][posY+1];
            case North :
                return westeros.getMap()[posX  ][posY+1];
            case NorthEast :
                return westeros.getMap()[posX+1][posY+1];
            case East :
                return westeros.getMap()[posX+1][posY  ];
            case SouthEast :
                return westeros.getMap()[posX+1][posY-1];
            case South :
                return westeros.getMap()[posX  ][posY-1];
            case SouthWest :
                return westeros.getMap()[posX-1][posY-1];
            case West :
                return westeros.getMap()[posX-1][posY  ];
            default:
                return null;
        }
    }
    
    //Méthodes protected - actions définies ou réutilisables en interne dans d'autres contextes
    /** 
    * Rolls a dice
    * @return diceResult   result of the throw
    */ 
    protected  DiceResult rollDice() {
       int diceValue = (int) (Math.random() * 100) + 1;//plus grand échec : 1 | plus grande réussite : 100
       
       //more to less probable
       if (diceValue < this.criticalSuccessThreshold && diceValue > this.failureThreshold) {
           return DiceResult.SUCCESS;
       }
       else if (diceValue < this.failureThreshold) {
           return DiceResult.FAILURE;
       }
       else {
           return DiceResult.CRITICAL_SUCCESS;
       }
    }

    protected abstract void movmentConsequences();

    protected abstract void meet(Human character, int remainingBoxes) throws IOException, InterruptedException;
    
    protected abstract void meet(WhiteWalker character, int remainingBoxes) throws IOException, InterruptedException;

    protected abstract void attack(Character character) throws IOException, InterruptedException;
    
    //Méthodes public - actions que peut réaliser l'instance
    public void move() throws IOException, InterruptedException {
        //etape 1 : recuperer les directions de deplacement envisageables
        ArrayList<Direction> validDir = potentialDirections();
        
        //etape 2 : se deplacer le long d'une direction
        int range = currentRange();
        if (!validDir.isEmpty() && range > 0) {
            //choisir une direction au hasard
            Direction takenDir = validDir.get((int)(Math.random() * validDir.size()));
            
            //tant que la case suivante est vide et à portée, le perso se déplace + action de se déplacer dans movmentConsequences (perte de stamina, gain de pv, xp...)
            do {//premiere case forcément vide
                westeros.getMap()[currentBox.getX()][currentBox.getY()].removeCharacter();
                currentBox = makeStep(takenDir);
                westeros.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(this);
                
                displayConsole((this instanceof Human? ((Human)this).getFullName() : "Un marcheur blanc") +
                        " se déplace" + (this instanceof Human? "(" + ((Human)this).stamina + ")" : ""), westeros, 4);
                
                movmentConsequences();
            } while(--range > 0 && isNextFree(takenDir));
        }

        //etape 3 : scan des environs dans carte et interaction avec persos des cases juxtaposées
        //diminutifs
        int limXInf = currentBox.getX()-1,
            limXSup = currentBox.getX()+1,
            xMax = GameBoard.getWidth(),
            limYInf = currentBox.getY()-1,
            limYSup = currentBox.getY()+1,
            yMax = GameBoard.getHeight();
        
        //test des limites de map dans boucle for (moins de test au total)
        this.currentBox.displayBox();
        for (int x = limXInf+(limXInf<0 ? 1 : 0); x <= limXSup-(limXSup>=xMax ? 1 : 0); ++x) {
            for (int y = limYInf+(limYInf<0 ? 1 : 0); y <= limYSup-(limYSup>=yMax? 1 : 0); ++y) {
                //si être vivant présent autour du perso
                Character somebody = westeros.getMap()[x][y].getCharacter();
                if (somebody != null && !(x == currentBox.getX() && y == currentBox.getY())) {
                    //va a sa rencontre
                    if (somebody instanceof Human) {
                        meet((Human) somebody,range);
                    }
                    else {
                        meet((WhiteWalker) somebody,range);
                    }
                }
            }
        }
    }
}


package character;

import java.io.IOException;
import java.util.ArrayList;

import gameplay.DiceResult;
import gameplay.Statistics;
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
    protected final static int MAX_RANGE = 10;//Attribut statique qui a du sens
    
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
    
    public void death(DamageSource cause, Character character) throws InterruptedException {
        westeros.getMap()[currentBox.getX()][currentBox.getY()].removeCharacter();
        displayConsole((this instanceof Human? ((Human)this).getFullName() : "Le marcheur blanc") + " meurt", westeros, 3);

        if (this instanceof Human) {
            this.westeros.getSafeZone(this.getClass().getSimpleName()).removeFactionMember();
            
            if (cause.equals(DamageSource.Battle)) {
                if (character instanceof Lannister) {
                    Statistics.northernerKilledByLannister();
                }
                else if (character instanceof Targaryen) {
                    Statistics.northernerKilledByTargaryen();
                }
                else if (character instanceof Stark) {
                    Statistics.southernerKilledByStark();
                }
                else if (character instanceof Wilding) {
                    Statistics.southernerKilledByWildings();
                }
                else {
                    Statistics.humanKilledByWW();
                }
            }
        }
        
        if (this instanceof Lannister) {
            if (cause.equals(DamageSource.Nature)) {
                Statistics.LannisterDeadAlone();
            }
            else {   
                Statistics.LannisterDeadInBattle();
            }
        }
        else if (this instanceof Targaryen) {
            if (cause.equals(DamageSource.Nature)) {
                Statistics.TargaryenDeadAlone();
            }
            else {
                Statistics.TargaryenDeadInBattle();
            }
        }
        else if (this instanceof Stark) {
            if (cause.equals(DamageSource.Nature)) {
                Statistics.StarkDeadAlone();
            }
            else {   
                Statistics.StarkDeadInBattle();
            }
        }
        else if (this instanceof Wilding) {
            if (cause.equals(DamageSource.Nature)) {
                Statistics.WildingDeadAlone();
            }
            else {   
                Statistics.WildingDeadInBattle();
            }
        }
        else {
            Statistics.WWDeadInBattle();
            
            if (character instanceof Lannister) {
                Statistics.WWKilledByLannister();
            }
            else if (character instanceof Targaryen) {
                Statistics.WWKilledByTargaryen();
            }
            else if (character instanceof Stark) {
                Statistics.WWKilledByStark();
            }
            else /*if (this instanceof Wilding)*/ {
                Statistics.WWKilledByWildings();
            }
        }
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
    public void addLife(int life) {
        this.life += life;
        if (this.life > maxLife) this.life = maxLife;
    }
    
    public void reduceLife(int life, DamageSource cause, Character character) throws InterruptedException {
        this.life -= life;
        if (this.life <= 0) this.death(cause,character);
    }
    
    public boolean isAlive() {
        return this.life > 0;
    }

    public void addPower(int power) {
        this.power += power;
        if (this.power > maxPower) this.power = maxPower;
    }

/*
    public void setDodge(int dodge) {
            this.dodge = (this.dodge < 0) ? 0 : dodge;
    }
*/
    
    //Méthodes private - actions spécifiques
    private int currentRange() throws InterruptedException {
        //si humain à court d'energie, bouge plus
        if (this instanceof Human && ((Human)this).stamina == 0) {
            if (((Human)this).turnsWithoutStamina++ >= Human.MAX_TURNS_WITHOUT_STAMINA) {//not found : no rescue
                this.reduceLife(this.life,DamageSource.Nature,null);
            }
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
        int xMax = GameBoard.getSize(), yMax = GameBoard.getSize();
        
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
        if (this instanceof Human && !currentBox.isSafe() && ((Human)this).stamina <= Human.LOW_STAMINA) {
            //les 3 directions qui rapprochent de la safezone en verifiant qu'elles ne sont pas bouchées
            //si bouchées, ne se déplace pas (économise ses forces)
            
            //corner : duo de directions cardinales
            Direction corner = ((Human)this).safeZoneDirection();
            if (isNextFree(corner)) {
                list.add(corner);
            }
            
            int posX = this.currentBox.getX(), posY = this.currentBox.getY();
            
            //une coordonnée au niveau de safezone : déplacement en "+"
            if ((corner.equals(Direction.NorthWest) && posX < SafeZone.getSize()) || 
                (corner.equals(Direction.SouthWest) && posY < SafeZone.getSize()) || 
                (corner.equals(Direction.SouthEast) && posX > GameBoard.getSize() - SafeZone.getSize()) || 
                (corner.equals(Direction.NorthEast) && posY > GameBoard.getSize() - SafeZone.getSize())) {
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
                    (corner.equals(Direction.NorthEast) && posX > GameBoard.getSize() - SafeZone.getSize()) || 
                    (corner.equals(Direction.NorthWest) && posY > GameBoard.getSize() - SafeZone.getSize())) {
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
    
    private void moveMessage() throws InterruptedException {
        String message1, message2;
        if (this instanceof Human) {
            message1 = ((Human)this).getFullName();
            message2 = "; energie : " + ((Human)this).stamina + ")";
        }
        else {
            message1 = "Un marcheur blanc";
            message2 = ")";
        }
        displayConsole(message1 + " se déplace (vie : " + this.life + message2, westeros, 4);
    }
    
    private boolean foundSomeoneInSurroundings(int range) throws IOException, InterruptedException {
        //scan des environs dans carte et interaction avec persos des cases juxtaposées
        boolean foundSomeone = false;
        
        //diminutifs
        int limXInf = currentBox.getX()-1,
            limXSup = currentBox.getX()+1,
            xMax = GameBoard.getSize(),
            limYInf = currentBox.getY()-1,
            limYSup = currentBox.getY()+1,
            yMax = GameBoard.getSize();
        
        //test des limites de map dans boucle for (moins de test au total)
        this.currentBox.displayBox();
        for (int x = limXInf+(limXInf<0 ? 1 : 0); x <= limXSup-(limXSup>=xMax ? 1 : 0); ++x) {
            for (int y = limYInf+(limYInf<0 ? 1 : 0); y <= limYSup-(limYSup>=yMax? 1 : 0); ++y) {
                //si être vivant présent autour du perso
                Character somebody = westeros.getMap()[x][y].getCharacter();
                if (somebody != null && somebody.isAlive() && !(x == currentBox.getX() && y == currentBox.getY())) {
                    foundSomeone = true;
                    //va a sa rencontre
                    if (somebody instanceof Human) {
                        meet((Human) somebody,range);
                    }
                    else {
                        meet((WhiteWalker) somebody,range);
                    }
                    if (!this.isAlive()) return true;
                }
            }
        }
        
        return foundSomeone;
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

    protected abstract void movmentConsequences() throws InterruptedException;

    protected abstract void meet(Human character, int remainingBoxes) throws IOException, InterruptedException;
    
    protected abstract void meet(WhiteWalker character, int remainingBoxes) throws IOException, InterruptedException;

    protected abstract void attack(Character character) throws IOException, InterruptedException;
    
    //Méthodes public - actions que peut réaliser l'instance
    public void move() throws IOException, InterruptedException {
        //etape 1 : connaitre la portée
        int range = currentRange();
        
        if (range > 0) {
            //etape 2 : recuperer les directions de deplacement envisageables
            ArrayList<Direction> validDir = potentialDirections();

            if (!validDir.isEmpty()) {
                //etape 3 : se deplacer le long d'une direction choisie au hasard
                Direction takenDir = validDir.get((int)(Math.random() * validDir.size()));

                //tant que la case suivante est vide et à portée, le perso se déplace + action de se déplacer dans movmentConsequences (perte de stamina, gain de pv, xp...)
                moveMessage();//etat initial
                do {//premiere case forcément vide
                    movmentConsequences();
                    if (!this.isAlive()) return;//humains qui perdent vie sur terrain inhospitaliers

                    westeros.getMap()[currentBox.getX()][currentBox.getY()].removeCharacter();
                    currentBox = makeStep(takenDir);
                    westeros.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(this);

                    moveMessage();//etat final
                } while(!foundSomeoneInSurroundings(range) && this.isAlive() && --range > 0 && isNextFree(takenDir));
            }
        }
        else if (this.isAlive()) {//mort par manque de stamina et de secours
            foundSomeoneInSurroundings(0);
        }
    }
}

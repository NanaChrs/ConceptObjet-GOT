
package character;

import java.io.IOException;
import java.util.ArrayList;

import gameplay.DiceResult;
import static gameplay.UserInterface.displayConsole;
import map.Box;
import map.Direction;
import map.GameBoard;

public abstract class Character {
    final static protected int MAX_LIFE = 100; //Attribut statique qui a du sens
    protected int life = 100;
    protected int power;
    //protected int dodge;
    
    static protected int MAX_STEP_NUMBER;
    static protected int CRITIC_SUCCESS_LEVEL=80;
    static protected int FAILURE_LEVEL=20;

    protected GameBoard westeros;
    protected Box currentBox;

    public Character() {
        MAX_STEP_NUMBER = 5;
    }
    
    public void death() throws InterruptedException {
        westeros.removeBody(this);
        displayConsole((this instanceof Human? ((Human)this).name + " " + this.getClass().getSimpleName() : "Un marcheur blanc") + 
                " meurt", westeros, 2);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) throws InterruptedException {
        if (life <= 0) this.death();
        this.life = (life > MAX_LIFE)? MAX_LIFE : life;
    }
    
    public Box getBox() {
    	return currentBox;
    }

    public void setMap(GameBoard map) {
        this.westeros = map;
    }

    public void setBox(Box position) {
        this.currentBox = position;
    }
    
    public static int getCriticSuccessLevel() {
        return CRITIC_SUCCESS_LEVEL;
    }

    public static int getFailureLevel() {
        return FAILURE_LEVEL;
    }

    public static int getMaxStepNumber() {
        return MAX_STEP_NUMBER;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = (this.power < 0) ? 0 : power;
    }
    
    public boolean isAlive() {
        return this.life > 0;
    }
/*
    public int getDodge() {
            return dodge;
    }

    public void setDodge(int dodge) {
            this.dodge = (this.dodge < 0) ? 0 : dodge;
    }
*/
    
    /** 
    * Rolls a dice
    * @return diceResult   result of the throw
    */ 
    protected  DiceResult rollDice() {
       int diceValue = (int) (Math.random() * 100) + 1;//plus grand échec : 0 | plus grande réussite : 100
       
       DiceResult result;//more to less probable
       
       if (diceValue < Character.getCriticSuccessLevel() && diceValue > Character.getFailureLevel()) {
           result = DiceResult.SUCCESS;
       }
       else if (diceValue < Character.getFailureLevel()) {
           result = DiceResult.FAILURE;
       }
       else {
           result = DiceResult.CRITIC_SUCCESS;
       }

       return result;
    }

    private int determineStepNumbers() {
        //si humain à court d'energie, bouge plus
        if (this instanceof Human && ((Human)this).stamina == 0) {
            return 0;
        }
        
        switch(this.rollDice()) {
            case CRITIC_SUCCESS:
                return MAX_STEP_NUMBER;
            case SUCCESS: 
                return (int) (0.5*MAX_STEP_NUMBER);
            default: 
                return (int) (0.25*MAX_STEP_NUMBER);
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
    
    private ArrayList<Direction> possibleDirections() {
        ArrayList<Direction> list = new ArrayList<>();

        /*
        //si humain bientot à court d'énergie, cherche safezone -> implémnter safezone
        if (this instanceof Human && ((Human)this).stamina <= Human.LOW_STAMINA) {
            Direction corner = ((Human)this).westeros.getSafeZone(this.getClass().getSimpleName()).getCorner();

            //les 3 directions qui rapprochent de la safezone en verifiant qu'elles ne sont pas bouchées
            //si bouchées, ne se déplace pas (économise ses forces)
            list.add(corner);
            list.add(corner);
            list.add(corner);

            return list;
        }
*/
        
        //ajoute directions dégagées à une case de distance
        for (Direction dir : Direction.values()) {
            if(isNextFree(dir)) {
                list.add(dir);
            }
        }
        
        return list;
    }

    private Box stepMove(Direction takenDirection) {
        //complémentaire de isNextFree
        switch(takenDirection) {
            case NorthWest :
                return westeros.getMap()[this.currentBox.getX()-1][this.currentBox.getY()+1];
            case North :
                return westeros.getMap()[this.currentBox.getX()][this.currentBox.getY()+1];
            case NorthEast :
                return westeros.getMap()[this.currentBox.getX()+1][this.currentBox.getY()+1];
            case East :
                return westeros.getMap()[this.currentBox.getX()+1][this.currentBox.getY()];
            case SouthEast :
                return westeros.getMap()[this.currentBox.getX()+1][this.currentBox.getY()-1];
            case South :
                return westeros.getMap()[this.currentBox.getX()][this.currentBox.getY()-1];
            case SouthWest :
                return westeros.getMap()[this.currentBox.getX()-1][this.currentBox.getY()-1];
            case West :
                return westeros.getMap()[this.currentBox.getX()-1][this.currentBox.getY()];
            default:
                return currentBox;//ou null
        }
    }
    
    public void move() throws IOException, InterruptedException {
        //etape 1 : recuperer les directions de deplacement envisageables
        ArrayList<Direction> validDir = possibleDirections();
        
        //etape 2 : se deplacer le long d'une direction
        int range = determineStepNumbers();
        if (!validDir.isEmpty() && range > 0) {
            //choisir une direction au hasard
            Direction takenDir = validDir.get((int)(Math.random() * validDir.size()));
            
            //tant que la case suivante est vide et à portée, le perso se déplace + action de se déplacer dans movmentConsequences (perte de stamina, gain de pv, xp...)
            do {//premiere case forcément vide
                westeros.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(null);
                currentBox = stepMove(takenDir);
                westeros.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(this);
                
                displayConsole((this instanceof Human? ((Human)this).name + " " + this.getClass().getSimpleName() : "Un marcheur blanc") + 
                        " se déplace", westeros, 3);
                
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
                if (!westeros.getMap()[x][y].isEmpty() && !westeros.getMap()[x][y].isObstacle() && 
                    !(x == currentBox.getX() && y == currentBox.getY())) {
                    //va a sa rencontre
                    if (westeros.getMap()[x][y].getCharacter() instanceof Human) {
                        meet((Human) westeros.getMap()[x][y].getCharacter(),range);
                    }
                    else {
                        meet((WhiteWalker) westeros.getMap()[x][y].getCharacter(),range);
                    }
                }
            }
        }
    }
    
    protected abstract void movmentConsequences() throws InterruptedException;

    protected abstract void meet(Human character, int remainingBoxes) throws IOException, InterruptedException;
    
    protected abstract void meet(WhiteWalker character, int remainingBoxes) throws IOException, InterruptedException;

    protected abstract void attack(Character character) throws IOException, InterruptedException;
}


package character;

import java.io.IOException;
import java.util.ArrayList;

import gameplay.DiceResult;
import static gameplay.UserInterface.displayConsole;
import map.Box;
import map.Direction;
import map.GameBoard;

public abstract class Character {
    static protected int MAX_LIFE = 100;
    protected int life = 100;
    protected int power;
    //protected int dodge;
    
    static protected int MAX_STEP_NUMBER;
    static protected int CRITIC_SUCCESS_LEVEL=80;
    static protected int FAILURE_LEVEL=20;

    protected GameBoard westeros;
    protected Box currentBox;
    protected Direction lastDirection;

    public Character() {
        MAX_STEP_NUMBER = 5;
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        if(life > MAX_LIFE) {
            this.life =100;
        }
        else if (life <=0) {
            this.life = 0;
        }
        else {
            this.life = life;
        }
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = (this.power < 0) ? 0 : power;
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
    
    public boolean isAlive() {
        return this.life > 0;
    }

    private int determineStepNumbers() {
        //si humain à court d'energie, bouge plus
        if (this instanceof Human) {
            Human character = (Human)this;
            if (character.stamina == 0) return 0;
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
    
    private Direction stepBack() {
        switch (lastDirection) {
            case North:
                return Direction.South;
            case South:
                return Direction.North;
            case East:
                return Direction.West;
            case West:
                return Direction.East;
            case NorthWest:
                return Direction.SouthEast;
            case SouthWest:
                return Direction.NorthEast;
            case NorthEast:
                return Direction.SouthWest;
            case SouthEast:
                return Direction.NorthWest;
        }
        return lastDirection;
    }
    
    private Direction stepBackDiagoRight() {
        switch (lastDirection) {
            case North:
                return Direction.SouthEast;
            case South:
                return Direction.NorthWest;
            case East:
                return Direction.SouthWest;
            case West:
                return Direction.NorthEast;
            case NorthWest:
                return Direction.East;
            case SouthWest:
                return Direction.North;
            case NorthEast:
                return Direction.South;
            case SouthEast:
                return Direction.West;
        }
        return lastDirection;
    }
    
    private Direction stepBackDiagoLeft() {
        switch (lastDirection) {
            case North:
                return Direction.SouthWest;
            case South:
                return Direction.NorthEast;
            case East:
                return Direction.NorthWest;
            case West:
                return Direction.SouthEast;
            case NorthWest:
                return Direction.South;
            case SouthWest:
                return Direction.East;
            case NorthEast:
                return Direction.West;
            case SouthEast:
                return Direction.North;
        }
        return lastDirection;
    }
    
    private ArrayList<Direction> possibleDirections() {
        ArrayList<Direction> list = new ArrayList<>();

        int x = currentBox.getX(), xMax = GameBoard.getWidth(),
            y = currentBox.getY(), yMax = GameBoard.getHeight();

        /*
        //si humain bientot à court d'énergie, cherche safezone -> implémnter safezone
        if (this instanceof Human) {
            Human character = (Human)this;
            if (character.stamina <= character.LOW_STAMINA) {
                //les 3 directions qui rapprochent de la safezone en verifiant qu'elles ne sont pas bouchées
                //si bouchées, ne se déplace pas (économise ses forces)
                list.add(Direction.North);
                list.add(Direction.North);
                list.add(Direction.North);
                return list;
            }
        }
*/
        
        //ajoute directions dégagées à une case de distance
        //bas gauche: (0,0) ; haut droite : (max,max)
        if (x-1 >= 0 && y+1 < yMax 
                && westeros.getMap()[x-1][y+1].isEmpty()) {
            list.add(Direction.NorthWest);
        }
        if (y+1 < yMax 
                && westeros.getMap()[x][y+1].isEmpty()) {
            list.add(Direction.North);
        }
        if (x+1 < xMax && y+1 < yMax 
                && westeros.getMap()[x+1][y+1].isEmpty()) {
            list.add(Direction.NorthEast);
        }
        if (x+1 < xMax 
                && westeros.getMap()[x+1][y].isEmpty()) {
            list.add(Direction.East);
        }
        if (x+1 < xMax && y-1 >= 0 
                && westeros.getMap()[x+1][y-1].isEmpty()) {
            list.add(Direction.SouthEast);
        }
        if (y-1 >= 0 
                && westeros.getMap()[x][y-1].isEmpty()) {
            list.add(Direction.South);
        }
        if (x-1 >= 0 && y-1 >= 0 
                && westeros.getMap()[x-1][y-1].isEmpty()) {
            list.add(Direction.SouthWest);
        }
        if (x-1 >= 0 
                && westeros.getMap()[x-1][y].isEmpty()) {
            list.add(Direction.West);
        }
        
        //si a beaucoup de choix, évite de reprendre inverse de dernière direction et ses diagonales
        if (lastDirection != null && list.size() > 1) {
            //commence par ne pas reculer
            Direction stepBack = stepBack();
            if (list.contains(stepBack)) {
                list.remove(stepBack);
            }
            
            //si a encore beaucoup de choix, préfère avancer
            if (list.size() > 2) {
                Direction diagoRight = stepBackDiagoRight(),
                        diagoLeft = stepBackDiagoLeft();
                if (list.contains(diagoRight)) {
                    list.remove(diagoRight);
                }
                if (list.contains(diagoLeft)) {
                    list.remove(diagoLeft);
                }
            }
            //si a juste un peu de choix, élimine une des diago 
            else if (list.size() > 1) {
                if (Math.random() > 0.5) {
                    Direction diagoRight = stepBackDiagoRight();
                    if (list.contains(diagoRight)) {
                        list.remove(diagoRight);
                    }
                }
                else {
                    Direction diagoLeft = stepBackDiagoLeft();
                    if (list.contains(diagoLeft)) {
                        list.remove(diagoLeft);
                    }
                }
            }
        }
        
        return list;
    }

    private boolean canMove(Direction takenDirection) {
        //complémentaire de possibleDirection
        switch(takenDirection) {
            case NorthWest :
                if (this.currentBox.getX()-1 >= 0 && this.currentBox.getY()+1 < GameBoard.getHeight()) {
                    return true;
                }
                break;
            case North :
                if (this.currentBox.getY()+1 < GameBoard.getHeight()) {
                    return true;
                }
                break;
            case NorthEast :
                if (this.currentBox.getX()+1 < GameBoard.getWidth() && this.currentBox.getY()+1 < GameBoard.getHeight()) {
                    return true;
                }
                break;
            case East :
                if (this.currentBox.getX()+1 < GameBoard.getWidth()) {
                    return true;
                }
                break;
            case SouthEast :
                if (this.currentBox.getX()+1 < GameBoard.getWidth() && this.currentBox.getY()-1 >= 0) {
                    return true;
                }
                break;
            case South :
                if (this.currentBox.getY()-1 >= 0) {
                    return true;
                }
                break;
            case SouthWest :
                if (this.currentBox.getX()-1 >= 0 && this.currentBox.getY()-1 >= 0) {
                    return true;
                }
                break;
            case West :
                if (this.currentBox.getX()-1 >= 0) {
                    return true;
                }
                break;
        }
        
        return false;
    }
    
    private Box stepMove(Direction takenDirection) {
        //complémentaire de possibleDirection, sécurisé par canMove
        Box nextBox = this.currentBox;
        switch(takenDirection) {
            case NorthWest :
                nextBox = westeros.getMap()[this.currentBox.getX()-1][this.currentBox.getY()+1];
                break;
            case North :
                nextBox = westeros.getMap()[this.currentBox.getX()][this.currentBox.getY()+1];
                break;
            case NorthEast :
                nextBox = westeros.getMap()[this.currentBox.getX()+1][this.currentBox.getY()+1];
                break;
            case East :
                nextBox = westeros.getMap()[this.currentBox.getX()+1][this.currentBox.getY()];
                break;
            case SouthEast :
                nextBox = westeros.getMap()[this.currentBox.getX()+1][this.currentBox.getY()-1];
                break;
            case South :
                nextBox = westeros.getMap()[this.currentBox.getX()][this.currentBox.getY()-1];
                break;
            case SouthWest :
                nextBox = westeros.getMap()[this.currentBox.getX()-1][this.currentBox.getY()-1];
                break;
            case West :
                nextBox = westeros.getMap()[this.currentBox.getX()-1][this.currentBox.getY()];
                break;
        }
        return nextBox;
    }
    
    public void move(String message) throws IOException, InterruptedException {
        //etape 1 : recuperer les directions de deplacement envisageables
        ArrayList<Direction> possibleDirections = possibleDirections();
        
        //etape 2 : se deplacer le long d'une direction
        int range = determineStepNumbers();
        if (!possibleDirections.isEmpty()) {
            //choisir une direction au hasard
            int randomIndex = (int) (Math.random() * (possibleDirections.size() - 1));
            Direction takenDirection = possibleDirections.get(randomIndex);
            
            //tant que la case suivante est vide et à portée, le perso se déplace + action de se déplacer dans movmentConsequences (perte de stamina, gain de pv, xp...)
            Box nextBox = currentBox;
            while (canMove(takenDirection) && (nextBox = stepMove(takenDirection)).isEmpty() && range-- > 0) {//ET logique : si le premier test est faux, ne fait pas le second et ne décrémente pas range (normalement)
                westeros.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(null);
                currentBox = nextBox;
                westeros.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(this);
                
                displayConsole(message, westeros, 4);
                
                movmentConsequences();
            }
            
            lastDirection = takenDirection;
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
    
    protected abstract void movmentConsequences();

    protected abstract void meet(Human character, int remainingBoxes) throws IOException;
    
    protected abstract void meet(WhiteWalker character, int remainingBoxes) throws IOException;

    protected abstract void attack(Character character) throws IOException;
    
}

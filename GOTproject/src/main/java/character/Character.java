package character;

import java.io.IOException;
import java.util.ArrayList;

import gameplay.DiceResult;
import map.Box;
import map.Direction;
import map.Westeros;

public abstract class Character {
    protected int life = 100;
    protected int power;
    //protected int dodge;
    
    static protected int MAX_STEP_NUMBER;
    static protected int CRITIC_SUCCESS_LEVEL;
    static protected int FAILURE_LEVEL;

    protected Box currentBox;
    protected Direction lastDirection;

    public Character() {
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
/*
    public int getDodge() {
            return dodge;
    }

    public void setDodge(int dodge) {
            this.dodge = (this.dodge < 0) ? 0 : dodge;
    }
*/
    protected  abstract void move(Box currentBox);
        //possibleDirections()
        //recuperer MAX_STEP_NUMBER
        //si stamina basse, direction rapprochant le plus de safezone
        //sinon, direction aléatoire parmi les possibles
        //portée = determineStepNumbers()
        //tant que case libre dans direction et portee ok, "avancer" (actualiser futures coordonn�es)
            //Si en dehors de sa SafeZone --> perte de PE
            //Sinon --> r�cup�re 3PE par case 
            //Si PV pas au max, r�cup�re 1PV
        //d�placer le personnage : changer ses coordonn�es + actualiser ancienne et nouvelle case
        //scanner les alentours et meet si perso trouvé
        //Fin de tour XP++

    protected abstract void meet(Human character, int remainingBoxes) throws IOException;
    
    protected abstract void meet(WhiteWalkers character, int remainingBoxes);
        //Si m�me famille, ajout de PV �quitablement en fonction du nombre de cases qu'il reste � parcourir pour la personne en mouvement
        //Si m�me faction, ajout de XP distribu� �quitablement en fct nb cases
        //Si faction diff�rente et en dehors de safezone, combat tour par tour => method attaquer (d�pend des familles)
        //SI plus de PE : 
            //Si != factions : individu sans PE meurt
            //Si mm faction : 1 seul PE d'aide transf�r�
            //Si mm famille : moiti� des PE partag�

    protected abstract void attack(Character character);
        // Puissance d'attaque et chance d'esquive de l'adversaire � prendre en compte
        //Lancer de d�s (seuils de % en fonction de la famille du personnage lancant l'attaque)
            //Si SUCCES CRITIC : attaque sp�ciale 
            //Si SUCCES : adversaire perd des points de vie en fonction de attaquant (adversaire n'essaye pas d'esquiver (dodge) ?)
                //Si PV adversaire tombe � 0 et attaquant != marcheur blanc, adversaire meurt et est supprime de la map et XP adversaire transf�r� � attquant 
                //Si PV adversaire tombe � 0 et attaquant == marcheur blanc, adversaire meurt et arrivee d'un nouveau marcheur blanc
                //Sinon, adversaire attack et ainsi de suite 
            //Si ECHEC : 
                //L'attaquant a rat� son attaque, rien ne se passe
            //echec critique (?) perd pv ou exp�erience?
    
    /** 
    * Rolls a dice
    * @return diceResult   result of the throw
    */ 
    public  DiceResult rollDice() {
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
    
    protected int determineStepNumbers() {
        switch(this.rollDice()) {
            case CRITIC_SUCCESS:
                return MAX_STEP_NUMBER;
            case SUCCESS: 
                return (int) (0.5*MAX_STEP_NUMBER);
            default: 
                return (int) (0.25*MAX_STEP_NUMBER);
        }	
    }
    
    protected ArrayList<Direction> possibleDirections(int maxXMap, int maxYMap) {
        ArrayList<Direction> directions = new ArrayList<>();

        if(currentBox.getX() == 0 && currentBox.getY()==0) {
                directions.add(Direction.East);
                directions.add(Direction.North);
                directions.add(Direction.NorthEast);
        }
        else if (currentBox.getX() == maxXMap && currentBox.getY() ==0) {
                directions.add(Direction.NorthWest);
                directions.add(Direction.West);
                directions.add(Direction.North);
        }
        else if(currentBox.getY() == maxYMap && currentBox.getX() == 0) {
                directions.add(Direction.South);
                directions.add(Direction.SouthEast);
                directions.add(Direction.East);
        }
        else if(currentBox.getY() == maxYMap && currentBox.getX() == maxXMap) {
                directions.add(Direction.South);
                directions.add(Direction.SouthWest);
                directions.add(Direction.West);
        }
        else if(currentBox.getX()==maxXMap) {
                directions.add(Direction.South);
                directions.add(Direction.SouthWest);
                directions.add(Direction.West);
                directions.add(Direction.North);
                directions.add(Direction.NorthWest);			
        }
        else if (currentBox.getX() == 0) {
                directions.add(Direction.South);
                directions.add(Direction.SouthEast);
                directions.add(Direction.East);
                directions.add(Direction.North);
                directions.add(Direction.NorthEast);			
        }
        else if(currentBox.getY()==0) {
                directions.add(Direction.West);
                directions.add(Direction.NorthWest);	
                directions.add(Direction.East);
                directions.add(Direction.North);
                directions.add(Direction.NorthEast);
        }
        else if(currentBox.getY() == maxYMap) {
                directions.add(Direction.South);
                directions.add(Direction.SouthWest);
                directions.add(Direction.West);
                directions.add(Direction.SouthEast);
                directions.add(Direction.East);
        }
        else {
                directions.add(Direction.South);
                directions.add(Direction.SouthWest);
                directions.add(Direction.West);
                directions.add(Direction.SouthEast);
                directions.add(Direction.East);
                directions.add(Direction.North);
                directions.add(Direction.NorthEast);	
                directions.add(Direction.NorthWest);
        }

        return directions;
    }

    protected ArrayList<Direction> possibleDirections_alternative() {
        ArrayList<Direction> list = new ArrayList();

        int x = currentBox.getX(), y = currentBox.getY();
        int xMax = Westeros.getWidth(), yMax = Westeros.getHeight();
        Box[][] map = new Box[xMax][yMax];
        //Westeros.getMap(map);

        if (x+1 < xMax && map[x+1][y].isEmpty()) {
            list.add(Direction.North);
        }
        if (x+1 < xMax && y+1 < yMax && map[x+1][y+1].isEmpty()) {
            list.add(Direction.NorthEast);
        }
        if (y+1 < xMax && map[x][y+1].isEmpty()) {
            list.add(Direction.East);
        }
        if (x-1 >= 0 && y+1 < yMax && map[x-1][y+1].isEmpty()) {
            list.add(Direction.SouthEast);
        }
        if (x-1 >= 0 && map[x-1][y].isEmpty()) {
            list.add(Direction.South);
        }
        if (x-1 >= 0 && y-1 >= 0 && map[x-1][y-1].isEmpty()) {
            list.add(Direction.SouthWest);
        }
        if (y-1 >= 0 && map[x][y-1].isEmpty()) {
            list.add(Direction.West);
        }
        if (x+1 < xMax && y-1 >= 0 && map[x+1][y-1].isEmpty()) {
            list.add(Direction.NorthWest);
        }

        return list;
    }

}

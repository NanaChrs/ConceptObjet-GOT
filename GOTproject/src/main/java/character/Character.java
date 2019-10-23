package character;

import java.io.IOException;
import java.util.ArrayList;

import gameplay.DiceResult;
import map.Box;
import map.Direction;
import map.Westeros;

public abstract class Character {
    static protected int MAX_LIFE = 100;
    protected int life = 100;
    protected int power;
    protected boolean isAlive;
    //protected int dodge;
    
    protected Westeros gameBoard;
    static protected int MAX_STEP_NUMBER;
    static protected int CRITIC_SUCCESS_LEVEL;
    static protected int FAILURE_LEVEL;

    protected Box currentBox;
    protected Direction lastDirection;

    public Character() {
    	this.isAlive = true;
    }

    public void setMap(Westeros map) {
        this.gameBoard = map;
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
		if(life > 100) {
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
    
    public boolean isAlive() {
		return this.isAlive;
	}

    protected int determineStepNumbers() {
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

    protected ArrayList<Direction> possibleDirections_alternative(Box[][] map) {
        ArrayList<Direction> list = new ArrayList();

        int x = currentBox.getX(), xMax = Westeros.getWidth(),
            y = currentBox.getY(), yMax = Westeros.getHeight();

        /*
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
        
        //haut gauche: (0,0) ; bas droite : (max,max) => inversion des y
        if (y-1 >= 0 
                && map[x][y-1].isEmpty()) {
            list.add(Direction.North);
        }
        if (x+1 < xMax && y-1 >= 0 
                && map[x+1][y-1].isEmpty()) {
            list.add(Direction.NorthEast);
        }
        if (x+1 < xMax 
                && map[x+1][y].isEmpty()) {
            list.add(Direction.East);
        }
        if (x+1 < xMax && y+1 < yMax 
                && map[x+1][y+1].isEmpty()) {
            list.add(Direction.SouthEast);
        }
        if (y+1 >= 0 
                && map[x][y+1].isEmpty()) {
            list.add(Direction.South);
        }
        if (x-1 >= 0 && y+1 < yMax 
                && map[x-1][y+1].isEmpty()) {
            list.add(Direction.SouthWest);
        }
        if (x-1 >= 0 
                && map[x-1][y].isEmpty()) {
            list.add(Direction.West);
        }
        if (x-1 >= 0 && y-1 >= 0 
                && map[x-1][y-1].isEmpty()) {
            list.add(Direction.NorthWest);
        }

        return list;
    }

    protected Box stepMove(Box[][] map, Direction takenDirection) {
        Box nextBox = this.currentBox;
        switch(takenDirection) {
            case North :
                nextBox = map[this.currentBox.getX()+1][this.currentBox.getY()];
                break;
            case NorthEast :
                nextBox = map[this.currentBox.getX()+1][this.currentBox.getY()+1];
                break;
            case East :
                nextBox = map[this.currentBox.getX()+1][this.currentBox.getY()];
                break;
            case SouthEast :
                nextBox = map[this.currentBox.getX()+1][this.currentBox.getY()];
                break;
            case South :
                nextBox = map[this.currentBox.getX()+1][this.currentBox.getY()];
                break;
            case SouthWest :
                nextBox = map[this.currentBox.getX()+1][this.currentBox.getY()+1];
                break;
            case West :
                nextBox = map[this.currentBox.getX()+1][this.currentBox.getY()];
                break;
            case NorthWest :
                nextBox = map[this.currentBox.getX()+1][this.currentBox.getY()];
                break;
        }
        return nextBox;
    }
    
    protected void move() throws IOException {
        //etape 1 : recuperer les directions de deplacement envisageables
        //ArrayList<Direction>possibleDirections = possibleDirections(Westeros.getWidth(), Westeros.getHeight());
        ArrayList<Direction> possibleDirections = possibleDirections_alternative(gameBoard.getMap());
        
        //etape 2 : se deplacer le long d'une direction
        int range = determineStepNumbers();
        if (!possibleDirections.isEmpty()) {
            //choisir une direction au hasard
            int randomIndex = (int) (Math.random() * (possibleDirections.size() - 1));
            Direction takenDirection = possibleDirections.get(randomIndex);

            //tant que la case suivante est vide et à portée, le perso se déplace + action de se déplacer dans movmentConsequences (perte de stamina, gain de pv, xp...)
            Box nextBox = currentBox;
            while ((nextBox = stepMove(gameBoard.getMap(), takenDirection)).isEmpty() && range-- > 0) {//ET logique : si le premier test est faux, ne fait pas le second et ne décrémente pas range (normalement)
                gameBoard.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(null);
                currentBox = nextBox;
                gameBoard.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(this);

                movmentConsequences();
            }
        }

        //etape 3 : scan des environs et interaction avec persos des cases juxtaposées
        for (int x = currentBox.getX()-1; x <= currentBox.getX()+1; ++x) {
            for (int y = currentBox.getY()-1; x <= currentBox.getY()+1; ++y) {
                if (x != currentBox.getX() && y != currentBox.getY() && !gameBoard.getMap()[x][y].isEmpty() && !gameBoard.getMap()[x][y].isObstacle()) {
                    if (gameBoard.getMap()[x][y].getCharacter().getClass() == Human.class) {
                        meet((Human) gameBoard.getMap()[x][y].getCharacter(),range);
                    }
                    else {
                        meet((WhiteWalker) gameBoard.getMap()[x][y].getCharacter(),range);
                    }
                }
            }
        }
    }
    
    protected abstract void movmentConsequences();

    protected abstract void meet(Human character, int remainingBoxes) throws IOException;
    
    protected abstract void meet(WhiteWalker character, int remainingBoxes) throws IOException;
        //Si m�me famille, ajout de PV �quitablement en fonction du nombre de cases qu'il reste � parcourir pour la personne en mouvement
        //Si m�me faction, ajout de XP distribu� �quitablement en fct nb cases
        //Si faction diff�rente et en dehors de safezone, combat tour par tour => method attaquer (d�pend des familles)
        //SI plus de PE : 
            //Si != factions : individu sans PE meurt
            //Si mm faction : 1 seul PE d'aide transf�r�
            //Si mm famille : moiti� des PE partag�

    protected abstract void attack(Character character) throws IOException;
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


    
}

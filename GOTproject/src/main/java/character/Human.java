package character;

//import java.util.ArrayList;
import gameplay.DiceResult;
import java.io.IOException;

import gameplay.FileManager;
import gameplay.Safezone;
import java.util.ArrayList;
import map.Box;
import map.Direction;
import map.Westeros;
//import gameplay.DiceResult;
//import map.Box;
//import map.Direction;

public abstract class Human extends Character {
    protected String name;

    protected int level = 0; //augmentation des stats  
    protected int xp = 0;
    static protected int MAX_POWER;
    static protected int MAX_STAMINA;
    protected int stamina;
    protected Safezone safezone;
	
    public Human(String name) {
        super();
        this.name = name;
	}
	
    
    public static int getMaxStamina() {
        return MAX_STAMINA;
    }

    public static int getMaxPower() {
        return MAX_POWER;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = (this.stamina < 0) ? 0 : stamina;
    }
    
    protected void meet(WhiteWalkers ww, int remainingBoxes) {
        this.attack(ww);
    }

    public void meet(Human h, int remainingBoxes) throws IOException {
            FileManager.writeToLogFile("[MEET] "+ this.name +" from House "+ this.getClass().getSimpleName() + " met " + h.name + " from House "+ h.getClass().getSimpleName() +".");

            if(this.getClass() == h.getClass()) {
                    if(this.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ this.name + " is exausted (0 Stamina). "+ h.name + " gave him/her half of his/her : "+ h.getStamina()/2 + ".");
                            this.setStamina(h.getStamina()/2);
                    }
                    else if (h.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ h.name + " is exausted (0 Stamina). "+ this.name + " gave him/her half of his/her : "+ this.getStamina()/2 + ".");
                            h.setStamina(this.getStamina()/2);
                    }
                    else {
                            FileManager.writeToLogFile("[MEET] They both gained "+ remainingBoxes +" xp. And restored 1/4 of their HP.");
                            this.xp += remainingBoxes;
                            h.xp += remainingBoxes;
                            this.life += 25;
                            h.life += 25;
                    }
            }
            else if (this.getClass().getSuperclass() == h.getClass().getSuperclass()) {
                    if(this.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ this.name + " is exausted (0 Stamina). "+ h.name + " gave him/her half of his/her : "+ h.getStamina()/2 + ".");
                            this.setStamina(h.getStamina()/2);
                    }
                    else if (h.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ h.name + " is exausted (0 Stamina). "+ this.name + " gave him/her half of his/her : "+ this.getStamina()/2 + ".");
                            h.setStamina(this.getStamina()/2);
                    }
                    else {
                            FileManager.writeToLogFile("[MEET] They both gained "+ remainingBoxes +" xp. And restored 1/4 of their HP.");
                            this.life += 25;
                            h.life += 25;
                    }
            }
            else {
                    if(this.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ this.name + " is exausted (0 Stamina). "+ h.name + " killed him/her.");
                            this.life = 0;
                    }
                    else if (h.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ h.name + " is exausted (0 Stamina). "+ this.name + " killed him/her.");
                            h.life = 0;
                    }
                    else {
                            this.attack(h);
                    }
            }
    }

    @Override
    protected void move(Box currentBox) {
        ArrayList<Direction>possibleDirections = this.possibleDirections(Westeros.getWidth(), Westeros.getHeight());

        Direction takenDirection;
        //si etre vivant a endurance basse, prend la direction de safezone
        if (this.getStamina() < 0.10*Human.getMaxStamina()) {
            //takenDirection = Safezone direction
        }

        //sinon, tire aleatoirement directions
        else {
            int randomIndex = (int) (Math.random() * (possibleDirections.size() - 1));
            takenDirection = possibleDirections.get(randomIndex);
        }

        int range = this.determineStepNumbers();
        do {
            //tant que case libre dans direction et port�e ok, "avancer" (actualiser futures coordonn�es)
                //Si en dehors de sa SafeZone --> perte de PE
                //Sinon --> r�cup�re 3PE par case 
                //Si PV pas au max, r�cup�re 1PV
        } while (--range > 0);


        //d�placer le personnage et changer ses coordonn�es + actualiser ancienne et nouvelle case

        //scanner les alentours et interagir si autre perso trouv�

        //Fin de tour XP++
    }

	
    protected abstract void superAttack(Character character);
}

package character;

import java.util.ArrayList;
import map.Box;
import map.Direction;
import map.Westeros;

public class WhiteWalkers extends Character {
    static final protected int MAX_STEP_NUMBER = 6;
	static final protected int CRITIC_SUCESS_LEVEL = 95;
	static final protected int FAILURE_LEVEL = 30;

    public WhiteWalkers() {
        super();
        setPower(20);
        //setDodge(20);
    }

    @Override
    protected void attack(Character character) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void move(Box currentBox) {
        ArrayList<Direction>possibleDirections = this.possibleDirections(Westeros.getWidth(), Westeros.getHeight());

        int randomIndex = (int) (Math.random() * (possibleDirections.size() - 1));
        Direction takenDirection = possibleDirections.get(randomIndex);

        int range = this.determineStepNumbers();
        do {
            //tant que case libre dans direction et port�e ok, "avancer" (actualiser futures coordonn�es)
                //Si en dehors de sa SafeZone --> perte de PE
                //Sinon --> r�cup�re 3PE par case 
                //Si PV pas au max, r�cup�re 1PV
        } while (--range > 0);

        //d�placer le personnage et changer ses coordonn�es + actualiser ancienne et nouvelle case

        //scanner les alentours et interagir si autre perso trouv�
    }
    
    @Override
    protected void meet(WhiteWalkers character, int remainingBoxes) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void meet(Human character, int remainingBoxes) {
        // TODO Auto-generated method stub
    }
}

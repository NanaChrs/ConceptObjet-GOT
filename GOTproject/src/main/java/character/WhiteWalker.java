package character;

import java.util.ArrayList;
import map.Box;
import map.Direction;
import map.Westeros;

public class WhiteWalker extends Character {
    static final protected int MAX_STEP_NUMBER = 6;
	static final protected int CRITIC_SUCESS_LEVEL = 95;
	static final protected int FAILURE_LEVEL = 30;

    public WhiteWalker() {
        super();
        setPower(20);
        //setDodge(20);
    }

    @Override
    protected void movmentConsequences() {
        //si pv pas au max, recupère 1 pv? this.life++;
    }
    
    @Override
    protected void meet(WhiteWalker character, int remainingBoxes) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void meet(Human character, int remainingBoxes) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void attack(Character character) {
        // TODO Auto-generated method stub

    }
}

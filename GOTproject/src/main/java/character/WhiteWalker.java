package character;

import java.io.IOException;

import gameplay.FileManager;

public class WhiteWalker extends Character {
    static final protected int MAX_STEP_NUMBER = 6;
	static final protected int CRITIC_SUCESS_LEVEL = 95;
	static final protected int FAILURE_LEVEL = 40;
	static protected int NB_WHITEWALKERS = 0; //Attribut statique qui a du sens
	
    public WhiteWalker() {
        setPower(20);
        NB_WHITEWALKERS++;
        //setDodge(20);
    }

    @Override
    protected void movmentConsequences() {
        //si pv pas au max, recupère 1 pv? this.life++;
    }
    
    @Override
    protected void meet(WhiteWalker c, int remainingBoxes) throws IOException {
        // TODO Auto-generated method stub
    	c.setLife(c.life +25);
    	this.setLife(this.life+25);
    	FileManager.writeToLogFile("[MEET] 2 whitwalkers met each other. They gained 1/4 of their HP. They are now at :");

    }

    @Override
    protected void meet(Human character, int remainingBoxes) throws IOException {
    	FileManager.writeToLogFile("[MEET] A whitewalker met "+ character.name + " from House "+ character.getClass().getSimpleName() +".");
    	
    	do {
    		this.attack(character);
    		if (character.isAlive()) character.attack(this);
    	} while(this.isAlive() && character.isAlive());
    	
    	if(!this.isAlive()) {
    		FileManager.writeToLogFile("[DEATH] The whitewalker is dead and "+ character.name+" from House "+ character.getClass().getSimpleName()+" gained 100 XP and 25 HP.");
    		character.xp += 100;
    		character.setLife(character.life+25);
    	}
    	else {
    		FileManager.writeToLogFile("[DEATH] The whitewalker killed "+ character.name+" from House "+ character.getClass().getSimpleName()+".");
    	}
    }

    @Override
    protected void attack(Character c) throws IOException {//Exception in thread "main" java.lang.ClassCastException: character.WhiteWalker cannot be cast to character.Human
        // TODO Auto-generated method stub
    		switch (this.rollDice()) {
    		case CRITIC_SUCCESS:
    		case SUCCESS:
    			Human h = (Human) c;
    			c.setLife(c.life - this.power);
    			FileManager.writeToLogFile("[ATTACK] The whitewalker attacked successfully. "+ h.name+" from House "+ h.getClass().getSimpleName()+" lost "+this.power +" HP and has now "+ c.life+" hp.");
    			break;
    		default:
    			FileManager.writeToLogFile("[ATTACK] The whitewalker missed his attack! Nothing happened.");
    			break;
    		}
    }
}

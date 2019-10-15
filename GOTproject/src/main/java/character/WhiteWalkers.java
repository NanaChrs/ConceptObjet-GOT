package character;

import map.Box;

public class WhiteWalkers extends Character {

	static final protected int CRITIC_SUCESS_LEVEL = 95;
	static final protected int FAILURE_LEVEL = 30;
	static final protected int MAX_STEP_NUMBER = 6;

	
	public WhiteWalkers() {
		super();
		setPower(20);
		setStamina(75);
		setDodge(20);
	}
	
	@Override
	protected void attack(Character character) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	protected void meet(Character character) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void move(Box currentBox, int maxStep, int maxXMap, int maxYMap) {
		// TODO Auto-generated method stub
		
	}

}

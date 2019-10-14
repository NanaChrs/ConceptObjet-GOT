package character;

import map.Box;

public class WhiteWalkers extends Character {
	
	public WhiteWalkers() {
		super();
		setPower(20);
		setStamina(75);
		setDodge(20);
		setMaxStepNumber(3);
	}
	
	@Override
	protected void attack(Character character) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void move(Box currentBox, int maxStep) {
		// TODO Auto-generated method stub
		
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

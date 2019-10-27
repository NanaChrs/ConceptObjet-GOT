package character;

import java.io.IOException;

import gameplay.FileManager;

public class Lannister extends Southerner {	

	//static final protected int MAX_STEP_NUMBER = 4;
	static final protected int CRITIC_SUCCESS_LEVEL= 65;
	static final protected int FAILURE_LEVEL = 5;
	
	public Lannister(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void superAttack(Character c) throws IOException {
		
		c.life = (int)this.life/3;
		this.setLife(this.life - (int)this.life/3);
		
		FileManager.writeToLogFile("[SUPERATTACK] A Lannister always pays his debts ! "+ this.name+" gave 1/3 of his life.");
	}

}

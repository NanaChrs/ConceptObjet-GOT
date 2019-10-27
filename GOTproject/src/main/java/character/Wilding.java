package character;

import java.io.IOException;

import gameplay.FileManager;

public class Wilding extends Northerner {
	//static final protected int MAX_STEP_NUMBER = 5;
	static final protected int CRITIC_SUCCESS_LEVEL=75;
	static final protected int FAILURE_LEVEL = 10;

	public Wilding(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void superAttack(Character character) throws IOException {		
		this.setLife(this.life+10);
		character.setLife((int) (character.life-2*(this.power+(int)this.xp*0.3)));

		FileManager.writeToLogFile("[SUPER ATTACK] Wilding just drank Giant milk ! He gained 10HP and his attack is doubled !");
	}
}

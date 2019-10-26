package character;

import java.io.IOException;

import gameplay.FileManager;

public class Stark extends Northerner {

	static final protected int MAX_STEP_NUMBER = 8;
	static final protected int CRITIC_SUCCESS_LEVEL= 80;
	static final protected int FAILURE_LEVEL = 15;

	public Stark(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void superAttack(Character character) {
		// TODO Auto-generated method stub
		character.setLife((int)character.life/2);
		try {
			FileManager.writeToLogFile("[SUPERATTACK] The wolves of Winterfell attack ! The opponent loses half his HP");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// PV adversaire divis� par 2
	}
}

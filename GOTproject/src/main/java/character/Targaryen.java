package character;

import java.io.IOException;

import gameplay.FileManager;

public class Targaryen extends Southerner {

	static final protected int CRITIC_SUCCESS_LEVEL= 95;

	static final protected int FAILURE_LEVEL = 25;

	public Targaryen(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void superAttack(Character character) {
		// TODO Auto-generated method stub
		
		character.setLife(0);
		try {
			FileManager.writeToLogFile("[SUPERATTACK] The dragons burns the opponent to the ground ! The opponent doesn't survive.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ONE SHOT
	}
}

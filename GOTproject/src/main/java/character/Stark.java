package character;

import java.io.IOException;

import gameplay.FileManager;

public class Stark extends Northerner {
    static final protected int MAX_STEP_NUMBER = 8;
    static final protected int CRITIC_SUCCESS_LEVEL= 80;
    static final protected int FAILURE_LEVEL = 15;
    static protected int NB_STARKS = 0;  //Attribut statique qui a du sens

    public Stark(String string) {
        super(string);
        NB_STARKS++;
    }

    @Override
    protected void superAttack(Character character) throws IOException, InterruptedException {
        character.setLife((int)character.life/2);
        FileManager.writeToLogFile("[SUPERATTACK] The wolves of Winterfell attack ! The opponent loses half his HP");
    }
}

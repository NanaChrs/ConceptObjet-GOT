package character;

import java.io.IOException;

import gameplay.FileManager;

public class Targaryen extends Southerner {
    static final protected int CRITIC_SUCCESS_LEVEL= 95;

    static final protected int FAILURE_LEVEL = 25;
    static protected int NB_TARGARYENS = 0;  //Attribut statique qui a du sens

    public Targaryen(String string) {
        super(string);
        NB_TARGARYENS++;
    }

    @Override
    protected void superAttack(Character character) throws IOException, InterruptedException {		
        character.setLife(0);
        FileManager.writeToLogFile("[SUPERATTACK] The dragons burns the opponent to the ground ! The opponent doesn't survive.");
    }
}

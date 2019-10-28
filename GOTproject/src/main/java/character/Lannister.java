package character;

import java.io.IOException;

import gameplay.FileManager;

public class Lannister extends Southerner {	

    //static final protected int MAX_STEP_NUMBER = 4;
    static final protected int CRITIC_SUCCESS_LEVEL= 65;
    static final protected int FAILURE_LEVEL = 5;
    static protected int NB_LANNISTERS = 0;  //Attribut statique qui a du sens

    public Lannister(String name) {
        super(name);
        NB_LANNISTERS++;
    }

    @Override
    protected void superAttack(Character c) throws IOException, InterruptedException {
        c.setLife((int)this.life/3);
        this.setLife(this.life - (int)this.life/3);

        FileManager.writeToLogFile("[SUPERATTACK] A Lannister always pays his debts ! "+ this.name+" gave 1/3 of his life.");
    }

}

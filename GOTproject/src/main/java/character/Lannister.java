package character;

import java.io.IOException;

import gameplay.FileManager;

public class Lannister extends Southerner {
    //statistiques
    protected static int NB_LANNISTERS = 0;  //Attribut statique qui a du sens
    
    //Attributs - Instance définie par :
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD= 65;
    protected final static int FAILURE_THRESHOLD = 5;

    //Constructeur - naissance de l'instance
    public Lannister(String name) {
        super(name, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
        NB_LANNISTERS++;
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void superAttack(Character c) throws IOException, InterruptedException {
        c.reduceLife((int)(this.maxLife/3),DamageSource.Battle,this);
        this.reduceLife((int)(this.life/3),DamageSource.Battle,this);//pas mortel

        FileManager.writeToLogFile("[SUPERATTACK] A Lannister always pays his debts ! "+ this.name+" gave 1/3 of his life.");
    }
}

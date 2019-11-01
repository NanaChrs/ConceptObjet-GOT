package character;

import java.io.IOException;

import gameplay.FileManager;

public class Stark extends Northerner {
    //statistiques
    protected static int NB_STARKS = 0;  //Attribut statique qui a du sens
    
    //Attributs - Instance définie par :
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD= 60;
    protected final static int FAILURE_THRESHOLD = 15;

    //Constructeur - naissance de l'instance
    public Stark(String name) {
        super(name, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
        NB_STARKS++;
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void superAttack(Character character) throws IOException, InterruptedException {
        character.reduceLife((int)(this.maxLife/2),DamageSource.Battle,this);
        FileManager.writeToLogFile("[SUPERATTACK] The wolves of Winterfell attack ! The opponent loses half his HP");
    }
}

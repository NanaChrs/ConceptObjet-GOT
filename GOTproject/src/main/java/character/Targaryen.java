package character;

import java.io.IOException;

import gameplay.FileManager;

public class Targaryen extends Southerner {
    //statistiques
    protected static int NB_TARGARYENS = 0;  //Attribut statique qui a du sens
    
    //Attributs - Instance définie par :
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD= 93;
    protected final static int FAILURE_THRESHOLD = 25;

    //Constructeur - naissance de l'instance
    public Targaryen(String name) {
        super(name, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
        NB_TARGARYENS++;
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void superAttack(Character character) throws IOException, InterruptedException {		
        character.reduceLife(character.life,DamageSource.Battle,this);
        FileManager.writeToLogFile("[SUPERATTACK] The dragons burns the opponent to the ground ! The opponent doesn't survive.");
    }
}

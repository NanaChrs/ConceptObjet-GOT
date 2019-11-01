package character;

import java.io.IOException;

import gameplay.FileManager;

public class Wilding extends Northerner {
    //statistiques
    protected static int NB_WILDINGS = 0;  //Attribut statique qui a du sens
    
    //Attributs - Instance définie par :
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD= 75;
    protected final static int FAILURE_THRESHOLD = 10;

    //Constructeur - naissance de l'instance
    public Wilding(String name) {
        super(name, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
        NB_WILDINGS++;
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void superAttack(Character character) throws IOException, InterruptedException {		
        this.addLife(10);
        character.reduceLife((int)1.5*this.power,DamageSource.Battle,this);

        FileManager.writeToLogFile("[SUPER ATTACK] Wilding just drank Giant milk ! He gained 10HP and his attack is doubled !");
    }
}

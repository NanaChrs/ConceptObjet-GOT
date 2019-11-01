package character;

import java.io.IOException;

import gameplay.FileManager;

public class Lannister extends Southerner {
    //Attributs - Instance définie par :
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD= 65;
    protected final static int FAILURE_THRESHOLD = 5;

    //Constructeur - naissance de l'instance
    public Lannister(String name) {
        super(name, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
    }
    
    public static void displayStatics() {
        String display = "\n\nClasse Lannister - hérite de Southerner";
        display += "\nPallier de succès critique : "+CRITICAL_SUCCESS_THRESHOLD;
        display += "\nPallier d'échec : "+FAILURE_THRESHOLD;
        
        System.out.println(display);
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void superAttack(Character c) throws IOException, InterruptedException {
        c.reduceLife((int)(this.maxLife/2),DamageSource.Battle,this);
        this.reduceLife((int)(this.life/6),DamageSource.Battle,this);//pas mortel

        FileManager.writeToLogFile("[SUPERATTACK] A Lannister always pays his debts ! "+ this.name+" gave 1/6 of his life.");
    }
}

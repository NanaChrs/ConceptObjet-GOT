package character;

import java.io.IOException;

import gameplay.FileManager;

public class Wilding extends Northerner {
    //Attributs - Instance définie par :
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD= 80;
    protected final static int FAILURE_THRESHOLD = 30;

    //Constructeur - naissance de l'instance
    public Wilding(String name) {
        super(name, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
    }

    //Affichage des caractéristiques statiques de la classe
    public static void displayStatics() {
        String display = "\n\nClasse Wilding - hérite de Northerner";
        display += "\nPalier de succès critique : "+CRITICAL_SUCCESS_THRESHOLD;
        display += "\nPalier d'échec : "+FAILURE_THRESHOLD;
        
        System.out.println(display);
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void superAttack(Character character) throws IOException, InterruptedException {		
        this.addLife(10);
        character.reduceLife((int)1.5*this.power,DamageSource.Battle,this);

        FileManager.writeToLogFile("SUPER ATTACK",this.getFullName()+" just drank Giant milk ! He gained 10HP and his next attack is multiplied by 1,5 !");
    }
}

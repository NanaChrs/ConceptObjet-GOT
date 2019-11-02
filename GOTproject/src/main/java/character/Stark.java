package character;

import java.io.IOException;

import gameplay.FileManager;

public class Stark extends Northerner {
    //Attributs - Instance définie par :
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD= 65;
    protected final static int FAILURE_THRESHOLD = 15;

    //Constructeur - naissance de l'instance
    public Stark(String name) {
        super(name, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
    }
    
    //Affichage des caractéristiques statiques de la classe
    public static void displayStatics() {
        String display = "\n\nClasse Stark - hérite de Northerner";
        display += "\nPalier de succès critique : "+CRITICAL_SUCCESS_THRESHOLD;
        display += "\nPalier d'échec : "+FAILURE_THRESHOLD;
        
        System.out.println(display);
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void superAttack(Character character) throws IOException, InterruptedException {
        character.reduceLife((int)(this.maxLife/3),DamageSource.Battle,this);
        FileManager.writeToLogFile("SUPER ATTACK","The wolves of Winterfell attack ! The opponent loses 1/3 of his HP");
    }
}

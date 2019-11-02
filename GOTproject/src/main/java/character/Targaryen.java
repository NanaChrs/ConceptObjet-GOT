package character;

import java.io.IOException;

import gameplay.FileManager;

public class Targaryen extends Southerner {
    //Attributs - Instance définie par :
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD= 95;
    protected final static int FAILURE_THRESHOLD = 30;

    //Constructeur - naissance de l'instance
    public Targaryen(String name) {
        super(name, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
    }

    //Affichage des caractéristiques statiques de la classe
    public static void displayStatics() {
        String display = "\n\nClasse Targaryen - hérite de Southerner";
        display += "\nPalier de succès critique : "+CRITICAL_SUCCESS_THRESHOLD;
        display += "\nPalier d'échec : "+FAILURE_THRESHOLD;
        
        System.out.println(display);
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void superAttack(Character character) throws IOException, InterruptedException {		
        character.reduceLife(character.life,DamageSource.Battle,this);
        FileManager.writeToLogFile("SUPER ATTACK","The dragons burns the opponent to the ground ! The opponent doesn't survive.");
    }
}

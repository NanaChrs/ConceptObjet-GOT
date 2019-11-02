package character;

public abstract class Southerner extends Human {
    //Attributs - Instance définie par :
    //  ses capacités et son potentiel
    protected final static int MAX_STAMINA = 100; 
    protected final static int INITIAL_POWER = 20; 
    protected final static int MAX_POWER = 120; 

    //Constructeur - naissance de l'instance
    public Southerner(String name, int criticalSuccessThreshold, int failureThreshold) {
        super(name, MAX_POWER, INITIAL_POWER, MAX_STAMINA, criticalSuccessThreshold, failureThreshold);
    }
    
    //Affichage des caractéristiques statiques de la classe
    public static void displayStatics() {
        String display = "\n\nClasse Southerner - hérite de Human";
        display += "\nEndurance max : "+MAX_STAMINA;
        display += "\nDégâts initiaux : "+INITIAL_POWER;
        display += "\nDégâts max : "+MAX_POWER;
        
        System.out.println(display);
    }
}

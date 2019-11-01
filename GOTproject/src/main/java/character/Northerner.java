package character;

public abstract class Northerner extends Human {
    //Attributs - Instance définie par :
    //  ses capacités et son potentiel
    protected final static int MAX_STAMINA = 80; //Attribut statique qui a du sens
    protected final static int INITIAL_POWER = 30; //Attribut statique qui a du sens
    protected final static int MAX_POWER = 100;  //Attribut statique qui a du sens

    //Constructeur - naissance de l'instance
    public Northerner(String name, int criticalSuccessThreshold, int failureThreshold) {
        super(name, MAX_POWER, INITIAL_POWER, MAX_STAMINA, criticalSuccessThreshold, failureThreshold);
    }
    
    public static void displayStatics() {
        String display = "\n\nClasse Northerner - hérite de Human";
        display += "\nEndurance max : "+MAX_STAMINA;
        display += "\nDégâts initiaux : "+INITIAL_POWER;
        display += "\nDégâts max : "+MAX_POWER;
        
        System.out.println(display);
    }
}

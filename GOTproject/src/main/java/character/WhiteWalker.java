package character;

import java.io.IOException;

import gameplay.FileManager;
import static gameplay.UserInterface.displayConsole;

public class WhiteWalker extends Character {
    //Attributs - Instance définie par :
    
	//  sa vie et les dégâts qu'il fait
    protected final static int LIFE = 150;//équivaut au INITIAL_LIFE de human
    protected final static int POWER = 45;//équivaut aux MAX_POWER de southerner et northerner
    
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD = 100;
    protected final static int FAILURE_THRESHOLD = 50;

    //Constructeur - naissance de l'instance
    public WhiteWalker() {
        super(LIFE, POWER, POWER, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
    }

    //Affichage des caractéristiques statiques de la classe
    public static void displayStatics() {
        String display = "\n\nClasse WhiteWalker - hérite de Character";
        display += "\nVie de départ : "+LIFE;
        display += "\nDégâts de départ : "+POWER;
        display += "\nPalier de succès critique : "+CRITICAL_SUCCESS_THRESHOLD;
        display += "\nPalier d'échec : "+FAILURE_THRESHOLD;
        
        System.out.println(display);
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void movmentConsequences() throws InterruptedException {
        //recupere pas de vie : pas réellement vivant
        this.currentBox.setWhiteWalkerWeather();//instaure climat mortel sur case pour x tours
    }
    
    @Override
    protected void meet(WhiteWalker c, int remainingBoxes) throws IOException, InterruptedException {
        //Affichage console et écriture logs : pas de changement de stats
    	displayConsole("Le marcheur blanc rencontre un semblable", westeros, 2);
    	FileManager.writeToLogFile(1,"MEET","2 whitewalkers met each other.");
    }

    @Override
    protected void meet(Human character, int remainingBoxes) throws IOException, InterruptedException {
        //Affichage console et écriture logs 
    	displayConsole("Le marcheur blanc attaque " + character.getFullName() + " lv" + character.level + " (vie : " + character.life + ")", westeros, 2);
    	FileManager.writeToLogFile(1,"MEET","A whitewalker ("+this.life+" HP and "+this.power+" PP) met "+ character.getFullName() + " (lv"+character.level+" : "+character.life+" HP and "+character.power+" PP).");
    	
    	//Combat
    	do {
            this.attack(character);
            if (character.isAlive()) character.attack(this);
    	} while(this.isAlive() && character.isAlive());
    	
    	//Si WW a perdu
    	if(!this.isAlive()) {
            FileManager.writeToLogFile("DEATH",character.getFullName()+" killed the Whitewalker : He/she gain 100 XP and 25 HP.");
            character.addXp(100);
            character.addLife(25);
    	}
    	
    	//Si humain a perdu
    	else {
            FileManager.writeToLogFile("DEATH","The whitewalker killed "+character.getFullName()+".");
    	}
    }

    @Override
    protected void attack(Character c) throws IOException, InterruptedException {
        switch (this.rollDice()) {
            case CRITICAL_SUCCESS: //pas de super attaque car WW
            case SUCCESS:
                Human h = (Human) c;
                c.reduceLife(this.power,DamageSource.Battle,this);
                FileManager.writeToLogFile("ATTACK","WhiteWalker :\t Successful attack : "+ h.getFullName()+" lost "+this.power +" HP and has now "+ c.life+" hp.");
                break;
            default:
                FileManager.writeToLogFile("ATTACK","WhiteWalker :\t Missed attack! Nothing happened.");
                break;
        }
    }
}

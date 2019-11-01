package character;

import java.io.IOException;

import gameplay.FileManager;
import static gameplay.UserInterface.displayConsole;

public class WhiteWalker extends Character {
    //statistiques
    protected static int NB_WHITEWALKERS = 0; //Attribut statique qui a du sens
    
    //Attributs - Instance définie par :
    //  sa vie et les dégâts qu'il fait
    protected final static int LIFE = 200;//équivaut au INITIAL_LIFE de human
    protected final static int POWER = 50;//équivaut aux MAX_POWER de southterner et northterner
    
    //  sa chance
    protected final static int CRITICAL_SUCCESS_THRESHOLD = 95;
    protected final static int FAILURE_THRESHOLD = 40;

    //Constructeur - naissance de l'instance
    public WhiteWalker() {
        super(LIFE, POWER, POWER, CRITICAL_SUCCESS_THRESHOLD, FAILURE_THRESHOLD);
        NB_WHITEWALKERS++;
    }

    //Méthodes protected - définition d'actions
    @Override
    protected void movmentConsequences() throws InterruptedException {
        //recupere pas de vie : pas réellement vivant
        this.currentBox.setWhiteWalkerWeather();//instaure climat mortel sur case pour x tours
    }
    
    @Override
    protected void meet(WhiteWalker c, int remainingBoxes) throws IOException, InterruptedException {
        displayConsole("Le marcheur blanc rencontre un semblable", westeros, 2);
    	FileManager.writeToLogFile("\n[MEET] 2 whitewalkers met each other. They gained 1/4 of their HP. They are now at :");
        
    	c.addLife(25);
    	this.addLife(25);
    }

    @Override
    protected void meet(Human character, int remainingBoxes) throws IOException, InterruptedException {
        displayConsole("Le marcheur blanc attaque " + character.getFullName() + " lv" + character.level + " (vie : " + character.life + ")", westeros, 2);
    	FileManager.writeToLogFile("\n[MEET] A whitewalker met "+ character.name + " from House "+ character.getClass().getSimpleName() +".");
    	
    	do {
            this.attack(character);
            if (character.isAlive()) character.attack(this);
    	} while(this.isAlive() && character.isAlive());
    	
    	if(!this.isAlive()) {
            FileManager.writeToLogFile("[DEATH] The whitewalker is dead and "+ character.name+" from House "+ character.getClass().getSimpleName()+" gained 100 XP and 25 HP.");
            character.addXp(100);
            character.addLife(25);
    	}
    	else {
            FileManager.writeToLogFile("[DEATH] The whitewalker killed "+ character.name+" from House "+ character.getClass().getSimpleName()+".");
    	}
    }

    @Override
    protected void attack(Character c) throws IOException, InterruptedException {
        switch (this.rollDice()) {
            case CRITICAL_SUCCESS:
            case SUCCESS:
                Human h = (Human) c;
                c.reduceLife(this.power,DamageSource.Battle,this);
                FileManager.writeToLogFile("[ATTACK] The whitewalker attacked successfully. "+ h.name+" from House "+ h.getClass().getSimpleName()+" lost "+this.power +" HP and has now "+ c.life+" hp.");
                break;
            default:
                FileManager.writeToLogFile("[ATTACK] The whitewalker missed his attack! Nothing happened.");
                break;
        }
    }
}

package character;

import java.io.IOException;

import gameplay.FileManager;
import gameplay.Safezone;

public abstract class Human extends Character {
    protected String name;

    protected int level = 0; //augmentation des stats  
    protected int xp = 0;
    static protected int MAX_POWER;
    static protected int MAX_STAMINA;
    static protected int LOW_STAMINA;
    protected int stamina;
    protected Safezone safezone;
	
    public Human(String name) {
        this.name = name;
    }
	
    
    public static int getMaxStamina() {
        return MAX_STAMINA;
    }

    public static int getMaxPower() {
        return MAX_POWER;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = (stamina < 0) ? 0 : stamina;
    }
    
    protected void movmentConsequences() {
        //Si en dehors de sa SafeZone --> perte de PE
        //Sinon --> r�cup�re 3PE
        
        if (life < MAX_LIFE) life++;
        xp++;
    }
    
    @Override
    protected void meet(WhiteWalker ww, int remainingBoxes) throws IOException {
    	FileManager.writeToLogFile("[MEET] "+ this.name +" from House "+ this.getClass().getSimpleName() + " met a White Walker.");
    	
    	do {
    		this.attack(ww);
    		if (ww.isAlive()) ww.attack(this);
    	} while(this.isAlive() && ww.isAlive());
    	
    	if(!this.isAlive()) {
    		FileManager.writeToLogFile("[DEATH] " + this.name + " from House " + this.getClass().getSimpleName() + " is killed by the whitewalker.");
    	}
    	else {
    		FileManager.writeToLogFile("[DEATH] The whitewalker is killed by "+ this.name+" from House "+ this.getClass().getSimpleName()+".");
    		this.xp += 100;
    		this.setLife(this.life+25);
    	}
    }

    @Override
    public void meet(Human h, int remainingBoxes) throws IOException {
            FileManager.writeToLogFile("[MEET] "+ this.name +" from House "+ this.getClass().getSimpleName() + " met " + h.name + " from House "+ h.getClass().getSimpleName() +".");

            if(this.getClass() == h.getClass()) {
                    if(this.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ this.name + " is exhausted (0 Stamina). "+ h.name + " gave him/her half of his/her : "+ h.getStamina()/2 + ".");
                            this.setStamina(h.getStamina()/2);
                    }
                    else if (h.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ h.name + " is exhausted (0 Stamina). "+ this.name + " gave him/her half of his/her : "+ this.getStamina()/2 + ".");
                            h.setStamina(this.getStamina()/2);
                    }
                    else {
                    	int xp;
                    	if(remainingBoxes <0) xp = 1;
                    	else xp = remainingBoxes;
                            FileManager.writeToLogFile("[MEET] They both gained "+ xp +" xp. And restored 1/4 of their HP.");
                            this.xp += xp;
                            h.xp += xp;
                            this.life += 25;
                            h.life += 25;
                    }
            }
            else if (this.getClass().getSuperclass() == h.getClass().getSuperclass()) {
                    if(this.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ this.name + " is exausted (0 Stamina). "+ h.name + " gave him/her half of his/her : "+ h.getStamina()/2 + ".");
                            this.setStamina(h.getStamina()/2);
                    }
                    else if (h.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ h.name + " is exausted (0 Stamina). "+ this.name + " gave him/her half of his/her : "+ this.getStamina()/2 + ".");
                            h.setStamina(this.getStamina()/2);
                    }
                    else {
                            FileManager.writeToLogFile("[MEET] They both restored 1/4 of their HP.");
                            this.life += 25;
                            h.life += 25;
                    }
            }
            else {
                    if(this.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ this.name + " is exhausted (0 Stamina). "+ h.name + " killed him/her.");
                            this.life = 0;
                    }
                    else if (h.getStamina() == 0) {
                            FileManager.writeToLogFile("[MEET] "+ h.name + " is exhausted (0 Stamina). "+ this.name + " killed him/her.");
                            h.life = 0;
                    }
                    else {
                    	do {
                    		this.attack(h);
                    		if (h.isAlive()) h.attack(this);
                    	} while(this.isAlive() && h.isAlive());
                    	
                    	if(!this.isAlive()) {
                    		h.xp += this.xp;
                    		FileManager.writeToLogFile("[DEATH] " + this.name + " from House " + this.getClass().getSimpleName() + " is killed by "+ h.name + " from House "+ h.getClass().getSimpleName());
                    	}
                    	else {
                    		FileManager.writeToLogFile("[DEATH] "+ h.name +" from House "+ h.getClass().getSimpleName()+" is killed by "+ this.name+" from House "+ this.getClass().getSimpleName()+".");
                    		this.xp += h.xp;
                    	}              	
                            
                    }
            }
    }
    
    @Override
    protected void attack(Character c) throws IOException {
    	switch(this.rollDice()) {
	    	case CRITIC_SUCCESS:
	    		this.superAttack(c);
	    		break;
	    	case SUCCESS:
	    		c.setLife(c.life - (int)(this.xp * 0.3) -this.power);
	    		if(c instanceof Human) {
	    			Human h = (Human) c;
	    			FileManager.writeToLogFile("[ATTACK] "+ this.name+" from House "+ this.getClass().getSimpleName()+" attacked successfully. "+ h.name+"from House "+ h.getClass().getSimpleName()+" lost "+c.power +"and has now "+ c.life+" hp.");
	    		}
	    		else {
	    			WhiteWalker w = (WhiteWalker) c;
	    			FileManager.writeToLogFile("[ATTACK] "+ this.name+" from House "+ this.getClass().getSimpleName()+" attacked successfully. The whitewalker lost "+c.power +"and has now "+ c.life+" hp.");
	    		}
				break;
			default:
				FileManager.writeToLogFile("[ATTACK]"+ this.name+" from House "+ this.getClass().getSimpleName() +" missed his attack! Nothing happened.");
				break;
	    }
    }

	
    protected abstract void superAttack(Character character);
}

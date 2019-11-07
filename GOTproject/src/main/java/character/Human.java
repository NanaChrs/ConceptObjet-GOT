package character;

import java.io.IOException;

import gameplay.FileManager;
import static gameplay.UserInterface.displayConsole;
import map.Direction;
import map.SafeZone;

public abstract class Human extends Character {
    //Attributs - Instance définie par :
	
    //  son nom et sa vie de départ
    protected String name;
    protected final static int INITIAL_LIFE = 100; 

    //  ses capacités de déplacement
    private final int maxStamina;
    protected int stamina;
    protected final static int LOW_STAMINA = 20; 
    protected SafeZone safezone;
    protected final static int MAX_TURNS_WITHOUT_STAMINA = 2; //nombre de tours possibles sans endurance avant de mourir
    protected int turnsWithoutStamina; //compteur de tours sans endurance
    
    //  son expérience
    protected final static int GAIN_BY_LEVEL = 5; 
    protected int level; //augmentation des stats
    protected final static int XP_THRESHOLD = 20;
    protected int xp;
    
    //Constructeur - naissance de l'instance
    public Human(String name, int maxPower, int power, int maxStamina, int criticalSuccessThreshold, int failureThreshold) {
        super(INITIAL_LIFE, maxPower, power, criticalSuccessThreshold, failureThreshold);
        this.maxStamina = (maxStamina < LOW_STAMINA * 2) ? LOW_STAMINA * 2 : maxStamina;
        this.stamina = LOW_STAMINA;//force les personnages à se regrouper avant de repartir à l'attaque
        this.level = 1;
        this.xp = 0;
        this.name = name;
    }
    
    //Affichage des caractéristiques statiques de la classe
    public static void displayStatics() {
        String display = "\n\nClasse Human - hérite de Character";
        display += "\nVie de départ : "+INITIAL_LIFE;
        display += "\nEndurance min avant de chercher safezone : "+LOW_STAMINA;
        display += "\nNombre de tours pouvant être passés sans endurance : "+MAX_TURNS_WITHOUT_STAMINA;
        display += "\nAmélioration des stats par niveau : "+GAIN_BY_LEVEL;
        display += "\nExpérience nécessaire pour gagner un niveau : "+XP_THRESHOLD;
        
        System.out.println(display);
    }
    
    
    //Getters & setters utiles
    
    //  nom
    public String getName() {
        return name;
    }
    
    public String getFullName() {
        return name + " " + this.getClass().getSimpleName();
    }
    
    //  déplacement
    public void addStamina(int stamina) {
        this.stamina += stamina;
        if (this.stamina > maxStamina) this.stamina = maxStamina;
    }
    
    public void reduceStamina(int stamina) throws InterruptedException {
        this.stamina -= stamina;
        if (this.stamina < 0) this.stamina = 0;
    }
    
    //  expérience
    private void newLevel() {
        this.xp %= XP_THRESHOLD;
        this.level++;
        this.maxLife += GAIN_BY_LEVEL;
        this.addPower(GAIN_BY_LEVEL);
    }
    
    public void addXp(int xp) {
        this.xp += (xp > 0 ? xp : 0);
        if (this.xp >= XP_THRESHOLD) {
        	for (int i = 0; i < this.xp/XP_THRESHOLD; ++xp) {
        		this.newLevel();
        	}
        }
    }
    
    //  safeZone
    public Direction safeZoneDirection() {
        return this.westeros.getSafeZone(this.getClass().getSimpleName()).getCorner();
    }
    
    //Méthodes protected - définition d'actions
    @Override
    protected void movmentConsequences() throws InterruptedException {
    	//Dégats infligés par la météo
        int fieldDamages = this.currentBox.getWeatherDamages();
        if (fieldDamages == 0) {
            this.addLife(1);//se soigne
        }
        else {
            this.reduceLife(fieldDamages,DamageSource.Nature,null);
        }
        
        if (this.isAlive()) {
            this.addXp(1);
            
            //si perso dans sa safezone
            if (this.currentBox.isSafe() && this.currentBox.getSafeFor().toString().equals(this.getClass().getSimpleName())) {
                this.addStamina(SafeZone.getRecovery());
            }
            else { 
                this.reduceStamina(1);
            }
        }
    }
    
    @Override
    protected void meet(WhiteWalker ww, int remainingBoxes) throws IOException, InterruptedException {
        //Affichage console et écriture logs
    	displayConsole(this.getFullName() + " lv" + this.level + " (vie : " + this.life + ") attaque un marcheur blanc", westeros, 2);
    	FileManager.writeToLogFile(1,"MEET",this.getFullName() + " (lv"+this.level+" : "+this.life+" HP and "+this.power+" PP) met a whitewalker ("+ww.life+" HP and "+ww.power+" PP)");
    	
    	
    	//Attaque entre perso et white walker
    	do {
            this.attack(ww);
            if (ww.isAlive()) ww.attack(this);
    	} while(this.isAlive() && ww.isAlive());
    	
    	//Si perso meurt
    	if(!this.isAlive()) {
            FileManager.writeToLogFile("DEATH","The whitewalker killed "+this.getFullName()+".");
        }
    	//Si WW meurt
    	else {
            FileManager.writeToLogFile("DEATH",this.getFullName()+" killed the Whitewalker : He/she gain 100 XP and 25 HP.");
            this.addXp(100);
            this.addLife(25);
    	}
    }

    @Override
    protected void meet(Human h, int remainingBoxes) throws IOException, InterruptedException {
        //Ecriture des logs 
    	FileManager.writeToLogFile(1,"MEET",this.getFullName() + " (lv"+this.level+" : "+this.life+" HP and "+this.power+" PP) met "+h.getFullName() + " (lv"+h.level+" : "+h.life+" HP and "+h.power+" PP)");
    	
    	
    	//Si même région ou hors safezone : affiche rencontre
    	if(this.getClass().getSuperclass().equals(h.getClass().getSuperclass()) || !this.currentBox.isSafe() && !h.currentBox.isSafe()) {
            displayConsole(this.getFullName() + " lv" + this.level + " (vie : " + this.life + ") rencontre " + h.getFullName() + " lv" + h.level + " (vie : " + h.life + ")", westeros, 2);
        }
    	//Sinon écrit juste en log car pas de conséquence à la rencontre
        else {
            FileManager.writeToLogFile("MEET","One of the character is inside a safezone ! No fight allowed.");
        }
        
        //amis (même région)
        if(this.getClass().getSuperclass().equals(h.getClass().getSuperclass())) {
        	//récupération stamina (sauvetage)
        	if(this.stamina == 0) {
                FileManager.writeToLogFile("RESCUE", this.getFullName()+" is exhausted (0 Stamina). "+ h.name + " gave him/her half of his/her : "+ h.stamina/2 + ".");
                this.addStamina(h.stamina/2);
                this.turnsWithoutStamina = 0; //sauvé
            }
            else if (h.stamina == 0) {
                FileManager.writeToLogFile("RESCUE", h.getFullName()+" is exhausted (0 Stamina). "+ this.name + " gave him/her half of his/her : "+ this.stamina/2 + ".");
                h.addStamina(this.stamina/2);
                h.turnsWithoutStamina = 0; //sauvé
            }
        	//récupération de stats
            else {
                FileManager.writeToLogFile("SHARING","They both restored 1/4 of their HP.");
                this.addLife(this.maxLife/4);
                h.addLife(h.maxLife/4);
                
                //si même faction : gain XP
                if (this.getClass() == h.getClass()) {
                    int gainXp = remainingBoxes < 0 ? 1 : remainingBoxes;

                    FileManager.writeToLogFile("SHARING","They both gained "+ gainXp +" xp.");
                    this.addXp(gainXp);
                    h.addXp(gainXp);
                }
            }
        }
        
        //ennemis (hors safezone)
        else if (!this.currentBox.isSafe() && !h.currentBox.isSafe()) {
            //si un des persos sans stamina, l'autre l'achève
        	if(this.stamina == 0) {
                FileManager.writeToLogFile("MURDER",this.getFullName()+" is exhausted (0 Stamina). "+ h.getFullName() + " killed him/her.");
                this.reduceLife(this.life,DamageSource.Battle,h);
            }
            else if (h.stamina == 0) {
                FileManager.writeToLogFile("MURDER",h.getFullName()+" is exhausted (0 Stamina). "+ this.getFullName() + " killed him/her.");
                h.reduceLife(h.life,DamageSource.Battle,this);
            }
        	//sinon, combat
            else {
                do {
                    this.attack(h);
                    if (h.isAlive()) h.attack(this);
                } while(this.isAlive() && h.isAlive());
                
                //si l'instance a perdu
                if(!this.isAlive()) {
                    FileManager.writeToLogFile("DEATH",h.getFullName()+" killed "+this.getFullName()+". Reward : "+this.xp+" XP");
                    h.addXp(this.xp + this.level * Human.XP_THRESHOLD);
                }
                //si l'autre perso a perdu
                else {
                    FileManager.writeToLogFile("DEATH",this.getFullName()+" killed "+h.getFullName()+". Reward : "+h.xp+" XP");
                    this.addXp(h.xp + h.level * Human.XP_THRESHOLD);
                }
            }
        }
    }
    
    @Override
    protected void attack(Character c) throws IOException, InterruptedException {
    	switch(this.rollDice()) {
            case CRITICAL_SUCCESS:
                this.superAttack(c);
                break;
            case SUCCESS:
                c.reduceLife(this.power,DamageSource.Battle,this);
                if(c instanceof Human) {
                    Human h = (Human) c;
                    FileManager.writeToLogFile("ATTACK",this.getClass().getSimpleName().toString()+"\t : Successful attack : "+ h.getClass().toString()+" lost "+this.power +" HP and has now "+ c.life+" hp.");
                }
                else {
                    FileManager.writeToLogFile("ATTACK",this.getClass().getSimpleName().toString()+"\t : Successful attack : The whitewalker lost "+this.power +" HP and has now "+ c.life+" hp.");
                }
                break;
            default:
                FileManager.writeToLogFile("ATTACK",this.getClass().getSimpleName().toString()+"\t : Missed attack! Nothing happened.");
                break;
        }
    }

    protected abstract void superAttack(Character character) throws IOException, InterruptedException;
}

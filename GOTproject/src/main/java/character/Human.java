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
    protected final static int INITIAL_LIFE = 100; //Attribut statique qui a du sens

    //  ses capacités de déplacement
    private final int maxStamina;
    protected int stamina;
    protected final static int LOW_STAMINA = 20; //Attribut statique qui a du sens
    protected SafeZone safezone;//agrégation
    protected final static int MAX_TURNS_WITHOUT_STAMINA = 2; //Attribut statique qui a du sens
    protected int turnsWithoutStamina;
    
    //  son expérience
    protected final static int GAIN_BY_LEVEL = 5; //Attribut statique qui a du sens
    protected int level; //augmentation des stats
    protected final static int XP_THRESHOLD = 40; //Attribut statique qui a du sens
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
    
    //Getters & setters utiles
    //  caractéristiques
    public static void displayStatics() {
        String display = "\n\nClasse Human - hérite de Character";
        display += "\nVie de départ : "+INITIAL_LIFE;
        display += "\nEndurance min avant de chercher safezone : "+LOW_STAMINA;
        display += "\nNombre de tours pouvant être passés sans endurance : "+MAX_TURNS_WITHOUT_STAMINA;
        display += "\nAmélioration des stats par niveau : "+GAIN_BY_LEVEL;
        display += "\nExpérience nécessaire pour gagner un niveau : "+XP_THRESHOLD;
        
        System.out.println(display);
    }
    
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
        this.xp = 0;
        this.level++;
        this.maxLife += GAIN_BY_LEVEL;
        this.addPower(GAIN_BY_LEVEL);
    }
    
    public void addXp(int xp) {
        this.xp += (xp > 0 ? xp : 0);
        if (this.xp >= XP_THRESHOLD) {
            this.newLevel();
        }
    }
    
    //  safeZone
    public Direction safeZoneDirection() {
        return this.westeros.getSafeZone(this.getClass().getSimpleName()).getCorner();
    }
    
    //Méthodes protected - définition d'actions
    @Override
    protected void movmentConsequences() throws InterruptedException {
        int fieldDamages = this.currentBox.getWeatherDamages();
        if (fieldDamages == 0) {
            this.addLife(1);//se soigne
        }
        else {
            this.reduceLife(fieldDamages,DamageSource.Nature,null);
        }
        
        if (this.isAlive()) {
            this.addXp(1);

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
        displayConsole(this.getFullName() + " lv" + this.level + " (vie : " + this.life + ") attaque un marcheur blanc", westeros, 2);
    	FileManager.writeToLogFile("\n[MEET] "+ this.name +" from House "+ this.getClass().getSimpleName() + " met a White Walker.");
    	
    	do {
            this.attack(ww);
            if (ww.isAlive()) ww.attack(this);
    	} while(this.isAlive() && ww.isAlive());
    	
    	if(!this.isAlive()) {
            FileManager.writeToLogFile("[DEATH] " + this.name + " from House " + this.getClass().getSimpleName() + " is killed by the whitewalker.");
            ww.addLife(25);
    	}
    	else {
            FileManager.writeToLogFile("[DEATH] The whitewalker is killed by "+ this.name+" from House "+ this.getClass().getSimpleName()+".");
            this.addXp(100);
            this.addLife(25);
    	}
    }

    @Override
    protected void meet(Human h, int remainingBoxes) throws IOException, InterruptedException {
        FileManager.writeToLogFile("\n[MEET] "+ this.name +" from House "+ this.getClass().getSimpleName() + " met " + h.name + " from House "+ h.getClass().getSimpleName() +".");
        if(this.getClass().getSuperclass().equals(h.getClass().getSuperclass()) || !this.currentBox.isSafe() && !h.currentBox.isSafe()) {
            displayConsole(this.getFullName() + " lv" + this.level + " (vie : " + this.life + ") rencontre " + h.getFullName() + " lv" + h.level + " (vie : " + h.life + ")", westeros, 2);
        }
        else {
            FileManager.writeToLogFile("[MEET] One of the character is inside a safezone ! No fight allowed.");
        }
        
        //amis (faction / région)
        if(this.getClass().getSuperclass().equals(h.getClass().getSuperclass())) {
            if(this.stamina == 0) {
                FileManager.writeToLogFile("[MEET] "+ this.name + " is exhausted (0 Stamina). "+ h.name + " gave him/her half of his/her : "+ h.stamina/2 + ".");
                this.addStamina(h.stamina/2);
            }
            else if (h.stamina == 0) {
                FileManager.writeToLogFile("[MEET] "+ h.name + " is exhausted (0 Stamina). "+ this.name + " gave him/her half of his/her : "+ this.stamina/2 + ".");
                h.addStamina(this.stamina/2);
            }
            else {
                FileManager.writeToLogFile("[MEET] They both restored 1/4 of their HP.");
                this.addLife(this.maxLife/4);
                h.addLife(h.maxLife/4);
                
                //même faction
                if (this.getClass() == h.getClass()) {
                    int gainXp = remainingBoxes < 0 ? 1 : remainingBoxes;

                    FileManager.writeToLogFile("[MEET] They both gained "+ gainXp +" xp.");
                    this.addXp(gainXp);
                    h.addXp(gainXp);
                }
            }
        }
        //ennemis
        else if (!this.currentBox.isSafe() && !h.currentBox.isSafe()) {
            if(this.stamina == 0) {
                FileManager.writeToLogFile("[MEET] "+ this.name + " is exhausted (0 Stamina). "+ h.name + " killed him/her.");
                this.reduceLife(this.life,DamageSource.Battle,h);
            }
            else if (h.stamina == 0) {
                FileManager.writeToLogFile("[MEET] "+ h.name + " is exhausted (0 Stamina). "+ this.name + " killed him/her.");
                h.reduceLife(h.life,DamageSource.Battle,this);
            }
            else {
                do {
                    this.attack(h);
                    if (h.isAlive()) h.attack(this);
                } while(this.isAlive() && h.isAlive());

                if(!this.isAlive()) {
                    h.addXp(this.xp);
                    FileManager.writeToLogFile("[DEATH] " + this.name + " from House " + this.getClass().getSimpleName() + " is killed by "+ h.name + " from House "+ h.getClass().getSimpleName());
                }
                else {
                    FileManager.writeToLogFile("[DEATH] "+ h.name +" from House "+ h.getClass().getSimpleName()+" is killed by "+ this.name+" from House "+ this.getClass().getSimpleName()+".");
                    this.addXp(h.xp);
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
                    FileManager.writeToLogFile("[ATTACK] "+ this.name+" from House "+ this.getClass().getSimpleName()+" attacked successfully. "+ h.name+"from House "+ h.getClass().getSimpleName()+" lost "+this.power +"and has now "+ c.life+" hp.");
                }
                else {
                    WhiteWalker w = (WhiteWalker) c;
                    FileManager.writeToLogFile("[ATTACK] "+ this.name+" from House "+ this.getClass().getSimpleName()+" attacked successfully. The whitewalker lost "+this.power +"and has now "+ c.life+" hp.");
                }
                break;
            default:
                FileManager.writeToLogFile("[ATTACK]"+ this.name+" from House "+ this.getClass().getSimpleName() +" missed his attack! Nothing happened.");
                break;
        }
    }

    protected abstract void superAttack(Character character) throws IOException, InterruptedException;
}

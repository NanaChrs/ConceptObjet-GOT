package character;

import java.io.IOException;

import gameplay.FileManager;
import gameplay.Safezone;

public abstract class Human extends Character {
	//protected int level = 0; //augmentation des stats  
	protected int xp = 0;
	protected Safezone safezone;
	protected String name;
	
	public Human(String name) {
		super();
		this.name = name;
		setDodge(5);
	}
	
	protected void meet(WhiteWalkers ww, int remainingBoxes) {
		this.attack(ww);
	}
	
	public void meet(Human h, int remainingBoxes) throws IOException {
		FileManager.writeToLogFile("[MEET] "+ this.name +" from House "+ this.getClass().getSimpleName() + " met " + h.name + " from House "+ h.getClass().getSimpleName() +".");
		
		if(this.getClass() == h.getClass()) {
			if(this.getStamina() == 0) {
				FileManager.writeToLogFile("[MEET] "+ this.name + " is exausted (0 Stamina). "+ h.name + " gave him/her half of his/her : "+ h.getStamina()/2 + ".");
				this.setStamina(h.getStamina()/2);
			}
			else if (h.getStamina() == 0) {
				FileManager.writeToLogFile("[MEET] "+ h.name + " is exausted (0 Stamina). "+ this.name + " gave him/her half of his/her : "+ this.getStamina()/2 + ".");
				h.setStamina(this.getStamina()/2);
			}
			else {
				FileManager.writeToLogFile("[MEET] They both gained "+ remainingBoxes +" xp. And restored 1/4 of their HP.");
				this.xp += remainingBoxes;
				h.xp += remainingBoxes;
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
				FileManager.writeToLogFile("[MEET] They both gained "+ remainingBoxes +" xp. And restored 1/4 of their HP.");
				this.life += 25;
				h.life += 25;
			}
		}
		else {
			if(this.getStamina() == 0) {
				FileManager.writeToLogFile("[MEET] "+ this.name + " is exausted (0 Stamina). "+ h.name + " killed him/her.");
				this.life = 0;
			}
			else if (h.getStamina() == 0) {
				FileManager.writeToLogFile("[MEET] "+ h.name + " is exausted (0 Stamina). "+ this.name + " killed him/her.");
				h.life = 0;
			}
			else {
				this.attack(h);
			}
		}
	}
	
	protected abstract void superAttack(Character character);
}

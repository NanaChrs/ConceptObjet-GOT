package character;

import gameplay.Safezone;

public abstract class Human extends Character {
	//protected int level = 0; //augmentation des stats  
	protected int xp = 0;
	protected Safezone safezone;
	protected String name;
	
	public Human() {
		super();
		setDodge(5);
	}
	
	protected abstract void superAttack(Character character);
}

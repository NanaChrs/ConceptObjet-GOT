package character;

import gameplay.Safezone;

public abstract class Human extends Character {
	protected int level = 0; //augmentation des stats  
	protected int xp = 0;
	protected Safezone safezone;
	protected String name;
}

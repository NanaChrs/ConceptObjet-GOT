package character;

import map.Box;
import map.Direction;

public abstract class Character {
	private int power = 0;
	static private int maxPower = 100;
	private int stamina;
	protected abstract void move(Box currentBox);
	private Box currentBox;
	protected abstract void meet(Character character);
	private int life = 100;
	private int dodge;
	private Direction lastDirection;
	protected abstract void possibleBoxes();
	
	
	
}

package character;

public abstract class Northerner extends Human {
	
	static final private int MAX_STAMINA = 120;
	static final private int MAX_POWER = 100;

	public Northerner(String string) {
		super(string);
		setPower(5);
		setStamina(120);
		setMaxStepNumber(6);
	}

	protected abstract void attack(Character character);

}

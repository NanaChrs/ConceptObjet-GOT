package character;

public abstract class Southerner extends Human {
	
	static final private int MAX_STAMINA = 100;
	static final private int MAX_POWER = 120;
	
	public Southerner() {
		super();
		setPower(10);
		setStamina(100);
		setMaxStepNumber(4);
	}
	
	protected abstract void attack(Character character);
	
}

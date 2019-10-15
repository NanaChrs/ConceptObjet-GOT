package character;

public abstract class Northerner extends Human {
	
	static final protected int MAX_STAMINA = 120;
	static final protected int MAX_POWER = 100;
	static final protected int CRITIC_SUCESS_LEVEL = 85;
	static final protected int FAILURE_LEVEL = 15;
	
	public Northerner() {
		super();
		setPower(5);
		setStamina(120);
	}
	
	protected abstract void attack(Character character);

}

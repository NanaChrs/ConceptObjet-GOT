package character;

public abstract class Southerner extends Human {
	
	static final protected int MAX_STAMINA = 100;
	static final protected int MAX_POWER = 120;
	static final protected int CRITIC_SUCESS_LEVEL = 80;
	static final protected int FAILURE_LEVEL = 17;
	
	public Southerner(String string) {
		super(string);
		setPower(10);
		setStamina(100);
	}
	
}

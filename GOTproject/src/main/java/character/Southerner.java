package character;

public abstract class Southerner extends Human {
	
	static final protected int MAX_STAMINA = 100;
	static final protected int MAX_POWER = 120;
	
	public Southerner(String string) {
		super(string);
		setPower(10);
		setStamina(100);
	}
	
}

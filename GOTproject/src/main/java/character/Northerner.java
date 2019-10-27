package character;

public abstract class Northerner extends Human {
	
	static final protected int MAX_STAMINA = 120;
	static final protected int MAX_POWER = 100;
	//safezone commune

	public Northerner(String string) {
            super(string);
            setPower(5);
            setStamina(120);
	}

}

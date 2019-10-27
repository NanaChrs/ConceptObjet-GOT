package character;

public abstract class Northerner extends Human {
	
	static final protected int MAX_STAMINA = 120; //Attribut statique qui a du sens
	static final protected int MAX_POWER = 100;  //Attribut statique qui a du sens
	//safezone commune

	public Northerner(String string) {
            super(string);
            setPower(5);
            setStamina(120);
	}

}

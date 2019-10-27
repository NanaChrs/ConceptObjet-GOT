package character;

public abstract class Southerner extends Human {
	
	static final protected int MAX_STAMINA = 100;  //Attribut statique qui a du sens
	static final protected int MAX_POWER = 120;  //Attribut statique qui a du sens
	//safezone commune
        
	public Southerner(String string) {
            super(string);
            setPower(10);
            setStamina(100);
	}
	
}

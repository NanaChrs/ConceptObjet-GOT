package character;

public abstract class Northerner extends Human {
	//faut caractérisitique, sinon classe redondante avec Southerner et human
	protected abstract void attack(Character character);

}

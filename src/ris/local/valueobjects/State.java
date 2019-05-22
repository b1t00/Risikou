package ris.local.valueobjects;

public enum State {
	SETUNITS,
	ATTACK,
	CHANGEUNITS;
	
	public State setNextState() {
		return State.values()[(this.ordinal() + 1) % values().length];
	}
}

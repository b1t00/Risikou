package ris.common.valueobjects;

import java.io.Serializable;

public enum State implements Serializable{
	SETUNITS,
	ATTACK,
	CHANGEUNITS;
	
	public State setNextState() {
		return State.values()[(this.ordinal() + 1) % values().length];
	}
}

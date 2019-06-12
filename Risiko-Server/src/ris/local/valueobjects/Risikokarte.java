package ris.local.valueobjects;

import java.io.Serializable;

public class Risikokarte implements Serializable {

	public enum Symbol {
		KANONE, REITER, SOLDAT;
	};

	private Symbol symbol;
	private Land land;
	private boolean ausgewaehl;

	public Risikokarte(Symbol symbol, Land land) {
		this.symbol = symbol;
		this.land = land;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public Land getLand() {
		return this.land;
	}

	public boolean getAusgewaehl() {
		return ausgewaehl;
	}

	public void setAusgewaehl(boolean ausgewaehl) {
		this.ausgewaehl = ausgewaehl;
	}

	@Override
	public String toString() {
		return symbol.toString() + " - " + land.getName();
	}
	
	

}

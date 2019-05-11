package ris.local.valueobjects;

import java.io.Serializable;

public class Risikokarte implements Serializable {
	
	public enum Symbol { KANONE, REITER, SOLDAT };
	
	private Symbol symbol2;
	private String symbol;
	private Land land;
	
	public Risikokarte(String symbol, Land land) {
		this.symbol = symbol;
		this.symbol2 = Symbol.REITER;
		this.land = land;
		}
	
	public String getSymbol() {
		return symbol;
	}
	
	public Land getLand() {
		return this.land;
	}
}

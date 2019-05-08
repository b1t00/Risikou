package ris.local.valueobjects;

import java.io.Serializable;

public class Risikokarte implements Serializable {
	private String symbol;
	private Land land;
	
	public Risikokarte(String symbol, Land land) {
		this.symbol = symbol;
		this.land = land;
		}
	
	public String getSymbol() {
		return symbol;
	}
	
	public Land getLand() {
		return this.land;
	}
}

package ris.local.valueobjects;

import java.io.Serializable;

public class Einheitenkarte implements Serializable {
	private String symbol;
	private Land land;
	
	public Einheitenkarte(String symbol, Land land) {
		this.symbol = symbol;
		this.land = land;
		}
	
	public String getSymbol() {
		return symbol;
	}
}

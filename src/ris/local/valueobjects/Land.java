package ris.local.valueobjects;


public class Land {
	private int einheiten;
	private int nummer;
	private String name;
	private String farbe;
	
	public Land(String name, int nummer, String farbe) {
		this.nummer = nummer;
		this.name = name;
		this.farbe = farbe;
	}
	
	public int getNummer() {
		return nummer;
	}
	
	public int getEinheiten() {
		return einheiten;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public String getFarbe() {
		return farbe;
	}
}
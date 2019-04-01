package ris.local.valueobjects;



public class Land {
	private int einheiten;
	private int nummer;
	private String name;
	
	private Land(int nummer, String name) {
		this.nummer = nummer;
		this.name = name;
	}
	
	public int getNummer() {
		return nummer;
	}
	
	public int getEinheiten() {
		return einheiten;
	}
}
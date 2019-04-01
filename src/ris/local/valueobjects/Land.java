package ris.local.valueobjects;


public class Land {
	private int einheiten;
	private int nummer;
	private String name;
	
	public Land(String name, int nummer) {
		this.nummer = nummer;
		this.name = name;
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
}
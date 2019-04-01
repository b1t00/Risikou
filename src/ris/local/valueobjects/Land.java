package ris.local.valueobjects;

import java.util.ArrayList;

public class Land {
	private int einheiten;
	private int nummer;
	private String name;
	public String toString() {
		return name;
	}
	
	static ArrayList<Land> laender = new ArrayList<Land>();
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
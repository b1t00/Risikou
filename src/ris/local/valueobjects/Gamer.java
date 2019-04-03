package ris.local.valueobjects;

import java.util.ArrayList;

public class Gamer {
	private String name;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	private String farbe = "rot";
	
	public Gamer(String name, ArrayList<Land> inBesitz, String farbe){
		this.name = name;
		this.inBesitz = inBesitz;
		this.farbe = farbe;
	}

	public String toString() {
		return name;
	}

	public String gibLaenderAus() {
		String rueckgabe = "";
		for (Land land: inBesitz) {
			rueckgabe += land.getNummer() + " > " + land.getName() + "\n";
		}
		return rueckgabe;
	}
	
	public String getFarbe() {
		return farbe;
	}
}

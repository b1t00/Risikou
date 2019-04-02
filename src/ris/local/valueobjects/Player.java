package ris.local.valueobjects;

import java.util.ArrayList;

	public class Player {
	private String name;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	public Player(String name, ArrayList<Land> inBesitz){
		this.name = name;
		this.inBesitz = inBesitz;
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
}

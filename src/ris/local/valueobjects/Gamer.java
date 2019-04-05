package ris.local.valueobjects;

import java.util.ArrayList;
import java.util.List;

import ris.local.domain.Weltverwaltung;

public class Gamer {
	private String name;
	private String farbe;
	// private int besatzerNr;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	//private int einheiten;

	public Gamer(String name, String farbe) {
		this.name = name;
		this.farbe = farbe;
	}
	
	public String getName() {
		return name;
	}

	public String getFarbe() {
		return farbe;
	}

	public ArrayList<Land> getBesitz() {
		return inBesitz;
	}
	
	public String toString() {
		return (name + " hat die Farbe " + farbe) ;
	}

	public String gibLaenderAus() {
		String rueckgabe = "";
		for (Land land: inBesitz) {
			rueckgabe += land.getNummer() + " > " + land + "\n";
		}
		return rueckgabe;
	}

	//diese Methode beim Hinzufügen von einzelnen Ländern
	public void addLand(Land neuesLand) {
		this.inBesitz.add(neuesLand);
	}
	
	//diese Methode beim Hinzufügen von einem ganzen Länder-Array
	public void addLaender(ArrayList<Land> neueLaender) {
		this.inBesitz = neueLaender;
	}

}

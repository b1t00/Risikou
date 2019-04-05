package ris.local.valueobjects;

import java.util.ArrayList;
import java.util.List;

import ris.local.domain.Worldmanagement;

public class Player {
	private String name;
	private String farbe;
	private int nummer;
	// private int besatzerNr;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	//private int einheiten;

	public Player(String name, String farbe, int nummer) {
		this.name = name;
		this.farbe = farbe;
		this.nummer = nummer;
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
			rueckgabe += name + " besitzt " + land.getNummer() + " > " + land + "\n";
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

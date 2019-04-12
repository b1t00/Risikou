package ris.local.valueobjects;

import java.util.ArrayList;
import java.util.List;


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
	public int getNummer() {
		return nummer;
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

	//vlt auch unnöttige methode siehe cui gibLaenderUndNummerVonSpielerAus()
	public ArrayList<String> gibLaenderUndNummer(){
		ArrayList<String> ausgabe = new ArrayList<String>();
		for(Land land : getBesitz()) {
			ausgabe.add(land.getNummer() + " : " + land.getName());
		}
		return ausgabe;
	}
//	public ArrayList<String> gibLaenderAus() {
//
//		String rueckgabe = "";
//		for (Land land: inBesitz) {
//			
//			rueckgabe += name + " besitzt " + land.getNummer() + " > " + land + "\n";
//			
//		}
//
//		return inBesitz;
//	}


	//diese Methode beim Hinzufügen von einzelnen Ländern
	public void addLand(Land neuesLand) {
		this.inBesitz.add(neuesLand);
	}
	
	//diese Methode beim Hinzufügen von einem ganzen Länder-Array, am Anfang
	public void addLaender(ArrayList<Land> neueLaender) {
		this.inBesitz = neueLaender;
	}
	
//	public void faerbeLaender(Player player) {
//		ArrayList<Land> besitz = getBesitz();
//		for(ArrayList<Land> b : besitz) {
//			b.setFarbe()
//		}
//	}

}

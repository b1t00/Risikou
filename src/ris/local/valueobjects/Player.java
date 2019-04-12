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
	
	//prüft, ob player land besitzt, wenn ja, löscht er es, wenn nein, fügt er es hinzu
	public void setBesitz(Land land) {
		//TODO: evtl. muss die equals methode überschrieben werden, damit contains funktioniert
		if (inBesitz.contains(land)) {
			inBesitz.remove(land);
		} else {
			inBesitz.add(land);
		}
	}
	
	public String toString() {
		return (name + " hat die Farbe " + farbe) ;
	}


	public ArrayList gibLaenderAus() {

//		String rueckgabe = "";
//		for (Land land: inBesitz) {
//			rueckgabe += name + " besitzt " + land.getNummer() + " > " + land + "\n";
//		}

		return inBesitz;
	}


	//diese Methode beim Hinzufügen von einzelnen Ländern
	//vielleicht hinfällig, kann einfach über setBesitz() aufgerufen werden
	public void addLand(Land neuesLand) {
		this.inBesitz.add(neuesLand);
	}
	
	//diese Methode beim Hinzufügen von einem ganzen Länder-Array, zu Beginn des Spiels
	public void addLaender(ArrayList<Land> neueLaender) {
		this.inBesitz = neueLaender;
	}
	
	public static void main(String[] args) {
		Player anna = new Player ("Anna", "Rot", 1);
		Land portugal = new Land ("Portugal", 1);
		Land schweden = new Land ("Schweden", 2);
		Land england = new Land ("England", 3);
		Land spanien = new Land ("Spanien", 4);
		anna.addLand(portugal);
		anna.addLand(schweden);
		anna.addLand(england);
		anna.setBesitz(spanien);
		anna.setBesitz(england);
		ArrayList<Land> annasLaender = anna.getBesitz();
		for (Land land: annasLaender) {
			System.out.println(land.getName());
		}
	}


}

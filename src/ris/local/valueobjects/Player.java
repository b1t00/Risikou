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
		return name;
	}

	//vlt auch unnöttige methode siehe cui gibLaenderUndNummerVonSpielerAus()
	public ArrayList<String> gibLaenderUndNummer(){
		ArrayList<String> ausgabe = new ArrayList<String>();
		for(Land land : getBesitz()) {
			ausgabe.add(land.getNummer() + " : " + land.getName());
		}
		return ausgabe;
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

	//diese Methode beim Hinzufügen von einzelnen Ländern, wahrscheinlich überflüssig, erledigt sich mit setBesitz
	public void addLand(Land neuesLand) {
		this.inBesitz.add(neuesLand);
	}
	
	//diese Methode beim Hinzufügen von einem ganzen Länder-Array, am Anfang
	public void addLaender(ArrayList<Land> neueLaender) {
		this.inBesitz = neueLaender;
	}
	

}

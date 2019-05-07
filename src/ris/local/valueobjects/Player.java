package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Player implements Serializable {
	private String name;
	private String farbe;
	private int nummer;
	private MissionsVorlage mission; //
	// private int besatzerNr;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	// private int einheiten;
	int[] uBlock = new int[12];

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

	public String getMission() {
		return mission.getMission();
	}
	
	public Boolean isMissionComplete(Player aktiverPlayer) {
		return mission.missionComplete(aktiverPlayer);
	}

	public void setMission(MissionsVorlage mission) {
		this.mission = mission;
	}

	public ArrayList<Land> getBesitz() {
		Collections.sort(inBesitz);
		return inBesitz;
	}

	public int[] getBlock() {
		return uBlock;
	}

	public int[] setBlock(int[] gB, int indexLand, int units) {
		gB[indexLand] += units;
		return gB;
	}

	public String toString() {
		return name;
	}

	// vlt auch unnöttige methode siehe cui gibLaenderUndNummerVonSpielerAus()
	public ArrayList<String> gibLaenderUndNummer() {
		ArrayList<String> ausgabe = new ArrayList<String>();
		for (Land land : getBesitz()) {
			ausgabe.add(land.getNummer() + " : " + land.getName());
		}
		return ausgabe;
	}

	// prüft, ob player land besitzt, wenn ja, löscht er es, wenn nein, fügt er es
	// hinzu
	public void setBesitz(Land land) {
		// TODO: evtl. muss die equals methode überschrieben werden, damit contains
		// funktioniert
		if (inBesitz.contains(land)) {
			inBesitz.remove(land);
		} else {
			inBesitz.add(land);
		}
	}

	// diese Methode beim Hinzufügen von einzelnen Ländern, wahrscheinlich
	// überflüssig, erledigt sich mit setBesitz
	public void addLand(Land neuesLand) {
		this.inBesitz.add(neuesLand);
	}

	// diese Methode beim Hinzufügen von einem ganzen Länder-Array, am Anfang
	public void addLaender(ArrayList<Land> neueLaender) {
		this.inBesitz = neueLaender;
	}

	public boolean isDead() { //checken @tobi muss wahrscheinlich nach jedem angriff kontrolliert werden
		if (inBesitz.size() <= 0) {
			return true;
		}
		return false;
	}

}

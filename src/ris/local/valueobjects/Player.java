package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Player implements Serializable {
	private String name;
	private String farbe;
	private int nummer;
	private Mission mission; //
	private ArrayList<Risikokarte> gezogeneRisikokarten;
	// private int besatzerNr;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	// private int einheiten;
	int[] uBlock = new int[12];
	// bei Einnahme eines Landes wird gutschriftEinheitenkarte auf true gesetzt
	private boolean gutschriftEinheitenkarte = false;

	public Player(String name, String farbe, int nummer) {
		this.name = name;
		this.farbe = farbe;
		this.nummer = nummer;
		this.gezogeneRisikokarten = new ArrayList<Risikokarte>();
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

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public ArrayList<Land> getBesitz() {
		Collections.sort(inBesitz);
		return inBesitz;
	}

	public int[] getBlock() {
		return uBlock;
	}
	
	//Methode wird aufgerufen, wenn ein Zug beendet wird
	public boolean getGutschriftEinheitenkarte() {
		return gutschriftEinheitenkarte;
	}
	
	public void setGutschriftEinheitenkarte(boolean wert) {
		this.gutschriftEinheitenkarte = wert;
	}

	public int[] setBlock(int[] gB, int indexLand, int units) {
		gB[indexLand] += units;
		return gB;
	}
	
	public void setEinheitenkarte(Risikokarte karte) {
		this.gezogeneRisikokarten.add(karte);
	}
	
	public ArrayList<Risikokarte> getEinheitenkarten(){
		return gezogeneRisikokarten;
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
	
	public int[] risikokartenKombi() {
		//Array mit den Anzahl der einheitenKarten für die verschiedenen Symbole
		//1. Stelle = Kanone, 2. Stelle = Reiter, 3. Stelle = Soldat
		int[] symbolAnzahlArray = {0, 0, 0};
		if(gezogeneRisikokarten != null) {
			for (Risikokarte karte: gezogeneRisikokarten) {
				if (karte.getSymbol().equals("Kanone")) {
					symbolAnzahlArray[0]++;
				} else if (karte.getSymbol().equals("Reiter")) {
					symbolAnzahlArray[1]++;
				} else {
					symbolAnzahlArray[2]++;
				}
			}
		}
		return symbolAnzahlArray;
	}
	
	//Methode bekommt eine int, stellvertretend für symbol (0 = Kanone, 1 = Reiter, 3 = Soldat, 4 = je eine)
	public void loescheRisikokarten(int index) {
		String[] symbolArray = {"Kanone", "Reiter", "Soldat"};
		int eingetauscht = 3;
		while(eingetauscht > 0) {
			//für den Fall, dass je eine Karte eingetauscht wird, wird zaehler hochgezählt, erst eine Kanone, dann Reiter, dann Soldat gelöscht
			if (index == 3) {
				//dafür muss der Risikokarten-Array zuerst sortiert werden
				Collections.sort(this.gezogeneRisikokarten, (karte1, karte2) -> karte1.getSymbol().compareTo(karte2.getSymbol()));
				int zaehler = 0;
				for (int i = 0; i < gezogeneRisikokarten.size(); i++) {
					if (gezogeneRisikokarten.get(i).getSymbol().equals(symbolArray[zaehler])){
						gezogeneRisikokarten.remove(i);
						eingetauscht--;
						zaehler ++;
						i--;
					}
				}
			} else {
				for (int i = 0; i < gezogeneRisikokarten.size(); i++) {
					if (gezogeneRisikokarten.get(i).getSymbol().equals(symbolArray[index])){
						gezogeneRisikokarten.remove(i);
						eingetauscht--;
					}
				}
			}
		}
		System.out.println("Karten wurden fertig eingetauscht, Anzahl: " + eingetauscht);
	}

	public boolean isDead() { //checken @tobi muss wahrscheinlich nach jedem angriff kontrolliert werden
		if (inBesitz.size() <= 0) {
			return true;
		}
		return false;
	}

}

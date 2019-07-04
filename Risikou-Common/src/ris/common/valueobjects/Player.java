package ris.common.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import ris.common.valueobjects.Risikokarte.Symbol;

public class Player implements Serializable {
	private String name;
	private String farbe;
	private int nummer;
	private Mission mission;
	private ArrayList<Risikokarte> gezogeneRisikokarten;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	int[] uBlock = new int[42];
	// bei Einnahme eines Landes wird gutschriftEinheitenkarte auf true gesetzt
	private boolean gutschriftEinheitenkarte = false;
	private boolean kartenWurdenEntfernt = false;

	private ArrayList<Symbol> dreiAusgewaehltenKarten = new ArrayList<Symbol>();

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
//		return "dies das verschiedene Dinge";
	}

	public Mission getMissionObject() {
		return this.mission;
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

	// Methode wird aufgerufen, wenn ein Zug beendet wird
	public boolean getGutschriftEinheitenkarte() {
		return gutschriftEinheitenkarte;
	}

	public void setGutschriftEinheitenkarte(boolean wert) {
		this.gutschriftEinheitenkarte = wert;
	}

	public void setBlock(int indexLand, int units) {
		uBlock[indexLand] += units;
	}

	public void setEinheitenkarte(Risikokarte karte) {
		this.gezogeneRisikokarten.add(karte);
	}

	public ArrayList<Risikokarte> getEinheitenkarten() {
		return gezogeneRisikokarten;
	}

	public String toString() {
		return name;
	}

	// vlt auch unn�ttige methode siehe cui gibLaenderUndNummerVonSpielerAus()
	public ArrayList<String> gibLaenderUndNummer() {
		ArrayList<String> ausgabe = new ArrayList<String>();
		for (Land land : getBesitz()) {
			ausgabe.add(land.getNummer() + " : " + land.getName());
		}
		return ausgabe;
	}

	// prueft, ob player land besitzt, wenn ja, loescht er es, wenn nein, fuegt er es hinzu
	public void setBesitz(Land land) {
		if (inBesitz.contains(land)) {
			inBesitz.remove(land);
		} else {
			inBesitz.add(land);
		}
	}

	// TODO: nochmal exceptions checken
	public Land getLandById(int i) {
		for (Land l : inBesitz) {
			if (l.getNummer() == i) {
				return l;
			}
		}
		return null;
	}

	// diese Methode beim Hinzufuegen von einzelnen Laendern, wahrscheinlich
	// ueberfluessig, erledigt sich mit setBesitz
	public void addLand(Land neuesLand) {
		this.inBesitz.add(neuesLand);
	}

	// diese Methode beim Hinzuf�gen von einem ganzen Laender-Array, am Anfang
	public void addLaender(ArrayList<Land> neueLaender) {
		this.inBesitz = neueLaender;
	}

//	------------------------------------------RisikokartenKombi-----------------------------------------------------------------------

	public int[] risikokartenKombi() {
		// erstellt Array mit den Anzahlen der Risikokarten f�r die verschiedenen
		// Symbole
		// 1. Stelle = Kanone, 2. Stelle = Reiter, 3. Stelle = Soldat
		int[] symbolAnzahlArray = { 0, 0, 0 };
		if (gezogeneRisikokarten != null) {
			for (Risikokarte karte : gezogeneRisikokarten) {
				if (karte.getSymbol() == Symbol.KANONE) {
					symbolAnzahlArray[0]++;
				} else if (karte.getSymbol().equals(Symbol.REITER)) {
					symbolAnzahlArray[1]++;
				} else {
					symbolAnzahlArray[2]++;
				}
			}
		}
		return symbolAnzahlArray;
	}

	// risikokarten-change possible?
	public boolean changePossible() {
		boolean reihe = true;
		for (int i = 0; i < this.risikokartenKombi().length; i++) {
			// Pr�fen, ob drei gleiche Karten vorhanden sind
			if (this.risikokartenKombi()[i] > 2) {
				return true;
			}
			// Eine Reihe wird �berpr�ft, in dem ab dem Moment, wenn ein Symbol auf 0 ist,
			// der boolean reihe auf false gesetzt wird
			else if (risikokartenKombi()[i] == 0) {
				reihe = false;
			}
		}
		return reihe;
	}

	public boolean mussTauschen() {
		if (gezogeneRisikokarten.size() > 4) {
			return true;
		}
		return false;
	}

// wird nicht benutzt die methode
	public boolean auswahlPruefen(ArrayList<Risikokarte> arry) {
		if (arry.get(0).getSymbol() == arry.get(1).getSymbol() && arry.get(1).getSymbol() == arry.get(2).getSymbol()) { // alle
																														// sind
																														// gleich
			return true;
		} else if (arry.get(0).getSymbol() != arry.get(1).getSymbol()
				&& arry.get(0).getSymbol() != arry.get(2).getSymbol()
				&& arry.get(1).getSymbol() != arry.get(2).getSymbol()) { // alle sind unterschiedlich
			return true;
		} else {
			kartenWurdenEntfernt = false;
			return false;
		}
	}
	
	/*
	 * mit dieser methode werden die risikokarten entfernt, wenn sie eingetauscht wurden
	 * dies passiert mit einem array der laender, die auf den risikokarten stehen
	 */
	public void removeKarten(ArrayList<Land> kicked) {
		System.out.println("---------");
		for (int i = 0; i <  this.gezogeneRisikokarten.size(); i++) {
			for(Land kick: kicked) {
				if(this.gezogeneRisikokarten.get(i).getLand().equals(kick)) {
					this.gezogeneRisikokarten.remove(this.gezogeneRisikokarten.get(i));
				}
			}
			kartenWurdenEntfernt = true;
		}
	}

	public boolean getKartenWurdenEntfernt() {
		return kartenWurdenEntfernt;
	}

	public boolean isDead() { // TODO: @tobi muss wahrscheinlich nach jedem angriff kontrolliert werden
		if (inBesitz.size() <= 0) {
			return true;
		}
		return false;
	}

}

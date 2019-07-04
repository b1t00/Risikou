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
//	der uBlock enthaelt die durch einen angriff blockierten einheiten, diese stehen an dem index, der der landnummer entspricht, auf der die blockierten einheiten stehen 
	int[] uBlock = new int[42];
//	bei Einnahme eines Landes wird gutschriftEinheitenkarte auf true gesetzt
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

	public void setBlock(int indexLand, int units) {
		uBlock[indexLand] += units;
	}

//	Methode wird aufgerufen, wenn ein Zug beendet wird
	public boolean getGutschriftEinheitenkarte() {
		return gutschriftEinheitenkarte;
	}

	public void setGutschriftEinheitenkarte(boolean wert) {
		this.gutschriftEinheitenkarte = wert;
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

//	prueft, ob player land besitzt, wenn ja, loescht er es, wenn nein, fuegt er es hinzu
	public void setBesitz(Land land) {
		if (inBesitz.contains(land)) {
			inBesitz.remove(land);
		} else {
			inBesitz.add(land);
		}
	}

	// diese Methode beim Hinzufuegen von einzelnen Laendern, wahrscheinlich
	// ueberfluessig, erledigt sich mit setBesitz
	public void addLand(Land neuesLand) {
		this.inBesitz.add(neuesLand);
	}

// diese Methode beim Hinzufuegen von einem ganzen Laender-Array, am Anfang
	public void addLaender(ArrayList<Land> neueLaender) {
		this.inBesitz = neueLaender;
	}

//	------------------------------------------RisikokartenKombi-----------------------------------------------------------------------

	public int[] risikokartenKombi() {
		// erstellt Array mit den Anzahlen der Risikokarten für die verschiedenen
		// Symbole
		// 1. Stelle = Kanone, 2. Stelle = Reiter, 3. Stelle = Soldat
		int[] symbolAnzahlArray = { 0, 0, 0 };
		if (gezogeneRisikokarten.size() > 2) {
			for (Risikokarte karte : gezogeneRisikokarten) {
				if (karte.getSymbol() == Symbol.KANONE) {
					symbolAnzahlArray[0]++;
				} else if (karte.getSymbol().equals(Symbol.REITER)) {
					symbolAnzahlArray[1]++;
				} else if (karte.getSymbol().equals(Symbol.SOLDAT)) {
					symbolAnzahlArray[2]++;
				}
			}
		}
		return symbolAnzahlArray;
	}

//	risikokarten-change possible?
	public boolean changePossible() {
		boolean reihe = true;
		for (int i = 0; i < this.risikokartenKombi().length; i++) {
//			Pruefen, ob drei gleiche Karten vorhanden sind
			if (this.risikokartenKombi()[i] > 2) {
				return true;
			}
//			Eine Reihe wird ueberprueft, in dem ab dem Moment, wenn ein Symbol auf 0 ist, der boolean reihe auf false gesetzt wird
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

	/*
	 * auswahl wird direkt im client geprueft um die socket verbindung zu schonen,
	 * daher nicht ganz so sicher
	 */
//	public boolean auswahlPruefen(ArrayList<Risikokarte> arry) {
//		if (arry.get(0).getSymbol() == arry.get(1).getSymbol() && arry.get(1).getSymbol() == arry.get(2).getSymbol()) { 
//			return true;
//		} else if (arry.get(0).getSymbol() != arry.get(1).getSymbol()
//				&& arry.get(0).getSymbol() != arry.get(2).getSymbol()
//				&& arry.get(1).getSymbol() != arry.get(2).getSymbol()) { // alle sind unterschiedlich
//			return true;
//		} else {
//			kartenWurdenEntfernt = false;
//			return false;
//		}
//	}

	/*
	 * mit dieser methode werden die risikokarten entfernt, wenn sie eingetauscht
	 * wurden dies passiert mit einem array der laender, die auf den risikokarten
	 * stehen
	 */
	public void removeKarten(ArrayList<Land> kicked) {
		for (int i = 0; i < this.gezogeneRisikokarten.size(); i++) {
			for (Land kick : kicked) {
				if (this.gezogeneRisikokarten.get(i).getLand().equals(kick)) {
					this.gezogeneRisikokarten.remove(this.gezogeneRisikokarten.get(i));
				}
			}
			kartenWurdenEntfernt = true;
		}
	}

	public boolean getKartenWurdenEntfernt() {
		return kartenWurdenEntfernt;
	}

	public boolean isDead() {
		if (inBesitz.size() <= 0) {
			return true;
		}
		return false;
	}
}

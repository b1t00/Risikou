package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import ris.local.valueobjects.Risikokarte.Symbol;

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

	// müsste nicht statt gb direkt auf uBlock zugegriffen werden?
//	public int[] setBlock(int[] gB, int indexLand, int units) {
//		gB[indexLand] += units;
//		return gB;
//	}

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
	
	//TODO: nochmal exceptions checken
	public Land getLandById(int i) {
		for(Land l : inBesitz) {
			if(l.getNummer() == i) {
				return l;
			}
		}
		return null;
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

//	------------------------------------------RisikokartenKombi-----------------------------------------------------------------------
	// TODO: diese beiden methoden können bestimmt vereinfacht werden und
	// zusammengefasst werden

	public int[] risikokartenKombi() {
		// erstellt Array mit den Anzahlen der Risikokarten für die verschiedenen
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
			// Prüfen, ob drei gleiche Karten vorhanden sind
			if (this.risikokartenKombi()[i] > 2) {
				return true;
			}
			// Eine Reihe wird überprüft, in dem ab dem Moment, wenn ein Symbol auf 0 ist,
			// der boolean reihe auf false gesetzt wird
			else if (risikokartenKombi()[i] == 0) {
				reihe = false;
			}
		}
		return reihe;
	}
	
	public boolean mussTauschen() {
		if(gezogeneRisikokarten.size() > 4) {
			return true;
		}
		return false;
	}
	
// wird nicht benutzt die methode
	public boolean auswahlPruefen(ArrayList<Risikokarte> arry) {
		if (arry.get(0).getSymbol() == arry.get(1).getSymbol() && arry.get(1).getSymbol() == arry.get(2).getSymbol()) { //alle sind gleich 
			return true;
		} else if (arry.get(0).getSymbol() != arry.get(1).getSymbol() && arry.get(0).getSymbol() != arry.get(2).getSymbol() && arry.get(1).getSymbol() != arry.get(2).getSymbol()) { // alle sind unterschiedlich
			return true;
		} else {
			kartenWurdenEntfernt = false;
			return false;
		}
	}
//	
//	public ArrayList<Symbol> sammleAusgewaehlte(Symbol sym){
//		dreiAusgewaehltenKarten.add(sym);
//	}

//	-----------------------------------------------------------------------------------------------------------------------------------------

	// Methode bekommt eine int, stellvertretend für symbol (0 = Kanone, 1 = Reiter,
	// 3 = Soldat, 4 = je eine)
//	public void loescheRisikokarten(int symbol) {
//		String[] symbolArray = { "Kanone", "Reiter", "Soldat" };
//		int eingetauscht = 3;
//		while (eingetauscht > 0) {
//			// für den Fall, dass je eine Karte eingetauscht wird, wird zaehler hochgezählt,
//			// erst eine Kanone, dann Reiter, dann Soldat gelöscht
//			if (symbol == 3) {
//				// dafür muss der Risikokarten-Array zuerst sortiert werden
//				Collections.sort(this.gezogeneRisikokarten,
//						(karte1, karte2) -> karte1.getSymbol().compareTo(karte2.getSymbol()));
//				int zaehler = 0;
//				for (int i = 0; i < gezogeneRisikokarten.size(); i++) {
//					if (gezogeneRisikokarten.get(i).getSymbol().equals(symbolArray[zaehler])) {
//						gezogeneRisikokarten.remove(i);
//						eingetauscht--;
//						zaehler++;
//						i--;
//					}
//				}
//			} else {
//				for (int i = 0; i < gezogeneRisikokarten.size(); i++) {
//					if (gezogeneRisikokarten.get(i).getSymbol().equals(symbolArray[symbol])) {
//						gezogeneRisikokarten.remove(i);
//						eingetauscht--;
//					}
//				}
//			}
//		}
//		System.out.println("Karten wurden fertig eingetauscht, Anzahl: " + eingetauscht);
//	}
	
	public void removeKarten(ArrayList<Risikokarte> kicked) {
		System.out.println("---------");
		for (int i = 0; i <  this.gezogeneRisikokarten.size(); i++) {
			for(Risikokarte kick: kicked) {
				if(this.gezogeneRisikokarten.get(i).getLand().equals(kick.getLand())) {
					this.gezogeneRisikokarten.remove(kick);
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

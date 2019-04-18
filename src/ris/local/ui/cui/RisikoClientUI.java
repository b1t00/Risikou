package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import ris.local.domain.Risiko;
import ris.local.domain.Worldmanagement;

import ris.local.valueobjects.Player;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;

public class RisikoClientUI {

	private Risiko risiko;
	private BufferedReader in;

	// Konstruktor
	private RisikoClientUI() {
		risiko = new Risiko();
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	// einlesen von Konsole
	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	private String liesIntEingabe() throws IOException {
		return in.readLine();
	}

//	*******************Spielstart****************************

	private void starteSpiel() {
		boolean richtigeEingabe = false;
		String eingabe = "";
		while (!richtigeEingabe) {
			System.out.println("Lust Risiko zu spielen? (y/n) :	");
			try {
				eingabe = liesEingabe();
			} catch (IOException e) {
			}
			switch (eingabe) {
			case "yes":
			case "j":
			case "y":
				try {
					wieVieleSpielerMenu();
				} catch (IOException e) {
				}
				System.out.println("jetzt beginnt das Spiel --------- mit anderen absprechen"); // hier gucken..
				risiko.verteileEinheiten(); // was passiert wann ??
				risiko.verteileMissionen(); //
				richtigeEingabe = true;
				break;
			case "no":
			case "nö":
			case "n":
				System.out.println("Risik wird beendet"); // Platzhalter für Spielbeenden TODO: Spiel beenden
				richtigeEingabe = true;
				break;
			default:
				System.out.println("ungültige eingabe");
				richtigeEingabe = false;
			}
		}
	}

	public void wieVieleSpielerMenu() throws IOException {
		String eingabeSpieler, name;
		String farbe = "";
		int nr;
		System.out.println("Wieviele Spieler soll es geben? :");
		eingabeSpieler = liesEingabe();
		nr = Integer.parseInt(eingabeSpieler);
		for (int i = 1; i <= nr; i++) {
			System.out.println("Wie soll spieler " + i + " heißen? : ");
			name = liesEingabe();
			do {
				farbe = farbeAuswaehlen();
				farbe = risiko.setFarbeAuswaehlen(farbe);
				if (risiko.getRichtigeEingabe())
					System.out.println("Diese Farbe wurde schon vergeben : bitte eine andere farbe wählen!");
			} while (risiko.getRichtigeEingabe());

			risiko.spielerAnlegen(name, farbe, i);
		}
	}

	public String farbeAuswaehlen() {

		ArrayList<String> farbenAuswahl = risiko.getFarbauswahl();
		String farbe = "";

		System.out.println("Welche Farbe möchtest nehmen?");
		if (farbenAuswahl.contains("rot")) {
			System.out.println("r : rot");
		}
		if (farbenAuswahl.contains("gruen")) {
			System.out.println("g : grün");
		}
		if (farbenAuswahl.contains("blau")) {
			System.out.println("b : blau");
		}
		if (farbenAuswahl.contains("pink")) {
			System.out.println("p : pink");
		}
		if (farbenAuswahl.contains("schwarz")) {
			System.out.println("s : schwarz");
		}
		if (farbenAuswahl.contains("weiss")) {
			System.out.println("w : weiß");
		}
		try {
			farbe = liesEingabe();
		} catch (IOException e) {
		}
		return farbe;

	}

//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Spielstart^^^^^^^^^^^^^^^^^^^^^^

	// @annie: brauchen wir diese methode überhaupt?
	public void setzeStartEinheiten() {
		int anzahlEinheiten = risiko.getAnzahlPlayer() * 3;
		int einheit = 1;
		Land aktuellesLand = null;
		
		while(anzahlEinheiten > 0) {
			System.out.println(risiko.gibAktivenSpieler());
			Player aktiverPlayer = risiko.gibAktivenSpieler();
			
			System.out.println(aktiverPlayer + ": setze eine Einheit.");
			ArrayList <Land> aktiveLaender = aktiverPlayer.getBesitz();
			//den prüfarray brauchen wir, um zu überprüfen, ob die eingabe gültig ist, in den pruefarray werden die möglichen zahlen geschrieben
			ArrayList <Integer> pruefArray = new ArrayList <Integer>();
			
			for (Land land: aktiveLaender) {
				System.out.println(land.getNummer() + " > " + land.getName());
				pruefArray.add(land.getNummer()); 
			}
			int land;
			try{
				//!!! hier muss noch geprüft werden, ob die zahl überhaupt zur auswahl stand > evtl. arraylist land geben lassen und dann mit equals
				land = Integer.parseInt(liesEingabe());
				aktuellesLand = risiko.getLandById(land);
			} catch(IOException e) {}
			aktuellesLand.setEinheiten(einheit);
			anzahlEinheiten--;
			risiko.machNaechsterSpieler();	
		}
	}
	

	public void round() {
		String input = "";
		boolean spielzug;
		while (true) {
			Player aktiverPlayer = risiko.gibAktivenSpieler();
			System.out.println(aktiverPlayer + " ist am Zug.");
			spielzug = true;
			//spieler bekommt einheiten
			setzeNeueEinheiten(aktiverPlayer);

			while (spielzug) {
				gibMenuAus(aktiverPlayer);
				try {
					input = liesEingabe();
				} catch (IOException e) {
				}
				verarbeiteEingabe(input, aktiverPlayer);
				if (input.equals("z")) {
					spielzug = false;
				}
			}
		}
	}
	
	//----------------------------------einheiten-------------------------------------------------
	public void setzeNeueEinheiten(Player aktiverPlayer) {
		int verfuegbareEinheiten = risiko.errechneVerfuegbareEinheiten(aktiverPlayer);
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();
		int landWahl = 0;
		boolean ungültig = true;
		//information auf der konsole, wie sich die verteilung errechnet?
		System.out.println(aktiverPlayer + " setzt " + verfuegbareEinheiten + " Einheiten.");
		ArrayList<Land> laender = aktiverPlayer.getBesitz();
		while (verfuegbareEinheiten > 0) {
			System.out.println("Wo soll die Einheit gesetzt werden?");
			pruefArray=laenderAusgabe(laender);
			ungültig = true;
			while (ungültig) {	
				try {
					landWahl = Integer.parseInt(liesEingabe());
				} catch(IOException e) {}
				if (pruefArray.contains(landWahl)) {
					ungültig = false;
				} else {
					System.out.println("Ungültige Eingabe, bitte wiederholen!");
				}
			}
			Land landMitNeuerEinheit = risiko.getLandById(landWahl);
			System.out.println("Wie viele Einheiten sollen gesetzt werden? Maximal: " + verfuegbareEinheiten);
			int anzahl = 1;
			ungültig = true;
			while (ungültig) {
				try {
					anzahl = Integer.parseInt(liesEingabe());
				} catch(IOException e) {}
				if(anzahl > verfuegbareEinheiten) {
					System.out.println("Verfügbare Anzahl wurde überschritten. Maximal verfügbar: " + verfuegbareEinheiten);
				} else {
					ungültig = false;
				}
			}
			landMitNeuerEinheit.setEinheiten(anzahl);
			verfuegbareEinheiten -= anzahl;
		}
		System.out.println("Alle Einheiten wurden gesetzt.");
	}
	//----------------------------------einheiten-------------------------------------------------

	public void gibMenuAus(Player aktiverPlayer) {
		System.out.print(aktiverPlayer + ": Was möchtest du tun?");
		System.out.print("               \n Angreifen: a");
		System.out.print("               \n Einheiten verschieben: e");
		System.out.print("               \n Weltübersicht anzeigen: w");		
		System.out.print("               \n Länder und Einheiten anzeigen: l"); //gibt länder mit einheiten aus und ob ein kontinent eingenommen ist
		System.out.print("               \n Länder und Einheiten von möglichen Gegnern zeigen: f"); //gibt länder aus, die an die eigenen angrenzen, beide mit einheiten
		System.out.print("               \n Mission anzeigen: m"); //wird später implementiert
		System.out.print("               \n Zug beenden: z");	//TODO
		System.out.print("               \n Spiel beenden: q"); //TODO
		System.out.flush();
	}

	public void verarbeiteEingabe(String input, Player aktiverPlayer) {
			switch(input) {
			case "a":
				attack(aktiverPlayer);
				break;
			case "e":
				verschiebeEinheiten(aktiverPlayer);
				break;
			case "w":
				gibWeltAus();
				break;
			case "l":
				ArrayList<Land> landAusgabe = aktiverPlayer.getBesitz();
				laenderAusgabe(landAusgabe);
			case "z":
				risiko.machNaechsterSpieler();
				System.out.println(aktiverPlayer + " hat seinen Zug beendet.");
				break;
			case "q":
				System.out.println("Risik wird beendet."); //TODO: Spiel beenden
				break;
			default:
				System.out.println("Ungültige Eingabe, bitte wiederholen."); //funktioniert das so? @ annie hab mal eine whileschleife in der round gebaut
//				gibMenuAus(aktiverPlayer);
//				try {
//					input = liesEingabe();
//					verarbeiteEingabe(input, aktiverPlayer);
//				} catch(IOException e){}
			break;
		}
	}

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	public void attack(Player angreifer) {
		boolean ungültig = true;

		Land att = null;
		Land def = null;
		int attEinheiten = 0;
		int defEinheiten = 0;
		Player defender = null;

		// das pruefarray wird wiederholt genutzt, um fehlerhafte eingaben zu erkennen.
		// es wird bei mehreren eingaben überschrieben
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();

		// Abfrage, welches Land angreifen soll mit Berücksichtigung der Möglichkeit
		System.out.println(angreifer + ": mit welchem Land möchtest du angreifen?");
		ArrayList<Land> attackLaender = risiko.getAngriffsLaender(angreifer);
		pruefArray = laenderAusgabe(attackLaender);
		ungültig = true;
		while (ungültig) {
			int start = 0;
			try {
				start = Integer.parseInt(liesEingabe());
				att = risiko.getLandById(start);
			} catch (IOException e) {
			}
			if (pruefArray.contains(start)) {
				ungültig = false;
			} else {
				System.out.println("Ungültige Eingabe, bitte wiederholen!");
			}
		}

		// Abfrage, welches Land angegriffen werden soll
		System.out.println("Welches Land soll angegriffen werden?");
		ArrayList<Land> feindlicheNachbarn = risiko.getFeindlicheNachbarn(att);
		pruefArray = laenderAusgabe(feindlicheNachbarn);
		ungültig = true;
		while (ungültig) {
			int ziel = 0;
			try {
				ziel = Integer.parseInt(liesEingabe());
				def = risiko.getLandById(ziel);
				defender = def.getBesitzer();
			} catch (IOException e) {
			}
			if (pruefArray.contains(ziel)) {
				ungültig = false;
			} else {
				System.out.println("Ungültige Eingabe, bitte wiederholen!");
			}
		}

		// angriff befindet sich in while-schleife, falls wiederholt angegriffen werden
		// soll
		boolean kampf = true;

		while (kampf) {
			// Abfrage, wie viele Einheiten angreifen
			ungültig = true;
			System.out.println(angreifer + ": mit wie viel Einheiten soll angegriffen werden? Verfügbar: "
					+ (att.getEinheiten() - 1) + ", maximal möglich: 3");
			while (ungültig) {
				try {
					attEinheiten = Integer.parseInt(liesEingabe());
				} catch (IOException e) {
				}
				if (attEinheiten > (att.getEinheiten() - 1) || attEinheiten > 3) {
					System.out.println("Ungültige Eingabe, bitte wiederholen");
				} else {
					ungültig = false;
				}
			}
			// Abfrage, wie viele Einheiten verteidigen
			ungültig = true;
			System.out.println(defender + ": mit wie viel Einheiten soll verteidigt werden? Maximal möglich: "
					+ def.getEinheiten());
			while (ungültig) {
				try {
					defEinheiten = Integer.parseInt(liesEingabe());
				} catch (IOException e) {
				}
				if (defEinheiten > 3 || defEinheiten > def.getEinheiten()) { // statt 3 -> 2
					System.out.println("Ungültige Eingabe, bitte wiederholen");
				} else {
					ungültig = false;
				}
			}

			System.out.println(angreifer + " greift mit " + att + " und " + attEinheiten + " Einheiten an.");
			System.out.println(defender + " verteidigt mit " + defEinheiten + " Einheiten.");

			// arrayList(0) > verlorene einheiten von attack, arrayList(1) > verlorene
			// einheiten von defense
			ArrayList<Integer> ergebnis = risiko.attack(att, def, attEinheiten, defEinheiten);

			// je nach Ausgang des Kampfs unterschiedliche fortgänge:

			// 1. angreifer hat gewonnen -> sollen weitere Einheiten verschoben werden?
			if (ergebnis.get(0) < ergebnis.get(1)) {
				System.out.println(angreifer + " hat gewonnen!");
				System.out.println(angreifer + " verliert: " + ergebnis.get(1) + " Einheiten.");// TODO: beide Zeilen
																								// wiederholen sich,
																								// Möglichkeit
																								// auszulagern?
				System.out.println(defender + " verliert: " + ergebnis.get(0) + " Einheiten.");
				// abfrage, ob weitere einheiten verschoben werden sollen, wenn dies möglich ist
				if (att.getEinheiten() > 1) {
					int answer = 0;
					System.out.println(
							"Wieviele Einheiten sollen auf das eroberte Land verschoben werden (auch 0 möglich)? Maximal: "
									+ (att.getEinheiten() - 1));
					ungültig = true;
					while (ungültig) {
						try {
							answer = Integer.parseInt(liesEingabe());
						} catch (IOException e) {
						}
						if (answer > (att.getEinheiten() - 1)) {
							System.out.println("Ungültige Eingabe, bitte wiederholen!");
						} else {
							ungültig = false;
						}
					}

					risiko.verschiebeEinheiten(att, def, answer);
				}
				System.out.println("Angriff ist beendet.");
				// änderung des boolean-werts verlässt den kampf und kehrt zum menü zurück
				kampf = false;

				// 2. + 3. angreifer hat verloren/unentschieden -> soll wieder angegriffen
				// werden? (wenn genug einheiten verbleiben)
			} else {
				if (ergebnis.get(0) > ergebnis.get(1)) {
					System.out.println(angreifer + " hat verloren!");
				} else {
					System.out.println("Unentschieden!");
				}
				System.out.println(angreifer + " verliert: " + ergebnis.get(1) + " Einheiten.");
				System.out.println(defender + " verliert: " + ergebnis.get(0) + " Einheiten.");
				if (att.getEinheiten() > 0) {
					System.out.println("Soll erneut angegriffen werden? (na klar/auf gar keinen fall)");
					String answer = "";
					try {
						answer = liesEingabe();
					} catch (IOException e) {
					}
					switch (answer) {
					case "na klar":
						// bricht switch-abfrage ab und kehrt an den anfang der while-schleife
						break;
					case "auf gar keinen fall":
						// änderung des boolean-werts verlässt den kampf und kehrt zum menü zurück
						kampf = false;
						break;
					}
				}
			}
		}
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public ArrayList<Integer> laenderAusgabe(ArrayList<Land> ausgabeLaender) {
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();
		for (Land land : ausgabeLaender) {
			System.out.println(land.getNummer() + " > " + land.getName());
			pruefArray.add(land.getNummer());
		}
		return pruefArray;
	}

	//WELT AUSGABE ->
	public void gibWeltAus() {
		ArrayList<Land> alleLaender = risiko.gibWeltAus();
		//gibt erst aus, wer welche Länder besitzt
		for (Land land: alleLaender) {
			System.out.println(land.getName() + " wird besitzt von " + land.getBesitzer().getName() + " mit " + land.getEinheiten() + " Einheiten.");
		}
		ArrayList<Kontinent> alleKontinente = risiko.gibAlleKontinente();
		ArrayList<Player> allePlayer = risiko.gibAlleSpieler();
		for (Kontinent kontinent: alleKontinente) {
			//gibt dann Kontinente mit dazugehörigen Ländern aus TODO: könnte ausgelagert werden, sodass gezielt darauf zugegriffen werden kann
			ArrayList<Land> eigeneLaender = kontinent.getLaender();
			System.out.println(kontinent.getName() + " besteht aus: ");
			for (Land land: eigeneLaender) {
				System.out.println(land.getName());
			}
			//wenn der Kontinent im Besitz eines Player ist, wird dies ausgegeben
			for (Player player: allePlayer) {
				if (kontinent.isOwnedByPlayer(player)) {
					System.out.println(player.getName() + " besitzt " + kontinent.getName());
				}
			}
		}
	}

	public void verschiebeEinheiten(Player aktiverPlayer) {
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();
		Land start = null;
		Land ziel = null;
		int anzahl = 0;
		System.out.println("Einheiten verschieben von: \n");
		ArrayList<Land> ausgabeLaender = aktiverPlayer.getBesitz();
		pruefArray = laenderAusgabe(ausgabeLaender);
		boolean ungültig = true;
		while (ungültig) {
			int von;
			try {
				von = Integer.parseInt(liesEingabe());
				start = risiko.getLandById(von);
			} catch(IOException e) {}
			if (pruefArray.contains(start)) {
				ungültig = false;
			} else {
				System.out.println("Ungültige Eingabe, bitte wiederholen!");
			}
		}
		System.out.println("Anzahl der Einheiten: (Maximal) " + (start.getEinheiten() - 1));
		ungültig = true;
		while (ungültig) {
			try {
				anzahl = Integer.parseInt(liesEingabe());
			} catch(IOException e) {}
			if (anzahl > (start.getEinheiten()-1)) {
				System.out.println("Ungültige Eingabe, bitte wiederholen!");
			} else {
				ungültig = false;
			}
		}
		System.out.println("Einheiten verschieben nach: \n");
		ArrayList <Land> nachbarLaender = risiko.getEigeneNachbarn(start);
		pruefArray = laenderAusgabe(nachbarLaender);
		ungültig = true;
		while(ungültig) {
			try {
				int nach = Integer.parseInt(liesEingabe());
				risiko.getLandById(nach);
			} catch(IOException e) {}
			if (pruefArray.contains(ziel)) {
				ungültig = false;
			} else {
				System.out.println("Ungültige Eingabe, bitte wiederholen!");
			}
		}
		risiko.verschiebeEinheiten(start, ziel, anzahl);
	}

	public void gibLaenderUndNummerVonSpielerAus(Player play) {
		for (int i = 0; i < play.getBesitz().size(); i++) {
			System.out.println(play.gibLaenderUndNummer().get(i) + " gehört " + play.getName());
		}
	}

	// @tobi // nur für testzwecke, da die missionen ja nicht sichtbar sein sollen
	public void gibSpielerMissionUndLaenderAus() {
		for (Player player : risiko.getSpielerArray()) {
			System.out.println("Spieler nr." + player.getNummer() + " : " + player.getName() + " hat die farbe - "
					+ player.getFarbe());
			System.out.println("und hat die Mission : \n" + player.getMission());
			for (int i = 0; i < player.getBesitz().size(); i++) {
				System.out.println(player.getBesitz().get(i).getNummer() + " : " + player.getBesitz().get(i).getName());
			}
			System.out.println();
		}
	}

	public void run() {
		starteSpiel();
		gibSpielerMissionUndLaenderAus();
//		****************_hier_gehts_los********
		round();

	}

	public static void main(String[] args) {
		RisikoClientUI cui = new RisikoClientUI();
		cui.run();
	}

}

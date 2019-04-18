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
			case "n�":
			case "n":
				System.out.println("Risik wird beendet"); // Platzhalter f�r Spielbeenden TODO: Spiel beenden
				richtigeEingabe = true;
				break;
			default:
				System.out.println("ung�ltige eingabe");
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
			System.out.println("Wie soll spieler " + i + " hei�en? : ");
			name = liesEingabe();
			do {
				farbe = farbeAuswaehlen();
				farbe = risiko.setFarbeAuswaehlen(farbe);
				if (risiko.getRichtigeEingabe())
					System.out.println("Diese Farbe wurde schon vergeben : bitte eine andere farbe w�hlen!");
			} while (risiko.getRichtigeEingabe());

			risiko.spielerAnlegen(name, farbe, i);
		}
	}

	public String farbeAuswaehlen() {

		ArrayList<String> farbenAuswahl = risiko.getFarbauswahl();
		String farbe = "";

		System.out.println("Welche Farbe m�chtest nehmen?");
		if (farbenAuswahl.contains("rot")) {
			System.out.println("r : rot");
		}
		if (farbenAuswahl.contains("gruen")) {
			System.out.println("g : gr�n");
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
			System.out.println("w : wei�");
		}
		try {
			farbe = liesEingabe();
		} catch (IOException e) {
		}
		return farbe;

	}

//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Spielstart^^^^^^^^^^^^^^^^^^^^^^

	// @annie: brauchen wir diese methode �berhaupt?
	public void setzeStartEinheiten() {
		int anzahlEinheiten = risiko.getAnzahlPlayer() * 3;
		int einheit = 1;
		Land aktuellesLand = null;
		
		while(anzahlEinheiten > 0) {
			System.out.println(risiko.gibAktivenSpieler());
			Player aktiverPlayer = risiko.gibAktivenSpieler();
			
			System.out.println(aktiverPlayer + ": setze eine Einheit.");
			ArrayList <Land> aktiveLaender = aktiverPlayer.getBesitz();
			//den pr�farray brauchen wir, um zu �berpr�fen, ob die eingabe g�ltig ist, in den pruefarray werden die m�glichen zahlen geschrieben
			ArrayList <Integer> pruefArray = new ArrayList <Integer>();
			
			for (Land land: aktiveLaender) {
				System.out.println(land.getNummer() + " > " + land.getName());
				pruefArray.add(land.getNummer()); 
			}
			int land;
			try{
				//!!! hier muss noch gepr�ft werden, ob die zahl �berhaupt zur auswahl stand > evtl. arraylist land geben lassen und dann mit equals
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
		boolean ung�ltig = true;
		//information auf der konsole, wie sich die verteilung errechnet?
		System.out.println(aktiverPlayer + " setzt " + verfuegbareEinheiten + " Einheiten.");
		ArrayList<Land> laender = aktiverPlayer.getBesitz();
		while (verfuegbareEinheiten > 0) {
			System.out.println("Wo soll die Einheit gesetzt werden?");
			pruefArray=laenderAusgabe(laender);
			ung�ltig = true;
			while (ung�ltig) {	
				try {
					landWahl = Integer.parseInt(liesEingabe());
				} catch(IOException e) {}
				if (pruefArray.contains(landWahl)) {
					ung�ltig = false;
				} else {
					System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
				}
			}
			Land landMitNeuerEinheit = risiko.getLandById(landWahl);
			System.out.println("Wie viele Einheiten sollen gesetzt werden? Maximal: " + verfuegbareEinheiten);
			int anzahl = 1;
			ung�ltig = true;
			while (ung�ltig) {
				try {
					anzahl = Integer.parseInt(liesEingabe());
				} catch(IOException e) {}
				if(anzahl > verfuegbareEinheiten) {
					System.out.println("Verf�gbare Anzahl wurde �berschritten. Maximal verf�gbar: " + verfuegbareEinheiten);
				} else {
					ung�ltig = false;
				}
			}
			landMitNeuerEinheit.setEinheiten(anzahl);
			verfuegbareEinheiten -= anzahl;
		}
		System.out.println("Alle Einheiten wurden gesetzt.");
	}
	//----------------------------------einheiten-------------------------------------------------

	public void gibMenuAus(Player aktiverPlayer) {
		System.out.print(aktiverPlayer + ": Was m�chtest du tun?");
		System.out.print("               \n Angreifen: a");
		System.out.print("               \n Einheiten verschieben: e");
		System.out.print("               \n Welt�bersicht anzeigen: w");		
		System.out.print("               \n L�nder und Einheiten anzeigen: l"); //gibt l�nder mit einheiten aus und ob ein kontinent eingenommen ist
		System.out.print("               \n L�nder und Einheiten von m�glichen Gegnern zeigen: f"); //gibt l�nder aus, die an die eigenen angrenzen, beide mit einheiten
		System.out.print("               \n Mission anzeigen: m"); //wird sp�ter implementiert
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
				System.out.println("Ung�ltige Eingabe, bitte wiederholen."); //funktioniert das so? @ annie hab mal eine whileschleife in der round gebaut
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
		boolean ung�ltig = true;

		Land att = null;
		Land def = null;
		int attEinheiten = 0;
		int defEinheiten = 0;
		Player defender = null;

		// das pruefarray wird wiederholt genutzt, um fehlerhafte eingaben zu erkennen.
		// es wird bei mehreren eingaben �berschrieben
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();

		// Abfrage, welches Land angreifen soll mit Ber�cksichtigung der M�glichkeit
		System.out.println(angreifer + ": mit welchem Land m�chtest du angreifen?");
		ArrayList<Land> attackLaender = risiko.getAngriffsLaender(angreifer);
		pruefArray = laenderAusgabe(attackLaender);
		ung�ltig = true;
		while (ung�ltig) {
			int start = 0;
			try {
				start = Integer.parseInt(liesEingabe());
				att = risiko.getLandById(start);
			} catch (IOException e) {
			}
			if (pruefArray.contains(start)) {
				ung�ltig = false;
			} else {
				System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
			}
		}

		// Abfrage, welches Land angegriffen werden soll
		System.out.println("Welches Land soll angegriffen werden?");
		ArrayList<Land> feindlicheNachbarn = risiko.getFeindlicheNachbarn(att);
		pruefArray = laenderAusgabe(feindlicheNachbarn);
		ung�ltig = true;
		while (ung�ltig) {
			int ziel = 0;
			try {
				ziel = Integer.parseInt(liesEingabe());
				def = risiko.getLandById(ziel);
				defender = def.getBesitzer();
			} catch (IOException e) {
			}
			if (pruefArray.contains(ziel)) {
				ung�ltig = false;
			} else {
				System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
			}
		}

		// angriff befindet sich in while-schleife, falls wiederholt angegriffen werden
		// soll
		boolean kampf = true;

		while (kampf) {
			// Abfrage, wie viele Einheiten angreifen
			ung�ltig = true;
			System.out.println(angreifer + ": mit wie viel Einheiten soll angegriffen werden? Verf�gbar: "
					+ (att.getEinheiten() - 1) + ", maximal m�glich: 3");
			while (ung�ltig) {
				try {
					attEinheiten = Integer.parseInt(liesEingabe());
				} catch (IOException e) {
				}
				if (attEinheiten > (att.getEinheiten() - 1) || attEinheiten > 3) {
					System.out.println("Ung�ltige Eingabe, bitte wiederholen");
				} else {
					ung�ltig = false;
				}
			}
			// Abfrage, wie viele Einheiten verteidigen
			ung�ltig = true;
			System.out.println(defender + ": mit wie viel Einheiten soll verteidigt werden? Maximal m�glich: "
					+ def.getEinheiten());
			while (ung�ltig) {
				try {
					defEinheiten = Integer.parseInt(liesEingabe());
				} catch (IOException e) {
				}
				if (defEinheiten > 3 || defEinheiten > def.getEinheiten()) { // statt 3 -> 2
					System.out.println("Ung�ltige Eingabe, bitte wiederholen");
				} else {
					ung�ltig = false;
				}
			}

			System.out.println(angreifer + " greift mit " + att + " und " + attEinheiten + " Einheiten an.");
			System.out.println(defender + " verteidigt mit " + defEinheiten + " Einheiten.");

			// arrayList(0) > verlorene einheiten von attack, arrayList(1) > verlorene
			// einheiten von defense
			ArrayList<Integer> ergebnis = risiko.attack(att, def, attEinheiten, defEinheiten);

			// je nach Ausgang des Kampfs unterschiedliche fortg�nge:

			// 1. angreifer hat gewonnen -> sollen weitere Einheiten verschoben werden?
			if (ergebnis.get(0) < ergebnis.get(1)) {
				System.out.println(angreifer + " hat gewonnen!");
				System.out.println(angreifer + " verliert: " + ergebnis.get(1) + " Einheiten.");// TODO: beide Zeilen
																								// wiederholen sich,
																								// M�glichkeit
																								// auszulagern?
				System.out.println(defender + " verliert: " + ergebnis.get(0) + " Einheiten.");
				// abfrage, ob weitere einheiten verschoben werden sollen, wenn dies m�glich ist
				if (att.getEinheiten() > 1) {
					int answer = 0;
					System.out.println(
							"Wieviele Einheiten sollen auf das eroberte Land verschoben werden (auch 0 m�glich)? Maximal: "
									+ (att.getEinheiten() - 1));
					ung�ltig = true;
					while (ung�ltig) {
						try {
							answer = Integer.parseInt(liesEingabe());
						} catch (IOException e) {
						}
						if (answer > (att.getEinheiten() - 1)) {
							System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
						} else {
							ung�ltig = false;
						}
					}

					risiko.verschiebeEinheiten(att, def, answer);
				}
				System.out.println("Angriff ist beendet.");
				// �nderung des boolean-werts verl�sst den kampf und kehrt zum men� zur�ck
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
						// �nderung des boolean-werts verl�sst den kampf und kehrt zum men� zur�ck
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
		//gibt erst aus, wer welche L�nder besitzt
		for (Land land: alleLaender) {
			System.out.println(land.getName() + " wird besitzt von " + land.getBesitzer().getName() + " mit " + land.getEinheiten() + " Einheiten.");
		}
		ArrayList<Kontinent> alleKontinente = risiko.gibAlleKontinente();
		ArrayList<Player> allePlayer = risiko.gibAlleSpieler();
		for (Kontinent kontinent: alleKontinente) {
			//gibt dann Kontinente mit dazugeh�rigen L�ndern aus TODO: k�nnte ausgelagert werden, sodass gezielt darauf zugegriffen werden kann
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
		boolean ung�ltig = true;
		while (ung�ltig) {
			int von;
			try {
				von = Integer.parseInt(liesEingabe());
				start = risiko.getLandById(von);
			} catch(IOException e) {}
			if (pruefArray.contains(start)) {
				ung�ltig = false;
			} else {
				System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
			}
		}
		System.out.println("Anzahl der Einheiten: (Maximal) " + (start.getEinheiten() - 1));
		ung�ltig = true;
		while (ung�ltig) {
			try {
				anzahl = Integer.parseInt(liesEingabe());
			} catch(IOException e) {}
			if (anzahl > (start.getEinheiten()-1)) {
				System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
			} else {
				ung�ltig = false;
			}
		}
		System.out.println("Einheiten verschieben nach: \n");
		ArrayList <Land> nachbarLaender = risiko.getEigeneNachbarn(start);
		pruefArray = laenderAusgabe(nachbarLaender);
		ung�ltig = true;
		while(ung�ltig) {
			try {
				int nach = Integer.parseInt(liesEingabe());
				risiko.getLandById(nach);
			} catch(IOException e) {}
			if (pruefArray.contains(ziel)) {
				ung�ltig = false;
			} else {
				System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
			}
		}
		risiko.verschiebeEinheiten(start, ziel, anzahl);
	}

	public void gibLaenderUndNummerVonSpielerAus(Player play) {
		for (int i = 0; i < play.getBesitz().size(); i++) {
			System.out.println(play.gibLaenderUndNummer().get(i) + " geh�rt " + play.getName());
		}
	}

	// @tobi // nur f�r testzwecke, da die missionen ja nicht sichtbar sein sollen
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

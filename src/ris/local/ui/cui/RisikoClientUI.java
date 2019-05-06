package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Mission;
import ris.local.valueobjects.Player;

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
					wieVielePlayerMenu();
				} catch (IOException e) {
				}
				risiko.verteileEinheiten();
				// hier abfrage ob mit missionen gespielt werden soll oder nicht
				risiko.verteileMissionen();
				risiko.setzeAktivenPlayer();
				System.out.println("jetzt beginnt das Spiel \n");
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
//public int pruefeZahl(int i) { // methode f�r falls zahleneingabe falsch ist..macht code k�rzer
//	try {
//		
//	}
//}
	public void wieVielePlayerMenu() throws IOException {
		String eingabePlayer;
		String name = "";
		String farbe = "";
		int nr = 0;
		boolean richtigeEingabe = false;
		while (!richtigeEingabe) {
			System.out.println("Wieviele Spieler soll es geben? (min 2 | Max 6) :");
			try {
				eingabePlayer = liesEingabe();
				
				nr = Integer.parseInt(eingabePlayer);
			} catch (IOException | NumberFormatException e) {
				// @tobi die Frage ob wir in solchen F�llen mit Exceptions arbeiten sollen oder
				// nicht..
				richtigeEingabe = false;
				System.err.println("ung�ltige Eingabe. Bitte wiederholen \n");
			}
			if (nr < 2 || nr > 6) {
				richtigeEingabe = false;
			} else {
				richtigeEingabe = true;
			}
		}
		System.out.println();
		for (int i = 0; i < nr; i++) {
			boolean schlechterName = true;
			System.out.println("Wie soll Player " + (i + 1) + " hei�en? : ");
			while (schlechterName) {
				name = liesEingabe();
				schlechterName = false;
				for (Player player : risiko.getPlayerArray()) {
					if (name.equals(player.getName())) {
						System.out.println("Diesen Name wurde schon vergeben: Bitte Eingabe wiederholen");
						schlechterName = true;
					}
				}
				if (name.equals("")) {
					System.out.println("Ung�ltiger Name: Bitte Eingabe wiederholen");
					schlechterName = true;
				}

			}

			do {
				farbe = farbeAuswaehlen();
				farbe = risiko.setFarbeAuswaehlen(farbe);
				if (risiko.getRichtigeEingabe())
					System.out.println(
							"Diese Farbe wurde schon vergeben oder es gibt die Farbe nicht : bitte w�hle nochmal eine Farbe!");
			} while (risiko.getRichtigeEingabe());

			risiko.PlayerAnlegen(name, farbe, i);
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

	// verteilt zu beginn des spiels starteinheiten, pro Spieler 3 Einheiten
	public void setzeStartEinheiten() {
		//die gesamte Anzahl der verteilten Einheiten wird gespeichert und mit jedem Setzen heruntergez�hlt
		int anzahlEinheiten = risiko.getAnzahlPlayer() * 3;
		int einheit = 1;
		Land aktuellesLand = null;
		boolean ung�ltig = true;

		while (anzahlEinheiten > 0) {
			Player aktiverPlayer = risiko.gibAktivenPlayer();
			System.out.println("");
			System.out.println(aktiverPlayer + ": setze eine Einheit.");
			ArrayList<Land> aktiveLaender = aktiverPlayer.getBesitz();
			System.out.println("");			
			ArrayList<Integer> pruefArray =  laenderAusgabe(aktiveLaender);
			
			ung�ltig = true;
			while(ung�ltig) {
				int land = -1;
				try {
					land = Integer.parseInt(liesEingabe());
				} catch (IOException e) {}
				if(pruefArray.contains(land)) {
					aktuellesLand = risiko.getLandById(land);
					ung�ltig = false;
				} else {
					System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
				}
			}
			
			aktuellesLand.setEinheiten(einheit);
			anzahlEinheiten--;
			risiko.machNaechsterPlayer();
		}
	}

	public void round() {
		String input = "";
		boolean spielzug;
		boolean nichtVerschoben = true;
		while (true) {
			Player aktiverPlayer = risiko.gibAktivenPlayer();
			System.out.println(aktiverPlayer + " ist am Zug.");
			System.out.println("");
			spielzug = true;
			// Player bekommt einheiten
			setzeNeueEinheiten(aktiverPlayer);

			while (spielzug) {
				System.out.println("");
				gibMenuAus(aktiverPlayer, nichtVerschoben);
				try {
					input = liesEingabe();
				} catch (IOException e) {
				}
				verarbeiteEingabe(input, aktiverPlayer, nichtVerschoben);
				if (input.equals("z")) {
					spielzug = false;
					nichtVerschoben = true;
				}
				if (input.equals("e")) {
					nichtVerschoben = false;
				}
			}
		}
	}

	// ----------------------------------einheiten-------------------------------------------------
	public void setzeNeueEinheiten(Player aktiverPlayer) {
		int verfuegbareEinheiten = risiko.errechneVerfuegbareEinheiten(aktiverPlayer);
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();
		int landWahl = 0;
		boolean ung�ltig = true;
		// information auf der konsole, wie sich die verteilung errechnet?
		System.out.println(aktiverPlayer + " setzt " + verfuegbareEinheiten + " Einheiten.");
		ArrayList<Land> laender = aktiverPlayer.getBesitz();
		while (verfuegbareEinheiten > 0) {
			System.out.println("Wo sollen die Einheiten gesetzt werden?");
			pruefArray = laenderAusgabe(laender);
			ung�ltig = true;
			while (ung�ltig) {
				try {
					landWahl = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					landWahl = -99;
				}
				if (pruefArray.contains(landWahl)) {
					ung�ltig = false;
				} else {
					System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
				}
			}
			Land landMitNeuerEinheit = risiko.getLandById(landWahl);
			int anzahl = 0; // @tobi darf man hier auch 0 hinschreiben??
			ung�ltig = true;
			while (ung�ltig) {
				System.out.println("Wie viele Einheiten sollen gesetzt werden? Maximal: " + verfuegbareEinheiten);
				try {
					anzahl = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					if (anzahl == 0) {
						anzahl = -1000;
					} else {
						ung�ltig = true;
					}

				}
				if (anzahl == 0) {
					System.out.println("Bitte wiederholen");
					ung�ltig = false;
				} else if (anzahl > verfuegbareEinheiten) {
					System.out.println(
							"Verf�gbare Anzahl wurde �berschritten. Maximal verf�gbar: " + verfuegbareEinheiten);
				} else if (anzahl < 1) {
					System.err.println("Geht nicht");
				} else {
					ung�ltig = false;
				}
			}
			landMitNeuerEinheit.setEinheiten(anzahl);
			verfuegbareEinheiten -= anzahl;
		}
		System.out.println("Alle Einheiten wurden gesetzt.");
	}
	// ----------------------------------einheiten-------------------------------------------------

	public void gibMenuAus(Player aktiverPlayer, boolean nichtVerschoben) {
		System.out.print(aktiverPlayer + ": Was m�chtest du tun?");
		if (nichtVerschoben) {
			System.out.print("\n   Angreifen: a");
		}
		System.out.print("\n   Einheiten verschieben: e");
		System.out.print("\n   Zug beenden: z");
		System.out.println("\n   Spiel beenden: q \n"); // TODO
		System.out.print("**Informationen anzeigen:**");
		System.out.print("\n   Welt�bersicht anzeigen: w");
		System.out.print("\n   L�nder und Einheiten anzeigen: l"); // gibt l�nder mit einheiten aus und ob ein kontinent
																	// eingenommen ist
		System.out.print("\n   L�nder und Einheiten von m�glichen Gegnern zeigen: f"); // gibt l�nder aus, die an die
																						// eigenen angrenzen, beide mit
																						// einheiten
		System.out.print("\n   Mission anzeigen: m \n"); // wird sp�ter implementiert
		System.out.flush();
	}

	public void verarbeiteEingabe(String input, Player aktiverPlayer, boolean nichtVerschoben) {
		switch (input) {
		case "a":
			if(nichtVerschoben) {
			attack(aktiverPlayer);
			} else {
				System.out.println("Ung�ltige Eingabe, bitte wiederholen.");
			}
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
			break;
		case "z":
			risiko.machNaechsterPlayer();
			System.out.println(aktiverPlayer + " hat seinen Zug beendet.");
			break;
		case "q":
			System.out.println("Risik wird beendet."); // TODO: Spiel beenden
			break;
		case "m":
			System.out.println(aktiverPlayer.getMission());
			break;
		default:
			System.out.println("Ung�ltige Eingabe, bitte wiederholen."); // funktioniert das so? @ annie hab mal eine
																			// whileschleife in der round gebaut
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
		if (risiko.getAngriffsLaender(angreifer).size() == 0) {
			System.out.println("Du kannst leider niemanden angreifen...");
			return;
		}
		// Abfrage, welches Land angreifen soll, es werden dabei nur L�nder ausgegeben,
		// die angreifen k�nnen
		System.out.println(angreifer + ": mit welchem Land m�chtest du angreifen?");
		ArrayList<Land> attackLaender = risiko.getAngriffsLaender(angreifer);
		pruefArray = laenderAusgabe(attackLaender);
		ung�ltig = true;
		while (ung�ltig) {
			int start = 0;
			try {
				start = Integer.parseInt(liesEingabe());
				att = risiko.getLandById(start);
			} catch (IOException | NumberFormatException e) {
				start = -1;
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
			} catch (IOException | NumberFormatException e) {
				ziel = -99; // dadurch wird eingabe ungueltig
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
					+ (att.getEinheiten() - 1));
			if (att.getEinheiten() - 1 > 3) {
				System.out.println(" Maximal m�glich: 3");
			}
			while (ung�ltig) {
				try {
					attEinheiten = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					ung�ltig = true;
				}
				if (attEinheiten > (att.getEinheiten() - 1) || attEinheiten > 3 || attEinheiten == 0) {
					System.out.println("Ung�ltige Eingabe, bitte wiederholen");
				} else {
					ung�ltig = false;
				}
			}
			// Abfrage, wie viele Einheiten verteidigen
			ung�ltig = true;
			System.out.println(defender + ": mit wie viel Einheiten soll verteidigt werden? Mindestens 1, Du hast: "
					+ def.getEinheiten());
			if (def.getEinheiten() > 2) {
				System.out.println("Maximal 2");
			}
			while (ung�ltig) {
				try {
					defEinheiten = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					System.out.println("keine gueltige eingabe");
					ung�ltig = true;
				}
				if (defEinheiten > 2 || defEinheiten > def.getEinheiten() || defEinheiten == 0) {
					System.out.println("Ung�ltige Eingabe, bitte wiederholen");
				} else {
					ung�ltig = false;
				}
			}

			System.out.print(angreifer + " greift mit " + att + " und " + attEinheiten);
			if (attEinheiten == 1) {
				System.out.println(" Einheit an.");
			} else {
				System.out.println(" Einheiten an.");
			}
			System.out.print(defender + " verteidigt " + def.getName() + " mit " + defEinheiten);
			if (defEinheiten == 1) {
				System.out.println(" Einheit.");
			} else {
				System.out.println(" Einheiten.");
			}
			System.out.println("");
			// arrayList(0) > verlorene einheiten von attack, arrayList(1) > verlorene
			// einheiten von defense
			ArrayList<Integer>aList=risiko.diceAttack(attEinheiten);
			ArrayList<Integer>dList=risiko.diceDefense(defEinheiten);
			for(int i=0;i<aList.size();i++) {
				System.out.println("Angreifender W�rfel Nr."+i+" = "+aList.get(i));
			}
			
			for(int i=0;i<dList.size();i++) {
				System.out.println("Verteidigender W�rfel Nr."+i+" = "+dList.get(i));
			}
			
			ArrayList<Integer> ergebnis = risiko.attack(att, def, attEinheiten, defEinheiten,aList,dList);

			// je nach Ausgang des Kampfs unterschiedliche fortg�nge:

			
			// 1. angreifer hat gewonnen, aber die Verteidigung hat weitere L�nder
			if (ergebnis.get(0) > ergebnis.get(1) && def.getBesitzer().equals(defender)) {
				System.out.println(angreifer + " hat gewonnen.");
				System.out.print(angreifer + " verliert: " + ergebnis.get(0));
				if (ergebnis.get(0) == -1) {
					System.out.println(" Einheit.");// TODO: beide Zeilen wiederholen sich, auslagern?
				} else {
					System.out.println(" Einheiten.");
				}

				System.out.print(defender + " verliert: " + ergebnis.get(1));
				if (ergebnis.get(1) == -1) {
					System.out.println(" Einheit.");// TODO: beide Zeilen wiederholen sich, auslagern?
				} else {
					System.out.println(" Einheiten.");
				}
				
				System.out.println("");
				System.out.println("Soll erneut angegriffen werden? (na klar/auf gar keinen fall)");
				String answer = "";
				try {
					answer = liesEingabe();
				} catch (IOException e) {}
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
			
			//2. Angreifer gewinnt und erobert das Land
			else if(def.getBesitzer().equals(angreifer)) {
				System.out.println(angreifer + " hat gewonnen und erobert " + def.getName() + ".");
				System.out.print(angreifer + " verliert: " + ergebnis.get(0));
				if (ergebnis.get(0) == -1) {
					System.out.println(" Einheit.");// TODO: beide Zeilen wiederholen sich, auslagern?
				} else {
					System.out.println(" Einheiten.");
				}

				System.out.print(defender + " verliert: " + ergebnis.get(1));
				if (ergebnis.get(1) == -1) {
					System.out.println(" Einheit.");// TODO: beide Zeilen wiederholen sich, auslagern?
				} else {
					System.out.println(" Einheiten.");
				}
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
						} catch (IOException | NumberFormatException e) {
							answer = -99;
						}
						if (answer > (att.getEinheiten() - 1)) {
							System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
						} else {
							ung�ltig = false;
						}
					}
					risiko.verschiebeEinheiten(att, def, answer);
				}
				System.out.println("Der Angriff ist beendet.");
				// �nderung des boolean-werts verl�sst den kampf und kehrt zum men� zur�ck
				kampf = false;

				// 3. + 4. angreifer hat verloren/unentschieden -> soll wieder angegriffen werden? (wenn genug einheiten verbleiben)
			} else {
				if (ergebnis.get(1) > ergebnis.get(0)) {
					System.out.println(angreifer + " hat verloren!");
				} else {
					System.out.println("Unentschieden!");
				}
				System.out.print(angreifer + " verliert: " + ergebnis.get(0));
				if (ergebnis.get(0) == -1) {
					System.out.println(" Einheit.");
				} else {
					System.out.println(" Einheiten.");
				}
				System.out.print(defender + " verliert: " + ergebnis.get(1));
				if (ergebnis.get(1) == -1) {
					System.out.println(" Einheit.");
				} else {
					System.out.println(" Einheiten.");
				}
				if ((att.getEinheiten() - 1) > 0) {
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
				} else {
					kampf = false;
				}
			}
		}
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public ArrayList<Integer> laenderAusgabe(ArrayList<Land> ausgabeLaender) {
		// im Pr�farray wird immer die Nummer der m�glichen L�nder,
		// mit dem Pr�farray kann bei der liesEingabe() Methode �berpr�ft werden, ob
		// eine g�ltige Zahl eingegeben wurde
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();
		for (Land land : ausgabeLaender) {
			System.out.print(land.getNummer() + " > " + land.getName() + " mit " + land.getEinheiten());
			if (land.getEinheiten() == 1) {
				System.out.println(" Einheit.");
			} else {
				System.out.println(" Einheiten.");
			}
			pruefArray.add(land.getNummer());
		}
		return pruefArray;
	}

	// WELT AUSGABE ->
	public void gibWeltAus() {
		ArrayList<Land> alleLaender = risiko.gibWeltAus();
		// gibt erst aus, wer welche L�nder besitzt
		for (Land land : alleLaender) {
			System.out
					.print(land.getNummer() + " : " + land.getName() + " geh�rt " + land.getBesitzer().getName() + " besetzt mit: " + land.getEinheiten());
			if (land.getEinheiten() == 1) {
				System.out.println("ner Einheit.");
			} else {
				System.out.println(" Einheiten.");
			}
		}
		System.out.println("");
		ArrayList<Kontinent> alleKontinente = risiko.gibAlleKontinente();
		ArrayList<Player> allePlayer = risiko.gibAllePlayer();
		for (Kontinent kontinent : alleKontinente) {

			// gibt dann Kontinente mit dazugeh�rigen L�ndern aus TODO: k�nnte ausgelagert
			// werden, sodass gezielt darauf zugegriffen werden kann
			ArrayList<Land> eigeneLaender = kontinent.getLaender();
			System.out.println(kontinent.getName() + " besteht aus: ");
			for (Land land : eigeneLaender) {
				System.out.println(land.getName());
			}
			// wenn der Kontinent im Besitz eines Player ist, wird dies ausgegeben
			boolean hasOwner = true;
			for (Player player : allePlayer) {
				if (kontinent.isOwnedByPlayer(player)) {
					System.out.println(player.getName() + " besitzt " + kontinent.getName());
					System.out.println("");
					hasOwner = true;
				}
			}
			if (hasOwner) {
				System.out.println(kontinent.getName() + " geh�rt keinem Player.");
				System.out.println("");
			}
		}
	}
	// Ende Welt-Ausgabe

	public void verschiebeEinheiten(Player aktiverPlayer) {
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();
		Land start = null;
		Land ziel = null;
		int anzahl = 0;
		if (risiko.getEinheitenVerschiebenVonLaender(aktiverPlayer).size() == 0) {
			System.out.println("Du kannst leider keine Einheiten verschieben.");
		} else {
			System.out.println("Einheiten verschieben von: \n");
			// dem Spieler werden nur die L�nder angezeigt, von denen aus verschoben werden
			// kann
			ArrayList<Land> ursprungsLaender = risiko.getEinheitenVerschiebenVonLaender(aktiverPlayer);
			pruefArray = laenderAusgabe(ursprungsLaender);
			boolean ung�ltig = true;
			while (ung�ltig) {
				int von = 0;
				try {
					von = Integer.parseInt(liesEingabe());
					start = risiko.getLandById(von);
				} catch (IOException e) {
					von = -99;
				}
				if (pruefArray.contains(von)) {
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
				} catch (IOException e) {
				}
				if (anzahl > (start.getEinheiten() - 1)) {
					System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
				} else {
					ung�ltig = false;
				}
			}
			System.out.println("Einheiten verschieben nach: \n");
			ArrayList<Land> nachbarLaender = risiko.getEigeneNachbarn(start);
			pruefArray = laenderAusgabe(nachbarLaender);
			ung�ltig = true;
			while (ung�ltig) {
				int nach = 0;
				try {
					nach = Integer.parseInt(liesEingabe());
					ziel = risiko.getLandById(nach);
				} catch (IOException e) {
				}
				if (pruefArray.contains(nach)) {
					ung�ltig = false;
				} else {
					System.out.println("Ung�ltige Eingabe, bitte wiederholen!");
				}
			}
			risiko.verschiebeEinheiten(start, ziel, anzahl);
		}
	}

	public void gibLaenderUndNummerVonPlayerAus(Player play) {
		for (int i = 0; i < play.getBesitz().size(); i++) {
			System.out.println(play.gibLaenderUndNummer().get(i) + " geh�rt " + play.getName());
		}
	}

	// @tobi // nur f�r testzwecke, da die missionen ja nicht sichtbar sein sollen
	public void gibPlayerMissionUndLaenderAus() {
		for (Player player : risiko.getPlayerArray()) {
			System.out.println("Player nr." + (player.getNummer() + 1) + " : " + player.getName() + " hat die farbe - "
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
		gibPlayerMissionUndLaenderAus();
		setzeStartEinheiten();
//		****************_hier_gehts_los********
		System.out.println("");
		System.out.println("Jetzt beginnt das Spiel!");

		round();

	}

	public static void main(String[] args) {
		RisikoClientUI cui = new RisikoClientUI();
		cui.run();
	}

}

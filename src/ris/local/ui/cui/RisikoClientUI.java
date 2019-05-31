package ris.local.ui.cui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.WriteAbortedException;
import java.util.ArrayList;

import ris.local.domain.Risiko;
import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.LandNichtInBesitzException;
import ris.local.exception.LandInBesitzException;
import ris.local.exception.UngueltigeAnzahlEinheitenException;
import ris.local.exception.ZuWenigEinheitenException;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;
import ris.local.valueobjects.Risikokarte;
import ris.local.valueobjects.Risikokarte.Symbol;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;
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

	private void eingangsMenue() {
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
				starteSpiel();
				richtigeEingabe = true;
				break;
			case "no":
			case "nein":
			case "n":
				System.out.println("Risik wird beendet"); // Platzhalter fï¿½r Spielbeenden TODO: Spiel beenden
				System.exit(0);
				break;
			default:
				System.out.println("ungueltige eingabe");
				richtigeEingabe = false;
			}
		}
	}

	public void starteSpiel() {
		String eingabe = "";
		boolean ungueltig = true;// TODO: Check
		while (ungueltig) {
			System.out.println("Neues Spiel beginnen (n) oder Spiel laden (l)?");
			try {
				eingabe = liesEingabe();
				ungueltig = false;
			} catch (IOException | NullPointerException e) {
				// TODO hier catchen habs nicht hingekriegt..
				System.out.println("hhhey");
				ungueltig = true;
			}
			switch (eingabe) {
			case "n":
				try {
					wieVielePlayerMenu();
				} catch (IOException e) {
				}
				risiko.verteileEinheiten();
				// hier evlt abfrage ob mit missionen gespielt werden soll oder nicht
				risiko.verteileMissionen();
				risiko.setzeAktivenPlayer();
				System.out.println("jetzt beginnt das Spiel \n");
				gibPlayerMissionUndLaenderAus();
				setzeStartEinheiten();
				ungueltig = false;
				break;
			case "l":
				System.out.println("Welche Datei soll geladen werden? Bitte den Dateinamen korrekt eingeben:");
				String datei = " ";
				try {
					datei = liesEingabe();
				} catch (IOException e) {
				}
				risiko.spielLaden(datei);
				System.out.println("Das Spiel wurde erfolgreich geladen.");
				ungueltig = false;
				break;
			default:
				System.out.println("ungueltige Eingabe");
				break;
			}
		}
	}

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
				richtigeEingabe = false;
				System.err.println("ungueltige Eingabe. Bitte wiederholen \n");
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
			System.out.println("Wie soll Player " + (i + 1) + " heissen? : ");
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
					System.out.println("ungueltiger Name: Bitte Eingabe wiederholen");
					schlechterName = true;
				}

			}

			do {
				farbe = farbeAuswaehlen();
				farbe = risiko.setFarbeAuswaehlen(farbe);
				System.out.println(risiko.getFarbauswahl());
				if (risiko.getRichtigeEingabe())
					System.out.println(
							"Diese Farbe wurde schon vergeben oder es gibt die Farbe nicht : bitte waehle nochmal eine Farbe!");
			} while (risiko.getRichtigeEingabe());

			risiko.playerAnlegen(name, farbe, i);
		}
	}

	public String farbeAuswaehlen() {

		ArrayList<String> farbenAuswahl = risiko.getFarbauswahl();
		String farbe = "";

		System.out.println("Welche Farbe moechtest nehmen?");
		if (farbenAuswahl.contains("rot")) {
			System.out.println("r : rot");
		}
		if (farbenAuswahl.contains("gruen")) {
			System.out.println("g : gruen");
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
			System.out.println("w : weiss");
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
		// die gesamte Anzahl der verteilten Einheiten wird gespeichert und mit jedem
		// Setzen heruntergezï¿½hlt
		int anzahlEinheiten = risiko.getAnzahlPlayer() * 3;
		int einheit = 1;
		Land aktuellesLand = null;
		boolean ungueltig = true;

		while (anzahlEinheiten > 0) {
			Player aktiverPlayer = risiko.gibAktivenPlayer();
			System.out.println("");
			System.out.println(aktiverPlayer + ": setze eine Einheit.");
			ArrayList<Land> aktiveLaender = aktiverPlayer.getBesitz();
			System.out.println("");
			ArrayList<Integer> pruefArray = laenderAusgabe(aktiveLaender);

			ungueltig = true;
			while (ungueltig) {
				int land = -1;
				try {
					land = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					land = -1;
				}
				if (pruefArray.contains(land)) {
					aktuellesLand = risiko.getLandById(land);
					ungueltig = false;
				} else {
					System.out.println("ungueltige Eingabe, bitte wiederholen!");
				}
			}

			try {
				aktuellesLand.setEinheiten(einheit);
			} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			anzahlEinheiten--;
			risiko.setNaechsterPlayer();
		}
	}

	public void round() {
		String input = "";
		boolean spielzug;
		boolean nichtVerschoben = true;
		while (!win()) { // ist so das Spiel beendet? scheinbar schon?
			Player aktiverPlayer = risiko.gibAktivenPlayer();
			switch (risiko.getCurrentState()) {
			case SETUNITS:
				setzeNeueEinheiten(aktiverPlayer);
				risiko.setNextState();
			case ATTACK:
				boolean ungültig = true;
				while(ungültig) {
					System.out.println("Soll angegriffen werden? (y/n) Zugriff auf Informationen über m");
					String eingabe = "";
					try {
						eingabe = liesEingabe();
					} catch(IOException e) {}
					switch(eingabe) {
					case "y":
						attack(aktiverPlayer); // TODO beim verschieben nach dem attack kann es zu exception zu wenige
						// einheiten kommen
						whoIsDead(); // testet und gibt aus ob jemand tot ist und nimmt ihn aus dem SpielerArray
						win();
						break;
					case "n":
						risiko.setNextState();
						ungültig = false;
						break;
					case "m":
						gibMenuAus(aktiverPlayer, nichtVerschoben);
					default:
						System.out.println("Ungültige Eingabe, bitte wiederholen!");	
					}
				}
				break;
			case CHANGEUNITS:
				verschiebeEinheiten(aktiverPlayer);
				win();
				risiko.setNextState();
				risiko.setNaechsterPlayer();
			}
			
			
			//Ab hier alter Vorgang
//			System.out.println(aktiverPlayer + " ist am Zug.");
//			System.out.println("");
//			spielzug = true;
//			// Player bekommt einheiten
//			setzeNeueEinheiten(aktiverPlayer);
//
//			while (spielzug && !win()) {
//				System.out.println("");
//				gibMenuAus(aktiverPlayer, nichtVerschoben);
//				try {
//					input = liesEingabe();
//				} catch (IOException e) {
//				}
//				verarbeiteEingabe(input, aktiverPlayer, nichtVerschoben);
//				if (input.equals("z")) {
//					spielzug = false;
//					nichtVerschoben = true;
//				}
//				if (input.equals("e")) {
//					nichtVerschoben = false;
//				}
//			}
		}
		System.out.println(risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
	}

	// ----------------------------------einheiten-------------------------------------------------
	public void setzeNeueEinheiten(Player aktiverPlayer) {
		// pruefen, ob weitere einheiten verfuegbar sind durch eintauschen der einheitenkarten
		int bonusEinheiten = 0;
		if (risiko.changePossible(aktiverPlayer)) {
			System.out.println("Du kannst Risikokarten gegen Bonuseinheiten eintauschen!");
			System.out.println("Interesse? (y/n)");
			boolean ungueltig = true;
			while (ungueltig) {
				String answer = "";
				try {
					answer = liesEingabe();
				} catch (IOException e) {
				}
				switch (answer) {
				case "y":
					ArrayList<Risikokarte> tauschkarten = aktiverPlayer.getEinheitenkarten();
					System.out.println("Welche Karten sollen eingetauscht werden?");
					for (int i = 0; i<tauschkarten.size(); i++) {
						for (Risikokarte karte: tauschkarten) {
							System.out.println(i + " > Symbol: " + karte.getSymbol() + ", Land: " + karte.getLand());
						}
					}
					Symbol symbolKarte1 = null, symbolKarte2 = null, symbolKarte3 = null;
					try {
						int karte1 = Integer.parseInt(liesEingabe());
						symbolKarte1 = tauschkarten.get(karte1).getSymbol();
					} catch(IOException e) {}
					try {
						int karte2 = Integer.parseInt(liesEingabe());
						symbolKarte2 = tauschkarten.get(karte2).getSymbol();
					} catch(IOException e) {}
					try {
						int karte3 = Integer.parseInt(liesEingabe());
						symbolKarte3 = tauschkarten.get(karte3).getSymbol();
					} catch(IOException e) {}
					if risiko.
					// wenn getauscht werden soll, wird methode aufgerufen, die mï¿½glcihe kombis
					// ausgibt und abfragt, welche kombi getauscht ewrden soll
//					bonusEinheiten = tauscheRisikokarten(aktiverPlayer);
					// hier noch abfrage, ob noch mehr getauscht werden soll, eventuell auch in
					// methode!
					ungueltig = false;
					break;
				case "n":
					ungueltig = false;
					break;
				default:
					System.out.println("ungueltige Eingabe!");
					break;
				}
			}
		}
		//hier werden einheiten für anzahl länder und evtl besitz kontinent von risiko geholt, mit bonuseinheiten addiert
		int verfuegbareEinheiten = risiko.errechneVerfuegbareEinheiten(aktiverPlayer) + bonusEinheiten;
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();
		int landWahl = 0;
		boolean ungueltig = true;
		// information auf der konsole, wie sich die verteilung errechnet?
		System.out.println(aktiverPlayer + " setzt " + verfuegbareEinheiten + " Einheiten.");
		ArrayList<Land> laender = aktiverPlayer.getBesitz();
		while (verfuegbareEinheiten > 0) {
			System.out.println("Wo sollen die Einheiten gesetzt werden?");
			pruefArray = laenderAusgabe(laender);
			ungueltig = true;
			while (ungueltig) {
				try {
					landWahl = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					landWahl = -99;
				}
				if (pruefArray.contains(landWahl)) {
					ungueltig = false;
				} else {
					System.out.println("ungueltige Eingabe, bitte wiederholen!");
				}
			}
			Land landMitNeuerEinheit = risiko.getLandById(landWahl);
			int anzahl = 0; // @tobi darf man hier auch 0 hinschreiben??
			ungueltig = true;
			while (ungueltig) {
				System.out.println("Wie viele Einheiten sollen gesetzt werden? Maximal: " + verfuegbareEinheiten);
				try {
					anzahl = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					if (anzahl == 0) {
						anzahl = -1000;
					} else {
						ungueltig = true;
					}

				}
				if (anzahl == 0) {
					System.out.println("Bitte wiederholen");
					ungueltig = false;
				} else if (anzahl > verfuegbareEinheiten) {
					System.out.println(
							"Verfuegbare Anzahl wurde ueberschritten. Maximal verfuegbar: " + verfuegbareEinheiten);
				} else if (anzahl < 1) {
					System.err.println("Geht nicht");
				} else {
					ungueltig = false;
				}
			}
			try {
				landMitNeuerEinheit.setEinheiten(anzahl);
			} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			verfuegbareEinheiten -= anzahl;
		}
		System.out.println("Alle Einheiten wurden gesetzt.");
	}

	//diese methode brauchen wir nicht mehr
	public int tauscheRisikokarten(Player aktiverPlayer) {
		// Der TauschkombiArray enthält vier Stellen mit je Anzahl an
		// Tauschkombinationen für
		// 0=Kanone, 1=Reiter, 2=Soldaten, 3=Reihe

		int[] tauschkombiArray = risiko.risikokartenTauschkombiVorhanden(aktiverPlayer);
		String[] symbolkombiArray = { "Kanonen", "Reitern", "Soldaten", "Reihen" };
		for (int i = 0; i < tauschkombiArray.length; i++) {
			if (tauschkombiArray[i] > 0) {
				System.out.println(i + " > " + symbolkombiArray[i]);
			}
		}
		System.out.println("-1 > Ich moechte doch nicht tauschen!");
		int answer = -1;
		boolean ungueltig = true;
		// hier wird getestet, ob die Eingabe gï¿½ltig ist, indem geschaut wird, ob an
		// der Stelle answer im tauschkombiArray etwas grï¿½ï¿½eres als 0 steht
		while (ungueltig) {
			try {
				answer = Integer.parseInt(liesEingabe());
			} catch (IOException e) {
			}
			if (answer >= 0) {
				if (tauschkombiArray[answer] > 0) {
					// lï¿½scht die eingetauschten Risikokarten beim Player
					aktiverPlayer.loescheRisikokarten(answer);
					ungueltig = false;
				}
			} else if (answer == -1) {
				return 0;
			} else {
				System.out.println("ungueltige Eingabe, bitte wiederholen!");
			}
		}
		// hier noch einmal umformulieren fï¿½r eine reihe
		System.out.println("Du hast 3 " + symbolkombiArray[answer] + " gegen eine Einheit getauscht!");
		return 1;
	}

	// ----------------------------------einheiten-------------------------------------------------

	public void gibMenuAus(Player aktiverPlayer, boolean nichtVerschoben) {
		System.out.print(aktiverPlayer + ": Was moechtest du tun?");
		if (nichtVerschoben) {
			System.out.print("\n   Angreifen: a");
		}
		System.out.print("\n   Einheiten verschieben: e");
		System.out.print("\n   Zug beenden: z");
		System.out.println("\n   Spiel speichern: s");
		System.out.println("\n   Spiel beenden: q \n"); // TODO
		System.out.print("**Informationen anzeigen:**");
		System.out.print("\n   Weltuebersicht anzeigen: w");
		System.out.print("\n   Laender und Einheiten anzeigen: l"); // gibt lï¿½nder mit einheiten aus und ob ein kontinent
																	// eingenommen ist
//		System.out.print("\n   Lï¿½nder und Einheiten von mï¿½glichen Gegnern zeigen: f"); // gibt lï¿½nder aus, die an die
		// TODO: wurde nicht implementiert // eigenen angrenzen, beide mit
		// einheiten
		System.out.print("\n   Mission anzeigen: m \n");
		System.out.print("\n   Einheitenkarten anzeigen: k \n");
		System.out.flush();
	}

	public void verarbeiteEingabe(String input, Player aktiverPlayer, boolean nichtVerschoben) {
		switch (input) {
//		case "a":
//			if (nichtVerschoben) {
//				attack(aktiverPlayer); // TODO beim verschieben nach dem attackn kann es zu exception zu wenige
//										// einheiten kommen
//				whoIsDead(); // testet und gibt aus ob jemand tot ist und nimmt ihn aus dem SpielerArray
//				win();
//			} else {
//				System.out.println("ungueltige Eingabe, bitte wiederholen.");
//			}
//			break;
//		case "e":
//			verschiebeEinheiten(aktiverPlayer);
//			win();
//			break;
		case "w":
			gibWeltAus();
			break;
		case "l":
			ArrayList<Land> landAusgabe = aktiverPlayer.getBesitz();
			laenderAusgabe(landAusgabe);
			break;
		case "z":
			risiko.setNaechsterPlayer();
			if (risiko.zieheEinheitenkarte(aktiverPlayer)) {
				// gibt die neueste Einheitenkarte aus, die sich an der letzten Stelle des
				// Einheitenkarten-Arrays befindet
				Risikokarte neu = aktiverPlayer.getEinheitenkarten().get(aktiverPlayer.getEinheitenkarten().size() - 1);
				System.out.println("Du hast mindestens ein Land erobert und bekommst die Einheitenkarte "
						+ neu.getLand().getName() + " mit dem Symbol: " + neu.getSymbol());
			}
			System.out.println(aktiverPlayer + " hat seinen Zug beendet.");
			break;
		case "q":
			System.out.println("Risik wird beendet."); // TODO: Spiel beenden
			System.exit(0); // TODO: @tobi ist das ok?
			break;
		case "m":
			System.out.println(aktiverPlayer.getMission());
			if(!aktiverPlayer.isMissionComplete(aktiverPlayer)) {
				System.out.println("Mission noch nicht erfuellt");
			};
			break;
		case "k":
			if (aktiverPlayer.getEinheitenkarten().size() == 0) {
				System.out.println("... ups, du hast keine Risikokarte!");
			} else {
				for (Risikokarte ein : aktiverPlayer.getEinheitenkarten()) {
					System.out.println(ein.getSymbol() + " : " + ein.getLand().getName());
				}
			}
			break;

		case "s":
			System.out.println("Wie soll die Datei heissen?");
			String datei = "";
			try {
				datei = liesEingabe();
			} catch (IOException e) {
			}
			risiko.spielSpeichern(datei);
			System.out.println("Das Spiel wurde erfolgreich speichert.");
		default:
			System.out.println("ungueltige Eingabe, bitte wiederholen."); // funktioniert das so? @ annie hab mal eine
																			// whileschleife in der round gebaut
			break;
		}
	}

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	public void attack(Player angreifer) {
		boolean ungueltig = true;

		Land att = null;
		Land def = null;
		int attEinheiten = 0;
		int defEinheiten = 0;
		Player defender = null;

		// das pruefarray wird wiederholt genutzt, um fehlerhafte eingaben zu erkennen.
		// es wird bei mehreren eingaben ï¿½berschrieben
		ArrayList<Integer> pruefArray = new ArrayList<Integer>();
		if (risiko.getAngriffsLaender(angreifer).size() == 0) {
			System.out.println("Du kannst leider niemanden angreifen...");
			return;
		}
		// Abfrage, welches Land angreifen soll, es werden dabei nur Lï¿½nder
		// ausgegeben,
		// die angreifen kï¿½nnen
		System.out.println(angreifer + ": mit welchem Land moechtest du angreifen?");
		ArrayList<Land> attackLaender = risiko.getAngriffsLaender(angreifer);
		pruefArray = laenderAusgabe(attackLaender);
		ungueltig = true;
		while (ungueltig) {
			int start = 0;
			try {
				start = Integer.parseInt(liesEingabe());
				att = risiko.getLandById(start);
			} catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
				start = -1;
			}
			if (pruefArray.contains(start)) {
				ungueltig = false;
			} else {
				System.out.println("ungueltige Eingabe, bitte wiederholen!");
			}
		}

		// Abfrage, welches Land angegriffen werden soll
		System.out.println("Welches Land soll angegriffen werden?");
		ArrayList<Land> feindlicheNachbarn = null;
		try {
			feindlicheNachbarn = risiko.getFeindlicheNachbarn(att);
		} catch (LandExistiertNichtException e) {
			e.printStackTrace();
		}
		pruefArray = laenderAusgabe(feindlicheNachbarn);
		ungueltig = true;
		while (ungueltig) {
			int ziel = 0;
			try {
				ziel = Integer.parseInt(liesEingabe());
				def = risiko.getLandById(ziel);
				defender = def.getBesitzer();
			} catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
				ziel = -99; // dadurch wird eingabe ungueltig
			}
			if (pruefArray.contains(ziel)) {
				ungueltig = false;
			} else {
				System.out.println("ungueltige Eingabe, bitte wiederholen!");
			}
		}

		// angriff befindet sich in while-schleife, falls wiederholt angegriffen werden
		// soll
		boolean kampf = true;

		while (kampf) {
			// Abfrage, wie viele Einheiten angreifen
			ungueltig = true;
			System.out.println(angreifer + ": mit wie viel Einheiten soll angegriffen werden? Verfuegbar: "
					+ (att.getEinheiten() - 1));
			if (att.getEinheiten() - 1 > 3) {
				System.out.println(" Maximal moeglich: 3");
			}
			while (ungueltig) {
				try {
					attEinheiten = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					ungueltig = true;
				}
				if (attEinheiten > (att.getEinheiten() - 1) || attEinheiten > 3 || attEinheiten <= 0) {
					System.out.println("ungueltige Eingabe, bitte wiederholen");
				} else {
					ungueltig = false;
				}
			}
			// Abfrage, wie viele Einheiten verteidigen
			ungueltig = true;
			System.out.println(defender + ": mit wie viel Einheiten soll verteidigt werden? Mindestens 1, Du hast: "
					+ def.getEinheiten());
			if (def.getEinheiten() > 2) {
				System.out.println("Maximal 2");
			}
			while (ungueltig) {
				defEinheiten = 0;
				try {
					defEinheiten = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException e) {
					System.out.println("keine gueltige eingabe");
					ungueltig = true;
				}
				if (defEinheiten > 2 || defEinheiten > def.getEinheiten() || defEinheiten <= 0) {
					System.out.println("ungueltige Eingabe, bitte wiederholen");
				} else {
					ungueltig = false;
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

			ArrayList<Integer> aList = null;
			try {
				aList = risiko.diceAttack(attEinheiten);
			} catch (UngueltigeAnzahlEinheitenException e) {
				e.printStackTrace();
			}
			ArrayList<Integer> dList = null;
			try {
				dList = risiko.diceDefense(defEinheiten);
			} catch (UngueltigeAnzahlEinheitenException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < aList.size(); i++) {
				System.out.println("Angreifender Wuerfel Nr." + (i + 1) + " = " + aList.get(i));
			}

			for (int i = 0; i < dList.size(); i++) {
				System.out.println("Verteidigender Wuerfel Nr." + (i + 1) + " = " + dList.get(i));
			}

			ArrayList<Integer> ergebnis = null; // TODO: Achtung check
			try {
				ergebnis = risiko.attack(att, def, attEinheiten, defEinheiten, aList, dList);
			} catch (ZuWenigEinheitenNichtMoeglichExeption e1) {
				// TODO Auto-generated catch block
				System.err.println("zu wenig einheite test stack");
				e1.printStackTrace();
			}

			// 1. angreifer hat gewonnen, aber die Verteidigung hat weitere Lï¿½nder
			if (ergebnis.get(0) > ergebnis.get(1) && def.getBesitzer().equals(defender)) {
				System.out.println("-------------------------------- \n");
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
				System.out.println("Soll erneut angegriffen werden? (y/n)");
				String answer = "";
				try {
					answer = liesEingabe();
				} catch (IOException e) {
				}
				switch (answer) {
				case "y":
					// bricht switch-abfrage ab und kehrt an den anfang der while-schleife
					break;
				case "n":
					// ï¿½nderung des boolean-werts verlï¿½sst den kampf und kehrt zum menï¿½
					// zurï¿½ck
					kampf = false;
					break;
				}
			}

			// 2. Angreifer gewinnt und erobert das Land
			else if (def.getBesitzer().equals(angreifer)) {
				System.out.println("-------------------------------- \n");
				System.out.println(angreifer + " hat gewonnen und erobert " + def.getName() + ".");
				System.out.print(angreifer + " verliert: " + ergebnis.get(0) + "\n");
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
				// abfrage, ob weitere einheiten verschoben werden sollen, wenn dies mï¿½glich
				// ist
				if (att.getEinheiten() > 1) {
					int answer = 0;
					System.out.println(
							"Wieviele Einheiten sollen auf das eroberte Land verschoben werden (auch 0 moeglich)? Maximal: "
									+ (att.getEinheiten() - 1));
					ungueltig = true;
					// TODO: CHECKEN hier kï¿½nnen auch minus einheiten verschoben werdne
					while (ungueltig) {
						try {
							answer = Integer.parseInt(liesEingabe());
						} catch (IOException | NumberFormatException e) {
							answer = 999; // TODO: Check
						}
						if (answer > (att.getEinheiten() - 1)) {
							System.out.println("ungueltige Eingabe, bitte wiederholen!");
						} else {
							ungueltig = false;
						}
					}
					try {
						risiko.verschiebeEinheiten(att, def, answer);
					} catch (LandExistiertNichtException | ZuWenigEinheitenException e) {
						e.printStackTrace();
					} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
						e.printStackTrace();
					}
				}
				System.out.println("Der Angriff ist beendet.");
				// ï¿½nderung des boolean-werts verlï¿½sst den kampf und kehrt zum menï¿½
				// zurï¿½ck
				kampf = false;

				// 3. + 4. angreifer hat verloren/unentschieden -> soll wieder angegriffen
				// werden? (wenn genug einheiten verbleiben)
			} else {
				if (ergebnis.get(1) > ergebnis.get(0)) {
					System.out.println("-------------------------------- \n");
					System.out.println(angreifer + " hat verloren!");
				} else {
					System.out.println("-------------------------------- \n");
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
					System.out.println("Soll erneut angegriffen werden? (y/n)");
					String answer = "";
					try {
						answer = liesEingabe();
					} catch (IOException e) {
					}
					switch (answer) {
					case "y":
						break;
					case "j":
						break;
					case "n":
						// ï¿½nderung des boolean-werts verlï¿½sst den kampf und kehrt zum menï¿½
						// zurï¿½ck
						kampf = false;
						break;
					case "no":
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
		// im Prï¿½farray wird immer die Nummer der mï¿½glichen Lï¿½nder,
		// mit dem Prï¿½farray kann bei der liesEingabe() Methode ï¿½berprï¿½ft werden,
		// ob
		// eine gï¿½ltige Zahl eingegeben wurde
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
		// gibt erst aus, wer welche Lï¿½nder besitzt
		for (Land land : alleLaender) {
			System.out.print(land.getNummer() + " : " + land.getName() + " gehoert " + land.getBesitzer().getName()
					+ " besetzt mit: " + land.getEinheiten());
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

			// gibt dann Kontinente mit dazugehï¿½rigen Lï¿½ndern aus TODO: kï¿½nnte
			// ausgelagert
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
				System.out.println(kontinent.getName() + " gehoert keinem Player.");
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
			// dem Spieler werden nur die Lï¿½nder angezeigt, von denen aus verschoben
			// werden
			// kann
			ArrayList<Land> ursprungsLaender = risiko.getEinheitenVerschiebenVonLaender(aktiverPlayer);
			pruefArray = laenderAusgabe(ursprungsLaender);
			boolean ungueltig = true;
			while (ungueltig) {
				int von = 0;
				try {
					von = Integer.parseInt(liesEingabe());
					start = risiko.getLandById(von);
				} catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
					von = -99;
				}
				if (pruefArray.contains(von)) {
					ungueltig = false;
				} else {
					System.out.println("ungueltige Eingabe, bitte wiederholen!");
				}
			}
			System.out.println("Anzahl der Einheiten: (Maximal) " + (start.getEinheiten() - 1));
			ungueltig = true;
			while (ungueltig) {
				try {
					anzahl = Integer.parseInt(liesEingabe());
				} catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
					anzahl = -99;
				}
				if (anzahl > (start.getEinheiten() - 1) || anzahl < 0) {
					System.out.println("ungueltige Eingabe, bitte wiederholen!");
				} else {
					ungueltig = false;
				}
			}
			System.out.println("Einheiten verschieben nach: \n");
			ArrayList<Land> nachbarLaender = risiko.getEigeneNachbarn(start);
			pruefArray = laenderAusgabe(nachbarLaender);
			ungueltig = true;
			while (ungueltig) {
				int nach = 0;
				try {
					nach = Integer.parseInt(liesEingabe());
					ziel = risiko.getLandById(nach);
				} catch (IOException |NumberFormatException |IndexOutOfBoundsException e) {
					System.err.println("hats geklappt? 926");
					nach = -1;
					ungueltig = true;
				}
				if (pruefArray.contains(nach)) {
					ungueltig = false;
				} else {
					System.out.println("ungueltige Eingabe, bitte wiederholen!");
				}
			}
			try {
				risiko.verschiebeEinheiten(start, ziel, anzahl);
			} catch (ZuWenigEinheitenException | LandExistiertNichtException
					| ZuWenigEinheitenNichtMoeglichExeption e) {
				e.printStackTrace();
			}
		}
	}

	public void gibLaenderUndNummerVonPlayerAus(Player play) {
		for (int i = 0; i < play.getBesitz().size(); i++) {
			System.out.println(play.gibLaenderUndNummer().get(i) + " gehoert " + play.getName());
		}
	}

	// @tobi // nur fï¿½r testzwecke, da die missionen ja nicht sichtbar sein sollen
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

	public boolean win() {
		if (aktuellerPlayerWin()) { // hier wird zuerst nochmal abgefragt ob aktiver Spieler gewonnen hat
			return true;
		} else if (risiko.allMissionsComplete()) { // danach wird fï¿½r jeden Spieler geguckt ob seine Mission erfï¿½llt
													// ist
			return true;
		} else {
			return false;
		}
	}

	// wir haben uns dazu entschieden, dass wenn mehr als ein Spieler gewinnt, der
	// Spieler gewinnt, der grad am Zug ist
	// Methode schaut ob aktueller Spieler gewonnen hat
	public boolean aktuellerPlayerWin() {
		if (risiko.rundeMissionComplete(risiko.gibAktivenPlayer())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean whoIsDead() { // TODO: Ist recht schlaue Methode vlt aussorcen
		for (int i = 0; i < risiko.getPlayerArray().size(); i++) {
			if (risiko.getPlayerArray().get(i).isDead()) {
				System.out.println(
						"Der Spieler " + risiko.getPlayerArray().get(i).getName() + " ist Tot und wird entfernt!");
				risiko.getPlayerArray().remove(i); // TODO: @tobi Ask ??wird der spieler dann wirklich daraus entfernt
				return true;
			}
		}
		return false;
	}

	public void run() {
		eingangsMenue();
//		setzeEinheiten und gibPlayerUndMissionenAus sind jetzt in der StarteSpielMethode, sonst werden die Methoden auch aufgerufen, wenn das Spiel nur geladen wird
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

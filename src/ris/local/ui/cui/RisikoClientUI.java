package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import ris.local.domain.Risiko;
<<<<<<< HEAD
import ris.local.domain.Worldmanagement;
=======
import ris.local.domain.Weltverwaltung;
>>>>>>> tobiBranch(master)
import ris.local.valueobjects.Player;
import ris.local.valueobjects.Land;

public class RisikoClientUI {

	private Risiko risiko;
<<<<<<< HEAD
	private Player aktiverSpieler;
	
	private Worldmanagement welt = new Worldmanagement();
	private BufferedReader in;
	private String name, farbe;
	private Player spieler;
	private Collection<String> farbenAuswahl  = new HashSet();

	// variablen für Spielvergabe (WIRD NOCH NICHT BENUTZT)
	int anzahlAnSpielern;
=======
	private BufferedReader in;
>>>>>>> tobiBranch(master)

	// Konstruktor
	private RisikoClientUI() {
<<<<<<< HEAD
		risiko = new Risiko();
		
=======

		risiko = new Risiko();
>>>>>>> tobiBranch(master)
		in = new BufferedReader(new InputStreamReader(System.in));
		farbenAuswahl.add("rot");
		farbenAuswahl.add("gruen"); 
		farbenAuswahl.add("blau");


	}

<<<<<<< HEAD
	public static void main(String[] args) {

		System.out.println("Lust Risiko zu spielen?");
		RisikoClientUI cui = new RisikoClientUI();
		
		cui.zeigeStartMenu();
		
		cui.leg2SpielerAn(2);
//		cui.spieler.getName();
	}

	public void spielAnlegen() {
		// Abfragen: wie viele Spieler?
		
		risiko.spielAnlegen(3);
	}
	
	public void spielerHinzufuegen() {
		// Name einlesen
		// Farbe einlesen
		Player gamer = risiko.spielerAnlegen("Klaus", "rot");
		
		System.out.println("Spieler " + gamer.getName() + " angelegt.");
	}
	
	// anlegen von zwei Spielern, erweiterbar?? for schleife?? #to
	public void leg2SpielerAn(int anzahlSpieler) {

		for ( int i = 0 ; i < anzahlSpieler ; i++) {
		// ******* hier wird Spieler1 angelegt
		System.out.print("Name von spieler 1: ");
		try {
			name = liesEingabe();
		} catch (IOException e) {}
		farbe = farbeAuswaehlen();

		spieler = new Player(name, farbe, laenderZuweisung(5)); //laender müssen noch einer Farbe hinzugewiesen werden
		// ausgabe für spieler
		System.out.println("Spieler wurde angelegt");
		System.out.println(spieler.getName() + " ist " + spieler.getFarbe() + " und besitzt die Laender : "
				+ spieler.getBesitz());
		}
//		// ******* hier wird spieler2 angelegt
//
//		System.out.print("Name von spieler 2: ");
//		try {
//			name = liesEingabe();
//		} catch (IOException e) {}
//		farbe = farbeAuswaehlen();
//
//		spieler2 = new Gamer(name, farbe, laenderZuweisung(5));
//		System.out.println("Spieler2 wurde angelegt");
//		System.out.println(spieler2.getName() + " ist " + spieler2.getFarbe() + " und besitzt die Laender : "
//				+ spieler2.getBesitz());
//
//		// entscheidung wer anfängt
//		System.out.println("spieler 2 fängt an");

	}

=======
>>>>>>> tobiBranch(master)
	// einlesen von Konsole
	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	private void anfangsMenue() {
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
				System.out.println("jetzt beginnt das Spiel"); // Platzhalter für Spielanfang
				richtigeEingabe = true;
				break;
			case "no":
			case "nö":
			case "n":
				System.out.println("Risik wird beendet"); // Platzhalter für Spielbeenden

				richtigeEingabe = true;
				break;
			default:
				System.out.println("ungültige eingabe");
				richtigeEingabe = false;

			}
		}
	}

	public void wieVieleSpielerMenu() throws IOException {

		String eingabeSpieler, name, farbe;
		int nr;
		System.out.println("Wieviele Spieler soll es geben? :");
		eingabeSpieler = liesEingabe();
		nr = Integer.parseInt(eingabeSpieler);
		for (int i = 1; i <= nr; i++) {
			System.out.println("Wie soll spieler " + i + " heißen? : ");
			name = liesEingabe();
//			System.out.println("welche farbe :");
			farbe = farbeAuswaehlen();
			risiko.spielerAnlegen(name, farbe, nr);
		}
		

<<<<<<< HEAD
	// muss überarbeitet werden, falsche collection und die 
=======
	}
	// Methode für farbeingabe; damit die richtigen Farben ausgewählt werden können
	Collection<String> farbenAuswahl  = new HashSet();
	// muss überarbeitet werden, falsche collection
>>>>>>> tobiBranch(master)
	public String farbeAuswaehlen() {
		String farbe = "";
		
		System.out.println("Welche Farbe möchtest nehmen?");
		if(farbenAuswahl.contains("rot")) {
			System.out.println("r : rot");
		}
		if(farbenAuswahl.contains("gruen")) {
			System.out.println("g : grün");
		}
		if(farbenAuswahl.contains("blau")) {
			System.out.println("b : blau");
		}
		try {
			farbe = liesEingabe();
		} catch (IOException e) {
		}
		switch (farbe) {
		case "r":
			farbe = "rot";
			farbenAuswahl.remove("rot");
			break;
		case "g":
			farbenAuswahl.remove("gruen");
			farbe = "gruen";
			break;
		case "b":
			farbenAuswahl.remove("blau");
			farbe = "blau";
			break;
		default:
			farbeAuswaehlen();
		}
		return farbe;
	}

	
	
	
	
	
	
	public static void main(String[] args) {
		RisikoClientUI cui = new RisikoClientUI();
		cui.anfangsMenue();
	}

	
	
}


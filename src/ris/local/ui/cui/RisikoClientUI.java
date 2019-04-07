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
import ris.local.valueobjects.Land;

public class RisikoClientUI {

	private Risiko risiko;
	private BufferedReader in;

	// Konstruktor
	private RisikoClientUI() {
		risiko = new Risiko();
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public static void main(String[] args) {
		RisikoClientUI cui = new RisikoClientUI();
		cui.run();
	}
	
	public void run() {		
		starteSpiel();
		verteileStartEinheiten();
		round();
	}

	// einlesen von Konsole
	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	private void starteSpiel() {
		boolean richtigeEingabe = false;
		String eingabe = "";
		while (!richtigeEingabe) {
			System.out.println("Lust Risiko zu spielen? (y/n) :	");
			try {
				eingabe = liesEingabe();
			} catch (IOException e) {}
			switch (eingabe) {
			case "yes": case "j": case "y":
				try {
					wieVieleSpielerMenu();
				} catch (IOException e) {}
				System.out.println("jetzt beginnt das Spiel"); // Platzhalter für Spielanfang
				richtigeEingabe = true;
				break;
			case "no": case "nö": case "n":
				System.out.println("Risik wird beendet"); // Platzhalter für Spielbeenden
				richtigeEingabe = true;
				break;
			default:
				System.out.println("ungültige eingabe");
				richtigeEingabe = false;
			}
		}
		//zufällige Verteilung der Einheiten
		risiko.verteileEinheiten();
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
			farbe = farbeAuswaehlen();
			risiko.spielerAnlegen(name, farbe, nr);
		}
	}

	public String farbeAuswaehlen() {
		String farbe = "";
		ArrayList<String> farbenAuswahl = risiko.gibAlleFarben();

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
		try {
			farbe = liesEingabe();
		} catch (IOException e) {}
		switch (farbe) {
		case "r":
			farbe = "rot";
			break;
		case "g":
			farbe = "gruen";
			break;
		case "b":
			farbe = "blau";
			break;
		default:
			farbeAuswaehlen();
		}
		return farbe;
	}
	
	public void verteileStartEinheiten() {
		int anzahlEinheiten = risiko.getAnzahlPlayer() * 5;
		int einheit = 1;
		
		while(anzahlEinheiten > 0) {
			Player aktiverPlayer = risiko.gibAktivenSpieler();
			System.out.println(aktiverPlayer + ": setze eine Einheit.");
			System.out.println(risiko.gibLaenderAus(aktiverPlayer));
			int land;
			try{
				//!!! hier muss noch geprüft werden, ob die zahl überhaupt zur auswahl stand > evtl. arraylist land geben lassen und dann mit equals
				land = Integer.parseInt(liesEingabe());
			} catch(IOException e) {}
			risiko.setztEinheit(int land, int einheit);
			anzahlEinheiten--;
			risiko.naechsterSpieler();
		}
	}
	
	public void round() {
		String aktiverPlayer = risiko.gibAktivenSpieler();
		String input = "";
		while(true) {
			//spieler bekommt einheiten
			gibMenuAus(aktiverPlayer);
			try {
				input = liesEingabe();
				verarbeiteEingabe(input, aktiverPlayer);
			} catch(IOException e){}

			risiko.naechsterSpieler();
		}
	}
	
	public void gibMenuAus(String aktiverPlayer) {
		System.out.print(aktiverPlayer + ": Was möchtest du tun?");
		System.out.print("               \n Angreifen: a");
		System.out.print("               \n Einheiten verschieben: e");
		System.out.print("               \n Zug beenden: z");
		System.out.print("               \n Spiel beenden: q");
		System.out.flush();
	}
	
	public void verarbeiteEingabe(String input, String aktiverPlayer) {
			switch(input) {
			case "a":
				attack(aktiverPlayer);
				break;
			case "e":
				//
				break;
			case "z":
				//
				break;
			case "q":
				//
				break;
			default:
				//
				break;
			}
	}
	
	public void attack(String angreifer) {
		int start;
		int ziel;
		int angriff;
		int defense;
		boolean kampf = true;
		
		//abfrage, von welchem land welches andere land angegriffen werden soll
		System.out.print(angreifer + " mit welchem Land möchtest du angreifen?");
		System.out.println(risiko.gibLaenderAus(angreifer));
		try {
			start = Integer.parseInt(liesEingabe()); 
		} catch(IOException e) {}
		System.out.println("Welches Land soll angegriffen werden? \n" + risiko.getFeinde(angreifer, start));
		try {
			ziel = Integer.parseInt(liesEingabe());
		} catch(IOException e) {}
		
		//ab hier beginnt das setzen der einheiten von beiden seiten, mehrmals möglich, da eventuell mehrere angriffe möglich sind
		while (kampf) {
			System.out.println("Mit wie vielen Einheiten soll angegriffen werden? Maximal:" + risiko.getEinheiten(start));
			try {
				angriff = Integer.parseInt(liesEingabe());
			} catch(IOException e) {}
			Player defender = risiko.getBesitzer(ziel);
			System.out.println(defender + ": Mit wievielen Einheiten möchtest du verteidigen? Maximal: " + risiko.getEinheiten(ziel));
			try {
				defense = Integer.parseInt(liesEingabe());
			} catch(IOException e) {}
			String winner = risiko.attack (start, ziel, angriff, defense);
			System.out.println(winner + " hat gewonnen.");
			if (winner.equals(angreifer)) {
				einheiten verschieben
				kampf = false;
			} else {
				System.out.println(angreifer + ": Erneut angreifen? (yes/no)");
				String answer;
				try {
					answer = liesEingabe();
				} catch(IOException e) {}
				switch (answer) {
				case "yes":
					break;
				case "no":
					kampf = false;
					break;
				}
			}
		}
	}


}
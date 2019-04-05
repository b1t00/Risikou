package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import ris.local.domain.Risiko;
import ris.local.domain.Worldmanagement;

import ris.local.valueobjects.Player;
import ris.local.valueobjects.Land;

public class RisikoClientUI {

	private Risiko risiko;

	private Worldmanagement welt = new Worldmanagement();
	private BufferedReader in;
	private String name, farbe;
	private Player spieler;
	private Collection<String> farbenAuswahl = new HashSet();

	// variablen für Spielvergabe (WIRD NOCH NICHT BENUTZT)
	int anzahlAnSpielern;

	// Konstruktor
	private RisikoClientUI() {

		risiko = new Risiko();
		in = new BufferedReader(new InputStreamReader(System.in));
		farbenAuswahl.add("rot");
		farbenAuswahl.add("gruen");
		farbenAuswahl.add("blau");

	}

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
			farbe = farbeAuswaehlen();
			risiko.spielerAnlegen(name, farbe, nr);
		}
	}

	public String farbeAuswaehlen() {
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

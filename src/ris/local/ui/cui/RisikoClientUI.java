package ris.local.ui.cui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ris.local.domain.Weltverwaltung;
import ris.local.valueobjects.Gamer;
import ris.local.valueobjects.Land;

public class RisikoClientUI {
//	private Weltverwaltung welt;

	private Weltverwaltung welt = new Weltverwaltung();
	private BufferedReader in;
	private String name, farbe;
	private Gamer spieler1, spieler2, spieler3;
	private Collection<String> farbenAuswahl;

	// variablen für Spielvergabe (WIRD NOCH NICHT BENUTZT)
	int anzahlAnSpielern;

	private RisikoClientUI() {
		in = new BufferedReader(new InputStreamReader(System.in));
		farbenAuswahl = new HashSet();
		farbenAuswahl.add("rot");
		farbenAuswahl.add("gruen");
		farbenAuswahl.add("blau");

	}

	public static void main(String[] args) {
		System.out.println("Lust Risiko zu spielen?");
		RisikoClientUI cui = new RisikoClientUI();
		cui.leg2SpielerAn();
	}

	// anlegen von zwei Spielern, erweiterbar?? for schleife?? #to
	public void leg2SpielerAn() {

		// ******* hier wird Spieler1 angelegt
		System.out.print("Name von spieler 1: ");
		try {
			name = liesEingabe();
		} catch (IOException e) {}
		farbe = farbeAuswaehlen();

		spieler1 = new Gamer(name, farbe, laenderZuweisung(6));
		System.out.println("Spieler1 wurde angelegt");
		System.out.println(spieler1.getName() + " ist " + spieler1.getFarbe() + " und besitzt die Laender : "
				+ spieler1.getBesitz());

		// ******* hier wird spieler2 angelegt

		System.out.print("Name von spieler 2: ");
		try {
			name = liesEingabe();
		} catch (IOException e) {}
		farbe = farbeAuswaehlen();

		spieler2 = new Gamer(name, farbe, laenderZuweisung(5));
		System.out.println("Spieler2 wurde angelegt");
		System.out.println(spieler2.getName() + " ist " + spieler2.getFarbe() + " und besitzt die Laender : "
				+ spieler2.getBesitz());

		// entscheidung wer anfängt
		System.out.println("spieler 2 fängt an");
	}

	// einlesen von Konsole
	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	// laufvariablen für zufällige Länderverteilung
	private int i = 0;
	private int k = 0;
	// hier werden Länderkarten gemischt.
	private ArrayList<Integer> zufall = shuffleIntegerArrayList();

	private ArrayList<Land> laenderZuweisung(int anzahlAnLaendern) {

		ArrayList<Land> besitzt = new ArrayList<Land>();

		for (; i < (k + anzahlAnLaendern); i++) {
			besitzt.add(welt.laender[zufall.get(i)]);
		}
		k = k + anzahlAnLaendern;
		return besitzt;
	}

	// return: gibt Arraylist<Integer> mit zufälligen Integers zurück
	public ArrayList<Integer> shuffleIntegerArrayList() {
		int laenderanzahl = 11;
		ArrayList nummern = new ArrayList();
		for (int i = 0; i < laenderanzahl; i++) {
			nummern.add(i);
		}
		Collections.shuffle(nummern);
		return nummern;
	}

	// muss überarbeitet werden, falsche collection
	public String farbeAuswaehlen() {
		System.out.println("Welche Farbe möchtest nehmen? \n r : rot  \n g : gruen \n b : blau ");
//		farbe = "rot";
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
	
	
}

//Collection zahlen = new HashSet();
//zahlen.
//public Collection farben(int wieviele) {
//while(zahlen.size() < wieviele) {
//zahlen.
//System.out.println(zahlen);
//return zahlen;
//}h


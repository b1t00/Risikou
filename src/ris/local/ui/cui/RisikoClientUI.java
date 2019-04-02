package ris.local.ui.cui;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import ris.local.domain.Weltverwaltung;
import ris.local.valueobjects.Gamer;
import ris.local.valueobjects.Land;

public class RisikoClientUI {
//	private Weltverwaltung welt;

	Weltverwaltung welt = new Weltverwaltung();
	private BufferedReader in;
	private String name, farbe;
	private Gamer spieler1, spieler2, spieler3;
	
	//variablen für Spielvergabe
	
	int anzahlAnSpielern;

	private RisikoClientUI() {
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	public static void main(String[] args) {
		System.out.println("heey");
		RisikoClientUI cui = new RisikoClientUI();
		cui.legSpielerAn();
	}

	public void legSpielerAn(int wievieleSpieler) {
		for(int i = 0; i < wievieleSpieler ; i++) {
		System.out.print("Name von spieler 1: ");
		try {
			name = liesEingabe();
		} catch (IOException e) {
		}
		System.out.print("Farbe von " + name + " : ");

		try {

			farbe = liesEingabe();
		} catch (IOException e) {
		}

		Gamer spieler = new Gamer(name, farbe, laenderZuweisung(11);
		System.out.println(
				spieler.getName() + " ist " + spieler1.getFarbe() + " besitzt die Laender : " + spieler1.getBesitz());
	}

	// einlesen von Konsole
	private String liesEingabe() throws IOException {
		return in.readLine();
	}
	
	int i = 0;
	ArrayList<Integer> zufall = zufallLaender();
	
	
	private ArrayList<Land> laenderZuweisung(int anzahlAnLaendern) {
		int zuweisungsZahl = anzahlAnLaendern/anzahlAnSpielern;
		ArrayList<Land> besitzt = new ArrayList<Land>();
		
		for (; i < i + zuweisungsZahl; i++) {
			besitzt.add(welt.laender[zufall.get(i)]);
		}
		return besitzt;

	}

	public ArrayList<Integer> zufallLaender() {
		int laenderanzahl = 10;
		ArrayList nummern = new ArrayList();
		for (int i = 0; i < laenderanzahl; i++) {
			nummern.add(i);
		}
		Collections.shuffle(nummern);
		return nummern;
	}

}

//public Collection zufallLaender(int wieviele) {
//Collection zahlen = new HashSet();
//while(zahlen.size() < wieviele) {
//	zahlen.add((int) (Math.random()*11));
//}
//for (Iterator iterator = zahlen.iterator(); iterator.hasNext();) {
//	Object name = (Object) iterator.next();
//	System.out.println(name);
//	
//}
//System.out.println(zahlen);
//return zahlen;
//}

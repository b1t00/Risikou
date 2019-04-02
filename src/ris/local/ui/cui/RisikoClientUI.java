package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ris.local.domain.Weltverwaltung;
import ris.local.valueobjects.Gamer;
import ris.local.valueobjects.Land;

public class RisikoClientUI {
	private Weltverwaltung welt;
	private BufferedReader in;
	
	public String name, farbe;
	

	public RisikoClientUI() {
		welt = new Weltverwaltung();
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public static void main(String[] args) {
		RisikoClientUI cui = new RisikoClientUI();
		cui.legSpielerAn();

//		System.out.println(spieler1.getName() + spieler1.getFarbe());
	}

	/*
	 * public int[] zufallLaenderverteilung(int wieviel) { int neueZahl =
	 * Math.random(); int[] arrayInt = new int[wieviel]; }
	 */
	

	

	public void legSpielerAn() {
		System.out.print("Name von spieler 1: ");
		try {
			name = liesEingabe();
		} catch (IOException e) {
		}
		System.out.print("Farbe von " + name + " :");

		try {

			farbe = liesEingabe();
		} catch (IOException e) {
		}

		System.out.println(name + " hat die Farbe : " + farbe);
	}

	// einlesen von Konsole
	private String liesEingabe() throws IOException {
		return in.readLine();
	}
	
	private void laenderZuweisung(Gamer gamer) {
		ArrayList<Land> besitzt = new ArrayList<Land>();
		
		besitzt.add(cui.welt.laender[0]);
		besitzt.add(cui.welt.laender[3]);
	}

}

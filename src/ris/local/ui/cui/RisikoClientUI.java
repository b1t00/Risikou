package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;

import ris.local.domain.Weltverwaltung;

public class RisikoClientUI {
	private Weltverwaltung welt;
	private BufferedReader in;
	
	
	public RisikoClientUI() {
		this.welt = new Weltverwaltung();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*erster Versuch für einen möglich Ablauf von einem Angriff*/
		System.out.println(spieler1 + " greift an. Wähle ein Land, das angreift:" + spieler1.gibLaenderAus());
		int nummer = Integer.parseInt(liesEingabe());
		//hier exception, wenn eingabe nicht gültig
		System.out.println("Welches Land soll angegriffen werden:" + welt.angriff(nummer));
		int feind = Integer.parseInt(liesEingabe());
		
		System.out.println("Mit wie vielen Einheiten soll angegriffen werden? Zahl zwischen 1 und" + spieler1.inBesitz[nummer].getEinheiten());
		int angreifer = Integer.parseInt(liesEingabe());
	}
	

	/*public int[] zufallLaenderverteilung(int wieviel) {
	int neueZahl = Math.random();
	int[] arrayInt = new int[wieviel];
	}
	*/
	
	
	
	
	public void legSpielerAn() {
		System.out.println("Name von spieler 1: ");
		Gamer gamer1 = new Gamer(liesEingabe());
		System.out.println("Name von spieler 2: ");
	}
	
	private static String liesEingabe() {
		return in.readLine();
	}
	

	
	
	
}
	
}
,
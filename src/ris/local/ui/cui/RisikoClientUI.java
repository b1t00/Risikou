package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;

import ris.local.domain.Weltverwaltung;

public class RisikoClientUI {
	private Weltverwaltung welt;
	private BufferedReader in;
	
	public RisikoClientUI() {
		welt = new Weltverwaltung();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		/*erster Versuch für einen möglich Ablauf von einem Angriff*/
		System.out.println(spieler1 + " greift an. Wähle ein Land, das angreift:" + spieler1.gibLaenderAus());
		String name = liesEingabe();
		System.out.println("Welches Land soll angegriffen werden:" + );
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
package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ris.local.domain.Weltverwaltung;
import ris.local.valueobjects.Gamer;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Player;

public class RisikoClientUI {
	private Weltverwaltung world;
	private BufferedReader in;
	
	
	public RisikoClientUI() {
		world = new Weltverwaltung();
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	private String liesEingabe throws IOException() {
		String rueckgabe = in.readLine();
		// einlesen von Konsole
		return rueckgabe;
	}
	
	/*erster Versuch für einen möglich Ablauf von einem Angriff*/
	public void attack(Player spieler1) {
		System.out.println(spieler1 + " greift an. Wähle ein Land, das angreift: \n" + spieler1.gibLaenderAus());
//		try {
//			int nummer = Integer.parseInt(liesEingabe());
//		} catch (IOException e) {}
//		
		//int nummer = Integer.parseInt(liesEingabe());
		System.out.println("Welches Land soll angegriffen werden: \n" + world.angriff(1, spieler1));
		//int feind = Integer.parseInt(liesEingabe());
		
	}
	
	public static void main(String[] args) {
		RisikoClientUI cui = new RisikoClientUI();
		ArrayList<Land> laender1 = new ArrayList<Land>();
		laender1.add(new Land("Portugal", 0, "blau"));
		laender1.add(new Land("Spanien", 1, "blau"));
		Player spieler1 = new Player("Otto", laender1, "blau");
		cui.attack(spieler1);
		
		
		//System.out.println("Mit wie vielen Einheiten soll angegriffen werden? Zahl zwischen 1 und" + spieler1.inBesitz[nummer].getEinheiten());
		//int angreifer = Integer.parseInt(liesEingabe());*/
	}
	
}
	/*public int[] zufallLaenderverteilung(int wieviel) {
	int neueZahl = Math.random();
	int[] arrayInt = new int[wieviel];
	}
	*/
	
	/*
	
	
	public void legSpielerAn() {
		System.out.println("Name von spieler 1: ");
		Player gamer1 = new Player(liesEingabe());
		System.out.println("Name von spieler 2: ");
	}
	
	private static String liesEingabe() {
		return in.readLine();
	}
	*/
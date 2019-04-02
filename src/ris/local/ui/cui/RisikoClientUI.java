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
	private Weltverwaltung welt;
	private BufferedReader in;
	
	
	public RisikoClientUI() {
		welt = new Weltverwaltung();
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	private String liesEingabe() {
		// einlesen von Konsole
		return in.readLine();
	}
	
	private int liesNummer() {
		int eingabe = Integer.parseInt(liesEingabe());
		return eingabe;
	}
	
	/*erster Versuch für einen möglich Ablauf von einem Angriff*/
	public void attack(Player spieler1) {
		System.out.println(spieler1 + " greift an. Wähle ein Land, das angreift: \n" + spieler1.gibLaenderAus());
		//int nummer = Integer.parseInt(liesEingabe());
		System.out.println("Welches Land soll angegriffen werden:" + world.angriff(0));
		//int feind = Integer.parseInt(liesEingabe());
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Weltverwaltung world = new Weltverwaltung();
		ArrayList<Land> laender1 = new ArrayList<Land>();
		laender1.add(new Land("Portugal", 0));
		laender1.add(new Land("Frankreich", 1));
		Player spieler1 = new Player("Otto", laender1);
		
		/*erster Versuch für einen möglich Ablauf von einem Angriff*/
		System.out.println(spieler1 + " greift an. Wähle ein Land, das angreift: \n" + spieler1.gibLaenderAus());
		//int nummer = Integer.parseInt(liesEingabe());
		System.out.println("Welches Land soll angegriffen werden:" + world.angriff(0));
		//int feind = Integer.parseInt(liesEingabe());
		
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
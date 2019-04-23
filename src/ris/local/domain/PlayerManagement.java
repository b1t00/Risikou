package ris.local.domain;
//cui frage 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import ris.local.valueobjects.Player;

public class PlayerManagement {

	private ArrayList<Player> gamerListe = new ArrayList<Player>();
	private ArrayList<String> farbenAuswahl = new ArrayList<String>();
	private boolean falscheEingabe;

	public PlayerManagement() {
		farbenAuswahl.add("rot"); //0
		farbenAuswahl.add("gruen"); //1
		farbenAuswahl.add("blau"); //2
		farbenAuswahl.add("weiss"); //3
		farbenAuswahl.add("pink"); //4
		farbenAuswahl.add("schwarz"); //5
	}

	public String menuFarbeAuswaehlen(String farbe) {
		switch (farbe) {
		case "r":
			return FarbeAuswaehlen(0);
		case "g":
			return FarbeAuswaehlen(1);
		case "b":
			return FarbeAuswaehlen(2);
		case "w":
			return FarbeAuswaehlen(3);
		case "p":
			return FarbeAuswaehlen(4);
		case "s":
			return FarbeAuswaehlen(5);
		default : 
			falscheEingabe = true;
		}
		return farbe;
	}
	
	public String FarbeAuswaehlen(int welcheFarbe) {
		String farbe = "";
		if(farbenAuswahl.get(welcheFarbe)!= null) {
			farbe = farbenAuswahl.get(welcheFarbe);
			farbenAuswahl.remove(welcheFarbe);
			farbenAuswahl.add(welcheFarbe, null);
			falscheEingabe = false;
			} else {
				falscheEingabe = true;
			}
		return farbe;
	}
	
	public boolean getRichtigeEingabe() {
		return falscheEingabe;
	}

	public ArrayList<String> getFarbauswahl() {
		return farbenAuswahl;
	}
	
	public ArrayList<Player> getPlayers() {
		return gamerListe;

	}
	
//	Methode die sagt wieviele Player es gibt, man kann auch getPlayers().size() aufrufen ^^
	public int getAnzahlPlayer() {
		return gamerListe.size();
	}
//	public void addPlayer(String name, String farbe, int nummer) {} // 


	public Player addPlayer(String name, String farbe, int nummer) { // hier die von Teschke Methode. Warum Player?
		Player player = new Player(name, farbe, nummer);
		gamerListe.add(player);
		return player;
	}

	// gibt Anzahl an Playern zurueck
	public int getPlayerAnzahl() { // es könnte sein das diese Methode ueberfluessig ist..
		return gamerListe.size();
	}

	// public void addElements(ArrayList<Land>) {

	// }
}
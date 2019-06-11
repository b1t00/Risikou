package ris.local.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import ris.local.exception.SpielerNameExistiertBereitsException;
import ris.local.valueobjects.Player;

public class PlayerManagement implements Serializable {

	private ArrayList<Player> gamerListe = new ArrayList<Player>();
	private ArrayList<String> farbenAuswahl = new ArrayList<String>();
	private boolean falscheEingabe;
	private ArrayList<Color> colorArray;

	public PlayerManagement() {
		farbenAuswahl.add("rot"); // 0
		farbenAuswahl.add("gruen"); // 1
		farbenAuswahl.add("blau"); // 2
		farbenAuswahl.add("weiss"); // 3
		farbenAuswahl.add("pink"); // 4
		farbenAuswahl.add("schwarz"); // 5
		colorArray = new ArrayList<Color>();
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
		default:
			falscheEingabe = true;
		}
		return farbe;
	}

	// @tobi man könnte das auch mit zahlen eingabe machen, dann könnte man sich
	// warhrscheinlich den switchcase sparen
	public String FarbeAuswaehlen(int welcheFarbe) {
		String farbe = "";
		if (farbenAuswahl.get(welcheFarbe) != null) {
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

	public Player addPlayer(String name, String farbe, int nummer) throws SpielerNameExistiertBereitsException { 
		Player player = new Player(name, farbe, nummer);
		for(Player p: gamerListe) {
			if(p.getName().equals(name)) {
				throw new SpielerNameExistiertBereitsException(name);
			}
		}
		gamerListe.add(player);
		return player;
	}
	public ArrayList<Color> getColorArray() {
		return colorArray;
	}

	public void setColorArray(Color color) {
		this.colorArray.add(color);
	}
}
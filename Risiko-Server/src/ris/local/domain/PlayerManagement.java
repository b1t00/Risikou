package ris.local.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.valueobjects.Player;

public class PlayerManagement implements Serializable {

	private ArrayList<Player> gamerListe = new ArrayList<Player>();
	private ArrayList<String> farbenAuswahl = new ArrayList<String>();
	private boolean falscheEingabe;
	private ArrayList<Color> colorArray;
	private int spielerAnzahl;

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

	public void setSpielerAnzahl(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}
	
	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}
	
//	Methode die sagt wieviele Player es gibt, man kann auch getPlayers().size() aufrufen ^^
	public int getAnzahlPlayer() {
		return gamerListe.size();
	}

	public void addPlayer(String name, String farbe, int nummer) throws SpielerNameExistiertBereitsException { 
		Player player = new Player(name, farbe, nummer);
		for(Player p: gamerListe) {
			if(p.getName().equals(name)) {
				throw new SpielerNameExistiertBereitsException(name, farbe, nummer);
			}
		}
		gamerListe.add(player);
	}
	
	public ArrayList<Color> getColorArray() {
		return colorArray;
	}
	
	public void setColorArray(String farbe) {
		switch (farbe) {
		case "rot":
			addColor(new Color(226, 19, 43));
			break;
		case "gruen":
			addColor(new Color(23, 119, 50));
			break;
		case "blau":
			addColor(new Color(30, 53, 214));
			break;
		case "pink":
			addColor(new Color(255, 51, 245));
			break;
		case "weiss":
			addColor(new Color(255, 255, 255));
			break;
		case "schwarz":
			addColor(new Color(0, 0, 0));
			break;
			}
	}

	public void addColor(Color color) {
		this.colorArray.add(color);
	}
}
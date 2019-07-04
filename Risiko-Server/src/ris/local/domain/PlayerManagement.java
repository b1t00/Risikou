package ris.local.domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.UngueltigeAnzahlSpielerException;
import ris.common.valueobjects.Player;

public class PlayerManagement implements Serializable {

	private ArrayList<Player> gamerListe = new ArrayList<Player>();
//	farbenauswahl alle verfuegbaren farben zu beginn des spiels im login-bereich
	private ArrayList<String> farbenAuswahl = new ArrayList<String>();
//	colorarray: die tatsaechlich genutzten farben im spiel
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

	public void menuFarbeAuswaehlen(String farbe) {
		switch (farbe) {
		case "rot":
			 FarbeAuswaehlen(0);
			 break;
		case "gruen":
			 FarbeAuswaehlen(1);
			 break;
		case "blau":
			 FarbeAuswaehlen(2);
			 break;
		case "weiss":
			 FarbeAuswaehlen(3);
			 break;
		case "pink":
			 FarbeAuswaehlen(4);
			 break;
		case "schwarz":
			 FarbeAuswaehlen(5);
			 break;
		}
	}

	/*
	 * entsprechend des int-Wert kann auf die Farbe im Index des farbenAuswahl Arrays zugegriffen werden
	 * add(welcheFarbe, null) damit der Array gleichlang bleibt und die Farben am richtigen Index bleiben
	 */
	public String FarbeAuswaehlen(int welcheFarbe) {
		String farbe = "";
		if (farbenAuswahl.get(welcheFarbe) != null) {
			farbe = farbenAuswahl.get(welcheFarbe);
			farbenAuswahl.remove(welcheFarbe);
			farbenAuswahl.add(welcheFarbe, null);
		} else {
			System.out.println("Farbe schon vergeben!");
		}
		return farbe;
	}

	public Player getPlayerById(int iD) {
		Player spieler = null;
		for(Player player: gamerListe) {
			if(player.getNummer() == iD) {
				return player;
			}
		}
		return spieler;
	}

	public ArrayList<String> getFarbauswahl() {
		return farbenAuswahl;
	}

	public ArrayList<Player> getPlayers() {
		return gamerListe;
	}

	public void setSpielerAnzahl(int spielerAnzahl) throws UngueltigeAnzahlSpielerException {
		if(spielerAnzahl > 6 || spielerAnzahl < 2) {
			throw new UngueltigeAnzahlSpielerException(spielerAnzahl);
		}
		this.spielerAnzahl = spielerAnzahl;
	}
	
	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}
	
//	Methode die sagt wieviele Player es gibt, man kann auch getPlayers().size() aufrufen
	public int getAnzahlPlayer() {
		return gamerListe.size();
	}

	public void addPlayer(String name, String farbe, int nummer) throws SpielerNameExistiertBereitsException { 
		Player player = new Player(name, farbe, nummer);
		for(Player p: gamerListe) {
			if(p.getName().equals(name)) {
				throw new SpielerNameExistiertBereitsException(name);
			}
		}
		gamerListe.add(player);
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

	public ArrayList<Color> getColorArray() {
		return colorArray;
	}
	
	public void addColor(Color color) {
		this.colorArray.add(color);
	}
}
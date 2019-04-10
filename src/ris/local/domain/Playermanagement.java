package ris.local.domain;
//cui frage 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import ris.local.valueobjects.Player;

public class Playermanagement {

	private List<Player> gamerListe = new ArrayList<Player>();
	private ArrayList<String> farbenAuswahl = new ArrayList();

	public Playermanagement() {
		farbenAuswahl.add("rot");
		farbenAuswahl.add("gruen");
		farbenAuswahl.add("blau");
		farbenAuswahl.add("gelb");
		farbenAuswahl.add("pink");
		farbenAuswahl.add("schwarz");
	}

	public ArrayList<String> setFarbeAuswaehlen(String farbe) {
//		String farbe = farbe ;

		switch (farbe) {
		case "r":
			if(farbenAuswahl.get(0)!= null) {
			farbenAuswahl.add(0, null);
			farbe = "rot";
			farbenAuswahl.remove("rot");
			} else {
				System.out.println("DIE FARBE WURDE SCHON AUSGEWÄHLT!!!");
//				setFarbeAuswaehlen(farbe);
				farbe = "pink";
			}
			break;
		case "g":
			farbenAuswahl.remove("gruen");
			farbe = "gruen";
			break;
		case "b":
			farbenAuswahl.remove("blau");
			farbe = "blau";
			break;
		default:
//			farbeAuswaehlen();
		}
		return farbenAuswahl;
	}

	public ArrayList<String> getFarbauswahl() {
		return farbenAuswahl;
	}
	public List<Player> getPlayers() {
		return gamerListe;

	}
	
//	Methode die sagt wieviele Spieler es gibt, man kann auch getPlayers().size() aufrufen ^^
	public int getAnzahlPlayer() {
		return gamerListe.size();
	}
//	public void addPlayer(String name, String farbe, int nummer) {} // 


	public Player addPlayer(String name, String farbe, int nummer) { // hier die von Teschke Methode. Warum Player?
		Player player = new Player(name, farbe, nummer);
		gamerListe.add(player);
		return player;
	}

	// gibt Anzahl an spielern zurueck
	public int getPlayerAnzahl() { // es könnte sein das diese Methode ueberfluessig ist..
		return gamerListe.size();
	}

	// public void addElements(ArrayList<Land>) {

	// }
}
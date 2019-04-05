package ris.local.domain;

import java.util.List;
import java.util.Vector;

import ris.local.valueobjects.Player;

public class PlayerManagement {
	private int spielerAnzahl;
	private List<Player> playerBestand = new Vector<Player>();
	
	public void spielerAnlegen(String name, String farbe, int spielerNr) {
		Player player = new Player(name, farbe, spielerNr);
		playerBestand.add(player);
//		System.out.println(spielerAnzahl + " spieler wurden angelegt");
	}
	
	public int getSpielerAnzahl() {
		return playerBestand.size();
	}
	
	public List<Player> getPlayerBestand() {
		return playerBestand;
	}
	
}

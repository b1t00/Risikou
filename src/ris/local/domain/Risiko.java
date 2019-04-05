package ris.local.domain;

import ris.local.valueobjects.Player;

public class Risiko {

	private Worldmanagement worldMg;
	private Playermanagement playerMg;
	private Spiellogik logik;
	private Player player;
	
	// Konstruktor
	
	public Risiko() {
		worldMg = new Worldmanagement();
		playerMg = new Playermanagement();
		logik = new Spiellogik(worldMg, playerMg);
	}
	
	public void spielAnlegen(int anzahl) {
		// ...
		// return gameObjekt;
	}
	
	public Player spielerAnlegen(String name, String farbe, int nummer) {
		playerMg.addPlayer(name, farbe, nummer);
	
		// playerMg.addPlayer(player);
		// gameObjekt.add(gamer);
		return player;
	}
	
	public Player gibAktivenSpieler() {
		return logik.gibAktivenSpieler();
	}
	
	public void naechsterSpieler() {
		return logik.naechsterSpieler();
	}

}

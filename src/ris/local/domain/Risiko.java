package ris.local.domain;

import ris.local.valueobjects.Gamer;

public class Risiko {

	private Worldmanagement worldMg;
	private Playermanagement playerMg;
	private Spiellogik logik;
	
	// Konstruktor
	
	public Risiko() {
		worldMg = new Worldmanagement();
		playerMg = new Playermanagement();
		logik = new Spiellogik();
	}
	
	public void spielAnlegen(int anzahl) {
		// ...
		// return gameObjekt;
	}
	
	public Gamer spielerAnlegen(String name, String farbe) {
		Gamer gamer = new Gamer(name, farbe);
		playerMg.addGamer(gamer);
		// gameObjekt.add(gamer);
		return gamer;
	}
	
	public Gamer gibAktivenSpieler() {
		return logik.gibAktivenSpieler();
	}
	
	public void naechsterSpieler() {
		return logik.naechsterSpieler();
	}

}

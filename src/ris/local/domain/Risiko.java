package ris.local.domain;

import ris.local.valueobjects.Gamer;

public class Risiko {

	private Weltverwaltung weltVW;
	private Gamerverwaltung gamerVW;
	private Spiellogik logik;
	
	// Konstruktor
	
	public void spielAnlegen(int anzahl) {
		// ...
		// return gameObjekt;
	}
	
	public Gamer spielerAnlegen(String name, String farbe) {
		Gamer gamer = new Gamer(name, farbe);
		gamerVW.addGamer(gamer);
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

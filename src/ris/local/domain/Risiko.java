package ris.local.domain;

import ris.local.valueobjects.Land;
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
		Player player = playerMg.addPlayer(name, farbe, nummer);
	
		// playerMg.addPlayer(player);
		// gameObjekt.add(gamer);
		return player;
	}
	
	//@to: Methode um Laender am Anfang zuf‰llig zu verteilen;
	public void verteileEinheiten() {
		logik.verteileEinheiten();
	}

	//@to: Methode die sagt wer anf‰ngt ... generelle frage: die methoden werden hier einfach nur stumpf weitergeleitet, damit man von der cui drauf zugreifen kann. 
	// weiﬂ ncht ob das richtig ist, in der bibliothek wirds ‰hnlich gemacht. #losch
	public void whoBegins() {
		logik.whoBegins();
	}

//	public Player gibAktivenSpieler() {
//		return logik.gibAktivenSpieler();
//	}
//	
//	public void naechsterSpieler() {
//		return logik.naechsterSpieler();
//	}
	
	public static void main(String[] args) {
		 Risiko risiTest = new Risiko();
		/* System.out.println(risiTest.logik.shuffleLaender());
		risiTest.logik.shuffleLaender();*/
		//risiTest.worldMg.erstelleWelt();
	}

}

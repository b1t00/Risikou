package ris.local.domain;

import java.util.List;

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
	
	//@to: Methode um Laender am Anfang zuf‰llig zu verteilen;
	public void verteileEinheiten() {
		logik.verteileEinheiten();
	}
	
	//@to: Methode die sagt wer anf‰ngt ... generelle frage: die methoden werden hier einfach nur stumpf weitergeleitet, damit man von der cui drauf zugreifen kann. 
	// weiﬂ ncht ob das richtig ist, in der bibliothek wirds ‰hnlich gemacht. #losch
	public void whoBegins() {
		logik.whoBegins();
	}
	
	
	public Player spielerAnlegen(String name, String farbe, int nummer) {
		Player player = playerMg.addPlayer(name, farbe, nummer);

		// playerMg.addPlayer(player);
		// gameObjekt.add(gamer);
		return player;
	}

//	public Player gibAktivenSpieler() {
//		return logik.gibAktivenSpieler();
//	}
//	
//	public void naechsterSpieler() {
//		return logik.naechsterSpieler();
//	}

	public static void main(String[] args) {

		Risiko rT = new Risiko();
		rT.spielerAnlegen("Anna", "rot", 1);
		rT.spielerAnlegen("Leo", "blau", 2);
		rT.spielerAnlegen("Normi", "gruen", 3);
		rT.spielerAnlegen("berta", "gruen", 3);
		rT.spielerAnlegen("Normi", "gruen", 3);
		rT.spielerAnlegen("Normi3", "gruen", 3);

		
		
		int p = rT.logik.laenderAnzahl;
		System.out.println("es gibt insgesammt " + p);
		
//		rT.logik.shuffleLaender();
		rT.verteileEinheiten();
		List<Player> spielerListe = rT.playerMg.getPlayers();
		for (int i = 0; i < rT.playerMg.getPlayerAnzahl(); i++) {
			System.out.println(spielerListe.get(i).gibLaenderAus());
			System.out.println(spielerListe.get(i).getBesitz().size());
			System.out.println("-----");
		}
		rT.whoBegins(); // kann man in verteileEinheiten tun
	}

}

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
		rT.logik.verteileLaender();
		List<Player> spielerListe = rT.playerMg.getPlayers();
		for (int i = 0; i < rT.playerMg.getPlayerAnzahl(); i++) {
			System.out.println(spielerListe.get(i).gibLaenderAus());
			System.out.println(spielerListe.get(i).getBesitz().size());
			System.out.println("-----");
		}
		rT.logik.whoBegins(); // kann man in verteileLaender tun
	}

}

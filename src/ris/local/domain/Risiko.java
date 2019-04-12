package ris.local.domain;


import java.util.ArrayList;
import java.util.List;

import ris.local.valueobjects.Land;
import ris.local.valueobjects.Player;

public class Risiko {

	private Worldmanagement worldMg;
	private PlayerManagement playerMg;
	private Spiellogik logik;
	private Player player;
	
	

	// Konstruktor

	public Risiko() {
		worldMg = new Worldmanagement();
		playerMg = new PlayerManagement();
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
	
	//@to: Methode die sagt wer anf‰ngt ... #to generelle frage: die methoden werden hier einfach nur stumpf weitergeleitet, damit man von der cui drauf zugreifen kann. 
	// weiﬂ ncht ob das richtig ist, in der bibliothek wirds ‰hnlich gemacht. #losch
	//beachte.. verteileEinheiten sollte vor dieser Methode implementiert werden.. ansonsten machts ja auch kein sinn
	public Player whoBegins() {
		return logik.whoBegins();
	}
	
	
	public Player spielerAnlegen(String name, String farbe, int nummer) {
		Player player = playerMg.addPlayer(name, farbe, nummer);

		// playerMg.addPlayer(player);
		// gameObjekt.add(gamer);
		return player;
	}

	public int getAnzahlPlayer() {
		return playerMg.getAnzahlPlayer();
	}
	public void gibLaenderAus() {
		player.gibLaenderAus();

	}

	public Player gibAktivenSpieler() {
		return logik.gibAktivenSpieler();
	}
//	
	public void machNaechsterSpieler() {
		logik.setSpielrunden();
	}
	
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	public ArrayList<Land> getAngriffsLaender(Player angreifer){
		ArrayList<Land> moeglicheLaender = logik.getLaenderMitMehrAlsEinerEinheit(angreifer);
		ArrayList<Land> attackLaender = logik.getLaenderMitFeindlichenNachbarn(angreifer, moeglicheLaender);
		return attackLaender;
	} 
	
	public ArrayList<Land> getFeindlicheNachbarn (Land attackLand) {
		ArrayList<Land> feindlicheLaender = logik.getFeindlicheNachbarn(attackLand);
		return feindlicheLaender;
	}
	
	public ArrayList<Integer> attack (Land att, Land def, int attEinheiten, int defEinheiten) {
		ArrayList<Integer> ergebnis = logik.attack(att, def, attEinheiten, defEinheiten);
		return ergebnis;
	}
	
	public Land getLandById (int zahl) {
		Land land = worldMg.getLandById(zahl);
		return land;
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	public void setLand(Player player1, Player player2) {
		player1.setBesitz(worldMg.getLaender().get(5));
		player1.setBesitz(worldMg.getLaender().get(6));
		player1.setBesitz(worldMg.getLaender().get(8));
		player1.setBesitz(worldMg.getLaender().get(2));
		player1.setBesitz(worldMg.getLaender().get(3));
		player2.setBesitz(worldMg.getLaender().get(7));
		player2.setBesitz(worldMg.getLaender().get(9));
		player2.setBesitz(worldMg.getLaender().get(10));
		player2.setBesitz(worldMg.getLaender().get(0));
		player2.setBesitz(worldMg.getLaender().get(1));
		player2.setBesitz(worldMg.getLaender().get(4));
		worldMg.getLaender().get(0).setBesitzer(player2);
		worldMg.getLaender().get(1).setBesitzer(player2);
		worldMg.getLaender().get(2).setBesitzer(player1);
		worldMg.getLaender().get(3).setBesitzer(player1);
		worldMg.getLaender().get(4).setBesitzer(player2);
		worldMg.getLaender().get(5).setBesitzer(player1);
		worldMg.getLaender().get(6).setBesitzer(player1);
		worldMg.getLaender().get(7).setBesitzer(player2);
		worldMg.getLaender().get(8).setBesitzer(player1);
		worldMg.getLaender().get(9).setBesitzer(player2);
		worldMg.getLaender().get(10).setBesitzer(player2);
		worldMg.getLaender().get(6).setEinheiten(6);
		worldMg.getLaender().get(7).setEinheiten(3);
	}
	
	
	
//	'''''''''' PlayerManagement ''''''''''''''''
	public ArrayList<String> getFarbauswahl() {
		return playerMg.getFarbauswahl();
	}
	
	public ArrayList<String> setFarbeAuswaehlen(String farbe) {
		return playerMg.setFarbeAuswaehlen(farbe);
	
	}
	
//	************************* TestMain **************************
	
	public static void main(String[] args) {
		Risiko test = new Risiko();
		test.spielerAnlegen("normen1", "blau", 0);
		test.spielerAnlegen("normen2", "farbe", 1);
		test.spielerAnlegen("normen3", "farbe", 2);
//		test.spielerAnlegen("normen4", "farbe", 3);
//		test.spielerAnlegen("normen5", "farb", 4);
//		test.spielerAnlegen("normen6", "farbe", 6);
		test.verteileEinheiten();
		
		
		for(int i = 0; i < 199 ; i++) {
			System.out.println(test.gibAktivenSpieler());
			test.machNaechsterSpieler();
			
		}
	}

}

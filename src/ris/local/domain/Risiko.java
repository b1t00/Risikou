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
	
	
	//@to: Methode um Laender am Anfang zufällig zu verteilen;
	public void verteileEinheiten() {
		logik.verteileEinheiten();
	}
	
	//@to: Methode die sagt wer anfängt ... #to generelle frage: die methoden werden hier einfach nur stumpf weitergeleitet, damit man von der cui drauf zugreifen kann. 
	// weiß ncht ob das richtig ist, in der bibliothek wirds ähnlich gemacht. #losch
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
	
	public ArrayList<String> gibLaenderUndNummer(){
		return player.gibLaenderUndNummer();
	}

	public int getAnzahlPlayer() {
		return playerMg.getAnzahlPlayer();
	}
	public void gibLaenderAus() {
	//	player.gibLaenderAus();

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
		ArrayList<Land> attackLaender = logik.getLaenderMitFeindlichenNachbarn(moeglicheLaender);
		return attackLaender;
	} 
	
	public ArrayList<Land> getFeindlicheLaender (Land attackLand) {
		ArrayList<Land> feindlicheLaender = logik.getFeindlicheLaender(attackLand);
		return feindlicheLaender;
	}
	
	public Land getLandById (int zahl) {
		Land land = worldMg.getLandById(zahl);
		return land;
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
//	'''''''''' PlayerManagement ''''''''''''''''
	public ArrayList<String> getFarbauswahl() {
		return playerMg.getFarbauswahl();
	}
	
	public ArrayList<String> setFarbeAuswaehlen(String farbe) {
		return playerMg.setFarbeAuswaehlen(farbe);
	
	}
	
//	*************************TestMain**************************
	
	public static void main(String[] args) {
		Risiko test = new Risiko();
		test.spielerAnlegen("normen1", "blau", 0);
		test.spielerAnlegen("normen2", "farbe", 1);
		test.spielerAnlegen("normen3", "farbe", 2);
		test.spielerAnlegen("normen4", "farbe", 3);
		test.spielerAnlegen("normen5", "farb", 4);
//		test.spielerAnlegen("normen6", "farbe", 6);
		test.verteileEinheiten();
//		Player playertest = test.gibAktivenSpieler();
//		for(Land land : test.worldMg.getLaender()) {
//			System.out.println(land.getNummer() + " : " + land.getName() + " gehört " + land.getBesitzer().getName());
////			System.out.println();
//		}
		System.out.println("Alle Laender und Besitzer (ungeordnet.. ich weiß nicht wieso)");//koente evlt so in die cui tun für die Weltausgabe
		ArrayList<Land> alleLaender = test.worldMg.getLaender();
		for(Land land : alleLaender) {
			System.out.println(land.getNummer() +" : " + land.getName() + " - gehört : " + land.getBesitzer().getName());
		}
		System.out.println("------------------ alle spieler reinfolge nach getAktivenSpieler------------------");
		
		for(int i = 0; i < 5 ; i++) {
			System.out.println(test.gibAktivenSpieler() + " und besitzt die Länder:");
		for(int k = 0 ; k < test.gibAktivenSpieler().getBesitz().size(); k++) {
			System.out.println(test.gibAktivenSpieler().gibLaenderUndNummer().get(k) + " gehört " + test.gibAktivenSpieler().getName());
		}
		test.machNaechsterSpieler();	
		}
	}

}

package ris.local.domain;


import java.util.ArrayList;
import java.util.List;


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
	
	//@to: Methode die sagt wer anf‰ngt ... generelle frage: die methoden werden hier einfach nur stumpf weitergeleitet, damit man von der cui drauf zugreifen kann. 
	// weiﬂ ncht ob das richtig ist, in der bibliothek wirds ‰hnlich gemacht. #losch
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

//	public Player gibAktivenSpieler() {
//		return logik.gibAktivenSpieler();
//	}
//	
//	public void naechsterSpieler() {
//		return logik.naechsterSpieler();
//	}
	
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
		test.spielerAnlegen("anna", "rot", 0);
		test.spielerAnlegen("leo", "gruen", 1);
		test.spielerAnlegen("normen1", "blau", 2);
		test.spielerAnlegen("normen2", "farbe2", 3);
		test.spielerAnlegen("normen3", "farbe3", 3);
		test.verteileEinheiten();
		
//		System.out.println(test.worldMg.getLaender().size());
		System.out.println(test.whoBegins());
	}

}

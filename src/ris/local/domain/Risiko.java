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
	
	public Player gibAktivenSpieler() {
		return logik.gibAktivenSpieler();
		//@annie: hier muss der name vom aktiven Spieler zurückgegeben werden
	}
	
	public void naechsterSpieler() {
		return logik.naechsterSpieler();
	}

}
//in der methode vielleicht name und besitzt herausnehmen

/* @annie: irgendwo array mit farben, auf die zugegriffen werden kann mit methode gibAlleFarben()
 * bei erstellung des Players muss dann die übergebene Farbe aus dem array herausgenommen werden */
// @annie: die Verteilung der Einheiten direkt nach dem Erstellen der Spieler wird mit risiko.verteileEinheiten() aufgerufen
public String gibLaenderAus(String player) {
	//player über namen herausfinden
	return player.gibLaenderAus();
} 
public void setztEinheit(int land, int einheit) {
	//int land in Land land umwandeln und dann setEinheit()
}
// @annie: getAnzahlPlayer
//@annie: methode getFeinde(string angreifer, int land)
//@annie: methode getEinheiten(int land)
//@annie: methode getBesitzer(int land)
//@annie: methode attack in logik muss einen string gewinner zurückgeben
//@annie: spieler werden immer nur mit namen genutzt
//@annie: getNachbarn() -> eigene Nachbarn vom Spieler zurückgeben
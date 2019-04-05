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
		//@annie: hier muss der name vom aktiven Spieler zur�ckgegeben werden
	}
	
	public void naechsterSpieler() {
		return logik.naechsterSpieler();
	}

}

/* @annie: irgendwo array mit farben, auf die zugegriffen werden kann mit methode gibAlleFarben()
 * bei erstellung des Players muss dann die �bergebene Farbe aus dem array herausgenommen werden */
// @annie: die Verteilung der Einheiten direkt nach dem Erstellen der Spieler wird mit risiko.verteileEinheiten() aufgerufen
public String gibLaenderAus(Player player) {
	return player.gibLaenderAus();
} // in der methode vielleicht name und besitzt herausnehmen
public void setztEinheit(int land, int einheit) {
	//int land in Land land umwandeln und dann setEinheit()
}
// @annie: getAnzahlPlayer
//@annie: methode getFeinde(Player angreifer, int land)
//@annie: methode getEinheiten(int land)
//@annie: methode getBesitzer(int land)
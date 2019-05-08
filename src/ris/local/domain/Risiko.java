package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.UngueltigeAnzahlEinheitenException;
import ris.local.exception.ZuWenigEinheitenException;
import ris.local.persistence.FilePersistenceManager;
import ris.local.valueobjects.Einheitenkarte;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Player;

public class Risiko implements Serializable {

	private WorldManagement worldMg;
	private PlayerManagement playerMg;
	private Spiellogik logik;
	private Player player, gewinner;
	private ArrayList<Einheitenkarte> einheitenkartenStapel;


	public Risiko() {
		worldMg = new WorldManagement();
		playerMg = new PlayerManagement();
		logik = new Spiellogik(worldMg, playerMg);
		EinheitenkartenManagement einheitenkartenMg = new EinheitenkartenManagement();
		einheitenkartenStapel = einheitenkartenMg.getEinheitenkarten();
	}

//	public void spielAnlegen(int anzahl) {
//		// ...
//		// return gameObjekt;
//	}

	// @to: Methode um Laender am Anfang zuf�llig zu verteilen;
	public void verteileEinheiten() {
		logik.verteileEinheiten();
	}

	public void verteileMissionen() {
		logik.verteileMissionen();
	}

	// @to: Methode die sagt wer anf�ngt ... #to generelle frage: die methoden
	// werden hier einfach nur stumpf weitergeleitet, damit man von der cui drauf
	// zugreifen kann.
	// wei� ncht ob das richtig ist, in der bibliothek wirds �hnlich gemacht. #losch
	// @annie: ich glaub das ist richtig so
	// beachte.. verteileEinheiten sollte vor dieser Methode implementiert werden..
	// ansonsten machts ja auch kein sinn
	public Player whoBegins() {
		return logik.whoBegins();
	}

	public Player PlayerAnlegen(String name, String farbe, int nummer) {
		Player player = playerMg.addPlayer(name, farbe, nummer);
		return player;
	}

	public void setzeAktivenPlayer() {
		logik.setzeStartSpieler();
	}

	public ArrayList<String> gibLaenderUndNummer() {
		return player.gibLaenderUndNummer();
	}

	public ArrayList<Player> getPlayerArray() {
		return playerMg.getPlayers();
	}

	public int getAnzahlPlayer() {
		return playerMg.getAnzahlPlayer();
	}

	public Land getLandById(int zahl) {
		Land land = worldMg.getLandById(zahl);
		return land;
	}

	public Player gibAktivenPlayer() {
		return logik.gibAktivenPlayer();
	}

	public void machNaechsterPlayer() {
		logik.naechsteSpielrunde();
	}

	// Missionsabfragen gilt f�r alle spieler
	public boolean allMissionsComplete() {
		for (Player play : playerMg.getPlayers()) {
			if (play.isMissionComplete(play)) {
				gewinner = play;
				return true;
			}
		}
		return false;
	}

	// Missionsabfrage vom akriven Spieler TODO: der spieler der in seiner Runde
	// gewonnen hat, hat gewonnen??
	public boolean rundeMissionComplete(Player play) {

		if (play.isMissionComplete(play)) {
			gewinner = play;
			return true;
		} else {
			return false;
		}
	}

	// fragt den player, ob er ein land eingenommen hat via boolean gutschriftEinheitenkarte und setzt diesen dann auf false
	public boolean zieheEinheitenkarte(Player aktiverPlayer) {
		if (aktiverPlayer.getGutschriftEinheitenkarte()) {
			Einheitenkarte neueKarte = einheitenkartenStapel.remove(0);
			aktiverPlayer.setEinheitenkarte(neueKarte);
			aktiverPlayer.setGutschriftEinheitenkarte(false);
			return true;
		}
		return false;
	}
	
	public int[] einheitenkartenTauschkombiVorhanden(Player aktiverPlayer) {
		//Symbolarray mit Anzahl der vorhandenen Einheitskarten
		//0 = Kanone, 1 = Reiter, 2 = Soldat
		int[] Symbolarray = aktiverPlayer.einheitenkartenKombi();
		//tauschkombi sagt aus, wieviele kombis vorhanden sind
		//0 = Kanone, 1 = Reiter, 2 = Soldat, 3 = Reihe
		int[] Tauschkombi = new int[4];
		if (Symbolarray[0] >= 3) {
			Tauschkombi[0] = Symbolarray[0]%3;
		}
		if (Symbolarray[1] >= 3) {
			Tauschkombi[1] = Symbolarray[1]%3;
		}
		if (Symbolarray[2] >= 3) {
			Tauschkombi[2] = Symbolarray[2]%3;
		}
		if (Symbolarray[0] > 0 && Symbolarray[1] > 0 && Symbolarray[2] > 0) {
			int min = Symbolarray[0];
			for (int i = 1; i < Symbolarray.length; i++) {
				if (Symbolarray[i] < min) {
					min = Symbolarray[i];
				}
			}
			Tauschkombi[3] = min;
		}
		return Tauschkombi;
	}

	// get Gewinner kann nur geholt werden, wenn einer eine Mission erf�llt hat bzw
	// missionenCompletet True ist..
	public Player getGewinner() {
		return gewinner;
	}

	public ArrayList<Land> getEigeneNachbarn(Land land) {
		return worldMg.getEigeneNachbarn(land);
	}

	public ArrayList<Land> getEinheitenVerschiebenVonLaender(Player player) {
		ArrayList<Land> verschiebbareEinheitenLaender = logik.getLaenderMitMehrAlsEinerEinheit(player);
		return logik.getLaenderMitEigenenNachbarn(verschiebbareEinheitenLaender);
	}

	// WELT AUSGABE->
	public ArrayList<Land> gibWeltAus() {
		Collections.sort(worldMg.getLaender());
		return worldMg.getLaender();
	}

	public ArrayList<Kontinent> gibAlleKontinente() {
		return worldMg.getKontinente();
	}

	public ArrayList<Player> gibAllePlayer() {
		return playerMg.getPlayers();
	}
	// WELT AUSGABE <-

	// ----------------------------------------einheiten-------------------------------------------------
	public int errechneVerfuegbareEinheiten(Player aktiverPlayer) {
		int verfuegbareEinheiten = logik.errechneVerfuegbareEinheiten(aktiverPlayer);
		return verfuegbareEinheiten;
	}
	// ----------------------------------------einheiten-------------------------------------------------

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Start^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	public ArrayList<Land> getAngriffsLaender(Player angreifer) {
		ArrayList<Land> moeglicheLaender = logik.getLaenderMitMehrAlsEinerEinheit(angreifer);
		ArrayList<Land> attackLaender = logik.getLaenderMitFeindlichenNachbarn(angreifer, moeglicheLaender);
		return attackLaender;
	}

	public ArrayList<Land> getFeindlicheNachbarn(Land attackLand) {
		ArrayList<Land> feindlicheLaender = logik.getFeindlicheNachbarn(attackLand);
		return feindlicheLaender;
	}

//	public AttackResult attack (Land att, Land def, int attEinheiten, int defEinheiten) {
//		// AttackResult
//		//	- W�rfel Angreifer
//		//  - ...
//		//  - Ergebnis ...
//	}

//	public ArrayList<Integer> attack (Land att, Land def, int attEinheiten, int defEinheiten) throws LaenderNichtBenachbartException, NichtGenugEinheitenException {

	public ArrayList<Integer> attack(Land att, Land def, int attEinheiten, int defEinheiten, ArrayList<Integer> aList,
			ArrayList<Integer> dList) {
		ArrayList<Integer> ergebnis = logik.attack(att, def, attEinheiten, defEinheiten, aList, dList);
		return ergebnis;
	}

	public ArrayList<Integer> diceDefense(int defUnit) throws UngueltigeAnzahlEinheitenException {
		return logik.diceDefense(defUnit);
	}

	public ArrayList<Integer> diceAttack(int attUnit) throws UngueltigeAnzahlEinheitenException {
		return logik.diceAttack(attUnit);
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Einheiten
	// verschieben^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public void verschiebeEinheiten(Land start, Land ziel, int menge)
			throws LandExistiertNichtException, ZuWenigEinheitenException {
		logik.moveUnits(start, ziel, menge);
	}

	public void spielSpeichern(String datei) {
		FilePersistenceManager fileMg = new FilePersistenceManager();
		fileMg.speichern(this, datei);
	}

	public void spielLaden(String datei) {
		FilePersistenceManager fileMg = new FilePersistenceManager();
		Risiko risikoSpeicher = fileMg.laden(datei);
		if (risikoSpeicher != null) {
			this.worldMg = risikoSpeicher.worldMg;
			this.playerMg = risikoSpeicher.playerMg;
			this.logik = risikoSpeicher.logik;
			this.einheitenkartenStapel = risikoSpeicher.einheitenkartenStapel;
			// namen der datei, damit speicherort immer der gleiche bleibt
		}
	}

//	'''''''''' PlayerManagement ''''''''''''''''
	public ArrayList<String> getFarbauswahl() {
		return playerMg.getFarbauswahl();
	}

	public String setFarbeAuswaehlen(String farbe) { // hier string
		return playerMg.menuFarbeAuswaehlen(farbe);

	}

	public boolean getRichtigeEingabe() {
		return playerMg.getRichtigeEingabe();
	}

	// test main
//	public static void main(String[] args) {
//		Risiko test = new Risiko();
//		test.PlayerAnlegen("a", "rot", 0);
//		test.PlayerAnlegen("b", "gruen", 1);
////		test.verteileMissionen();
////		test.getPlayerArray().get(0).getMission();
//		
//	}
	// TODO: @tobi nach jeder rund einbinden?? Spieler aus Array l�schen
	public boolean isPlayerDead(Player play) {
		return play.isDead();
	}
}

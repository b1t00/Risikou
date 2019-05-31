package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.LandInBesitzException;
import ris.local.exception.UngueltigeAnzahlEinheitenException;
import ris.local.exception.ZuWenigEinheitenException;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;
import ris.local.persistence.FilePersistenceManager;
import ris.local.valueobjects.Risikokarte;
import ris.local.valueobjects.State;
import ris.local.valueobjects.Turn;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Player;

public class Risiko implements Serializable {

	private WorldManagement worldMg;
	private PlayerManagement playerMg;
	private Spiellogik logik;
	private Player player;
	private ArrayList<Risikokarte> einheitenkartenStapel;
	private Turn turn;

	public Risiko() {
		worldMg = new WorldManagement();
		playerMg = new PlayerManagement();
		logik = new Spiellogik(worldMg, playerMg);
		RisikokartenManagement einheitenkartenMg = new RisikokartenManagement();
		einheitenkartenStapel = einheitenkartenMg.getEinheitenkarten();
		turn = new Turn();
		turn.state = State.SETUNITS;
	}

	public State getCurrentState() {
		return turn.getCurrentState();
	}

	public void setNextState() {
		turn.setNextState();
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
	// wei� ncht ob das richtig ist, in der bibliothek wirds �hnlich gemacht.
	// #losch
	// @annie: ich glaub das ist richtig so
	// beachte.. verteileEinheiten sollte vor dieser Methode implementiert werden..
	// ansonsten machts ja auch kein sinn
	public Player whoBegins() {
		return logik.whoBegins();
	}

	public Player playerAnlegen(String name, String farbe, int nummer) {
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

	public void setNaechsterPlayer() {
		logik.naechsteSpielrunde();
	}

//	******************************>Missions-Sachen<**************************
	// Missionsabfragen gilt f�r alle spieler
	public boolean allMissionsComplete() {
		return logik.allMissionsComplete();
	}

	public boolean rundeMissionComplete(Player play) {
		return logik.rundeMissionComplete(play);
	}

	public Player getGewinner() {
		return logik.getGewinner();
	}

	// fragt den player, ob er ein land eingenommen hat via boolean
	// gutschriftEinheitenkarte und setzt diesen dann auf false
	public boolean zieheEinheitenkarte(Player aktiverPlayer) {
		if (aktiverPlayer.getGutschriftEinheitenkarte()) {
			Risikokarte neueKarte = einheitenkartenStapel.remove(0);
			aktiverPlayer.setEinheitenkarte(neueKarte);
			aktiverPlayer.setGutschriftEinheitenkarte(false);
			return true;
		}
		return false;
	}

	public boolean changePossible(Player aktiverPlayer) {
		return aktiverPlayer.changePossible();
	}

	public int[] risikokartenTauschkombiVorhanden(Player aktiverPlayer) {
		return logik.risikokartenTauschkombiVorhanden(aktiverPlayer);
	}

	public boolean isGueltigeTauschkombi() {
		return true; //mal eingebaut
	};

	// get Gewinner kann nur geholt werden, wenn einer eine Mission erf�llt hat
	// bzw
	// missionenCompletet True ist..

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

	public ArrayList<Land> getFeindlicheNachbarn(Land attackLand) throws LandExistiertNichtException {
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
			ArrayList<Integer> dList) throws ZuWenigEinheitenNichtMoeglichExeption {
		ArrayList<Integer> ergebnis;
		ergebnis = logik.attack(att, def, attEinheiten, defEinheiten, aList, dList);
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
			throws LandExistiertNichtException, ZuWenigEinheitenException, ZuWenigEinheitenNichtMoeglichExeption {
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
		System.out.println("eins");
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

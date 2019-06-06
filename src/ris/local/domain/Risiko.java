package ris.local.domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.UngueltigeAnzahlEinheitenException;
import ris.local.exception.ZuWenigEinheitenException;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;
import ris.local.persistence.FilePersistenceManager;
import ris.local.valueobjects.Attack;
import ris.local.valueobjects.GameObject;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Player;
import ris.local.valueobjects.Risikokarte;
import ris.local.valueobjects.Risikokarte.Symbol;
import ris.local.valueobjects.State;
import ris.local.valueobjects.Turn;

public class Risiko {

	private WorldManagement worldMg;
	private PlayerManagement playerMg;
	private Spiellogik logik;
	private Player player;
	private ArrayList<Risikokarte> einheitenkartenStapel;
	private Turn turn;
	private GameObject game;

	public Risiko() {
		worldMg = new WorldManagement();
		playerMg = new PlayerManagement();
		turn = new Turn(playerMg);
		logik = new Spiellogik(worldMg, playerMg, turn);
		RisikokartenManagement einheitenkartenMg = new RisikokartenManagement();
		einheitenkartenStapel = einheitenkartenMg.getEinheitenkarten();
		turn.state = State.SETUNITS;
		game = new GameObject(playerMg.getPlayers(), turn);
	}

	public State getCurrentState() {
		return turn.getCurrentState();
	}

	public void setNextState() {
		turn.setNextState();
	}
	
	public boolean getTauschZeit() {
		return turn.getTauschZeit();
	}
	
	public void setTauschZeit(boolean tauschZeit) {
		turn.setTauschZeit(tauschZeit);
	}
	
	public boolean getLandClickZeit() {
		return turn.getLandClickZeit();
	}
	
	public void setLandClickZeit(boolean landClickZeit) {
		turn.setLandClickZeit(landClickZeit);
	}

	// @to: Methode um Laender am Anfang zufaellig zu verteilen;
	public void verteileEinheiten() {
		logik.verteileEinheiten();
	}

	public void verteileMissionen() {
		logik.verteileMissionen();
	}

	// @to: Methode die sagt wer anfï¿½ngt ... #to generelle frage: die methoden
	// werden hier einfach nur stumpf weitergeleitet, damit man von der cui drauf
	// zugreifen kann.
	// weiï¿½ ncht ob das richtig ist, in der bibliothek wirds ï¿½hnlich gemacht.
	// #losch
	// @annie: ich glaub das ist richtig so
	// beachte.. verteileEinheiten sollte vor dieser Methode implementiert werden..
	// ansonsten machts ja auch kein sinn
	public void whoBegins() {
		turn.setAktivenPlayer(logik.whoBegins());
		turn.setPlayerList(playerMg.getPlayers());
	}

	public Player playerAnlegen(String name, String farbe, int nummer) {
		Player player = playerMg.addPlayer(name, farbe, nummer);
		return player;
	}

	// TODO: diese methode kann wahrschienich weg, nochmal überprüfen!
	public void setzeAktivenPlayer() {
		turn.setAktivenPlayer(logik.setzeStartSpieler());
	}

	public ArrayList<String> gibLaenderUndNummer() {
		return player.gibLaenderUndNummer();
	}

	public ArrayList<Land> getEigeneLaender(Player player) {
		return player.getBesitz();
	}

	public ArrayList<Player> getPlayerArray() {
		return playerMg.getPlayers();
	}

	public int getAnzahlPlayer() {
		return playerMg.getAnzahlPlayer();
	}

	public Land getLandById(int zahl) throws LandExistiertNichtException {
		Land land = worldMg.getLandById(zahl);
		return land;
	}

	public Player gibAktivenPlayer() {
		return turn.gibAktivenPlayer();
	}

	public void setNextPlayer() {
		turn.naechsteSpielrunde();
	}

//	******************************>Missions-Sachen<**************************
	// Missionsabfragen gilt fuer alle spieler
	public boolean allMissionsComplete() {
		return logik.allMissionsComplete();
	}

	public boolean rundeMissionComplete(Player play) {
		return logik.rundeMissionComplete(play);
	}

	public Player getGewinner() {
		return logik.getGewinner();
	}

//	****************************RISIKOKARTEN************************************

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

	// gibt zurück, ob ein Player Risikokarten gegen Einheiten eintauschen kann
	//TODO: wird aktuell direkt beim Player aufgerufen -> hier loeschen oder aendern! 
	public boolean changePossible(Player aktiverPlayer) {
		return aktiverPlayer.changePossible();
	}
	
	public boolean mussTauschen(Player aktiverPlayer) {
		return aktiverPlayer.mussTauschen();
	}

	public boolean isGueltigeTauschkombi(Symbol s1, Symbol s2, Symbol s3) {
		return logik.isGueltigeTauschkombi(s1, s2, s3);
	}

	public ArrayList<Risikokarte> getRisikoKarten() {
		return einheitenkartenStapel;
	};

//	****************************RISIKOKARTEN************************************

	// ALTE METHODE, KANN GELÖSCHT WERDEN
//	public int[] risikokartenTauschkombiVorhanden(Player aktiverPlayer) {
//		return logik.risikokartenTauschkombiVorhanden(aktiverPlayer);
//	}

	// get Gewinner kann nur geholt werden, wenn einer eine Mission erfï¿½llt hat
	// bzw
	// missionenCompletet True ist..

	public ArrayList<Land> getEigeneNachbarn(Land land) {
		return worldMg.getEigeneNachbarn(land);
	}

	public boolean isBenachbart(Land land1, Land land2) {
		return worldMg.isBenachbart(land1, land2);
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

	
	public boolean kannAngreifen(Player player) {
		return logik.kannAngreifen(player);
	}
	
	//TODO: exception behandeln

	public boolean attackLandGueltig(Land att) {
		try {
			return logik.attackLandGueltig(att);
		} catch (LandExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean defenseLandGueltig(Land att, Land def) {
		return logik.defenseLandGueltig(att, def);
	}

	public boolean moveFromLandGueltig(Land move) {
		return logik.moveFromLandGueltig(move);
	}
	
	public boolean moveToLandGueltig(Land from, Land to) {
		return logik.moveToLandGueltig(from, to);
	}
	
	public boolean moveUnitsGueltig(Land from, Land to, int units) {
		return logik.moveUnitsGueltig(from, to, units);
	}

	public ArrayList<Land> getAngriffsLaender(Player angreifer) {
		ArrayList<Land> moeglicheLaender = logik.getLaenderMitMehrAlsEinerEinheit(angreifer);
		ArrayList<Land> attackLaender = logik.getLaenderMitFeindlichenNachbarn(angreifer, moeglicheLaender);
		return attackLaender;
	}

	public ArrayList<Land> getFeindlicheNachbarn(Land attackLand) throws LandExistiertNichtException {
		ArrayList<Land> feindlicheLaender = logik.getFeindlicheNachbarn(attackLand);
		return feindlicheLaender;
	}

//	public ArrayList<Integer> attack (Land att, Land def, int attEinheiten, int defEinheiten) throws LaenderNichtBenachbartException, NichtGenugEinheitenException {

	public Attack attack(Land att, Land def, int attEinheiten, int defEinheiten)
			throws ZuWenigEinheitenNichtMoeglichExeption {
		return logik.attack(att, def, attEinheiten, defEinheiten);
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

	public boolean kannVerschieben(Player player) {
		return logik.kannVerschieben(player);
	}
	
	public void moveUnits(Land start, Land ziel, int menge)
			throws LandExistiertNichtException, ZuWenigEinheitenException, ZuWenigEinheitenNichtMoeglichExeption {
		logik.moveUnits(start, ziel, menge);
	}

	public void spielSpeichern(String datei) {
		FilePersistenceManager fileMg = new FilePersistenceManager();
		fileMg.speichern(game, datei);
	}

	public void spielLaden(String datei){
		FilePersistenceManager fileMg = new FilePersistenceManager();
		GameObject gameSpeicher = fileMg.laden(datei);
		if (gameSpeicher != null) {
			game.setAllePlayer(gameSpeicher.getAllePlayer());
			game.setSpielstand(gameSpeicher.getSpielstand());
			//Jeder geladene Spieler muss erst dem Playermanagement hinzugefügt werden
			for(int i = 0; i < game.getAllePlayer().size(); i++) {
				Player loadedPlayer = game.getAllePlayer().get(i);
				playerMg.addPlayer(loadedPlayer.getName(), loadedPlayer.getFarbe(), loadedPlayer.getNummer());
				//im anschluss werden die Länder entsprechend verteilt
				playerMg.getPlayers().get(i).addLaender(loadedPlayer.getBesitz());
				//und die Risikokarten
				for (Risikokarte karte: loadedPlayer.getEinheitenkarten()) {
					playerMg.getPlayers().get(i).setEinheitenkarte(karte);
				}
				//und für jedes Land werden die Einheiten neu gesetzt
				for(Land loadedLand: loadedPlayer.getBesitz()) {
					Land land = null;
					//TODO: das catchen an andere Stelle!
					try {
						land = worldMg.getLandById(loadedLand.getNummer());
					} catch (LandExistiertNichtException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						land.setEinheiten(loadedLand.getEinheiten());
					} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

//	'''''''''' PlayerManagement ''''''''''''''''
	public ArrayList<String> getFarbauswahl() {
		return playerMg.getFarbauswahl();
	}

	public String setFarbeAuswaehlen(String farbe) { // hier string
		return playerMg.menuFarbeAuswaehlen(farbe);
	}
	
	// Man muss einfach nur risiko.getColorArray().get(und hier die Spielernummer vom gewünschten spieler eintragen), damit die entsprechende Spielerfarbe erscheint.
	public ArrayList<Color> getColorArray() {
		return playerMg.getColorArray();
	}

	public void setColorArray(Color color) {
		playerMg.setColorArray(color);
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
	// TODO: @tobi nach jeder rund einbinden?? Spieler aus Array lï¿½schen
	public boolean isPlayerDead(Player play) {
		return play.isDead();
	}
}

package ris.local.domain;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.UngueltigeAnzahlEinheitenException;
import ris.common.exceptions.ZuWenigEinheitenException;
import ris.common.exceptions.ZuWenigEinheitenNichtMoeglichExeption;
import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.GameObject;
import ris.common.valueobjects.Kontinent;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Player;
import ris.common.valueobjects.Risikokarte;
import ris.common.valueobjects.Risikokarte.Symbol;
import ris.common.valueobjects.State;
import ris.common.valueobjects.Turn;
import ris.local.persistence.FilePersistenceManager;

public class Risiko implements RisikoInterface, Serializable {

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
		turn = new Turn(playerMg.getPlayers());
		logik = new Spiellogik(worldMg, playerMg, turn);
		RisikokartenManagement einheitenkartenMg = new RisikokartenManagement();
		einheitenkartenStapel = einheitenkartenMg.getEinheitenkarten();
//		turn.state = State.SETUNITS;
		game = new GameObject(playerMg.getPlayers(), turn);
	}

	public State getCurrentState() {
		return turn.getCurrentState();
	}

	public void setNextState() {
		System.out.println("risiko, wird state gesetzte? ");
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
	
	public void spielAufbau() {
		System.out.println("spiel wird aufgebaut!");
		logik.verteileEinheiten();
		logik.verteileMissionen();
		setzeAktivenPlayer();
	}

	// @to: Methode die sagt wer anfaengt
	public void whoBegins() {
		turn.setAktivenPlayer(logik.whoBegins());
		turn.setPlayerList(playerMg.getPlayers());
	}
	
	public void setSpielerAnzahl(int spielerAnzahl) {
		playerMg.setSpielerAnzahl(spielerAnzahl);
	}
	
	public int getSpielerAnzahl() {
		return playerMg.getSpielerAnzahl();
	}

	public void playerAnlegen(String name, String farbe, int nummer) throws SpielerNameExistiertBereitsException {
		playerMg.addPlayer(name, farbe, nummer);
		//Farbe wird dem Colorarray hinzugefügt
		setColorArray(farbe);
	}

	// TODO: diese methode kann wahrschienich weg, nochmal ueberpruefen!
	public void setzeAktivenPlayer() { //TODO: evtl 
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
		return worldMg.getLandById(zahl);
	}
	
	public Player getPlayerById(int zahl) {
		return playerMg.getPlayerById(zahl);
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

	public boolean rundeMissionComplete() {
		return logik.rundeMissionComplete(gibAktivenPlayer());
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

	// gibt zurueck, ob ein Player Risikokarten gegen Einheiten eintauschen kann
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

	// ALTE METHODE, KANN GELOESCHT WERDEN
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
	public int errechneVerfuegbareEinheiten() {
		int verfuegbareEinheiten = logik.errechneVerfuegbareEinheiten(turn.gibAktivenPlayer());
		return verfuegbareEinheiten;
	}
	// ----------------------------------------einheiten-------------------------------------------------

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Start^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	
	public boolean kannAngreifen() {
		return logik.kannAngreifen(gibAktivenPlayer());
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
			throws ZuWenigEinheitenNichtMoeglichExeption, ZuWenigEinheitenException {
		return logik.attack(att, def, attEinheiten, defEinheiten);
	}

	public ArrayList<Integer> diceDefense(int defUnit) throws UngueltigeAnzahlEinheitenException {
		return logik.diceDefense(defUnit);
	}

	public ArrayList<Integer> diceAttack(int attUnit) throws UngueltigeAnzahlEinheitenException {
		return logik.diceAttack(attUnit);
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Einheiten verschieben^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public boolean kannVerschieben(Player player) {
		return logik.kannVerschieben(player);
	}
	
	public void moveUnits(Land start, Land ziel, int menge) {
		try {
			logik.moveUnits(start, ziel, menge);
		} catch (ZuWenigEinheitenException | LandExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void spielSpeichern(String datei) {
		FilePersistenceManager fileMg = new FilePersistenceManager();
		fileMg.speichern(game, datei);
	}
	
	public String[] getSpielladeDateien(){
//		in verzeichnis werden die dateien als string gespeichert 
		String[] verzeichnis = new String[10];
		
//		dann werden die dateien ausgelesen
		//System.getProperty("file.separator") macht es möglich, mit unterschiedlichen Betriebssystemen den Pfad zu laden
		Path dir = Paths.get("files" + System.getProperty("file.separator"));
		DirectoryStream<Path> stream = null;
		try {
			stream = Files.newDirectoryStream(dir);
//			dann werden die dateien in den array geschrieben
			int i = 0;
		      for (Path entry: stream) {
		    	  verzeichnis[i] = entry.getFileName().toString();
		    	  i++;
		      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return verzeichnis;
	}

	public void spielLaden(String datei) throws SpielerNameExistiertBereitsException, ZuWenigEinheitenException {
		FilePersistenceManager fileMg = new FilePersistenceManager();
		GameObject gameSpeicher = fileMg.laden(datei);
		if (gameSpeicher != null) {
			game.setAllePlayer(gameSpeicher.getAllePlayer());
			turn = gameSpeicher.getSpielstand();
//			Jeder geladene Spieler muss erst dem Playermanagement hinzugefï¿½gt werden
			for(int i = 0; i < game.getAllePlayer().size(); i++) {
				Player loadedPlayer = game.getAllePlayer().get(i);
				playerMg.addPlayer(loadedPlayer.getName(), loadedPlayer.getFarbe(), loadedPlayer.getNummer());

				//im anschluss werden die Laender entsprechend verteilt
				playerMg.getPlayers().get(i).addLaender(loadedPlayer.getBesitz());
				//und die Risikokarten
				for (Risikokarte karte: loadedPlayer.getEinheitenkarten()) {
					playerMg.getPlayers().get(i).setEinheitenkarte(karte);
				}
				//und fuer jedes Land werden die Einheiten neu gesetzt
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
					} catch (ZuWenigEinheitenException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println("eigentlich fertig geladen");
		} else {
			System.out.println("datei wohl = null");
		}
	}
	
	public void setEinheiten(Land land, int units) {
		try {
			land.setEinheiten(units);
		} catch (ZuWenigEinheitenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	'''''''''' PlayerManagement ''''''''''''''''
	public ArrayList<String> getFarbauswahl() {
		return playerMg.getFarbauswahl();
	}

	public String setFarbeAuswaehlen(String farbe) { // hier string
		return playerMg.menuFarbeAuswaehlen(farbe);
	}
	
	// Man muss einfach nur risiko.getColorArray().get(und hier die Spielernummer vom gewï¿½nschten spieler eintragen), damit die entsprechende Spielerfarbe erscheint.
	public ArrayList<Color> getColorArray() {
		return playerMg.getColorArray();
	}

	public void setColorArray(String farbe) {
		playerMg.setColorArray(farbe);
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

	@Override
	public void allUpdate(String ereignis) {
		// TODO Auto-generated method stub
		
	}

}

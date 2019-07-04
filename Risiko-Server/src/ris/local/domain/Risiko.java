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
import ris.common.exceptions.LandInBesitzException;
import ris.common.exceptions.LandNichtInBesitzException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.UngueltigeAnzahlEinheitenException;
import ris.common.exceptions.UngueltigeAnzahlSpielerException;
import ris.common.exceptions.ZuVieleDateienException;
import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.GameObject;
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
	/*
	 * mit spielWurdeGeladen fragt jeder client bei der wahl von spielladen ab, ob
	 * bereits von einem anderen spieler ein spiel geladen wurde
	 */
	private boolean spielWurdeGeladen = false;
	private GameObject geladenesSpiel = null;
	private int spielerGeladen = 0;

	public Risiko() {
		worldMg = new WorldManagement();
		playerMg = new PlayerManagement();
		turn = new Turn(playerMg.getPlayers());
		logik = new Spiellogik(worldMg, playerMg, turn);
		game = new GameObject(playerMg.getPlayers(), turn);
		RisikokartenManagement einheitenkartenMg = new RisikokartenManagement();
		einheitenkartenStapel = einheitenkartenMg.getEinheitenkarten();
	}

	public void setSpielerAnzahl(int spielerAnzahl) throws UngueltigeAnzahlSpielerException {
		playerMg.setSpielerAnzahl(spielerAnzahl);
	}

	public int getSpielerAnzahl() {
		return playerMg.getSpielerAnzahl();
	}

	public void playerAnlegen(String name, String farbe, int nummer) throws SpielerNameExistiertBereitsException {
		playerMg.addPlayer(name, farbe, nummer);
		// Farbe wird dem Colorarray hinzugefügt
		setColorArray(farbe);
	}

	/*
	 * spielAufbau() verteilt zufaellig alle laender an die spieler und gibt jedem
	 * Spieler eine zufaellige Mission und setzt den aktiven Spieler
	 * 
	 * @see ris.common.interfaces.RisikoInterface#spielAufbau()
	 */
	public void spielAufbau() {
		logik.verteileEinheiten();
		logik.verteileMissionen();
		setzeAktivenPlayer();
	}

	/*
	 * Methode die ausrechnet wer anfaengt
	 */
	public void whoBegins() {
		turn.setAktivenPlayer(logik.whoBegins());
		turn.setPlayerList(playerMg.getPlayers());
	}

	public void setzeAktivenPlayer() { // TODO: evtl
		turn.setAktivenPlayer(logik.setzeStartSpieler());
	}

	public State getCurrentState() {
		return turn.getCurrentState();
	}

	public void setNextState() {
		turn.setNextState();
	}

	public void setNextPlayer() {
		turn.naechsteSpielrunde();
	}

	/*
	 * gibt an spieler zurueck ob risikokarten eingetauscht werden duerfen
	 * 
	 * @see ris.common.interfaces.RisikoInterface#getTauschZeit()
	 */
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

	public ArrayList<Land> getLaender() {
		return worldMg.getLaender();
	}

	public ArrayList<Land> getEigeneLaender(Player player) {
		return player.getBesitz();
	}

	public Land getLandById(int zahl) throws LandExistiertNichtException {
		return worldMg.getLandById(zahl);
	}

	public ArrayList<Player> getPlayerArray() {
		return playerMg.getPlayers();
	}

	/*
	 * gibt den spieler der dran ist, aus dem playerArray wieder
	 * 
	 * @see ris.common.interfaces.RisikoInterface#gibAktivenPlayer()
	 */
	public Player gibAktivenPlayer() {
		return turn.gibAktivenPlayer();
	}

	public int getAnzahlPlayer() {
		return playerMg.getAnzahlPlayer();
	}

	public Player getPlayerById(int zahl) {
		return playerMg.getPlayerById(zahl);
	}
	
	public void setEinheiten(Land land, int units) throws UngueltigeAnzahlEinheitenException {
		land.setEinheiten(units);
	}

//	******************************>Gewinnabfragen<**************************
	/*
	 * diese Methode ueberprueft ob ein Spieler seine Mission erfuellt hat
	 * 
	 * @see ris.common.interfaces.RisikoInterface#allMissionsComplete()
	 */
	public boolean allMissionsComplete() {
		return logik.allMissionsComplete();
	}

	/*
	 * ueberprueft ob @param Player seine Mission erfuellt hat
	 * 
	 * @see ris.common.interfaces.RisikoInterface#rundeMissionComplete()
	 */
	public boolean rundeMissionComplete() {
		return logik.rundeMissionComplete(gibAktivenPlayer());
	}

	public Player getGewinner() {
		return logik.getGewinner();
	}

//	****************************RISIKOKARTEN************************************

	/*
	 * fragt den player, ob er ein land eingenommen hat via boolean
	 * gutschriftEinheitenkarte und setzt diesen dann auf false falls true, zieht er
	 * automatisch eine Einheitenkarte vom Stapel und diese Karte wird vom Stapel
	 * geloescht
	 * 
	 * @see ris.common.interfaces.RisikoInterface#zieheEinheitenkarte()
	 */
	public boolean zieheEinheitenkarte() {
		Player aktiverPlayer = gibAktivenPlayer();
		if (aktiverPlayer.getGutschriftEinheitenkarte()) {
			Risikokarte neueKarte = einheitenkartenStapel.remove(0);
			aktiverPlayer.setEinheitenkarte(neueKarte);
			aktiverPlayer.setGutschriftEinheitenkarte(false);
			return true;
		}
		return false;
	}

	/*
	 * die Methode bekommt vom Client eine ArrayList<mit den ids der laender, welche
	 * auf den eingetauschten Risikokarten sind>
	 * 
	 * @see
	 * ris.common.interfaces.RisikoInterface#removeRisikoKarten(java.util.ArrayList)
	 */
	public void removeRisikoKarten(ArrayList<Integer> risikokartenWahl) {
		ArrayList<Land> kicked = new ArrayList<Land>();
		for (Integer landId : risikokartenWahl) {
			try {
				kicked.add(getLandById(landId));
			} catch (LandExistiertNichtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		gibAktivenPlayer().removeKarten(kicked);
	}

	// gibt zurueck, ob ein Player Risikokarten gegen Einheiten eintauschen kann
	public boolean changePossible(Player aktiverPlayer) {
		return aktiverPlayer.changePossible();
	}

	/*
	 * sobald ein Spieler 5 Karten gezogen hat, wird er gezwungen diese
	 * einzutauschen
	 */
	public boolean mussTauschen(Player aktiverPlayer) {
		return aktiverPlayer.mussTauschen();
	}

	/*
	 * wird um Fehler aufgrund der Socketverbindung auszuweichen, auf dem Client
	 * abgefragt Sicherer waere diese Methode auf dem Server in der Spiellogik zu
	 * benutzen
	 */
	public boolean isGueltigeTauschkombi(Symbol s1, Symbol s2, Symbol s3) {
		return logik.isGueltigeTauschkombi(s1, s2, s3);
	}

	public ArrayList<Risikokarte> getRisikoKarten() {
		return einheitenkartenStapel;
	};

//	****************************RISIKOKARTEN************************************

	/*
	 * gibt ein Array<Land> zurueck mit den Nachbern mit dem gleichen Besitzer wenn
	 * Null, beudeutet das er nur von Feinden umgeben ist
	 */
	public ArrayList<Land> getEigeneNachbarn(Land land) {
		return worldMg.getEigeneNachbarn(land);
	}

	public boolean isBenachbart(Land land1, Land land2) {
		return worldMg.isBenachbart(land1, land2);
	}

	/*
	 * Wurde nur in der CUI benutzts
	 */
	public ArrayList<Land> gibWeltAus() {
		Collections.sort(worldMg.getLaender());
		return worldMg.getLaender();
	}

	public int errechneVerfuegbareEinheiten() {
		int verfuegbareEinheiten = logik.errechneVerfuegbareEinheiten(turn.gibAktivenPlayer());
		return verfuegbareEinheiten;
	}

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Start^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	/*
	 * geht den Besitzt des aktiven Spielers durch und schaut ob ein Land angreifen
	 * kann
	 * 
	 * @see ris.common.interfaces.RisikoInterface#kannAngreifen()
	 */
	public boolean kannAngreifen() {
		return logik.kannAngreifen(gibAktivenPlayer());
	}

	/*
	 * wird benutzt um zu ueberpruefen ob das ausgewaehlte Land angreifen kann
	 * 
	 * @see ris.common.interfaces.RisikoInterface#attackLandGueltig(ris.common.
	 * valueobjects.Land)
	 */
	public boolean attackLandGueltig(Land att) {
		return logik.attackLandGueltig(att);
	}

	/*
	 * wird benutzt um zu ueberpruefen ob man das ausgewaehlte Land angreifen kann
	 * 
	 * @see ris.common.interfaces.RisikoInterface#attackLandGueltig(ris.common.
	 * valueobjects.Land)
	 */
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

	/*
	 * wird aufgerufen, wenn angreifer laender gewaehlt und einheiten eingegeben hat
	 * danach bekommt der verteidiger bescheid, dass er eine anzahl an units
	 * eingeben muss
	 * 
	 * @see
	 * ris.common.interfaces.RisikoInterface#attackStart(ris.common.valueobjects.
	 * Land, ris.common.valueobjects.Land, int)
	 */
	public void attackStart(Land attLand, Land defLand, int attUnits)
			throws LandNichtInBesitzException, LandInBesitzException {
		if (attLand.getBesitzer().getName().equals(gibAktivenPlayer().getName())) {
			if (!defLand.getBesitzer().getName().equals(gibAktivenPlayer().getName())) {
				logik.attackStart(attLand, defLand, attUnits);
			} else {
				throw new LandInBesitzException(defLand);
			}
		} else {
			throw new LandNichtInBesitzException(attLand);
		}
	}

	public Attack attackFinal(int defUnits) {
		return logik.attackFinal(defUnits);
	}

	public int getDefLandUnits() {
		return logik.getDefLandUnits();
	}

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Einheiten
	// verschieben^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public boolean kannVerschieben(Player player) {
		return logik.kannVerschieben(player);
	}

	public void moveUnits(Land start, Land ziel, int menge) {
		logik.moveUnits(start, ziel, menge);
	}

//	***************************************SPEICHERN UND LADEN***************************************************

	public void spielSpeichern(String datei) {
		FilePersistenceManager fileMg = new FilePersistenceManager();
		fileMg.speichern(game, datei);
	}

	public String[] getSpielladeDateien() {
//		in verzeichnis werden die dateien als string gespeichert 
		/*
		 * wir haben 10 Speicherplaetze in unserem Spiel wenn es mehr sind, muessen
		 * diese noch manuell herausgeloescht werden
		 */
		String[] verzeichnis = new String[10];

//		dann werden die dateien ausgelesen
		// System.getProperty("file.separator") macht es möglich, mit unterschiedlichen
		// Betriebssystemen den Pfad zu laden
		Path dir = Paths.get("files" + System.getProperty("file.separator"));
		DirectoryStream<Path> stream = null;
		try {
			stream = Files.newDirectoryStream(dir);
//			dann werden die dateien in den array geschrieben
			int i = 0;
			for (Path entry : stream) {
//				if(i == 10) {
//					throw new ZuVieleDateienException(11);
//				}
				verzeichnis[i] = entry.getFileName().toString();
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return verzeichnis;
	}

	public GameObject gameObjectLaden(String datei) {
		FilePersistenceManager fileMg = new FilePersistenceManager();
		GameObject gameSpeicher = fileMg.laden(datei);
//		sobald das spiel vom ersten client geladen wird, wird boolean und geladenesSpiel gesetzt;
		this.spielWurdeGeladen = true;
		this.geladenesSpiel = gameSpeicher;
		return gameSpeicher;
	}

	public void spielLaden(String datei) throws SpielerNameExistiertBereitsException, LandExistiertNichtException {
		if (geladenesSpiel != null) {
			game.setAllePlayer(geladenesSpiel.getAllePlayer());
			game.setSpielstand(turn);
			turn = geladenesSpiel.getSpielstand();

			for (int i = 0; i < game.getAllePlayer().size(); i++) {
//			    Jeder geladene Spieler muss erst dem Playermanagement hinzugefuegt werden
				Player loadedPlayer = game.getAllePlayer().get(i);
				playerMg.addPlayer(loadedPlayer.getName(), loadedPlayer.getFarbe(), loadedPlayer.getNummer());

//				im anschluss werden die Laender entsprechend verteilt
				playerMg.getPlayers().get(i).addLaender(loadedPlayer.getBesitz());
				playerMg.getPlayers().get(i).setMission(loadedPlayer.getMissionObject());

//				und die Risikokarten
				for (Risikokarte karte : loadedPlayer.getEinheitenkarten()) {
					playerMg.getPlayers().get(i).setEinheitenkarte(karte);
				}

//				und fuer jedes Land werden die Einheiten neu gesetzt
				for (Land loadedLand : loadedPlayer.getBesitz()) {
					Land land = null;
					land = worldMg.getLandById(loadedLand.getNummer());
					try {
						// einheiten - 1, da jedes land per default eine einheit besitzt
						land.setEinheiten(loadedLand.getEinheiten() - 1);
						land.setBesitzer(loadedPlayer);
					} catch (UngueltigeAnzahlEinheitenException e) {
						System.err.println("Fehler beim Laden.");
						e.printStackTrace();
					}
				}

//				die farbe von jedem player wird dem colorArray hinzugefuegt
				playerMg.setColorArray(loadedPlayer.getFarbe());
			}
		} else {
			System.err.println("Die geladene Datei ist leer");
		}
	}

//  gibt geladenes Spiel zurueck
	public GameObject getGeladenesSpiel() {
		return geladenesSpiel;
	}

	@Override
	public boolean spielWurdeGeladen() {
		return this.spielWurdeGeladen;
	}
	
	public void spielLadenTrue() {
		spielWurdeGeladen = true;
	}


	/*
	 * Methode wird aufgerufen, wenn sich ein Spieler beim Laden anmeldet, daher
	 * wird erst inkrementiert und der neue Wert zurueckgegeben
	 * 
	 * @see ris.common.interfaces.RisikoInterface#wieVieleSpielerImGame()
	 */
	public int wieVieleSpielerImGame() {
		return ++spielerGeladen;
	}

//	'''''''''' PlayerManagement ''''''''''''''''
	/*
	 * gibt alle verfuegbaren farben (String) fuer das anmelden im spiel 
	 * @see ris.common.interfaces.RisikoInterface#getFarbauswahl()
	 */
	public ArrayList<String> getFarbauswahl() {
		return playerMg.getFarbauswahl();
	}

	public void setFarbeAuswaehlen(String farbe) { 
		playerMg.menuFarbeAuswaehlen(farbe);
	}

	/*
	 * Gibt alle verwendeten Farben (RGB) der Spieler zurueck.
	 * Man muss einfach nur risiko.getColorArray().get(und hier die Spielernummer
	 * vom gewuenschten spieler eintragen), damit die entsprechende Spielerfarbe
	 * erscheint.
	 */
	public ArrayList<Color> getColorArray() {
		return playerMg.getColorArray();
	}

	public void setColorArray(String farbe) {
		playerMg.setColorArray(farbe);
	}


	// TODO: muss noch implementiert werden
	public boolean isPlayerDead(Player play) {
		return play.isDead();
	}

	
	/*
	 * ab hier Methoden aus RisikoInterface, die auf Client-Seite gebraucht werden
	 * Fehlermeldung, wenn diese nicht im Interface auftauchen
	 * 
	 * @see ris.common.interfaces.RisikoInterface#allUpdate(java.lang.String)
	 */
	@Override
	public void allUpdate(String ereignis) {
	
	}

	@Override
	public void aksForServerListenerNr() {
		// TODO Auto-generated method stub

	}

	@Override
	public void aktiveClientAskHowMany(int sListenerNr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pressBackButn(int sListenerNr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spielEintreitenBtn(int sListenerNr) {
		// TODO Auto-generated method stub

	}

	@Override
	public GameObject getGameDatei() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void spielerWurdeGeladen() {
		// TODO Auto-generated method stub

	}

}

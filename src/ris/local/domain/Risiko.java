package ris.local.domain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Player;

public class Risiko {

	private WorldManagement worldMg;
	private PlayerManagement playerMg;
	private Spiellogik logik;
	private Player player, gewinner;
	

	// Konstruktor

	public Risiko() {
		worldMg = new WorldManagement();
		playerMg = new PlayerManagement();
		logik = new Spiellogik(worldMg, playerMg);
		
	}

	public void spielAnlegen(int anzahl) {
		// ...
		// return gameObjekt;
	}
	
	
	//@to: Methode um Laender am Anfang zuf�llig zu verteilen;
	public void verteileEinheiten() {
		logik.verteileEinheiten();
	}
	
	public void verteileMissionen() {
		logik.verteileMissionen();
	}
	//@to: Methode die sagt wer anf�ngt ... #to generelle frage: die methoden werden hier einfach nur stumpf weitergeleitet, damit man von der cui drauf zugreifen kann. 
	// wei� ncht ob das richtig ist, in der bibliothek wirds �hnlich gemacht. #losch @annie: ich glaub das ist richtig so
	//beachte.. verteileEinheiten sollte vor dieser Methode implementiert werden.. ansonsten machts ja auch kein sinn
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
	public ArrayList<String> gibLaenderUndNummer(){
		return player.gibLaenderUndNummer();
	}

	public ArrayList<Player> getPlayerArray(){
		return playerMg.getPlayers();
	}
	public int getAnzahlPlayer() {
		return playerMg.getAnzahlPlayer();
	}
	
	public Land getLandById (int zahl) {
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
	public boolean missionenComplete() {
		for(Player play:playerMg.getPlayers()) {
			if(play.isMissionComplete(player)) {
				gewinner = player;
				return true;
			} 
			
		}
		return false;
	}
	
	//get Gewinner kann nur geholt werden, wenn einer eine Mission erf�llt hat bzw missionenCompletet True ist..
	public Player getGewinner() {
		return gewinner;
	}
	
	public ArrayList<Land> getEigeneNachbarn(Land land){
		return worldMg.getEigeneNachbarn(land);
	}
	
	public ArrayList<Land> getEinheitenVerschiebenVonLaender(Player player){
		ArrayList<Land> verschiebbareEinheitenLaender = logik.getLaenderMitMehrAlsEinerEinheit(player);
		return logik.getLaenderMitEigenenNachbarn(verschiebbareEinheitenLaender);
	}
	
	//WELT AUSGABE->
	public ArrayList<Land> gibWeltAus(){
		Collections.sort(worldMg.getLaender());
		return worldMg.getLaender();
	}
	
	public ArrayList<Kontinent> gibAlleKontinente(){
		return worldMg.getKontinente();
	}
	
	public ArrayList<Player> gibAllePlayer(){
		return playerMg.getPlayers();
	}
	//WELT AUSGABE <-
	
	//----------------------------------------einheiten-------------------------------------------------
	public int errechneVerfuegbareEinheiten(Player aktiverPlayer) {
		int verfuegbareEinheiten = logik.errechneVerfuegbareEinheiten(aktiverPlayer);
		return verfuegbareEinheiten;
	}
	//----------------------------------------einheiten-------------------------------------------------
	
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Start^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	public ArrayList<Land> getAngriffsLaender(Player angreifer){
		ArrayList<Land> moeglicheLaender = logik.getLaenderMitMehrAlsEinerEinheit(angreifer);
		ArrayList<Land> attackLaender = logik.getLaenderMitFeindlichenNachbarn(angreifer, moeglicheLaender);
		return attackLaender;
	} 
	
	public ArrayList<Land> getFeindlicheNachbarn (Land attackLand) {
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

	public ArrayList<Integer> attack (Land att, Land def, int attEinheiten, int defEinheiten) {
		ArrayList<Integer> ergebnis = logik.attack(att, def, attEinheiten, defEinheiten);
		return ergebnis;
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Einheiten verschieben^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	public void verschiebeEinheiten(Land start,Land ziel,int menge) {
		logik.moveUnits(start, ziel, menge);	
	}
	
//	'''''''''' PlayerManagement ''''''''''''''''
	public ArrayList<String> getFarbauswahl() {
		return playerMg.getFarbauswahl();
	}
	
	public String setFarbeAuswaehlen(String farbe) { //hier string
		return playerMg.menuFarbeAuswaehlen(farbe);
	
	}
	
	public boolean getRichtigeEingabe() {
		return playerMg.getRichtigeEingabe();
	}
	
	public static void main(String[] args) {
		Risiko test = new Risiko();
		test.PlayerAnlegen("a", "rot", 0);
		test.PlayerAnlegen("b", "gruen", 1);
//		test.verteileMissionen();
//		test.getPlayerArray().get(0).getMission();
		
	}
}

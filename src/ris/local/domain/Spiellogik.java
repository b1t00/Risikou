﻿
package ris.local.domain;

import ris.local.domain.PlayerManagement;
import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.UngueltigeAnzahlEinheitenException;
import ris.local.exception.ZuWenigEinheitenException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ris.local.valueobjects.Player;
import ris.local.valueobjects.MissionGegner;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Mission;

public class Spiellogik implements Serializable{

	PlayerManagement gamerVW;
	private WorldManagement worldMg;
	private PlayerManagement playerMg;
	private MissionsManagement missionsMg;
	private int spielrunden;
	private List<Player> playerList;
	private Player aktiverPlayer;

	public Spiellogik(WorldManagement worldMg, PlayerManagement playerMg) {
		this.worldMg = worldMg;
		this.playerMg = playerMg;
		spielrunden = 0;
		playerList = playerMg.getPlayers();
	}

	// ***********************************Spiel_Anfang********************************************

	// Methode um Alle "LaenderKarten" durchzumischen
//	public ArrayList<Land> shuffleLaender() {
//		return shuffle;
//	}

	// Methode um Laender am Anfang zuf�llig zu verteilen;
	public void verteileEinheiten() {
		ArrayList<Land> alleLaender = worldMg.getLaender();
		Collections.shuffle(alleLaender);

		int i = 0;
		while (i < alleLaender.size()) {
			for (Player playerVert : playerList) {
				if (i == alleLaender.size()) {
					break;
				} else {
					playerVert.addLand(alleLaender.get(i));
					i++;
				}
			}
		}
		teileLaenderBesitzerZu();
	}

	// methode die allen laender den entsprechenden Player zuteilt
	// wird denke ich nur ganz am anfang gebraucht..
//	wahrscheinlich benutzt man bei Laenderwechseln die setBesitzer methode vom Land
	public void teileLaenderBesitzerZu() {
		for (Player player : playerMg.getPlayers()) {
			for (Land land : player.getBesitz()) {
				land.setBesitzer(player);
			}
		}
	}

	// Methode die sagt wer anfaengt
	public Player whoBegins() {
		if (worldMg.getLaender().size() % playerList.size() != 0) { // abfrage ob alle laender aufgehen oder nicht.
			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getBesitz().size() > playerList.get(i + 1).getBesitz().size()) {
					return playerList.get(i + 1);
				}
			}
		}
		// wenn alles aufgeht f�ngt Player 1 an
		return playerList.get(0);
	}

	public void setzeStartSpieler() {
		aktiverPlayer = whoBegins();
	}

	public void verteileMissionen() {
		missionsMg = new MissionsManagement(playerMg, worldMg);

		ArrayList<Mission> missionsCopy = missionsMg.getMission();
		Collections.shuffle(missionsCopy);
		for (Player play : playerMg.getPlayers()) {
			Mission mission = missionsCopy.remove(0);
			if (mission instanceof MissionGegner && ((MissionGegner) mission).getGegner() == play) {
				// ops suicide, zur�ck tun und neue suchen
				missionsCopy.add(mission);
				mission = missionsCopy.remove(0);
			}
			play.setMission(mission);
		}

	}


	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SpielAnfang_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	// ----------------------------------------einheiten-------------------------------------------------
	public int errechneVerfuegbareEinheiten(Player aktiverPlayer) {
		int verfuegbareEinheiten = 0;
		int landBesitz = aktiverPlayer.getBesitz().size();
		verfuegbareEinheiten = (landBesitz / 3);
		if (verfuegbareEinheiten < 3) {
			verfuegbareEinheiten = 3;
		}
		ArrayList<Kontinent> kontinente = worldMg.getKontinente();
		for (Kontinent kontinent : kontinente) {
			if (kontinent.isOwnedByPlayer(aktiverPlayer)) {
				// kontinent braucht attribut: bonusEinheiten, entsprechend den bonuseinheiten
				// worldmg braucht methode getKontinente
				verfuegbareEinheiten += kontinent.getValue();
			}
		}
		return verfuegbareEinheiten;
	}
	// ----------------------------------------einheiten-------------------------------------------------

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Anfang^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	// Methode pr�ft, ob L�nder von einem Player mehr als eine Einheit hat
	public ArrayList<Land> getLaenderMitMehrAlsEinerEinheit(Player player) {
		ArrayList<Land> starkeLaender = new ArrayList<Land>();
		ArrayList<Land> besitzPlayer = player.getBesitz();
		for (Land land : besitzPlayer) {
			if (land.getEinheiten() - player.getBlock()[land.getNummer()] > 1) {
				starkeLaender.add(land);
			}
		}
		return starkeLaender;
	}

	// Methode pr�ft, ob das Land ein Nachbarland mit einem anderen Besitzer hat
	public ArrayList<Land> getLaenderMitFeindlichenNachbarn(Player angreifer, ArrayList<Land> laender) {
		ArrayList<Land> moeglicheAngreifer = new ArrayList<Land>();
		ArrayList<Land> besitzVonPlayer = laender;
		for (Land land : besitzVonPlayer) {
			for (int i = 0; i < worldMg.nachbarn[land.getNummer()].length; i++) {
				if (worldMg.nachbarn[land.getNummer()][i]) {
					if (!worldMg.getLaender().get(i).getBesitzer().equals(angreifer)) {
						moeglicheAngreifer.add(land);
						break;
					}
				}
			}
		}
		return moeglicheAngreifer;
	}

	public ArrayList<Land> getFeindlicheNachbarn(Land attackLand) {
		ArrayList<Land> feindlicheLaender = new ArrayList<Land>();
		for (int i = 0; i < worldMg.nachbarn[attackLand.getNummer()].length; i++) {
			if (worldMg.nachbarn[attackLand.getNummer()][i]) {
				if (!worldMg.getLaender().get(i).getBesitzer().equals(attackLand.getBesitzer())) {
					feindlicheLaender.add(worldMg.getLaender().get(i));
				}
			}
		}
		return feindlicheLaender;
	}

	public ArrayList<Land> getLaenderMitEigenenNachbarn(ArrayList<Land> eigeneLaender) {
		ArrayList<Land> hatNachbarn = new ArrayList<Land>();
		for (Land land : eigeneLaender) {
			for (int i = 0; i < worldMg.nachbarn[land.getNummer()].length; i++) {
				if (worldMg.nachbarn[land.getNummer()][i]) {
					if (worldMg.getLaender().get(i).getBesitzer().equals(land.getBesitzer())) {
						hatNachbarn.add(land);
						break;
					}
				}
			}
		}
		return hatNachbarn;
	}

//	public ArrayList<Integer> attack(Land att, Land def, int attEinheiten, int defEinheiten) {
//		// rollDice gibt eine Int-ArrayList zurueck, an erster Stelle die verlorenen
//		// Einheiten vom Angreifer, an zweiter vom Verteidiger
//		Player attacker = att.getBesitzer();
//
//		// setzt, wenn blockierte einheiten agreifen diese vorerst auf 0
//		int buBlock = attacker.getBlock()[att.getNummer()];
//		if (attacker.getBlock()[att.getNummer()] > 0) {
//			if (attacker.getBlock()[att.getNummer()] - attEinheiten >= 0) {
//				attacker.setBlock(attacker.getBlock(), att.getNummer(), -attEinheiten);
//			}
//		}



	public ArrayList<Integer> diceAttack(int attUnit) throws UngueltigeAnzahlEinheitenException{
		if(attUnit>3 | attUnit<=0) {
			throw new UngueltigeAnzahlEinheitenException("Mindestens 1 Einheit, maximal 3");
		}
		ArrayList<Integer> aList = new ArrayList<Integer>();
		for (int i = 0; i < attUnit; i++) {
			aList.add((int) (Math.random() * 6) + 1);
		}
		return aList;
	}


	public ArrayList<Integer> diceDefense(int defUnit)throws UngueltigeAnzahlEinheitenException{
		if(defUnit>2|defUnit<=0) {
			throw new UngueltigeAnzahlEinheitenException("Mindestens 1 Einheit,maximal 2");
		}
		ArrayList<Integer> dList = new ArrayList<Integer>();
		for (int i = 0; i < defUnit; i++) {
			dList.add((int) (Math.random() * 6) + 1);
		}
		return dList;
	}

	public ArrayList<Integer> diceResults(ArrayList<Integer> aList,ArrayList<Integer> defList){
		int lossDef=0;
		int lossAtt=0;
		Collections.sort(aList);
		Collections.sort(defList);
		Collections.reverse(aList);
		Collections.reverse(defList);

		if (aList.size() - defList.size() == 2) {
			aList.remove(2);
			aList.remove(1);

		}
		if (aList.size() - defList.size() == 1) {
			aList.remove(defList.size());
		}

		if (defList.size() - aList.size() == 2) {
			defList.remove(2);
			defList.remove(1);

		}
		if (defList.size() - aList.size() == 1) {
			defList.remove(aList.size());

		}
	
		if (defList.size() == 1) {
			if (aList.get(0) > defList.get(0))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
		}
		if (defList.size() == 2) {
			if (aList.get(0) > defList.get(0))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
			if (aList.get(1) > defList.get(1))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
		}
		if (defList.size() == 3) {
			if (aList.get(0) > defList.get(0))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
			if (aList.get(1) > defList.get(1))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
			if (aList.get(2) > defList.get(2))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
		}
		ArrayList<Integer> unitLoss = new ArrayList<Integer>();
		unitLoss.add(lossAtt);
		unitLoss.add(lossDef);
		return unitLoss;
	}
	

public ArrayList<Integer> attack (Land att, Land def,int attEinheiten, int defEinheiten,ArrayList<Integer> aList,ArrayList<Integer> dList) {
		//rollDice gibt eine Int-ArrayList zurueck, an erster Stelle die verlorenen Einheiten vom Angreifer, an zweiter vom Verteidiger
		Player attacker= att.getBesitzer();
		
		//setzt, wenn blockierte einheiten agreifen diese vorerst auf 0
		int buBlock= attacker.getBlock()[att.getNummer()];
		if(attacker.getBlock()[att.getNummer()]>0) {
			if(attacker.getBlock()[att.getNummer()]-attEinheiten>=0) {
				attacker.setBlock(attacker.getBlock(),att.getNummer(),-attEinheiten);

			}
			if (attacker.getBlock()[att.getNummer()] - attEinheiten < 0) {
				attacker.setBlock(attacker.getBlock(), att.getNummer(), -attacker.getBlock()[att.getNummer()]);
			}
		}

//
//		// ergebnis ist ein Array, an 1. Stelle die verlorenen attack-Einheiten, an 2.
//		// die verlorenen defense-Einheiten
//		ArrayList<Integer> ergebnis = rollDice(defEinheiten, attEinheiten);
		
		//ergebnis ist ein Array, an 1. Stelle die verlorenen attack-Einheiten, an 2. die verlorenen defense-Einheiten
		// #TODO: nochmal checken ob nichts doppelt
		ArrayList<Integer> ergebnis = diceResults(aList, dList);
		att.setEinheiten(ergebnis.get(0));
		def.setEinheiten(ergebnis.get(1));

		if (def.getEinheiten() > 0) {
			if (buBlock + ergebnis.get(0) >= 0) {
				int block = buBlock + ergebnis.get(0);
				attacker.setBlock(attacker.getBlock(), att.getNummer(), block);
			} else {
				attacker.setBlock(attacker.getBlock(), att.getNummer(), 0);
			}
		}

		// wenn die Einheiten auf def jetzt bei 0 sind, werden die Angriffs-Einheiten
		// verschoben
		if (def.getEinheiten() == 0) {
			def.setEinheiten(attEinheiten + ergebnis.get(0));
			att.setEinheiten(-(attEinheiten + ergebnis.get(0)));
			Player loser = def.getBesitzer();
			loser.setBesitz(def);
			Player winner = att.getBesitzer();
			winner.setBesitz(def);
			def.setBesitzer(winner);

			// setzt bei erobertem Land die beteiligten Einheiten auf Block
			winner.setBlock(winner.getBlock(), def.getNummer(), def.getEinheiten());
		}
		return ergebnis;
	}


	public ArrayList<Integer> rollDice(int attUnits, int defUnits) throws UngueltigeAnzahlEinheitenException {
		int lossDef = 0;
		int lossAtt = 0;
		ArrayList<Integer> aList = new ArrayList<Integer>();
		ArrayList<Integer> defList = new ArrayList<Integer>();
		for (int i = 0; i < attUnits; i++) {
			aList.add((int) (Math.random() * 6) + 1);
//Random r = new Random(System.currentTimeMillis());
//int zahl = r.nextInt(6) + 1;
		}
		for (int j = 0; j < defUnits; j++) {
			defList.add((int) (Math.random() * 6) + 1);

		}

		Collections.sort(aList);
		Collections.sort(defList);
		Collections.reverse(aList);
		Collections.reverse(defList);

		if (aList.size() - defList.size() == 2) {
			aList.remove(2);
			aList.remove(1);

		}
		if (aList.size() - defList.size() == 1) {
			aList.remove(defList.size());
		}

		if (defList.size() - aList.size() == 2) {
			defList.remove(2);
			defList.remove(1);

		}
		if (defList.size() - aList.size() == 1) {
			defList.remove(aList.size());

		}

		if (defList.size() == 1) {
			if (aList.get(0) > defList.get(0))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
		}
		if (defList.size() == 2) {
			if (aList.get(0) > defList.get(0))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
			if (aList.get(1) > defList.get(1))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
		}
		if (defList.size() == 3) {
			if (aList.get(0) > defList.get(0))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
			if (aList.get(1) > defList.get(1))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
			if (aList.get(2) > defList.get(2))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
		}
		ArrayList<Integer> unitLoss = new ArrayList<Integer>();
		unitLoss.add(lossAtt);
		unitLoss.add(lossDef);
		return unitLoss;
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	// ***************************************->Runden<-**************************************

	public Player gibAktivenPlayer() {
		return aktiverPlayer;
	}

	// @tobi wird glaube nirgendwo benutzt.. kann man aber evtl Extrasachen mit
	// machen
	public int getSpielrunden() {
		return spielrunden;
	}

	// evtl andere bennenung als set
	public void naechsteSpielrunde() {
		this.spielrunden++;
		aktiverPlayer = playerList.get((aktiverPlayer.getNummer() + 1) % playerList.size());
	}
//	********************************** Angriffslogik **********************************

	boolean angriffMoeglich(Land def, Land att, int countUnits) {
		if (worldMg.nachbarn[def.getNummer()][att.getNummer()] == true && att.getEinheiten() - countUnits > 0) {
			return true;
		} else {
			return false;
		}
	}

//	public ArrayList<Integer> verteileEinheiten(){}

//	public void angriffAuswerten(ArrayList<Integer> dice, Land def, Land att) {
//		Player attacker = isOwner(att);
//		int defNew = dice.get(0);
//		if (def.getEinheiten() + dice.get(0) > 0) {
//			def.setEinheiten(defNew);
//		} else {
//			def.setFarbe(isOwner(def).getFarbe());
//		}
//		att.setEinheiten(dice.get(1));
//	}
//	public Player isOwner(Land attacker) {
//		for (Player p : playerMg.getPlayers()) {
//			for (Land l : p.getBesitz()) {
//				if (l.getNummer() == attacker.getNummer()) {
//					return p;
//				}
//			}
//		}
//		return null;
//	}

//	public void moveUnits() { // TODO
	public boolean movePossible(Land start, Land ziel, int menge) {
		boolean einheiten = true;
		if (start.getEinheiten() - menge < 1) {
			einheiten = false;
		}
//		Player x = isOwner(start);
//		ArrayList<Land> z= x.getBesitz();
		ArrayList<Land> connected;
		boolean nachbar = false;
		if (worldMg.isBenachbart(start, ziel)) {
			nachbar = true;
		}
		return (nachbar && einheiten);
	}
	
	public void moveUnits(Land start,Land ziel, int menge) throws ZuWenigEinheitenException, LandExistiertNichtException {
		if ((start.getEinheiten() - menge) < 1) {
			throw new ZuWenigEinheitenException("Zu wenige Einheiten");
		}
		if(!worldMg.getLaender().contains(start)) {
			throw new LandExistiertNichtException(start + " existiert nicht.");
		}

		start.setEinheiten(-menge);
		ziel.setEinheiten(menge);
//		if(movePossible(start,ziel,menge)&&start.getBesitzer()==gibAktivenPlayer()
//				&&ziel.getBesitzer() == gibAktivenPlayer()) {
//			start.setEinheiten(-menge);
//			ziel.setEinheiten(menge);
//		}
	}

	public int[] initBlockedUnits(int[] uBlock) {
		for (int i = 0; i < uBlock.length; i++) {
			uBlock[i] = 0;
		}
		return uBlock;
	}

	public int unitsAvailable(Land l) {
		return l.getEinheiten();
	}

}

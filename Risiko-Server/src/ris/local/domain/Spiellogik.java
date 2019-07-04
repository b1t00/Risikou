
package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.UngueltigeAnzahlEinheitenException;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.Kontinent;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Mission;
import ris.common.valueobjects.MissionGegner;
import ris.common.valueobjects.Player;
import ris.common.valueobjects.Risikokarte.Symbol;
import ris.common.valueobjects.Turn;
public class Spiellogik implements Serializable {

	PlayerManagement gamerVW;
	private WorldManagement worldMg;
	private PlayerManagement playerMg;
	private MissionsManagement missionsMg;
	private Turn turn;
	private List<Player> playerList;
	private Player aktiverPlayer, gewinner;
	private Attack attackObjekt;

	public Spiellogik(WorldManagement worldMg, PlayerManagement playerMg, Turn turn) {
		this.worldMg = worldMg;
		this.playerMg = playerMg;
		this.turn = turn;
		playerList = playerMg.getPlayers();
	}

	// ***********************************Spiel_Anfang********************************************

//  Methode um Laender am Anfang zufaellig zu verteilen;
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

//	methode die allen laender den entsprechenden Player zuteilt
	public void teileLaenderBesitzerZu() {
		for (Player player : playerMg.getPlayers()) {
			for (Land land : player.getBesitz()) {
				land.setBesitzer(player);
			}
		}
	}

	public Player setzeStartSpieler() {
		return whoBegins();
	}
//	Methode die sagt wer anfaengt
	public Player whoBegins() {
//		abfrage ob alle laender aufgehen oder nicht.
		if (worldMg.getLaender().size() % playerList.size() != 0) { 
			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getBesitz().size() > playerList.get(i + 1).getBesitz().size()) {
					return playerList.get(i + 1);
				}
			}
		}
//		wenn alles aufgeht faengt Player 1 an
		return playerList.get(0);
	}

//**********************************>MISSIONS-SACHEN<************************************

	public void verteileMissionen() {
		missionsMg = new MissionsManagement(playerMg, worldMg);
		ArrayList<Mission> missionsAr = missionsMg.getMission();
		Collections.shuffle(missionsAr);
		for (Player player : playerMg.getPlayers()) {
			Mission mission = missionsAr.remove(0);
			if (mission instanceof MissionGegner && ((MissionGegner) mission).getGegner() == player) {
//				ops suicide, zurueck tun und neue suchen
				missionsAr.add(mission);
				mission = missionsAr.remove(0);
			}
			player.setMission(mission);
		}

	}

//	ueberprueft ob einer der spieler gewonnen hat
	public boolean allMissionsComplete() {
		for (Player play : playerMg.getPlayers()) {
			if (play.isMissionComplete(play)) {
//				setzt den Gewinner auf die Variable, damit diese Methode nur einmal aufgerufen werden muss
				gewinner = play;
				return true;
			}
		}
		return false;
	}

//	ueberprueft, ob der aktive player gewonnen hat
	public boolean rundeMissionComplete(Player play) {
		if (play.isMissionComplete(play)) {
			gewinner = play;
			return true;
		} else {
			return false;
		}
	}

	public Player getGewinner() {
		return gewinner;
	}

	/*
	 * Methode die zu beginn jedes zuges aufgerufen wird und ermittelt, wie viele einheiten gesetzt werden koennen
	 */
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

//  die Methode bekommt drei Symbole und gibt zurück, ob die Kombi gueltig ist
	public boolean isGueltigeTauschkombi(Symbol symbol1, Symbol symbol2, Symbol symbol3) {
		if (symbol1 == symbol2 && symbol2 == symbol3) {
			return true;
		} else if (symbol1 != symbol2 && symbol2 != symbol3 && symbol1 != symbol3) {
			return true;
		} else
			return false;
	}


// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Anfang^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	/*
	 * schaut, ob der besitzer des jeweiligen nachbars identisch ist mit dem eigenen besitzer
	 * wenn ja, dann ist es kein feindliches land
	 */
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

	public ArrayList<Land> getEigeneNachbarn(Land moveLand) throws LandExistiertNichtException {
		if (!worldMg.getLaender().contains(moveLand)) {
			throw new LandExistiertNichtException(moveLand);
		}
		ArrayList<Land> eigeneNachbarn = new ArrayList<Land>();
		for (int i = 0; i < worldMg.nachbarn[moveLand.getNummer()].length; i++) {
			if (worldMg.nachbarn[moveLand.getNummer()][i]) {
				if (worldMg.getLaender().get(i).getBesitzer().equals(moveLand.getBesitzer())) {
					eigeneNachbarn.add(worldMg.getLaender().get(i));
				}
			}
		}
		return eigeneNachbarn;
	}

	//TODO: brauchen wir nciht?
//	public ArrayList<Land> getLaenderMitEigenenNachbarn(ArrayList<Land> eigeneLaender) {
//		ArrayList<Land> hatNachbarn = new ArrayList<Land>();
//		for (Land land : eigeneLaender) {
//			for (int i = 0; i < worldMg.nachbarn[land.getNummer()].length; i++) {
//				if (worldMg.nachbarn[land.getNummer()][i]) {
//					if (worldMg.getLaender().get(i).getBesitzer().equals(land.getBesitzer())) {
//						hatNachbarn.add(land);
//						break;
//					}
//				}
//			}
//		}
//		return hatNachbarn;
//	}

	// gibt zurueck, ob von einem Land Einheiten verschoben werden können
	public boolean moveFromLandGueltig(Land move) {
		try {
			if (move.getBesitzer().equals(turn.gibAktivenPlayer()) && move.getEinheiten() > 1
					&& getEigeneNachbarn(move).size() > 0) {
				return true;
			}
		} catch (LandExistiertNichtException e) {
			System.out.println("Land existiert nicht");
			e.printStackTrace();
		}
		return false;
	}

	public boolean moveToLandGueltig(Land from, Land to) {
		if (from.getBesitzer().equals(to.getBesitzer()) && worldMg.isBenachbart(from, to)) {
			return true;
		}
		return false;
	}

	// ueberprueft, ob ein ausgewaehltes Land angreifen kann (Besitzer =
	// aktiverPlayer, hat feindliche Nachbarn und mehr als 1Einheit)
	public boolean attackLandGueltig(Land att) {
		if (turn.gibAktivenPlayer().equals(att.getBesitzer()) && att.getEinheiten() > 1
				&& getFeindlicheNachbarn(att).size() > 0) {
			return true;
		}
		return false;
	}

	public boolean defenseLandGueltig(Land att, Land def) {
		if (!turn.gibAktivenPlayer().equals(def.getBesitzer()) && worldMg.isBenachbart(att, def)) {
			return true;
		}
		return false;
	}

//					**********************ENDE Angriff-Abfragen******************************

//	public ArrayList<Integer> attack(Land att, Land def, int attEinheiten, int defEinheiten) {
//		// rollDice gibt eine Int-ArrayList zurueck, an erster Stelle die verlorenen
//		// Einheiten vom Angreifer, an zweiter vom Verteidiger
//		Player attacker = att.getBesitzer();
	public void attackStart(Land attLand, Land defLand, int attUnits) {
		attackObjekt = new Attack(attLand, defLand, attUnits);
	}

	public int getDefLandUnits() {
		return attackObjekt.getDefLand().getEinheiten();
	}

	public Attack attackFinal(int defUnits) {
		attackObjekt.setAnzahlDefUnits(defUnits);

		Player attacker = attackObjekt.getAttacker();
		Player defender = attackObjekt.getDefender();
		Land attLand = attackObjekt.getAttLand();
		Land defLand = attackObjekt.getDefLand();
		int anzahlAttUnits = attackObjekt.getAnzahlAttUnits();

		/*
		 * buBlock speichter die Anzahl der blockierten Einheiten zu jedem Land
		 * (blockiert sind Einheiten, wenn sie schon einmal angegriffen haben) vor der
		 * Auswertung des Kampfes, muss die Anzahl der jetzt angreifenden Einheiten
		 * abgezogen werden, damit bei Verlust oder Verschieben der Einheiten und einem
		 * Verbleib von weniger Einheiten als im buBlock vermerkt, der buBlock fuer das
		 * entsprechende Land automatisch "geleert" ist.
		 */
		int buBlock = attacker.getBlock()[attLand.getNummer()];
		if (buBlock > 0) {
			if (buBlock - anzahlAttUnits >= 0) {
				attacker.setBlock(attLand.getNummer(), -anzahlAttUnits);
			}
			if (buBlock - anzahlAttUnits < 0) {
				attacker.setBlock(attLand.getNummer(), -attacker.getBlock()[attLand.getNummer()]);
			}
		}

		// Methode diceAttack und diceDefense geben pro gesetzter Unit einen zufaelligen
		// Wert zwischen 1 und 6 in einer ArrayList zurueck
		ArrayList<Integer> attList = null;
		ArrayList<Integer> defList = null;
		try {
			attList = diceAttack(anzahlAttUnits);
			attackObjekt.setAttUnits(attList);
			defList = diceDefense(defUnits);
			attackObjekt.setDefUnits(defList);
		} catch (UngueltigeAnzahlEinheitenException e) {
			e.printStackTrace();
		}

		// result ist ein Array[2]: an 1. Stelle die verlorenen attack-Einheiten, an 2.
		// die verlorenen defense-Einheiten (Werte sind negativ)
		ArrayList<Integer> result = null;
		result = diceResults(attList, defList);
		attackObjekt.setResult(result);

		// dem attack land werden die einheiten abgezogen, die einheiten vom defland
		// weiter unten, falls alle einheiten verloren gehen und diese somit 0 werden
		// wuerden
		try {
			attLand.setEinheiten(result.get(0));
		} catch (UngueltigeAnzahlEinheitenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * wurde das Land nicht erobert, wird der alte Wert an Blockierten Einheiten
		 * minus die verlorenen Einheiten wieder in das buBlock-Array geschrieben
		 */
		if (defLand.getEinheiten() + result.get(1) > 0) {
			try {
				defLand.setEinheiten(result.get(1));
			} catch (UngueltigeAnzahlEinheitenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (buBlock + result.get(0) >= 0) {
				int block = buBlock + result.get(0);
				attacker.setBlock(attLand.getNummer(), block);
			}
			// wer weniger einheiten verloren hat, gewinnt den kampf
			if (result.get(0) > result.get(1)) {
				attackObjekt.setWinner(attacker);
				attackObjekt.setLoser(defender);
			} else {
				attackObjekt.setWinner(defender);
				attackObjekt.setLoser(attacker);
			}
		}

		// wenn das Land erobert wurde (def-einheiten sind auf 0), werden die
		// Angriffs-Einheiten verschoben
		if (defLand.getEinheiten() + result.get(1) == 0) {
			// winUnits sind die Einheiten, die automatisch in das eroberte Land ziehen
			// (angreifende Einheiten - verlorene Attack-Einheiten)
			int winUnits = anzahlAttUnits + result.get(0);
			try {
				// erst werden einheiten des angreifers hinzuaddiert
				defLand.setEinheiten(winUnits);
				// dann einheiten des verteidigers abezogen (diese reihenfolge, damit die anzahl
				// der einheiten nicht bei 0 landet)
				defLand.setEinheiten(result.get(1));
				attLand.setEinheiten(-winUnits);
			} catch (UngueltigeAnzahlEinheitenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			attackObjekt.setLoser(defender);
			attackObjekt.setWinner(attacker);

			// das Land wird beim verlierer geloescht und dem Sieger hinzugefuegt
			defender.setBesitz(defLand);
			attacker.setBesitz(defLand);
			// der Besitzer des eroberten Landes wird aktualisiert
			defLand.setBesitzer(attacker);

			// setzt bei erobertem Land die beteiligten Einheiten auf Block
			attacker.setBlock(defLand.getNummer(), defLand.getEinheiten());

			// setzt beim gewinner den gutschriftEinheitenkarte auf true, damit er diese am
			// ende des Zuges Risikokarte ziehen kann
			attacker.setGutschriftEinheitenkarte(true);
		}
		return attackObjekt;
	}

	// ALTE ATTACK METHODE - KANN GELÖSCHT WERDEN, WENN NICHT MEHR BENÖTIGT
//	public ArrayList<Integer> attack(Land att, Land def, int attEinheiten, int defEinheiten, ArrayList<Integer> aList,
//			ArrayList<Integer> dList) throws ZuWenigEinheitenNichtMoeglichExeption {
//		// rollDice gibt eine Int-ArrayList zurueck, an erster Stelle die verlorenen
//		// Einheiten vom Angreifer, an zweiter vom Verteidiger
//		Player attacker = att.getBesitzer();
//
//		// setzt, wenn blockierte einheiten agreifen diese vorerst auf 0
//		int buBlock = attacker.getBlock()[att.getNummer()];
//		if (attacker.getBlock()[att.getNummer()] > 0) {
//			if (attacker.getBlock()[att.getNummer()] - attEinheiten >= 0) {
//				attacker.setBlock(attacker.getBlock(), att.getNummer(), -attEinheiten);
//
//
//			}
//			if (attacker.getBlock()[att.getNummer()] - attEinheiten < 0) {
//				attacker.setBlock(attacker.getBlock(), att.getNummer(), -attacker.getBlock()[att.getNummer()]);
//			}
//		}
//
//
//		// ergebnis ist ein Array, an 1. Stelle die verlorenen attack-Einheiten, an 2.
//		// die verlorenen defense-Einheiten
////		ArrayList<Integer> ergebnis = rollDice(defEinheiten, attEinheiten);
//		// #TODO: nochmal checken ob nichts doppelt
//		ArrayList<Integer> ergebnis= null;
//		try{
//			ergebnis= diceResults(aList, dList);
//			}
//		catch(UngueltigeAnzahlEinheitenException e) {
//			e.printStackTrace();
//		}
//		att.setEinheiten(ergebnis.get(0));
//		def.setEinheiten(ergebnis.get(1));
//
//		if (def.getEinheiten() > 0) {
//			if (buBlock + ergebnis.get(0) >= 0) {
//				int block = buBlock + ergebnis.get(0);
//				attacker.setBlock(attacker.getBlock(), att.getNummer(), block);
//			} else {
//				attacker.setBlock(attacker.getBlock(), att.getNummer(), 0);
//			}
//		}
//
//		// wenn die Einheiten auf def jetzt bei 0 sind, werden die Angriffs-Einheiten
//		// verschoben
//		if (def.getEinheiten() == 0) {
//			def.setEinheiten(attEinheiten + ergebnis.get(0));
//			att.setEinheiten(-(attEinheiten + ergebnis.get(0)));
//			Player loser = def.getBesitzer();
//			loser.setBesitz(def);
//			Player winner = att.getBesitzer();
//			winner.setBesitz(def);
//			def.setBesitzer(winner);
//
//			// setzt bei erobertem Land die beteiligten Einheiten auf Block
//			winner.setBlock(winner.getBlock(), def.getNummer(), def.getEinheiten());
//
//			// setzt beim gewinner den gutschriftEinheitenkarte auf true, damit er diese am
//			// ende des Zuges ziehen kann
//			winner.setGutschriftEinheitenkarte(true);
//		}
//		return ergebnis;
//	}
	// BIS HIERHIN LÖSCHEN!

	// DICEDICEDICE

	public ArrayList<Integer> diceAttack(int attUnit) throws UngueltigeAnzahlEinheitenException {
		if (attUnit > 3 | attUnit <= 0) {
			throw new UngueltigeAnzahlEinheitenException(1, 3);
		}
		ArrayList<Integer> aList = new ArrayList<Integer>();
		for (int i = 0; i < attUnit; i++) {
			aList.add((int) (Math.random() * 6) + 1);
		}
		return aList;
	}

	public ArrayList<Integer> diceDefense(int defUnit) throws UngueltigeAnzahlEinheitenException {
		if (defUnit > 2 | defUnit <= 0) {
			throw new UngueltigeAnzahlEinheitenException(1, 2);
		}
		ArrayList<Integer> dList = new ArrayList<Integer>();
		for (int i = 0; i < defUnit; i++) {
			dList.add((int) (Math.random() * 6) + 1);
		}

		return dList;
	}

	public ArrayList<Integer> diceResults(ArrayList<Integer> aList, ArrayList<Integer> defList) {
		int lossDef = 0;
		int lossAtt = 0;
		Collections.sort(aList);
		Collections.sort(defList);
		Collections.reverse(aList);
		Collections.reverse(defList);

		// die anzahl der einheiten von att und def wird angeglichen, indem die
		// niedrigsten Augen von def/att gelöscht werden
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

		// die Augenanzahlen werden verglichen
		// bei insgesamt einem Würfel pro Person
		if (defList.size() == 1) {
			if (aList.get(0) > defList.get(0))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
		}

		// bei insgesamt 2 Wuerfeln pro Person
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

		// bei insgesamt 3 Wuerfeln pro Person
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

		// ergebnis-Array wird zurückgegeben, 1.Stelle = verlorene Einheiten att,
		// 2.Stelle = verlorene Einheiten def
		ArrayList<Integer> unitLoss = new ArrayList<Integer>();
		unitLoss.add(lossAtt);
		unitLoss.add(lossDef);
		return unitLoss;
	}

	// END DICEDICEDICE

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	// ***************************************->Runden<-**************************************
//ist jetzt komplett im Turn, kann GELÖSCHT WERDEN, wenn nicht mehr benötigt!
//	public Player gibAktivenPlayer() {
//		return aktiverPlayer;
//	}
//
//	// @tobi wird glaube nirgendwo benutzt.. kann man aber evtl Extrasachen mit
//	// machen
//	public int getSpielrunden() {
//		return spielrunden;
//	}
//
//	// evtl andere bennenung als set
//	public void naechsteSpielrunde() {
//		this.spielrunden++;
//		aktiverPlayer = playerList.get((aktiverPlayer.getNummer() + 1) % playerList.size());
//	}
//	********************************** Angriffslogik **********************************

	/*
	 * Angriff ist möglich, wenn attacker und defender-Länder benachbart sind, der
	 * attacker mehr als eine Einheit auf seinem Land hat und ihm nicht beide Länder
	 * gehören
	 */
	public boolean angriffMoeglich(Land def, Land att, int countUnits) {
		if (worldMg.nachbarn[def.getNummer()][att.getNummer()] == true && att.getEinheiten() - countUnits > 0
				&& !(def.getBesitzer().equals(att.getBesitzer()))) {
			return true;
		} else {
			return false;
		}
	}

	// wird aufgerufen, bevor der Spieler in die Attack-Phase kommt
	public boolean kannAngreifen(Player player) {
		for (Land land : player.getBesitz()) {
			if (attackLandGueltig(land)) {
				return true;
			}
		}
		return false;
	}

	public boolean moveUnitsGueltig(Land from, Land to, int units) {
		if (units > 0) {
//			fuer die verfuegbaren Einheiten muessen die blockierten Einheiten abgezogen werden, die allerdings im Spieler stehen
			int verfuegbareEinheiten = (from.getEinheiten() - from.getBesitzer().getBlock()[from.getNummer()]);
			if ((verfuegbareEinheiten - units) > 0 && worldMg.isBenachbart(from, to)) {
				return true;
			}
		}
		return false;
	}

	public boolean kannVerschieben(Player player) {
		for (Land land : player.getBesitz()) {
			if (land.getEinheiten() > 1 && worldMg.getEigeneNachbarn(land).size() > 0) {
				return true;
			}
		}
		return false;
	}

	public void moveUnits(Land start, Land ziel, int menge) {
		try {
			start.setEinheiten(-menge);
			ziel.setEinheiten(menge);
		} catch (UngueltigeAnzahlEinheitenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

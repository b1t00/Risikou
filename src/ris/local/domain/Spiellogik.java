
package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.UngueltigeAnzahlEinheitenException;
import ris.local.exception.ZuWenigEinheitenException;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;
import ris.local.valueobjects.Attack;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Mission;
import ris.local.valueobjects.MissionGegner;
import ris.local.valueobjects.Player;
import ris.local.valueobjects.Risikokarte.Symbol;
import ris.local.valueobjects.Turn;

public class Spiellogik implements Serializable {

	PlayerManagement gamerVW;
	private WorldManagement worldMg;
	private PlayerManagement playerMg;
	private MissionsManagement missionsMg;
	private Turn turn;
//	private int spielrunden;
	private List<Player> playerList;
	private Player aktiverPlayer, gewinner;

	public Spiellogik(WorldManagement worldMg, PlayerManagement playerMg, Turn turn) {
		this.worldMg = worldMg;
		this.playerMg = playerMg;
		this.turn = turn;
//		spielrunden = 0;
		playerList = playerMg.getPlayers();
	}

	// ***********************************Spiel_Anfang********************************************

	// Methode um Alle "LaenderKarten" durchzumischen
//	public ArrayList<Land> shuffleLaender() {
//		return shuffle;
//	}

	// Methode um Laender am Anfang zuf�llig zu verteilen;
	public void verteileEinheiten() {
		System.out.println("werde ich ausgeführt?");
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
				System.out.println("hio" + player.getName());
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

	public Player setzeStartSpieler() {
		return whoBegins();
	}
//**********************************>MISSIONS-SACHEN<************************************

	public void verteileMissionen() {
		missionsMg = new MissionsManagement(playerMg, worldMg);

		ArrayList<Mission> missionsAr = missionsMg.getMission();
		Collections.shuffle(missionsAr);
		for (Player player : playerMg.getPlayers()) {
			Mission mission = missionsAr.remove(0);
			if (mission instanceof MissionGegner && ((MissionGegner) mission).getGegner() == player) {
				// ops suicide, zurueck tun und neue suchen
				missionsAr.add(mission);
				mission = missionsAr.remove(0);
			}
			player.setMission(mission);
		}
		
	}

	public boolean allMissionsComplete() {
		for (Player play : playerMg.getPlayers()) {
			if (play.isMissionComplete(play)) {
				gewinner = play;
				return true;
			}
		}
		return false;
	}
	
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
	
	// ----------------------------------------einheiten-------------------------------------------------
//	Diese Methode befindet sich jetzt in der Player-Klasse
//	public boolean changePossible(Player aktiverPlayer) {
//		int[] symbolAnzahlArray = aktiverPlayer.risikokartenKombi();
//		boolean reihe = true;
//		for (int i = 0; i < symbolAnzahlArray.length; i++) {
//			if (symbolAnzahlArray[i] > 2) {
//				return true;
//			}
//			//Eine Reihe wird überprüft, in dem ab dem Moment, wenn ein Symbol auf 0 ist, der boolean reihe auf false gesetzt wird
//			else if (symbolAnzahlArray[i]==0) {
//				reihe = false;
//			}
//		} if(reihe) {
//			return true;
//		}
//		return false;
//	}
	
	
//	ALTE METHODE, KANN GELÖSCHT WERDEN, BEFINDET SICH JETZT IM PLAYER OBJEKT
//	public int[] risikokartenTauschkombiVorhanden(Player aktiverPlayer) {
//		//Symbolarray mit Anzahl der vorhandenen Einheitskarten
//		//0 = Kanone, 1 = Reiter, 2 = Soldat
//		int[] symbolAnzahlArray = aktiverPlayer.risikokartenKombi();
//		//tauschkombi sagt aus, wieviele kombis vorhanden sind
//		//0 = Kanone, 1 = Reiter, 2 = Soldat, 3 = Reihe
//		int[] tauschkombi = new int[4];
//		if (symbolAnzahlArray[0] >= 3) {
//			tauschkombi[0] = symbolAnzahlArray[0]%3;
//		}
//		if (symbolAnzahlArray[1] >= 3) {
//			tauschkombi[1] = symbolAnzahlArray[1]%3;
//		}
//		if (symbolAnzahlArray[2] >= 3) {
//			tauschkombi[2] = symbolAnzahlArray[2]%3;
//		}
//		if (symbolAnzahlArray[0] > 0 && symbolAnzahlArray[1] > 0 && symbolAnzahlArray[2] > 0) {
//			int min = symbolAnzahlArray[0];
//			for (int i = 1; i < symbolAnzahlArray.length; i++) {
//				if (symbolAnzahlArray[i] < min) {
//					min = symbolAnzahlArray[i];
//				}
//			}
//			tauschkombi[3] = min;
//		}
//		return tauschkombi;
//	}
	
	//die Methode bekommt drei Symbole und gibt zurück, ob die Kombi gültig ist
	public boolean isGueltigeTauschkombi(Symbol symbol1, Symbol symbol2, Symbol symbol3) {
		if (symbol1 == symbol2 && symbol2 == symbol3) {
			return true;
		} else if (symbol1 != symbol2 && symbol2 != symbol3 && symbol1 != symbol3) {
			return true;
		} else return false;
	}
	
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
	
//					**********************Angriff-Abfragen******************************

	//TODO: nur für die CUI?
	// Methode prueft, ob Laender von einem Player mehr als eine Einheit hat
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

	//TODO: nur für die CUI?
	// Methode prueft, ob das Land ein Nachbarland mit einem anderen Besitzer hat
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

	public ArrayList<Land> getFeindlicheNachbarn(Land attackLand) throws LandExistiertNichtException {
		if(!worldMg.getLaender().contains(attackLand)) {
			throw new LandExistiertNichtException(attackLand);
		}
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
		if(!worldMg.getLaender().contains(moveLand)) {
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
	
	public ArrayList<Land> getLaenderMitEigenenNachbarn(ArrayList<Land> eigeneLaender)  {
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
	
	//gibt zurück, ob von einem Land Einheiten verschoben werden können
	public boolean moveFromLandGueltig(Land move) {
		try {
			if(move.getBesitzer().equals(turn.gibAktivenPlayer()) &&
					getEigeneNachbarn(move).size() > 0) {
				return true;
			}
		} catch (LandExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//überprüft, ob ein ausgewähltes Land angreifen kann (Besitzer = aktiverPlayer, hat feindliche Nachbarn und mehr als 1Einheit)
	public boolean attackLandGueltig(Land att) throws LandExistiertNichtException {
		if(turn.gibAktivenPlayer().equals(att.getBesitzer()) &&
				getFeindlicheNachbarn(att).size() > 0 &&
				att.getEinheiten() > 1) {
			return true;
		} 
		return false;
	}
	
	public boolean defenseLandGueltig(Land att, Land def) {
		if(!turn.gibAktivenPlayer().equals(def.getBesitzer())&&
				worldMg.isBenachbart(att, def)) {
			return true;
		} 
		return false;
	}
	
	
//					**********************ENDE Angriff-Abfragen******************************

//	public ArrayList<Integer> attack(Land att, Land def, int attEinheiten, int defEinheiten) {
//		// rollDice gibt eine Int-ArrayList zurueck, an erster Stelle die verlorenen
//		// Einheiten vom Angreifer, an zweiter vom Verteidiger
//		Player attacker = att.getBesitzer();
	

	public Attack attack(Land att, Land def, int attUnits, int defUnits) throws ZuWenigEinheitenNichtMoeglichExeption {
		Player attacker = att.getBesitzer();
		Player defender = def.getBesitzer();
		
		//erstellt ein neues Attack-Objekt, das später die Würfelaugen speichert
		Attack attackObjekt = new Attack(attacker, defender, att, def);
		
		/*
		 *  setzt, wenn "blockierte" einheiten agreifen diese vorerst auf 0
		 * Einheiten sind blockiert, wenn sie in der gleichen Runde durch einen Angriff verschoben wurden
		 */
		
		//buBlock speichert die Anzahl der blockierten Einheiten vom AngriffsLand
		int buBlock = attacker.getBlock()[att.getNummer()];
		if (buBlock > 0) {
			if (buBlock - attUnits >= 0) {
				attacker.setBlock(att.getNummer(), -attUnits);
			}
			if (buBlock - attUnits < 0) {
				attacker.setBlock(att.getNummer(), -attacker.getBlock()[att.getNummer()]);
			}
		}
		
		// Methode diceAttack und diceDefense geben pro Unit einen zufälligen Wert zwischen 1 und 6 in einer ArrayList zurück
		ArrayList<Integer> attList = null;
		try {
			attList = diceAttack(attUnits);
			attackObjekt.setAttUnits(attList);
		} catch (UngueltigeAnzahlEinheitenException e) {
			e.printStackTrace();
		}
		ArrayList<Integer> defList = null;
		try {
			defList = diceDefense(defUnits);
			attackObjekt.setDefUnits(defList);
		} catch (UngueltigeAnzahlEinheitenException e) {
			e.printStackTrace();
		}

		// ergebnis ist ein Array[2]: an 1. Stelle die verlorenen attack-Einheiten, an 2. die verlorenen defense-Einheiten (Werte sind negativ
		ArrayList<Integer> result = null;
		try{
			result= diceResults(attList, defList);
			}
		catch(UngueltigeAnzahlEinheitenException e) {
			e.printStackTrace();
		}
		attackObjekt.setResult(result);
		
		//das Spielfeld wird dem Ergebnis des Angriff angepasst
		//den beiden Ländern werden mit setEinheiten die verlorenen Einheiten abgezogen
		att.setEinheiten(result.get(0));
		def.setEinheiten(result.get(1));
		
		/*
		 * wurde das Land nicht erobert, wird der alte Wert an 
		 * Blockierten Einheiten - die verlorenen Einheiten wieder in das Block-Array geschrieben
		 */
		if (def.getEinheiten() > 0) {
			if (buBlock + result.get(0) >= 0) {
				int block = buBlock + result.get(0);
				attacker.setBlock(att.getNummer(), block);
			} else {
				attacker.setBlock(att.getNummer(), 0);
			}
			attackObjekt.setWinner(defender);
			attackObjekt.setLoser(attacker);
		}

		// wenn das Land erobert wurde (def-einheiten sind auf 0), werden die Angriffs-Einheiten verschoben
		if (def.getEinheiten() == 0) {
			//winUnits sind die Einheiten, die automatisch in das eroberte Land ziehen (angreifende Einheiten - verlorene Attack-Einheiten)
			int winUnits = attUnits + result.get(0);
			def.setEinheiten(winUnits);
			att.setEinheiten(-winUnits);
			attackObjekt.setLoser(defender);
			attackObjekt.setWinner(attacker);
			
			//das Land wird beim verlierer gelöscht und dem Sieger hinzugefügt
			defender.setBesitz(def);
			attacker.setBesitz(def);
			//der Besitzer des eroberten Landes wird aktualisiert
			def.setBesitzer(attacker);

			// setzt bei erobertem Land die beteiligten Einheiten auf Block
			attacker.setBlock(def.getNummer(), def.getEinheiten());

			// setzt beim gewinner den gutschriftEinheitenkarte auf true, damit er diese am
			// ende des Zuges ziehen kann
			attacker.setGutschriftEinheitenkarte(true);
		}
		
		return attackObjekt;
	}
	
	//ALTE ATTACK METHODE - KANN GELÖSCHT WERDEN, WENN NICHT MEHR BENÖTIGT
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
	//BIS HIERHIN LÖSCHEN!

	//DICEDICEDICE
	
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
			throw new UngueltigeAnzahlEinheitenException(1,2);
		}
		ArrayList<Integer> dList = new ArrayList<Integer>();
		for (int i = 0; i < defUnit; i++) {
			dList.add((int) (Math.random() * 6) + 1);
		}
		
		return dList;
	}


	public ArrayList<Integer> diceResults(ArrayList<Integer> aList, ArrayList<Integer> defList) throws UngueltigeAnzahlEinheitenException {
		
		if(aList.size()>3|| aList.size()<=0 || defList.size()>2||defList.size()<=0) {
			throw new UngueltigeAnzahlEinheitenException(1,3,1,2);
		}
		int lossDef = 0;
		int lossAtt = 0;
		Collections.sort(aList);
		Collections.sort(defList);
		Collections.reverse(aList);
		Collections.reverse(defList);

		//die anzahl der einheiten von att und def wird angeglichen, indem die niedrigsten Augen von def/att gelöscht werden
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

		//die Augenanzahlen werden verglichen
		//bei insgesamt einem Würfel pro Person
		if (defList.size() == 1) {
			if (aList.get(0) > defList.get(0))
				lossDef = lossDef - 1;
			else
				lossAtt = lossAtt - 1;
		}
		
		//bei insgesamt 2 Würfeln pro Person
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
	
		//bei insgesamt 3 Würfeln pro Person
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
		
		//ergebnis-Array wird zurückgegeben, 1.Stelle = verlorene Einheiten att, 2.Stelle = verlorene Einheiten def
		ArrayList<Integer> unitLoss = new ArrayList<Integer>();
		unitLoss.add(lossAtt);
		unitLoss.add(lossDef);
		return unitLoss;
	}
	
	//END DICEDICEDICE

	
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
	
	

	/*Angriff ist möglich, wenn attacker und defender-Länder benachbart sind,
	 * der attacker mehr als eine Einheit auf seinem Land hat 
	 * und ihm nicht beide Länder gehören
	 */
	boolean angriffMoeglich(Land def, Land att, int countUnits) {
		if (worldMg.nachbarn[def.getNummer()][att.getNummer()] == true 
				&& att.getEinheiten() - countUnits > 0 
				&& !(def.getBesitzer().equals(att.getBesitzer()))) {
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

	public void moveUnits(Land start, Land ziel, int menge)
			throws ZuWenigEinheitenException,ZuWenigEinheitenNichtMoeglichExeption, LandExistiertNichtException {
		if ((start.getEinheiten() - menge) < 1) {
			throw new ZuWenigEinheitenException("Zu wenige Einheiten");
		}
		if (!worldMg.getLaender().contains(start)) {
			throw new LandExistiertNichtException(start);
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

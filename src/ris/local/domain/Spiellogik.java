
package ris.local.domain;

import ris.local.domain.PlayerManagement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ris.local.valueobjects.Player;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.Land;

public class Spiellogik {

	PlayerManagement gamerVW;
	private Worldmanagement worldMg;
	private PlayerManagement playerMg;
	private int spielrunden;

	public Spiellogik(Worldmanagement worldMg, PlayerManagement playerMg) {
		this.worldMg = worldMg;
		this.playerMg = playerMg;
		spielrunden = 0;
	}

	// ***********************************Spiel_Anfang********************************************

	// Methode um Alle "LaenderKarten" durchzumischen
	public ArrayList<Land> shuffleLaender() {
		ArrayList<Land> shuffle = worldMg.getLaender();
		Collections.shuffle(shuffle);
		return shuffle;
	}

	// Methode um Laender am Anfang zuf�llig zu verteilen;
	public void verteileEinheiten() {
		ArrayList<Land> shuffle = shuffleLaender();
		int alleLaender = shuffle.size();
		List<Player> playerList = playerMg.getPlayers();

		int i = 0;
		while (i < alleLaender) {
			for (Player playerVert : playerList) {
				if (i == alleLaender) {
					break;
				} else {
					playerVert.addLand(shuffle.get(i));
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
		for(Player player : playerMg.getPlayers()) {
			for(Land land : player.getBesitz()) {
				land.setBesitzer(player);
			}
		}
	}

	// Methode die sagt wer anf�ngt
	public Player whoBegins() {
		List<Player> playerList = playerMg.getPlayers(); // mittlerweile doppelt. vlt global anlegen
		if (shuffleLaender().size() % playerList.size() != 0) { // abfrage ob alle laender aufgehen oder nicht.
			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getBesitz().size() > playerList.get(i + 1).getBesitz().size()) {
					return playerList.get(i + 1);
				}
			}
		}
		// wenn alles aufgeht f�ngt spieler 1 an
		return playerList.get(0);
	}

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SpielAnfang_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	//----------------------------------------einheiten-------------------------------------------------
	public int errechneVerfuegbareEinheiten(Player aktiverPlayer) {
		int verfuegbareEinheiten = 0;
		int landBesitz = aktiverPlayer.getBesitz().size();
		verfuegbareEinheiten = (landBesitz/3);
		if(verfuegbareEinheiten < 3) {
			verfuegbareEinheiten = 3;
		}
		ArrayList<Kontinent> kontinente = worldMg.getKontinente();
		for (Kontinent kontinent: kontinente) {
			if(kontinent.isOwnedByPlayer(aktiverPlayer)) {
				//kontinent braucht attribut: bonusEinheiten, entsprechend den bonuseinheiten
				//worldmg braucht methode getKontinente
				verfuegbareEinheiten += kontinent.getValue();
			}
		}
		return verfuegbareEinheiten;
	}
	//----------------------------------------einheiten-------------------------------------------------
	
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Anfang^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	// Methode pr�ft, ob L�nder von einem Player mehr als eine Einheit hat
	public ArrayList<Land> getLaenderMitMehrAlsEinerEinheit (Player player) {
		ArrayList<Land> starkeLaender = new ArrayList<Land>();
		ArrayList<Land> besitzPlayer = player.getBesitz();
		for (Land land: besitzPlayer) {
			if (land.getEinheiten() > 1) {
				starkeLaender.add(land);
			}
		}
		return starkeLaender;
	}
	
	// Methode pr�ft, ob das Land ein Nachbarland mit einem anderen Besitzer hat
	public ArrayList<Land> getLaenderMitFeindlichenNachbarn (Player angreifer, ArrayList<Land> laender){
		ArrayList<Land> moeglicheAngreifer = new ArrayList<Land>();
		ArrayList<Land> besitzVonPlayer = laender;
		System.out.println(besitzVonPlayer.get(0));
		for (Land land: besitzVonPlayer) {
			for (int i=0; i < worldMg.nachbarn[land.getNummer()].length; i++) {
				if (worldMg.nachbarn[land.getNummer()][i]) {
					if (!worldMg.getLaender().get(i).getBesitzer().equals(angreifer)) {
						moeglicheAngreifer.add(land);
						System.out.println(land + " kann angreifen");
					}
				}
			}
		}
		return moeglicheAngreifer;
	}
	
	public ArrayList<Land> getFeindlicheNachbarn(Land attackLand){
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
	
	public ArrayList<Integer> attack (Land att, Land def, int attEinheiten, int defEinheiten) {
		//rollDice gibt eine Int-ArrayList zur�ck, an erster Stelle die verlorenen Einheiten vom Angreifer, an zweiter vom Verteidiger
		ArrayList<Integer> ergebnis = rollDice(defEinheiten, attEinheiten);
		att.setEinheiten(ergebnis.get(1));
		def.setEinheiten(ergebnis.get(0));
//		System.out.println("Einheiten vom Angreifer: " + att.getEinheiten() + ", Einheiten vom Verteidiger: " + def.getEinheiten());
		//wenn die Einheiten auf def jetzt bei 0 sind, werden die Angriffs-Einheiten verschoben
		if (def.getEinheiten()==0) {
			def.setEinheiten(attEinheiten + ergebnis.get(0));
			att.setEinheiten(-(attEinheiten + ergebnis.get(0)));
			Player loser = def.getBesitzer();
			loser.setBesitz(def);
			Player winner = att.getBesitzer();
			winner.setBesitz(def);
			def.setBesitzer(winner);
			
		}
		return ergebnis;
	}
	
	public ArrayList<Integer> rollDice(int attUnits, int defUnits) {
		int lossDef = 0;
		int lossAtt = 0;
		ArrayList<Integer> aList = new ArrayList<Integer>();
		ArrayList<Integer> defList = new ArrayList<Integer>();
		for (int i = 0; i < attUnits; i++) {
			aList.add((int) (Math.random() * 6) + 1);

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
	
	public Player gibAktivenSpieler() {
		int spielbeginn = whoBegins().getNummer(); //abfrage funktioniert �ber spieler ID
		ArrayList<Player> spielerListe = playerMg.getPlayers();
		return spielerListe.get((spielbeginn+spielrunden)%(playerMg.getPlayers().size()));
	}
	
	public int getSpielrunden() {
		return spielrunden;
	}
	
	// evtl andere bennenung als set
	public void setSpielrunden() {
		this.spielrunden++;
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

	public void angriffAuswerten(ArrayList<Integer> dice, Land def, Land att) {
		Player attacker = isOwner(att);
		int defNew = dice.get(0);
		if (def.getEinheiten() + dice.get(0) > 0) {
			def.setEinheiten(defNew);
		} else {
			def.setFarbe(isOwner(def).getFarbe());
		}
		att.setEinheiten(dice.get(1));
	}
	public Player isOwner(Land attacker) {
		for (Player p : playerMg.getPlayers()) {
			for (Land l : p.getBesitz()) {
				if (l.getNummer() == attacker.getNummer()) {
					return p;
				}
			}
		}
		return null;
	}

//	********************************** Einheiten verschieben **********************************
	//int [] uBlock= new int[worldMg.getLaender().size()];
	
	
	public boolean movePossible(Land start,Land ziel,int menge) {
		boolean einheiten =true;
		if(start.getEinheiten()-menge<1) {
			einheiten=false;
		}
		Player x = isOwner(start);
		ArrayList<Land> z= x.getBesitz();
		ArrayList<Land> connected;
		boolean nachbar=false;
		if(worldMg.isBenachbart(start, ziel)) {
			nachbar=true;
		}
	return (nachbar&&einheiten);
	}
	
	
	
	public void moveUnits(Land start,Land ziel, int menge) {
		if(movePossible(start,ziel,menge)&&start.getBesitzer()==gibAktivenSpieler()&&ziel.getBesitzer()==gibAktivenSpieler()) {
			start.setEinheiten(-menge);
			ziel.setEinheiten(menge);
		}
	}
//	********************************** Einheiten blockieren **********************************
	public int[] initBlockedUnits(int[] uBlock) {
		for (int i=0; i<uBlock.length; i++) {
			uBlock[i]=0;
		}
		return uBlock;
	}
	public String landStatus(Land l) {
		return l.getFarbe();
	}

	public int unitsAvailable(Land l) {
		return l.getEinheiten();
	}


}


package ris.local.domain;

import ris.local.domain.PlayerManagement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ris.local.valueobjects.Player;
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

	// Methode um Laender am Anfang zufällig zu verteilen;
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

	// Methode die sagt wer anfängt
	public Player whoBegins() {
		List<Player> playerList = playerMg.getPlayers(); // mittlerweile doppelt. vlt global anlegen
		if (shuffleLaender().size() % playerList.size() != 0) { // abfrage ob alle laender aufgehen oder nicht.
			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getBesitz().size() > playerList.get(i + 1).getBesitz().size()) {
					return playerList.get(i + 1);
				}
			}
		}
		// wenn alles aufgeht fängt spieler 1 an
		return playerList.get(0);
	}

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^SpielAnfang_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	// Methode prüft, ob Länder von einem Player mehr als eine Einheit hat
	public ArrayList<Land> getLaenderMitMehrAlsEinerEinheit (Player player) {
		ArrayList<Land> starkeLaender = new ArrayList<Land>();
		ArrayList<Land> besitzPlayer = player.getBesitz();
		for (Land land: besitzPlayer) {
			if (land.getNummer() > 1) {
				starkeLaender.add(land);
			}
		}
		return starkeLaender;
	}
	
	// Methode prüft, ob das Land ein Nachbarland mit einem anderen Besitzer hat
	public ArrayList<Land> getLaenderMitFeindlichenNachbarn (ArrayList<Land> laender){
		ArrayList<Land> moeglicheAngreifer = new ArrayList<Land>();
		ArrayList<Land> besitzPlayer = laender;
		for (Land land: besitzPlayer) {
			for (int i=0; i < worldMg.nachbarn[land.getNummer()].length; i++) {
				if (!worldMg.getLaender().get(i).getBesitzer.equals(besitzPlayer.getBesitz())) {
					moeglicheAngreifer.add(land);
				}
			}
		}
		return moeglicheAngreifer;
	}
	
	public ArrayList<Land> getFeindlicheLaender(Land attackLand){
		ArrayList<Land> feindlicheLaender = new ArrayList<Land>();
		for (int i = 0; i < worldMg.nachbarn[attackLand.getNummer()].length; i++) {
			if (!worldMg.getLaender().get(i).getBesitzer().equals(attackLand.getBesitzer())) {
				feindlicheLaender.add(worldMg.getLaender().get(i));
			}
		}
		return feindlicheLaender;
	}
	
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Angriff_Ende^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	// ***************************************->Runden<-**************************************
	
	public Player gibAktivenSpieler() {
		int spielbeginn = whoBegins().getNummer(); //abfrage funktioniert über spieler ID
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

//	public void moveUnits() { // TODO
//		
//	}
//	public boolean movePossible() {}
//	
	public String landStatus(Land l) {
		return l.getFarbe();
	}

	public int unitsAvailable(Land l) {
		return l.getEinheiten();
	}

//	public String angriff(int land, Player spieler){
//		ArrayList<Land> feinde = new ArrayList<Land>();
//		for (int i = 0; i < nachbarn[land].length; i++) {
//			if (nachbarn[land][i] && !(laender[land].getFarbe().equals(spieler.getFarbe()))) {
//				feinde.add(laender[i]);
//			}
//		}
//		String result = "";
//		for (Land feind: feinde) {
//			result += feind.getNummer() + " > " + feind.getName() + "\n";
//		}
//		return result;
//	}

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
		System.out.println("d1" + " " + defList.get(0));
		System.out.println("d2" + " " + defList.get(1));
		System.out.println("d3" + " " + defList.get(2));
		System.out.println("a1" + " " + aList.get(0));
		System.out.println("a2" + " " + aList.get(1));
		System.out.println("a3" + " " + aList.get(2));
		System.out.println(" ");
		Collections.sort(aList);
		Collections.sort(defList);
		Collections.reverse(aList);
		Collections.reverse(defList);
		System.out.println("d1" + " " + defList.get(0));
		System.out.println("d2" + " " + defList.get(1));
		System.out.println("d3" + " " + defList.get(2));
		System.out.println("a1" + " " + aList.get(0));
		System.out.println("a2" + " " + aList.get(1));
		System.out.println("a3" + " " + aList.get(2));
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
		unitLoss.add(lossDef);
		unitLoss.add(lossAtt);
		System.out.println("Def:" + lossDef + " " + "Att" + lossAtt);
		return unitLoss;
	}

}


package ris.local.domain;

import ris.local.domain.Playermanagement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ris.local.valueobjects.Player;
import ris.local.valueobjects.Land;

public class Spiellogik {

	Playermanagement gamerVW;
	private Worldmanagement worldMg;
	private Playermanagement playerMg;

	public int laenderAnzahl;

	public Spiellogik(Worldmanagement worldMg, Playermanagement playerMg) {
		this.worldMg = worldMg;
		this.playerMg = playerMg;

		laenderAnzahl = worldMg.getLaender().size();
	}

//	*********************************** SpielAnfang ********************************************

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
	}

	// Methode die sagt wer anfängt  
	public void whoBegins() {
		List<Player> playerList = playerMg.getPlayers(); // mittlerweile doppelt. vlt global anlegen
		if(shuffleLaender().size() % playerList.size() != 0) { //abfrage ob alle laender aufgehen oder nicht.
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getBesitz().size() > playerList.get(i + 1).getBesitz().size()) {
				System.out.println(playerList.get(i + 1).getName() + " faengt an"); // platzhalter methode wer zuerst anfängt
				break;
			}
		}
		} else {
			System.out.println(playerList.get(0).getName() + " faengt an"); // hier geht die Verteilung aller laender auf und spieler eins fängt an
		}
	}
	
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ SpielAnfang ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
//	public void faerbeLaender() {
//		
//	}
	

//	// laufvariablen für zufällige Länderverteilung
//	private int i = 0;
//	private int k = 0;
//	// hier werden Länderkarten gemischt.
//
//	private ArrayList<Land> laenderZuweisung(int anzahlAnLaendern) {
//
//
//		for (; i < (k + anzahlAnLaendern); i++) {
//			besitzt.add(welt.laender[zufall.get(i)]);
//		}
//		k = k + anzahlAnLaendern;
//		return besitzt;
//	}
//	
//	
//	********************************** Angriffslogik **********************************

//	boolean angriffMoeglich() {}

//	public ArrayList<Integer> verteileEinheiten(){}

	public void angriffAuswerten(ArrayList<Integer> dice, Land def, Land att) {
		Player attacker = isOwner(att);
		int defNew = dice.get(0);
		if (def.getEinheiten() > dice.get(0)) {
			def.setEinheiten(defNew);
		} else {
			def.setFarbe(attacker.getFarbe());
		}
		att.setEinheiten(dice.get(1));
	}

	public Player isOwner(Land attacker) {
		for (Player p : playerMg.getPlayers())
			if (p.getBesitz().contains(attacker)) {
				return p;
			}
		return null;
	}
//	public void moveUnits() {}
//	public boolean movePossible() {
		
//	}
//	
//	public String landStatus() {}
//	public int unitsAvailable(int Land) {}

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
//	

	private ArrayList<Integer> rollDice(int attUnits, int defUnits) {
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

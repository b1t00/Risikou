package ris.local.domain;

import java.util.ArrayList;

import ris.local.valueobjects.Player;
import ris.local.valueobjects.Land;

public class Spiellogik {
	private Worldmanagement worldMg;
	private Playermanagement playerMg;
	
	public Spiellogik(Worldmanagement worldMg, Playermanagement playerMg) {
		this.worldMg = worldMg;
		this.playerMg = playerMg;
	}
	
	public String angriff(int land, Player spieler){
		ArrayList<Land> feinde = new ArrayList<Land>();
		for (int i = 0; i < nachbarn[land].length; i++) {
			if (nachbarn[land][i] && !(laender[land].getFarbe().equals(spieler.getFarbe()))) {
				feinde.add(laender[i]);
			}
		}
		String result = "";
		for (Land feind: feinde) {
			result += feind.getNummer() + " > " + feind.getName() + "\n";
		}
		return result;
	}
	
}

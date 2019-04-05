package ris.local.domain;

import java.util.ArrayList;

import ris.local.valueobjects.Gamer;
import ris.local.valueobjects.Land;

public class Spiellogik {

	Gamerverwaltung gamerVW;
	
	
	public String angriff(int land, Gamer spieler){
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

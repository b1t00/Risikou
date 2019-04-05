package ris.local.valueobjects;

import java.util.ArrayList;

public class Kontinent {
	public String name;
	public ArrayList<Land> laender;
	
	public Kontinent(String name, ArrayList<Land> laender) {
		this.name = name;
		this.laender = laender;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Land> getLaender(){
		return laender;
	}
	
	//sollte vielleicht auch in die spiellogik?
	public boolean isOwnedByPlayer(Player player) {
		ArrayList<Land> testArray = player.getBesitz();
		//durchläuft den eigenen Array und überprüft bei jedem Land, ob es in dem Besitz-Array des Spielers ist
		for (Land land: laender) {
			if (!testArray.contains(land)) {
				return false;
			}
		}
		return true;
	}
	
	
}

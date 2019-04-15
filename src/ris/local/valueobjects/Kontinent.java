package ris.local.valueobjects;

import java.util.ArrayList;

public class Kontinent {
	public String name;
	public ArrayList<Land> laender;
	private int bonusEinheiten;
	
	public Kontinent(String name, ArrayList<Land> laender, int bonusEinheiten) {
		this.name = name;
		this.laender = laender;
		this.bonusEinheiten = bonusEinheiten;
	}
	
	public int getBonusEinheiten() {
		return bonusEinheiten;
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
		//durchl�uft den eigenen Array und �berpr�ft bei jedem Land, ob es in dem Besitz-Array des Spielers ist
		for (Land land: laender) {
			if (!testArray.contains(land)) {
				return false;
			}
		}
		return true;
	}
	
	
}

package ris.local.valueobjects;

import java.util.ArrayList;

public class Kontinent {
	public String name;
	private ArrayList<Land> laender; 
	private int value;
	
	public Kontinent(String name, ArrayList<Land> laender, int value) {
		this.name = name;
		this.laender = laender;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Land> getLaender(){
		return laender;
	}
	
	public int getValue() {
		return value;
	}
	
	//sollte vielleicht auch in die spiellogik?
	public boolean isOwnedByPlayer(Player player) {
		ArrayList<Land> testArray = player.getBesitz();
		//durchläuft den eigenen Array und überprüft bei jedem Land, ob es in dem Besitz-Array des Players ist
		for (Land land: laender) {
			if (!testArray.contains(land)) {
				return false;
			}
		}
		return true;
	}
	
	
}

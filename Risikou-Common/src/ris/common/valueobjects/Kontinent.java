package ris.common.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Kontinent implements Serializable {
	public String name;
	private ArrayList<Land> laender;
//	Anzahl an Einheiten, die der Besitzer bekommt, wenn er den Kontinent besitzt
	private int value;

	public Kontinent(String name, ArrayList<Land> laender, int value) {
		this.name = name;
		this.laender = laender;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Land> getLaender() {
		return laender;
	}

	public int getValue() {
		return value;
	}

//	durchlaeuft den eigenen Array und ueberprueft bei jedem Land, ob es in dem Besitz-Array des Players ist
	public boolean isOwnedByPlayer(Player player) {
		ArrayList<Land> testArray = player.getBesitz();
		for (Land land : laender) {
			if (!testArray.contains(land)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}

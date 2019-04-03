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
	
	public boolean isOwnedByGamer(Gamer gamer) {
		return false; // TODO
	}
	
}

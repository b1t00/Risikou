package ris.local.valueobjects;

import java.util.ArrayList;

import ris.local.domain.PlayerManagement;

public class Missionen {

	private ArrayList<String> missionen;
	private PlayerManagement playerMg;
	
	public Missionen(PlayerManagement playerMg) {
		this.playerMg = playerMg;
		missionen = new ArrayList<String>();
		missionen.add("mission 1");
		missionen.add("mission 2");
		missionen.add("mission 3");
		missionen.add("mission 4");
		missionen.add("mission 5");
		missionen.add("mission 6");
		farbenUeberspielen();
		
	}
	
	public ArrayList<String> getMissionen(){
		return missionen;
	}
	public void farbenUeberspielen() {
		ArrayList<String> farben = playerMg.getFarbauswahl();
		
		if(!farben.contains("rot")) {
			missionen.add("befreie rot");
		}
		if(!farben.contains("gruen")) {
			missionen.add("befreie gruen");
		}
		if(!farben.contains("blau")) {
			missionen.add("befreie blau");
		}
		if(!farben.contains("pink")) {
			missionen.add("befreie pink");
		}
		if(!farben.contains("schwarz")) {
			missionen.add("befreie schwarz");
		}
		if(!farben.contains("weiss")) {
			missionen.add("befreie weiﬂ");
		}
	}
}

package ris.local.domain;

import java.util.ArrayList;

public class MissionsManagement{
	private ArrayList<String> missionen;
	private PlayerManagement playerMg;
	
	public MissionsManagement(PlayerManagement playerMg) {
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
			missionen.add("lösche rot aus");
		}
		if(!farben.contains("gruen")) {
			missionen.add("lass gruen einen schmerzhaften langsamen Tod sterben \n wenn du selbst Grün bist, mach etwas anderes");
		}
		if(!farben.contains("blau")) {
			missionen.add("erledige blau");
		}
		if(!farben.contains("pink")) {
			missionen.add("jage pink");
		}
		if(!farben.contains("schwarz")) {
			missionen.add("kill schwarz");
		}
		if(!farben.contains("weiss")) {
			missionen.add("hau weiß weg");
		}
	}
}

package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

import ris.local.domain.PlayerManagement;

public class Mission implements Serializable{

	private ArrayList<String> missionen;
	private PlayerManagement playerMg;
	
	public Mission(PlayerManagement playerMg) {
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
	
	public ArrayList<String> getMission(){
		return missionen;
	}
	public void farbenUeberspielen() {
		ArrayList<String> farben = playerMg.getFarbauswahl();
		
		if(!farben.contains("rot")) {
			missionen.add("l�sche rot aus");
		}
		if(!farben.contains("gruen")) {
			missionen.add("lass gruen einen schmerzhaften langsamen Tod sterben \n wenn du selbst Gr�n bist, mach etwas anderes");
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
			missionen.add("hau wei� weg");
		}
	}
}

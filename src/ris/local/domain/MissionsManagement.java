package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ris.local.valueobjects.MissionGegner;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.MissionKontinent;
import ris.local.valueobjects.MissionLaenderanzahl;
import ris.local.valueobjects.MissionTest;
import ris.local.valueobjects.Mission;
import ris.local.valueobjects.Player;

public class MissionsManagement implements Serializable {
	private ArrayList<Mission> missionen;
	private PlayerManagement playerMg;
	private WorldManagement worldMg;


	public MissionsManagement(PlayerManagement playerMg, WorldManagement worldMg) {
		this.playerMg = playerMg;
		this.worldMg = worldMg;
		missionen = new ArrayList<Mission>();

		// @tobi Methode wird wahrscheinlich nicht gebraucht : Methode die automatisch Eroberungsmission für einen Kontinent erstellt
//		for(Kontinent kontinent : worldMg.getKontinente()) { 
//			missionen.add(new KontinentMission("mission 1: erobere " + kontinent.getName(), kontinent.getLaender()));
//				
//			}

		missionen.add(new MissionTest(worldMg.getLaender().get(0)));
		missionen.add(new MissionKontinent("mission 1: Erobere Europa und Australien", worldMg.getKontinente().get(0),
				worldMg.getKontinente().get(1)));
		missionen.add(new MissionLaenderanzahl("mission2 : Erobere 8 (test) Länder deiner Wahl"));
		for (Player player : playerMg.getPlayers()) {
			missionen.add(
					new MissionGegner("loesche " + player.getName() + " (" + player.getFarbe() + ")" + " aus!", player));
		}
	}
	public ArrayList<Mission> getMission() {
		return missionen;
	}

}

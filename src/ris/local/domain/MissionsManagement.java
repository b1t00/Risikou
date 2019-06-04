package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ris.local.valueobjects.MissionGegner;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.MissionKontinent;
import ris.local.valueobjects.MissionLaenderanzahl;
import ris.local.valueobjects.Mission;
import ris.local.valueobjects.Player;

public class MissionsManagement {
	private ArrayList<Mission> missionen;
	private PlayerManagement playerMg;
	private WorldManagement worldMg;

	public MissionsManagement(PlayerManagement playerMg, WorldManagement worldMg) {
		this.playerMg = playerMg;
		this.worldMg = worldMg;
		missionen = new ArrayList<Mission>();

		// @tobi Methode wird wahrscheinlich nicht gebraucht : Methode die automatisch
		// Eroberungsmission für einen Kontinent erstellt
//		for(Kontinent kontinent : worldMg.getKontinente()) { 
//			missionen.add(new KontinentMission("mission 1: erobere " + kontinent.getName(), kontinent.getLaender()));
//				
//			}

		missionen.add(new MissionKontinent(worldMg.getKontinente().get(0), worldMg.getKontinente().get(1)));
		missionen.add(new MissionLaenderanzahl());
		for (Player player : playerMg.getPlayers()) {
			missionen.add(new MissionGegner(player));
		}
	}

	public ArrayList<Mission> getMission() {
		return missionen;
	}

}

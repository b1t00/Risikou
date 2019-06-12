package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ris.common.valueobjects.MissionGegner;
import ris.common.valueobjects.Kontinent;
import ris.common.valueobjects.MissionKontinent;
import ris.common.valueobjects.MissionLaenderanzahl;
import ris.common.valueobjects.Mission;
import ris.common.valueobjects.Player;

public class MissionsManagement implements Serializable{
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

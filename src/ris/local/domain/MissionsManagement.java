package ris.local.domain;

import java.util.ArrayList;
import java.util.List;

import ris.local.valueobjects.MissionGegner;
import ris.local.valueobjects.Kontinent;
import ris.local.valueobjects.MissionKontinent;
import ris.local.valueobjects.MissionLaenderanzahl;
import ris.local.valueobjects.MissionsVorlage;
import ris.local.valueobjects.Player;

public class MissionsManagement {
	private ArrayList<MissionsVorlage> missionen;
	private PlayerManagement playerMg;
	private WorldManagement worldMg;

	private boolean isPlayer = false;

	public MissionsManagement(PlayerManagement playerMg, WorldManagement worldMg) {
		this.playerMg = playerMg;
		this.worldMg = worldMg;
		missionen = new ArrayList<MissionsVorlage>();

		// @tobi Methode wird wahrscheinlich nicht gebraucht
//		for(Kontinent kontinent : worldMg.getKontinente()) { 
//			missionen.add(new KontinentMission("mission 1: erobere " + kontinent.getName(), kontinent.getLaender()));
//				
//			}

		missionen.add(new MissionKontinent("mission 1: Erobere Europa und Australien", worldMg.getKontinente().get(0),
				worldMg.getKontinente().get(1)));
		missionen.add(new MissionLaenderanzahl("mission2 (test): Erobere 6 Länder deiner Wahl"));
		for (Player player : playerMg.getPlayers()) {
			missionen.add(
					new MissionGegner("loesche " + player.getName() + "(" + player.getFarbe() + ")" + " aus!", player));
		}

	}

	public ArrayList<MissionsVorlage> getMission() {
		return missionen;
	}

}

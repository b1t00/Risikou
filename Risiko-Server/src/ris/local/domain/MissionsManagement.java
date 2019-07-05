package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;

import ris.common.valueobjects.Mission;
import ris.common.valueobjects.MissionGegner;
import ris.common.valueobjects.MissionKontinent;
import ris.common.valueobjects.MissionLaenderanzahl;
import ris.common.valueobjects.Player;

/*
 * @class drei verschiedene Missionsklassen werden hier verwaltet
 */
public class MissionsManagement implements Serializable {
	private ArrayList<Mission> missionen;
	private PlayerManagement playerMg;
	private WorldManagement worldMg;

	public MissionsManagement(PlayerManagement playerMg, WorldManagement worldMg) {
		this.playerMg = playerMg;
		this.worldMg = worldMg;
		missionen = new ArrayList<Mission>();

		missionen.add(new MissionKontinent(worldMg.getKontinente().get(0), worldMg.getKontinente().get(1)));
		missionen.add(new MissionKontinent(worldMg.getKontinente().get(2), worldMg.getKontinente().get(4)));
		missionen.add(new MissionKontinent(worldMg.getKontinente().get(3), worldMg.getKontinente().get(5)));
		missionen.add(new MissionLaenderanzahl());
		missionen.add(new MissionLaenderanzahl());
		for (Player player : playerMg.getPlayers()) {
			missionen.add(new MissionGegner(player));
		}
	}

	public ArrayList<Mission> getMission() {
		return missionen;
	}

}

package ris.local.valueobjects;

import java.io.Serializable;

public class MissionLaenderanzahl extends Mission implements Serializable {

	public MissionLaenderanzahl() {
		super("mission2 : Erobere 8 (test) L�nder deiner Wahl");
	}

	@Override
	public boolean missionComplete(Player aktiverSpieler) {
		if (aktiverSpieler.getBesitz().size() > 7) {
			return true;
		}
		return false;
	}

}

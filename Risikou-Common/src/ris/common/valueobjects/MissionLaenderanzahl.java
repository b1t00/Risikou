package ris.common.valueobjects;

import java.io.Serializable;

public class MissionLaenderanzahl extends Mission implements Serializable {

	public MissionLaenderanzahl() {
		super("mission2 : Erobere 24 Laender deiner Wahl");
	}

	@Override
	public boolean missionComplete(Player aktiverSpieler) {
		if (aktiverSpieler.getBesitz().size() > 23) {
			return true;
		}
		return false;
	}

}

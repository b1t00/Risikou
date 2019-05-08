package ris.local.valueobjects;

import java.io.Serializable;

public class MissionLaenderanzahl extends Mission implements Serializable {

	public MissionLaenderanzahl(String missionstext) {
		super(missionstext);
	}

	@Override
	public boolean missionComplete(Player aktiverSpieler) {
		if (aktiverSpieler.getBesitz().size() > 7) {
			return true;
		}
		return false;
	}

}

package ris.local.valueobjects;

public class MissionLaenderanzahl extends MissionsVorlage {

	public MissionLaenderanzahl(String missionstext) {
		super(missionstext);
	}

	@Override
	public boolean missionComplete(Player aktiverSpieler) {
		if (aktiverSpieler.getBesitz().size() > 5) {
			return true;
		}
		return false;
	}

}

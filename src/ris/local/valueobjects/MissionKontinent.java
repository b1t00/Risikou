package ris.local.valueobjects;

import java.util.ArrayList;

public class MissionKontinent extends Mission {
	private Kontinent kontinent1, kontinent2;

	public MissionKontinent(String missionstext, Kontinent kontinent1, Kontinent kontinent2) {
		super(missionstext);
		this.missionstext = missionstext;
		this.kontinent1 = kontinent1;
		this.kontinent2 = kontinent2;


	}

	public boolean missionComplete(Player aktiverSpieler) {
		if (kontinent1.isOwnedByPlayer(aktiverSpieler) && kontinent2.isOwnedByPlayer(aktiverSpieler))
			return true;
		else
			return false;
	}

}
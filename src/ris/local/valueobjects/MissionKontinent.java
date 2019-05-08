package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

public class MissionKontinent extends Mission implements Serializable {
	private Kontinent kontinent1, kontinent2;

	// evtl. noch eine Alternative mit Kontinent einer Wahl
	public MissionKontinent(String missionstext, Kontinent kontinent1, Kontinent kontinent2) {
		super(missionstext);
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
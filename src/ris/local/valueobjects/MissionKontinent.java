package ris.local.valueobjects;

import java.util.ArrayList;

public class MissionKontinent extends MissionsVorlage {
	private Kontinent kontinent1, kontinent2;
	private ArrayList<Land> einzunehmendeKontinente; // checken

	public MissionKontinent(String missionstext, Kontinent kontinent1, Kontinent kontinent2) {
		super(missionstext);
		this.missionstext = missionstext;
		this.kontinent1 = kontinent1;
		this.kontinent2 = kontinent2;

//		for(Kontinent kontinent : kontinente) {
//			for(Land land : kontinent.getLaender()) {
//				
//			}

	}

	public boolean missionComplete(Player aktiverSpieler) {
		if (kontinent1.isOwnedByPlayer(aktiverSpieler) && kontinent2.isOwnedByPlayer(aktiverSpieler))
			return true;
		else
			return false;
	}

}
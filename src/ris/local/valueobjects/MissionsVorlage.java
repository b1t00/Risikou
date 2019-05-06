package ris.local.valueobjects;

import java.util.ArrayList;

import ris.local.domain.PlayerManagement;


public abstract class MissionsVorlage {

	protected String missionstext;

	public MissionsVorlage(String missionstext){
		this.missionstext = missionstext;		
	}
	
	public String getMission(){
		return missionstext; 
	}
	
	public abstract boolean missionComplete(Player aktiverSpieler);	
	
}


package ris.common.valueobjects;

import java.io.Serializable;

public abstract class Mission implements Serializable {

	protected String missionstext;

	public Mission(String missionstext){
		this.missionstext = missionstext;		
	}
	
	public String getMission(){
		return missionstext; 
	}
	
	public abstract boolean missionComplete(Player aktiverSpieler);	
	
}


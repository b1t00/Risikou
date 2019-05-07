package ris.local.valueobjects;

public abstract class Mission {

	protected String missionstext;

	public Mission(String missionstext){
		this.missionstext = missionstext;		
	}
	
	public String getMission(){
		return missionstext; 
	}
	
	public abstract boolean missionComplete(Player aktiverSpieler);	
	
}


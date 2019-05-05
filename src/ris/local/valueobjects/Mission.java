package ris.local.valueobjects;

import java.util.ArrayList;

import ris.local.domain.PlayerManagement;

public class Mission {

private String missionstext;

	public Mission(String missionstext){
		this.missionstext = missionstext;
	}
	
	public String getMission(){
		return missionstext;
	}
	
	//missionerledigt methode()
}

package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

import ris.local.domain.PlayerManagement;

public class Mission implements Serializable{

private String missionstext;

	public Mission(String missionstext){
		this.missionstext = missionstext;
	}
	
	public String getMission(){
		return missionstext;
	}
	
	//missionerledigt methode()
}

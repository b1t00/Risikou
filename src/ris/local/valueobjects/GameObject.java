package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable{

	private ArrayList<Player> allePlayer;
	private Turn spielstand;
	
	public GameObject(ArrayList<Player> allePlayer, Turn spielstand) {
		this.allePlayer = allePlayer;
		this.spielstand = spielstand;
	}
	
	public void setAllePlayer(ArrayList<Player> allePlayer) {
		this.allePlayer = allePlayer;
	}
	
	public void setSpielstand(Turn spielstand) {
		this.spielstand = spielstand;
	}
	
	public ArrayList<Player> getAllePlayer(){
		return allePlayer;
	}
	
	public Turn getSpielstand() {
		return spielstand;
	}
	
}

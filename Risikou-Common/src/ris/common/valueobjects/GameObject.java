package ris.common.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

/*
 *  @class das GameObject wird beim Speichern genutzt und enthaelt alle wichtigen Daten zu dem Spiel
 */

public class GameObject implements Serializable{

	private ArrayList<Player> allePlayer;
	private Turn spielstand;
	//risikokartenstapel
	
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

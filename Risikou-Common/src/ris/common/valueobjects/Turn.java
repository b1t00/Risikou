package ris.common.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

//import ris.local.domain.PlayerManagement;

public class Turn implements Serializable{
	
	private Player aktiverPlayer;
	//state steht für Spielphase
	private State state;
	private int spielrunden;
	private ArrayList<Player> playerList;
//	tauschzeit und landclickzeit sind werte fuer die gui, stehen diese auf true werden clicks ausgewertet, bei false werden sie ignoriert
	private boolean tauschZeit = false;
	private boolean landClickZeit = false;
	
	public Turn(ArrayList<Player> playerList) {
		this.state = State.SETUNITS;
		this.playerList = playerList;
		spielrunden = 0;
	}

	public void setNextState() {
		this.state = this.state.setNextState();
		
		System.out.println("set next state : " + getCurrentState().toString()); //TODO: Test
	}
	
	public State getCurrentState () {
		return state;
	}	
	
	public void setAktivenPlayer(Player player) {
		aktiverPlayer = player;
	}
	
	public Player gibAktivenPlayer() {
		return aktiverPlayer;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}
	
	// @tobi wird glaube nirgendwo benutzt.. kann man aber evtl Extrasachen mit
	// machen
	public int getSpielrunden() {
		return spielrunden;
	}

	// evtl andere bennenung als set
	public void naechsteSpielrunde() {
		this.spielrunden++;
		aktiverPlayer = playerList.get((aktiverPlayer.getNummer() + 1) % playerList.size());
	}
	
	public boolean getTauschZeit() {
		return tauschZeit;
	}
	
	public void setTauschZeit(boolean tauschZeit) {
		this.tauschZeit = tauschZeit;
	}

	public boolean getLandClickZeit() {
		return landClickZeit;
	}
	
	public void setLandClickZeit(boolean landClickZeit) {
		this.landClickZeit = landClickZeit;
	}


}

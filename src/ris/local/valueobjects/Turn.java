package ris.local.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ris.local.domain.PlayerManagement;

public class Turn implements Serializable{
	private Player aktiverPlayer;
	//state steht für Spielphase
	//TODO: muss noch private gemacht werdem
	public State state;
	private int spielrunden;
	private ArrayList<Player> playerList;
	private PlayerManagement playerMg;
	
	public Turn(PlayerManagement playerMg) {
		this.state = State.SETUNITS;
		this.playerMg = playerMg;
		playerList = playerMg.getPlayers();
		spielrunden = 0;
	}

	public void setNextState() {
		this.state = this.state.setNextState();
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
	
//	public static void main(String[] args) {
//		Turn turn = new Turn();
//		turn.state = State.ATTACK;
//		System.out.println(turn.state);
//		turn.setNextState();
//		System.out.println(turn.state);
//	}
}

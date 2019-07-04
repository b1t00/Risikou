package ris.common.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * @class Turn enthaelt den aktuellen Spielstand: also aktiver Player, Spielphase(State) und eine ArrayList mit allen Playern des Spiels
 */
public class Turn implements Serializable {

	private Player aktiverPlayer;
//	state steht für Spielphase
	private State state;
	private ArrayList<Player> playerList;
	private int spielrunden;
//	tauschzeit und landclickzeit sind werte fuer die gui, stehen diese auf true werden clicks ausgewertet, bei false werden sie ignoriert
	private boolean tauschZeit = false;
	private boolean landClickZeit = false;

	public Turn(ArrayList<Player> playerList) {
//		state ist zu Beginn immer SetUnits
		this.state = State.SETUNITS;
		this.playerList = playerList;
		spielrunden = 0;
	}

	public void setNextState() {
		this.state = this.state.setNextState();
	}

	public State getCurrentState() {
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

//	spielrunden wird bisher nicht genutzt, aber vielleicht hilfreich, um dem spieler anzuzeigen, dass er schon zu lange spielt?
	public int getSpielrunden() {
		return spielrunden;
	}

//	naechste Spielrunde setzt den neuen aktiven Player
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

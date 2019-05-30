package ris.local.valueobjects;

import java.io.Serializable;

public class Turn implements Serializable{
	private Player aktuellerPlayer;
	//state steht für Spielphase
	//TODO: muss noch private gemacht werdem
	public State state;
	
	public Turn() {
		this.state = State.SETUNITS;
	}
	
	//turn gibt aktuellen player zurück
	public Player getAktuellerPlayer (){
		return aktuellerPlayer;
	}
	
	public void setNextState() {
		this.state = this.state.setNextState();
	}
	
	public State getCurrentState () {
		return state;
	}	
	
//	public static void main(String[] args) {
//		Turn turn = new Turn();
//		turn.state = State.ATTACK;
//		System.out.println(turn.state);
//		turn.setNextState();
//		System.out.println(turn.state);
//	}
}

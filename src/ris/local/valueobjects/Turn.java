package ris.local.valueobjects;

public class Turn {
	private Player aktuellerPlayer;
	//state steht für Spielphase
	public State state;
	
	
	//enum State enthält drei Spielphasen
	private enum State {
		SETUNITS,
		ATTACK,
		CHANGEUNITS;
		
		public State setNextState() {
			return State.values()[(this.ordinal() + 1) % values().length];
		}
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
	
	public static void main (String[] args) {
		Turn turn = new Turn();
		turn.state = State.SETUNITS;
		System.out.println("Anfangswert = " + turn.state);
		turn.setNextState();
		System.out.println("Iterierter Wert = " + turn.state);
		turn.setNextState();
		System.out.println("Iterierter Wert = " + turn.state);
		turn.setNextState();
		System.out.println("Iterierter Wert = " + turn.state);
		
	}
}

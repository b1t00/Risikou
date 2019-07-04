package ris.common.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * @class Attack wird bei jedem neuen Angriff erstellt und beinhaltet alle noetigen Informationen
 * zum einen notwendig, da die Info der verteidigenden Units zu einem spaeteren Zeitpunkt hinzukommen
 * wird an alle Clients verschickt, damit diese die Informationen in der GUI verarbeiten koennen
 */
public class Attack implements Serializable {
	private Player attacker;
	private Player defender;
	private Land attack;
	private Land defense;
	private Player winner;
	private Player loser;
	private int anzahlAttUnits;
	private int anzahlDefUnits;
	private int[] attUnits;
	private int[] defUnits;
	private ArrayList<Integer> result;

	
	public Attack(Land attLand, Land defLand, int anzahlAttUnits) {
		this.attack = attLand;
		this.defense = defLand;
		this.attacker = attLand.getBesitzer();
		this.defender = defLand.getBesitzer();
		this.anzahlAttUnits = anzahlAttUnits;
	}
	
	public void setAnzahlDefUnits(int anzahlDefUnits) {
		this.anzahlDefUnits = anzahlDefUnits;
	}
	
	public void setAttUnits(ArrayList<Integer> attList) {
		this.attUnits = new int[attList.size()];
		for(int i = 0; i < attList.size(); i++) {
			this.attUnits[i] = attList.get(i);
		}
	}
	
	public void setDefUnits(ArrayList<Integer> defList) {
		this.defUnits = new int[defList.size()];
				for(int i = 0; i < defList.size(); i++) {
					this.defUnits[i] = defList.get(i);
				}
	}
	
	public void setResult(ArrayList<Integer> result) {
		this.result = result;
	}
	
	public void setWinner(Player player) {
		winner = player;
	}
	
	public void setLoser(Player player) {
		loser = player;
	}
	
	public ArrayList<Integer> getResult(){
		return result;
	}
	
	public Player getWinner() {
		return winner;
	}

	public Player getLoser() {
		return loser;
	}
	
	public int[] getAttUnits() {
		return attUnits;
	}
	
	public int[] getDefUnits() {
		return defUnits;
	}

	public Player getAttacker() {
		return attacker;
	}

	public Player getDefender() {
		return defender;
	}
	
	public Land getAttLand() {
		return attack;
	}
	
	public Land getDefLand() {
		return defense;
	}
	
	public int getAnzahlAttUnits() {
		return anzahlAttUnits;
	}
}

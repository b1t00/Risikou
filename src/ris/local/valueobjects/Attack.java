package ris.local.valueobjects;

import java.util.ArrayList;

public class Attack {
	private Player attacker;
	private Player defender;
	private Land attack;
	private Land defense;
	private Player winner;
	private Player loser;
	private ArrayList<Integer> attUnits;
	private ArrayList<Integer> defUnits;
	private ArrayList<Integer> result;

	
	public Attack(Player attacker, Player defender, Land attack, Land defense) {
		this.attacker = attacker;
		this.defender = defender;
		this.attack = attack;
		this.defense = defense;
	}
	
	public void setAttUnits(ArrayList<Integer> attUnits) {
		this.attUnits = attUnits;
	}
	
	public void setDefUnits(ArrayList<Integer> defUnits) {
		this.defUnits = defUnits;
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
	
	public Player getWinner() {
		return winner;
	}

	public Player getLoser() {
		return loser;
	}
	
	public ArrayList getAttUnits() {
		return attUnits;
	}
	
	public ArrayList getDefUnits() {
		return defUnits;
	}

	public Player getAttacker() {
		return attacker;
	}

	public Player getDefender() {
		return defender;
	}
}

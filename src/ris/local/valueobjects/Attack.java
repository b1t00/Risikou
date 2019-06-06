package ris.local.valueobjects;

import java.util.ArrayList;

public class Attack {
	private Player attacker;
	private Player defender;
	private Land attack;
	private Land defense;
	private Player winner;
	private Player loser;
	private int[] attUnits;
	private int[] defUnits;
	private ArrayList<Integer> result;

	
	public Attack(Player attacker, Player defender, Land attack, Land defense) {
		this.attacker = attacker;
		this.defender = defender;
		this.attack = attack;
		this.defense = defense;
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
}

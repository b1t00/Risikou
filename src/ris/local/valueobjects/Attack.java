package ris.local.valueobjects;

import java.util.ArrayList;

public class Attack {
	private Player attacker;
	private Player defender;
	private Land attack;
	private Land defense;
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

}

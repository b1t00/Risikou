package ris.local.valueobjects;

import java.util.ArrayList;

public class Attack {
	private Player attacker;
	private Player verteidiger;
	private Land att;
	private Land defense;
	private int attUnits;
	private int unitsVerteidigung;
	private ArrayList<Integer> auswertung = new ArrayList<Integer>();
	
	public Attack(Player attacker, Land attack) {
		this.attacker = attacker;
		this.att = attack;
	}
	
	public void setDefense(Land defense, Player verteidiger) {
		this.defense = defense;
		this.verteidiger = verteidiger;
	}
	
	public void setUnitsAngriff(int angriff) {
		attUnits = angriff;
	}
	
	public void setUnitsVerteidigung(int verteidigung) {
		unitsVerteidigung = verteidigung;
	}
	
	public ArrayList<Integer> angriffAuswerten(){
		int buBlock = attacker.getBlock()[att.getNummer()];
		if (attacker.getBlock()[att.getNummer()] > 0) {
			if (attacker.getBlock()[att.getNummer()] - attUnits >= 0) {
				attacker.setBlock(attacker.getBlock(), att.getNummer(), -attUnits);
			}
			if (attacker.getBlock()[att.getNummer()] - attUnits < 0) {
				attacker.setBlock(attacker.getBlock(), att.getNummer(), -attacker.getBlock()[att.getNummer()]);
			}
		}
	}
}

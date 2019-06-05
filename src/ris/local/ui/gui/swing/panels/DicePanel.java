package ris.local.ui.gui.swing.panels;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ris.local.valueobjects.Attack;

public class DicePanel extends JPanel {
	private Attack attObj;
	
	public void setAttack(Attack attObj) {
		this.attObj = attObj;
	}
	
	public void showResult() {
		String ergebnis = attObj.getAttacker() + " würfelt ";
		System.out.println("dice attack anzahl: " + attObj.getAttUnits().length);
		int[] attack = attObj.getAttUnits();
		for (Integer i : attack) {
			ergebnis = ergebnis + i + ", ";
		}
		ergebnis = ergebnis + ". " + attObj.getDefender() + " würfelt ";
		int[] defense = attObj.getDefUnits();
		for (Integer i : defense) {
			ergebnis = ergebnis + i + ", ";
		}
		JOptionPane.showMessageDialog(null, ergebnis);
	}
	//Methode zum Anzeigen
	
}

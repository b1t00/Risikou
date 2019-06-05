package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Attack;
import ris.local.valueobjects.Land;

public class DialogPanel extends JPanel {

	private JLabel titel;
	private JTextArea info;
	private Risiko ris;
	private String aktion;
	
	public DialogPanel(Risiko risiko) {
		ris = risiko;	
		setupUI();
	}
	
	public void setupUI() {
		titel = new JLabel("Info");
		info = new JTextArea();
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setEditable(false);
		info.setText("Herzlich Willkommen zu Risiko!");
		this.setLayout(new GridLayout(2,1));
		this.add(titel);
		this.add(info);
	}
	
	public void update(String aktion) {
		this.aktion = aktion;
		switch(aktion) {
		case "nextPlayer":
			info.setText(ris.gibAktivenPlayer() + " ist am Zug.");
			break;
		case "setUnits":
			info.setText(ris.gibAktivenPlayer() + " setzt neue Einheiten");
			break;
		case "attack":
			info.setText(ris.gibAktivenPlayer() + " greift an.");
			break;
		case "moveUnits":
			info.setText(ris.gibAktivenPlayer() + " verschiebt Einheiten.");
		}
	}
	
	public void update(Attack attackObj) {
		if(attackObj.getWinner().equals(attackObj.getAttacker())) {
			info.setText(attackObj.getAttacker() + " hat mit " + attackObj.getAttLand() + " " + attackObj.getDefLand() + " angegriffen. \n"
				+ "Einheiten Angriff: " + attackObj.getAttUnits().length + "\nEinheiten Defense: " + attackObj.getDefUnits().length +
				".\n" + attackObj.getWinner() + " gewinnt und erobert " + attackObj.getDefLand());
		} else {
			info.setText(attackObj.getAttacker() + " hat mit " + attackObj.getAttLand() + " " + attackObj.getDefLand() + " angegriffen.\n"
					+ "Einheiten Angriff: " + attackObj.getAttUnits().length + "\nEinheiten Defense: " + attackObj.getDefUnits().length +
					"\n" + attackObj.getAttacker() + " verliert den Kampf.");
		}
	}
	
	public void update(Land land1, Land land2, int number) {
		info.setText(ris.gibAktivenPlayer() + " verschiebt " + number + " Einheiten von " + land1 + " nach " + land2 + ".");
	}
}

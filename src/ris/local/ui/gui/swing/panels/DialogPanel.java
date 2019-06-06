package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

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
		JScrollPane scrollPane = new JScrollPane(info);
		info.setText("Herzlich Willkommen zu Risiko! \n");
		this.setLayout(new GridLayout(2,1));
		this.add(titel);
		this.add(scrollPane);
		
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,3));
	}
	
	//text anhaengen bei phasenuebergang
	public void update(String aktion) {
		this.aktion = aktion;
		switch(aktion) {
		case "nextPlayer":
			info.append(ris.gibAktivenPlayer() + " ist am Zug. \n");
			break;
		case "setUnits":
			info.append(ris.gibAktivenPlayer() + " setzt neue Einheiten \n");
			break;
		case "attack":
			info.append(ris.gibAktivenPlayer() + " greift an. \n");
			break;
		case "moveUnits":
			info.append(ris.gibAktivenPlayer() + " verschiebt Einheiten. \n");
		}
	}
	
	//text anhaengen bei angriff
	public void update(Attack attackObj) {
		if(attackObj.getWinner().equals(attackObj.getAttacker())) {
			info.append(attackObj.getAttacker() + " hat mit " + attackObj.getAttLand() + " " + attackObj.getDefLand() + " angegriffen. \n"
				+ "Einheiten Angriff: " + attackObj.getAttUnits().length + "\nEinheiten Defense: " + attackObj.getDefUnits().length +
				".\n" + attackObj.getWinner() + " gewinnt und erobert " + attackObj.getDefLand() + " \n");
		} else {
			info.append(attackObj.getAttacker() + " hat mit " + attackObj.getAttLand() + " " + attackObj.getDefLand() + " angegriffen.\n"
					+ "Einheiten Angriff: " + attackObj.getAttUnits().length + "\nEinheiten Defense: " + attackObj.getDefUnits().length +
					"\n" + attackObj.getAttacker() + " verliert den Kampf. \n");
		}
	}
	
	//text anhaengen bei verschieben von einheiten
	public void update(Land land1, Land land2, int number) {
		if(number == 1) {
			info.append(ris.gibAktivenPlayer() + " verschiebt eine Einheit von " + land1 + " nach " + land2 + "\n");			
		} else {
			info.append(ris.gibAktivenPlayer() + " verschiebt " + number + " Einheiten von " + land1 + " nach " + land2 + "\n");
		}
	}
	
	//text anhaengen bei set units
	public void update(Land land) {
		info.append(ris.gibAktivenPlayer() + " setzt eine Einheit auf " + land + "\n");
	}
}

package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Attack;

public class DialogPanel extends JPanel {

	private JLabel titel;
	private JLabel info;
	private Risiko ris;
	private String aktion;
	
	public DialogPanel(Risiko risiko) {
		ris = risiko;	
		setupUI();
	}
	
	public void setupUI() {
		titel = new JLabel("Info");
		info = new JLabel("Herzlich Willkommen zu Risiko!");
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
			//attack objekt?
//			info = new JLabel()
		}
	}
	
	public void update(Attack attackObj) {
		info.setText(attackObj.getAttacker() + " hat mit " + attackObj.getAttLand() + " " + attackObj.getDefLand() + " angegriffen.");
	}
}

package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SetUnitsPanel extends JPanel {
	
	public interface SetUnitsListener {
		//methods
	}
	
	private SetUnitsListener listener;
	private int units;
//	private Risiko ris;
	private JLabel titel = new JLabel("Set units");
	private JLabel info = new JLabel("Setze insgesamt: " + units + " Einheiten. (entsprechendes Land anklicken)");
	
	public SetUnitsPanel (SetUnitsListener sul, int einheiten) {
		listener = sul;
		this.units = einheiten;
//		ris = risiko
		
		setupUI();
	}

	public void setupUI() {
		this.setLayout(new GridLayout(4, 1));
		this.add(titel);
		this.add(info);
	}
	
	//eventuell überflüssig, die GUI kann jedes Mal ein neues Panel mit neuer Units Zahl erstellen.
	public void decrementUnits() {
		units --;
	}
	
}

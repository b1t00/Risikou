package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;

public class SetUnitsPanel extends JPanel {
	
	public interface SetUnitsListener {
		//methods
	}
	
	private SetUnitsListener listener;
	private int units;
	private Risiko ris;
	private JLabel titel = new JLabel("Set units");
	private JLabel info;
	
	public SetUnitsPanel (int einheiten, Risiko risiko) {
//		listener = sul;
		this.units = einheiten;
		ris = risiko;
		
		setupUI();
	}

	public void setupUI() {
		this.setLayout(new GridLayout(4, 1));
		info =  new JLabel("Setze insgesamt: " + units + " Einheiten. (entsprechendes Land anklicken)");
		this.add(titel);
		this.add(info);
	}
	
	//eventuell überflüssig, die GUI kann jedes Mal ein neues Panel mit neuer Units Zahl erstellen.
	public void decrementUnits() {
		units --;
	}
	
	public int getVerfuegbareEinheiten() {
		return units;
	}
	
	public void update() {
		info.setText("Setze insgesamt: " + units + " Einheiten. (entsprechendes Land anklicken)");
	}
}

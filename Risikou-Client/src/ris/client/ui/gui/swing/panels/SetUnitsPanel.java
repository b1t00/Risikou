package ris.client.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import ris.common.interfaces.RisikoInterface;

public class SetUnitsPanel extends JPanel {
	
	private int units;
	private RisikoInterface ris;
	private JLabel titel = new JLabel("Set units");
	private JTextArea info;
	
	public SetUnitsPanel (int einheiten, RisikoInterface risiko) {
		this.units = einheiten;
		ris = risiko;
		
		setupUI();
	}

	public void setupUI() {
		this.setLayout(new GridLayout(4, 1));
		
		info = new JTextArea();
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setEditable(false);
		info.setText(ris.gibAktivenPlayer() + ": Setze insgesamt: " + units + " Einheiten. (entsprechendes Land anklicken)");
		this.add(titel);
		this.add(info);
		
		setBorder(new LineBorder(ris.getColorArray().get(ris.gibAktivenPlayer().getNummer()), 5));
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

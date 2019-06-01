package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;

public class InfoPanel extends JPanel{

	private Risiko ris;
	
	private JLabel name;
	private JLabel farbe = new JLabel("Farbe: blau");
	private JLabel mission = new JLabel("Deine Mission: Erobere rot");
//	private JLabel laender;
	
	public InfoPanel(Risiko ris) {
		this.ris = ris;
		
		name = new JLabel("Name: " + ris.gibAktivenPlayer());
		
		setupUI();
	}
	
	public void setupUI() {
		this.setLayout(new GridLayout(3,2));
		this.setSize(600,200);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(name);
		this.add(farbe);
		this.add(mission);
//		this.add(laender);
	}
}

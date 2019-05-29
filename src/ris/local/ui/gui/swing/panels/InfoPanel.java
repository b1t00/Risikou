package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel{

	
	private JLabel name = new JLabel("Name: Annie");
	private JLabel farbe = new JLabel("Farbe: blau");
	private JLabel mission = new JLabel("Deine Mission: Erobere rot");
	
	public InfoPanel() {
		setupUI();
	}
	
	public void setupUI() {
		this.setLayout(new GridLayout(3,1));
		this.setSize(600,200);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(name);
		this.add(farbe);
		this.add(mission);
	}
}

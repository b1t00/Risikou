package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;

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
		this.add(name);
		this.add(farbe);
		this.add(mission);
	}
}

package ris.local.ui.gui.swing.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.ui.gui.RisikoClientGUI;

public class InfoPanel extends JPanel {

	private Risiko risiko;

	private RisikokartenPanel risikoKartenTPl;
	private SpielerDetailsPanel spielerDetailsPl;
	
	public InfoPanel(Risiko ris) {
		this.risiko = ris;
		setupUI();
	}

	public void setupUI() {
		layOutSetUp();
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		this.add(name);
		this.add(farbe);
		this.add(mission);
		System.out.println(risiko.gibAktivenPlayer());
//		System.out.println(risiko.gibAktivenPlayer().getEinheitenkarten());

	}

	public void layOutSetUp() {

		setLayout(new BorderLayout());
		spielerDetailsPl = new SpielerDetailsPanel(risiko);
		spielerDetailsPl.setPreferredSize(new Dimension(400, this.getHeight()));
//		spielerDetailsPl.set
		spielerDetailsPl.setBackground(Color.blue);
		add(spielerDetailsPl, BorderLayout.WEST);
		
//		risikoKartenTPl = new RisikokartenPanel(risiko,RisikoClientGUI client);
//		risikoKartenTPl.setPreferredSize(new Dimension(500, this.getHeight()));
//		add(risikoKartenTPl, BorderLayout.CENTER);
	}
	
	//funktion um Heigth auszugeben
	public int getInfoHeight() {
		return this.getHeight();
	}
	
}

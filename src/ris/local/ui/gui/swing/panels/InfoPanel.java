package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;

public class InfoPanel extends JPanel {

	private Risiko risiko;

	private JLabel name;
	private JLabel farbe = new JLabel("Farbe: blau");
	private JLabel mission = new JLabel("Deine Mission: Erobere rot");
	private RisikokartenPanel risikoKartenTPl;
	private JButton karteBtn;
//	private JLabel laender;

	public InfoPanel(Risiko ris) {
		this.risiko = ris;
		name = new JLabel("Name: " + ris.gibAktivenPlayer());
		setupUI();
	}

	public void setupUI() {
		layOutSetUp();

		this.setBorder(BorderFactory.createLineBorder(Color.black));

//		if(risiko.gibAktivenPlayer().getEinheitenkarten() == null) {
		risikoKartenTPl = new RisikokartenPanel(risiko);
		add(risikoKartenTPl);
		
//		}
//		} else if {
//		risikoKartenTPl = new RisikokartenTauschPanel(risiko, risiko.gibAktivenPlayer().getEinheitenkarten().get(0));
//		}

	}

	public void layOutSetUp() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.NONE;

		// 1.Spalte 1. Reihe/////////////////////////////////////////

		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 0;
		gc.gridy = 0;

		gc.anchor = GridBagConstraints.CENTER;

		add(name, gc);

		// 1.Spalte 2. Reihe /////////////////////////////////////////

		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 0;
		gc.gridy = 1;

		gc.anchor = GridBagConstraints.CENTER;

		add(farbe, gc);

		// 1.Spalte 3. Reihe/////////////////////////////////////////

		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 0;
		gc.gridy = 2;

		gc.anchor = GridBagConstraints.CENTER;

		add(mission, gc);

		// 2.Spalte 1. Reihe/////////////////////////////////////////

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = 1;
		
		gc.anchor = GridBagConstraints.CENTER;
		karteBtn = new JButton("karte");
		karteBtn.setPreferredSize(new Dimension(100,100));
//		add(karteBtn, gc);
	}
}

package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ris.local.domain.Risiko;

public class SpielerDetailsPanel extends JPanel {
	private Risiko risiko;

	private JLabel name;
	private JLabel farbe;
	private String farbAuswahl;
	private JLabel mission;
	private RisikokartenPanel risikoKartenTPl;
	

	public SpielerDetailsPanel(Risiko ris) {
		this.risiko = ris;
//		farbAuswahl = risiko.gibAktivenPlayer().getFarbe());
		name = new JLabel("Name: " + risiko.gibAktivenPlayer());
		farbe = new JLabel("Farbe: " + farbAuswahl); // TODO: Farbe als Enums?
		mission = new JLabel(
				"<html><center>" + "Deine Mission: <br>" + risiko.gibAktivenPlayer().getMission() + "</center></html>");
		setupUI();
	}

	public void setupUI() {
		layOutSetUp();
		
//		this.setBorder(BorderFactory.createEtchedBorder());
		
		//		this.setBackground(Color.lightGray); //TODO: Farbuebergabe
		 update();
	}

	public void update() {
		name.setText("Name: " + risiko.gibAktivenPlayer());
		farbe.setText("Farbe: " + risiko.gibAktivenPlayer().getFarbe());
		mission.setText(
				"<html><center>" + "Deine Mission: <br>" + risiko.gibAktivenPlayer().getMission() + "</center></html>");
		setBorder(new LineBorder(risiko.getColorArray().get(risiko.gibAktivenPlayer().getNummer()), 5));
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

//		gc.weightx = 1;
//		gc.weighty = 1;
//
//		gc.gridx = 1;
//
//		gc.anchor = GridBagConstraints.CENTER;
////		karteBtn = new JButton("karte");
////		karteBtn.setPreferredSize(new Dimension(100, 100));
//		add(karteBtn, gc);
	}
}

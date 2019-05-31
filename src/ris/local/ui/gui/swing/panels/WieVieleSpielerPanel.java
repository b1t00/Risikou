package ris.local.ui.gui.swing.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ris.local.ui.gui.RisikoClientGUI;

public class WieVieleSpielerPanel extends JPanel {

	private JTextField anzahlSpielertextfield;

	private JButton starteSpiel;
	private JButton zurueck;
	private RisikoClientGUI client;

	int anzahlSpieler = 0;

	public WieVieleSpielerPanel(RisikoClientGUI client) {
		this.client = client;
		Dimension size = this.getPreferredSize();
		size.width = 850;
		this.setPreferredSize(size);
		setup();

	}

	private void setup() {
		//*****Layout*****
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		//// 1. Line 1. Column ///////////////////

		gc.weightx = 0.5;
		gc.weighty = 0.5;

		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 0;

		this.add(new JLabel("Wieviele Spieler? (2 bis 6) :"), gc);

		//// 1. Line 2. Column ///////////////////

		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 1;
		gc.gridy = 0;

		anzahlSpielertextfield = new JTextField(10);
		this.add(anzahlSpielertextfield, gc);

		//// 2. Line 1. Column ///////////////////

		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0;
		gc.gridy = 1;

		zurueck = new JButton("Zurück");
		this.add(zurueck, gc);
		zurueck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.showLoginPanel();
			}
		});

		//// 2. Line 2. Column ///////////////////

		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 1;
		gc.gridy = 1;

		starteSpiel = new JButton("starte Spiel");
		starteSpiel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String inputText = anzahlSpielertextfield.getText();
				try {
					anzahlSpieler = Integer.parseInt(inputText);
					// ... start new session
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Uh-oh!", inputText + " ist keine gültige Nummer",
							JOptionPane.ERROR_MESSAGE);
				}
				if (anzahlSpieler > 1 && anzahlSpieler < 7) {
					
					client.showNeuerSpielerPanel();
				} else {
					JOptionPane.showMessageDialog(null, "Nur 2 bis 6 Spieler Moeglich");
				}

			}



		});
		this.add(starteSpiel, gc);
		

	}
	public void addGehtNicht() {
		JLabel gehtNet = new JLabel("Geht nicht");
		this.add(gehtNet);

	}

	public int getAnzahlSpieler() {
		return anzahlSpieler;
	}

}


package ris.local.ui.gui.swing.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ris.local.domain.Risiko;
import ris.local.ui.gui.RisikoClientGUI;

public class NeuerSpielerPanel extends JPanel {

	JTextField nameField;
	JButton hinzufuegen;
	JComboBox<String> farbauswahlCB;
	RisikoClientGUI client;
	int x;

	private Risiko risiko;

	String test;

	public NeuerSpielerPanel(Risiko risiko, RisikoClientGUI client) {
		this.risiko = risiko;
		this.client = client;

		this.setIgnoreRepaint(false);
		x = risiko.getPlayerArray().size() + 1;


//		x = client.getSpielerAnzahl();

//		Dimension size = this.getPreferredSize();
//		size.width = 200;
//		this.setPreferredSize(size);
//		this.revalidate();
//		this.repaint();

		test = "test";
		nameField = new JTextField(20);

		String[] farbListe = risiko.getFarbauswahl().toArray(new String[risiko.getFarbauswahl().size()]);
		farbauswahlCB = new JComboBox<String>(farbListe);

		hinzufuegen = new JButton("hinzufuegen");
		System.out.println("hey");
		hinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("ho");
				String name = nameField.getText();
				String farbe = (String) farbauswahlCB.getSelectedItem();
				farbe = risiko.setFarbeAuswaehlen(farbe);
				System.out.println(risiko.getFarbauswahl());
				if (x < client.getSpielerAnzahl()) {
					risiko.playerAnlegen(name, farbe, x);
					System.out.println("hier gucken playerAnlegen :" + x);
					x++;
					
					

					client.showNeuerSpielerPanel();
					revalidate();
					repaint();

					test = "zwei";
				}
				if (x == client.getSpielerAnzahl()) {
					risiko.verteileEinheiten();
					risiko.verteileMissionen();
					risiko.whoBegins();
					client.showGamePanel();
				}
				System.out.println(risiko.getPlayerArray());
				for (int i = 0; i < risiko.getPlayerArray().size(); i++) {
					System.out.println(risiko.getPlayerArray().get(i).getFarbe());
					System.out.println(risiko.getPlayerArray().get(i).getNummer());
				}
			}
		});

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		//// 1. Line 1. Column ///////////////////

		gc.weightx = 0.2;
		gc.weighty = 0.8;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.gridy = 0;

		this.add(new JLabel("Name von Spieler " + x), gc);
		System.out.println("hier gucken x JLabel: " + x);

		//// 1. Line 2. Column ///////////////////

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;

		this.add(nameField, gc);

		//// 2. Line 1. Column ///////////////////

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.gridy = 1;

		this.add(new JLabel("Farbe"), gc);

		//// 2. Line 2. Column ///////////////////

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 1;

		this.add(farbauswahlCB, gc);

		gc.anchor = GridBagConstraints.CENTER;

		gc.gridy = 2;

		this.add(hinzufuegen, gc);
	}

}

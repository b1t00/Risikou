
package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ris.local.domain.Risiko;
import ris.local.ui.gui.RisikoClientGUI;

public class NeuerSpielerPanel extends JPanel {
	private JLabel nameLabel;
	private JTextField nameField;
	private JButton hinzufuegen;
	private JComboBox<String> farbauswahlCB;
	private RisikoClientGUI client;
	private int x;

	private Risiko risiko;

	String test;

	public NeuerSpielerPanel(Risiko risiko, RisikoClientGUI client) {
		this.risiko = risiko;
		this.client = client;

		this.setIgnoreRepaint(false);

		x = 0;

		nameLabel = new JLabel("Name von Spieler " + (x + 1));

		test = "test";
		nameField = new JTextField(10);

		String[] farbListe = risiko.getFarbauswahl().toArray(new String[risiko.getFarbauswahl().size()]); // moeglichkeit
																											// ArrayList<String>
																											// zu einem
																											// String[]
																											// array
																											// umzuschreiben
		farbauswahlCB = new JComboBox<String>(farbListe); // ComboBox braucht String[] Array
		farbauswahlCB.setSelectedIndex(x);

		hinzufuegen = new JButton("hinzufuegen");

		hinzufuegen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String name = nameField.getText();
				String farbe = (String) farbauswahlCB.getSelectedItem();
				farbe = risiko.setFarbeAuswaehlen(farbe);
				colorArraySetzten(farbe);
				int farbIndex = farbauswahlCB.getSelectedIndex();
				System.out.println("name : " + name);
				System.out.println(risiko.getFarbauswahl() + " funktioniert leider nicht"); // TODO: geht leider net
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "Du solltest schon einen Namen auswahlen");
				} else if (x < client.getSpielerAnzahl()) {

					risiko.playerAnlegen(name, farbe, x);

					nameField.setText("");
					x++;
					nameLabel.setText("Name von Spieler " + (x + 1));
//					nameLabel.revalidate();
//					nameLabel.repaint();
					farbauswahlCB.removeItemAt(farbIndex);
					client.showNeuerSpielerPanel();

				}
				if (x == client.getSpielerAnzahl()) {
					// hier startet das Spiel, (kein Spieler wird mehr hinzugefuegt)
					risiko.verteileEinheiten(); // Einheiten werden verteilt
					risiko.verteileMissionen(); // missionen werden verteilt
					risiko.setzeAktivenPlayer(); // der erste Spieler wird berechnet
					client.showGamePanel();
				}
				// TODO: Sysos sind nur zum testen. koennen weg
//				System.out.println(risiko.getPlayerArray());
//				for (int i = 0; i < risiko.getPlayerArray().size(); i++) {
//					System.out.println(risiko.getPlayerArray().get(i).getFarbe());
//					System.out.println(risiko.getPlayerArray().get(i).getNummer());
//				}
			}
		});

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		//// 1. Line 1. Column ///////////////////

		gc.weightx = 0.6;
		gc.weighty = 0.4;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.gridy = 0;

		this.add(nameLabel, gc);
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

		gc.anchor = GridBagConstraints.LINE_START;

		gc.gridy = 2;
		gc.gridx = 1;

		this.add(hinzufuegen, gc);
	}

	public void colorArraySetzten(String farbe) {
		switch (farbe) {
		case "rot":
			risiko.setColorArray(new Color(226, 19, 43));
			break;
		case "gruen":
			risiko.setColorArray(new Color(23, 119, 50));
			break;
		case "blau":
			risiko.setColorArray(new Color(30, 53, 214));
			break;
		case "pink":
			risiko.setColorArray(new Color(255, 51, 245));
			break;
		case "weiss":
			risiko.setColorArray(new Color(255, 255, 255));
			break;
		case "schwarz":
			risiko.setColorArray(new Color(0, 0, 0));
			break;
		}

	}

}

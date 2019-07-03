package ris.client.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ris.client.ui.gui.RisikoClientGUI;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.interfaces.RisikoInterface;

public class NeuerSpielerPanel extends JPanel {
	private JLabel nameLabel;
	private JTextField nameField;
	private JButton hinzufuegen;
	private JComboBox<String> farbauswahlCB;
	private RisikoClientGUI client;
	private int spielerNr;

	private RisikoInterface risiko;

	String test;

	public NeuerSpielerPanel(RisikoInterface risiko, RisikoClientGUI client) {
		this.risiko = risiko;
		this.client = client;

		this.setIgnoreRepaint(false);
		spielerNr = risiko.getPlayerArray().size() + 1;
		
		nameLabel = new JLabel("Name von Spieler " + spielerNr);

		test = "test";
		nameField = new JTextField(10);

		ArrayList<String> farben = risiko.getFarbauswahl();
		for(int i = 0 ; i < farben.size() ; i++) {
			if(farben.get(i) == null) {
				farben.remove(i);
			}
		}
		
		String[] farbListe = farben.toArray(new String[farben.size()]); // moeglichkeit ArrayList<String> zu einem Strin[] array umzuschreiben
		farbauswahlCB = new JComboBox<String>(farbListe); // ComboBox braucht String[] Array
		farbauswahlCB.setSelectedIndex(spielerNr);

		hinzufuegen = new JButton("hinzufuegen");

		hinzufuegen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String name = nameField.getText();
				String farbe = (String) farbauswahlCB.getSelectedItem();
//				farbe = risiko.setFarbeAuswaehlen(farbe);
//				colorArraySetzten(farbe);
				int farbIndex = farbauswahlCB.getSelectedIndex();
				System.out.println("name : " + name);
				risiko.setFarbeAuswaehlen(farbe);
				if (name.equals("")) {
					//TODO: falls name schon vergeben ist
					JOptionPane.showMessageDialog(null, "Du solltest schon einen Namen auswaehlen");
				} else {
					try {
						risiko.playerAnlegen(name, farbe, (spielerNr-1));
					} catch (SpielerNameExistiertBereitsException e1) {
						JOptionPane.showMessageDialog(null, e1.getLocalizedMessage());
						System.out.println(e1);
						nameField.setText("");
						return;
					}
					client.setSpieler(name, (spielerNr-1));
					System.out.println("player angelegt!");
					hinzufuegen.setText("wartet auf nächsten spieler");	
					hinzufuegen.setEnabled(false);

//					farbauswahlCB.removeItemAt(farbIndex);
//					if(spielerNr == 1) {
//					client.spielEintreitenBtn();
//					}

				}

				//				if (x == client.getSpielerAnzahl()) {
//					// hier startet das Spiel, (kein Spieler wird mehr hinzugefuegt)
////					im Spielaufbau werden Einheiten und Missionen verteilt sowie der erste Spieler bestimmt
//					risiko.spielAufbau();
//					System.out.println("Nummer: " + risiko.gibAktivenPlayer().getNummer());
//					client.showGamePanel();
//				}
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
		System.out.println("hier gucken x JLabel: " + spielerNr);

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
}

package ris.client.ui.gui.swing.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Player;

public class SpielerDetailsPanel extends JPanel {
	private RisikoInterface risiko;

	private JLabel name;
	private JLabel farbe;
	private String farbAuswahl;
	private JLabel mission;
	private RisikokartenPanel risikoKartenTPl;
	private String spielername;
	

	public SpielerDetailsPanel(RisikoInterface ris, String spielername) {
		this.risiko = ris;
		this.spielername = spielername;
		setupUI();
	}

	public void setupUI() {
		Player dieserPlayer = null;
		ArrayList<Player> allePlayer = risiko.getPlayerArray();
		for(Player player: allePlayer) {
			if (player.getName().equals(spielername)){
				dieserPlayer = player;
			}
		}
		name = new JLabel("Name: " + dieserPlayer.getName());
		farbe = new JLabel("Farbe: " + dieserPlayer.getFarbe()); // TODO: Farbe als Enums?
		mission = new JLabel(
				"<html><center>" + "Deine Mission: <br>" + dieserPlayer.getMission() + "</center></html>");
		
		layOutSetUp();
		setBorder(new LineBorder(risiko.getColorArray().get(dieserPlayer.getNummer()), 5));
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
	}
}

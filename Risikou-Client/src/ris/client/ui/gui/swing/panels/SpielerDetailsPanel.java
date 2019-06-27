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
//		farbAuswahl = risiko.gibAktivenPlayer().getFarbe());
		//diese sachen sind jetzt in der setupui methode
//		name = new JLabel("Name: " + spielername);
//		farbe = new JLabel("Farbe: " + farbAuswahl); // TODO: Farbe als Enums?
//		mission = new JLabel(
//				"<html><center>" + "Deine Mission: <br>" + risiko.gibAktivenPlayer().getMission() + "</center></html>");
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
//		this.setBorder(BorderFactory.createEtchedBorder());
		
//		//		this.setBackground(Color.lightGray); //TODO: Farbuebergabe
//		 update();
	}

//	wird nicht mehr benötigt, da es die ganze zeit gleich bleibt
//	public void update() {
//		name.setText("Name: " + risiko.gibAktivenPlayer());
//		farbe.setText("Farbe: " + risiko.gibAktivenPlayer().getFarbe());
//		mission.setText(
//				"<html><center>" + "Deine Mission: <br>" + risiko.gibAktivenPlayer().getMission() + "</center></html>");
//		setBorder(new LineBorder(risiko.getColorArray().get(risiko.gibAktivenPlayer().getNummer()), 5));
//	}

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

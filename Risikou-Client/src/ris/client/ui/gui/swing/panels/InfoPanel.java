package ris.client.ui.gui.swing.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ris.common.interfaces.RisikoInterface;

public class InfoPanel extends JPanel {

	private RisikoInterface risiko;

	private RisikokartenPanel risikoKartenTPl;
	private SpielerDetailsPanel spielerDetailsPl;
	private SpielerUebersichtsPanel spielerUebersichtsPl;
	private String name;
	
	public InfoPanel(RisikoInterface ris, String name) {
		this.risiko = ris;
		this.name = name;
		System.out.println("im infopanel " + name);
		setupUI();
	}

	public void setupUI() {
		layOutSetUp();
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,3));
	}

	public void layOutSetUp() {

		setLayout(new BorderLayout());
		spielerDetailsPl = new SpielerDetailsPanel(risiko, name);
		spielerDetailsPl.setPreferredSize(new Dimension(400, this.getHeight()));
		spielerDetailsPl.setBackground(Color.lightGray);
		add(spielerDetailsPl, BorderLayout.WEST);
		
		spielerUebersichtsPl = new SpielerUebersichtsPanel(risiko);
		spielerUebersichtsPl.setPreferredSize(new Dimension(200, this.getHeight()));
		spielerUebersichtsPl.setBackground(Color.ORANGE);
		add(spielerUebersichtsPl, BorderLayout.EAST);
//		risikoKartenTPl = new RisikokartenPanel(risiko,RisikoClientGUI client);
//		risikoKartenTPl.setPreferredSize(new Dimension(500, this.getHeight()));
//		add(risikoKartenTPl, BorderLayout.CENTER);
	}
	
	//funktion um Heigth auszugeben
	public int getInfoHeight() {
		return this.getHeight();
	}
	
	public void setupUebersicht() {
		spielerUebersichtsPl.setupUI();
	}
	
	public void update() {
//		spielerDetailsPl.update();
		spielerUebersichtsPl.update();
	}
	
}

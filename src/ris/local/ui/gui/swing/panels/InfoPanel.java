package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;

public class InfoPanel extends JPanel {

	private Risiko risiko;

	private JLabel name;
	private JLabel farbe = new JLabel("Farbe: blau");
	private JLabel mission = new JLabel("Deine Mission: Erobere rot");
	private RisikokartenTauschPanel risikoKartenTPl;
//	private JLabel laender;

	public InfoPanel(Risiko ris) {
		this.risiko = ris;

		name = new JLabel("Name: " + ris.gibAktivenPlayer());

		setupUI();
	}

	public void setupUI() {
		

//		this.add(farbe);
//		this.add(mission);
		System.out.println(risiko.gibAktivenPlayer());
		System.out.println(risiko.gibAktivenPlayer().getEinheitenkarten());
//		if(risiko.gibAktivenPlayer().getEinheitenkarten() == null) {
		risikoKartenTPl = new RisikokartenTauschPanel(risiko);
		add(risikoKartenTPl);
//		}
//		} else if {
//		risikoKartenTPl = new RisikokartenTauschPanel(risiko, risiko.gibAktivenPlayer().getEinheitenkarten().get(0));
//		}
//		this.add(laender);

		//// 1. Line 1. Column ///////////////////
		this.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

		GridBagLayout gc = new GridBagLayout();
		this.setLayout(gc);
		
//		gc.weightx = 0.8;
//		gc.weighty = 0.2;
//		
//		gc.anchor = GridBagConstraints.LINE_END;
//		gc.gridx = 0;
//		gc.gridy = 0;

		

		this.add(name, gc);
		System.out.println("hier gucken x JLabel: " + x);

		//// 1. Line 2. Column ///////////////////

//		gc.anchor = GridBagConstraints.LINE_START;
//		gc.gridx = 1;
//		gc.gridy = 0;
//
//		this.add(nameField, gc);

		//// 2. Line 1. Column ///////////////////

	}
	public void layoutSetzten() {
		
		
		
		
	}
}

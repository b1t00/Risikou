package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;

public class InfoPanel extends JPanel{

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
		GridBagLayout gb = new GridBagLayout();
		this.setLayout(gb);
		this.setSize(600,400);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		this.add(name);
		this.add(farbe);
		this.add(mission);
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
	}	
}

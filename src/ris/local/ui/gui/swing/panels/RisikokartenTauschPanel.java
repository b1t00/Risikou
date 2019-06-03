package ris.local.ui.gui.swing.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Risikokarte;

public class RisikokartenTauschPanel extends JPanel {

	private JButton kartenButton;
	private Risikokarte karte;
	private Risiko risiko;
	
	public RisikokartenTauschPanel(Risiko risk) {
		kartenButton = new JButton("Leer");
		kartenButton.setBackground(Color.GRAY);
		
	}
	public RisikokartenTauschPanel(Risiko risk, Risikokarte karte) {

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(20, 10));
		if (karte != null) {
			this.karte = karte;
			kartenButton = new JButton(karte.getSymbol().toString());
		} else if (karte == null) {
			kartenButton = new JButton("Leer");
			kartenButton.setBackground(Color.GRAY);
		}
		add(kartenButton, BorderLayout.CENTER);

	}
}

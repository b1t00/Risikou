package ris.client.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import ris.common.interfaces.RisikoInterface;

public class PausePanel extends JPanel {
	private RisikoInterface ris;
	private JLabel titel = new JLabel("Pause");
	private JTextArea info;
	private int spielerNummer;
	
	//bekommt id, damit die richtige Farbe angezeigt werden kann
	public PausePanel (int iD, RisikoInterface risiko) {
		this.setLayout(new GridLayout(2, 1));
		spielerNummer = iD;
		ris = risiko;
		info = new JTextArea();
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setEditable(false);
		
		if(spielerNummer == ris.gibAktivenPlayer().getNummer()) {
			info.setText("Warte auf Verteidigung!");
		} else {
			info.setText("Du bist gerade nicht an der Reihe...");
		}
		
		this.add(titel);
		this.add(info);
		
		setBorder(new LineBorder(ris.getColorArray().get(spielerNummer), 5));
	}
	
	
}

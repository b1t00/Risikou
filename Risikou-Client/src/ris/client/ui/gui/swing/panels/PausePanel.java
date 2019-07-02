package ris.client.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import ris.common.interfaces.RisikoInterface;

public class PausePanel extends JPanel {
	private JLabel titel = new JLabel("Pause");
	private JTextArea info;
	
	//bekommt id, damit die richtige Farbe angezeigt werden kann
	public PausePanel (int iD, RisikoInterface ris) {
		this.setLayout(new GridLayout(2, 1));
		info = new JTextArea();
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setEditable(false);
		info.setText("Du bist gerade nicht an der Reihe...");
		this.add(titel);
		this.add(info);
		setBorder(new LineBorder(ris.getColorArray().get(iD), 5));
	}	
}

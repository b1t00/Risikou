package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ris.local.domain.Risiko;

public class AttackPanel extends JPanel{
	
	public interface AttackListener {
		//Methoden
	}
	
	private Risiko risiko = null;
	private AttackListener listener = null;
	
	private JLabel label = new JLabel("Attack");
	private JLabel abfrage = new JLabel("Möchtest du angreifen?");
//	abfrage.setHorizontalAlignment(JLabel.Left);
	private JButton yesButton = new JButton("Ja");
	private JButton noButton = new JButton("Nein");
	
	this.setLayout(new GridLayout(4, 1));
	this.add(label);
	this.add(abfrage);
	this.add(yesButton);
	this.add(noButton);
	
	this.setSize(640, 480);
	this.setVisible(true);
	
}

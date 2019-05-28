package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ris.local.domain.Risiko;

public class UnitNumberPanel extends JPanel {

	public interface UnitNumberListener{
		public void numberLogged(int number, UnitNumber un);
	}
	
	//besser: in turn implementieren
	public enum UnitNumber{
		ATTACK,
		DEFENSE,
		MOVE
	}
	
	private Risiko ris;
	private UnitNumberListener listener;
	private JLabel titel;
	private JLabel frage;
	private JTextField numberTextField = new JTextField();
	private UnitNumber unitNumber;
	private JButton logButton;
	
	public UnitNumberPanel(UnitNumberListener unl, UnitNumber un) {
		listener = unl;
		unitNumber = un;
		
//		ris = risiko;
		
		setupUI();
		setupEvents();
	}
	
	
	//hier switchcase von ris.getCurrentState -> für Defense: if aktiverPlayer == eigener Player
	//TODO: wie funktioniert das?
	public void setupUI(){
		switch(ris.getCurrentState()) {
		case ATTACK:
			titel = new JLabel("Attack");
			frage = new JLabel("Mit vielen Einheiten soll angegriffen werden?");
			logButton = new JButton("Angreifen!");
			break;
		case DEFENSE:
			titel = new JLabel("Attack");
			//muss hier die info übermittelt werden, wie viele Einheiten angreifen?
			frage = new JLabel("Mit wie vielen Einheiten soll verteidigt werden?");
			logButton = new JButton("Verteidigen!");
			break;
		case MOVE:
			titel = new JLabel("Move units");
			frage = new JLabel("Wie viele Einheiten sollen verschoben werden?");
			logButton = new JButton("Verschieben!");
			break;
		}
		
		this.setLayout(new GridLayout(4, 1));
		this.add(titel);
		this.add(frage);
		this.add(numberTextField);
		this.add(logButton);
	}
	
	//ab hier verarbeitung von events
	private void setupEvents() {
		logButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			System.out.println("Event: " + ae.getActionCommand());
			UnitNumberEinloggen();
			}
		});
	}

	private void UnitNumberEinloggen() {
		String number = numberTextField.getText();
	
		if (!number.isEmpty()) {
			try {
				int numberAlsInt = Integer.parseInt(number);
				//unitNumber muss mit übergeben werden, damit die GUI die entsprechende folgende Methode aufrufen kann
				// vielleicht unnötig? GUI kann turn fragen...
				listener.numberLogged(numberAlsInt, unitNumber);
				
			} catch (NumberFormatException nfe) {
				System.err.println("Bitte eine Zahl eingeben.");
			}
		}
	}
}

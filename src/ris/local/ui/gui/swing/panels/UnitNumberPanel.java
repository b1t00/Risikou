package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ris.local.domain.Risiko;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;

public class UnitNumberPanel extends JPanel {

	public interface UnitNumberListener{
		public void numberLogged(int number, UnitNumber un) throws ZuWenigEinheitenNichtMoeglichExeption;
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
	private int safedNumber;
//	private JComboBox <Integer> anzahl;
	
	public UnitNumberPanel(UnitNumberListener unl, UnitNumber un) {
		listener = unl;
		unitNumber = un;
//		ris = risiko;
		
		setupUI();
		setupEvents();
	}

	public void setupUI(){
		switch(unitNumber) {
		case ATTACK:
			titel = new JLabel("Attack");
			frage = new JLabel("Mit vielen Einheiten soll angegriffen werden?");
			logButton = new JButton("Angreifen!");
			break;
		case DEFENSE:
			titel = new JLabel("Attack");
			//muss hier die info �bermittelt werden, wie viele Einheiten angreifen?
			frage = new JLabel("Mit wie vielen Einheiten soll verteidigt werden?");
			logButton = new JButton("Verteidigen!");
			break;
		case MOVE:
			titel = new JLabel("Move units");
			frage = new JLabel("Wie viele Einheiten sollen verschoben werden?");
//			Integer[] zahlen = getM�glicheZahlen;
//			anzahl = new JComboBox<>(zahlen);
			logButton = new JButton("Verschieben!");
//			this.add(anzahl);	
			break;
		}
		
		this.setLayout(new GridLayout(4, 1));
		this.add(titel);
		this.add(frage);
		this.add(numberTextField);
		this.add(logButton);
	}
	
	public int getNumber() {
		return safedNumber;
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
				int numberAsInt = Integer.parseInt(number);
				safedNumber = numberAsInt;
				try {
					listener.numberLogged(numberAsInt, unitNumber);
					numberTextField.setText("");
				} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (NumberFormatException nfe) {
				System.err.println("Bitte eine Zahl eingeben.");
			}
		}
	}
}

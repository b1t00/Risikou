package ris.client.ui.gui.swing.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ris.common.interfaces.RisikoInterface;

public class UnitNumberPanel extends JPanel {

	public interface UnitNumberListener{
		public void numberLogged(int number, UnitNumber un);
	}
	
	//besser: in turn implementieren
	public enum UnitNumber{
		ATTACK,
		MOVEATTACK,
		DEFENSE,
		MOVE
	}
	
	private RisikoInterface ris;
	private int iD;
	private UnitNumberListener listener;
	private JLabel titel;
	private JTextArea frage = new JTextArea();
	private JTextField numberTextField = new JTextField();
	private UnitNumber unitNumber;
	private JButton logButton;
	private int safedNumber;
//	private JComboBox <Integer> anzahl;
	
	public UnitNumberPanel(UnitNumberListener unl, UnitNumber un, RisikoInterface risiko, int iD) {		
		listener = unl;
		unitNumber = un;
		ris = risiko;
		this.iD = iD;
		
		//ermöglicht automatischen Zeilenumbruch
		frage = new JTextArea();
		frage.setLineWrap(true);
		frage.setWrapStyleWord(true);
		frage.setEditable(false);
		
		setupUI();
		setupEvents();
	}

	public void setupUI(){
		switch(unitNumber) {
		case ATTACK:
			titel = new JLabel("Attack");
			frage.setText("Mit vielen Einheiten soll angegriffen werden?");
			logButton = new JButton("Angreifen!");
			break;
		case MOVEATTACK:
			titel = new JLabel("Einheiten nachruecken");
			frage.setText("Wieviele Einheiten sollen nachruecken?");
			logButton = new JButton("Verschieben");
			break;
		case DEFENSE:
			titel = new JLabel("Attack");
			frage.setText("Du wurdest angegriffen! Mit wie vielen Einheiten soll verteidigt werden?");
			logButton = new JButton("Verteidigen!");
			break;
		case MOVE:
			titel = new JLabel("Move units");
			frage.setText("Wie viele Einheiten sollen verschoben werden?");
//			Integer[] zahlen = getMöglicheZahlen;
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
		
		setBorder(new LineBorder(ris.getColorArray().get(iD), 5));
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
				listener.numberLogged(numberAsInt, unitNumber);
				numberTextField.setText("");
				
				
			} catch (NumberFormatException nfe) {
				System.err.println("Bitte eine Zahl eingeben.");
			}
		}
	}
}

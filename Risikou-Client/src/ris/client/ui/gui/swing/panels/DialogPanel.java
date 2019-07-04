package ris.client.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;

import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.Land;

public class DialogPanel extends JPanel {
	
	public interface SpeichernListener {
		public void speichern();
	}

	private SpeichernListener listener;
	private JLabel titel;
	private JTextArea info;
	private RisikoInterface ris;
	private String aktion;
	private JButton speicherButton;
	private int runde = 1; // TO DO: hier könnte man die runden aus risiko Abfragen ansonsten wär das nicht aktuell mit Speicherstand
	
	public DialogPanel(RisikoInterface risiko, SpeichernListener listener) {
		ris = risiko;	
		this.listener = listener;
		
		setupUI();
		setupEvents();
	}
	
	public void setupUI() {
		titel = new JLabel("INFO", SwingConstants.CENTER);
		info = new JTextArea();
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(info);	
		speicherButton = new JButton("Spiel speichern");
		info.setText("Herzlich Willkommen zu Risiko! \n");
		
		this.setLayout(new GridLayout(3,1));
		this.add(titel);
		this.add(scrollPane);
		this.add(speicherButton);
		
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,3));
	}
	
	//text anhaengen bei phasenuebergang
	public void update(String aktion) {
		this.aktion = aktion;
		switch(aktion) {
		case "nextPlayer":
			try {
				info.getDocument().insertString(0, "Runde : " + runde++ + ris.gibAktivenPlayer() + " ist am Zug. \n", null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			info.append(ris.gibAktivenPlayer() + " ist am Zug. \n");
			break;
		case "setUnits":
			try {
				info.getDocument().insertString(0, ris.gibAktivenPlayer() + " setzt neue Einheiten \n", null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			info.append(ris.gibAktivenPlayer() + " setzt neue Einheiten \n");
			break;
		case "attack":
			info.append(ris.gibAktivenPlayer() + " greift an. \n");
			break;
		case "moveUnits":
			info.append(ris.gibAktivenPlayer() + " verschiebt Einheiten. \n");
		}
	}
	
	//text anhaengen bei angriff
	public void update(Attack attackObj) {
		if(attackObj.getWinner().equals(attackObj.getAttacker())) {
			try {
				info.getDocument().insertString(0, attackObj.getAttacker() + " hat mit " + attackObj.getAttLand() + " " + attackObj.getDefLand() + " angegriffen. \n"
					+ "Einheiten Angriff: " + attackObj.getAttUnits().length + "\n Einheiten Defense: " + attackObj.getDefUnits().length +
					".\n" + attackObj.getWinner() + " gewinnt und erobert " + attackObj.getDefLand() + " \n", null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				info.getDocument().insertString(0, attackObj.getAttacker() + " hat mit " + attackObj.getAttLand() + " " + attackObj.getDefLand() + " angegriffen.\n"
						+ "Einheiten Angriff: " + attackObj.getAttUnits().length + "\nEinheiten Defense: " + attackObj.getDefUnits().length +
						"\n" + attackObj.getAttacker() + " verliert den Kampf. \n", null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//text anhaengen bei verschieben von einheiten
	public void update(Land land1, Land land2, int number, String player) {
		if(number == 1) {
			try {
				info.getDocument().insertString(0,player + " verschiebt eine Einheit von " + land1 + " nach " + land2 + "\n",null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			try {
				info.getDocument().insertString(0,player + " verschiebt " + number + " Einheiten von " + land1 + " nach " + land2 + "\n",null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//text anhaengen bei set units
	public void updateSetUnit(String land, String player) {
		try {
			System.out.println("dialogp probiert ein updateLand");
			info.getDocument().insertString(0, player + " setzt eine Einheit auf " + land + "\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void beginAttack(String attacker, String defender) {
		try {
			info.getDocument().insertString(0, attacker + "greift " + defender + " an\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ab hier eventuell auslagern -> wohin?
	public void setupEvents() {
		speicherButton.addActionListener(new AntwortListener());
	}

	
	class AntwortListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent aE) {
			listener.speichern();
		}
	}
}

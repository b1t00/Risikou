package ris.local.ui.gui.swing.panels;

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
import javax.swing.border.LineBorder;

import ris.local.domain.Risiko;

public class QuestionPanel extends JPanel{
	
	public interface QuestionListener {
		//Etwas ungluecklich -> da im attack zwei verschiedene fragen moeglich sind (angreifen oder einheiten nachruecken), muss der eine fall extra bedient werden, daher ueberladung der methoden
		public void answerSelected(boolean answer, String phase);		
		public void answerSelected(boolean answer);
	}
	
	private Risiko ris = null;
	private QuestionListener listener = null;
	private String phase = "";
	

	private JLabel titel = new JLabel("Attack");
	private JTextArea abfrage;
	private JButton yesButton = new JButton("Ja");
	private JButton noButton = new JButton("Nein");
	
	public QuestionPanel(QuestionListener listener, Risiko risiko, String phase) {
		ris = risiko;
		this.listener = listener;
		this.phase = phase;
		
		setupUI();
		setupEvents();
	}
	
	
	public void setupUI() {
		this.setLayout(new GridLayout(4, 1));
		this.setSize(100,400);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		titel.setHorizontalTextPosition(SwingConstants.CENTER);
		this.add(titel);
		
		//ermöglicht automatischen Zeilenumbruch
		abfrage = new JTextArea();
		abfrage.setLineWrap(true);
		abfrage.setWrapStyleWord(true);
		abfrage.setEditable(false);
		//wenn in der Attack Phase gefragt wird, ob Einheiten nachgeholt werden sollen, kann nicht auf das Turn Objekt zugegriffen werden -> eigener Fall
		if (phase.equals("nachruecken")) {
			titel.setText("Einheiten nachruecken");
			abfrage.setText("Moechtest du mit weiteren Einheiten nachruecken?");
		} else {
			switch (ris.getCurrentState()) {
			case SETUNITS:
				titel.setText("CardCombi");
				abfrage.setText("Du kannst Risiko-Karten gegen Einheiten eintauschen! Interesse?");
				break;
			case ATTACK: 
				titel.setText("Attack");
				abfrage.setText(ris.gibAktivenPlayer() + ": Moechtest du angreifen?");
				break;
			case CHANGEUNITS:
				titel.setText("Move units");
				 abfrage.setText(ris.gibAktivenPlayer() + ": Moechtest du Einheiten verschieben?");
				 break;
			}
		}
		
		this.add(abfrage);
		this.add(yesButton);
		this.add(noButton);
		
		setBorder(new LineBorder(ris.getColorArray().get(ris.gibAktivenPlayer().getNummer()), 5));		
	}
	
	public void setupEvents() {
		yesButton.addActionListener(new AntwortListener(true));
		noButton.addActionListener(new AntwortListener(false));
	}

	
	class AntwortListener implements ActionListener {
		//der antwortListener speichert die Antwort mittels Button
		private boolean answer;
		
		public AntwortListener(boolean answer) {
			this.answer = answer;
		}
		
		//und ruft den attackListener (die GUI) auf, die die Antwort weiterverarbeitet
		@Override
		public void actionPerformed(ActionEvent aE) {
			if(phase.equals("nachruecken")) {
				listener.answerSelected(answer, phase);
			} else {
				listener.answerSelected(answer);
			}
		}
	}
	

	
}

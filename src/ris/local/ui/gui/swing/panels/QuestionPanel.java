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

import ris.local.domain.Risiko;

public class QuestionPanel extends JPanel{
	
	public interface QuestionListener {
		public void answerSelected(boolean answer);
	}
	
	private Risiko ris = null;
	private QuestionListener listener = null;
	

	private JLabel titel = new JLabel("Attack");
	//vorher statt JTextArea: JLabel, dann aber kein Zeilenumbruch
	private JTextArea abfrage;
	private JButton yesButton = new JButton("Ja");
	private JButton noButton = new JButton("Nein");
	
	public QuestionPanel(QuestionListener listener, Risiko risiko) {
		ris = risiko;
		this.listener = listener;
		
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
		
		switch (ris.getCurrentState()) {
		case SETUNITS:
			abfrage.setText("Du kannst Risiko-Karten gegen Einheiten eintauschen! Interesse?");
			break;
		case ATTACK: 
			 abfrage.setText(ris.gibAktivenPlayer() + ": Möchtest du angreifen?");
			 break;
		case CHANGEUNITS:
			titel.setText("Move units");
			 abfrage.setText(ris.gibAktivenPlayer() + ": Möchtest du Einheiten verschieben?");
			 break;
		}
		
		this.add(abfrage);
		this.add(yesButton);
		this.add(noButton);
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
			listener.answerSelected(answer);
		}
	}
	

	
}

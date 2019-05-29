package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ris.local.domain.Risiko;

public class QuestionPanel extends JPanel{
	
	public interface QuestionListener {
		public void answerSelected(boolean answer);
	}

//	public enum Question {
//		ATTACK,
//		CHANGEUNITS
//	}
	
	private Risiko ris = null;
	private QuestionListener listener = null;
//	private Question question;
	

	private JLabel titel = new JLabel("Attack");
	private JLabel abfrage;
//	abfrage.setHorizontalAlignment(JLabel.Left);
	private JButton yesButton = new JButton("Ja");
	private JButton noButton = new JButton("Nein");
	
	public QuestionPanel(QuestionListener listener, Risiko risiko) {
		ris = risiko;
		this.listener = listener;
//		this.question = question;
		
		setupUI();
		
		setupEvents();
	}
	
	
	public void setupUI() {
		this.setLayout(new GridLayout(4, 1));
		this.setSize(100,400);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(titel);
		
		switch (ris.getCurrentState()) {
		case SETUNITS:
//			if(ris.changePossible(ris.gibAktivenPlayer())) {
//				abfrage = new JLabel("Möchtest du Risiko-Karten eintauschen?");
//			}
			abfrage = new JLabel("Hier kommt noch eine Abfrage hin");
			break;
		case ATTACK: 
			 abfrage = new JLabel("Möchtest du angreifen?");
			 break;
		case CHANGEUNITS:
			 abfrage = new JLabel("Möchtest du Einheiten verschieben?");
			 break;
		}
		
		this.add(abfrage);
		this.add(yesButton);
		this.add(noButton);
		this.setBackground(Color.GREEN);
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

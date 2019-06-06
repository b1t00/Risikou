package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ris.local.domain.Risiko;

public class EintauschPanel extends JPanel{
	
	public interface EintauschListener {
		public void eintauschButtonClicked(String answer);
	}
	
	private EintauschListener listener;
	private Risiko ris;
	private JLabel titel = new JLabel("Set Units");
	private JButton setzButton = new JButton("Einheiten setzen");
	private JButton tauschButton = new JButton("Risikokarten eintauschen");
	
	public EintauschPanel(EintauschListener listener, Risiko risiko) {
		this.listener = listener;
		ris = risiko;
		setupUI();
		setupEvents();
	}
	
	public void setupUI() {
		this.setLayout(new GridLayout(3,1));
		
		this.add(titel);
		this.add(setzButton);
		this.add(tauschButton);
		
		if(ris.gibAktivenPlayer().changePossible()){
			tauschButton.setEnabled(true);
		} else {
			tauschButton.setEnabled(false);
		}
		
		if(ris.gibAktivenPlayer().mussTauschen()) {
			setzButton.setEnabled(false);
		} else {
			setzButton.setEnabled(true);
		}
		
		setBorder(new LineBorder(ris.getColorArray().get(ris.gibAktivenPlayer().getNummer()), 5));
	}
	
	public void setupEvents() {
		setzButton.addActionListener(new AntwortListener("setzen"));
		tauschButton.addActionListener(new AntwortListener("tauschen"));
	}

	
	class AntwortListener implements ActionListener {
		//der antwortListener speichert die Antwort mittels Button
		private String answer;
		
		public AntwortListener(String answer) {
			this.answer = answer;
		}
		
		//und ruft den attackListener (die GUI) auf, die die Antwort weiterverarbeitet
		@Override
		public void actionPerformed(ActionEvent aE) {
			listener.eintauschButtonClicked(answer);
		}
	}

}

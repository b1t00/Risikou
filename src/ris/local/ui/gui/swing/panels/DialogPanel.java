package ris.local.ui.gui.swing.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;

public class DialogPanel extends JPanel {

	private JLabel titel;
	private JLabel info;
	private Risiko ris;
	
	public DialogPanel(Risiko risiko) {
		ris = risiko;
		
		setupUI();
	}
	
	public void setupUI() {
		switch(ris.getCurrentState()) {
		case ATTACK:
			//TODO: alle fälle:lösung für infos, die noch nicht vermerkt sind!
		}
	}
}

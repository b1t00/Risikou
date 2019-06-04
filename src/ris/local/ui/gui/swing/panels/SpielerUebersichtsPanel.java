package ris.local.ui.gui.swing.panels;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Player;

public class SpielerUebersichtsPanel extends JPanel {

	private Risiko risiko;
	private JLabel player1;
	private JLabel player2;
	private JLabel player3;

	SpielerUebersichtsPanel(Risiko risiko) {
		this.risiko = risiko;
		setupUI();
	}
	
	public void setupUI(){
		ArrayList<Player> alleSpieler = risiko.getPlayerArray();
		for (Player player: alleSpieler) {
//			JLabel 
		}
	}
}

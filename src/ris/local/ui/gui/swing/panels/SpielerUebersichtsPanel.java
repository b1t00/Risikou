package ris.local.ui.gui.swing.panels;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Player;

public class SpielerUebersichtsPanel extends JPanel {

	private Risiko risiko;
	private ArrayList<JLabel> players;
	private JLabel player1;

	public SpielerUebersichtsPanel(Risiko risiko) {
		this.risiko = risiko;
	}
	
	public void setupUI(){
		ArrayList<Player> alleSpieler = risiko.getPlayerArray();
		for (Player player: alleSpieler) {
			System.out.println("in der schleife angekommen");
			players.add(new JLabel(player.getName()));
		}
		
		for(JLabel label: players) {
			this.add(label);
		}
	}
}

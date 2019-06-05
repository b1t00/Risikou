package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Player;

public class SpielerUebersichtsPanel extends JPanel {

	private Risiko risiko;
	private ArrayList<JLabel> playersLbl;
	ArrayList<Player> alleSpieler;

	public SpielerUebersichtsPanel(Risiko risiko) {
		this.risiko = risiko;
		setupUI();
	}
	
	public void setupUI(){
		alleSpieler = risiko.getPlayerArray();
		ArrayList<JLabel> playersLbl = new ArrayList<JLabel>();
		for (Player player: alleSpieler) {
			if(risiko.gibAktivenPlayer().getName().equals(player.getName())) {
				
				playersLbl.add(new JLabel("<html><center><i>" + player.getName() + "<i><center></html>"));
				playersLbl.get(player.getNummer()).setBorder(BorderFactory.createLineBorder(risiko.getColorArray().get(player.getNummer()),3));
				playersLbl.get(player.getNummer()).setBackground(Color.yellow); //TODO: geht net
			} else {
				playersLbl.add(new JLabel("<html>" + player.getName().toString()+ "</html>"));
				playersLbl.get(player.getNummer()).setBorder(BorderFactory.createLineBorder(risiko.getColorArray().get(player.getNummer()),3));
			}
		}
		
		for(JLabel label: playersLbl) {
			this.add(label);
		}
		
		
//		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 50, 5); // Abstand zwischen Buttens
//		setLayout(fl);
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
	}
	public void update() {
		this.removeAll();
		this.revalidate();
		this.repaint();
		setupUI();
		
		
	}
}

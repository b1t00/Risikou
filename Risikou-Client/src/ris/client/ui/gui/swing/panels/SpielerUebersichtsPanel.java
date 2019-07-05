package ris.client.ui.gui.swing.panels;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Player;

public class SpielerUebersichtsPanel extends JPanel {

	private RisikoInterface risiko;
	private JLabel anzeigeUeberschrift;
	private ArrayList<JLabel> playersLbl;
	ArrayList<Player> alleSpieler;

	public SpielerUebersichtsPanel(RisikoInterface risiko) {
		anzeigeUeberschrift = new JLabel("<html>" + "Spielerübersicht" + "</html>");
		anzeigeUeberschrift.setBackground(new Color(216, 135, 47));
		anzeigeUeberschrift.setOpaque(true);
		anzeigeUeberschrift.setForeground(Color.BLACK);
//		anzeigeUeberschrift.invalidate(); //TODO:moeglichkeit suchen, dass jlabel nicht entfernt werden kann
		this.add(anzeigeUeberschrift);
		this.risiko = risiko;
		setupUI();
	}

	public void setupUI() {
		alleSpieler = risiko.getPlayerArray();
		ArrayList<JLabel> playersLbl = new ArrayList<JLabel>();
		for (Player player : alleSpieler) {
			if (risiko.gibAktivenPlayer().getName().equals(player.getName())) {
				playersLbl.add(new JLabel("<html><center><i>" + player.getName() + "<i><center></html>"));
				playersLbl.get(player.getNummer())
						.setBorder(BorderFactory.createLineBorder(risiko.getColorArray().get(player.getNummer()), 3));
				playersLbl.get(player.getNummer()).setOpaque(true);
				playersLbl.get(player.getNummer()).setForeground(Color.WHITE);
				playersLbl.get(player.getNummer()).setBackground(new Color(216, 135, 47));
				// TODO: geht net
			} else {
				playersLbl.add(new JLabel("<html>" + player.getName().toString() + "</html>"));
				playersLbl.get(player.getNummer()).setOpaque(true);
				playersLbl.get(player.getNummer()).setForeground(risiko.getColorArray().get(player.getNummer()));
				playersLbl.get(player.getNummer()).setBackground(new Color(216, 135, 47));
				playersLbl.get(player.getNummer())
						.setBorder(BorderFactory.createLineBorder(risiko.getColorArray().get(player.getNummer()), 3));
			}
		}

		for (JLabel label : playersLbl) {
			this.add(label);
		}
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void update() {
		this.removeAll();
		this.revalidate();
		this.repaint();
		setupUI();

	}
}

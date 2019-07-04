package ris.client.ui.gui.swing.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Player;

public class werBistDuPanle extends JPanel {

	public interface SpielerladenListener {
		public void spielerLaden(String name, int spielerNr);
	}

	private SpielerladenListener listener;
	private JButton ladeSpielerButton = new JButton("beitreten");
	private JList<String> alleSpielerNamen;
	private RisikoInterface risiko;
	private ArrayList<Player> allePlayer;
	private int spielerNr;

	public werBistDuPanle(SpielerladenListener listener, RisikoInterface risiko) {
		this.listener = listener;
		this.risiko = risiko;
		setupUI();
		setupEvents();
	}

	public void setupUI() {
//			System.out.println("Fehler1");
		ArrayList<String> namen = new ArrayList<String>();
		// Arraylist<Player> wird zu String[] umgeschriebn
//		allePlayer = risiko.getGameDatei().getAllePlayer();
		allePlayer = risiko.getPlayerArray();
		for (int i = 0; i < allePlayer.size(); i++) {
			namen.add(risiko.getPlayerArray().get(i).getName());
		}
		String[] playerStringArray = namen.toArray(new String[namen.size()]);
		alleSpielerNamen = new JList<String>(playerStringArray);
		this.add(ladeSpielerButton);
		this.add(alleSpielerNamen);
	}

	public void setupEvents() {
		ladeSpielerButton.addActionListener(new ButtonListener());
	}

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name = alleSpielerNamen.getSelectedValue();
			
			for(Player play : allePlayer) {
				if(play.getName().equals(name)) {
					spielerNr = play.getNummer();			
				};
			}
			
			System.out.println("aktuelle spielernr" +  spielerNr);
			listener.spielerLaden(alleSpielerNamen.getSelectedValue(),spielerNr);
		}
	}

}

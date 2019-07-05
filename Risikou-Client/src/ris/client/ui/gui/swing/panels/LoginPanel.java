package ris.client.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ris.client.ui.gui.RisikoClientGUI;
/*
 * Panel fuer den Login
 */
public class LoginPanel extends JPanel {

	private JButton spielStartenBtn;
	private JButton spielLadenBtn;
	private RisikoClientGUI gui;

	public LoginPanel(RisikoClientGUI client) {
		this.gui = client;
		spielStartenBtn = new JButton("Neues Spiel beginnen");
		spielStartenBtn.setMnemonic(KeyEvent.VK_ENTER);
		spielStartenBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.aktiveClientAskHowMany();
				client.showNeuesSpielPanel();
				client.spielNotReady();
			}
		});
		this.add(spielStartenBtn);
		if(gui.gameNotReady()) {
			setEnableNeuesSpielbtn(false);
		}
		spielLadenBtn = new JButton("Spiel laden");
		spielLadenBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean load;
				load = client.spielWurdeGeladen();
				if (load) {
					client.showWerBistDuPanel();
				} else {
					client.showLadePanel();
				}
			}
		});
		this.add(spielLadenBtn);
	}

	public void setEnableNeuesSpielbtn(boolean sichtbar) {
		spielStartenBtn.setEnabled(sichtbar);
	}

	public void setSpielEintreitenBtn() {
		spielStartenBtn.setText("Party beitreten");
		spielStartenBtn.setEnabled(true);
	}
}

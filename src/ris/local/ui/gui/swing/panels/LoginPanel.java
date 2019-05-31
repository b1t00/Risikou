package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ris.local.ui.gui.RisikoClientGUI;

public class LoginPanel extends JPanel {

	private JButton spielStartenBtn;

	private JButton spielLadenBtn;

	private RisikoClientGUI client;

	public LoginPanel(RisikoClientGUI client) {
		this.client = client;
//		Dimension size = this.getPreferredSize();
//		size.width = 500;
//		this.setPreferredSize(size);
		spielStartenBtn = new JButton("Neues Spiel beginnen");
		spielStartenBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.showNeuesSpielPanel();
			}
		});
		this.add(spielStartenBtn);
		spielLadenBtn = new JButton("Spiel laden");
		this.add(spielLadenBtn);
//		setLocation(null);
	}
}

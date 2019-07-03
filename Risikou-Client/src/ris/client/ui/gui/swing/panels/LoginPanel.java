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

public class LoginPanel extends JPanel {

	private JButton spielStartenBtn;
	private JButton spielLadenBtn;
	private RisikoClientGUI client;

	public LoginPanel(RisikoClientGUI client) {
		this.client = client;
		Dimension size = this.getPreferredSize();
		size.width = 1100;
		this.setPreferredSize(size);
//		setLocationRelativeTo(null);
		spielStartenBtn = new JButton("Neues Spiel beginnen");
		spielStartenBtn.setMnemonic(KeyEvent.VK_ENTER);
		spielStartenBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.aktiveClientAskHowMany();
				client.showNeuesSpielPanel();
			}
		});
		this.add(spielStartenBtn);
		spielLadenBtn = new JButton("Spiel laden");
		spielLadenBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				client.zweitausendaLook();
				client.showLadePanel();
			}
		});
		this.add(spielLadenBtn);
//		setupEvents();
	}
	
	public void setEnableNeuesSpielbtn(boolean sichtbar) {
		spielStartenBtn.setEnabled(sichtbar);
	}
//	public void setupEvents(){
//		spielLadenBtn.addActionListener(new ButtonListener());
//	}
//	
//	class ButtonListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent aE) {
//			listener.laden();
//		}
//	}

	public void setSpielEintreitenBtn() {
		
		spielStartenBtn.setText("Party beitreten");
		spielStartenBtn.setEnabled(true);
	}
}

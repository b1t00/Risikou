package ris.client.ui.gui.swing.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import ris.common.interfaces.RisikoInterface;

public class LadePanel extends JPanel {

	public interface LadeListener{
		public void spielLaden(String dateiname);
	}
	
	private LadeListener listener;
	private JButton ladeButton = new JButton("Laden");
	private JList<String> alleDateien; 
	private RisikoInterface risiko;
	
	
	public LadePanel(LadeListener listener, RisikoInterface risiko) {
		this.listener = listener;
		this.risiko = risiko;
		setupUI();
		setupEvents();
	}
	
	public void setupUI() {
		System.out.println("Fehler1");

		String[] verzeichnis = risiko.getSpielladeDateien();
		alleDateien = new JList<String>(verzeichnis);
		this.add(ladeButton);
		this.add(alleDateien);
	}
	
	public void setupEvents() {
		ladeButton.addActionListener(new ButtonListener());
	}
	
	public class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(" hiiiiiiiiiiiiiiiiiiiieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeerrrrrrrrrrrrrrrr " + alleDateien.getSelectedValue());
			listener.spielLaden(alleDateien.getSelectedValue());			
		}	
	}
	
}
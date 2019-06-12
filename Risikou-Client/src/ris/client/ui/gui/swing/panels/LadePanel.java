package ris.client.ui.gui.swing.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import ris.client.ui.gui.swing.panels.LadePanel.LadeListener;

public class LadePanel extends JPanel {

	public interface LadeListener{
		public void spielLaden(String dateiname);
	}
	
	private LadeListener listener;
	private JButton ladeButton = new JButton("Laden");
	private JList<String> alleDateien; 
	
	
	public LadePanel(LadeListener listener) {
		this.listener = listener;
		setupUI();
		setupEvents();
	}
	
	public void setupUI() {
		String[] verzeichnis = new String[10];
		//System.getProperty("file.separator") macht es möglich, mit unterschiedlichen Betriebssystemen den Pfad zu laden
		Path dir = Paths.get("files" + System.getProperty("file.separator"));
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			int i = 0;
		      for (Path entry: stream) {
		    	  verzeichnis[i] = entry.getFileName().toString();
		    	  i++;
		         System.out.println("Datei: " + entry.getFileName());
		      }
		} catch (IOException e) {
				e.printStackTrace();
		}
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
			System.out.println(alleDateien.getSelectedValue());
			listener.spielLaden(alleDateien.getSelectedValue());			
		}	
	}
	
}

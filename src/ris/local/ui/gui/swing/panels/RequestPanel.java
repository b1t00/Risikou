package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RequestPanel extends JPanel{
	
	public interface RequestListener{
		//methoden
	}
	
	//wird benötigt, damit die richtige Frage ausgegeben werden kann
	// besser: im turn implementieren
	public enum CountryRequest{
		ATTACKCOUNTRY,
		DEFENSECOUNTRY,
		MOVEFROMCOUNTRY,
		MOVETOCOUNTRY
	}
	
	private RequestListener listener;
//	private Risiko ris = null;
	private JLabel titel;
	private JLabel abfrage;
	private CountryRequest countryR;
	
	public RequestPanel (RequestListener rl, CountryRequest cr) {
		listener = rl;
		countryR = cr;
//		ris = risiko;
		
		setupUI();
	}
	
	
	// auch möglich: mittels enum alle abfragen durch eine Abfrage-Klasse implementieren?
	public void setupUI() {
		switch(countryR) {
		case ATTACKCOUNTRY:
			titel = new JLabel("Attack");
			abfrage =  new JLabel("Mit welchem Land soll angegriffen werden? (direkt anklicken)");
			break;
		case DEFENSECOUNTRY:
			titel = new JLabel("Attack");
			abfrage =  new JLabel("Welches Land soll angegriffen werden? (direkt anklicken)");
			break;
		case MOVEFROMCOUNTRY:
			titel = new JLabel("Move units");
			abfrage =  new JLabel("Von welchem Land soll eine Einheit verschoben werden? (direkt anklicken)");
			break;
		case MOVETOCOUNTRY:
			titel = new JLabel("Move units");
			abfrage =  new JLabel("Zu welchem Land soll eine Einheit verschoben werden? (direkt anklicken)");
			break;
		}
		
		this.setLayout(new GridLayout(4, 1));
//		this.setSize(400,100);
//		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(titel);
		this.add(abfrage);
	}
	
	public CountryRequest getCountryRequest() {
		return countryR;
	}

}

package ris.local.ui.gui.swing.panels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ris.local.domain.Risiko;

public class RequestPanel extends JPanel{
	
	//wird benötigt, damit die richtige Frage ausgegeben werden kann
	// besser: im turn implementieren
	public enum CountryRequest{
		SETUNITS,
		ATTACKCOUNTRY,
		DEFENSECOUNTRY,
		MOVEFROMCOUNTRY,
		MOVETOCOUNTRY
	}

	private Risiko ris = null;
	private JLabel titel;
	private JTextArea abfrage;
	private CountryRequest countryR;

	
	public RequestPanel (CountryRequest cr, Risiko risiko) {
		countryR = cr;
		ris = risiko;
		
		abfrage = new JTextArea();
		abfrage.setLineWrap(true);
		abfrage.setWrapStyleWord(true);
		abfrage.setEditable(false);
		
		setupUI();
	}
	
	
	// auch möglich: mittels enum alle abfragen durch eine Abfrage-Klasse implementieren?
	public void setupUI() {
		switch(countryR) {
		case SETUNITS:
			break;
		case ATTACKCOUNTRY:
			titel = new JLabel("Attack");
			abfrage.setText("Mit welchem Land soll angegriffen werden? (direkt anklicken)");
			break;
		case DEFENSECOUNTRY:
			titel = new JLabel("Attack");
			abfrage.setText("Welches Land soll angegriffen werden? (direkt anklicken)");
			break;
		case MOVEFROMCOUNTRY:
			titel = new JLabel("Move units");
			abfrage.setText("Von welchem Land soll eine Einheit verschoben werden? (direkt anklicken)");
			break;
		case MOVETOCOUNTRY:
			titel = new JLabel("Move units");
			abfrage.setText("Zu welchem Land soll eine Einheit verschoben werden? (direkt anklicken)");
			break;
		}
		
		this.setLayout(new GridLayout(4, 1));
		this.add(titel);
		this.add(abfrage);
	}
	
	public CountryRequest getCountryRequest() {
		return countryR;
	}


}

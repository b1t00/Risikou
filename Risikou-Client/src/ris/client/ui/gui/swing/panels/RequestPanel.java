package ris.client.ui.gui.swing.panels;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import ris.common.interfaces.RisikoInterface;

public class RequestPanel extends JPanel{
	
	//wird benötigt, damit die richtige Frage ausgegeben werden kann
	// besser: im turn implementieren
	public enum CountryRequest{
		CARDREQUEST,
		SETUNITS,
		ATTACKCOUNTRY,
		DEFENSECOUNTRY,
		MOVEFROMCOUNTRY,
		MOVETOCOUNTRY
	}

	private RisikoInterface ris = null;
	private JLabel titel;
	private JTextArea abfrage;
	private CountryRequest countryR;
	private Font schriftart;

	
	public RequestPanel (CountryRequest cr, RisikoInterface risiko) {
		schriftart = new Font("Impact", Font.PLAIN, 20);
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
		case CARDREQUEST:
			titel = new JLabel("CardCombi", SwingConstants.CENTER);
			titel.setFont(schriftart);
			abfrage.setText("Klicke auf drei deiner Risikokarten. Mögliche Kombinationen: drei gleiche Symbole oder drei unterschiedliche. \n Eine Karte mit einem Land, das du besitzt, bringt eine extra Einheit.");
		case SETUNITS:
			break;
		case ATTACKCOUNTRY:
			titel = new JLabel("Attack", SwingConstants.CENTER);
			titel.setFont(schriftart);
			abfrage.setText("Mit welchem Land soll angegriffen werden? (direkt anklicken)");
			break;
		case DEFENSECOUNTRY:
			titel = new JLabel("Attack", SwingConstants.CENTER);
			titel.setFont(schriftart);
			abfrage.setText("Welches Land soll angegriffen werden? (direkt anklicken)");
			break;
		case MOVEFROMCOUNTRY:
			titel = new JLabel("Move units", SwingConstants.CENTER);
			titel.setFont(schriftart);
			abfrage.setText("Von welchem Land soll eine Einheit verschoben werden? (direkt anklicken)");
			break;
		case MOVETOCOUNTRY:
			titel = new JLabel("Move units", SwingConstants.CENTER);
			titel.setFont(schriftart);
			abfrage.setText("Zu welchem Land soll eine Einheit verschoben werden? (direkt anklicken)");
			break;
		}
		
		this.setLayout(new GridLayout(4, 1));
		this.add(titel);
		this.add(abfrage);
		
		setBorder(new LineBorder(ris.getColorArray().get(ris.gibAktivenPlayer().getNummer()), 5));
	}
	
	public CountryRequest getCountryRequest() {
		return countryR;
	}


}

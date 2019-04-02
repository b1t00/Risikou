package ris.local.domain;

import ris.local.valueobjects.Land;

public class Weltverwaltung {
	
	
		
	
	Land portugal = new Land("Portugal", 0);
	Land spanien = new Land("Spanien", 1);
	Land frankreich = new Land("Frankreich", 2);
	Land belgien = new Land("Belgien", 3);
	Land niederlande = new Land("Niederlande", 4);
	Land westernAustralia = new Land("Western Australia", 5);
	Land northernTerritory = new Land("Northern Territory", 6);
	Land queensland = new Land("Queensland", 7);
	Land southAustralia = new Land("South Australia", 8);
	Land newSouthWales = new Land("New South Wales", 9);
	Land victoria = new Land("Victoria", 10);
	
	public Land[] laender = {portugal, spanien, frankreich, belgien, niederlande, westernAustralia, northernTerritory, queensland, southAustralia, newSouthWales, victoria};
	
	boolean[][] nachbarn = {{false, true, false, false, false, false, false, false, false, false, false},
							{true, false, true, false, false, false, false, false, false, false, false},
							{false, true, false, true, false, false, false, false, false, false, false},
							{false, false, true, false, true, false, false, false, false, false, false},
							{false, false, false, true, false, false, false, false, false, false, false},
							{false, false, false, false, false, false, true, false, true, false, false},
							{false, false, false, false, false, true, false, true, true, false, false},
							{false, false, false, false, false, false, true, false, true, true, false},
							{false, false, false, false, false, true, true, true, false, true, false},
							{false, false, false, false, false, false, false, true, true, false, true},
							{false, false, false, false, false, false, false, false, true, true, false}
							};
	
	
	public Weltverwaltung() {
		
	}
	
	public boolean isBenachbart(Land land1, Land land2){
		return (nachbarn[land1.getNummer()][land2.getNummer()]);
	}
	
	
}

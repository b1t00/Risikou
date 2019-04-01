package ris.local.domain;

import ris.local.valueobjects.Land;

public class Weltverwaltung {
	Land[] laenderArray = {new Land("Portugal", 0), new Land("Spanien", 1), new Land("Frankreich", 2), new Land("Belgien", 3), new Land("Niederlande", 4),
							new Land("Western Australia", 5), new Land("Northern Territory", 6), new Land("Queensland", 7), new Land("South Australia", 8),
							new Land("New South Wales", 9), new Land("Victoria", 10)};
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
	
	public boolean isBenachbart(Land land1, Land land2){
		if (nachbarn[land1.getNummer()][land2.getNummer()]) {
			return true;
		}
		return false;
	}

}

package ris.local.domain;

import ris.local.valueobjects.Land;

public class Weltverwaltung {
	Land[] laenderArray = {new Land("Frankreich", 0), new Land("Italien", 1), new Land("Spanien", 2)};
	boolean[][] nachbarn = {{false, true, true},
							{true, false, false},
							{true, false, false}};
	
	

}

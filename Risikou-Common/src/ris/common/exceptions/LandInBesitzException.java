package ris.common.exceptions;

import ris.common.valueobjects.Land;

public class LandInBesitzException extends Exception {
	
	public LandInBesitzException(Land land) {
		super(land.getName() + " gehört dem Angreifer und kann somit nicht angegriffen werden...");
	}
	
}

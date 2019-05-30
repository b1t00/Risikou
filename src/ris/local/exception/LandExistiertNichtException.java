package ris.local.exception;

import ris.local.valueobjects.Land;

public class LandExistiertNichtException extends Exception {
	
	public LandExistiertNichtException(Land land) {
		super("Das ausgewaehlte Land "+land+" existiert nicht");
	}

}

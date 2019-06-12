package ris.common.exceptions;

import ris.common.valueobjects.Land;

public class LandExistiertNichtException extends Exception {
	
	public LandExistiertNichtException(Land land) {
		super("Das ausgewaehlte Land "+land+" existiert nicht");
	}

}

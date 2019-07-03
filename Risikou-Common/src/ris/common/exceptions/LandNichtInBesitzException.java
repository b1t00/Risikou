package ris.common.exceptions;

import ris.common.valueobjects.Land;

public class LandNichtInBesitzException extends Exception {

	public LandNichtInBesitzException(Land land) {
		super(land.getName() + " gehört nicht dem Angreifer...");
	}
}

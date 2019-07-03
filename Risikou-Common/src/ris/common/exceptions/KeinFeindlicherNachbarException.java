package ris.common.exceptions;

import ris.common.valueobjects.Land;

public class KeinFeindlicherNachbarException extends Exception {

	public KeinFeindlicherNachbarException(Land land) {
		super(land.getName() + " hat keine feindlichen Nachbarn.");
	}
}

package ris.common.exceptions;

public class ZuVieleDateienException extends Exception {
	public ZuVieleDateienException(int dateien) {
			super("Es koennen nur 10 Dateien gespeichert werden. Fuer freien Speicherplatz Dateien loeschen! \n Sie haben zurzeit " + dateien + " in dem files-Ordner.");
		}
	}


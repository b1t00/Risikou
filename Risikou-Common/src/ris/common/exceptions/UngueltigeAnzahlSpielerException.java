package ris.common.exceptions;

public class UngueltigeAnzahlSpielerException extends Exception {
	public UngueltigeAnzahlSpielerException(int anzahl) {
		//Korrekte Anzahl der Spieler uebergebens
		super("Die Anzahl der Spieler: " + anzahl + " ist nicht gueltig.\n Minimum 2 Spieler, Maximum 6 Spieler.");
	}
}


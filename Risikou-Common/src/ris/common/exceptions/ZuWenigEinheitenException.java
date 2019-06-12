package ris.common.exceptions;

public class ZuWenigEinheitenException extends Exception {
	public ZuWenigEinheitenException(int anzahl) {
		super("Ungueltige Anzahl, du kannst maximal "+(anzahl-1)+" Einheiten verwenden");
	}

}

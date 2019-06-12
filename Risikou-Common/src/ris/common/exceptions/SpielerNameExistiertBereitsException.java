package ris.common.exceptions;

public class SpielerNameExistiertBereitsException extends Exception {
	public SpielerNameExistiertBereitsException(String name, String farbe, int idNummer) {
		super("Ein Spieler mit dem Namen " + name + " existiert bereits \nBitte waehl eine anderen Namen");
	}
}

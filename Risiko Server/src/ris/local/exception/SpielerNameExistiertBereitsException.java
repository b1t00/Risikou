package ris.local.exception;

public class SpielerNameExistiertBereitsException extends Exception{
	public SpielerNameExistiertBereitsException(String name) {
		super("Der Spieler mit dem Namen "+ name+ " existiert bereits");
	}
}

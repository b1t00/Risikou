package ris.local.exception;

public class UngueltigeAnzahlEinheitenException extends Exception{
	
	private int minimum;
	private int maximum;
	
	public UngueltigeAnzahlEinheitenException(int min, int max) {
//		super("Minimal " + min + " Einheiten usw...");
		
		this.minimum = min;
		this.maximum = max;
	}
	
	public UngueltigeAnzahlEinheitenException(String message) {
		super(message);
	}

//	@Override
//	public String getMessage() {
//		return "Minimal " + minimum + " Einheiten usw...";
//	}
	
	public int getMinimum() {
		return minimum;
	}

	public int getMaximum() {
		return maximum;
	}
}

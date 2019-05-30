package ris.local.exception;

public class UngueltigeAnzahlEinheitenException extends Exception{
	
	private int minimum;
	private int maximum;
	private int minD;
	private int maxD;
	
	public UngueltigeAnzahlEinheitenException(int min, int max) {
		super("Minimal " + min + " Einheiten , maximal"+max+" Einheiten");
		
		this.minimum = min;
		this.maximum = max;
	}
	public UngueltigeAnzahlEinheitenException(int minA,int maxA,int minD,int maxD) {
		super("Ungueltige Anzahl an Einheiten, Angreifer mininma l" +minA+" ,maximal "+maxA+" ,Verteidiger minimal"+minD+" ,maximal "+2);
		this.minimum=minA;
		this.maximum=maxA;
		this.minD=minD;
		this.maxD=maxD;
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

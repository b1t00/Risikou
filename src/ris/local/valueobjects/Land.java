package ris.local.valueobjects;



public class Land {
	
	public static void main(String[] args) {
		Land nl = new Land("NL",5);
		System.out.println(nl.getEinheiten());
		nl.setEinheiten(2);
		nl.setEinheiten(-1);
		System.out.println(nl.getEinheiten());
	}
	
	private int einheiten = 1;
	private int nummer;
	private String name;

	private String farbe;
	
	public Land(String name, int nummer, String farbe) {
		this.nummer = nummer;
		this.name = name;
		this.farbe = farbe;
	}
	
	public int getNummer() {
		return nummer;
	}
	
	// Methode um Einheiten zu setzten 
	// evtl exception wenn einheiten unter 0 oder 1 gehen.
	public void setEinheiten(int einheit) {
		this.einheiten = this.einheiten + einheit;
	}
	
	
	public int getEinheiten() {
		return einheiten;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public String getFarbe() {
		return farbe;
	}


	@Override
	public boolean equals(Object obj) {
				if(obj instanceof Land) {
					Land neuesLand = (Land) obj;
					return ((this.name.equals(neuesLand.name)));
				}
				return false;
	}
	
	
	
}
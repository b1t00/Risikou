package ris.local.valueobjects;

public class Land {
	private int einheiten;
	private int nummer;
	private String name;
	private String farbe; // steht f�r den Spieler
	private Player besitzer;
	
	public Land(String name, int nummer) {
		this.nummer = nummer;
		this.name = name;
	}
	
	// Methode um Einheiten zu setzten 
	// abfrage, ob noch genug einheiten auf dem land stehen findet in der spiellogik||risiko||cui statt
	public void setEinheiten(int einheit) {
			this.einheiten += einheit;
	}
	
	@Override
	public String toString() {
		return (name + " mit " + einheiten + " Einheiten. \n");
	}
	
	//@to Methode um player/besitzer zu setzten 
	public void setBesitzer(Player besitzer) {
		this.besitzer = besitzer;
	}
	public Player getBesitzer() {
		return this.besitzer;
	}
	
//	@to
	public String auflistung() {
		return this.nummer + " : " + this.einheiten;
	}
	
	public int getEinheiten() {
		return einheiten;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFarbe() {
		return farbe;
	}
	
	public int getNummer() {
		return nummer;
	}
	
	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}
	
	public Player getBesitzer() {
		return besitzer;
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
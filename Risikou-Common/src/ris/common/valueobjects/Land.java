package ris.common.valueobjects;

import java.io.Serializable;

import ris.common.exceptions.UngueltigeAnzahlEinheitenException;

public class Land implements Comparable, Serializable{
	private int einheiten;
	private int nummer;
	private String name;
	private Player besitzer;
//	Positionen der Fahne und der Einheitenanzahl
	private int xFahne;
	private int yFahne;
	private int xEinheiten;
	private int yEinheiten;
	
	public Land(String name, int nummer, int einheiten,int xFahne,int yFahne,int xEinheiten,int yEinheiten) {
		this.nummer = nummer;
		this.name = name;
		this.einheiten = einheiten;
		this.xFahne = xFahne;		this.yFahne = yFahne;
		this.xEinheiten = xEinheiten;
		this.yEinheiten = yEinheiten;
	}
	
//	Methode um Einheiten zu setzten 
	public void setEinheiten(int einheit) throws UngueltigeAnzahlEinheitenException {
		System.out.println("set einheiten im land");
			if((this.einheiten + einheit) < 1)  {
				throw new UngueltigeAnzahlEinheitenException(1, this.einheiten-1);
			} else {
				this.einheiten += einheit;
			}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
//	Methode um player/besitzer zu setzen 
	public void setBesitzer(Player besitzer) {
		this.besitzer = besitzer;
	}
	public Player getBesitzer() {
		return this.besitzer;
	}

	public int getEinheiten() {
		return einheiten;
	}
	
	/*
	 * gibt Anzahl Einheiten zurueck, die fuer das Verschieben am Ende des Zuges zur Verfuegung stehen
	 * dafuer wird der Wert beim uBlock (dieser wird beim automatischen Verschieben beim Angriff gesetzt)
	 * von dem eigentlichen Einheiten-Wert abgezogen.
	 */
	public int getVerfuegbareEinheiten() {
		return einheiten - besitzer.getBlock()[nummer] - 1;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNummer() {
		return nummer;
	}


	@Override
	public boolean equals(Object obj) {
			if(obj instanceof Land) {
				Land neuesLand = (Land) obj;
				return ((this.name.equals(neuesLand.name)));
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o) {
		Land other = (Land) o;
		return this.nummer - other.getNummer();
	}

	public int getXFahne() {
		return xFahne;
	}
	
	public int getYFahne() {
		return yFahne;
	}

	public int getYEinheiten() {
		return yEinheiten;
	}

	public int getXEinheiten() {
		return xEinheiten;
	}

	
	
}
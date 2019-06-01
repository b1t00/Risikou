package ris.local.valueobjects;

import java.io.Serializable;

import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;

public class Land implements Comparable, Serializable{
	private int einheiten;
	private int nummer;
	private String name;
	private Player besitzer;
	private String rgb;
	
	public Land(String name, int nummer, int einheiten,String rgb) {
		this.nummer = nummer;
		this.name = name;
		this.einheiten = einheiten;
		this.rgb = rgb;
	}
	
	// Methode um Einheiten zu setzten 
	// abfrage, ob noch genug einheiten auf dem land stehen findet in der spiellogik||risiko||cui statt
	public void setEinheiten(int einheit) throws ZuWenigEinheitenNichtMoeglichExeption {
			if(this.einheiten < 0) {
				throw new ZuWenigEinheitenNichtMoeglichExeption("Das Land hat wenig Einheiten !! ACHTUNG FEHLER!");
			} else {
				this.einheiten += einheit;
			}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	//@to Methode um player/besitzer zu setzten 
	public void setBesitzer(Player besitzer) {
		this.besitzer = besitzer;
	}
	public Player getBesitzer() {
		return this.besitzer;
	}
	
	public String auflistung() {
		return this.nummer + " : " + this.einheiten;
	}
	
	public int getEinheiten() {
		return einheiten;
	}
	
	/*
	 * gibt Anzahl Einheiten zurueck, die fuer das Verschieben am Ende des Zuges zur Verfuegung stehen
	 * dafuer wird der Wert beim uBlock(dieser wird beim automatischen Verschieben beim Angriff gesetzt)
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
	
	
}
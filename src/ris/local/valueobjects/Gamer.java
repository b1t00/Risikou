package ris.local.valueobjects;

import java.util.ArrayList;
import java.util.List;

import ris.local.domain.Weltverwaltung;

public class Gamer {
	private String name;

	public String toString() {
		return name;
	}

	public String gibLaenderAus() {
		String rueckgabe = "";
		for (Land land: inBesitz) {
			rueckgabe += land.getNummer() + " > " + land.getName() + "\n";
		}
		return rueckgabe;
	}

	private String farbe;
	// private int besatzerNr; //für spielablauf
	private int darfsetzten; // variable für die Spieler die er in der Runde setzten darf
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	private ArrayList<Land> europa, autralien;
	
	Weltverwaltung welt = new Weltverwaltung();

	public Gamer(String name, String farbe, ArrayList inBesitz) {
		this.name = name;
		this.farbe = farbe;
		this.inBesitz = inBesitz;
		kontinentCheck(inBesitz, welt.getEuropa());    
	}

	public String getName() {
		return name;
	}

	public String getFarbe() {
		return farbe;
	}

	public ArrayList<Land> getBesitz() {
		return inBesitz;
	}

	public boolean kontinentCheck(ArrayList<Land> spielerListe, ArrayList<Land> kontinent ) {
		int k = 0;
			for (Land land : kontinent) {
				if (!spielerListe.contains(land)) {
					System.out.println("heeey false");
					return false;
					
				} else {
					System.out.println(europa.get(k));
					k++; 
					
					}
			}
			
			return true;
	}
}

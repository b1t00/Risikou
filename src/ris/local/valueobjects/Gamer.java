package ris.local.valueobjects;

import java.util.ArrayList;

import ris.local.domain.Weltverwaltung;

public class Gamer {
	private String name;
	private String farbe;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	

	public Gamer(String name, String farbe, ArrayList inBesitz) {
		this.name = name;
		this.farbe = farbe;
		this.inBesitz = inBesitz;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFarbe() {
		return farbe;
	}
	
	public ArrayList<Land> getBesitz(){
		return inBesitz;
	}
	
	

	public static void main(String[] args) {
		Weltverwaltung welt = new Weltverwaltung();
		 ArrayList<Land> besitzt = new ArrayList<Land>();
		
		besitzt.add(welt.laender[0]);
		besitzt.add(welt.laender[3]);
//		System.out.println(besitzt);
		
		Gamer hannes = new Gamer("Hannes","rot",besitzt);
		System.out.println(hannes);
		
	}
}

package ris.local.valueobjects;

import java.util.ArrayList;
import java.util.List;

import ris.local.domain.Weltverwaltung;

public class Player {
	private String name;
	private String farbe;
	private int besatzerNr; //für spielablauf
	private int darfsetzten; // variable für die Spieler die er in der Runde setzten darf
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	private ArrayList<Land> europa, autralien;
	
//	Weltverwaltung welt = new Weltverwaltung();

	public Player(String name, String farbe, int nr) {
		this.name = name;
		this.besatzerNr = nr;
		this.farbe = farbe;
//		this.inBesitz = inBesitz;
//		kontinentCheck(inBesitz, welt.getEuropa());    // <----------- diese Methode equals überschreibung #to
	}
}
//	}
//
//	public String getName(int zahl) {
//		if(besatzerNr == zahl) {
//		return name;}
//		return "nichts";
//	}
//
//	public String getFarbe() {
//		return farbe;
//	}
//
//	public ArrayList<Land> getBesitz() {
//		return inBesitz;
//	}
//
//	public boolean kontinentCheck(ArrayList<Land> spielerListe, ArrayList<Land> kontinent ) {
//		int k = 0;
//			for (Land land : kontinent) {
//				if (!spielerListe.contains(land)) {
//					System.out.println("heeey false");
//					return false;
//					
//				} else {
////					System.out.println(europa.get(k));
//					k++; 
//					
//					}
//			}
//			
//			return true;
//	}
//
//}

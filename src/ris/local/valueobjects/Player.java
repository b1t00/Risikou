package ris.local.valueobjects;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import ris.local.domain.Worldmanagement;
=======
import ris.local.domain.Weltverwaltung;
>>>>>>> tobiBranch(master)

public class Player {
	private String name;
	private String farbe;
<<<<<<< HEAD
	private int nummer;
	// private int besatzerNr;
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	//private int einheiten;

	public Player(String name, String farbe, int nummer) {
		this.name = name;
		this.farbe = farbe;
		this.nummer = nummer;
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
	
	public String toString() {
		return (name + " hat die Farbe " + farbe) ;
	}

	public String gibLaenderAus() {
		String rueckgabe = "";
		for (Land land: inBesitz) {
			rueckgabe += name + " besitzt " + land.getNummer() + " > " + land + "\n";
		}
		return rueckgabe;
	}

	//diese Methode beim Hinzuf�gen von einzelnen L�ndern
	public void addLand(Land neuesLand) {
		this.inBesitz.add(neuesLand);
	}
	
	//diese Methode beim Hinzuf�gen von einem ganzen L�nder-Array
	public void addLaender(ArrayList<Land> neueLaender) {
		this.inBesitz = neueLaender;
	}

}
=======
	private int besatzerNr; //f�r spielablauf
	private int darfsetzten; // variable f�r die Spieler die er in der Runde setzten darf
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	private ArrayList<Land> europa, autralien;
	
//	Weltverwaltung welt = new Weltverwaltung();

	public Player(String name, String farbe, int nr) {
		this.name = name;
		this.besatzerNr = nr;
		this.farbe = farbe;
//		this.inBesitz = inBesitz;
//		kontinentCheck(inBesitz, welt.getEuropa());    // <----------- diese Methode equals �berschreibung #to
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
>>>>>>> tobiBranch(master)

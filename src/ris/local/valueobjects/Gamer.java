package ris.local.valueobjects;

import java.util.ArrayList;
import java.util.List;

import ris.local.domain.Weltverwaltung;

public class Gamer {
	private String name;
	private String farbe;
	private int darfsetzten; // variable für die Spieler die er in der Runde setzten darf
	private ArrayList<Land> inBesitz = new ArrayList<Land>();
	private ArrayList<Land> europa, autralien;
	Weltverwaltung welt = new Weltverwaltung();

	public Gamer(String name, String farbe, ArrayList inBesitz) {
		this.name = name;
		this.farbe = farbe;
		this.inBesitz = inBesitz;
		kontinentCheck(inBesitz);

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

	public boolean kontinentCheck(ArrayList<Land> check) {
		europa = new ArrayList<Land>();
		for(int i = 0 ; i < 10 ; i++) {
		europa.add(welt.laender[i]);
		}
		int k = 0;

		for (Land land : europa) {
			System.out.println("hier gucken !!! : "+ check.contains(land));
			System.out.println(europa.get(k));
			k++;
		}
		return true;
	}

}

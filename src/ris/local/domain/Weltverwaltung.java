package ris.local.domain;

import java.util.*;


import ris.local.valueobjects.Land;
import ris.local.ui.cui.RisikoClientUI;
import ris.local.valueobjects.Gamer;
import ris.local.valueobjects.Kontinent;

public class Weltverwaltung {
	
	private ArrayList<Land> laender = new ArrayList<Land>(); 
	private ArrayList<Kontinent> kontinente = new ArrayList<Kontinent>();
	
	public Weltverwaltung() {
		erstelleWelt();
	}

	public void erstelleWelt() {
		//Erstellung von Ländern und speichern in der ArrayList<Land>
		Land portugal = new Land("Portugal", 0);
		laender.add(portugal);
		Land spanien = new Land("Spanien", 1);
		laender.add(spanien);
		Land frankreich = new Land("Frankreich", 2);
		laender.add(frankreich);
		Land belgien = new Land("Belgien", 3);
		laender.add(belgien);
		Land niederlande = new Land("Niederlande", 4);
		laender.add(niederlande);
		Land westernAustralia = new Land("Western Australia", 5);
		laender.add(westernAustralia);
		Land northernTerritory = new Land("Northern Territory", 6);
		laender.add(northernTerritory);
		Land queensland = new Land("Queensland", 7);
		laender.add(queensland);
		Land southAustralia = new Land("South Australia", 8);
		laender.add(southAustralia);
		Land newSouthWales = new Land("New South Wales", 9);
		laender.add(newSouthWales);
		Land victoria = new Land("Victoria", 10);
		laender.add(victoria);
		
		//ab hier werden die Kontinente erstellt und dann in der Array-Liste<Kontinent> gespeichert
		ArrayList<Land> eu = new ArrayList<Land>();	
		eu.add(portugal);
		eu.add(spanien);
		eu.add(frankreich);
		eu.add(belgien);
		eu.add(niederlande);
		Kontinent europa = new Kontinent("Europa", eu);
		kontinente.add(europa);
		ArrayList<Land> au = new ArrayList<Land>();
		au.add(westernAustralia);
		au.add(northernTerritory);
		au.add(queensland);
		au.add(southAustralia);
		au.add(newSouthWales);
		au.add(victoria);
		Kontinent australien = new Kontinent("Australien", au);
		kontinente.add(australien);
	}
	
	//hier noch kommentar zu der Matrix
	boolean[][] nachbarn = {{false, true, false, false, false, false, false, false, false, false, false}, //welches land?
							{true, false, true, false, false, false, false, false, false, false, false},
							{false, true, false, true, false, false, false, false, false, false, false},
							{false, false, true, false, true, false, false, false, false, false, false},
							{false, false, false, true, false, false, false, false, false, false, false},
							{false, false, false, false, false, false, true, false, true, false, false},
							{false, false, false, false, false, true, false, true, true, false, false},
							{false, false, false, false, false, false, true, false, true, true, false},
							{false, false, false, false, false, true, true, true, false, true, false},
							{false, false, false, false, false, false, false, true, true, false, true},
							{false, false, false, false, false, false, false, false, true, true, false}
							};	
	
	public boolean isBenachbart(Land land1, Land land2){
		return (nachbarn[land1.getNummer()][land2.getNummer()]);
	}

}

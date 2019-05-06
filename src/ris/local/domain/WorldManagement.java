package ris.local.domain;

import java.util.*;


import ris.local.valueobjects.Land;
import ris.local.ui.cui.RisikoClientUI;
import ris.local.valueobjects.Player;
import ris.local.valueobjects.Kontinent;

public class WorldManagement {
	
	private ArrayList<Land> laender = new ArrayList<Land>(); 
	private ArrayList<Land> shuffle = new ArrayList<Land>();
	private ArrayList<Kontinent> kontinente = new ArrayList<Kontinent>();
	
	public WorldManagement() {
		erstelleWelt();
	}

	public void erstelleWelt() {
		//Erstellung von L�ndern und speichern in der ArrayList<Land>
		Land portugal = new Land("Portugal", 0, 1);
		laender.add(portugal);
		Land spanien = new Land("Spanien", 1, 1);
		laender.add(spanien);
		Land frankreich = new Land("Frankreich", 2, 1);
		laender.add(frankreich);
		Land belgien = new Land("Belgien", 3, 1);
		laender.add(belgien);
		Land niederlande = new Land("Niederlande", 4, 1);
		laender.add(niederlande);
		Land westernAustralia = new Land("Western Australia", 5, 1);
		laender.add(westernAustralia);
		Land northernTerritory = new Land("Northern Territory", 6, 1);
		laender.add(northernTerritory);
		Land queensland = new Land("Queensland", 7, 1);
		laender.add(queensland);
		Land southAustralia = new Land("South Australia", 8, 1);
		laender.add(southAustralia);
		Land newSouthWales = new Land("New South Wales", 9, 1);
		laender.add(newSouthWales);
		Land victoria = new Land("Victoria", 10, 1);
		laender.add(victoria);
		Land narnia = new Land("narnia", 11, 1);
		laender.add(narnia);
		
		//ab hier werden die Kontinente erstellt und dann in der Array-Liste<Kontinent> gespeichert
		ArrayList<Land> eu = new ArrayList<Land>();	
		eu.add(portugal);
		eu.add(spanien);
		eu.add(frankreich);
		eu.add(belgien);
		eu.add(niederlande);
		Kontinent europa = new Kontinent("Europa", eu, 4);
		kontinente.add(europa);
		
		ArrayList<Land> au = new ArrayList<Land>();
		au.add(westernAustralia);
		au.add(northernTerritory);
		au.add(queensland);
		au.add(southAustralia);
		au.add(newSouthWales);
		au.add(victoria);
		Kontinent australien = new Kontinent("Australien", au, 3);
		kontinente.add(australien);
	}
	
	public ArrayList<Land> getLaender(){
		Collections.sort(laender);
		return laender;
	}
	
	public ArrayList<Land> getShuffle(){
		return shuffle;
	}
	
	//hier noch kommentar zu der Matrix
	boolean[][] nachbarn = {{false, true, false, false, false, false, false, false, false, false, false, false},		//Portugal
							{true, false, true, false, false, false, false, false, false, false, false, false},			//Spanien
							{false, true, false, true, false, false, false, false, false, false, false, false},			//Frankreich
							{false, false, true, false, true, false, false, false, false, false, false, false},			//Belgien
							{false, false, false, true, false, false, false, false, false, false, false, false},		//Niederland
							{false, false, false, false, false, false, true, false, true, false, false, false},			//Western Australia
							{false, false, false, false, false, true, false, true, true, false, false, false},			//Northern Territory
							{false, false, false, false, false, false, true, false, true, true, false, false},			//Queensland
							{false, false, false, false, false, true, true, true, false, true, false, false},			//South Australia
							{false, false, false, false, false, false, false, true, true, false, true, false},			//New South Wales
							{false, false, false, false, false, false, false, false, true, true, false, false},			//Victoria
							{false, false, false, false, false, false, false, false, false, false, false, false} 		//Narnia
							};	
	
	public boolean isBenachbart(Land land1, Land land2){
		return (nachbarn[land1.getNummer()][land2.getNummer()]);
	}
	
	public Land getLandById(int zahl) {
		return laender.get(zahl);
	}
	
	public ArrayList<Kontinent> getKontinente(){
		return kontinente;
	}
	
	public Kontinent getEuropa(){ //test
		return kontinente.get(0);
	}
	
	public ArrayList<Land> getEigeneNachbarn(Land land){
		ArrayList<Land> eigeneNachbarn = new ArrayList<Land>();
		for (int i = 0; i < nachbarn[land.getNummer()].length; i++) {
			if (nachbarn[land.getNummer()][i]) {
				if(land.getBesitzer().equals(laender.get(i).getBesitzer())) {
					eigeneNachbarn.add(laender.get(i));
				}
			}
		}
		return eigeneNachbarn;
	}

}

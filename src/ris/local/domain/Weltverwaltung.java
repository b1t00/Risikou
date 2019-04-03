package ris.local.domain;

import java.util.*;


import ris.local.valueobjects.Land;
import ris.local.ui.cui.RisikoClientUI;
import ris.local.valueobjects.Gamer;

public class Weltverwaltung {

	Land portugal = new Land("Portugal", 0, "blau");
	Land spanien = new Land("Spanien", 1, "blau");
	Land frankreich = new Land("Frankreich", 2, "rot");
	Land belgien = new Land("Belgien", 3, "blau");
	Land niederlande = new Land("Niederlande", 4, "blau");
	Land westernAustralia = new Land("Western Australia", 5, "blau");
	Land northernTerritory = new Land("Northern Territory", 6, "blau");
	Land queensland = new Land("Queensland", 7, "b");
	Land southAustralia = new Land("South Australia", 8, "b");
	Land newSouthWales = new Land("New South Wales", 9, "b");
	Land victoria = new Land("Victoria", 10, "b");

	
	public Land[] laender = {portugal, spanien, frankreich, belgien, niederlande, westernAustralia, northernTerritory, queensland, southAustralia, newSouthWales, victoria};
	
	boolean[][] nachbarn = {{false, true, false, false, false, false, false, false, false, false, false},
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
	
	
	ArrayList<Land> europa = new ArrayList<Land>();

	public Weltverwaltung() {
	europa.add(portugal);
	europa.add(spanien);
	europa.add(frankreich);
	europa.add(belgien);
	europa.add(niederlande);
	}
	
	public ArrayList<Land> getEuropa(){
		return europa;

	}
	
	public boolean isBenachbart(Land land1, Land land2){
		return (nachbarn[land1.getNummer()][land2.getNummer()]);
	}

		
	public String angriff(int land, Gamer spieler){
		ArrayList<Land> feinde = new ArrayList<Land>();
		for (int i = 0; i < nachbarn[land].length; i++) {
			if (nachbarn[land][i] && !(laender[land].getFarbe().equals(spieler.getFarbe()))) {
				feinde.add(laender[i]);
			}
		}
		String result = "";
		for (Land feind: feinde) {
			result += feind.getNummer() + " > " + feind.getName() + "\n";
		}
		return result;
	}
	
	public void attack(Gamer spieler1) {
		System.out.println(spieler1 + " greift an. Wähle ein Land, das angreift: \n" + spieler1.gibLaenderAus());
//		try {
//			int nummer = Integer.parseInt(liesEingabe());
//		} catch (IOException e) {}
//		
		//int nummer = Integer.parseInt(liesEingabe());
		System.out.println("Welches Land soll angegriffen werden: \n" + world.angriff(1, spieler1));
		//int feind = Integer.parseInt(liesEingabe());
		
	}
	
	public static void main(String[] args) {
		RisikoClientUI cui = new RisikoClientUI();
		ArrayList<Land> laender1 = new ArrayList<Land>();
		laender1.add(new Land("Portugal", 0, "blau"));
		laender1.add(new Land("Spanien", 1, "blau"));
		Gamer spieler1 = new Gamer("Otto", laender1, "blau");
		this.attack(spieler1);

}

package ris.local.domain;

import java.io.Serializable;
import java.util.*;


import ris.local.valueobjects.Land;
import ris.local.ui.cui.RisikoClientUI;
import ris.local.valueobjects.Player;
import ris.local.valueobjects.Kontinent;

public class WorldManagement implements Serializable{
	
	private ArrayList<Land> laender = new ArrayList<Land>(); 
	private transient ArrayList<Land> shuffle = new ArrayList<Land>();
	private ArrayList<Kontinent> kontinente = new ArrayList<Kontinent>();
	
	public WorldManagement() {
		erstelleWelt();
	}

	public void erstelleWelt() {
		//Erstellung von Ländern und speichern in der ArrayList<Land>
		Land groenland = new Land("Groenland", 0, 1,335,77);
		laender.add(groenland);
		Land island = new Land("Island", 1, 1,452,103);
		laender.add(island);
		Land brexit = new Land("Grossbritannien", 2, 1,426,227);
		laender.add(brexit);
		Land westeuropa = new Land("Westeuropa", 3, 1,449,274);
		laender.add(westeuropa);
		Land suedeuropa = new Land("Suedeuropa", 4, 1,557,265);
		laender.add(suedeuropa);
		Land nordeuropa = new Land("Nordeuropa", 5, 1,525,201);
		laender.add(nordeuropa);
		Land skandinavien = new Land("Skandinavien", 6, 1,555,111);
		laender.add(skandinavien);
		Land ukraine = new Land("Ukraine", 7, 1,586,192);
		laender.add(ukraine);
		Land ural = new Land("Ural", 8, 1,732,189);
		laender.add(ural);
		Land sibirien = new Land("Sibirien", 9, 1,781,124);
		laender.add(sibirien);
		Land yakutsk = new Land("Yakutsk", 10, 1,852,89);
		laender.add(yakutsk);
		Land kamchatka = new Land("Kamchatka", 11, 1,937,91);
		laender.add(kamchatka);
		Land irkutsk = new Land("Irkutsk", 12, 1,849,175);
		laender.add(irkutsk);
		Land mongolei = new Land("Mongolei", 13, 1,889,200);
		laender.add(mongolei);
		Land japan = new Land("Japan", 14, 1,985,215);
		laender.add(japan);
		Land china = new Land("China", 15, 1,801,264);
		laender.add(china);
		Land afghanistan = new Land("Afghanistan", 16, 1,703,277);
		laender.add(afghanistan);
		Land naherOsten = new Land("Naher Osten", 17, 1,641,392);
		laender.add(naherOsten);
		Land indien = new Land("Indien", 18, 1,741,330);
		laender.add(indien);
		Land siam = new Land("Siam", 19, 1,841,336);
		laender.add(siam);
		Land indonesien = new Land("Indonesien", 20, 1,890,483);
		laender.add(indonesien);
		Land neuGuinea = new Land("Neu Guinea", 21, 1,966,471);
		laender.add(neuGuinea);
		Land westaustralien = new Land("Westaustralien", 22, 1,922,574);
		laender.add(westaustralien);
		Land ostaustralien = new Land("Ostaustralien", 23, 1,1008,626);
		laender.add(ostaustralien);
		Land madagaskar = new Land("Madagaskar", 24, 1,665,600);
		laender.add(madagaskar);
		Land suedafrika = new Land("Suedafrika", 25, 1,543,584);
		laender.add(suedafrika);
		Land kongo = new Land("Kongo", 26, 1,560,501);
		laender.add(kongo);
		Land ostafrika = new Land("Ostafrika", 27, 1,583,453);
		laender.add(ostafrika);
		Land aegypten = new Land("Aegypten", 28, 1,539,397);
		laender.add(aegypten);
		Land nordafrika = new Land("Nordafrika", 29, 1,450,385);
		laender.add(nordafrika);
		Land argentinien = new Land("Argentinien", 30, 1,248,535);
		laender.add(argentinien);
		Land peru = new Land("Peru", 31, 1,209,463);
		laender.add(peru);
		Land brasilien = new Land("Brasilien", 32, 1,316,489);
		laender.add(brasilien);
		Land venezuela = new Land("Venezuela", 33, 1,200,394);
		laender.add(venezuela);
		Land zentralamerika = new Land("Zentralamerika", 34, 1,163,313);
		laender.add(zentralamerika);
		Land oststaaten = new Land("Oststaaten", 35, 1,259,239);
		laender.add(oststaaten);
		Land weststaaten = new Land("Weststaaten", 36, 1,121,251);
		laender.add(weststaaten);
		Land quebec = new Land("Quebec", 37, 1,267,186);
		laender.add(quebec);
		Land ontario = new Land("Ontario", 38, 1,118,133);
		laender.add(ontario);
		Land alberta = new Land("Alberta", 39, 1,144,171);
		laender.add(alberta);
		Land alaska = new Land("Alaska", 40, 1,52,71);
		laender.add(alaska);
		Land nwTerrit = new Land("Nord-West Territorium", 41, 1,141,70);
		laender.add(nwTerrit);
		
		
		//ab hier werden die Kontinente erstellt und dann in der Array-Liste<Kontinent> gespeichert
		ArrayList<Land> eu = new ArrayList<Land>();	
		eu.add(island);
		eu.add(brexit);
		eu.add(westeuropa);
		eu.add(suedeuropa);
		eu.add(nordeuropa);
		eu.add(skandinavien);
		eu.add(ukraine);
		Kontinent europa = new Kontinent("Europa", eu, 4);
		kontinente.add(europa);
		
		ArrayList<Land> na = new ArrayList<Land>();
		na.add(zentralamerika);
		na.add(oststaaten);
		na.add(weststaaten);
		na.add(quebec);
		na.add(ontario);
		na.add(alberta);
		na.add(alaska);
		na.add(groenland);
		na.add(nwTerrit);
		Kontinent nordamerika = new Kontinent("Nordamerika", na, 3);
		kontinente.add(nordamerika);
		
		
		ArrayList<Land> sa = new ArrayList<Land>();
		sa.add(argentinien);
		sa.add(brasilien);
		sa.add(peru);
		sa.add(venezuela);
		Kontinent suedamerika = new Kontinent("Suedamerika", sa, 2);
		kontinente.add(suedamerika);
		
		
		ArrayList<Land> af = new ArrayList<Land>();
		af.add(madagaskar);
		af.add(suedafrika);
		af.add(nordafrika);
		af.add(aegypten);
		af.add(kongo);
		af.add(ostafrika);
		Kontinent afrika= new Kontinent("Afrika",af,1);
		kontinente.add(afrika);
		
		
		ArrayList<Land> as = new ArrayList<Land>();
		as.add(ural);
		as.add(sibirien);
		as.add(yakutsk);
		as.add(kamchatka);
		as.add(irkutsk);
		as.add(mongolei);
		as.add(japan);
		as.add(china);
		as.add(afghanistan);
		as.add(naherOsten);
		as.add(indien);
		as.add(siam);
		Kontinent asien= new Kontinent("Asien",as,0);
		kontinente.add(asien);
		
		
		ArrayList<Land> au = new ArrayList<Land>();
		au.add(indonesien);
		au.add(neuGuinea);
		au.add(ostaustralien);
		au.add(westaustralien);
		Kontinent australien= new Kontinent("Australien",au,5);
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
	boolean[][] nachbarn = {{false, true,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, true, true, false, false, true,},		//groenland			
							{true, false,true, false, false, false, true, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//island
							{true, true,false, true, false, false, true, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//brexit
							{false, false,true, false, true, true, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, true, false, false,false, false, false, false, false, false, false, false, false, false,},			//westeuropa						
							{false, false,false, true, false, true, false, true, false, false, false, false,false, false, false, false, false, true, false, false, false, false,false, false, false, false, false, false, true, true, false, false,false, false, false, false, false, false, false, false, false, false,},		//suedeuropa
							{false, false,true, true, true, false, true, true, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//nordeuropa
							{false, true,false, false, false, true, false, true, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//skandinavien
							{false, false,false, false, true, true, true, false, true, false, false, false,false, false, false, false, true, true, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//ukraine
							{false, false,false, false, false, false, false, true, false, true, false, false,false, false, false, true, true, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//ural					
							{false, false,false, false, false, false, false, false, true, false, true, false,true, true, false, true, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//sibirien							
							{false, false,false, false, false, false, false, false, false, true, false, true, true, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//yakutsk						
							{false, false,false, false, false, false, false, false, false, false, true, false, true, true, true, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, true, false,},			//kamchatka
							{false, false,false, false, false, false, false, false, false, true, true, true,false, true, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},		//irkutsk
							{false, false,false, false, false, false, false, false, false, true, false, true, true, false, true, true, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//mongolei					
							{false, false,false, false, false, false, false, false, false, false, false, true,false, true, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//japan						
							{false, false,false, false, false, false, false, false, true, true, false, false,false, true, false, false, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//china							
							{false, false,false, false, false, false, false, true, true, false, false, false,false, false, false, true, false, true, true, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},		//afghanistan						
							{false, false,false, false, true, false, false, true, false, false, false, false,false, false, false, false, true, false, true, false, false, false,false, false, false, false, false, true, true, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//naher osten						
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, true, true, true, false, true, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//indien							
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, true, false, false, true, false, true, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//siam							
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, true, false, true, true, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//indonesien							
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, true, false, true, true, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//neu guinea					
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//westaustralien							
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,}, 		//ostaustralien
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, true, false, true, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},		//Madagaskar
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, true, false, true, true, false, false, false, false,false, false, false, false, false, false, false, false, false, false,},			//suedafrika
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, true, false, true, false, true, false, false,false, false, false, false, false, false, false, false, false, false,},			//kongo
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, true, false, false, false, false,false, false, true, true, true, false, true, true, false, false,false, false, false, false, false, false, false, false, false, false,},			//ostafrika
							{false, false,false, false, true, false, false, false, false, false, false, false,false, false, false, false, false, true, false, false, false, false,false, false, false, false, false, true, false, true, false, false,false, false, false, false, false, false, false, false, false, false,},		//aegypten
							{false, false,false, true, true, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, true, true, true, false, false, false,true, false, false, false, false, false, false, false, false, false,},			//nordafrika
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false,},			//argentinien
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, true, false, true, true, false, false, false, false, false, false, false, false,},			//peru
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, true, true, true,false, true, false, false, false, false, false, false, false, false,},			//brasilien				
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, true,true, false, true, false, false, false, false, false, false, false,},		//venezuela
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, true, false, true, true, false, false, false, false, false,},			//zentralamerika
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, true, false, true, true, true, false, false, false,},			//oststaaten
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, true, true, false, false, true, true, false, false,},			//weststaaten
							{true, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, true, false, false, true, false, false, false,},		//quebec
							{true, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, true, true, true, false, true, false, true,},			//ontario
							{false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, true, false, true, false, true, true,},			//alberta
							{false, false,false, false, false, false, false, false, false, false, false, true,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, true, false, true,},			//alaska
							{true, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true, true, true, false,}			//nw territ
							};	
	
	public boolean isBenachbart(Land land1, Land land2){
		return (nachbarn[land1.getNummer()][land2.getNummer()]);
	}
	
	public Land getLandById(int zahl) {
		for(Land land: laender) {
			if(land.getNummer()==zahl)
				return land;
		}
		return null;
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

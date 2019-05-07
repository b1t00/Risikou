package ris.local.domain;

import java.util.ArrayList;

import ris.local.valueobjects.Einheitenkarte;

public class EinheitenkartenManagement {

	public ArrayList<Einheitenkarte> einheitenkarten;
	public WorldManagement worldMg;

	public EinheitenkartenManagement() {
		einheitenkarten = new ArrayList<Einheitenkarte>();
		worldMg = new WorldManagement();
		for (int i = 0; i < worldMg.getLaender().size(); i++) {
			if (i % 3 == 0) {
				einheitenkarten.add(new Einheitenkarte("Kanone", worldMg.getLandById(i)));
			} else if (i % 3 == 1) {
				einheitenkarten.add(new Einheitenkarte("Soldat", worldMg.getLandById(i)));
			} else if (i % 3 == 2) {
				einheitenkarten.add(new Einheitenkarte("Reiter", worldMg.getLandById(i)));
			}
		}
		// TODO: @tobi Joker einbauen??? 
	}
	public ArrayList<Einheitenkarte> getEinheitenkarten(){
		return einheitenkarten;
	}
}

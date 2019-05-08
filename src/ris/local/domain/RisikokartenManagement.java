package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import ris.local.valueobjects.Risikokarte;

public class RisikokartenManagement implements Serializable {

	public ArrayList<Risikokarte> einheitenkarten;
	public WorldManagement worldMg;

	public RisikokartenManagement() {
		einheitenkarten = new ArrayList<Risikokarte>();
		Collections.shuffle(einheitenkarten);
		worldMg = new WorldManagement();
		for (int i = 0; i < worldMg.getLaender().size(); i++) {
			if (i % 3 == 0) {
				einheitenkarten.add(new Risikokarte("Kanone", worldMg.getLandById(i)));
			} else if (i % 3 == 1) {
				einheitenkarten.add(new Risikokarte("Soldat", worldMg.getLandById(i)));
			} else if (i % 3 == 2) {
				einheitenkarten.add(new Risikokarte("Reiter", worldMg.getLandById(i)));
			}
		}
		// TODO: @tobi Joker einbauen???
	}

	public ArrayList<Risikokarte> getEinheitenkarten() {
		Collections.shuffle(einheitenkarten);
		return einheitenkarten;
	}

}

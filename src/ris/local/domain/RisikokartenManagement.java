package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import ris.local.valueobjects.Risikokarte;
import ris.local.valueobjects.Risikokarte.Symbol;

public class RisikokartenManagement implements Serializable {

	public ArrayList<Risikokarte> einheitenkarten;
	public WorldManagement worldMg;

	public RisikokartenManagement() {
		einheitenkarten = new ArrayList<Risikokarte>();
		worldMg = new WorldManagement();
		for (int i = 0; i < worldMg.getLaender().size(); i++) {
			if (i % 3 == 0) {
				einheitenkarten.add(new Risikokarte(Symbol.KANONE, worldMg.getLandById(i)));
			} else if (i % 3 == 1) {
				einheitenkarten.add(new Risikokarte(Symbol.SOLDAT, worldMg.getLandById(i)));
			} else if (i % 3 == 2) {
				einheitenkarten.add(new Risikokarte(Symbol.REITER, worldMg.getLandById(i)));
			}
		}
		Collections.shuffle(einheitenkarten);
		// TODO: @tobi Joker einbauen???
	}

	public ArrayList<Risikokarte> getEinheitenkarten() {
		return einheitenkarten;
	}

}

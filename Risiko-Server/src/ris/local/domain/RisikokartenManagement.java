package ris.local.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import ris.common.exceptions.LandExistiertNichtException;
import ris.common.valueobjects.Risikokarte;
import ris.common.valueobjects.Risikokarte.Symbol;

public class RisikokartenManagement implements Serializable {

	public ArrayList<Risikokarte> einheitenkarten;
	public WorldManagement worldMg;

	public RisikokartenManagement() {
		einheitenkarten = new ArrayList<Risikokarte>();
		worldMg = new WorldManagement();
		for (int i = 0; i < worldMg.getLaender().size(); i++) {
			if (i % 3 == 0) {
				try {
					einheitenkarten.add(new Risikokarte(Symbol.KANONE, worldMg.getLandById(i)));
				} catch (LandExistiertNichtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (i % 3 == 1) {
				try {
					einheitenkarten.add(new Risikokarte(Symbol.SOLDAT, worldMg.getLandById(i)));
				} catch (LandExistiertNichtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (i % 3 == 2) {
				try {
					einheitenkarten.add(new Risikokarte(Symbol.REITER, worldMg.getLandById(i)));
				} catch (LandExistiertNichtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Collections.shuffle(einheitenkarten);
		// TODO: @tobi Joker einbauen???
	}

	public ArrayList<Risikokarte> getEinheitenkarten() {
		return einheitenkarten;
	}

}

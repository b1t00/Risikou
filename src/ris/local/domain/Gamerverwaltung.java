package ris.local.domain;

import java.util.List;
import java.util.Vector;

import ris.local.valueobjects.Gamer;

public class Gamerverwaltung {

	private List<Gamer> gamerListe = new Vector<>();

	public void addGamer(Gamer gamer) {
		gamerListe.add(gamer);
	}
	
	
}

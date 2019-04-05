package ris.local.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ris.local.valueobjects.Player;

public class Playermanagement {

	private List<Player> gamerListe = new ArrayList<Player>();

	public void addPlayer(String name, String farbe, int nummer) {
		Player player = new Player(name, farbe, nummer);
		gamerListe.add(player);
	}
		
	//public void addElements(ArrayList<Land>) {
		
	//}
}

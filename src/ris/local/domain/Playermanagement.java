package ris.local.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ris.local.valueobjects.Player;

public class Playermanagement {

	private List<Player> gamerListe = new ArrayList<Player>();
	
	public List<Player> getPlayers(){
		return gamerListe;

	}
	
	/*++++++++++Demonstrationszweck+++++++
	 * public void addierePlayer(Player x) {
		gamerListe.add(x);
		
	}*/
	
	/*public void addPlayer(String name, String farbe, int nummer) {
		Player p= new Player(name,farbe,nummer);
		gamerListe.add(p);
	} */

	public Player addPlayer(String name, String farbe, int nummer) { // hier die von Teschke Methode. Warum Player?
		Player player = new Player(name, farbe, nummer);
		gamerListe.add(player);
		return player;
	}
	
	// gibt Anzahl an spielern zur�ck
//	public int getPlayerAnzahl(){
//		return gamerListe.size();
//	}
		
	//public void addElements(ArrayList<Land>) {
		
	//}
}
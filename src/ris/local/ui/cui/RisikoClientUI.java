package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import ris.local.valueobjects.Gamer;
import ris.local.valueobjects.Land;
public class RisikoClientUI {
	ArrayList<Land> inBesitz= new ArrayList<Land>();
	private BufferedReader in;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*public int[] zufallLaenderverteilung(int wieviel) {
	int neueZahl = Math.random();
	int[] arrayInt = new int[wieviel];
	}
	*/
	
	
	
	
	public void legSpielerAn() {
		System.out.println("Name von spieler 1: ");
		Gamer gamer1 = new Gamer(liesEingabe(),inBesitz);
		System.out.println("Name von spieler 2: ");
		Gamer gamer2 = new Gamer(liesEingabe(),inBesitz);
	}
	
	private String liesEingabe() {
			return in.readLine();
	}
	
}
	


package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;

public class RisikoClientUI {
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
		Gamer gamer1 = new Gamer(liesEingabe());
		System.out.println("Name von spieler 2: ");
	}
	
	private String liesEingabe() {
		return in.readLine();
	}
	
}
	
}
,
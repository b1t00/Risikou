package ris.local.domain;

public class Risiko {

	private int spielerAnzahl;
	private PlayerManagement playerManagement;

	public Risiko() {
		playerManagement = new PlayerManagement();
	}

	public void spielerAnlegen(String name,String farbe,  int nr ) {
		this.spielerAnzahl = spielerAnzahl;
		playerManagement.spielerAnlegen(name, farbe, nr);
		
		System.out.println("es gibt " + playerManagement.getSpielerAnzahl() + " Spieler \n");
	}

}
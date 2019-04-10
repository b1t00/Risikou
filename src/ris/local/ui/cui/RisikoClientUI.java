package ris.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import ris.local.domain.Risiko;
import ris.local.domain.Worldmanagement;

import ris.local.valueobjects.Player;
import ris.local.valueobjects.Land;

public class RisikoClientUI {

	private Risiko risiko;
	private BufferedReader in;

	// Konstruktor
	private RisikoClientUI() {
		risiko = new Risiko();
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	// einlesen von Konsole
	private String liesEingabe() throws IOException {
		return in.readLine();
	}

	private void starteSpiel() {
		boolean richtigeEingabe = false;
		String eingabe = "";
		while (!richtigeEingabe) {
			System.out.println("Lust Risiko zu spielen? (y/n) :	");
			try {
				eingabe = liesEingabe();
			} catch (IOException e) {}
			switch (eingabe) {
			case "yes": case "j": case "y":
				try {
					wieVieleSpielerMenu();
				} catch (IOException e) {}
				System.out.println("jetzt beginnt das Spiel"); // Platzhalter für Spielanfang
				richtigeEingabe = true;
				break;
			case "no": case "nö": case "n":
				System.out.println("Risik wird beendet"); // Platzhalter für Spielbeenden TODO: Spiel beenden
				richtigeEingabe = true;
				break;
			default:
				System.out.println("ungültige eingabe");
				richtigeEingabe = false;
			}
		}
		//zufällige Verteilung der Einheiten
		risiko.verteileEinheiten();
	}

	public void wieVieleSpielerMenu() throws IOException {
		String eingabeSpieler, name, farbe;
		int nr;
		System.out.println("Wieviele Spieler soll es geben? :");
		eingabeSpieler = liesEingabe();
		nr = Integer.parseInt(eingabeSpieler);
		for (int i = 1; i <= nr; i++) {
			System.out.println("Wie soll spieler " + i + " heißen? : ");
			name = liesEingabe();
			farbe = farbeAuswaehlen();
			risiko.spielerAnlegen(name, farbe, nr);
		}
	}

	public String farbeAuswaehlen() {
		String farbe = "";
		ArrayList<String> farbenAuswahl = risiko.getFarbauswahl();

		System.out.println("Welche Farbe möchtest nehmen?");
		if (farbenAuswahl.contains("rot")) {
			System.out.println("r : rot");
		}
		if (farbenAuswahl.contains("gruen")) {
			System.out.println("g : grün");
		}
		if (farbenAuswahl.contains("blau")) {
			System.out.println("b : blau");
		}
		try {
			farbe = liesEingabe();
		} catch (IOException e) {}
		switch (farbe) {
		case "r":
			farbe = "rot";
			break;
		case "g":
			farbe = "gruen";
			break;
		case "b":
			farbe = "blau";
			break;
		default:
			farbeAuswaehlen();
		}
		return farbe;
	}
	
	public void setzeStartEinheiten() {
		int anzahlEinheiten = risiko.getAnzahlPlayer() * 5;
		int einheit = 1;
		
		while(anzahlEinheiten > 0) {
			Player aktiverPlayer = risiko.gibAktivenSpieler();
			System.out.println(aktiverPlayer + ": setze eine Einheit.");
			ArrayList <Land> aktiveLaender = aktiverPlayer.gibLaenderAus();
			//den prüfarray brauchen wir, um zu überprüfen, ob die eingabe gültig ist, in den pruefarray werden die möglichen zahlen geschrieben
			ArrayList <Integer> pruefArray = new ArrayList <Integer>();
			
			for (Land land: aktiveLaender) {
				System.out.println(land.getNummer() + " > " + land.getName());
				pruefArray.add(land.getNummer()); 
			}
			int land;
			try{
				//!!! hier muss noch geprüft werden, ob die zahl überhaupt zur auswahl stand > evtl. arraylist land geben lassen und dann mit equals
				land = Integer.parseInt(liesEingabe());
			} catch(IOException e) {}
			risiko.setztEinheit(int land, int einheit);
			anzahlEinheiten--;
			risiko.naechsterPlayer();	
		}
	}
	
	public void round() {
		String input = "";
		while(true) {
			Player aktiverPlayer = risiko.gibAktivenPlayer();
			//spieler bekommt einheiten
			gibMenuAus(aktiverPlayer);
			try {
				input = liesEingabe();
				verarbeiteEingabe(input, aktiverPlayer);
			} catch(IOException e){}

			risiko.naechsterPlayer();
		}
	}
	
	public void gibMenuAus(Player aktiverPlayer) {
		System.out.print(aktiverPlayer + ": Was möchtest du tun?");
		System.out.print("               \n Angreifen: a");
		System.out.print("               \n Einheiten verschieben: e");
		System.out.print("               \n Zug beenden: z");
		System.out.print("               \n Spiel beenden: q");
		System.out.flush();
	}
	
	public void verarbeiteEingabe(String input, Player aktiverPlayer) {
			switch(input) {
			case "a":
				attack(aktiverPlayer);
				break;
			case "e":
				verschiebeEinheiten(aktiverPlayer);
				break;
			case "z":
				risiko.naechsterSpieler();
				System.out.println(aktiverPlayer + " hat seinen Zug beendet.");
				break;
			case "q":
				System.out.println("Risik wird beendet."); //TODO: Spiel beenden
				break;
			default:
				System.out.println("Ungültige Eingabe, bitte wiederholen."); //funktioniert das so?
				gibMenuAus(aktiverPlayer);
				try {
					input = liesEingabe();
					verarbeiteEingabe(input, aktiverPlayer);
				} catch(IOException e){}
				break;
			}
	}
	
	public void attack(Player angreifer) {
		Land attackLand; //Land, das angreift
		Land enemyLand;
		Player defender; //Player-Objekt des angegriffenen Landes
		//int start;	//Nummer vom Land, das angreift
		int ziel;	//Nummer vom Land, das angegriffen wird
		int angriff;	//einheiten, die angreifen
		int defense;	//einheiten, die verteidigen
		boolean kampf = true;
		
		//abfrage, von welchem land welches andere land angegriffen werden soll
		System.out.print(angreifer + " mit welchem Land möchtest du angreifen?");
		laenderAusgeben(angreifer);
		try {
			int start = Integer.parseInt(liesEingabe()); 
			attackLand = risiko.getLandById(start);
		} catch(IOException e) {}
		
		//Abfrage, welches Land attackiert werden soll
		ArrayList<Land> feinde = risiko.getFeinde(angreifer, attackLand);
		System.out.println("Welches Land soll angegriffen werden? \n" + risiko.getFeinde(angreifer, start));
		for (Land land: feinde) {
			System.out.println(land.getNummer() + " > " + land.getName());
		}
		try {
			ziel = Integer.parseInt(liesEingabe());
			enemyLand = risiko.getLandById(ziel);
		} catch(IOException e) {}
		
		//ab hier beginnt das setzen der einheiten von beiden seiten, mehrmals möglich, da eventuell mehrere angriffe möglich sind, bei sieg oder abbruch des angriffs wird kampf auf false gesetzt
		while (kampf) {
			System.out.println("Mit wie vielen Einheiten soll angegriffen werden? Maximal: " + attackLand.getEinheiten());
			try {
				angriff = Integer.parseInt(liesEingabe());
			} catch(IOException e) {}
			
			defender = risiko.getBesitzer(enemyLand);
			System.out.println(defender + ": Mit wievielen Einheiten möchtest du verteidigen? Maximal: " + enemyLand.getEinheiten());
			try {
				defense = Integer.parseInt(liesEingabe());
			} catch(IOException e) {}
			
			//attack-methode gibt string mit gewinner und jeweiligem verlust an einheiten zurück und einheiten werden entsprechend dem Kampfausgang versetzt
			String ergebnis = risiko.attack (attackLand, enemyLand, angriff, defense);
			Player winner = risiko.getWinner();
			System.out.println(ergebnis);
			
			//wenn angreifer gewonnen hat, können weitere einheiten verschoben werden (falls mehr als 1 einheit auf dem angriffsland stehen)
			if (winner.equals(angreifer)) {
				if(attackLand.getEinheiten() > 1) {
					System.out.println("Sollen weitere Einheiten verschoben werden? (y/n) : ");
					String answer = "";
					try {
						answer = liesEingabe();
					} catch(IOException e) {}
					if(answer.equals("y")) {
						System.out.println("Wieviele Einheiten sollen verschoben werden? (Maximal: )" + (attackLand.getEinheiten() - 1));
						int anzahl;
						try {
							anzahl = Integer.parseInt(liesEingabe());
							risiko.verschiebeEinheiten(attackLand, enemyLand, anzahl);
						} catch(IOException e) {}
					}
				}
				//wenn angreifer gewonnen hat, aber nicht mehr einheiten verschoben werden können, ist der angriff beendet
				kampf = false;
				//wenn angreifer nicht gewonnen hat, kann er erneut angreifen TODO: eventuell erst abfrage, ob überhaupt noch angegriffen werden kann
			} else {
				System.out.println(angreifer + ": Erneut angreifen? (yes/no)");
				String answer;
				try {
					answer = liesEingabe();
				} catch(IOException e) {}
				switch (answer) {
				case "yes":
					//da kampf nicht auf false gesetzt wird, springt das programm wieder zum beginn der while-schleife und der angriff wird wiederholt
					break;
				case "no":
					kampf = false;
					break;
				}
			}
		}
	}
	
	public void laenderAusgeben(Player player) {
		ArrayList <Land> angriffsLaender = player.gibLaenderAus();
		for (Land land: angriffsLaender) {
			System.out.println(land.getNummer() + " > " + land.getName());
		}
		//evtl. pruefarray zurückgeben
	}
	
	public void verschiebeEinheiten(Player aktiverPlayer) {
		Land start;
		int ziel;
		int anzahl;
		System.out.println("Einheiten verschieben von: \n");
		laenderAusgeben(aktiverPlayer);
		try {
			int von = Integer.parseInt(liesEingabe());
			start = risiko.getLandById(von);
		} catch(IOException e) {}
		System.out.println("Anzahl der Einheiten: (Maximal) " + (start.getEinheiten() - 1));
		try {
			anzahl = Integer.parseInt(liesEingabe());
		} catch(IOException e) {}
		System.out.println("Einheiten verschieben nach: \n");
		ArrayList <Land> nachbarLaender = risiko.gibNachbarn(start);
		for (Land land: nachbarLaender) {
			System.out.println(land.getNummer() + " > " + land.getName());
		}
		try {
			ziel = Integer.parseInt(liesEingabe());
			risiko.verschiebeEinheiten(start, ziel, anzahl);
		} catch(IOException e) {}
	}

public static void main(String[] args) {
	RisikoClientUI cui = new RisikoClientUI();
	cui.run();
}

public void run() {		
	starteSpiel();
	setzeStartEinheiten();
	round();
}

	public static void main(String[] args) {
		RisikoClientUI cui = new RisikoClientUI();
		cui.anfangsMenue();
		System.out.println("hey");
		Risiko risiko = new Risiko();
		cui.risiko.verteileEinheiten();
		cui.risiko.whoBegins();
	}

}


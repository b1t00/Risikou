package ris.server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.ZuWenigEinheitenException;
import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Land;

public class ClientRequestProcessor implements Runnable {

	private ArrayList<Object> aL;	
	private RisikoInterface risiko;
	private Socket clientSocket;
	private BufferedReader in;
	private PrintStream out;
	private ObjectOutputStream oos;

	public ClientRequestProcessor(Socket socket, RisikoInterface risiko) {
		this.clientSocket = socket;
		this.risiko = risiko;

		try {
//			versuch
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintStream(clientSocket.getOutputStream());

		} catch (IOException e) {
			try {
				clientSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void run() {

		String input = "";

		out.println("Server an Client: Bin bereit fuer den Kampf");

		do {
			// Beginn der Benutzerinteraktion:
			// Aktion vom Client einlesen [dann ggf. weitere Daten einlesen ...]
			try {
				input = in.readLine();
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
				System.out.println(e.getMessage());
				continue;
			}
			switch(input) {
//			case null : 
//				// input wird von readLine() auf null gesetzt, wenn Client Verbindung abbricht
//				// Einfach behandeln wie ein "quit"
//				input = "q";
//				break;
			case "getFarbauswahl" :
				getFarbauswahl();
				break;
			case "playerAnlegen": 
				playerAnlegen();
				break;
			case "spielAufbau":
				spielAufbau();
				break;
			case "gibAktivenPlayer":
				try {
					oos.writeObject(risiko.gibAktivenPlayer());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "getColorArray":
				try {
					oos.writeObject(risiko.getColorArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "setSpielerAnzahl":
				int spielerAnzahl = 0;
				try {
					spielerAnzahl = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				risiko.setSpielerAnzahl(spielerAnzahl);
			case "getSpielerAnzahl":
				out.println(risiko.getSpielerAnzahl());
			case "getPlayerArray":
				try {
					oos.writeObject(risiko.getPlayerArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "getCurrentState":
				try {
					oos.writeObject(risiko.getCurrentState());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "spielSpeichern":
				String name = null;
				try {
					name = in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				risiko.spielSpeichern(name);
				break;
			case "getSpielladeDateien":
				try {
					oos.writeObject(risiko.getSpielladeDateien());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "spielLaden":
				String datei = null;
				try {
					datei = in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					risiko.spielLaden(datei);
				} catch (SpielerNameExistiertBereitsException | ZuWenigEinheitenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case "getLandClickZeit":
				try {
					oos.writeObject(risiko.getLandClickZeit());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "setLandClickZeit":
				String clickBar = ""; 
				try {
					clickBar = in.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				boolean obLandClickBar = Boolean.parseBoolean(clickBar);
				risiko.setLandClickZeit(obLandClickBar);
				break;
			case "getTauschZeit":
				try {
					oos.writeObject(risiko.getTauschZeit());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "setTauschZeit":
				boolean obTauschBar = false;
				String tauschBar = ""; 
				try {
					tauschBar = in.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (tauschBar.equals(true))
					obLandClickBar = true;
				risiko.setLandClickZeit(obTauschBar);
				break;
			case "errechneVerfuegbareEinheiten":
				try {
					oos.writeObject(risiko.errechneVerfuegbareEinheiten());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "setEinheiten":
				int landID = 0;
				int units = 0;
				Land land = null;
				try {
					landID = Integer.parseInt(in.readLine());
					units = Integer.parseInt(in.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					land = risiko.getLandById(landID);
				} catch (LandExistiertNichtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				risiko.setEinheiten(land, units);
				break;
			case "getLandById":
				try {
					int landById = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
//			if (input == null) {
//				// input wird von readLine() auf null gesetzt, wenn Client Verbindung abbricht
//				// Einfach behandeln wie ein "quit"
//				input = "q";
//			} else if (input.equals("fa")) {
//				// Aktion "Bücher _a_usgeben" gewählt
//				getFarben();
//			}

		} while (!(input.equals("q")));
	}

	public void getFarbauswahl() {
		ArrayList<String> farben;
		farben = risiko.getFarbauswahl();
		out.println(farben.size());

		for (String farbe : farben) {
			out.println(farbe);
		}
	}
	
	public void playerAnlegen() {
		String name;
		try {
			name = in.readLine();
			String farbe = in.readLine();
			
			int iD = Integer.parseInt(in.readLine());
			try {
				risiko.playerAnlegen(name, farbe, iD);
			} catch (SpielerNameExistiertBereitsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) { 	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void spielAufbau() {
		risiko.spielAufbau();
	}
	public void schickeArrayList(Object o, ArrayList<Object> aL){
		
		System.out.println(aL.size());
	}
		
}

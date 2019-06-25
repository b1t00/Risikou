package ris.server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.ZuWenigEinheitenException;
import ris.common.interfaces.RisikoInterface;
import ris.common.interfaces.ServerListener;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.State;

public class ClientRequestProcessor implements Runnable {

	private ArrayList<Object> aL;	
	private RisikoInterface risiko;
	private Socket clientSocket;
	private BufferedReader in;
	private PrintStream out;
	private ObjectOutputStream oos;
	private ArrayList<ServerListener> allServerListeners;

	public ClientRequestProcessor(OutputStream outClient, Socket clientSocket, RisikoInterface risiko, ArrayList<ServerListener> allServerListeners) {
		this.clientSocket = clientSocket;
		this.out = new PrintStream(outClient);
		this.risiko = risiko;
		this.allServerListeners = allServerListeners;

		try {
			oos = new ObjectOutputStream(out);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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
			case "gibAktivenPlayer":
				try {
					oos.reset();
					oos.writeObject(risiko.gibAktivenPlayer());
					oos.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "getColorArray":
				try {
//					oos.reset();
					oos.writeObject(risiko.getColorArray());
					oos.reset();
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
/*				
				for (ServerListener sl : allServerListeners) {
					sl.handleEvent(e);
				}
				*/
				break;
			case "getSpielerAnzahl":
				try {
					oos.reset();
					oos.writeObject(risiko.getSpielerAnzahl());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			case "getPlayerArray":
				try {
					oos.reset();
					oos.writeObject(risiko.getPlayerArray());					
					oos.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "getCurrentState":
				try {
					oos.reset();
					oos.writeObject(risiko.getCurrentState());
					oos.reset();
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
					oos.reset();
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
					oos.reset();
					oos.writeObject(risiko.getLandClickZeit());
					oos.reset();
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
					oos.reset();
					oos.writeObject(risiko.getTauschZeit());
					oos.reset();
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
					oos.reset();
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
			case "allUpdate":
				String updateState = null;
//				bekommt von der gui den befehl, allen clients zu sagen, dass sie sich updaten sollen
				try {
//					durch den state wird das update erm�glicht
					updateState = (String) in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(ServerListener sl: allServerListeners) {
					sl.handleEvent("updateDialog");
					sl.handleEvent(updateState);
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
		try {
			oos.writeObject(risiko.getFarbauswahl());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if(risiko.getPlayerArray().size() == risiko.getSpielerAnzahl()) {
			risiko.spielAufbau();
			for(ServerListener sl: allServerListeners) {
				sl.handleEvent("initializeGamePanel");
			}
		}
	}
	
//	public void schickeArrayList(Object o, ArrayList<Object> aL){
//		System.out.println(aL.size());
//	}
		
}

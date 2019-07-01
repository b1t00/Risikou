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
	//private PrintStream out;
	private ObjectOutputStream oos;
	private ArrayList<ServerListener> allServerListeners;

	public ClientRequestProcessor(ObjectOutputStream outClient, Socket clientSocket, RisikoInterface risiko, ArrayList<ServerListener> allServerListeners) {
		System.out.println("client request processor begin");
		this.clientSocket = clientSocket;
		//this.out = new PrintStream(outClient);
		this.risiko = risiko;
		this.allServerListeners = allServerListeners;

		try {
			oos = outClient; //new ObjectOutputStream(out);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		} catch (IOException e) {
			System.out.println("hab ich nicht");
			try {
				clientSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("client request processor finished");
	}
	
	@Override
	public void run() {
		System.out.println("thread started - ready for battle - waiting a second");
		System.out.println("sende init message to server");
		String input = "";
		//out.println("Server an Client: Bin bereit fuer den Kampf");
		try {
			oos.reset();
			oos.writeObject("bereit f�r den kampf");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		do {
			// Beginn der Benutzerinteraktion:
			// Aktion vom Client einlesen [dann ggf. weitere Daten einlesen ...]
			try {
				System.out.println("warte auf client");
				input = in.readLine();
				System.out.println("input crp : " + input);
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
				System.out.println(e.getMessage());
				continue;
			}
			switch(input) {
			case "sentAliveMessage":
				try {
					oos.reset();
					oos.writeObject("I'm alive");
					oos.reset();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
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
					oos.reset();
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
			case "kannAngreifen":
				System.out.println("bin im crp kann angreifen");
				try {
					oos.reset();
					oos.writeObject(risiko.kannAngreifen());
					oos.reset();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				break;
			case "getSpielerAnzahl":
				try {
					oos.reset();
					oos.writeObject(risiko.getSpielerAnzahl());
					oos.reset();
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
					System.out.println("in crp fassade fragt nach state :" + risiko.getCurrentState());
					oos.reset();
					oos.writeObject(risiko.getCurrentState());
					oos.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "attackLandGueltig":																				//attackLandGueltig
				attackLandGueltig();
				break;
			case "defenseLandGueltig":																				//attackLandGueltig
				defenseLandGueltig();
				break;
			case "setNextState":
				risiko.setNextState();
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
				break;
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
				setTauschZeit();
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
				setEinheiten();
				break;
			case "getLandById":
				getLandById();
				break;
			case "rundeMissionComplete":
				try {
					oos.reset();
					oos.writeObject(risiko.rundeMissionComplete());
					oos.reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "allMissionsComplete":
				try {
					oos.reset();
					oos.writeObject(risiko.allMissionsComplete());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "allUpdate":
				allUpdate();
				break;
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
		String name = null;
		String farbe = null;
		try {
			name = in.readLine();
			farbe = in.readLine();
			
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
		System.out.println("neuen spieler angelegt: " + name + " farbe " + farbe);
		if(risiko.getPlayerArray().size() == risiko.getSpielerAnzahl()) {
			risiko.spielAufbau();
			for(ServerListener sl: allServerListeners) {
				sl.handleEvent("initializeGamePanel");
			}
		}
	}
	public void attackLandGueltig() {
				try {
					int att = Integer.parseInt(in.readLine());
					
					oos.reset();
					oos.writeObject(risiko.attackLandGueltig(risiko.getLandById(att)));
					oos.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LandExistiertNichtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	public void defenseLandGueltig() {
		Integer att = null;
		Integer def = null;
		try {
			att = Integer.parseInt(in.readLine());
			def = Integer.parseInt(in.readLine());
			
			oos.reset();
			oos.writeObject(risiko.defenseLandGueltig(risiko.getLandById(att),risiko.getLandById(def)));
			oos.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LandExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientsUpdaten("beginAttack");
		clientsUpdaten(att.toString());
		clientsUpdaten(def.toString());
	}
	
	public void setEinheiten() {
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
		for(ServerListener sl: allServerListeners) {
			System.out.println("will er land updaten?? crp");
			sl.handleEvent("updateDialog(Land)");
			sl.handleEvent(land.getName());
		}
	}
	
	public void setTauschZeit() {
		boolean obTauschBar = false;
		String tauschBar = ""; 
		try {
			tauschBar = in.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (tauschBar.equals(true))
			obTauschBar = true;
		risiko.setLandClickZeit(obTauschBar);
	}
	
	public void getLandById() {
		int iD = 0;
		try {
			iD = Integer.parseInt(in.readLine());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oos.reset();
			oos.writeObject(risiko.getLandById(iD));
			oos.reset();
		} catch (IOException | LandExistiertNichtException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void clientsUpdaten(String welchesUpdate) {
		for(ServerListener sl: allServerListeners) {
			sl.handleEvent(welchesUpdate);
		}
	}

	public void allUpdate() {
		String updateState = null;
//		bekommt von der gui den befehl, allen clients zu sagen, dass sie sich updaten sollen
		try {
//			durch den state wird das update erm�glicht
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
		
}

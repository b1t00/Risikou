package ris.server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.LandInBesitzException;
import ris.common.exceptions.LandNichtInBesitzException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.UngueltigeAnzahlEinheitenException;
import ris.common.exceptions.UngueltigeAnzahlSpielerException;
import ris.common.interfaces.RisikoInterface;
import ris.common.interfaces.ServerListener;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.GameObject;
import ris.common.valueobjects.Land;

public class ClientRequestProcessor implements Runnable {

	private ArrayList<Object> aL;
	private RisikoInterface risiko;
	private Socket clientSocket;
	private BufferedReader in;
	// private PrintStream out;
	private ObjectOutputStream oos;
	private ArrayList<ServerListener> allServerListeners;
	private boolean closed = false;

	public ClientRequestProcessor(ObjectOutputStream outClient, Socket clientSocket, RisikoInterface risiko,
			ArrayList<ServerListener> allServerListeners) {
		System.out.println("client request processor begin");
		this.clientSocket = clientSocket;
		// this.out = new PrintStream(outClient);
		this.risiko = risiko;
		this.allServerListeners = allServerListeners;

		try {
			oos = outClient; // new ObjectOutputStream(out);
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
		// out.println("Server an Client: Bin bereit fuer den Kampf");
		try {
			oos.reset();
			oos.writeObject("bereit für den kampf");
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
//				if(e instanceof SocketException) {
//					try {
//						clientSocket.close();
//					} catch (IOException e1) {
//						System.out.println("Fehler beim Beenden der Verbindung");
//						e1.printStackTrace();
//					}
//				}
				continue;
			}
			switch (input) {
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
			case "aksForServerListenerNr":
				for (int i = 0; i < allServerListeners.size(); i++) {
					allServerListeners.get(i).schickeReinesObject(allServerListeners.size());
					allServerListeners.get(i).schickeReinesObject(i);
				}
				break;
			case "aktiveClientAskHowMany":
				int clientNr = -99;
				try {
					clientNr = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				for (int i = 0; i < allServerListeners.size(); i++) {
					if (!(i == clientNr)) {
						allServerListeners.get(i).handleEvent("enableStartBtn");
					}
				}
				break;
			case "pressBackButn":
				int clientNr2 = -99;
				try {
					clientNr2 = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				for (int i = 0; i < allServerListeners.size(); i++) {
					if (!(i == clientNr2)) {
						allServerListeners.get(i).handleEvent("unenableStartBtn");
					}
				}
				break;
			case "spielEintreitenBtn":
				int clientNr3 = -99;
				try {
					clientNr3 = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				for (int i = 0; i < allServerListeners.size(); i++) {
					if (!(i == clientNr3)) {
						allServerListeners.get(i).handleEvent("spielEintreitenBtn");
					}
				}
				break;
//			case null : 
//				// input wird von readLine() auf null gesetzt, wenn Client Verbindung abbricht
//				// Einfach behandeln wie ein "quit"
//				input = "q";
//				break;
			case "getFarbauswahl":
				getFarbauswahl();
				break;
			case "setFarbeAuswaehlen":
				try {
					risiko.setFarbeAuswaehlen(in.readLine());
				} catch (IOException e4) {

					e4.printStackTrace();
				}
				break;
			case "playerAnlegen":
				playerAnlegen();
				break;
			case "gibAktivenPlayer":
				try {
					oos.reset();
					oos.writeObject(risiko.gibAktivenPlayer());
					oos.reset();
					System.out.println("aktiver Player gesendet");
				} catch (IOException e) {
					System.out.println("Fehler beim Senden von aktiver Player");
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
					System.out.println("Fehler beim Senden von Color-Array");
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
				try {
					risiko.setSpielerAnzahl(spielerAnzahl);
					try {
						oos.reset();
						oos.writeObject("hat geklappt");
						oos.reset();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (UngueltigeAnzahlSpielerException e4) {
					try {
						oos.reset();
						oos.writeObject(e4);
						oos.reset();
						System.out.println(e4.getLocalizedMessage());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					e4.printStackTrace();
				}
				/*
				 * for (ServerListener sl : allServerListeners) { sl.handleEvent(e); }
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
					System.out.println("Fehler beim Senden von playerarray");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "getLaender":
				try {
					oos.reset();
					oos.writeObject(risiko.getLaender());
					oos.reset();
				} catch (IOException e) {
					System.out.println("Fehler beim Senden von laenderarray");
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
			case "attackLandGueltig": // attackLandGueltig
				attackLandGueltig();
				break;
			case "defenseLandGueltig": // attackLandGueltig
				defenseLandGueltig();
				break;
			case "attackStart":
				attackStart();
				break;
			case "getDefLandUnits":
				try {
					oos.reset();
					oos.writeObject(risiko.getDefLandUnits());
					oos.reset();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			case "attackFinal":
				attackFinal();
				break;
			case "kannVerschieben":
				int nr = -3; // TODO Achtung: es gibt kein spieler mit dem wert -3
				try {
					nr = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try {
					oos.reset();
					oos.writeObject(risiko.kannVerschieben(risiko.getPlayerById(nr)));
					oos.reset();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			case "moveFromLandGueltig": // attackLandGueltig
				moveFromLandGueltig();
				break;
			case "moveToLandGueltig": // attackLandGueltig
				moveToLandGueltig();
				break;
			case "moveUnitsGueltig":
				moveUnitsGueltig();
				break;
//			case "moveUnits":
//				try {
//					int vonMov = Integer.parseInt(in.readLine());
//					int zuMov = Integer.parseInt(in.readLine());
//					int unitsMov = Integer.parseInt(in.readLine());
//					try {
//						risiko.moveUnits(risiko.getLandById(vonMov), risiko.getLandById(zuMov), unitsMov);
//					} catch (ZuWenigEinheitenException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (LandExistiertNichtException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				} catch (NumberFormatException | IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				break;
			case "setNextState":
				risiko.setNextState();
				break;
			case "setNextPlayer":
				risiko.setNextPlayer();
				allServerListeners.get(risiko.gibAktivenPlayer().getNummer()).handleEvent("anDerReihe");
				break;
			case "spielSpeichern":
				String name = null;
				try {
					name = in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("CRP ich speicher grad die Datei " + name);
				risiko.spielSpeichern(name);
				disconnect();
				break;
			case "getSpielladeDateien":
				int x = 1;
				int y = 1;
				String[] dateien = risiko.getSpielladeDateien();
				try {
					oos.reset();
					for (String s : dateien) {
						if (s == null) {
							System.out.println(x);
							dateien[x - 1] = "freier Speicher";
						}

						System.out.println("datei " + x++ + " " + s);
					}
					for (String s : dateien) {
						System.out.println("datei " + y++ + " " + s);
					}
					oos.writeObject(dateien);
					System.out.println("schicke ich die sachen los?");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "spielLadenTrue":
				risiko.spielLadenTrue();
				System.out.println("bin auch iner crp");
				updateExceptAktiverPlayer("spielLadenTrue");
				break;
			case "spielLaden":
				String datei = null;
				GameObject ladeDatei = null;
				try {
					datei = in.readLine();
					ladeDatei = risiko.gameObjectLaden(datei);
					oos.reset();
					oos.writeObject(ladeDatei);
					oos.reset();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					risiko.spielLaden(datei);
				} catch (SpielerNameExistiertBereitsException | LandExistiertNichtException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// achtung das muss noch umgeschrieben werden

				break;
			case "kannSpielGeladenWerden":
				kannSpielGeladenWerden();
				break;
			case "getLandClickZeit":
				try {
					oos.reset();
					oos.writeObject(risiko.getLandClickZeit());
					oos.reset();
				} catch (IOException e) {
					System.out.println("Problem beim Schicken von LandClickZeit");
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
					oos.reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "getGewinner":
				try {
					oos.reset();
					oos.writeObject(risiko.getGewinner());
					oos.reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				clientsUpdaten("gewinner gefunden");
				clientsUpdaten(risiko.getGewinner().getName());
				disconnect();
				break;
			case "zieheEinheitenkarte":
				try {
					oos.reset();
					oos.writeObject(risiko.zieheEinheitenkarte());
					oos.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
//				// Aktion "BÃ¼cher _a_usgeben" gewÃ¤hlt
//				getFarben();
//			}

		} while (!closed);
	}

	private void kannSpielGeladenWerden() {
		System.out.println(" CRP GEHT DAS ? <-----------------------" + risiko.getGameDatei().getAllePlayer().size());
		// TODO: button auf ausstellen
		if (true) {
			System.out.println("muesste gehen");
		} else {
			for (ServerListener sl : allServerListeners) {
				sl.handleEvent("initializeGamePanel");
			}

		}

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
				oos.reset();
				oos.writeObject("hat geklappt");
				oos.reset();
			} catch (SpielerNameExistiertBereitsException e) {
				oos.reset();
				oos.writeObject(e);
				oos.reset();
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("neuen spieler angelegt: " + name + " farbe " + farbe);
		if (risiko.getPlayerArray().size() == risiko.getSpielerAnzahl()) {
			risiko.spielAufbau();
			for (ServerListener sl : allServerListeners) {
				sl.handleEvent("initializeGamePanel");
			}
		} else {

			for (int i = 0; i < allServerListeners.size(); i++) {

				allServerListeners.get(i).handleEvent("spielEintreitenBtn");

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
			oos.writeObject(risiko.defenseLandGueltig(risiko.getLandById(att), risiko.getLandById(def)));
			oos.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LandExistiertNichtException e) {
			e.printStackTrace();
		}
	}

	public void attackStart() {
		Land attLand = null;
		Land defLand = null;
		int attUnits = 0;

		try {
			attLand = risiko.getLandById(Integer.parseInt(in.readLine()));
			defLand = risiko.getLandById(Integer.parseInt(in.readLine()));
			attUnits = Integer.parseInt(in.readLine());
		} catch (NumberFormatException | LandExistiertNichtException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		 risiko.attackStart(attLand, defLand, attUnits);
			 try {
				oos.reset();
				oos.writeObject("hat geklappt");
				oos.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 updateExceptAktiverPlayer("attackStart");
			 updateExceptAktiverPlayer(attLand.getName());
			 updateExceptAktiverPlayer(defLand.getName());
			 updateExceptAktiverPlayer(attLand.getBesitzer().getName());
			 updateExceptAktiverPlayer(defLand.getBesitzer().getName());
		} catch(LandNichtInBesitzException | LandInBesitzException e) {
			try {
				oos.reset();
				oos.writeObject(e);
				oos.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void attackFinal() {
		int defUnits = 0;
		try {
			defUnits = Integer.parseInt(in.readLine());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Attack attackObjekt = null;
		attackObjekt = risiko.attackFinal(defUnits);
		try {
			oos.reset();
			oos.writeObject(attackObjekt);
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Winner: " + attackObjekt.getWinner());
		clientsUpdaten("attackFinal");
		schickeAttackObjekt(attackObjekt);
	}

	public void moveFromLandGueltig() {
		try {
			int von = Integer.parseInt(in.readLine());

			oos.reset();
			oos.writeObject(risiko.moveFromLandGueltig(risiko.getLandById(von)));
			oos.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LandExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveToLandGueltig() {
		Integer von = null;
		Integer zu = null;
		try {
			von = Integer.parseInt(in.readLine());
			zu = Integer.parseInt(in.readLine());

			oos.reset();
			oos.writeObject(risiko.moveToLandGueltig(risiko.getLandById(von), risiko.getLandById(zu)));
			oos.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LandExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveUnitsGueltig() {
		Integer vonGue = null;
		Integer zuGue = null;
		Integer unitsGue = null;
		try {
			vonGue = Integer.parseInt(in.readLine());
			zuGue = Integer.parseInt(in.readLine());
			unitsGue = Integer.parseInt(in.readLine());
			oos.reset();
			oos.writeObject(risiko.moveUnitsGueltig(risiko.getLandById(vonGue), risiko.getLandById(zuGue), unitsGue));
			System.out.println("move units gültig? "
					+ risiko.moveUnitsGueltig(risiko.getLandById(vonGue), risiko.getLandById(zuGue), unitsGue));
			oos.reset();
		} catch (NumberFormatException | LandExistiertNichtException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (risiko.moveUnitsGueltig(risiko.getLandById(vonGue), risiko.getLandById(zuGue), unitsGue)) {
					try {
						risiko.moveUnits(risiko.getLandById(vonGue), risiko.getLandById(zuGue), unitsGue);
					} catch (LandExistiertNichtException | UngueltigeAnzahlEinheitenException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					for (int i = 0; i < allServerListeners.size(); i++) {
						if (!(i == risiko.gibAktivenPlayer().getNummer())) {
							try {
								allServerListeners.get(i).handleEvent("updateMoveUnits");
								allServerListeners.get(i).schickeReinesObject((risiko.getLandById(vonGue)));
								allServerListeners.get(i).schickeReinesObject((risiko.getLandById(zuGue)));
								allServerListeners.get(i).schickeReinesObject(unitsGue);
								allServerListeners.get(i).handleEvent(risiko.gibAktivenPlayer().getName());
							} catch (LandExistiertNichtException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
		} catch (LandExistiertNichtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void moveUnitsGueltig() {
//		Integer vonGue = null;
//		Integer zuGue = null;
//		Integer unitsGue = null;
//		try {
//			vonGue = Integer.parseInt(in.readLine());
//			zuGue = Integer.parseInt(in.readLine());
//			unitsGue = Integer.parseInt(in.readLine());
//
//			oos.reset();
//			oos.writeObject(risiko.moveUnitsGueltig(risiko.getLandById(vonGue), risiko.getLandById(zuGue), unitsGue));
//			oos.reset();
//
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (LandExistiertNichtException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (int i = 0; i < allServerListeners.size(); i++) {
//			if (!(i == risiko.gibAktivenPlayer().getNummer())) {
//				try {
//					allServerListeners.get(i).handleEvent("updateMoveUnits");
//					allServerListeners.get(i).schickeReinesObject((risiko.getLandById(vonGue)));
//					allServerListeners.get(i).schickeReinesObject((risiko.getLandById(zuGue)));
//					allServerListeners.get(i).schickeReinesObject(unitsGue);
//				} catch (LandExistiertNichtException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//	};

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
		try {
			risiko.setEinheiten(land, units);
			try {
				oos.reset();
				oos.writeObject("hat geklappt");
				oos.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UngueltigeAnzahlEinheitenException e) {
			try {
				oos.reset();
				oos.writeObject(e);
				oos.reset();
				oos.writeObject(land.getEinheiten()-1);
				oos.reset();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		for (int i = 0; i < allServerListeners.size(); i++) {
			if (!(i == risiko.gibAktivenPlayer().getNummer())) {
				allServerListeners.get(i).handleEvent("updateDialog(Land)");
				allServerListeners.get(i).handleEvent(land.getName());
				allServerListeners.get(i).handleEvent(risiko.gibAktivenPlayer().getName());
			}
		}
//		for(ServerListener sl: allServerListeners) {
//			System.out.println("will er land updaten?? crp");
//			sl.handleEvent("updateDialog(Land)");
//			sl.handleEvent(land.getName());
//		}
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
		if (tauschBar.equals("true"))
			obTauschBar = true;
		risiko.setTauschZeit(obTauschBar);
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
			try {
				oos.reset();
				oos.writeObject("Error");
				oos.reset();
				System.out.println(e1);
				System.out.println("excpetion im server");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			e1.printStackTrace();
		}
	}

	public void clientsUpdaten(String welchesUpdate) {
		for (ServerListener sl : allServerListeners) {
			sl.handleEvent(welchesUpdate);
		}
	}

	public void updateExceptAktiverPlayer(String welchesUpdate) {
		for (int i = 0; i < allServerListeners.size(); i++) {
			if (!(i == risiko.gibAktivenPlayer().getNummer())) {
				allServerListeners.get(i).handleEvent(welchesUpdate);
			}
		}
	}

	public void schickeAttackObjekt(Attack aO) {
		for (ServerListener sl : allServerListeners) {
			sl.schickeObjekt(aO);
		}
	}

	public void allUpdate() {
		String updateState = null;
//		bekommt von der gui den befehl, allen clients zu sagen, dass sie sich updaten sollen
		try {
//			durch den state wird das update ermöglicht
			updateState = (String) in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < allServerListeners.size(); i++) {
//		for(ServerListener sl: allServerListeners) {
			if (!(i == risiko.gibAktivenPlayer().getNummer())) {
				allServerListeners.get(i).handleEvent("updateDialog");
				allServerListeners.get(i).handleEvent(updateState);
			}
		}
	}
	
	private void disconnect() {
		for (ServerListener sl : allServerListeners) {
			sl.beendeVerbindung();
		}
		try {
			clientSocket.close();
			System.out.println("ich schließe, Feierabend!");
			closed = true;
		} catch (IOException e) {
			System.out.println("Fehler beim Beenden der Verbindung");
			e.printStackTrace();
		}
	}

}

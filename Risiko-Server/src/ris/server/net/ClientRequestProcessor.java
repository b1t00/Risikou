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

/*
 * @class nimmt String(anweisung) vom client an und verarbeitet sie
 * @param input = "anweisung"
 */
public class ClientRequestProcessor implements Runnable {

	private ArrayList<Object> aL;
	private RisikoInterface risiko;
	private Socket clientSocket;
	private BufferedReader in;
	private ObjectOutputStream oos;
	private ArrayList<ServerListener> allServerListeners;
	private boolean closed = false;

	public ClientRequestProcessor(ObjectOutputStream outClient, Socket clientSocket, RisikoInterface risiko,
			ArrayList<ServerListener> allServerListeners) {
		System.out.println("client request processor begin");
		this.clientSocket = clientSocket;
		this.risiko = risiko;
		this.allServerListeners = allServerListeners;

		try {
			oos = outClient;
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		} catch (IOException e) {
			System.out.println("hab ich nicht");
			try {
				clientSocket.close();
			} catch (IOException e1) {
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
		try {
			oos.reset();
			oos.writeObject("bereit für den kampf");
		} catch (Exception e) {
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
			switch (input) {
			/*
			 * sent live message wird gesendet damit der ServerRequestProcessor nicht mehr
			 * "zuhoert", nach dem verarbeiten von do not listen in den DoNotListen Mode
			 * geht.
			 */
			case "doNotListen":
				try {
					oos.reset();
					oos.writeObject("Do not listen");
					oos.reset();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			/*
			 * methode die dem Client seine Nummer Im ServerListener verraet
			 */
			case "aksForServerListenerNr":
				for (int i = 0; i < allServerListeners.size(); i++) {
					allServerListeners.get(i).schickeReinesObject(allServerListeners.size());
					allServerListeners.get(i).schickeReinesObject(i);
				}
				break;
			case "aktiveClientAskHowMany":
				int clientNr = -99; // koentte man als error wert benutzens
				try {
					clientNr = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e4) {
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
					e.printStackTrace();
				}
				break;
			case "setSpielerAnzahl":
				int spielerAnzahl = 0;
				try {
					spielerAnzahl = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e2) {
					e2.printStackTrace();
				}
				try {
					risiko.setSpielerAnzahl(spielerAnzahl);
					try {
						oos.reset();
						oos.writeObject("hat geklappt");
						oos.reset();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (UngueltigeAnzahlSpielerException e4) {
					try {
						oos.reset();
						oos.writeObject(e4);
						oos.reset();
						System.out.println(e4.getLocalizedMessage());
					} catch (IOException e) {
						e.printStackTrace();
					}
					e4.printStackTrace();
				}
				break;
			case "kannAngreifen":
				try {
					oos.reset();
					oos.writeObject(risiko.kannAngreifen());
					oos.reset();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
				break;
			case "getSpielerAnzahl":
				try {
					oos.reset();
					oos.writeObject(risiko.getSpielerAnzahl());
					oos.reset();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				break;
			case "getPlayerArray":
				try {
					oos.reset();
					oos.writeObject(risiko.getPlayerArray());
					oos.reset();
				} catch (IOException e) {
					System.err.println("Fehler beim Senden von playerarray");
					e.printStackTrace();
				}
				break;
			case "getLaender":
				try {
					oos.reset();
					oos.writeObject(risiko.getLaender());
					oos.reset();
				} catch (IOException e) {
					System.err.println("Fehler beim Senden von laenderarray");
					e.printStackTrace();
				}
				break;
			case "getCurrentState":
				try {
					oos.reset();
					oos.writeObject(risiko.getCurrentState());
					oos.reset();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "attackLandGueltig":
				attackLandGueltig();
				break;
			case "defenseLandGueltig":
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
					e2.printStackTrace();
				}
				break;
			case "attackFinal":
				attackFinal();
				break;
			case "kannVerschieben":
				int nr = -3; // Default: Achtung: es gibt kein spieler mit dem wert -3
				try {
					nr = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e3) {
					e3.printStackTrace();
				}
				try {
					oos.reset();
					oos.writeObject(risiko.kannVerschieben(risiko.getPlayerById(nr)));
					oos.reset();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				break;
			case "moveFromLandGueltig":
				moveFromLandGueltig();
				break;
			case "moveToLandGueltig":
				moveToLandGueltig();
				break;
			case "moveUnitsGueltig":
				moveUnitsGueltig();
				break;
			case "setNextState":
				risiko.setNextState();
				break;
			case "setNextPlayer":
				risiko.setNextPlayer();
				allServerListeners.get(risiko.gibAktivenPlayer().getNummer()).handleEvent("anDerReihe"); // TODO:Achtung:
																											// muesste
																											// noch
																											// geandert
																											// werden
																											// weil
																											// serverListenerNr
																											// ist nicht
																											// unbedingt
																											// spielerNr
				break;
			case "spielSpeichern":
				String name = null;
				try {
					name = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
					}
					for (String s : dateien) {
					}
					oos.writeObject(dateien);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case "gameReady":
				try {
					oos.reset();
					oos.writeObject(risiko.gameNotReady());
					oos.reset();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				break;
			case "gameNotReady":
				risiko.setSpielNotReady();
				break;
			case "spielLadenTrue":
				risiko.spielLadenTrue();
				updateExceptAktiverPlayer("spielLadenTrue");
				break;
			case "spielLaden":
				String datei = null;

				try {
					datei = in.readLine();
					risiko.gameObjectLaden(datei);

				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					risiko.spielLaden(datei);
				} catch (SpielerNameExistiertBereitsException | LandExistiertNichtException e) {
					e.printStackTrace();
				}
				break;
			case "kannSpielGeladenWerden":
				kannSpielGeladenWerden();
				break;
			case "getGameDatei":
				GameObject ladeDatei = null;
				ladeDatei = risiko.getGeladenesSpiel();
				try {
					oos.reset();
					oos.writeObject(ladeDatei);
					oos.reset();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				break;
			case "spielWurdeGeladen":
				try {
					oos.reset();
					oos.writeObject(risiko.spielWurdeGeladen());
					oos.reset();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				break;
			case "spielerWurdeGeladen":
				int geladeneSpieler = risiko.wieVieleSpielerImGame();
				if (geladeneSpieler == risiko.getGeladenesSpiel().getAllePlayer().size()) {
					for (ServerListener sl : allServerListeners) {
						sl.handleEvent("initializeGamePanel");
					}
				}
				break;
			case "getLandClickZeit":
				try {
					oos.reset();
					oos.writeObject(risiko.getLandClickZeit());
					oos.reset();
				} catch (IOException e) {
					System.out.println("Problem beim Schicken von LandClickZeit");
					e.printStackTrace();
				}
				break;
			case "setLandClickZeit":
				String clickBar = "";
				try {
					clickBar = in.readLine();
				} catch (IOException e1) {
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
					e1.printStackTrace();
				}
				break;
			case "allMissionsComplete":
				try {
					oos.reset();
					oos.writeObject(risiko.allMissionsComplete());
					oos.reset();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case "getGewinner":
				try {
					oos.reset();
					oos.writeObject(risiko.getGewinner());
					oos.reset();
				} catch (IOException e1) {
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
					e.printStackTrace();
				}
				break;
			case "removeRisikoKarten":
				removeRisikoKarten();
				break;
			case "getKontinentVonLand":
				int landId = 0;
				try {
					landId = Integer.parseInt(in.readLine());
				} catch (NumberFormatException | IOException e1) {
					e1.printStackTrace();
				}
				Land land = null;
				try {
					land = risiko.getLandById(landId);
				} catch (LandExistiertNichtException e1) {
					e1.printStackTrace();
				}
				try {
					oos.reset();
					oos.writeObject(risiko.getKontinentVonLand(land));
					oos.reset();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "allUpdate":
				allUpdate();
				break;
			}
		} while (!closed);
	}

	private void kannSpielGeladenWerden() {
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
			e.printStackTrace();
		}
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
		risiko.setSpielReady();
	}

	public void attackLandGueltig() {
		try {
			int att = Integer.parseInt(in.readLine());
			oos.reset();
			oos.writeObject(risiko.attackLandGueltig(risiko.getLandById(att)));
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LandExistiertNichtException e) {
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
				e.printStackTrace();
			}
			updateExceptAktiverPlayer("attackStart");
			updateExceptAktiverPlayer(attLand.getName());
			updateExceptAktiverPlayer(defLand.getName());
			updateExceptAktiverPlayer(attLand.getBesitzer().getName());
			updateExceptAktiverPlayer(defLand.getBesitzer().getName());
		} catch (LandNichtInBesitzException | LandInBesitzException e) {
			try {
				oos.reset();
				oos.writeObject(e);
				oos.reset();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void attackFinal() {
		int defUnits = 0;
		try {
			defUnits = Integer.parseInt(in.readLine());
		} catch (NumberFormatException | IOException e) {
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

	public void removeRisikoKarten() {
		ArrayList<Integer> risikokartenWahl = new ArrayList<Integer>();
		int land1 = 0;
		int land2 = 0;
		int land3 = 0;
		try {
			land1 = Integer.parseInt(in.readLine());
			land2 = Integer.parseInt(in.readLine());
			land3 = Integer.parseInt(in.readLine());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		risikokartenWahl.add(land1);
		risikokartenWahl.add(land2);
		risikokartenWahl.add(land3);
		risiko.removeRisikoKarten(risikokartenWahl);
	}

	public void moveFromLandGueltig() {
		try {
			int von = Integer.parseInt(in.readLine());

			oos.reset();
			oos.writeObject(risiko.moveFromLandGueltig(risiko.getLandById(von)));
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LandExistiertNichtException e) {
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
			e.printStackTrace();
		} catch (LandExistiertNichtException e) {
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
			oos.reset();
		} catch (NumberFormatException | LandExistiertNichtException | IOException e) {
			e.printStackTrace();
		}

		try {
			if (risiko.moveUnitsGueltig(risiko.getLandById(vonGue), risiko.getLandById(zuGue), unitsGue)) {
				try {
					risiko.moveUnits(risiko.getLandById(vonGue), risiko.getLandById(zuGue), unitsGue);
				} catch (LandExistiertNichtException | UngueltigeAnzahlEinheitenException e1) {
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
							e.printStackTrace();
						}
					}
				}
			}
		} catch (LandExistiertNichtException e) {
			e.printStackTrace();
		}
	}

	public void setEinheiten() {
		int landID = 0;
		int units = 0;
		Land land = null;
		try {
			landID = Integer.parseInt(in.readLine());
			units = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			land = risiko.getLandById(landID);
		} catch (LandExistiertNichtException e) {
			e.printStackTrace();
		}
		try {
			risiko.setEinheiten(land, units);
			try {
				oos.reset();
				oos.writeObject("hat geklappt");
				oos.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UngueltigeAnzahlEinheitenException e) {
			try {
				oos.reset();
				oos.writeObject(e);
				oos.reset();
				oos.writeObject(land.getEinheiten() - 1);
				oos.reset();
			} catch (IOException e1) {
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
	}

	public void setTauschZeit() {
		boolean obTauschBar = false;
		String tauschBar = "";
		try {
			tauschBar = in.readLine();
		} catch (IOException e1) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void clientsUpdaten(String welchesUpdate) {
		for (ServerListener sl : allServerListeners) {
			sl.handleEvent(welchesUpdate);
		}
	}

	/*
	 * TODO: sammel methode um alle clients zu aktualisieren ausser dem aktiven
	 * spieler, weil es sonst zu problemen mit der Verbindung kommt. muss aber
	 * umgeschrieben werden und funktioniert so nicht wenn die Spieler sich nicht in
	 * der richtigen Reinfolge einloggen
	 */
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
			e.printStackTrace();
		}
		for (int i = 0; i < allServerListeners.size(); i++) {
			if (!(i == risiko.gibAktivenPlayer().getNummer())) {
				allServerListeners.get(i).handleEvent("updateDialog");
				allServerListeners.get(i).handleEvent(updateState);
			}
		}
	}

	/*
	 * ich hab auch mal feierabend!
	 */
	private void disconnect() {
		for (ServerListener sl : allServerListeners) {
			sl.beendeVerbindung();
		}
		try {
			clientSocket.close();
			System.out.println("ich schließe, Feierabend!");
			closed = true;
		} catch (IOException e) {
			System.err.println("Fehler beim Beenden der Verbindung, aber ich will schlafen");
			e.printStackTrace();
		}
	}

}

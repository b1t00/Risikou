package ris.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import ris.client.ui.gui.RisikoClientGUI;
import ris.common.interfaces.ServerListener;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.State;
import ris.common.valueobjects.State;

public class ServerRequestProcessor implements ServerListener, Runnable {
	private RisikoClientGUI client;
//	private Socket socket;
	private ObjectInputStream sin;
	private BufferedReader in;

	public ServerRequestProcessor(ObjectInputStream sin, BufferedReader in, RisikoClientGUI client) {
		this.in = in;
		this.client = client;
//		try {
//			socket = new Socket(host, port);
//			InputStream is = socket.getInputStream();
		this.sin = sin;

//					new ObjectInputStream(is);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private boolean doNotListenMode = false;

	public void setDoNotListenMode(boolean mode) {
		doNotListenMode = mode;
	}

	public boolean getDoNotListenMode() {
		return doNotListenMode;
	}

	private boolean waitingForServer = false;

	public boolean isWaitingForServer() {
		return waitingForServer;
	}

	@Override
	public void run() {
		System.out.println("bin bereit für infous!");
		try {
			String input = sin.readObject().toString();
			System.out.println("should read ready for battle: " + input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (true) {
			if (doNotListenMode) {
				//System.out.println("ich höre nicht zu");
				try {
					Thread.sleep(25); // rennt zu schnell dadurch, wenn er nichts zum verarbeiten hat
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			String input = "";
			// Aktionen vom Server werden eingelesen und verarbeitet
			try {
//				synchronized (sin) {
					waitingForServer = true;
					System.out.println("warte auf server");
					input = sin.readObject().toString();
					System.out.println("got input " + input);
					waitingForServer = false;
//				}
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Server (Aktion): ");
				System.out.println(e.toString());
				System.out.println(e.getMessage());
				return;
			}
			switch (input) {
			case "enableStartBtn":
				client.setEnableNeuesSpielbtn(false);
				break;
			case "unenableStartBtn":
				client.setEnableNeuesSpielbtn(true);
				break;
			case "spielEintreitenBtn":
				client.setSpielEintreitenBtn();
				break;
			case "spielWurdeAngefanen":
				// TODO:
			case "initializeGamePanel":
//				client.removeLoginPanel();
				client.showGamePanel();
				break;
			case "initializeFromLaden":
				client.initializeFromLaden();
				break;
			case "anDerReihe":
				client.setCurrentState(State.SETUNITS);
				client.showQuestion();
				break;
			case "spielLadenTrue":
				client.setSpielgeladenTrue();
				break;
			case "updateDialog":
				String ereignis = null;
				synchronized (sin) {
					try {
						ereignis = sin.readObject().toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				client.updateDialog(ereignis);
				break;	
			case "updateDialog(Land)":
				String land = null;
				String player = null;
//				synchronized (sin) {
					try {
						land = sin.readObject().toString();
						player = sin.readObject().toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//				}
				client.updateDialogSetUnit(land, player);
				client.updateWorld();
				break;
			case "updateMoveUnits":
				Land von = null;
				Land zu = null;
				int unit = 0;
				String verschieber = null;
				try {
					von = (Land) sin.readObject();
					zu = (Land) sin.readObject();
					unit = Integer.parseInt(sin.readObject().toString());
					verschieber = sin.readObject().toString();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				client.updateWorld();
				client.updateMoveUnits(von, zu, unit, verschieber);
				break;
			case "attackStart":
				String attLand = null;
				String defLand = null;
				String attacker = null;
				String defender = null;
//				int defLandUnits = 0;
				try {
					try {
						attLand = (String) sin.readObject();
						defLand = (String) sin.readObject();
						attacker = (String) sin.readObject();
						defender = (String) sin.readObject();
//						defLandUnits = Integer.parseInt( (String) sin.readObject());
					} catch (NumberFormatException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					System.out.println("angreifer : " + attacker + "verteidiger :" + defender);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("(SRP) gui name :" + client.getNameFromGui());
				client.setAttackPlayer(attLand, defLand, attacker, defender);
				break;
			case "attackFinal":
				Attack attackObjekt = null;
				try {
					attackObjekt = (Attack) sin.readObject();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				client.updateAttack(attackObjekt);
				///client.setAttackPlayer(attacker, defender);
				//setDoNotListenMode(false);
			//	System.out.println("(SRP) gui name :" + client.getNameFromGui());
				break;
			case "Tschuess!":
				client.disconnect();
				break;
			case "gewinner gefunden":
				String gewinner = "";
				try {
					gewinner = (String) sin.readObject();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				client.showWinner(gewinner);
				break;
			default:
				System.out.println("etwas wurde eingelesen: " + input);
				break;
			}

		}
	}

	@Override
	public void handleEvent(String e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void schickeObjekt(Attack aO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schickeReinesObject(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beendeVerbindung() {
		// TODO Auto-generated method stub
		
	}

}

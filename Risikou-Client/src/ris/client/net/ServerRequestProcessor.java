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

/**
 * @class Klasse die die ganze Zeit "zuhoert" und update prozesse fuer alle
 *        Clients annimmt
 *
 */
public class ServerRequestProcessor implements ServerListener, Runnable {
	private RisikoClientGUI client;
	private ObjectInputStream sin;
	private BufferedReader in;
	private boolean doNotListenMode = false;
	private boolean waitingForServer = false;

	public ServerRequestProcessor(ObjectInputStream sin, BufferedReader in, RisikoClientGUI client) {
		this.in = in;
		this.client = client;
		this.sin = sin;
	}

	/*
	 * Der DoNotListenMode setzt die WhileSchleife so, dass SRP keinen Input mehr
	 * erwartet und somit nicht mehr "zuhoert" ob updates kommen Somit sollte die
	 * RisikoFassade mit Server kommunizieren koennen
	 */
	public void setDoNotListenMode(boolean mode) {
		doNotListenMode = mode;
	}

	public boolean getDoNotListenMode() {
		return doNotListenMode;
	}

	/*
	 * Methode die RisikoFassade verraet ob der ServerRequestProzessor auf einen
	 * Input wartet oder nicht
	 */
	public boolean isWaitingForServer() {
		return waitingForServer;
	}

	@Override
	public void run() {
		System.out.println("bin bereit für infos!");
		try {
			String input = sin.readObject().toString();
			System.out.println("should read ready for battle: " + input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (true) {
			if (doNotListenMode) {
				try {
					Thread.sleep(25); // rennt zu schnell dadurch, wenn er nichts zum verarbeiten hat
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			String input = "";
			// Aktionen vom Server werden eingelesen und verarbeitet
			try {
				waitingForServer = true;
				System.out.println("warte auf server");
				input = sin.readObject().toString();
				System.out.println("got input " + input);
				waitingForServer = false;
			} catch (Exception e) {
				System.err.println("--->Fehler beim Lesen vom Server (Aktion): ");
				System.err.println(e.toString());
				System.err.println(e.getMessage());
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
			case "initializeGamePanel":
				client.showGamePanel();
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
				try {
					ereignis = sin.readObject().toString();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				client.updateDialog(ereignis);

				break;
			case "updateDialog(Land)":
				String land = null;
				String player = null;
				try {
					land = sin.readObject().toString();
					player = sin.readObject().toString();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
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
					e1.printStackTrace();
				} catch (IOException e1) {
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
				try {
					try {
						attLand = (String) sin.readObject();
						defLand = (String) sin.readObject();
						attacker = (String) sin.readObject();
						defender = (String) sin.readObject();
					} catch (NumberFormatException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				client.setAttackPlayer(attLand, defLand, attacker, defender);
				break;
			case "attackFinal":
				Attack attackObjekt = null;
				try {
					attackObjekt = (Attack) sin.readObject();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				client.updateAttack(attackObjekt);
				break;
			case "Tschuess!":
				client.disconnect();
				break;
			case "gewinner gefunden":
				String gewinner = "";
				try {
					gewinner = (String) sin.readObject();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				client.showWinner(gewinner);
//				client.disconnectWin();
				break;
			default:
				System.out.println("etwas wurde eingelesen: " + input);
				break;
			}
		}
	}

	/*
	 * Methoden vom Interface welche nicht genutzt werden
	 * @see ris.common.interfaces.ServerListener#handleEvent(java.lang.String)
	 */
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

package ris.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import ris.client.ui.gui.RisikoClientGUI;
import ris.common.interfaces.ServerListener;
import ris.common.valueobjects.Land;

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
		System.out.println("bin bereit für infos!");
		try {
			String input = sin.readObject().toString();
			System.out.println("should read ready for battle: " + input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (true) {
			if (doNotListenMode) {
				System.out.println("ich höre nicht zu");
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
//			case "":
//				System.out.println("etwas wurde eingelesen");
//				break;
			case "spielWurdeAngefanen":
				// TODO:
			case "initializeGamePanel":
				client.showGamePanel();
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
				synchronized (sin) {
					try {
						land = sin.readObject().toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				client.updateDialogSetUnit(land);
				client.updateWorld();
				// folgende methode sagt dem client, dass er mit dem weiteren SetUnits
				// weitermachen kann, ansonsten gibt es probleme mit den Threads
				// es kann sein, dass die updateDialog(Land) methode noch in anderen Situationen
				// genutzt wird, dann muss noch eine alternative Loesung gefunden werden.
//				client.continueSetUnits();
				break;
			case "beginAttack":
				int attacker = 0;
				int defender = 0;
				try {
					try {
						attacker = Integer.parseInt(sin.readObject().toString());
						defender = Integer.parseInt(sin.readObject().toString());
					} catch (NumberFormatException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("angreifer : " + attacker + "verteidiger :" + defender);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("(SRP) gui name :" + client.getNameFromGui());
				client.setAttackPlayer(attacker, defender);

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

}

package ris.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import ris.client.ui.gui.RisikoClientGUI;
import ris.common.interfaces.ServerListener;

public class ServerRequestProcessor implements ServerListener, Runnable {
	private RisikoClientGUI client;
//	private Socket socket;
	private ObjectInputStream sin;

	public ServerRequestProcessor(ObjectInputStream sin, RisikoClientGUI client) {
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

	@Override
	public void run() {
		System.out.println("bin bereit für infos!");
		while (true) {
			String input = "";
			// Aktionen vom Server werden eingelesen und verarbeitet
			try {
				synchronized (sin) {
					input = sin.readUTF();
				}
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Server (Aktion): ");
				System.out.println(e.getMessage());
			}
			switch (input) {
//			case "":
//				System.out.println("etwas wurde eingelesen");
//				break;
			case "initializeGamePanel":
				client.showGamePanel();
				break;
			case "updateDialog":
				String ereignis = null;
				synchronized(sin) {
					try {
						ereignis = sin.readUTF();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				client.updateDialog(ereignis);
				break;
			default:
				System.out.println("etwas wurde eingelesen");
				break;
			}
		
		}
	}

	@Override
	public void handleEvent(String e) {
		// TODO Auto-generated method stub

	}

}

package ris.server.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ris.common.interfaces.RisikoInterface;
import ris.common.interfaces.ServerListener;
import ris.common.valueobjects.Attack;

//ist nicht wirklich ein listener
public class ServerFassade implements ServerListener {

	private RisikoInterface risiko;
	private Socket clientSocket;
	private ObjectOutputStream out;

	public ServerFassade(ObjectOutputStream outClient, RisikoInterface risiko) {
//		public ServerFassade(Socket clientSocket, RisikoInterface risiko) {
//		this.clientSocket = clientSocket;
		this.out = outClient;
		/*try {
			this.out = new ObjectOutputStream(new PrintStream(outClient));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		this.risiko = risiko;
	}

	@Override
	public void handleEvent(String e) {
		try {
			System.out.println("sending event: " + e);
			out.reset();
			out.writeObject(e);
			out.flush();
			System.out.println(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
	
	public void schickeObjekt(Attack aO) {
		System.out.println("sending object: ");
		try {
			out.reset();
			out.writeObject(aO);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void schickeReinesObject(Object o) {
		try {
			out.reset();
			out.writeObject(o);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void beendeVerbindung() {
		try {
			out.reset();
			out.writeObject("Tschuess!");
			out.reset();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		try {
//			clientSocket.close();
//			System.out.println("Verbindung zu " + clientSocket.getInetAddress()
//				+ ":" + clientSocket.getPort() + " durch Client abgebrochen");
//		} catch (Exception e) {
//			System.out.println("--->Fehler beim Beenden der Verbindung: ");
//			System.out.println(e.getMessage());
//			//		out.println("Fehler");
//		}
	}

}

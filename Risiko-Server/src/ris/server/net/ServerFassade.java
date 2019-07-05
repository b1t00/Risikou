package ris.server.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ris.common.interfaces.RisikoInterface;
import ris.common.interfaces.ServerListener;
import ris.common.valueobjects.Attack;

/*
 * ist fuer das updaten der Clients zustaendig
 * wird in einem ArrayGespeichert
 */
public class ServerFassade implements ServerListener {

	private RisikoInterface risiko;
	private Socket clientSocket;
	private ObjectOutputStream out;

	public ServerFassade(ObjectOutputStream outClient, RisikoInterface risiko) {

		this.out = outClient;
		this.risiko = risiko;
	}

	@Override
	public void handleEvent(String e) {
		try {
			System.out.println("sending event: " + e);
			out.reset();
			out.writeObject(e);
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void schickeObjekt(Attack aO) {
		System.out.println("sending object: ");
		try {
			out.reset();
			out.writeObject(aO);
			out.flush();
		} catch (IOException e) {
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
			e.printStackTrace();
		}
	}
	
	public void beendeVerbindung() {
		try {
			out.reset();
			out.writeObject("Tschuess!");
			out.reset();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

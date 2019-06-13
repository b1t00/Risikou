package ris.server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.interfaces.RisikoInterface;

public class ClientRequestProcessor implements Runnable {

	private ArrayList<Object> aL;
	
	private RisikoInterface risiko;

	private Socket clientSocket;

	private BufferedReader in;

	private PrintStream out;

	public ClientRequestProcessor(Socket socket, RisikoInterface risiko) {
		this.clientSocket = socket;
		this.risiko = risiko;

		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintStream(clientSocket.getOutputStream());

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

		out.println("Server an Client: Bin Bereit f�r den Kampf");

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
			case "spielAufbau":
				spielAufbau();
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
		ArrayList<String> farben;
		farben = risiko.getFarbauswahl();
		out.println(farben.size());

		for (String farbe : farben) {
			out.println(farbe);
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
	}
	
	public void spielAufbau() {
		risiko.spielAufbau();
	}
	public void schickeArrayList(Object o, ArrayList<Object> aL){
		
		System.out.println(aL.size());
	}
		
}

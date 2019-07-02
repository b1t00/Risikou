package ris.server.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import ris.common.interfaces.RisikoInterface;
import ris.common.interfaces.ServerListener;
import ris.local.domain.Risiko;

public class MiniRisServer {

	public final static int DEFAULT_PORT = 6789;

	protected int port;
	protected ServerSocket serverSocket;
	private RisikoInterface risiko;
	private ArrayList<ServerListener> allServerListeners;
	private ObjectOutputStream theoneobjectstream;
	
	public MiniRisServer(int port) {
		risiko = new Risiko();

		allServerListeners = new ArrayList<ServerListener>(); 
		
		if (port == 0) {
			port = DEFAULT_PORT;
		}
		this.port = port;

		try {
			serverSocket = new ServerSocket(port);
			InetAddress ia = InetAddress.getLocalHost();
			System.out.println("Host :" + ia.getHostName());
			System.out.println("Server *" + ia.getHostAddress() + "* lauscht auf Port - " + serverSocket.getLocalPort());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	int x = 0;

	public void acceptClientConnectRequests() {
		try {
			while (true) {
				System.out.println("warte auf client");
				Socket clientSocket = serverSocket.accept();
				System.out.println("juhuu jemand will mit mir spielen - waiting for connection");
				
				// damit der outputstream gut funktioniert muessen die methoden in den interface
				// synchronized implementieren
				OutputStream out = clientSocket.getOutputStream();
				theoneobjectstream = new ObjectOutputStream(new PrintStream(out));
				System.out.println("serverlistener nr " + x);
				ServerListener listener = new ServerFassade(theoneobjectstream, risiko,x++);
//				ServerListener listener = new ServerFassade(clientSocket, risiko);
				allServerListeners.add(listener);
				ClientRequestProcessor c = new ClientRequestProcessor(theoneobjectstream, clientSocket, risiko, allServerListeners);
				Thread t = new Thread(c);
				//startet die run Methode vom ClientRequestProcessor
				t.start(); 
				
			}
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {

		int port = 0;

		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				// TODO: Numberformat etc
				port = 0;
			}
		}
//		try {
		MiniRisServer server = new MiniRisServer(port);
		server.acceptClientConnectRequests();
//		} catch (IOException e) {
//		 TODO:
//		}
	}
}

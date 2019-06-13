package ris.server.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import ris.common.interfaces.RisikoInterface;
import ris.local.domain.Risiko;

public class MiniRisServer {

	public final static int DEFAULT_PORT = 6789;

	protected int port;
	protected ServerSocket serverSocket;
	private RisikoInterface risiko;

	public MiniRisServer(int port) {
		risiko = new Risiko();

		if (port == 0) {
			port = DEFAULT_PORT;
		}
		this.port = port;

		try {
			serverSocket = new ServerSocket(port);

			InetAddress ia = InetAddress.getLocalHost();
			System.out.println("Host :" + ia.getHostName());
			System.out.println("Server *" + ia.getHostAddress() + "* lauscht auf Port");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void acceptClientConnectRequests() {
		try {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				ClientRequestProcessor c = new ClientRequestProcessor(clientSocket, risiko);
				Thread t = new Thread(c);
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
		// TODO:
//		}
	}
}

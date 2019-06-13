package ris.server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import ris.common.interfaces.RisikoInterface;

public class ClientRequestProcessor implements Runnable{
	
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
		out.println("Server an Client: Bin Bereit für den Kampf");
	}

}

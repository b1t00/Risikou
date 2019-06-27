package ris.server.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import ris.common.interfaces.RisikoInterface;
import ris.common.interfaces.ServerListener;

//ist nicht wirklich ein listener
public class ServerFassade implements ServerListener {
	
	private RisikoInterface risiko;
	private Socket clientSocket;
	private ObjectOutputStream out;
	
	public ServerFassade(OutputStream outClient, RisikoInterface risiko) {
//		public ServerFassade(Socket clientSocket, RisikoInterface risiko) {
//		this.clientSocket = clientSocket;
		try {
			this.out = new ObjectOutputStream( new PrintStream(outClient));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.risiko = risiko;
	}
	
	@Override
	public void handleEvent(String e) {
		try {
			out.reset();
			out.writeUTF(e);
			out.flush();
			System.out.println(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

}

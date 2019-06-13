package ris.client.net;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Player;
import ris.common.valueobjects.Risikokarte;
import ris.common.valueobjects.State;

public class RisikoFassade implements RisikoInterface {
	private Socket socket = null;
	private BufferedReader sin;
	private PrintStream sout;

	public RisikoFassade(String host, int port) {
		try {
			socket = new Socket(host, port);
			InputStream is = socket.getInputStream();
			sin = new BufferedReader(new InputStreamReader(is));
			sout = new PrintStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fehler beim SocketStream oeffnen : " + e);
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.err.println("Socket geschlossen");
			System.exit(0);
		}
		
		System.err.println("Verbunden: " + socket.getInetAddress() + " : " + socket.getPort());
		try {
			String message = sin.readLine();
			System.out.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public State getCurrentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean kannAngreifen(Player play) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player gibAktivenPlayer() {
		Player aktiverPlayer;
		
		sout.println("aP");
		
		return null;
	}

	@Override
	public void setNextState() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean kannVerschieben(Player play) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNextPlayer() {
		// TODO Auto-generated method stub

	}

	@Override
	public int errechneVerfuegbareEinheiten(Player play) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLandClickZeit(boolean obLandClickbar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTauschZeit(boolean obTauschbar) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean zieheEinheitenkarte(Player playerHatGezogen) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveUnits(Land von, Land zu, int units) {
		// TODO Auto-generated method stub

	}

	@Override
	public Attack attack(Land angriff, Land defence, int unitsAngriff, int unitsDefend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getGewinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveUnitsGueltig(Land von, Land zu, int units) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void playerAnlegen(String name, String farbe, int iD) {
		Integer ID = iD;
		sout.println("playerAnlegen");
		sout.println(name);
		sout.println(farbe);
		sout.println(ID.toString());
	}

	@Override
	public void setColorArray(Color color) {
		// TODO Auto-generated method stub

	}

	public void spielAufbau() {
		sout.println("spielAufbau");
	}

	@Override
	public ArrayList<Player> getPlayerArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Risikokarte> getRisikoKarten() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Land getLandById(int landId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean allMissionsComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rundeMissionComplete(Player play) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void spielSpeichern(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spielLaden(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getLandClickZeit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getTauschZeit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean attackLandGueltig(Land attacker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean defenseLandGueltig(Land attacker, Land defender) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveFromLandGueltig(Land von) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveToLandGueltig(Land von, Land zu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Color> getColorArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getFarbauswahl() {
		ArrayList<String> farbauswahl = new ArrayList<String>();
		
		sout.println("getFarbauswahl");
		System.out.println("hier angekommen");
		
		String antwort = "";
		try {
			antwort = sin.readLine();
			int anzahl = Integer.parseInt(antwort);
			for(int i=0; i<anzahl; i++) {
				String farbe;
				String antwort2 = "";
				antwort = sin.readLine();
				farbauswahl.add(antwort);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		return farbauswahl;
	}

	@Override
	public String setFarbeAuswaehlen(String farbe) {
		// TODO Auto-generated method stub
		return null;
	}

}

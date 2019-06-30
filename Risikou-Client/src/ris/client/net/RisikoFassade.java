package ris.client.net;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ris.client.ui.gui.RisikoClientGUI;
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
	private ObjectInputStream ois;
	ServerRequestProcessor serverListener;

	public RisikoFassade(String host, int port, RisikoClientGUI gui) {
		try {
			socket = new Socket(host, port);
//			startServer(socket);<>
			InputStream is = socket.getInputStream();
			ois = new ObjectInputStream(is);
			sin = new BufferedReader(new InputStreamReader(is));
			sout = new PrintStream(socket.getOutputStream());

			serverListener = new ServerRequestProcessor(ois, sin, gui);
			Thread t = new Thread(serverListener);
			t.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fehler beim SocketStream oeffnen : " + e);
			if (socket != null) {
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
		/*try {
			String message = sin.readLine();
			System.out.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	@Override
	public State getCurrentState() {
		goIntoCommandMode();
		State currentState = null;
//		while (currentState == null) {
			try {
				synchronized (ois) {
					goIntoCommandMode();
					sout.println("getCurrentState");
					Object o = new Object();
					o = ois.readObject();
					System.out.println("(RF)objectState " + o);
					currentState = (State) o;
//				currentState = (State) ois.readObject(); // Was passiert mit UTF??
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}
		System.out.println("(RF)cuurent state " + currentState);
		releaseCommandMode();
		return currentState;
	}

	@Override
	public boolean kannAngreifen() {
		sout.println("kannAngreifen");
		boolean angriff = false;
		synchronized (ois) {
			try {
				Object o = new Object();
				o = ois.readObject();
				System.out.println("kann Angreifen ? : " + o.toString());
				angriff = (boolean) o;
//				angriff = (boolean) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return angriff;
	}

	@Override
	public Player gibAktivenPlayer() {
		goIntoCommandMode();
		
		Player aktiverPlayer = null;
		sout.println("gibAktivenPlayer");
		try {
			synchronized (ois) {
				aktiverPlayer = (Player) ois.readObject();
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		releaseCommandMode();
		return aktiverPlayer;
	}

	@Override
	public void setNextState() {
		goIntoCommandMode();
		sout.println("setNextState");
		releaseCommandMode();
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
	public int errechneVerfuegbareEinheiten() {
		goIntoCommandMode();
		sout.println("errechneVerfuegbareEinheiten");

		int units = 0;
		try {
			synchronized (ois) {
				units = (Integer) ois.readObject();
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		releaseCommandMode();
		return units;
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
		goIntoCommandMode();
		Integer ID = iD;
		sout.println("playerAnlegen");
		sout.println(name);
		sout.println(farbe);
		sout.println(ID.toString());
		releaseCommandMode();
	}

	// TODO: kann glaube ich weg
	@Override
	public void spielAufbau() {
		sout.println("spielAufbau");
	}

	@Override
	public void setSpielerAnzahl(int spielerAnzahl) {
		sout.println("setSpielerAnzahl");
		sout.println(spielerAnzahl);
	}

	@Override
	public int getSpielerAnzahl() {
		sout.println("getSpielerAnzahl");
		int spielerAnzahl = 0;
		try {
			synchronized (ois) {
				spielerAnzahl = (Integer) ois.readObject();
			}
		} catch (ClassNotFoundException | NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return spielerAnzahl;
	}

	private void goIntoCommandMode(){
		serverListener.setDoNotListenMode(true);
		if (serverListener.isWaitingForServer())
		{
			sout.println("sentAliveMessage");
			while (serverListener.isWaitingForServer())
			{
				System.out.println("still waiting for server");				
			}
			System.out.println("done we can go into command mode");
		}
	}
	
	private void releaseCommandMode(){
		serverListener.setDoNotListenMode(false);
		System.out.println("released command mode");
	}
	
	@Override
	public ArrayList<Player> getPlayerArray() {
		goIntoCommandMode();
		ArrayList<Player> allePlayer = new ArrayList<Player>();
		sout.println("getPlayerArray");
		try {
//			if(!ois.ct().equals("leer")){
			synchronized (ois) {
				
				allePlayer = (ArrayList<Player>) ois.readObject();
			}
//			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		releaseCommandMode();
		return allePlayer;
	}

	@Override
	public ArrayList<Risikokarte> getRisikoKarten() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Land getLandById(int landId) {
		goIntoCommandMode();
		System.out.println("rf getLandByID");
		sout.println("getLandById");
		System.out.println("(rf) landID" + landId);
		sout.println(landId);

		Land land = null;
		try {
			synchronized (ois) {
				land = (Land) ois.readObject();
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		releaseCommandMode();
		return land;
	}

	@Override
	public boolean allMissionsComplete() {
		sout.println("allMisionsComplete");
		boolean win = false;
		synchronized (ois) {
			try {
				win = (boolean) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return win;
	}

	@Override
	public boolean rundeMissionComplete() {
		sout.println("rundeMissionComplete");
		boolean win = false;
		synchronized (ois) {
			try {
				win = (boolean) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return win;
	}

	@Override
	public void spielSpeichern(String name) {
		sout.println("spielSpeichern");
		// muss hier noch darauf gewartet werden, dass der server sein ok gibt?
		sout.println(name);
	}

	@Override
	public String[] getSpielladeDateien() {
		String[] verzeichnis = new String[10];

		sout.println("getSpielladeDateien");
		try {
			synchronized (ois) {
				verzeichnis = (String[]) ois.readObject();
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return verzeichnis;
	}

	@Override
	public void spielLaden(String name) {
		sout.println("spielLaden");
		sout.println(name);
	}

	@Override
	public boolean getLandClickZeit() {
		goIntoCommandMode();
		boolean landClickZeit = true;

		sout.println("getLandClickZeit");
		try {
			synchronized (ois) {
				landClickZeit = (boolean) ois.readObject();
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("clickzeit: " + landClickZeit);
		releaseCommandMode();
		return landClickZeit;
	}

	@Override
	public boolean getTauschZeit() {
		boolean tauschZeit = false;

		sout.println("getTauschZeit");
		try {
			synchronized (ois) {
				tauschZeit = (boolean) ois.readObject();
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tauschZeit;
	}

	@Override
	public void setLandClickZeit(boolean obLandClickbar) {
		goIntoCommandMode();
		sout.println("setLandClickZeit");
		sout.println(obLandClickbar);
		releaseCommandMode();
	}

	@Override
	public void setTauschZeit(boolean obTauschbar) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean attackLandGueltig(Land attacker) {
		// TODO Auto-generated method stub
		System.out.println("Land : " + attacker.getNummer());
		sout.println("attackLandGueltig");
		sout.println(attacker.getNummer());
		synchronized (ois) {
			try {
				return (boolean) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean defenseLandGueltig(Land attacker, Land defender) {
		sout.println("defenseLandGueltig");
		sout.println(attacker.getNummer());
		sout.println(defender.getNummer());
		synchronized (ois) {
			try {
				boolean b = (boolean) ois.readObject();
				System.out.println("defenseLandGueltig ist gueltig");
				return b;
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	private void sout(int nummer) {
		// TODO Auto-generated method stub
		
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
		goIntoCommandMode();
		ArrayList<Color> ColorArray = new ArrayList<Color>();
		sout.println("getColorArray");
		try {
			synchronized (ois) {
				ColorArray = (ArrayList<Color>) ois.readObject();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		releaseCommandMode();
		return ColorArray;
	}

	@Override
	public ArrayList<String> getFarbauswahl() {
		goIntoCommandMode();
		ArrayList<String> farbauswahl = new ArrayList<String>();

		sout.println("getFarbauswahl");

//		String antwort = "";
		try {
			synchronized (ois) {
				farbauswahl = (ArrayList<String>) ois.readObject();
			}
//			int anzahl = Integer.parseInt(antwort);
//			for(int i=0; i<anzahl; i++) {
//				String farbe;
//				String antwort2 = "";
//				synchronized(ois) {
//				antwort = sin.readLine();
//				}
//				farbauswahl.add(antwort);
//			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		releaseCommandMode();
		return farbauswahl;
	}

	@Override
	public String setFarbeAuswaehlen(String farbe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEinheiten(Land land, int units) {
		goIntoCommandMode();
		System.out.println("ich setzte einheiten <-----------------------");
		sout.println("setEinheiten");
//		die ID vom Land wird verschickt -> Methode getLandByID
		sout.println(land.getNummer());
		sout.println(units);
		releaseCommandMode();

	}

	public void allUpdate(String ereignis) {
		System.out.println("rf geht das update?");
		sout.println("allUpdate");
		sout.println(ereignis);
	}

	@Override
	public Player getPlayerById(int iD) {
		// TODO Auto-generated method stub
		return null;
	}

}

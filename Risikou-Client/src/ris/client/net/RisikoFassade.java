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
import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.LandInBesitzException;
import ris.common.exceptions.LandNichtInBesitzException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.UngueltigeAnzahlEinheitenException;
import ris.common.exceptions.UngueltigeAnzahlSpielerException;
import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.GameObject;
import ris.common.valueobjects.Kontinent;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Player;
import ris.common.valueobjects.Risikokarte;
import ris.common.valueobjects.State;

/*
 * @class RisikoFassade implementiert auf Seite des Clients das RisikoInterface
 * die gui ruft hier die methoden auf
 * die fassade schickt den aufruf als string an den clientrequestprocessor auf dem server
 * 
 * Manche Methoden werfen oefter eine Exception, da der falsche Datentyp eingelesen wird.
 * Der Fehler konnte nicht gefunden werden, eine voruebergehende Loesung wurde mittels
 * rekursivem Aufruf der Methode versucht -> wirkt stabiler, aber muesste noch geaendert werden.
 * Kein guter Loesungsansatz, aber sonst waere das Spiel oefter abgestuerzt
 */
public class RisikoFassade implements RisikoInterface {
	private Socket socket = null;
	private BufferedReader sin;
	private PrintStream sout;
	private ObjectInputStream ois;
	ServerRequestProcessor serverListener;
	private RisikoClientGUI gui;

	public RisikoFassade(Socket socket, RisikoClientGUI gui) {
		try {
			InputStream is = socket.getInputStream();
			ois = new ObjectInputStream(is);
			sin = new BufferedReader(new InputStreamReader(is));
			sout = new PrintStream(socket.getOutputStream());
			this.gui = gui;

//			RisikoFassade erstellt einen ServerRequestProcessor, der auf updates wartet
			serverListener = new ServerRequestProcessor(ois, sin, gui);
			Thread t = new Thread(serverListener);
			t.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fehler beim SocketStream oeffnen : " + e);
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			System.err.println("Socket geschlossen");
			System.exit(0);
		}
		System.err.println("Verbunden: " + socket.getInetAddress() + " : " + socket.getPort());
	}

	/*
	 * Methode wird zu Beginn jedes Methodenaufrufs aufgerufen sagt dem
	 * ServerRequestProcessor, dass er nicht zuhoeren soll und kann somit selbst
	 * Inuts vom Server empfangen
	 */
	private void goIntoCommandMode() {
		serverListener.setDoNotListenMode(true);
		if (serverListener.isWaitingForServer()) {
			sout.println("doNotListen");
			while (serverListener.isWaitingForServer()) {
				System.out.println("still waiting for server");
			}
			System.out.println("done we can go into command mode");
		}
	}

	/*
	 * wird zum Ende jeder Methode aufgerufen und versetzt den
	 * ServerRequestProcessor wieder in Listen-Mode
	 */
	private void releaseCommandMode() {
		serverListener.setDoNotListenMode(false);
		System.out.println("released command mode");
	}

	@Override
	public State getCurrentState() {
		goIntoCommandMode();
		State currentState = null;
		sout.println("getCurrentState");
		try {
			Object o = new Object();
			o = ois.readObject();
			currentState = (State) o;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return currentState;
	}

	@Override
	public boolean kannAngreifen() {
		goIntoCommandMode();
		sout.println("kannAngreifen");
		boolean angriff = false;
		try {
			Object o = new Object();
			o = ois.readObject();
			angriff = (boolean) o;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return angriff;
	}

	int count = 0;

	@Override
	public Player gibAktivenPlayer() {
		goIntoCommandMode();
		Player aktiverPlayer = null;
		sout.println("gibAktivenPlayer");
		try {
			System.out.println("geb mir den aktiven player Rf");
			aktiverPlayer = (Player) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Fehler eim Einlesen vom player " + count);
			e.printStackTrace();
			if (++count < 10) {
				gibAktivenPlayer();
			}
//			Versuch rekursiver Aufruf, wenn falscher input eingelesen wurde, der kein Player ist
			gibAktivenPlayer();

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
		goIntoCommandMode();
		sout.println("kannVerschieben");
		Integer playerNr = play.getNummer();
		sout.println(playerNr.toString());
		try {
			return (boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return false;
	}

	@Override
	public void setNextPlayer() {
		goIntoCommandMode();
		sout.println("setNextPlayer");
		releaseCommandMode();
	}

	@Override
	public int errechneVerfuegbareEinheiten() {
		goIntoCommandMode();
		sout.println("errechneVerfuegbareEinheiten");
		int units = 0;
		try {
			units = (Integer) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return units;
	}

	@Override
	public boolean zieheEinheitenkarte() {
		boolean kannZiehen = false;
		goIntoCommandMode();
		sout.println("zieheEinheitenkarte");
		try {
			kannZiehen = (Boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return kannZiehen;
	}

	@Override
	public boolean moveUnitsGueltig(Land von, Land zu, int units) {
		goIntoCommandMode();
		Integer vonGue = von.getNummer();
		Integer zuGue = zu.getNummer();
		Integer unitsGue = units;
		boolean gueltigkeit = false;
		sout.println("moveUnitsGueltig");
		sout.println(vonGue.toString());
		sout.println(zuGue.toString());
		sout.println(unitsGue.toString());
		try {
			return gueltigkeit = (boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return gueltigkeit;
	}

	@Override
	public void moveUnits(Land von, Land zu, int units) {
		goIntoCommandMode();
		Integer vonMov = von.getNummer();
		Integer zuMov = zu.getNummer();
		Integer unitsMov = units;
		sout.println("moveUnits");
		sout.println(vonMov.toString());
		sout.println(zuMov.toString());
		sout.println(unitsMov.toString());
		releaseCommandMode();
	}

	@Override
	public Player getGewinner() {
		goIntoCommandMode();
		Player winner = null;
		sout.println("getGewinner");
		try {
			winner = (Player) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return winner;
	}

//	hier beispiel fuer werfen einer exception, die bereits im server geworfen wurde
	@Override
	public void playerAnlegen(String name, String farbe, int iD) throws SpielerNameExistiertBereitsException {
		goIntoCommandMode();
		Integer ID = iD;
		sout.println("playerAnlegen");
		sout.println(name);
		sout.println(farbe);
		sout.println(ID.toString());
		try {
			Object input = ois.readObject();
			if (input instanceof SpielerNameExistiertBereitsException) {
				throw new SpielerNameExistiertBereitsException(name);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
	}

	@Override
	public void spielAufbau() {
		goIntoCommandMode();
		sout.println("spielAufbau");
		releaseCommandMode();
	}

	@Override
	public void setSpielerAnzahl(int spielerAnzahl) throws UngueltigeAnzahlSpielerException {
		goIntoCommandMode();
		sout.println("setSpielerAnzahl");
		sout.println(spielerAnzahl);
		try {
			Object input = ois.readObject();
			if (!(input instanceof String)) {
				throw new UngueltigeAnzahlSpielerException(spielerAnzahl);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
	}

	@Override
	public int getSpielerAnzahl() {
		goIntoCommandMode();
		sout.println("getSpielerAnzahl");
		int spielerAnzahl = 0;
		try {
			spielerAnzahl = (Integer) ois.readObject();
		} catch (ClassNotFoundException | NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return spielerAnzahl;
	}

	@Override
	public ArrayList<Player> getPlayerArray() {
		goIntoCommandMode();
		ArrayList<Player> allePlayer = new ArrayList<Player>();
		sout.println("getPlayerArray");
		try {
			Object input = ois.readObject();
			if (input instanceof String) {
				String fromServer = (String) input;
				if (fromServer.equals("gewinner gefunden")) {
					String gewinner = "";
					try {
						gewinner = (String) ois.readObject();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					gui.showWinner(gewinner);
				}
			} else {
				allePlayer = (ArrayList<Player>) input;
			}
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Fehler beim einlesen vom playerarray");
//			e.printStackTrace();
//			getPlayerArray();
		}
		releaseCommandMode();
		return allePlayer;
	}

	public ArrayList<Land> getLaender() {
		ArrayList<Land> alleLaender = new ArrayList<Land>();
		goIntoCommandMode();
		sout.println("getLaender");
		try {
			alleLaender = (ArrayList<Land>) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Fehler beim einlesen vom landarray");
			e.printStackTrace();
		}
		releaseCommandMode();
		return alleLaender;
	}

	// TODO: kann geloescht werden?
	@Override
	public ArrayList<Risikokarte> getRisikoKarten() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Land getLandById(int landId) throws LandExistiertNichtException {
		goIntoCommandMode();
		sout.println("getLandById");
		sout.println(landId);
		Land land = null;
		try {
			Object input = ois.readObject();
			if (input instanceof Land) {
				land = (Land) input;
			} else {
				throw new LandExistiertNichtException(land);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return land;
	}

	@Override
	public boolean allMissionsComplete() {
		goIntoCommandMode();
		sout.println("allMissionsComplete");
		boolean win = false;
		try {
			win = (boolean) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return win;
	}

	@Override
	public boolean rundeMissionComplete() {
		goIntoCommandMode();
		sout.println("rundeMissionComplete");
		boolean win = false;
		try {
			win = (boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			rundeMissionComplete();
		}
		releaseCommandMode();
		return win;
	}

	@Override
	public void spielSpeichern(String name) {
		sout.println("spielSpeichern");
		sout.println(name);
	}

	@Override
	public String[] getSpielladeDateien() {
		goIntoCommandMode();
		String[] verzeichnis = new String[10];
		sout.println("getSpielladeDateien");
		try {
			verzeichnis = (String[]) (ois.readObject());
			for (String s : verzeichnis) {
				System.out.println("eins");
				System.out.println(s);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return verzeichnis;
	}

	@Override
	public void spielLaden(String name) {
		goIntoCommandMode();
		sout.println("spielLaden");
		sout.println(name);
		releaseCommandMode();
	}

	public GameObject getGameDatei() {
		goIntoCommandMode();
		sout.println("getGameDatei");
		GameObject gameDatei = null;
		try {
			gameDatei = (GameObject) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(gameDatei.getSpielstand() + "tuuuuuuuuuuuuuuuuuuuuuuuuuuuuuurn up");
		releaseCommandMode();
		return gameDatei;
	}

	public boolean spielWurdeGeladen() {
		goIntoCommandMode();
		boolean wurdeSpielGeladen = false;
		sout.println("spielWurdeGeladen");
		try {
			wurdeSpielGeladen = (boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return wurdeSpielGeladen;
	}

	public void spielerWurdeGeladen() {
		sout.println("spielerWurdeGeladen");
	}

	@Override
	public boolean getLandClickZeit() {
		goIntoCommandMode();
		boolean landClickZeit = true;
		sout.println("getLandClickZeit");
		try {
			landClickZeit = (boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Fehler bei LandclickZeit");
			e.printStackTrace();
			getLandClickZeit();
		}
		releaseCommandMode();
		return landClickZeit;
	}

	@Override
	public boolean getTauschZeit() {
		goIntoCommandMode();
		boolean tauschZeit = false;
		sout.println("getTauschZeit");
		try {
			tauschZeit = (boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
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
		goIntoCommandMode();
		sout.println("setTauschZeit");
		sout.println(String.valueOf(obTauschbar));
		releaseCommandMode();
	}

	@Override
	public boolean attackLandGueltig(Land attacker) {
		goIntoCommandMode();
		sout.println("attackLandGueltig");
		sout.println(attacker.getNummer());
		try {
			return (boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return false;
	}

	@Override
	public boolean defenseLandGueltig(Land attacker, Land defender) {
		goIntoCommandMode();
		sout.println("defenseLandGueltig");
		sout.println(attacker.getNummer());
		sout.println(defender.getNummer());
		try {
			boolean b = (boolean) ois.readObject();
			return b;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return false;
	}

	public int getDefLandUnits() {
		goIntoCommandMode();
		sout.println("getDefLandUnits");
		int defLandUnits = 0;
		try {
			defLandUnits = (Integer) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return defLandUnits;
	}

	public void attackStart(Land attLand, Land defLand, int attUnits)
			throws LandNichtInBesitzException, LandInBesitzException {
		goIntoCommandMode();
		sout.println("attackStart");
		sout.println(attLand.getNummer());
		sout.println(defLand.getNummer());
		sout.println(attUnits);
		try {
			Object input = ois.readObject();
			if (input instanceof LandNichtInBesitzException) {
				throw new LandNichtInBesitzException(attLand);
			} else if (input instanceof LandInBesitzException) {
				throw new LandInBesitzException(defLand);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
	}

	@Override
	public Attack attackFinal(int defUnits) {
		goIntoCommandMode();
		sout.println("attackFinal");
		sout.println(defUnits);
		Attack attackObjekt = null;
		try {
			attackObjekt = (Attack) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return attackObjekt;
	}

	@Override
	public boolean moveFromLandGueltig(Land von) {
		goIntoCommandMode();
		sout.println("moveFromLandGueltig");
		sout.println(von.getNummer());
		try {
			return (boolean) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return false;
	}

	@Override
	public boolean moveToLandGueltig(Land von, Land zu) {
		goIntoCommandMode();
		sout.println("moveToLandGueltig");
		sout.println(von.getNummer());
		sout.println(zu.getNummer());
		try {
			boolean b = (boolean) ois.readObject();
			return b;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return false;
	}

	@Override
	public ArrayList<Color> getColorArray() {
		goIntoCommandMode();
		ArrayList<Color> ColorArray = new ArrayList<Color>();
		sout.println("getColorArray");
		try {
			ColorArray = (ArrayList<Color>) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return ColorArray;
	}

	public void removeRisikoKarten(ArrayList<Integer> risikokartenWahl) {
		goIntoCommandMode();
		sout.println("removeRisikoKarten");
		for (Integer landId : risikokartenWahl) {
			sout.println(String.valueOf(landId));
		}
		releaseCommandMode();
	}

	@Override
	public ArrayList<String> getFarbauswahl() {
		goIntoCommandMode();
		ArrayList<String> farbauswahl = new ArrayList<String>();
		sout.println("getFarbauswahl");
		try {
			farbauswahl = (ArrayList<String>) ois.readObject();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		releaseCommandMode();
		return farbauswahl;
	}

	@Override
	public void setFarbeAuswaehlen(String farbe) {
		goIntoCommandMode();
		sout.println("setFarbeAuswaehlen");
		sout.println(farbe);
		releaseCommandMode();
	}

	@Override
	public void setEinheiten(Land land, int units) throws UngueltigeAnzahlEinheitenException {
		goIntoCommandMode();
		sout.println("setEinheiten");
		sout.println(land.getNummer());
		sout.println(units);
		Object input;
		try {
			input = ois.readObject();
			if (!(input instanceof String)) {
				int max = (Integer) ois.readObject();
				throw new UngueltigeAnzahlEinheitenException(1, max);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
	}

	@Override
	public Kontinent getKontinentVonLand(Land defLand) {
		Kontinent k = null;
		goIntoCommandMode();
		sout.println("getKontinentVonLand");
		sout.println(defLand.getNummer());
		try {
			k = (Kontinent) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		return null;
	}

	public void allUpdate(String ereignis) {
		sout.println("allUpdate");
		sout.println(ereignis);
	}

	public void aksForServerListenerNr() {
		goIntoCommandMode();
		int serverListenerNr = -99;
		int serverListenerSize = -99;
		sout.println("aksForServerListenerNr");
		try {
			serverListenerSize = (int) ois.readObject();
			serverListenerNr = (int) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		releaseCommandMode();
		gui.setServerListenerNr(serverListenerNr);
	}

	public void aktiveClientAskHowMany(int serverListenerNr) {
		Integer sListenerNr = serverListenerNr;
		sout.println("aktiveClientAskHowMany");
		sout.println(sListenerNr.toString());
	}

	public void pressBackButn(int serverListenerNr) {
		Integer sListenerNr = serverListenerNr;
		sout.println("pressBackButn");
		sout.println(sListenerNr.toString());
	}

	public void spielEintreitenBtn(int sListenerNr) {
		sout.println("spielEintreitenBtn");
		sout.println(sListenerNr);
	};

	/*
	 * ab hier Methoden, die vom Interface stammen, aber nicht implementiert werden
	 * muessen
	 * 
	 * @see ris.common.interfaces.RisikoInterface#getPlayerById(int)
	 */
	@Override
	public Player getPlayerById(int iD) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameObject gameObjectLaden(String datei) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public GameObject getGeladenesSpiel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int wieVieleSpielerImGame() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void spielLadenTrue() {
		// TODO Auto-generated method stub

	}

}

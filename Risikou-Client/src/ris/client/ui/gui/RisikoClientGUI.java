package ris.client.ui.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import ris.client.net.RisikoFassade;
import ris.client.net.ServerRequestProcessor;

//	MapImage Größe 120 / 711

import ris.client.ui.gui.swing.panels.DialogPanel;
import ris.client.ui.gui.swing.panels.DialogPanel.SpeichernListener;
import ris.client.ui.gui.swing.panels.DicePanel;
import ris.client.ui.gui.swing.panels.EintauschPanel;
import ris.client.ui.gui.swing.panels.EintauschPanel.EintauschListener;
import ris.client.ui.gui.swing.panels.InfoPanel;
import ris.client.ui.gui.swing.panels.KartenButton.kartenAuswahlListener;
import ris.client.ui.gui.swing.panels.LadePanel;
import ris.client.ui.gui.swing.panels.LadePanel.LadeListener;
import ris.client.ui.gui.swing.panels.LoginPanel;
import ris.client.ui.gui.swing.panels.NeuerSpielerPanel;
import ris.client.ui.gui.swing.panels.PausePanel;
import ris.client.ui.gui.swing.panels.QuestionPanel;
import ris.client.ui.gui.swing.panels.QuestionPanel.QuestionListener;
import ris.client.ui.gui.swing.panels.RequestPanel;
import ris.client.ui.gui.swing.panels.RequestPanel.CountryRequest;
import ris.client.ui.gui.swing.panels.RisikokartenPanel;
import ris.client.ui.gui.swing.panels.RisikokartenPanel.RisikoKartenListener;
import ris.client.ui.gui.swing.panels.SetUnitsPanel;
import ris.client.ui.gui.swing.panels.UnitNumberPanel;
import ris.client.ui.gui.swing.panels.UnitNumberPanel.UnitNumber;
import ris.client.ui.gui.swing.panels.UnitNumberPanel.UnitNumberListener;
import ris.client.ui.gui.swing.panels.WieVieleSpielerPanel;
import ris.client.ui.gui.swing.panels.WorldPanel;
import ris.client.ui.gui.swing.panels.WorldPanel.WorldListener;
import ris.client.ui.gui.swing.panels.werBistDuPanle;
import ris.client.ui.gui.swing.panels.werBistDuPanle.SpielerladenListener;
import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.LandInBesitzException;
import ris.common.exceptions.LandNichtInBesitzException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.UngueltigeAnzahlEinheitenException;
import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Risikokarte;
import ris.common.valueobjects.State;

public class RisikoClientGUI extends JFrame implements QuestionListener, WorldListener, UnitNumberListener,
		kartenAuswahlListener, RisikoKartenListener, EintauschListener, SpeichernListener, LadeListener, SpielerladenListener {

	public static final int DEFAULT_PORT = 6789;

	private RisikoInterface risiko;
	private ServerRequestProcessor serverListener;
	private Socket socket;

//	name ist notwendig, damit die gui weiß, was sie anzeigen soll // gleicht mit crp ab
	private String name = null;
	private int spielerNummer;
	private int sListenerNr;
	private boolean spielGeladenTrue = false;
	// LOGIN //
	private LoginPanel loginPl;
	private LadePanel ladePl;
	private werBistDuPanle werBistPl;
	private WieVieleSpielerPanel wieVielePl;
	private NeuerSpielerPanel neuerSpielerPl;
	private RisikokartenPanel risikoKartenTPl;

	// Elemente vom Layout des GUI-Frames
	private JPanel container;
	private CardLayout cl = new CardLayout();
	private WorldPanel worldPl;
	private InfoPanel infoPl;
	private DialogPanel dialogPl;

	private DicePanel dicePl;

	// Das RequestPanel wird benoetigt, wenn auf ein Land geklickt werden muss
	private RequestPanel attackFromPl;
	private RequestPanel attackToPl;
	private RequestPanel moveFromPl;
	private RequestPanel moveToPl;
	private RequestPanel cardRequestPl;

	private EintauschPanel eintauschPl;

	private SetUnitsPanel setUnitsPl;

	private PausePanel pausePl;

	private QuestionPanel changeCardsPl;
	private QuestionPanel attackQuestionPl;
	private QuestionPanel moveUnitsQuestionPl;

	private UnitNumberPanel attackNumberPl;
	private UnitNumberPanel moveAttackNumberPl;
	private UnitNumberPanel defenseNumberPl;
	private UnitNumberPanel moveNumberPl;

	private JPanel gamePl;
	/*
	 * Die lokslr Speicherung der Variable currentState im Client ist eine Notloesung, da die Abfrage des currentStates von 
	 * Risiko immer wieder zu Problemen gefuehrt hat. Aufgrund von Zeitknappheit konnte der Fehler nicht weiter gesucht 
	 * werden. Damit das Spiel nicht immer wieder aus diesem Grund abstuerzt, haben wir uns dazu entschieden, die Variable 
	 * im Client zu speichern, was natuerlich zu Probleme fuehren kann, wenn die Methode risiko.setNextState() nicht korrekt 
	 * ausgefuehrt wird.
	 */
	private State currentState;

	public RisikoClientGUI(String host, int port) {

		zweitausendaLook();
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		risiko = new RisikoFassade(socket, this);

		initializeLoginPl();
//		testSetUp(); // legt drei spieler an. zum testen
//		showGamePanel(); // TODO: nur zum testen. wird mit Login dialog aber nicht aufgerufen
	}

	private void initializeLoginPl() {
		// LOGIN
		loginPl = new LoginPanel(this);
		
		wieVielePl = new WieVieleSpielerPanel(this, risiko);
//		neuerSpielerPl = new NeuerSpielerPanel(risiko, this);
		Container c = this.getContentPane();
		c.add(loginPl);
		setSize(new Dimension(340, 340)); // größe vom Loginpanel
//		pack();
		setLocationRelativeTo(null); // setzt Jframe in die Mitte vom Bildschirm
		setVisible(true);
		risiko.aksForServerListenerNr();
	}

	public void setSpieler(String name, int iD) {
		this.name = name;
		this.spielerNummer = iD;
	}

	public void initializeGamePl() {
		System.out.println("GUI mein name ist " + name);
		System.out.println("meine nummer ist " + spielerNummer);
		
//		risiko.spielAufbau();
		Toolkit tk = Toolkit.getDefaultToolkit();
//		int xSize = ((int) tk.getScreenSize().getWidth());
		int xSize = 1400;
		System.out.println("die hoehe vom spiel :" + xSize);
		int ySize = 900;
//		int ySize = ((int) tk.getScreenSize().getHeight());

		System.out.println("die hoehe vom spiel :" + ySize);

		setLocation(0, 0); // setzt Jframe wieder nach links oben (nicht mehr in die mitte)
		this.setSize(xSize, ySize);

		this.setVisible(true);
		this.setTitle("Risiko");

//		this.setSize(945,1750);
		// ermoeglicht das schliessen des fensters
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		gamePl = new JPanel();

		// Layot der Frames
		gamePl.setLayout(new BorderLayout());

//		//WEST enthält aufforderungen und informationen über den spielverlauf
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(2, 1));
		container = new JPanel();
		dialogPl = new DialogPanel(risiko, this);
		westPanel.add(container);
		westPanel.add(dialogPl);

//		//SOUTH enthält informationen über den spieler
		infoPl = new InfoPanel(risiko, name);
		infoPl.setPreferredSize(new Dimension(1400, (int) (ySize * 0.17))); // TODO: tutorium
		risikoKartenTPl = new RisikokartenPanel(risiko, this);
		risikoKartenTPl.setPreferredSize(new Dimension(500, infoPl.getHeight()));
		infoPl.add(risikoKartenTPl, BorderLayout.CENTER);

		// Layout = CardLayout cl
		container.setLayout(cl);
		container.setPreferredSize(new Dimension(200, 60));
		container.setBorder(BorderFactory.createLineBorder(Color.black));

		gamePl.add(westPanel, BorderLayout.WEST);
		gamePl.add(infoPl, BorderLayout.SOUTH);

		this.add(gamePl);
		dicePl = new DicePanel();
//		container.add(dicePl, "dice");
		this.setVisible(true);
	}
	
	public void initializeFromLaden() {
		System.out.println("GUI mein name ist " +  name);
		System.out.println("GUI meine spielerNr ist " + spielerNummer);
		System.out.println("GUI meine serverListenerNr ist " + sListenerNr);
		if(name.equals(risiko.gibAktivenPlayer().getName())) {
			System.out.println("ich bin der aktive spieler");
		} else {
			System.out.println("ich bin passiver Spieler");
		}
		// TODO: hier muss vom laden gesztarzez werden
		
	}

	public void showGamePanel() {
		if(name == null) {
			System.out.println("ich hab keinen namen"); // TODO: Methode um socket verbindung zu trennen und sorry spiel voll panel anzuzeigen
//			System.exit(0);
		} else {
		initializeGamePl();
		worldPl = new WorldPanel(this, risiko, spielerNummer);
		gamePl.add(worldPl, BorderLayout.CENTER);
		showPanel(gamePl);
		showIndividuellesPanel();
		}
//		showQuestion();
	}


	// je nach spielphase wird ein anderes panel im container-panel angezeigt
	public void showQuestion() {
		System.out.println("show Question <-------------");
		switch (currentState) {
//		switch (risiko.getCurrentState()) {
		case SETUNITS:
			System.out.println("set units funktioniert");
			eintauschPl = new EintauschPanel(this, risiko);
			System.out.println("??");
			container.add(eintauschPl, "eintausch");
			cl.show(container, "eintausch");
			break;
		case ATTACK:
			System.out.println("aaaaaaaataaaaaaaaaack");
			if (risiko.kannAngreifen()) {
				attackQuestionPl = new QuestionPanel(this, risiko, "state", spielerNummer);
				container.add(attackQuestionPl, "attackQuestion");
				cl.show(container, "attackQuestion");
			} else {
				JOptionPane.showMessageDialog(null, "Du kannst leider niemanden angreifen.");
				currentState = currentState.setNextState();
				risiko.setNextState();
				showQuestion();
			}
			break;
		case CHANGEUNITS:
			if (risiko.kannVerschieben(risiko.gibAktivenPlayer())) {
				
				System.out.println("kann ich verschieben GUI <-----------------------????????????????????????????ß");
				moveUnitsQuestionPl = new QuestionPanel(this, risiko, "state", spielerNummer);
				container.add(moveUnitsQuestionPl, "moveUnitsQuestion");
				cl.show(container, "moveUnitsQuestion");
			} else {
				JOptionPane.showMessageDialog(null, "Du kannst leider keine Einheiten verschieben.");
				currentState = currentState.setNextState();
				risiko.setNextState();
				risiko.setNextPlayer();
				infoPl.update();
				risikoKartenTPl.setUp();
				pausePl = new PausePanel(this.spielerNummer, risiko);
				container.add(pausePl, "pausePl");
				cl.show(container, "pausePl");
//				showQuestion();
			}
			break;
		default:
			// TODO
			break;
		}
	}

	@Override
	public void eintauschButtonClicked(String answer) {
		if (answer.equals("setzen")) {
			System.out.println("show set uniiiits");
			showSetUnits();
//			risiko.allUpdate("setUnits");
		} else {
			cardRequestPl = new RequestPanel(CountryRequest.CARDREQUEST, risiko);
			container.add(cardRequestPl, "cardRequest");
			cl.show(container, "cardRequest");
			risiko.setTauschZeit(true);
		}
	}

	public void showSetUnits() {
//		dialogPl.update("setUnits");
		int units = risiko.errechneVerfuegbareEinheiten();
		System.out.println("Verfügbare Einheiten: " + units);
		setUnitsPl = new SetUnitsPanel(units, risiko);
		container.add(setUnitsPl, "setUnits");
		cl.show(container, "setUnits");
		risiko.setLandClickZeit(true);
	}

	@Override // question panel
	public void answerSelected(boolean answer, String phase) {
		if (answer) {
			moveAttackNumberPl = new UnitNumberPanel(this, UnitNumber.MOVEATTACK, risiko, spielerNummer);
			container.add(moveAttackNumberPl, "moveAttack");
			cl.show(container, "moveAttack");
		} else {
			showQuestion();
		}
	}

	@Override // question panel
	// antwortListener vom Question Panel
	public void answerSelected(boolean answer) {
		if (answer) {
			// wenn mit ja geantwortet wird:
			switch (currentState) {
//			switch (risiko.getCurrentState()) {
			case SETUNITS:
//				cardRequestPl = new RequestPanel(CountryRequest.CARDREQUEST, risiko);
//				container.add(cardRequestPl, "cardRequest");
//				cl.show(container, "cardRequest");
//				risiko.setTauschZeit(true);
//				break;
			case ATTACK:
				dialogPl.update("attack");
				attackFromPl = new RequestPanel(CountryRequest.ATTACKCOUNTRY, risiko);
				container.add(attackFromPl, "attackFrom");
				cl.show(container, "attackFrom");
				risiko.setLandClickZeit(true);
				break;
			case CHANGEUNITS:
				dialogPl.update("moveUnits");
				moveFromPl = new RequestPanel(CountryRequest.MOVEFROMCOUNTRY, risiko);
				container.add(moveFromPl, "moveFrom");
				cl.show(container, "moveFrom");
				risiko.setLandClickZeit(true);
				break;
			// wenn mit nein geantwortet wird:
			}
		} else {
			switch (currentState) {
//			switch (risiko.getCurrentState()) {
			case SETUNITS:
//				showSetUnits();
//				break;
			case ATTACK:
				currentState = currentState.setNextState();
				risiko.setNextState();
				showQuestion();
				break;
			case CHANGEUNITS:
				// dialog-fenster: dein zug ist beendet
//				wenn keine Einheiten mehr getauscht werden sollen, wird der Zug beendet, dazu wird vorerst überprüft, ob ein Land
//				eingenommen wurde, dann wird eine Einheitenkarte gezogen
//				die Methode zieheEinheitenkarte prüft, ob ein Land eingenommen wurde, zieht automatisch eine Risikokarte und gibt als boolean
//				zurück, ob ein Land eingenommen wurde 
				if (risiko.zieheEinheitenkarte()) {
//					updateKartenpanel2();
					for (Risikokarte karte : risiko.gibAktivenPlayer().getEinheitenkarten()) {
						System.out.println(karte);
					}
					// info, welche risikokarte gezogen wurde
					JOptionPane.showMessageDialog(null,
							"Du hast ein Land erobert und bekommst eine Risikokarte \n"
									+ risiko.gibAktivenPlayer().getEinheitenkarten()
											.get(risiko.gibAktivenPlayer().getEinheitenkarten().size() - 1));
					risikoKartenTPl.setUp();
				}
//				risikoKartenTPl.setUp();
				currentState = currentState.setNextState();
				risiko.setNextState();
				risiko.setNextPlayer();
				infoPl.update();
				pausePl = new PausePanel(this.spielerNummer, risiko);
				container.add(pausePl, "pausePl");
				cl.show(container, "pausePl");
				System.out.println("Nächste Spielphase");
				break;
			default:
				// TODO
				break;
			}
		}
	}

	@Override // unit number panel
	// unitNumberListener, die UnitNumber gibt an, in welcher Spielphase wir uns befinden (attack, defense, move)
	public void numberLogged(int number, UnitNumber un) {
		System.out.println("Status un: " + un);
		switch (un) {
		case ATTACK:
			if ((number > (worldPl.getAttackLand1().getEinheiten() - 1)) || number > 3 || number < 1) {
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl Einheiten.");
			} else {
				try {
					risiko.attackStart(worldPl.getAttackLand1(), worldPl.getAttackLand2(), number);
				} catch (LandNichtInBesitzException | LandInBesitzException e) {
					JOptionPane.showMessageDialog(null, e);
					e.printStackTrace();
				}
				pausePl = new PausePanel(this.spielerNummer, risiko);
				container.add(pausePl, "pausePl");
				cl.show(container, "pausePl");
			}
			break;
		case MOVEATTACK:
//			if (number > worldPl.getAttackLand1().getEinheiten() - 1) {
//				JOptionPane.showMessageDialog(null, "Ungültige Anzahl Einheiten.");
//			} else {
//				risiko.moveUnitsGueltig(worldPl.getAttackLand1(), worldPl.getAttackLand2(), number);
//				updateWorld();
//				updateMoveUnits(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number, name);
//				showQuestion();
//			}
			if (risiko.moveUnitsGueltig(worldPl.getAttackLand1(), worldPl.getAttackLand2(), number)) {
				updateWorld();
				updateMoveUnits(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number, name);
				// TODO:Check, ob durch das verschieben von einheiten eine Mission erfüllt wurde
				if (win()) {
					risiko.getGewinner();
				}
				showQuestion();
			} else {
				JOptionPane.showMessageDialog(null, "Ungueltige Anzahl Einheiten!");
			}
			break;
		case DEFENSE: 
			int defLandUnits = risiko.getDefLandUnits();
			// wenn die defense-nummer eingeloggt wurde, wird die attack hier durchgeführt
			if (number <= defLandUnits && number < 3) {
				pausePl = new PausePanel(this.spielerNummer, risiko);
				container.add(pausePl, "pausePl");
				cl.show(container, "pausePl");
				risiko.attackFinal(number);
		 
//		   wenn defense ungültige anzahl einheiten angegeben hat:
			} else {
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl an Einheiten!");
			}
			break;
		case MOVE:
			if (risiko.moveUnitsGueltig(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number)) {
//				try {
//					risiko.moveUnits(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number);
					updateWorld();
					updateMoveUnits(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number, name);
					// TODO:Check, ob durch das verschieben von einheiten eine Mission erfüllt wurde
					if (win()) {
						risiko.getGewinner();
//						JOptionPane.showMessageDialog(null,
//								risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
					}
//				} catch (LandExistiertNichtException | ZuWenigEinheitenException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				showQuestion();
			} else {
				JOptionPane.showMessageDialog(null, "Ungueltige Anzahl Einheiten!");
			}
		}
	}

	public void continueSetUnits() {
		if (risiko.gibAktivenPlayer().getNummer() == spielerNummer) {
			if (setUnitsPl.getVerfuegbareEinheiten() > 0) {
				setUnitsPl.update();
			} else {
				risiko.setLandClickZeit(false);
				currentState = currentState.setNextState();
				risiko.setNextState();
				showQuestion();
			}
		}
	}

	@Override // worldlistener
	public void countryClicked(Land land) {
		switch (currentState) {
//		switch (risiko.getCurrentState()) {
		case SETUNITS:
			//wenn wir uns im setUnits State befinden, bedeutet das, dass auf dem land eine Einheit gesetzt wurde
			try {
				risiko.setEinheiten(land, 1);
			} catch (UngueltigeAnzahlEinheitenException e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
				return;
			}
			//der aktive Player updatet sich automatisch, alle anderen werden über die serverListener informiert
			updateDialogSetUnit(land.getName(), name);
			updateWorld();
			//Check, ob durch das Setzen einer Unit die Mission erfüllt wurde
			if (win()) {
				risiko.getGewinner();
//				JOptionPane.showMessageDialog(null,
//						risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
			}
			//auf dem setUnitPl sind die restlichen Einheiten gespeichert und werden runtergezählt
			setUnitsPl.decrementUnits();
			if (setUnitsPl.getVerfuegbareEinheiten() > 0) {
				setUnitsPl.update();
			} else {
				risiko.setLandClickZeit(false);
				currentState = currentState.setNextState();
				risiko.setNextState();
				showQuestion();
			}
			break;
		case ATTACK:
			//wenn attackState 2 ist, wurde nur das erste Land eingeloggt, das zweite wird erwartet
			if (worldPl.getAttackState() == 2) {
				attackToPl = new RequestPanel(CountryRequest.DEFENSECOUNTRY, risiko);
				container.add(attackToPl, "attackTo");
				risiko.setLandClickZeit(true);
				cl.show(container, "attackTo");
			} else {
				// wenn attackState nicht 2 ist, wird im Dialog-Panel abgefragt, wie viele
				// Einheiten angreifen sollen
				attackNumberPl = new UnitNumberPanel(this, UnitNumber.ATTACK, risiko, spielerNummer);
				container.add(attackNumberPl, "attackNumber");
				cl.show(container, "attackNumber");
				// & es kann nicht mehr auf ein Land geklickt werden
				risiko.setLandClickZeit(false);
			}
			break;
		case CHANGEUNITS:
			// abfrage nach dem stand der Phase (2 > das 2.Land fehlt; 1 > das erste Land wird erwartet
			if (worldPl.getMoveState() == 2) {
				moveToPl = new RequestPanel(CountryRequest.MOVETOCOUNTRY, risiko);
				container.add(moveToPl, "moveTo");
				risiko.setLandClickZeit(true);
				cl.show(container, "moveTo");
			} else if (worldPl.getMoveState() == 1) {
				System.out.println("<----------------------- bin ich hier im moveState2222?? GuiRequest");
				moveNumberPl = new UnitNumberPanel(this, UnitNumber.MOVE, risiko, spielerNummer);
				container.add(moveNumberPl, "moveNumber");
				cl.show(container, "moveNumber");
				risiko.setLandClickZeit(false);
			}
		}
	}

	// ------------------------ UPDATE'S from SRP -------------------------------\\
	public void updateWorld() {
		worldPl.removeAll();
		worldPl.revalidate();
		worldPl.repaint();
	}

	public void updateDialog(String ereignis) {
		dialogPl.update(ereignis);
	}

	public void updateDialogSetUnit(String land, String player) {
		System.out.println("bei updatedialog in gui angekommen");
		dialogPl.updateSetUnit(land, player);
	}
	
	public void updateAttack(Attack attackObjekt) {
		dialogPl.update(attackObjekt);
		dicePl.setAttack(attackObjekt);
		dicePl.showResult();
		
		String winnerName = attackObjekt.getWinner().getName();
		String aktuellerPlayerName = risiko.gibAktivenPlayer().getName();
		
		System.out.println("name aktiver player: " + risiko.gibAktivenPlayer());
		System.out.println("name gewinner: " + attackObjekt.getWinner());
		System.out.println("besitzer def land: " +attackObjekt.getDefLand().getBesitzer());
		
//		Ausgabe im JOptionPane:
		//hier kann die wiederholte abfrage, ob es sich um den aktivenSpieler handelt bestimmt verkürzt werden
		String ergebnis = "";
		if (attackObjekt.getResult().get(0) == -1) {
			ergebnis += attackObjekt.getAttacker() + " verliert " + -attackObjekt.getResult().get(0)
					+ " Einheit, ";
		} else {
			ergebnis += attackObjekt.getAttacker() + " verliert " + -attackObjekt.getResult().get(0)
					+ " Einheiten, ";
		}
		if (attackObjekt.getResult().get(1) == -1) {
			ergebnis += attackObjekt.getDefender() + " verliert " + -attackObjekt.getResult().get(1)
					+ " Einheit.";
		} else {
			ergebnis += attackObjekt.getDefender() + " verliert " + -attackObjekt.getResult().get(1)
					+ " Einheiten.";
		}
		JOptionPane.showMessageDialog(null, ergebnis);
		
		if (winnerName.equals(aktuellerPlayerName)
				&& attackObjekt.getDefLand().getBesitzer().getName().equals(aktuellerPlayerName)) {
			JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat gewonnen und nimmt "
					+ attackObjekt.getDefLand() + " ein.");
			updateWorld();
			// check, ob jemand gewonnen hat
			if (win()) {
				risiko.getGewinner();
//				JOptionPane.showMessageDialog(null,
//						risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
			}
			if(risiko.gibAktivenPlayer().getNummer() == spielerNummer) {
				if (attackObjekt.getAttLand().getEinheiten() > 1) {
					System.out.println("einheiten verbleibend: " + worldPl.getAttackLand1().getEinheiten());
					QuestionPanel nachrueckPl = new QuestionPanel(this, risiko, "nachruecken", spielerNummer);
					container.add(nachrueckPl, "nachruecken");
					cl.show(container, "nachruecken");
				} else {
					showQuestion();
				}
			}
		} else if (winnerName.equals(risiko.gibAktivenPlayer().getName())) {
			JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat den Kampf gewonnen.");
			updateWorld();
			if(risiko.gibAktivenPlayer().getNummer() == spielerNummer) {
				showQuestion();
			}
		} else {
			JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat den Kampf verloren!");
			updateWorld();
			// check, ob jemand gewonnen hat
			if (win()) {
				risiko.getGewinner();
//				JOptionPane.showMessageDialog(null,
//						risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
			}
			if(risiko.gibAktivenPlayer().getNummer() == spielerNummer) {
				showQuestion();
			}
		}
	}
	
	public void updateMoveUnits(Land von, Land zu, int unit, String player) {
		dialogPl.update(von, zu, unit, player);
	}
	
	

/////////////////////*********SHOW METHODEN**********\\\\\\\\\\\\\\\\\\\\\

	public void showPanel(JPanel panel) {
		Container c = getContentPane();
		c.removeAll();
		c.add(panel);
		c.revalidate();
		c.repaint();
	}

	public void showNeuesSpielPanel() {
		if (risiko.getPlayerArray().size() == 0) {
			showPanel(wieVielePl);
		} else {
			showNeuerSpielerPanel();
		}
	}

	public void showLoginPanel() {
//		loginPl.setPreferredSize(new Dimension(50, 50));
		this.pack();
		showPanel(loginPl);
	}
	
	public void showWerBistDuPanel() {
		werBistPl = new werBistDuPanle(this,risiko);
		this.pack();
		werBistPl = new werBistDuPanle(this, risiko);
		showPanel(werBistPl);
		werBistPl.setSize(new Dimension(340, 340));
	}

	public void showLadePanel() {
		ladePl = new LadePanel(this, risiko);
		System.out.println("wo hab ich probleme?");
		showPanel(ladePl);
	}

	public void showNeuerSpielerPanel() {
		neuerSpielerPl = new NeuerSpielerPanel(risiko, this);
		showPanel(neuerSpielerPl);
	}

	// Diese Methode prüft, wer der Client ist zeigt dementsprechend das
	// "interaktive" Panel an
	public void showIndividuellesPanel() {
		if (risiko.gibAktivenPlayer().getName().equals(this.name)) {
			//eigentlich unschoen, wird quasi lokal kopiert, aber somit muss seltener der currentstate vom risiko abgefragt werden
			currentState = State.SETUNITS;
			showQuestion();
		} else {
			pausePl = new PausePanel(this.spielerNummer, risiko);
			container.add(pausePl, "pausePl");
			cl.show(container, "pausePl");
		}
	}

//TODO: ueberfluessig?
	public int getSpielerAnzahl() {
		return wieVielePl.getAnzahlSpieler();
	}

	public static void main(String[] args) {
		int portArg = 0;
		String hostArg = null;
		InetAddress ia = null;

		switch (args.length) {
		case 0:
			try {
				ia = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.println("XXX InetAddress Fehler : " + e);
				System.exit(0);
				e.printStackTrace();
			}
			hostArg = ia.getHostName();
			portArg = DEFAULT_PORT;
			break;
		case 1:
			portArg = DEFAULT_PORT;
			hostArg = args[0];
			break;
		case 2:
			hostArg = args[0];
			try {
				portArg = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.out.println("TODO");
				System.exit(0);
			}
		}

		final String host = hostArg;
		final int port = portArg;

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				RisikoClientGUI gui = new RisikoClientGUI(host, port);
			}
		});
	}

	public void showSetUnits(int plus) {
		dialogPl.update("setUnits");
		int units = risiko.errechneVerfuegbareEinheiten() + plus;
		System.out.println("Verfuegbare Einheiten: " + units);
		setUnitsPl = new SetUnitsPanel(units, risiko);
		container.add(setUnitsPl, "setUnits");
		cl.show(container, "setUnits");
		risiko.setLandClickZeit(true);
	}

	@Override
	public void combiAusgewaehlt(ArrayList<Integer> auswahl) {
		//wenn risikokarten erfolgreich eingetauscht wurden, muss noch überprüft werden, ob ein land im besitz ist und dann die einheiten gutgeschrieben werden
		for (Land l : risiko.gibAktivenPlayer().getBesitz()) {
			for (Integer landId: auswahl) {
				// falls der spieler das Land von der Risikokarte beim eintauschen besitzt..
				try {
					if (l.getName().equals(risiko.getLandById(landId).getName())) {
						// .. wird auf das land zwei einheiten gesetzt
						try {
							risiko.setEinheiten(l, 2);
						} catch (UngueltigeAnzahlEinheitenException e) {
							JOptionPane.showMessageDialog(null, e);
							e.printStackTrace();
						}	
						updateWorld(); 
					}
				} catch (LandExistiertNichtException e) {
					JOptionPane.showMessageDialog(null, "Du hast ins Meer geklickt... So gewinnst du nie!");
					System.out.println(e);
					return;
				}
			}
		}
		risiko.setTauschZeit(false);
		showSetUnits(5);
		infoPl.update();
		risikoKartenTPl.setUp();
	}

	@Override
	public boolean pruefenObDreiRichtig() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean win() {
//		erst wird gecheckt, ob der aktuellePlayer gewonnen hat
		if (aktuellerPlayerWin()) {
			return true;
//		im Anschluss wird ueberprueft, ob ein anderer Player gewonnen hat
		} else if (risiko.allMissionsComplete()) {
			return true;
		}
		return false;
	}

	// Methode schaut ob aktueller Spieler gewonnen hat
	public boolean aktuellerPlayerWin() {
		// fragt ob der aktuellePlayer gewonnen hat
		if (risiko.rundeMissionComplete()) {
			return true;
		}
		return false;
	}

	@Override // risikokartenpanel
	public void updateKartenpanel() {
		risikoKartenTPl.invalidate();
		risikoKartenTPl.repaint();
	}

	public void updateKartenpanel2() {
		risikoKartenTPl.setUp();
	}

	public void zweitausendaLook() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getNameFromGui() {
		return name;
	}

	//// ---------------------- TestSpielstart ohne login
	//// ----------------------------\\\\ nicht auf Server Erprobt
	public void testSetUp() {
		try {
			risiko.playerAnlegen("Annie", "rot", 0);
			risiko.playerAnlegen("Tobi", "gruen", 1);
			risiko.playerAnlegen("Hannes", "blau", 2);
		} catch (SpielerNameExistiertBereitsException e) {
			System.out.println(e.getLocalizedMessage());
		}
		risiko.spielAufbau();
		for (int x = 0; x < 10; x++) {
			System.out.println("hier :" + x % 3);
			risiko.getPlayerArray().get(x % 3).setEinheitenkarte(risiko.getRisikoKarten().get(x));
			System.out.println(risiko.getPlayerArray().get(x % 3).getEinheitenkarten().get(0).getSymbol());
		}
	}

	// ___________________________ Speichern & Laden
	// __________________________________

	@Override
	public void speichern() {
		String dateiname = JOptionPane.showInputDialog("Name der Datei: ");
		System.out.println(dateiname);
		risiko.spielSpeichern(dateiname);
	}

	@Override
	public void spielLaden(String dateiname) {
		try {
			try {
				risiko.spielLaden(dateiname);
			} catch (LandExistiertNichtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SpielerNameExistiertBereitsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("hier gehts weiter mit wer bist du ");
		showWerBistDuPanel();
//		showGamePanel();
	}
	
	public void disconnect() {
		JOptionPane.showMessageDialog(null, "Das Spiel wurde gespeichert und ist nun beendet.");
		setVisible(false);
		dispose();
//		try {
//			socket.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void spielLadenTrue() {
		risiko.spielLadenTrue();
		
	}

	public boolean spielWurdeGeladen() {
		return risiko.spielWurdeGeladen();
	}
	

	@Override
	public void spielerLaden(String name, int spielerNr) {
		this.name = name;
		this.spielerNummer = spielerNr;
		risiko.spielerWurdeGeladen();
//		initializeGamePl(); 
	}

	public void setAttackPlayer(String attLand, String defLand, String attacker, String defender) {
		if (name.equals(defender)) {
			System.out.println("Ich " + name + " bin verteidiger");
			defenseNumberPl = new UnitNumberPanel(this, UnitNumber.DEFENSE, risiko, spielerNummer);
			container.add(defenseNumberPl, "defenseNumber");
			cl.show(container, "defenseNumber");
		} //und panel updaten bei allen
	}
	
	public void showWinner(String name) {
		JOptionPane.showMessageDialog(null, name + " hat gewonnen!! Wuuuhuuu!!");
	}
	
	public void setCurrentState(State state) {
		currentState = state;
	}
	
	public void setServerListenerNr(int serverListenerNr) {
		this.sListenerNr = serverListenerNr;
	}
	public void aktiveClientAskHowMany() {
		risiko.aktiveClientAskHowMany(sListenerNr);
	}
	// wenn man auf zurueck im wievieleSpielerPanel drückt, wird neuesSpielStarten der anderen  Clients wieder aktiviert
	public void pressBackButn() {
		risiko.pressBackButn(sListenerNr);
	}
	public void setEnableNeuesSpielbtn(boolean an) {
		loginPl.setEnableNeuesSpielbtn(an);
	}

	public void spielEintreitenBtn() {
		risiko.spielEintreitenBtn(sListenerNr);
	}

	public void setSpielEintreitenBtn() {
		loginPl.setSpielEintreitenBtn();
		
	}

	public void removeLoginPanel() {
		this.remove(loginPl);
		this.remove(neuerSpielerPl);
		this.remove(wieVielePl);
		
	}

	public void farbeAktualisieren(String farbe) {
		risiko.setFarbeAuswaehlen(farbe);
	}

	public void setSpielgeladenTrue() {
		spielGeladenTrue = true;
	}

}

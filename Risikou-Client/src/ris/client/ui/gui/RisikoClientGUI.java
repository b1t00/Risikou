package ris.client.ui.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.net.InetAddress;
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
import ris.common.exceptions.LandExistiertNichtException;
import ris.common.exceptions.SpielerNameExistiertBereitsException;
import ris.common.exceptions.ZuWenigEinheitenException;
import ris.common.exceptions.ZuWenigEinheitenNichtMoeglichExeption;
import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Attack;
import ris.common.valueobjects.Land;
import ris.common.valueobjects.Risikokarte;

public class RisikoClientGUI extends JFrame implements QuestionListener, WorldListener, UnitNumberListener,
		kartenAuswahlListener, RisikoKartenListener, EintauschListener, SpeichernListener, LadeListener {

	public static final int DEFAULT_PORT = 6789;

	private RisikoInterface risiko;

	// LOGIN //
	private LoginPanel loginPl;
	private LadePanel ladePl;
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

	private QuestionPanel changeCardsPl;
	private QuestionPanel attackQuestionPl;
	private QuestionPanel moveUnitsQuestionPl;

	private UnitNumberPanel attackNumberPl;
	private UnitNumberPanel moveAttackNumberPl;
	private UnitNumberPanel defenseNumberPl;
	private UnitNumberPanel moveNumberPl;

	private JPanel gamePl;

	public RisikoClientGUI(String host, int port) {

		zweitausendaLook();
		risiko = new RisikoFassade(host, port);
		initializeLoginPl();
//		testSetUp(); // legt drei spieler an. zum testen
//		showGamePanel(); // TODO: nur zum testen. wird mit Login dialog aber nicht aufgerufen
	}

	private void initializeLoginPl() {
		// LOGIN
		loginPl = new LoginPanel(this);
		wieVielePl = new WieVieleSpielerPanel(this);

		neuerSpielerPl = new NeuerSpielerPanel(risiko, this);
		Container c = this.getContentPane();
		c.add(loginPl);
		setSize(new Dimension(340, 340)); // größe vom Loginpanel
//		pack();
		setLocationRelativeTo(null); // setzt Jframe in die Mitte vom Bildschirm
		setVisible(true);
	}

	private void initializeGamePl() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		System.out.println(xSize);
		int ySize = ((int) tk.getScreenSize().getHeight());
		System.out.println(ySize);

		setLocation(0, 0); // setzt Jframe wieder nach links oben (nicht mehr in die mitte)
		this.setSize(xSize, ySize);

		this.setVisible(true);
		this.setTitle("Risiko");

		// ermoeglicht das schliessen des fensters
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		gamePl = new JPanel();

		// Layot der Frames
		gamePl.setLayout(new BorderLayout());

//		//WEST
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(2, 1));
		container = new JPanel();
		dialogPl = new DialogPanel(risiko, this);
		westPanel.add(container);
		westPanel.add(dialogPl);

//		//SOUTH
		infoPl = new InfoPanel(risiko);
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
		container.add(dicePl, "dice");
		this.setVisible(true);

	}

	public void showGamePl() {
		this.add(gamePl);
		// CENTER
		worldPl = new WorldPanel(this, risiko);
		gamePl.add(worldPl, BorderLayout.CENTER);
		this.add(gamePl);
	}

	// je nach spielphase wird ein anderes panel im container-panel angezeigt
	public void showQuestion() {
		switch (risiko.getCurrentState()) {
		case SETUNITS:
			eintauschPl = new EintauschPanel(this, risiko);
			container.add(eintauschPl, "eintausch");
			cl.show(container, "eintausch");
			break;
		case ATTACK:
			if (risiko.kannAngreifen(risiko.gibAktivenPlayer())) {
				attackQuestionPl = new QuestionPanel(this, risiko, "state");
				container.add(attackQuestionPl, "attackQuestion");
				cl.show(container, "attackQuestion");
			} else {
				JOptionPane.showMessageDialog(null, "Du kannst leider niemanden angreifen.");
				risiko.setNextState();
				showQuestion();
			}
			break;
		case CHANGEUNITS:
			if (risiko.kannVerschieben(risiko.gibAktivenPlayer())) {
				moveUnitsQuestionPl = new QuestionPanel(this, risiko, "state");
				container.add(moveUnitsQuestionPl, "moveUnitsQuestion");
				cl.show(container, "moveUnitsQuestion");
			} else {
				JOptionPane.showMessageDialog(null, "Du kannst leider keine Einheiten verschieben.");
				risiko.setNextState();
				risiko.setNextPlayer();
				infoPl.update();
				risikoKartenTPl.setUp();
				showQuestion();
			}
			break;
		default:
			// TODO
			break;
		}
	}

	public void showSetUnits() {
		dialogPl.update("setUnits");
		int units = risiko.errechneVerfuegbareEinheiten();
		System.out.println("Verfügbare Einheiten: " + units);
		setUnitsPl = new SetUnitsPanel(units, risiko);
		container.add(setUnitsPl, "setUnits");
		cl.show(container, "setUnits");
		risiko.setLandClickZeit(true);
	}

	@Override
	public void eintauschButtonClicked(String answer) {
		if (answer.equals("setzen")) {
			showSetUnits();
		} else {
			cardRequestPl = new RequestPanel(CountryRequest.CARDREQUEST, risiko);
			container.add(cardRequestPl, "cardRequest");
			cl.show(container, "cardRequest");
			risiko.setTauschZeit(true);
		}
	}

	@Override // question panel
	public void answerSelected(boolean answer, String phase) {
		if (answer) {
			moveAttackNumberPl = new UnitNumberPanel(this, UnitNumber.MOVEATTACK, risiko);
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
			switch (risiko.getCurrentState()) {
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
			switch (risiko.getCurrentState()) {
			case SETUNITS:
//				showSetUnits();
//				break;
			case ATTACK:
				risiko.setNextState();
				showQuestion();
				break;
			case CHANGEUNITS:
				// dialog-fenster: dein zug ist beendet
//				wenn keine Einheiten mehr getauscht werden sollen, wird der Zug beendet, dazu wird vorerst überprüft, ob ein Land
//				eingenommen wurde, dann wird eine Einheitenkarte gezogen
//				die Methode zieheEinheitenkarte prüft, ob ein Land eingenommen wurde, zieht automatisch eine Risikokarte und gibt als boolean
//				zurück, ob ein Land eingenommen wurde 
//				TODO: eventuell vorher abfrage, ob land eingenommen wurde
				if (risiko.zieheEinheitenkarte(risiko.gibAktivenPlayer())) {
					updateKartenpanel2();
					for (Risikokarte karte : risiko.gibAktivenPlayer().getEinheitenkarten()) {
						System.out.println(karte);
					}
					// info, welche risikokarte gezogen wurde
					JOptionPane.showMessageDialog(null,
							"Du hast ein Land erobert und bekommst eine Risikokarte \n"
									+ risiko.gibAktivenPlayer().getEinheitenkarten()
											.get(risiko.gibAktivenPlayer().getEinheitenkarten().size() - 1));
				}
				risiko.setNextState();
				risiko.setNextPlayer();
				infoPl.update();
				risikoKartenTPl.setUp();
				showQuestion();
				System.out.println("Nächste Spielphase");
				break;
			default:
				// TODO
				break;
			}
		}
	}

	@Override // unit number panel
	// unitNumberListener, die UnitNumber gibt an, in welcher Spielphase wir
	// unsbefinden (attack, defense, move)
	public void numberLogged(int number, UnitNumber un) throws ZuWenigEinheitenNichtMoeglichExeption {
		System.out.println("Status un: " + un);
		switch (un) {
		case ATTACK:
			if ((number > (worldPl.getAttackLand1().getEinheiten() - 1)) || number > 3 || number < 1) {
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl Einheiten.");
			} else {
				defenseNumberPl = new UnitNumberPanel(this, UnitNumber.DEFENSE, risiko);
				container.add(defenseNumberPl, "defenseNumber");
				cl.show(container, "defenseNumber");
			}
			break;
		case MOVEATTACK:
			if (number > worldPl.getAttackLand1().getEinheiten() - 1) {
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl Einheiten.");
			} else {
				try {
					risiko.moveUnits(worldPl.getAttackLand1(), worldPl.getAttackLand2(), number);
					updateWorld();
				} catch (LandExistiertNichtException | ZuWenigEinheitenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showQuestion();
			}
			break;
		case DEFENSE:
			// wenn die defense-nummer eingeloggt wurde, wird die attack hier durchgeführt
			if (number <= worldPl.getAttackLand2().getEinheiten() && number < 3) {
				Attack attackObjekt = null;
				try {
					attackObjekt = risiko.attack(worldPl.getAttackLand1(), worldPl.getAttackLand2(),
							attackNumberPl.getNumber(), defenseNumberPl.getNumber());
					dialogPl.update(attackObjekt);
					dicePl.setAttack(attackObjekt);
					dicePl.showResult();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// hier eher dice panel aufrufen
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
				if (attackObjekt.getWinner().equals(risiko.gibAktivenPlayer())
						&& worldPl.getAttackLand2().getBesitzer().equals(risiko.gibAktivenPlayer())) {
					JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat gewonnen und nimmt "
							+ worldPl.getAttackLand2() + " ein.");
					updateWorld();
					// check, ob jemand gewonnen hat
					if (win()) {
						JOptionPane.showMessageDialog(null,
								risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
					}
					if (worldPl.getAttackLand1().getEinheiten() > 1) {
						System.out.println("hier müsste panel kommen");
						System.out.println("einheiten verbleibend: " + worldPl.getAttackLand1().getEinheiten());
						QuestionPanel nachrueckPl = new QuestionPanel(this, risiko, "nachruecken");
						container.add(nachrueckPl, "nachruecken");
						cl.show(container, "nachruecken");
					} else {
						showQuestion();
					}
				} else if (attackObjekt.getWinner().equals(risiko.gibAktivenPlayer())) {
					JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat den Kampf gewonnen.");
					updateWorld();
					showQuestion();
				} else {
					JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat den Kampf verloren!");
					updateWorld();
					// check, ob jemand gewonnen hat
					if (win()) {
						JOptionPane.showMessageDialog(null,
								risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
					}
					showQuestion();
				}
				// wenn defense ungültige anzahl einheiten angegeben hat:
			} else {
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl an Einheiten!");
			}
			break;
		case MOVE:
			if (risiko.moveUnitsGueltig(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number)) {
				try {
					risiko.moveUnits(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number);
					updateWorld();
					dialogPl.update(worldPl.getAttackLand1(), worldPl.getAttackLand2(), number);
					// Check, ob durch das verschieben von einheiten eine Mission erfüllt wurde
					if (win()) {
						JOptionPane.showMessageDialog(null,
								risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
					}
				} catch (LandExistiertNichtException | ZuWenigEinheitenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showQuestion();
			} else {
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl Einheiten!");
			}
		}
	}

	@Override // worldlistener
	public void countryClicked(Land land) {
		switch (risiko.getCurrentState()) {
		case SETUNITS:
			System.out.println("wurde verarbeitet");
			try {
				risiko.setEinheiten(land, 1);
				land.setEinheiten(1);
				updateWorld();
				dialogPl.update(land);
				// Check, ob durch das Setzen einer Unit die Mission erfüllt wurde
				if (win()) {
					JOptionPane.showMessageDialog(null, risiko.getGewinner().getName() + " hat gewonnen!! Wuuuhuuu!!");
				}
			} catch (ZuWenigEinheitenException e) {
				System.out.println(e.getLocalizedMessage());
			}
			setUnitsPl.decrementUnits();
			if (setUnitsPl.getVerfuegbareEinheiten() > 0) {
				setUnitsPl.update();
			} else {
				risiko.setLandClickZeit(false);
				risiko.setNextState();
				showQuestion();
			}
			break;
		case ATTACK:
			// wenn attackState 2 ist, wurde nur das erste Land eingeloggt, das zweite wird
			// erwartet
			if (worldPl.getAttackState() == 2) {
				attackToPl = new RequestPanel(CountryRequest.DEFENSECOUNTRY, risiko);
				container.add(attackToPl, "attackTo");
				risiko.setLandClickZeit(true);
				cl.show(container, "attackTo");
			} else {
				// wenn attackState nicht 2 ist, wird im Dialog-Panel abgefragt, wie viele
				// Einheiten angreifen sollen
				attackNumberPl = new UnitNumberPanel(this, UnitNumber.ATTACK, risiko);
				container.add(attackNumberPl, "attackNumber");
				cl.show(container, "attackNumber");
				// & es kann nicht mehr auf ein Land geklickt werden
				risiko.setLandClickZeit(false);
			}
			break;
		case CHANGEUNITS:
			// abfrage nach dem stand der Phase (2 > das 2.Land fehlt; 1 > das erste Land
			// wird erwartet
			if (worldPl.getMoveState() == 2) {
				moveToPl = new RequestPanel(CountryRequest.MOVETOCOUNTRY, risiko);
				container.add(moveToPl, "moveTo");
				risiko.setLandClickZeit(true);
				cl.show(container, "moveTo");
			} else if (worldPl.getMoveState() == 1) {
				moveNumberPl = new UnitNumberPanel(this, UnitNumber.MOVE, risiko);
				container.add(moveNumberPl, "moveNumber");
				cl.show(container, "moveNumber");
				risiko.setLandClickZeit(false);
			}
		}
	}

	//// TestSpielstart ohne login \\\\
	public void testSetUp() {
		try {
			risiko.playerAnlegen("Annie", "rot", 0);
			risiko.playerAnlegen("Tobi", "gruen", 1);
			risiko.playerAnlegen("Hannes", "blau", 2);
		} catch (SpielerNameExistiertBereitsException e) {
			System.out.println(e.getLocalizedMessage());
		}
//		risiko.set(new Color(226, 19, 43));
//		risiko.setColorArray(new Color(23, 119, 50));
//		risiko.setColorArray(new Color(30, 53, 214));
		risiko.spielAufbau();
		for (int x = 0; x < 10; x++) {
			System.out.println("hier :" + x % 3);
			risiko.getPlayerArray().get(x % 3).setEinheitenkarte(risiko.getRisikoKarten().get(x));
			System.out.println(risiko.getPlayerArray().get(x % 3).getEinheitenkarten().get(0).getSymbol());
		}
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
		synchronized (this) { //oder mit key aber denke muss man nicht
			if (risiko.getPlayerArray().size() == 0) {
				showPanel(wieVielePl);
			}
		}
		// synchronised
		showNeuerSpielerPanel();
	}

	public void showLoginPanel() {
//		loginPl.setPreferredSize(new Dimension(50, 50));
		this.pack();
		showPanel(loginPl);
	}

	public void showLadePanel() {
		ladePl = new LadePanel(this, risiko);
		showPanel(ladePl);
	}

	public void showNeuerSpielerPanel() {
		showPanel(neuerSpielerPl);
	}

	public void showGamePanel() {
		initializeGamePl();
		worldPl = new WorldPanel(this, risiko);
		gamePl.add(worldPl, BorderLayout.CENTER);
		showPanel(gamePl);
		showQuestion();
	}

//TODO: ueberfluessig?
	public int getSpielerAnzahl() {
		return wieVielePl.getAnzahlSpieler();
	}

	public void updateWorld() {
		worldPl.removeAll();
		worldPl.revalidate();
		worldPl.repaint();
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

	public void combiAusgewaehlt(ArrayList<Risikokarte> auswahl) {
		ArrayList<Risikokarte> kicked = auswahl;
		for (Land l : risiko.gibAktivenPlayer().getBesitz()) {
			for (Risikokarte k : kicked) {
				// falls der spieler das Land von der Risikokarte beim eintauschen besitzt..
				if (l.getName().equals(k.getLand().getName())) {
					// .. wird auf das land zwei einheiten gesetzt
					try {
						try {
							risiko.getLandById(l.getNummer()).setEinheiten(2);
						} catch (LandExistiertNichtException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						updateWorld();
					} catch (ZuWenigEinheitenException e) {
						// TODO Auto-generated catch block
						e.getLocalizedMessage();
					}
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
		if (risiko.rundeMissionComplete(risiko.gibAktivenPlayer())) {
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

	@Override
	public void speichern() {
		String dateiname = JOptionPane.showInputDialog("Name der Datei: ");
		System.out.println(dateiname);
		risiko.spielSpeichern(dateiname);
	}

	@Override
	public void spielLaden(String dateiname) {
		try {
			risiko.spielLaden(dateiname);
		} catch (SpielerNameExistiertBereitsException | ZuWenigEinheitenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hier gehts weiter");
		showGamePanel();
	}
}

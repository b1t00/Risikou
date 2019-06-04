package ris.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import ris.local.domain.Risiko;
import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.ZuWenigEinheitenException;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;
import ris.local.ui.gui.swing.panels.DicePanel;
import ris.local.ui.gui.swing.panels.InfoPanel;
import ris.local.ui.gui.swing.panels.LoginPanel;
import ris.local.ui.gui.swing.panels.NeuerSpielerPanel;
import ris.local.ui.gui.swing.panels.QuestionPanel;
import ris.local.ui.gui.swing.panels.QuestionPanel.QuestionListener;
import ris.local.ui.gui.swing.panels.RequestPanel;
import ris.local.ui.gui.swing.panels.RequestPanel.CountryRequest;
import ris.local.ui.gui.swing.panels.RequestPanel.RequestListener;
import ris.local.ui.gui.swing.panels.RisikokartenPanel;
import ris.local.ui.gui.swing.panels.RisikokartenPanel.RisikoKartenListener;
import ris.local.ui.gui.swing.panels.SetUnitsPanel;
import ris.local.ui.gui.swing.panels.UnitNumberPanel;
import ris.local.ui.gui.swing.panels.UnitNumberPanel.UnitNumber;
import ris.local.ui.gui.swing.panels.UnitNumberPanel.UnitNumberListener;
import ris.local.ui.gui.swing.panels.WieVieleSpielerPanel;
import ris.local.ui.gui.swing.panels.WorldPanel;
import ris.local.ui.gui.swing.panels.KartenButton.kartenAuswahlListener;
import ris.local.ui.gui.swing.panels.WorldPanel.WorldListener;
import ris.local.valueobjects.Attack;
import ris.local.valueobjects.Land;
//	MapImage Größe 120 / 711

public class RisikoClientGUI extends JFrame
		implements QuestionListener, WorldListener, RequestListener, UnitNumberListener, kartenAuswahlListener,  RisikoKartenListener{

	private Risiko risiko;

	// LOGIN //
	private LoginPanel loginPl;
	private WieVieleSpielerPanel wieVielePl;
	private NeuerSpielerPanel neuerSpielerPl;
	private RisikokartenPanel risikoKartenTPl;

	private JPanel container;
	private CardLayout cl = new CardLayout();
	private WorldPanel worldPl;
	private InfoPanel infoPl;

//	private DialogPanel dialogPl;
//	private SetUnitsPanel setUnitsPl;

	private DicePanel dicePl;

	// Das RequestPanel wird benötigt, wenn auf ein Land geklickt werden muss
	private RequestPanel attackFromPl;
	private RequestPanel attackToPl;
	private RequestPanel moveFromPl;
	private RequestPanel moveToPl;

	private SetUnitsPanel setUnitsPl;

	private QuestionPanel attackQuestionPl;
	private QuestionPanel moveUnitsQuestionPl;

	private UnitNumberPanel moveNumberPl;
	private UnitNumberPanel attackNumberPl;
	private UnitNumberPanel defenseNumberPl;

	private JPanel gamePl;

	public RisikoClientGUI() {
		risiko = new Risiko();
//		initializeLoginPl();
		testSetUp(); // legt drei spieler an. zum testen
		showGamePanel(); // TODO: nur zum testen. wird mit Login dialog aber nicht aufgerufen
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
		container = new JPanel();
//		
//		//CENTER
		worldPl = new WorldPanel(this, risiko);

//		//SOUTH
		infoPl = new InfoPanel(risiko);
		infoPl.setPreferredSize(new Dimension(1400, (int) (ySize * 0.17)));
		risikoKartenTPl = new RisikokartenPanel(risiko, this);
		risikoKartenTPl.setPreferredSize(new Dimension(500, infoPl.getHeight()));
		infoPl.add(risikoKartenTPl, BorderLayout.CENTER);

		// Layout = CardLayout
		container.setLayout(cl);
		container.setPreferredSize(new Dimension(200, 60));
		container.setBorder(BorderFactory.createLineBorder(Color.black));

//		dicePl = new DicePanel(risiko,this);

		// evtl hier ein Problem, da in demcContainer noch nix ist

		gamePl.add(container, BorderLayout.WEST);
		gamePl.add(infoPl, BorderLayout.SOUTH);
		gamePl.add(worldPl, BorderLayout.CENTER);

//		infoPl.add(risikoKartenTPl);
//		
		this.add(gamePl);

		// ab hier werden die einzelnen Panels erstellt und mit einem key versehen
		attackFromPl = new RequestPanel(this, CountryRequest.ATTACKCOUNTRY, risiko);
		container.add(attackFromPl, "attackFrom");

		attackToPl = new RequestPanel(this, CountryRequest.DEFENSECOUNTRY, risiko);
		container.add(attackToPl, "attackTo");

		moveFromPl = new RequestPanel(this, CountryRequest.MOVEFROMCOUNTRY, risiko);
		container.add(moveFromPl, "moveFrom");

		moveToPl = new RequestPanel(this, CountryRequest.MOVETOCOUNTRY, risiko);
		container.add(moveToPl, "moveTo");

		moveNumberPl = new UnitNumberPanel(this, UnitNumber.MOVE);
		container.add(moveNumberPl, "moveNumber");

		attackNumberPl = new UnitNumberPanel(this, UnitNumber.ATTACK);
		container.add(attackNumberPl, "attackNumber");

		defenseNumberPl = new UnitNumberPanel(this, UnitNumber.DEFENSE);
		container.add(defenseNumberPl, "defenseNumber");

		dicePl = new DicePanel();
		container.add(dicePl, "dice");

		this.setVisible(true);

	}

	public void showGamePl() {
		this.add(gamePl);
		// WEST
//				container = new JPanel();

		// CENTER
//		worldPl = new WorldPanel(this, risiko);

		// SOUTH
//				infoPl = new InfoPanel();

//		container.setLayout(cl);
//		container.setSize(50,100);
//		container.setBorder(BorderFactory.createLineBorder(Color.black));

		gamePl.add(container, BorderLayout.WEST);
//		gamePl.add(worldPl, BorderLayout.CENTER);
		gamePl.add(infoPl, BorderLayout.SOUTH);

		this.add(gamePl);
	}

	// je nach spielphase wird ein anderes panel im container-panel angezeigt
	public void showQuestion() {
		// if player = risiko.gibAktiverPlayer()
		switch (risiko.getCurrentState()) {
		case ATTACK:
			attackQuestionPl = new QuestionPanel(this, risiko);
			container.add(attackQuestionPl, "attackQuestion");
			cl.show(container, "attackQuestion");
			break;
		case CHANGEUNITS:
			moveUnitsQuestionPl = new QuestionPanel(this, risiko);
			container.add(moveUnitsQuestionPl, "moveUnitsQuestion");
			cl.show(container, "moveUnitsQuestion");
			break;
		default:
			// TODO
			break;
		}
	}

	public void showDialog() {
		// if aktuellerPlayer == player{
		int units = risiko.errechneVerfuegbareEinheiten(risiko.gibAktivenPlayer());
		System.out.println("Verfügbare Einheiten: " + units);
		setUnitsPl = new SetUnitsPanel(units, risiko);
		container.add(setUnitsPl, "setUnits");
		cl.show(container, "setUnits");
	}

	@Override
	// antwortListener vom Question Panel
	public void answerSelected(boolean answer) {

		// eventuell noch ein zweiter parameter, um welche frage es sich handelt
		// wenn mit ja geantwortet wird
		if (answer) {
			switch (risiko.getCurrentState()) {
			case ATTACK:
				// ersetzt das fragePanel durch das attackPanel
				cl.show(container, "attackFrom");
				System.out.println("Der Angriff beginnt");
				break;
			case CHANGEUNITS:
				// ersetzt das fragePanel durch das Verschiebe-EinheitenPanel
				cl.show(container, "moveNumber");
				System.out.println("Frage nach move number");
				break;
			default:
				// TODO
				break;
			}
			// wenn mit nein geantwortet wird:
		} else {
			risiko.setNextState();
			switch (risiko.getCurrentState()) {
			case ATTACK:
				cl.show(container, "moveUnitsQuestion");
				break;
			case CHANGEUNITS:
				// dialog-fenster: dein zug ist beendet
				risiko.setNaechsterPlayer();
				// zeigt den neuen dialog
				showQuestion();
				System.out.println("Nächste Spielphase");
				break;
			default:
				// TODO
				break;
			}
		}
	}

	@Override
	// unitNumberListener, die UnitNumber gibt an, in welcher Spielphase wir uns
	// befinden, eventuell unnötig, wenn Turn gefragt werden kann?
	public void numberLogged(int number, UnitNumber un) throws ZuWenigEinheitenNichtMoeglichExeption {
		System.out.println("Status un: " + un);
		switch (un) {
		case ATTACK:
			if ((number > (worldPl.getAttackLand1().getEinheiten() - 1)) || number > 3 || number < 1) {
				System.out.println("Einheiten auf angriffsland: " + worldPl.getAttackLand1().getEinheiten());
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl Einheiten.");
			} else {
				cl.show(container, "defenseNumber");
			}
			break;
		case DEFENSE:
			// wenn die defense-nummer eingeloggt wurde, wird die attack hier durchgeführt
			if (number <= worldPl.getAttackLand2().getEinheiten() && number < 3) {
				Attack attackObjekt = null;
				try {
					attackObjekt = risiko.attack(worldPl.getAttackLand1(), worldPl.getAttackLand2(),
							attackNumberPl.getNumber(), defenseNumberPl.getNumber());
					dicePl.setAttack(attackObjekt);
					dicePl.showResult();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// hier eher dice panel aufrufen
				JOptionPane.showMessageDialog(null,
						risiko.gibAktivenPlayer() + " hat " + attackObjekt.getAttUnits().get(0) + " gewürfelt");

				if (attackObjekt.getWinner().equals(risiko.gibAktivenPlayer())) {
					JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat gewonnen und nimmt "
							+ worldPl.getAttackLand2() + " ein.");
				} else {
					JOptionPane.showMessageDialog(null, attackObjekt.getWinner() + " hat den Kampf gewonnen!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl an Einheiten!");
			}
			showQuestion();
			break;

		case MOVE:
			if (risiko.genugEinheiten(worldPl.getMoveLand1(), number)) {
				try {
					risiko.moveUnits(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number);
				} catch (LandExistiertNichtException | ZuWenigEinheitenException
						| ZuWenigEinheitenNichtMoeglichExeption e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// Fehlermeldung, dass zu viele Einheiten verschoben werden sollen
			}

		}
	}

	@Override // worldlistener
	public void countryClicked(Land land) {

		switch (risiko.getCurrentState()) {
		case SETUNITS:
			System.out.println("Einheiten vorher: " + land.getEinheiten());
			try {
				land.setEinheiten(1);
			} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Einheiten nachher: " + land.getEinheiten());
			setUnitsPl.decrementUnits();
			System.out.println("Einheiten panel: " + setUnitsPl.getVerfuegbareEinheiten());
			if (setUnitsPl.getVerfuegbareEinheiten() > 0) {
				setUnitsPl.update();
			} else {
				System.out.println("FrageStatus vorher: " + risiko.getCurrentState());
				risiko.setNextState();
				showQuestion();
				System.out.println("FrageStatus nachher: " + risiko.getCurrentState());
			}
			break;
		case ATTACK:
			// wenn attackState 2 ist, wurde nur das erste Land eingeloggt, das zweite wird
			// erwartet
			if (worldPl.getAttackState() == 2) {
				cl.show(container, "attackTo");
			} else {
				// wenn attackState nicht 2 ist, wird im Dialog-Panel abgefragt, wie viele
				// Einheiten angreifen sollen
				cl.show(container, "attackNumber");
			}
			showQuestion();
			break;
		case CHANGEUNITS:
			// abfrage nach dem stand der Phase
			if (worldPl.getMoveState() == 1) {
				// überprüfung, ob das Land auch wirklich dem Besitzer gehört
				if (risiko.gibAktivenPlayer().equals(land.getBesitzer())) {
					// wen ja, wird das RequesPanel mit der abfrage nach dem Zielland gezeigt
					cl.show(container, "moveTo");
				} else {
					// TODO: Fehlermeldung
				}
			} else if (worldPl.getMoveState() == 2) {
				if (risiko.gibAktivenPlayer().equals(land.getBesitzer())
						&& risiko.isBenachbart(land, worldPl.getMoveLand1())) {
					// wenn beide Länder korrekt eingeloggt (also richtiger besitzer und benachbart)
					// sind, wird abgefragt, wie viele Einheiten verschoben werden sollen
					cl.show(container, "moveNumber");
				} else {
					// TODO: Fehlermeldung
				}
			}

			System.out.println("movestate: " + worldPl.getMoveState());

			// checkt zuerst, ob das Land dem spieler gehört
			if (risiko.getEigeneLaender(risiko.gibAktivenPlayer()).contains(land)) {

			} else {
				// dialogfenster mit fehlermeldung
			}
		}
	}

	//// TestSpielstart ohne login \\\\
	public void testSetUp() {
		risiko.playerAnlegen("Annie", "rot", 1);
		risiko.playerAnlegen("Tobi", "gruen", 2);
		risiko.playerAnlegen("Hannes", "blau", 3);
		risiko.verteileEinheiten();
		risiko.verteileMissionen();
		risiko.setzeAktivenPlayer();
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
		showPanel(wieVielePl);
	}

	public void showLoginPanel() {
//		loginPl.setPreferredSize(new Dimension(50, 50));
		this.pack();
		showPanel(loginPl);
	}

	public void showNeuerSpielerPanel() {
		showPanel(neuerSpielerPl);
	}

	public void showGamePanel() {
		initializeGamePl();
		System.out.println("aktiver Player: " + risiko.gibAktivenPlayer());
		System.out.println("aktive player länder: " + risiko.getEigeneLaender(risiko.gibAktivenPlayer()));
		showDialog();
		System.out.println("länder von spieler 1" + risiko.getPlayerArray().get(0).getBesitz());

		System.out.println("");
		worldPl = new WorldPanel(this, risiko);
		gamePl.add(worldPl, BorderLayout.CENTER);
		showPanel(gamePl);
	}

//TODO: uberflüssig?
	public int getSpielerAnzahl() {
		return wieVielePl.getAnzahlSpieler();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				RisikoClientGUI gui = new RisikoClientGUI();
			}
		});
	}

}

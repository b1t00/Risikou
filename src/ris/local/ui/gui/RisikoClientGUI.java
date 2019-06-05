package ris.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import ris.local.domain.Risiko;
import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.ZuWenigEinheitenException;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;
import ris.local.ui.gui.swing.panels.DialogPanel;
import ris.local.ui.gui.swing.panels.DicePanel;
import ris.local.ui.gui.swing.panels.InfoPanel;
import ris.local.ui.gui.swing.panels.KartenButton.kartenAuswahlListener;
import ris.local.ui.gui.swing.panels.LoginPanel;
import ris.local.ui.gui.swing.panels.NeuerSpielerPanel;
import ris.local.ui.gui.swing.panels.QuestionPanel;
import ris.local.ui.gui.swing.panels.QuestionPanel.QuestionListener;
import ris.local.ui.gui.swing.panels.RequestPanel;
import ris.local.ui.gui.swing.panels.RequestPanel.CountryRequest;
import ris.local.ui.gui.swing.panels.RisikokartenPanel;
import ris.local.ui.gui.swing.panels.RisikokartenPanel.RisikoKartenListener;
import ris.local.ui.gui.swing.panels.SetUnitsPanel;
import ris.local.ui.gui.swing.panels.UnitNumberPanel;
import ris.local.ui.gui.swing.panels.UnitNumberPanel.UnitNumber;
import ris.local.ui.gui.swing.panels.UnitNumberPanel.UnitNumberListener;
import ris.local.ui.gui.swing.panels.WieVieleSpielerPanel;
import ris.local.ui.gui.swing.panels.WorldPanel;
import ris.local.ui.gui.swing.panels.WorldPanel.WorldListener;
import ris.local.valueobjects.Attack;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Risikokarte;
//	MapImage Größe 120 / 711

public class RisikoClientGUI extends JFrame
		implements QuestionListener, WorldListener, UnitNumberListener, kartenAuswahlListener,  RisikoKartenListener{

	private Risiko risiko;

	// LOGIN //
	private LoginPanel loginPl;
	private WieVieleSpielerPanel wieVielePl;
	private NeuerSpielerPanel neuerSpielerPl;
	private RisikokartenPanel risikoKartenTPl;

	//Elemente vom Layout des GUI-Frames
	private JPanel container;
	private CardLayout cl = new CardLayout();
	private WorldPanel worldPl;
	private InfoPanel infoPl;
	private DialogPanel dialogPl;


	private DicePanel dicePl;

	// Das RequestPanel wird benötigt, wenn auf ein Land geklickt werden muss
	private RequestPanel attackFromPl;
	private RequestPanel attackToPl;
	private RequestPanel moveFromPl;
	private RequestPanel moveToPl;
	private RequestPanel cardRequestPl;

	private SetUnitsPanel setUnitsPl;

	private QuestionPanel changeCardsPl;
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
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(2, 1));
		container = new JPanel();
		dialogPl = new DialogPanel(risiko);
		westPanel.add(container);
		westPanel.add(dialogPl);

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

		gamePl.add(westPanel, BorderLayout.WEST);
		gamePl.add(infoPl, BorderLayout.SOUTH);

//		infoPl.add(risikoKartenTPl);
//		
		this.add(gamePl);

		// ab hier werden die einzelnen Panels erstellt und mit einem key versehen
		attackFromPl = new RequestPanel(CountryRequest.ATTACKCOUNTRY, risiko);
		container.add(attackFromPl, "attackFrom");

		attackToPl = new RequestPanel(CountryRequest.DEFENSECOUNTRY, risiko);
		container.add(attackToPl, "attackTo");

		moveFromPl = new RequestPanel(CountryRequest.MOVEFROMCOUNTRY, risiko);
		container.add(moveFromPl, "moveFrom");

		moveToPl = new RequestPanel(CountryRequest.MOVETOCOUNTRY, risiko);
		container.add(moveToPl, "moveTo");
		
		cardRequestPl = new RequestPanel(CountryRequest.CARDREQUEST, risiko);
		container.add(cardRequestPl, "cardRequest");

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
		// CENTER
		worldPl = new WorldPanel(this, risiko);
		gamePl.add(worldPl, BorderLayout.CENTER);
		this.add(gamePl);
	}

	// je nach spielphase wird ein anderes panel im container-panel angezeigt
	public void showQuestion() {
		switch (risiko.getCurrentState()) {
		case SETUNITS:
			if(risiko.gibAktivenPlayer().changePossible()) {
				changeCardsPl = new QuestionPanel(this, risiko);
				container.add(changeCardsPl, "changeCards");
				cl.show(container, "changeCards");
				
			} else {
				showSetUnits();
			}
			break;
		case ATTACK:
			if(risiko.kannAngreifen(risiko.gibAktivenPlayer())) {
				attackQuestionPl = new QuestionPanel(this, risiko);
				container.add(attackQuestionPl, "attackQuestion");
				cl.show(container, "attackQuestion");
			} else {
				JOptionPane.showMessageDialog(null, "Du kannst leider niemanden angreifen.");
				risiko.setNextState();
				showQuestion();
			}
			break;
		case CHANGEUNITS:
			if(risiko.kannVerschieben(risiko.gibAktivenPlayer())) {
				moveUnitsQuestionPl = new QuestionPanel(this, risiko);
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
		int units = risiko.errechneVerfuegbareEinheiten(risiko.gibAktivenPlayer());
		System.out.println("Verfügbare Einheiten: " + units);
		setUnitsPl = new SetUnitsPanel(units, risiko);
		container.add(setUnitsPl, "setUnits");
		cl.show(container, "setUnits");
	}
	
	public void showSetUnits(int plus) {
		dialogPl.update("setUnits");
		int units = risiko.errechneVerfuegbareEinheiten(risiko.gibAktivenPlayer()) + plus;
		System.out.println("Verfügbare Einheiten: " + units);
		setUnitsPl = new SetUnitsPanel(units, risiko);
		container.add(setUnitsPl, "setUnits");
		cl.show(container, "setUnits");
	}

	@Override
	// antwortListener vom Question Panel
	public void answerSelected(boolean answer) {
		if (answer) {
			//wenn mit ja geantwortet wird:
			switch (risiko.getCurrentState()) {
			case SETUNITS:
				cl.show(container, "cardRequest");
				risiko.setTauschZeit(true);
				break;
			case ATTACK:
				dialogPl.update("attack");
				cl.show(container, "attackFrom");
				System.out.println("Der Angriff beginnt");
				break;
			case CHANGEUNITS:
				dialogPl.update("moveUnits");
				cl.show(container, "moveFrom");
				break;
			// wenn mit nein geantwortet wird:
			} 
		} else {
			switch (risiko.getCurrentState()) {
			case SETUNITS:
				showSetUnits();
				break;
			case ATTACK:
				risiko.setNextState();
				showQuestion();
				break;
			case CHANGEUNITS:
				// dialog-fenster: dein zug ist beendet
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

	@Override
	// unitNumberListener, die UnitNumber gibt an, in welcher Spielphase wir unsbefinden (attack, defense, move)
	public void numberLogged(int number, UnitNumber un) throws ZuWenigEinheitenNichtMoeglichExeption {
		System.out.println("Status un: " + un);
		switch (un) {
		case ATTACK:

			if((number > (worldPl.getAttackLand1().getEinheiten()-1)) || number > 3 || number < 1) {
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
					dialogPl.update(attackObjekt);
					dicePl.setAttack(attackObjekt);
					dicePl.showResult();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//hier eher dice panel aufrufen
				if(attackObjekt.getWinner().equals(risiko.gibAktivenPlayer())) {
					JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat gewonnen und nimmt " + worldPl.getAttackLand2() + " ein.");
				} else {
					JOptionPane.showMessageDialog(null, risiko.gibAktivenPlayer() + " hat den Kampf verloren!");
				}
				updateWorld();
				showQuestion();
			} else {
				JOptionPane.showMessageDialog(null, "Ungültige Anzahl an Einheiten!");
			}
			break;
		case MOVE:
			if(risiko.moveUnitsGueltig(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number)) {
				try {
					risiko.moveUnits(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number);
					updateWorld();
					dialogPl.update(worldPl.getAttackLand1(), worldPl.getAttackLand2(), number);
				} catch (LandExistiertNichtException | ZuWenigEinheitenException
						| ZuWenigEinheitenNichtMoeglichExeption e) {
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
			try {
				land.setEinheiten(1);
				updateWorld();
			} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setUnitsPl.decrementUnits();
			if(setUnitsPl.getVerfuegbareEinheiten() > 0) {
				setUnitsPl.update();
			} else {
				risiko.setNextState();
				showQuestion();
			}
			break;
		case ATTACK:
			// wenn attackState 2 ist, wurde nur das erste Land eingeloggt, das zweite wird erwartet
			if (worldPl.getAttackState() == 2) {
				cl.show(container, "attackTo");
			} else {
				//wenn attackState nicht 2 ist, wird im Dialog-Panel abgefragt, wie viele Einheiten angreifen sollen
				cl.show(container, "attackNumber");
			}
			break;
		case CHANGEUNITS:
			// abfrage nach dem stand der Phase (2 > das 2.Land fehlt; 1 > das erste Land wird erwartet
			if (worldPl.getMoveState() == 2) {
					cl.show(container, "moveTo");
			} else if (worldPl.getMoveState() == 1) {
					cl.show(container, "moveNumber");
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
		worldPl = new WorldPanel(this, risiko);
		gamePl.add(worldPl, BorderLayout.CENTER);
		showPanel(gamePl);
		showQuestion();
	}
	
	public void combiAusgewaehlt() {
		ArrayList<Risikokarte> kicked = risikoKartenTPl.getCombi();
		for (Land l : risiko.gibAktivenPlayer().getBesitz()) {
			for (Risikokarte k : kicked) {
				//falls der spieler das Land von der Risikokarte beim eintauschen besitzt..
				if (l.getName().equals(k.getLand().getName())) { 
					// .. wird auf das land zwei einheiten gesetzt
						try {
							risiko.getLandById(l.getNummer()).setEinheiten(2);
							updateWorld();
						} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
				}
			}
		}
		showSetUnits(5);
		infoPl.update();
	}

//TODO: uberflüssig?
	public int getSpielerAnzahl() {
		return wieVielePl.getAnzahlSpieler();
	}
	
	public void updateWorld(){
		worldPl.removeAll();
		worldPl.revalidate();
		worldPl.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				RisikoClientGUI gui = new RisikoClientGUI();
			}
		});
	}

	@Override
	public boolean pruefenObDreiRichtig() {
		// TODO Auto-generated method stub
		return false;
	}


}

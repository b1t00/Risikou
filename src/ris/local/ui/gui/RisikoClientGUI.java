package ris.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ris.local.domain.Risiko;
import ris.local.exception.LandExistiertNichtException;
import ris.local.exception.ZuWenigEinheitenException;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;
import ris.local.ui.gui.swing.panels.DicePanel;
import ris.local.ui.gui.swing.panels.InfoPanel;
import ris.local.ui.gui.swing.panels.LoginPanel;
import ris.local.ui.gui.swing.panels.QuestionPanel;
//import ris.local.ui.gui.swing.panels.QuestionPanel.Question;
import ris.local.ui.gui.swing.panels.QuestionPanel.QuestionListener;
import ris.local.ui.gui.swing.panels.RequestPanel;
import ris.local.ui.gui.swing.panels.RequestPanel.CountryRequest;
import ris.local.ui.gui.swing.panels.RequestPanel.RequestListener;
import ris.local.ui.gui.swing.panels.UnitNumberPanel;
import ris.local.ui.gui.swing.panels.UnitNumberPanel.UnitNumber;
import ris.local.ui.gui.swing.panels.UnitNumberPanel.UnitNumberListener;
import ris.local.ui.gui.swing.panels.WorldPanel;
import ris.local.ui.gui.swing.panels.WorldPanel.WorldListener;
import ris.local.valueobjects.Attack;
import ris.local.valueobjects.Land;

public class RisikoClientGUI extends JFrame implements QuestionListener, WorldListener, RequestListener, UnitNumberListener {
 
	private Risiko risiko;

	private JPanel container;
	private CardLayout cl = new CardLayout();
	private WorldPanel worldPl;
	private InfoPanel infoPl;
	private LoginPanel loginPl;
//	private DialogPanel dialogPl;
//	private SetUnitsPanel setUnitsPl;
	
	private DicePanel dicePl;
	
	//Das RequestPanel wird benötigt, wenn auf ein Land geklickt werden muss
	private RequestPanel attackFromPl;
	private RequestPanel attackToPl;
	private RequestPanel moveFromPl;
	private RequestPanel moveToPl;
	
	private QuestionPanel attackQuestionPl;
	private QuestionPanel moveUnitsQuestionPl;
	
	private UnitNumberPanel moveNumberPl;
	private UnitNumberPanel attackNumberPl;	
	private UnitNumberPanel defenseNumberPl;
	
	public RisikoClientGUI() {
		risiko = new Risiko();
		initialize();
		showQuestion();
	}

	private void initialize() {
		//ermoeglicht das schliessen des fensters
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Layot der Frames
		this.setLayout(new BorderLayout());

		
	

		// LOGIN
		loginPl = new LoginPanel();

		//WEST
		container = new JPanel();
		//Layout = CardLayout
		container.setLayout(cl);
		container.setSize(100,400);
		container.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//CENTER
		worldPl = new WorldPanel(this, risiko);
		
		//SOUTH
		infoPl = new InfoPanel();	

//		dicePl = new DicePanel(risiko,this);

		//evtl hier ein Problem, da in demcContainer noch nix ist

		this.add(container, BorderLayout.WEST);
		this.add(worldPl, BorderLayout.CENTER);

		this.add(loginPl);
		this.add(container, BorderLayout.NORTH);
		this.add(worldPl, BorderLayout.CENTER);

		this.add(infoPl, BorderLayout.SOUTH);
		
		this.setSize(600, 600);
		this.setVisible(true);
		this.setTitle("Risiko");
		
		//ab hier werden die einzelnen Panels erstellt und mit einem key versehen
		attackFromPl = new RequestPanel(this, CountryRequest.ATTACKCOUNTRY);
		container.add(attackFromPl, "attackFrom");
		
		attackToPl = new RequestPanel(this, CountryRequest.DEFENSECOUNTRY);
		container.add(attackToPl, "attackTo");
		
		moveFromPl = new RequestPanel(this, CountryRequest.MOVEFROMCOUNTRY);
		container.add(moveFromPl, "moveFrom");
		
		moveToPl = new RequestPanel(this, CountryRequest.MOVETOCOUNTRY);
		container.add(moveToPl, "moveTo");
		
		moveNumberPl = new UnitNumberPanel(this, UnitNumber.MOVE);
		container.add(moveNumberPl, "moveNumber");
		
		attackNumberPl = new UnitNumberPanel(this, UnitNumber.ATTACK);
		container.add(attackNumberPl, "attackNumber");

		defenseNumberPl = new UnitNumberPanel(this, UnitNumber.DEFENSE);
		container.add(defenseNumberPl, "defenseNumber");
		
		moveUnitsQuestionPl = new QuestionPanel(this, risiko);
		container.add(moveUnitsQuestionPl, "moveUnitsQuestion");
		
		attackQuestionPl = new QuestionPanel(this, risiko);
		container.add(attackQuestionPl, "attackQuestion");
		
		dicePl = new DicePanel();
		container.add(dicePl, "dice");
		
	}
	
	//je nach spielphase wird ein anderes panel im container-panel angezeigt
	public void showQuestion() {	
		//if player = risiko.gibAktiverPlayer()
		switch(risiko.getCurrentState()) {
		case ATTACK:
			cl.show(container, "attackQuestion");
			break;
		case CHANGEUNITS:
			cl.show(container, "moveUnitsQuestion");
			break;
		default:
			//TODO
			break;			
		}
	}

	@Override
	//antwortListener vom Question Panel
	public void answerSelected(boolean answer) {
			
		//eventuell noch ein zweiter parameter, um welche frage es sich handelt
		//wenn mit ja geantwortet wird
		if (answer) {
			switch(risiko.getCurrentState()) {
			case ATTACK:
				//ersetzt das fragePanel durch das attackPanel
				cl.show(container, "attackFrom");
				System.out.println("Der Angriff beginnt");
				break;
			case CHANGEUNITS:
				//ersetzt das fragePanel durch das Verschiebe-EinheitenPanel
				cl.show(container, "moveNumber");
				System.out.println("Frage nach move number");
				break;
			default:
				//TODO
				break;
			}
			//wenn mit nein geantwortet wird:
		} else {
			risiko.setNextState();
			switch(risiko.getCurrentState()) {
			case ATTACK:
				cl.show(container, "moveUnitsQuestion");
				break;
			case CHANGEUNITS:
				//dialog-fenster: dein zug ist beendet
				risiko.setNaechsterPlayer();
				//zeigt den neuen dialog
				showQuestion();
				System.out.println("Nächste Spielphase");
				break;
			default:
				//TODO
				break;
			}
		}
	}
	
	@Override
	//unitNumberListener, die UnitNumber gibt an, in welcher Spielphase wir uns befinden, eventuell unnötig, wenn Turn gefragt werden kann?
	public void numberLogged(int number, UnitNumber un) throws ZuWenigEinheitenNichtMoeglichExeption {

			switch(un) {
			case ATTACK:
				//test, ob zahl gültig ist
				cl.show(container, "defenseNumber");
				break;
			case DEFENSE:
				try {
					Attack attackObjekt = risiko.attack(worldPl.getAttackLand1(), worldPl.getAttackLand2(), attackNumberPl.getNumber(), defenseNumberPl.getNumber());
					dicePl.setAttack(attackObjekt);
					cl.show(container, "dice");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ausgabe von der attacke und wie geht es weiter?
				break;
			
			case MOVE:
				if(risiko.genugEinheiten(worldPl.getMoveLand1(), number)) {
					try {
						risiko.moveUnits(worldPl.getMoveLand1(), worldPl.getMoveLand2(), number);
					} catch (LandExistiertNichtException | ZuWenigEinheitenException | ZuWenigEinheitenNichtMoeglichExeption e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				} else {
					//Fehlermeldung, dass zu viele Einheiten verschoben werden sollen
				}
				
			}
		
		//ruft methoden auf, die die eingaben verarbeiten
		System.out.println("Es wird mit " + number + " Einheiten angegriffen.");
	}
	
	@Override //worldlistener
	public void countryClicked(Land land) {		
		
		switch(risiko.getCurrentState()) {
		case SETUNITS:
		case ATTACK:
			if(worldPl.getAttackState() == 1) {
				if(land.getBesitzer().equals(risiko.gibAktivenPlayer())) {
					// und hier auch noch Abfrage, ob Land angreifen kann -> am besten in Risiko Methode, die das überprüft
					cl.show(container, "attackTo");
				} else {
					//Fehlermeldung mit Schleife
				}
			} else {
				if(!land.getBesitzer().equals(risiko.gibAktivenPlayer()) && risiko.isBenachbart(land, worldPl.getAttackLand1())) {
					// eventuell die Bedingung in risiko auslagern
					cl.show(container, "attackNumber");
				} else {
					//Fehlermeldung mit Schleife
				}
			}
			//neues Attack-Objekt
			//Attack-Objekt hat Angreifer, Verteidiger, Land1 und Land2 und zwei Werte für Einheiten
			//hat eine Methode mit Angriff auswerten
		case CHANGEUNITS:
			//abfrage nach dem stand der Phase
			if(worldPl.getMoveState() == 1) {
				//überprüfung, ob das Land auch wirklich dem Besitzer gehört
				if(risiko.gibAktivenPlayer().equals(land.getBesitzer())) {
					//wen ja, wird das RequesPanel mit der abfrage nach dem Zielland gezeigt
					cl.show(container, "moveTo");
				} else {
					//TODO: Fehlermeldung
				}
			} else if(worldPl.getMoveState() == 2) {
				if(risiko.gibAktivenPlayer().equals(land.getBesitzer()) && 
					risiko.isBenachbart(land, worldPl.getMoveLand1())) {
					//wenn beide Länder korrekt eingeloggt (also richtiger besitzer und benachbart) sind, wird abgefragt, wie viele Einheiten verschoben werden sollen
					cl.show(container, "moveNumber");
				} else {
					//TODO: Fehlermeldung
				}
			}
			
			//checkt zuerst, ob das Land dem spieler gehört
			if(risiko.getEigeneLaender(risiko.gibAktivenPlayer()).contains(land)) {
				
			} else {
				//dialogfenster mit fehlermeldung
			}
			}
	}
	
		public static void main (String[] args) {
			RisikoClientGUI gui = new RisikoClientGUI();
			gui.risiko.PlayerAnlegen("Annie", "rot", 1);
			gui.risiko.PlayerAnlegen("Tobi", "gruen", 2);
			gui.risiko.verteileEinheiten();
			gui.risiko.verteileMissionen();
			gui.risiko.setzeAktivenPlayer();
		}
}

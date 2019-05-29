package ris.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ris.local.domain.Risiko;
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
import ris.local.valueobjects.Land;

public class RisikoClientGUI extends JFrame implements QuestionListener, WorldListener, RequestListener, UnitNumberListener {
 
	private Risiko risiko;

	private JPanel container;
	private CardLayout cl = new CardLayout();
//	private DicePanel dicePl;
	private WorldPanel worldPl;
	private InfoPanel infoPl;
	private LoginPanel loginPl;
//	private DialogPanel dialogPl;
//	private SetUnitsPanel setUnitsPl;
//	private RequestPanel changeUnitsFirstPl;
//	private RequestPanel changeUnitsSecondPl;
//	private DefensePanel defensePl;
//  private RequestPanel attackSecondPl;
	
	//Das RequestPanel wird benötigt, wenn auf ein Land geklickt werden muss
	private RequestPanel attackFromPl;
	private RequestPanel attackToPl;
	private RequestPanel moveFromPl;
	private RequestPanel moveToPl;
	
	private QuestionPanel attackQuestionPl;
	private QuestionPanel moveUnitsQuestionPl;
	
	private UnitNumberPanel moveNumberPl;
	private UnitNumberPanel attackUnitPl;	//attackUnitPl = new UnitNumberPanel(this, UnitNumber.ATTACK);
	
	public RisikoClientGUI() {
		risiko = new Risiko();
		initialize();
		showDialog();
	}

	private void initialize() {
		//ermoeglicht das schliessen des fensters
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Layot der Frames
		this.setLayout(new BorderLayout());
		// LOGIN
		loginPl = new LoginPanel();
		//NORTH
		container = new JPanel();
		//Layout = CardLayout
		container.setLayout(cl);
		
		//EAST
		worldPl = new WorldPanel(this);
		
		//SOUTH
		infoPl = new InfoPanel();	

//		dicePl = new DicePanel(risiko,this);

		//evtl hier ein Problem, da in demcContainer noch nix ist
		this.add(loginPl);
		this.add(container, BorderLayout.NORTH);
		this.add(worldPl, BorderLayout.EAST);
		this.add(infoPl, BorderLayout.SOUTH);
		
		this.setSize(480, 480);
		this.setVisible(true);
		
		//ab hier werden die einzelnen Panels erstellt und mit einem key versehen
		attackFromPl = new RequestPanel(this, CountryRequest.ATTACKCOUNTRY);
		container.add(attackFromPl, "attackFrom");
		
		moveFromPl = new RequestPanel(this, CountryRequest.MOVEFROMCOUNTRY);
		container.add(moveFromPl, "moveFrom");
		
		moveToPl = new RequestPanel(this, CountryRequest.MOVETOCOUNTRY);
		container.add(moveToPl, "moveTo");
		
		moveNumberPl = new UnitNumberPanel(this, UnitNumber.MOVE);
		container.add(moveNumberPl, "moveNumber");
		
		moveUnitsQuestionPl = new QuestionPanel(this, risiko);
		container.add(moveUnitsQuestionPl, "moveUnitsQuestion");
		
		attackQuestionPl = new QuestionPanel(this, risiko);
		container.add(attackQuestionPl, "attackQuestion");
		
	}
	
	//je nach spielphase wird ein anderes panel im container-panel angezeigt
	public void showDialog() {	
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
			switch(risiko.getCurrentState()) {
			case ATTACK:
				risiko.setNextState();
				cl.show(container, "moveUnitsQuestion");
				break;
			case CHANGEUNITS:
				//dialog-fenster: dein zug ist beendet
				risiko.setNaechsterPlayer();
				risiko.setNextState();
				//zeigt den neuen dialog
				showDialog();
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
	public void numberLogged(int number, UnitNumber un) {
		//ruft methoden auf, die die eingaben verarbeiten
		System.out.println("Es wird mit " + number + " Einheiten angegriffen.");
	}
	
	//worldlistener
	public void countryClicked(Land land) {
		switch(risiko.getCurrentState()) {
		case ATTACK:
			if(risiko.getAngriffsLaender(risiko.gibAktivenPlayer()).contains(land)) {
				//mache weiter
			} else {
				//dialogausgabe: angriff nicht möglich
			}
			//neues Attack-Objekt
			//Attack-Objekt hat Angreifer, Verteidiger, Land1 und Land2 und zwei Werte für Einheiten
			//hat eine Methode mit Angriff auswerten
		case CHANGEUNITS:
			//checkt zuerst, ob das Land dem spieler gehört
			if(risiko.getEigeneLaender(risiko.gibAktivenPlayer()).contains(land)) {
				
			} else {
				//dialogfenster mit fehlermeldung
			}
			}
	}
	
	public static void main (String[] args) {
		RisikoClientGUI gui = new RisikoClientGUI();
	}
}

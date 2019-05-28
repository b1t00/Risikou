package ris.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ris.local.domain.Risiko;
import ris.local.ui.gui.swing.panels.InfoPanel;
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

public class RisikoClientGUI extends JFrame implements QuestionListener, WorldListener, RequestListener, UnitNumberListener {
 
	private Risiko risiko;

	private JPanel container;
	private CardLayout cl = new CardLayout();
//	private DicePanel dicePl;
	private WorldPanel worldPl;
	private InfoPanel infoPl;
//	private DialogPanel dialogPl;
//	private SetUnitsPanel setUnitsPl;
//	private RequestPanel changeUnitsFirstPl;
//	private RequestPanel changeUnitsSecondPl;
//	private DefensePanel defensePl;
//  private RequestPanel attackSecondPl;
	private RequestPanel attackFirstPl;
	private QuestionPanel attackQuestion;
	private UnitNumberPanel attackUnitPl;
	
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
		
		//NORTH
		container = new JPanel();
		//Layout = CardLayout
		container.setLayout(cl);
		
		//EAST
		worldPl = new WorldPanel(this);
		
		//SOUTH
		infoPl = new InfoPanel();	

//		dialogPl = new DialogPanel(risiko, this);
//		dicePl = new DicePanel(risiko,this);

		//evtl hier ein Problem, da in demcContainer noch nix ist
		this.add(container, BorderLayout.NORTH);
		this.add(worldPl, BorderLayout.EAST);
		this.add(infoPl, BorderLayout.SOUTH);
		
		this.setSize(480, 480);
		this.setVisible(true);
	}
	
	//je nach spielphase wird ein anderes panel im container-panel angezeigt
	public void showDialog() {	
		switch(risiko.getCurrentState()) {
		case ATTACK:
			QuestionPanel attackPl = new QuestionPanel(this, risiko);
			//attack methode checken: was muss hier hin? auswertung 
			break;
		case CHANGEUNITS:
			QuestionPanel moveUnitsPl = new QuestionPanel(this, risiko);			
		}
		attackQuestion = new QuestionPanel(this, risiko);
		attackFirstPl = new RequestPanel(this, CountryRequest.ATTACKCOUNTRY);
		attackUnitPl = new UnitNumberPanel(this, UnitNumber.ATTACK);
		
		
		container.add(attackQuestion, "1");
		container.add(attackUnitPl, "2");
//		container.add(attackFirstPl, "2");
		cl.show(container, "1");
	}

	@Override
	//antwortListener vom Question Panel
	public void answerSelected(boolean answer) {
		//funktioniert nicht!
		if(risiko.getCurrentState() == ATTACK)
			
			
		//eventuell noch ein zweiter parameter, um welche frage es sich handelt
		if (answer) {
			switch(risiko.getCurrentState()) {
			case ATTACK:
				//ersetze das fragePanel durch das attackPanel
				cl.show(container, "2");
				System.out.println("Der Angriff beginnt");
				break;
			case CHANGEUNITS:
				UnitNumberPanel moveFromPl = new UnitNumberPanel(this, UnitNumber.MOVE);
				break;
			default:
				break;

			}
		} else {
			QuestionPanel changeQuestion = new QuestionPanel(this, risiko);
			container.add(changeQuestion, "3");
			cl.show(container, "3");
			System.out.println("Nächste Phase");
			//ersetze das fragePanel durch das nächstePanel
		}
	}
	
	@Override
	//unitNumberListener, die UnitNumber gibt an, in welcher Spielphase wir uns befinden, eventuell unnötig, wenn Turn gefragt werden kann?
	public void numberLogged(int number, UnitNumber un) {
		//ruft methoden auf, die die eingaben verarbeiten
		System.out.println("Es wird mit " + number + " Einheiten angegriffen.");
	}
	
	public static void main (String[] args) {
		RisikoClientGUI gui = new RisikoClientGUI();
	}



}

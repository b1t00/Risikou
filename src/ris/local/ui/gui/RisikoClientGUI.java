package ris.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ris.local.ui.gui.swing.panels.InfoPanel;
import ris.local.ui.gui.swing.panels.QuestionPanel;
import ris.local.ui.gui.swing.panels.QuestionPanel.Question;
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
 
	//private Risiko risiko;

	private JPanel container = new JPanel();
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
	private UnitNumberPanel defenseUnitPl;
	
	public RisikoClientGUI() {
		initialize();
	}

	private void initialize() {
		//ermoeglicht das schliessen des fensters
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//Layot der Frames
		this.setLayout(new BorderLayout());
		
		//NORTH
		attackQuestion = new QuestionPanel(this, Question.ATTACK);
		
		//EAST
		worldPl = new WorldPanel(this);
		
		//NORTH
		attackFirstPl = new RequestPanel(this, CountryRequest.ATTACKCOUNTRY);
		attackUnitPl = new UnitNumberPanel(this, UnitNumber.ATTACK);
		defenseUnitPl = new UnitNumberPanel(this, UnitNumber.DEFENSE);
		
		//SOUTH
		infoPl = new InfoPanel();
		
		container.setLayout(cl);
		container.add(attackQuestion, "1");
		container.add(attackUnitPl, "2");
		container.add(defenseUnitPl, "3");
		
//		container.add(attackFirstPl, "2");
		cl.show(container, "1");

//		dialogPl = new DialogPanel(risiko, this);
//		dicePl = new DicePanel(risiko,this);
		
	//	this.add(questionPl, BorderLayout.NORTH);
	//	this.add(attackPl, BorderLayout.SOUTH);
		this.add(container, BorderLayout.NORTH);
		this.add(worldPl, BorderLayout.EAST);
		this.add(infoPl, BorderLayout.SOUTH);
		
		this.setSize(480, 480);
		this.setVisible(true);
	}

	@Override
	//antwortListener vom Question Panel
	public void answerSelected(boolean answer) {
		//eventuell noch ein zweiter parameter, um welche frage es sich handelt
		if (answer) {
			cl.show(container, "2");
			System.out.println("Der Angriff beginnt");
			//ersetze das fragePanel durch das attackPanel
		} else {
			QuestionPanel changeQuestion = new QuestionPanel(this, Question.CHANGEUNITS);
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
		System.out.println("status: " + attackUnitPl.getUnitNumber());
		cl.show(container, "3");
	}
	
	public static void main (String[] args) {
		RisikoClientGUI gui = new RisikoClientGUI();
	}



}

package ris.local.ui.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import ris.local.domain.Risiko;
import ris.local.ui.gui.swing.panels.DialogPanel;
import ris.local.ui.gui.swing.panels.DialogPanel.DialogListener;
import ris.local.ui.gui.swing.panels.DicePanel;
import ris.local.ui.gui.swing.panels.InfoPanel;
import ris.local.ui.gui.swing.panels.WorldPanel;

public class RisikoClientGUI extends JFrame implements DialogListener {

	private Risiko risiko;

	private DicePanel dicePl;
	private WorldPanel worldPl;
	private InfoPanel infoPl;
	private DialogPanel dialogPl;

	private void initialize() {

		this.setLayout(new BorderLayout());

		dialogPl = new DialogPanel(risiko, this);
		
		worldPl = new WorldPanel(risiko, this);
	}

}

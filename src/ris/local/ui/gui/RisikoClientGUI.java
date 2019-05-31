package ris.local.ui.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ris.local.domain.Risiko;
import ris.local.ui.gui.swing.panels.GamePanel;
import ris.local.ui.gui.swing.panels.LoginPanel;
import ris.local.ui.gui.swing.panels.NeuerSpielerPanel;
import ris.local.ui.gui.swing.panels.WieVieleSpielerPanel;

public class RisikoClientGUI extends JFrame {

	// private Risiko risiko;

	private WieVieleSpielerPanel wieVielePl;
	private LoginPanel loginPl;
	private NeuerSpielerPanel neuerSpielerPl;

	private GamePanel gamePl;

	private Risiko risiko;

	// private JPanel container = new JPanel();

//	private DicePanel dicePl;

	boolean visible = true;

	public RisikoClientGUI() {
//		loginPl.setVisible(false);
		super("Risikou");
		risiko = new Risiko();
		initialize();
	}

	private void initialize() {
		// ermoeglicht das schliessen des fensters
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Layot der Frames
		this.setLayout(new BorderLayout());

		// LOGIN
		loginPl = new LoginPanel(this);
		gamePl = new GamePanel(this);
		wieVielePl = new WieVieleSpielerPanel(this);
		neuerSpielerPl = new NeuerSpielerPanel(risiko, this);

		Container c = this.getContentPane();
		c.add(loginPl);

//		this.setSize(480, 480);

		this.setLocationRelativeTo(null);
		this.setVisible(visible);
	}

	//TEST
	public void setClientSize(int width, int height) {
		this.setSize(width, height);
	}

	public void showPanel(JPanel panel) {
		
		Container c = getContentPane();
		c.removeAll();
		c.add(panel);
		c.revalidate();
		c.repaint();
		
		panel.revalidate();
		panel.repaint();
		panel.requestFocus();
		
		
	}
	public void showNeuesSpielPanel() {
		showPanel(wieVielePl);
	}

	public void showLoginPanel() {
		this.setSize(getPreferredSize());
		showPanel(loginPl);
	}


	public void showNeuerSpielerPanel() {
		showPanel(neuerSpielerPl);
	}

	public void showGamePanel() {
		showPanel(gamePl);
	}

	public int getSpielerAnzahl() {
		return wieVielePl.getAnzahlSpieler();
	}

	public static void main(String[] args) {
		new RisikoClientGUI();

	}

}

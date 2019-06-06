package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import ris.local.ui.gui.swing.panels.RisikokartenPanel.RisikoKartenListener;
import ris.local.valueobjects.Risikokarte;

public class KartenButton extends JButton {
	private String titel;
	private Risikokarte risikoKarte;
	private boolean ausgewaehlt = false;
	private ImageIcon icon;

	public interface kartenAuswahlListener {
		public boolean pruefenObDreiRichtig();
	}

	public KartenButton(Risikokarte karte) {
		super("nichts");
		this.risikoKarte = karte;
		this.setPreferredSize(new Dimension(130, 140));
//		System.out.println("ging nicht" + ip.getHeight()); // mal gucken
		setUp();
		
	}

	public void setTitel(String neuerTitel) {
		this.setText(neuerTitel);
	}
	
	public void setUp() {
		if (risikoKarte == null) {
			this.setBackground(Color.GRAY);
			setBorder(BorderFactory.createLoweredBevelBorder());
		} else {
			this.setBackground(Color.GREEN);
			
			setBorder(BorderFactory.createRaisedSoftBevelBorder());
		}
		setVerticalTextPosition(SwingConstants.BOTTOM);
	}

	public Risikokarte getKarte() {
		return risikoKarte;
	}

	public void setAusgewaehlt(boolean istAusgewaehlt) {
		if (risikoKarte == null) {
			this.setBackground(Color.GRAY);
			setBorder(BorderFactory.createLoweredBevelBorder());
		} else {
			risikoKarte.setAusgewaehl(istAusgewaehlt);
			if (istAusgewaehlt) {
				this.setBackground(Color.CYAN);
			} else {
				this.setBackground(Color.GREEN);
			}
		}
//		return true;
	}

	public boolean getAusgewaehlt() {
		return risikoKarte.getAusgewaehl();
	}
}

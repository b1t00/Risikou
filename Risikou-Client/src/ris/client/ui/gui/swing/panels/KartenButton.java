package ris.client.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import ris.common.valueobjects.Risikokarte;	

public class KartenButton extends JButton {
	private String titel;
	private Risikokarte risikoKarte;
	private boolean ausgewaehlt = false;
	private ImageIcon icon;

	public interface kartenAuswahlListener {
		public boolean pruefenObDreiRichtig();
	}

	public KartenButton(Risikokarte karte) {
		super("noch keine Karte");
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
			this.setBackground(new Color(161, 141, 61));
						setBorder(BorderFactory.createRaisedSoftBevelBorder());
		}
		setVerticalTextPosition(SwingConstants.BOTTOM);
	}

	public Risikokarte getKarte() {
		return risikoKarte;
	}

	public void setAusgewaehlt(boolean istAusgewaehlt) {
		ausgewaehlt = istAusgewaehlt;
		if (risikoKarte == null) {
			this.setBackground(Color.GRAY);
			setBorder(BorderFactory.createLoweredBevelBorder());
		} else {
//			risikoKarte.setAusgewaehl(istAusgewaehlt);
			if (istAusgewaehlt) {
				this.setBackground(new Color(187, 170, 80));
			} else {
				this.setBackground(new Color(161, 141, 61));
			}
		}
	}

	public boolean getAusgewaehlt() {
		return ausgewaehlt;
//		return risikoKarte.getAusgewaehl();
	}
}

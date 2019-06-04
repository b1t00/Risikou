package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import ris.local.ui.gui.swing.panels.RisikokartenPanel.RisikoKartenListener;
import ris.local.valueobjects.Risikokarte;

public class KartenButton extends JButton {
	private String titel;
	private Risikokarte risikoKarte;
	private boolean ausgewaehlt = false;
	
	public interface kartenAuswahlListener {
		public boolean pruefenObDreiRichtig();
	}

	public KartenButton(Risikokarte karte) {
		super("nichts");
		this.risikoKarte = karte;
		this.setPreferredSize(new Dimension(130, 140));
//		System.out.println("ging nicht" + ip.getHeight()); // mal gucken
		this.setBackground(Color.GRAY);
		if (risikoKarte == null) {
			setBorder(BorderFactory.createLoweredBevelBorder());
		} else {
			setBorder(BorderFactory.createRaisedSoftBevelBorder());
		}
//		addActionListener();
		setVerticalTextPosition(SwingConstants.BOTTOM);
	}

	public void setTitel(String neuerTitel) {
		this.setText(neuerTitel);
	}
	
	public Risikokarte getKarte() {
		return risikoKarte;
	}

//	public void addActionListener() {
//		super.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(risikoKarte != null && !ausgewaehlt) {
//				System.out.println(risikoKarte.getSymbol());
//				ausgewaehlt = ausgewaehlt();
//				}
//			}
//		});
//	}
	public boolean setAusgewaehlt() {
		this.setBackground(Color.CYAN);
		risikoKarte.setAusgewaehl(true);
		return true;
	}
	public boolean getAusgewaehlt() {
		return risikoKarte.getAusgewaehl();
	}
}

package ris.local.ui.gui.swing.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.exception.ZuWenigEinheitenNichtMoeglichExeption;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Risikokarte;
import ris.local.valueobjects.Risikokarte.Symbol;

public class RisikokartenPanel extends JPanel {

	public interface RiskoKartenListener {
		public void combiAusgewaehlt();
	}

	private Risikokarte karte;
	private Risiko risiko;
	private InfoPanel ip;
	private RisikoKartenListener listener;

	private ArrayList<KartenButton> spielerKarten = new ArrayList<KartenButton>();

	public interface RisikoKartenListener {

	}

	public RisikokartenPanel(Risiko risk, RisikoKartenListener listener) {
		this.risiko = risk;
//		this.ip = ip;
		setUp();
//		System.out.println("muss man?" + manMussTauschen());

	}

	public RisikokartenPanel(Risiko risk, Risikokarte karte) {

	}

	public void setUp() {
		
		if(!(spielerKarten.size() == 0)) {
			spielerKarten.removeAll(spielerKarten);
			this.removeAll();
		}
		
		for (int i = 0; i < 5; i++) {
			if (i < risiko.gibAktivenPlayer().getEinheitenkarten().size()) {
				spielerKarten.add(new KartenButton(risiko.gibAktivenPlayer().getEinheitenkarten().get(i)));

				spielerKarten.get(i).setTitel("<html><center>"
						+ risiko.gibAktivenPlayer().getEinheitenkarten().get(i).getSymbol().toString() + "<br><br>"
						+ risiko.gibAktivenPlayer().getEinheitenkarten().get(i).getLand() + "</center></html>");
				spielerKarten.get(i).setBackground(Color.green); // TODO farbe von Spieler einbauen
			} else {
				spielerKarten.add(new KartenButton(null));
			}
		}

		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 50, 5); // Abstand zwischen Buttens
		setLayout(fl);
		for (KartenButton k : spielerKarten) {
			add(k);
			k.addActionListener(new KartenListener());
		}
		this.setBackground(Color.YELLOW);

	}

	// Wenn 5 Karten vorhanden sind! TODO: in runden implementieren
	public boolean manMussTauschen() {
		for (KartenButton k : spielerKarten) {
			if (k.getKarte() == null) {
				return false;
			}
		}
		return true;
	}

	// check ob spieler Drei karten
	public boolean dreiKartenAusgewaehlt() {
		ArrayList<Risikokarte> ausgeWahlteKarten = new ArrayList<Risikokarte>();
		for (Risikokarte k : risiko.gibAktivenPlayer().getEinheitenkarten()) {
			if (k.getAusgewaehl()) {
				ausgeWahlteKarten.add(k);
			}
			if ((ausgeWahlteKarten.size() > 2)) { // wenn drei Karten ausgewahlt wurden, wird diese Methode ausgef�hrt.
				if (risiko.gibAktivenPlayer().auswahlPruefen(ausgeWahlteKarten)) {
					System.out.println("einl�sen w�re schonmal richtig");
					loeseRisikoKartenEin(ausgeWahlteKarten); // wenn drei
					return true;
				} else {
					// es wurden nicht die richtigen Karten eingel�st
					System.out.println("es wurden nicht die richtigen Karten eingel�st");
					return false;
				}
			}
		}
		return false;

	}

	public void loeseRisikoKartenEin(ArrayList<Risikokarte> ausgeWahlteKarten) {
		for (Land l : risiko.gibAktivenPlayer().getBesitz()) {
			for (Risikokarte k : ausgeWahlteKarten) {
				if (l.getName().equals(k.getLand().getName())) { // falls der spieler das Land von der Risikokarte beim
																	// eintauschen besitzt..
					try {
						System.out.println("hier gucken" + risiko.gibAktivenPlayer().getBesitz());
						risiko.getLandById(l.getNummer()).setEinheiten(2); // .. wird auf das
																									// land zwei
																									// einheiten gesetzt
//						System.out.println("hier danach " + risiko.gibAktivenPlayer().getLandById(l.getNummer()));
						// TODO: karte aus dejm Array Loeschen
						// TODO: generell anzahl zu setztenden Einheiten erh�hen!
						risiko.gibAktivenPlayer();
					} catch (ZuWenigEinheitenNichtMoeglichExeption e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
				}
			}
		}
	}

	// muss in gleich klasse
	class KartenListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

//			risiko.gibAktivenPlayer().auswahlPruefen(Arr); // gucken ob das array geht..
//			listener.combiAusgewaehlt();
			if (!dreiKartenAusgewaehlt() && risiko.getTauschZeit()) {
				KartenButton b = (KartenButton) e.getSource();
				b.setAusgewaehlt();
//				System.out.println("wurde eine KArte ausgewaeht" + dreiKartenAusgewaehlt());
			}
		}

	}

}

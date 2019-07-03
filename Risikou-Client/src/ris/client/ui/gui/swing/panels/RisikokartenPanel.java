package ris.client.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ris.common.interfaces.RisikoInterface;
import ris.common.valueobjects.Risikokarte;

public class RisikokartenPanel extends JPanel {

	public interface RiskoKartenListener {
		public void combiAusgewaehlt();
	}

	private Risikokarte karte;
	private RisikoInterface risiko;
	private InfoPanel ip;
	private RisikoKartenListener listener;
	//ImageIcon image = new ImageIcon(getClass().getResource("../assets/img/button.png"));

	private ArrayList<KartenButton> spielerKartenBtn = new ArrayList<KartenButton>();

	public interface RisikoKartenListener {
		public void updateKartenpanel();

		public void updateKartenpanel2();

		public void combiAusgewaehlt(ArrayList<Risikokarte> auswahl);

//		public void updateKartenpanelZwo();
	}

	public RisikokartenPanel(RisikoInterface risk, RisikoKartenListener listener) {
		this.risiko = risk;
		this.listener = listener;
		setUp();

	}

	public void setUp() {
		if (spielerKartenBtn.size() != 0) {
			for (KartenButton kb : spielerKartenBtn)
				this.remove(kb);
			this.removeAll();
		}
		spielerKartenBtn.removeAll(spielerKartenBtn);

	for(int i = 0; i < 5; i++){ //TODO: kann man schoener machen, aber muss auch nicht
			if (i < risiko.gibAktivenPlayer().getEinheitenkarten().size()) {
				System.out.println(spielerKartenBtn);
	
				spielerKartenBtn.add(new KartenButton(risiko.gibAktivenPlayer().getEinheitenkarten().get(i)));
				spielerKartenBtn.get(i).setTitel("<html><center>"
						+ risiko.gibAktivenPlayer().getEinheitenkarten().get(i).getSymbol().toString() + "<br><br>"
						+ risiko.gibAktivenPlayer().getEinheitenkarten().get(i).getLand() + "</center></html>");
				//spielerKartenBtn.get(i).setBackground(Color.green); // TODO farbe von Spieler einbauen

			} else {
				spielerKartenBtn.add(new KartenButton(null));
			}
		}

		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 20, 5); // Abstand zwischen Buttens
		setLayout(fl);
		for (KartenButton k : spielerKartenBtn) {
			add(k);
			k.setVisible(true);
			k.addActionListener(new KartenListener());
		}
//		this.setBackground(new Color(154, 87, 0)); // hier kann man hintegrund setzten
	}

	// Wenn 5 Karten vorhanden sind! TODO: in runden implementieren
	public boolean manMussTauschen() {
		for (KartenButton k : spielerKartenBtn) {
			if (k.getKarte() == null) {
				return false;
			}
		}
		return true;
	}

//	public ArrayList<Risikokarte> getCombi(){
//		return ausgeWahlteKarten;
//	}
	// check ob spieler Drei karten
	public boolean dreiKartenAusgewaehlt() {
		ArrayList<Risikokarte> ausgeWahlteKarten = new ArrayList<Risikokarte>();
		System.out.println("hier : ak" + ausgeWahlteKarten);
		for (Risikokarte k : risiko.gibAktivenPlayer().getEinheitenkarten()) {
			if (k.getAusgewaehl()) {
				ausgeWahlteKarten.add(k);
				
//				for(Risikokarte rk : ausgeWahlteKarten) {
				for( int i = 0 ; i < ausgeWahlteKarten.size() ;i++) {
					System.out.println("hier : ak2" + ausgeWahlteKarten.get(i));
					
				}
//				k.setAusgewaehl(false);
			}
			if ((ausgeWahlteKarten.size() > 2)) { // wenn drei Karten ausgewahlt wurden, wird diese Methode ausgefï¿½hrt.
				if (risiko.gibAktivenPlayer().auswahlPruefen(ausgeWahlteKarten)) {
					System.out.println("einlï¿½sen wï¿½re schonmal richtig");

					risiko.gibAktivenPlayer().removeKarten(ausgeWahlteKarten);

					listener.combiAusgewaehlt(ausgeWahlteKarten);

//					
					for (Risikokarte r : risiko.gibAktivenPlayer().getEinheitenkarten()) {
						r.setAusgewaehl(false);
						
					}
					for(KartenButton kb : spielerKartenBtn) { 
						kb.setAusgewaehlt(false);
						kb.setUp();
						}
//					spielerKartenBtn.removeAll(spielerKartenBtn)
					risiko.gibAktivenPlayer().removeKarten(ausgeWahlteKarten);
					ausgeWahlteKarten.removeAll(ausgeWahlteKarten);

					risiko.gibAktivenPlayer().removeKarten(ausgeWahlteKarten);
					for(Risikokarte karte: ausgeWahlteKarten) {
						for(KartenButton button: spielerKartenBtn) {
							if(button.getKarte().equals(karte)) {
								this.remove(button);
								button.repaint();
								break;
							}
						}
					}
					listener.updateKartenpanel2();
					
					// hier könnte listener noch eine Methode machen, damit state gesetzt wird

					return true;
				} else {
					// es wurden nicht die richtigen Karten eingelï¿½st
//					JOptionPane.showInternalMessageDialog(null,
//							"Diese Kombination geht leider nicht \nVersuch es nochmal",
//							"es wurden nicht die richtigen Karten eingeloest", JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(null,
						    "Diese Kombination geht leider nicht \nVersuch es nochmal",
						    "es wurden nicht die richtigen Karten eingeloest",
						    JOptionPane.WARNING_MESSAGE);

					for (Risikokarte r : risiko.gibAktivenPlayer().getEinheitenkarten()) {
						r.setAusgewaehl(false);
					}
					for (KartenButton kb : spielerKartenBtn) {
						kb.setAusgewaehlt(false);
					}
					listener.updateKartenpanel();
					return false;
				}
			}
		}
		return false;

	}

//	zaehler fuer Easteregg
	int easterE = 0;

// muss in gleich klasse
	class KartenListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

//			risiko.gibAktivenPlayer().auswahlPruefen(Arr); // gucken ob das array geht..
			if (!dreiKartenAusgewaehlt() && risiko.getTauschZeit()) {

				KartenButton b = (KartenButton) e.getSource();
				b.setAusgewaehlt(true);

				if (dreiKartenAusgewaehlt()) {
//					if(//abfrage nach gï¿½ltigkeit)
//							listener.tauscheRisikokarten(ausgewahelte kartenarray);
				}

//				System.out.println("wurde eine KArte ausgewaeht" + dreiKartenAusgewaehlt());
				easterE = 0;
			}
			//TODO: wenn wir zeit haben
			if (easterE++ > 3) {
				JOptionPane.showInternalMessageDialog(null, "Sorry, aber grad kannst du keine Karten eintauschen",
						"Du kannst grad nichts einloesen", JOptionPane.INFORMATION_MESSAGE);
				easterE = 0;
			}

		}
	}

}

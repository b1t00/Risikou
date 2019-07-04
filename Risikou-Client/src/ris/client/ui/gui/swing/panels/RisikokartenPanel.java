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
//	zaehler fuer Easteregg
	int easterE = 0;

	private ArrayList<KartenButton> spielerKartenBtn = new ArrayList<KartenButton>();

	public interface RisikoKartenListener {
		public void updateKartenpanel();
		public void updateKartenpanel2();
		public void combiAusgewaehlt(ArrayList<Integer> auswahl);
	}

	public RisikokartenPanel(RisikoInterface risk, RisikoKartenListener listener) {
		this.risiko = risk;
		this.listener = listener;
		setUp();
	}

	public void setUp() {
		if (spielerKartenBtn.size() != 0) {
			for (KartenButton kb : spielerKartenBtn) {
				this.remove(kb);
				this.revalidate();
				this.repaint();
			}
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

	public boolean manMussTauschen() {
		for (KartenButton k : spielerKartenBtn) {
			if (k.getKarte() == null) {
				return false;
			}
		}
		return true;
	}

	// check ob spieler bereits drei Karten ausgewaehlt hat
	public boolean dreiKartenAusgewaehlt() {
		System.out.println("in der karten ausgewaehlt methode");
		ArrayList<KartenButton> ausgeWahlteKarten = new ArrayList<KartenButton>();
//		for (Risikokarte k : risiko.gibAktivenPlayer().getEinheitenkarten()) {
		for (KartenButton k : spielerKartenBtn) {
			System.out.println("ist karte ausgewaehlt? " + k.getAusgewaehlt());
			if (k.getAusgewaehlt()) {
				ausgeWahlteKarten.add(k);
			}
		}
		if ((ausgeWahlteKarten.size() > 2)) { 
//	    wenn drei Karten ausgewaehlt wurden, wird als naechstes ueberprueft, ob die kombi gueltig ist
			if (auswahlPruefen(ausgeWahlteKarten)) {
				//die entsprechenden Risikokarten zu den Buttons werden in einen neuen Array geschrieben, damit sie vom Player im Server geloescht werden koennen
				ArrayList<Integer> risikokartenWahl = new ArrayList<Integer>();
				for(KartenButton k : ausgeWahlteKarten) {
					risikokartenWahl.add(k.getKarte().getLand().getNummer());
				}
				System.out.println("einloesen waere schonmal richtig");
				risiko.removeRisikoKarten(risikokartenWahl);
					
//				for(KartenButton kb : spielerKartenBtn) { 
//							kb.setAusgewaehlt(false);
//							kb.setUp();
//					}
//				for(Risikokarte karte: ausgeWahlteKarten) {
//					for(KartenButton button: spielerKartenBtn) {
//						if(button.getKarte().equals(karte)) {
//							this.remove(button);
//							button.repaint();
//							break;
//						}
//					}
//			}
//				aber deses brauchen wir schon nech
				for(KartenButton kb : ausgeWahlteKarten) { 
					System.out.println("remove karte");
							kb.setAusgewaehlt(false);
							kb.setUp();
							this.remove(kb);
							kb.repaint();
				}
				listener.combiAusgewaehlt(risikokartenWahl);
				listener.updateKartenpanel();
				return true;
			} else {
			// es wurden nicht die richtigen Karten eingeloest
				JOptionPane.showMessageDialog(null, "Diese Kombi geht leider nicht. \n Versuche es erneut.");
//				for (KartenButton kb : spielerKartenBtn) {
				for (KartenButton kb : ausgeWahlteKarten) {
					kb.setAusgewaehlt(false);
				}
				listener.updateKartenpanel();
				return false;
				}
		}
		System.out.println("return false nicht drei karten ausgewaehlt");
		return false;
	}

	public boolean auswahlPruefen(ArrayList<KartenButton> arry) {
		//alle sind gleich
		if (arry.get(0).getKarte().getSymbol() == arry.get(1).getKarte().getSymbol() && arry.get(1).getKarte().getSymbol() == arry.get(2).getKarte().getSymbol()) {  
			return true;
		} // alle sind unterschiedlich
		else if (arry.get(0).getKarte().getSymbol() != arry.get(1).getKarte().getSymbol() && arry.get(0).getKarte().getSymbol() != arry.get(2).getKarte().getSymbol() && arry.get(1).getKarte().getSymbol() != arry.get(2).getKarte().getSymbol()) { 
			return true;
		} else {
			return false;
		}
	}

	class KartenListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
//	wenn auf die risikokarte geklickt wird, wird zuerst ueberprueft, ob ueberhaupt geklickt werden kann
		System.out.println("tauschzeit ist: " + risiko.getTauschZeit());
		if(risiko.getTauschZeit()) {
			if (!dreiKartenAusgewaehlt()) {
				KartenButton b = (KartenButton) e.getSource();
				b.setAusgewaehlt(true);
//				risiko.setKarteAusgewaehlt(b.getKarte(), true);
			}	
			//Methode checkt nochmal, ob jetzt drei Karten ausgewaehlt wurden und verarbeitet dies
			dreiKartenAusgewaehlt();
	
		} else {
			if(easterE < 2) {
				JOptionPane.showMessageDialog(null, "Sorry, aber du kannst grad keine Karten eintauschen..");
	//			JOptionPane.showInternalMessageDialog(null, "Sorry, aber grad kannst du keine Karten eintauschen",
	//			"Du kannst grad nichts einloesen", JOptionPane.INFORMATION_MESSAGE);
				easterE++;
			} else {
				JOptionPane.showMessageDialog(null, "Du kannst wirklich keine Karten eintauschen!");
				easterE = 0;
			}
		}
	}
	}

}

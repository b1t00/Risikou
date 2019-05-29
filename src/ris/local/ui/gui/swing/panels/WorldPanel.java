package ris.local.ui.gui.swing.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Land;

public class WorldPanel extends JPanel {
	
	private Risiko ris;
	private WorldListener listener;
	private Land attackLand1;
	private Land attackLand2;
	private Land moveLand1;
	private Land moveLand2;
	private int attackState;
	private int moveState;
//	private BufferedImage karte = null;
	private ImageIcon karte = null;

	public interface WorldListener {
		public void countryClicked(Land land);
	}
	
	public WorldPanel (WorldListener wl, Risiko risiko) {
		listener = wl;
		ris = risiko;
		
		//states werden zu Beginn auf 1 gesetzt und dann je nach Spielstand auf 2 gewechselt
		this.attackState = 1;
		this.moveState = 1;
		
		loadImage();
        initPanel();
	}
	
	  private void loadImage() {
	        karte = new ImageIcon("assets/img/karte.jpg");
	    }
	    
	    private void initPanel() {
//	        int w = karte.getIconWidth();
//	        int h = karte.getIconHeight();
//	        setPreferredSize(new Dimension(w, h));
//	        setSize(800, 600);
	        karte.setImage(karte.getImage().getScaledInstance(800,600,Image.SCALE_DEFAULT));
	    }    

	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        karte.paintIcon(this, g, 0, 0);
	        
	    }
	
	
	public class ClickListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Land land = null;
			//je nach state des spiels und state der phase wird das geklickte land auf das jeweilige Attribut gesetzt
		//	Land land = ris.getLandById(zahl);
			switch(ris.getCurrentState()) {
			case SETUNITS:	
			case ATTACK:
				if(attackState == 1) {
					attackLand1 = land;
					attackState = 2;
				} else {
					attackLand2 = land;
					attackState = 1;
				}
			case CHANGEUNITS:
				if(moveState == 1) {
					moveLand1 = land;
					moveState = 2;
				} else {
					moveLand2 = land;
					moveState = 1;
				}
			}
			
			listener.countryClicked(land);
		}
	}
	
	//Getter Methoden
	public int getAttackState() {
		return attackState;
	}
	
	public int getMoveState() {
		return moveState;
	}
	
	public Land getAttackLand1() {
		return attackLand1;
	}
	
	public Land getAttackLand2() {
		return attackLand2;
	}
	
	public Land getMoveLand1() {
		return moveLand1;
	}
	
	public Land getMoveLand2() {
		return moveLand2;
	}

	
}

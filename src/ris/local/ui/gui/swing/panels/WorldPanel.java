package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	private BufferedImage karte = null;
	
//	private ImageIcon karte = null;

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

//_________________Methode bei MouseClic___________________________
        //Kann die Methode ausgelagert werden?
        addMouseListener(new MouseAdapter() { 
            public void mousePressed(MouseEvent me) { 
            	System.out.println("Klick!");
			// TODO Auto-generated method stub
			int x = me.getX();
			int y = me.getY();

			Color color = new Color(karte.getRGB(x,y));
			int b = color.getBlue();
			System.out.println("color ist: " + b);
			System.out.println("Land: " + risiko.getLandById(b));
			
			Land land = null;
//			//je nach state des spiels und state der phase wird das geklickte land auf das jeweilige Attribut gesetzt
			land = ris.getLandById(b);
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
//_________________ENDE Methode bei MouseClic___________________________
            
            
          }); 
	}
	
	  private void loadImage() {    
			try {
				karte=ImageIO.read(new File("assets/img/risiko_map_b.png"));
			}
			catch(IOException e){System.out.println("HIER IST EIN FEHLER.");
			}	
	  }
	  
	    @Override
	    public void paintComponent(Graphics g) {
	    	g.drawImage(karte,0,0,null);	        
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

package ris.local.ui.gui.swing.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ris.local.domain.Risiko;
import ris.local.valueobjects.Land;
import ris.local.domain.WorldManagement;

public class WorldPanel extends JPanel {
	private WorldManagement wM= new WorldManagement();
	private Risiko ris;
	private WorldListener listener;
	private Land attackLand1;
	private Land attackLand2;
	private Land moveLand1;
	private Land moveLand2;
	private int attackState;
	private int moveState;
	private BufferedImage karte = null;
	private BufferedImage karte2 =null;
	private BufferedImage flagr= null;
	private BufferedImage flagbl= null;
	private BufferedImage flaggr= null;
	private BufferedImage flagw= null;
	private BufferedImage flagbc= null;
	private BufferedImage flagp= null;
	
//	private ImageIcon karte = null;

	public interface WorldListener {
		public void countryClicked(Land land);
	}

	public WorldListener getListener() {
		return this.listener;
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

			int x = me.getX();
			int y = me.getY();

			Color color = new Color(karte.getRGB(x,y));
			int b = color.getBlue();
			System.out.println("Land: " + risiko.getLandById(b));
			System.out.println("x: "+ x+"  y: "+y);
			Land land = null;
//			//je nach state des spiels und state der phase wird das geklickte land auf das jeweilige Attribut gesetzt
			land = ris.getLandById(b);
			switch(ris.getCurrentState()) {
			case SETUNITS:	
				if(land.getBesitzer().equals(ris.gibAktivenPlayer())) {
					listener.countryClicked(land);
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Das Land gehört dir nicht");
				}
				break;
			case ATTACK:
				if(attackState == 1) {
					//es wird überprüft, ob das angeklickte Land gültig ist
					if(ris.attackLandGueltig(land)) {
						attackLand1 = land;
						attackState = 2;
						listener.countryClicked(land);
					} else {
						JOptionPane.showMessageDialog(null, "Das Land gehört dir nicht.");
					}	
				} else {
					if(ris.defenseLandGueltig(attackLand1, land)) {
						attackLand2 = land;
						attackState = 1;
						listener.countryClicked(land);
					} else {
						JOptionPane.showMessageDialog(null, "Das Land gehört dir selber oder ist nicht mit dem Angriffsland benachbart!");
					}
				}
				break;
			case CHANGEUNITS:
				if(moveState == 1) {
					if(ris.moveFromLandGueltig(land)) {
						moveLand1 = land;
						moveState = 2;
						listener.countryClicked(land);
					} else {
						JOptionPane.showMessageDialog(null, "Das Land gehört dir nicht oder du kannst von hier keine Einheiten verschieben.");
					}
				} else {
					//TODO: und länder sind benachbart!
					if(land.getBesitzer().equals(ris.gibAktivenPlayer())) {
						moveLand2 = land;
						moveState = 1;
						listener.countryClicked(land);
					} else {
						JOptionPane.showMessageDialog(null, "Das Land gehört dir nicht.");
					}
				}
				break;
			}
            } 
//_________________ENDE Methode bei MouseClic___________________________
            
            
          }); 
	}
	
	  private void loadImage() {    
			try {
				karte=ImageIO.read(new File("assets/img/risiko_map_b.png"));
				karte2=ImageIO.read(new File("assets/img/risiko_map.jpg"));
				flagr=ImageIO.read(new File("assets/img/flag_rot.png"));
				flagbl=ImageIO.read(new File("assets/img/flag_blau.png"));
				flaggr=ImageIO.read(new File("assets/img/flag_gruen.png"));
				flagw=ImageIO.read(new File("assets/img/flag_weiss.png"));
				flagp=ImageIO.read(new File("assets/img/flag_pink.png"));
				flagbc=ImageIO.read(new File("assets/img/flag_schwarz.png"));
			}
			catch(IOException e){System.out.println("HIER IST EIN FEHLER.");
			}	
	  }

	  public void flagForCountry(ArrayList<Land> laender,Graphics g) {
		  laender= wM.getLaender();
		  for(Land land:laender) {
		  switch(land.getBesitzer().getFarbe()) {
		  case "rot":
			  g.drawImage(flagr,land.getXf(),land.getYf(),null);
			  break;
		  case "gruen":
			  g.drawImage(flaggr,land.getXf(),land.getYf(),null);
			  break;
		  case "blau":
			  g.drawImage(flagbl,land.getXf(),land.getYf(),null);
			  break;
		  case "weiss":
			  g.drawImage(flagw,land.getXf(),land.getYf(),null);
		  case "pink":
			  g.drawImage(flagp,land.getXf(),land.getYf(),null);
		  case "schwarz":
			  g.drawImage(flagbc,land.getXf(),land.getYf(),null);
			  g.drawString("1", land.getxE(), land.getyE());
		default:
			g.drawImage(flagp,land.getXf(),land.getYf(),null);
		  }  
		  }
	  }
	    @Override
	    public void paintComponent(Graphics g) {
	    	g.drawImage(karte,0,0,null);
	    	g.drawImage(karte2,0,0,null);
	    	for(Land land:wM.getLaender()) {
	    		g.drawImage(flagp,land.getXf(),land.getYf(),null);
	    		g.setFont(new Font("TimesRoman", Font.BOLD, 24));
	    		g.drawString(" "+land.getEinheiten(), land.getxE(), land.getyE());
	    		//flagForCountry(wM.getLaender(), g);


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

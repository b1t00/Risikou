package ris.local.ui.gui.swing.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ris.local.valueobjects.Attack;
import ris.local.valueobjects.Land;
import ris.local.valueobjects.Player;

public class DicePanel extends JPanel {
	private Attack attObj;
	private BufferedImage wuerfel1 = null;
	private BufferedImage wuerfel2 = null;
	private BufferedImage wuerfel3 = null;
	private BufferedImage wuerfel4 = null;
	private BufferedImage wuerfel5 = null;
	private BufferedImage wuerfel6 = null;
	
	
	public DicePanel() {
		loadImage();
	}
	
	private void loadImage() {    
		try {
			wuerfel1 = ImageIO.read(new File("assets/img/wuerfel1.png"));
			wuerfel2 = ImageIO.read(new File("assets/img/wuerfel2.jpg"));
			wuerfel3 = ImageIO.read(new File("assets/img/wuerfel3.png"));
			wuerfel4 = ImageIO.read(new File("assets/img/wuerfel4.png"));
			wuerfel5 = ImageIO.read(new File("assets/img/wuerfel5.png"));
			wuerfel6 = ImageIO.read(new File("assets/img/wuerfel6.png"));
		}
		catch(IOException e){System.out.println("HIER IST EIN FEHLER.");
		}	
	}
	
	   @Override
	    public void paintComponent(Graphics g) {
	    	g.drawImage(wuerfel1, 0, 0, null);
	    	g.drawImage(wuerfel2, 0, 0, null);
	    	g.drawImage(wuerfel3, 0, 0, null);
	    	g.drawImage(wuerfel4, 0, 0, null);
	    	g.drawImage(wuerfel5, 0, 0, null);
	    	g.drawImage(wuerfel6, 0, 0, null);
	    	
//	    	ArrayList<Player> playerArray = ris.getPlayerArray();
//	    	for(Player player: playerArray) {
//	    		for (Land land: player.getBesitz()) {
//	    			drawFlag(land, player.getFarbe(), g);
//	    			g.drawString(" "+land.getEinheiten(), land.getxE(), land.getyE());
//	    			g.setFont(new Font("TimesRoman", Font.BOLD, 18));
//	    		}
//	    	}
	    }
	
	public void setAttack(Attack attObj) {
		this.attObj = attObj;
	}
	
	public void showResult() {
		String ergebnis = attObj.getAttacker() + " würfelt ";
		System.out.println("dice attack anzahl: " + attObj.getAttUnits().length);
		int[] attack = attObj.getAttUnits();
		for (Integer i : attack) {
			ergebnis = ergebnis + i + ", ";
		}
		ergebnis = ergebnis + ". " + attObj.getDefender() + " würfelt ";
		int[] defense = attObj.getDefUnits();
		for (Integer i : defense) {
			ergebnis = ergebnis + i + ", ";
		}
		JOptionPane.showMessageDialog(null, ergebnis);
	}
	//Methode zum Anzeigen
	
}

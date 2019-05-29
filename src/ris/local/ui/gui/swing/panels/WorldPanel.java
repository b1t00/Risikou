package ris.local.ui.gui.swing.panels;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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

	public interface WorldListener {
		public void countryClicked(Land land);
	}
	
	public WorldPanel (WorldListener wl, Risiko risiko) {
		listener = wl;
		ris = risiko;
		
		//states werden zu Beginn auf 1 gesetzt und dann je nach Spielstand auf 2 gewechselt
		this.attackState = 1;
		this.moveState = 1;
		
	
//		try {
//			karte = ImageIO.read(WorldPanel.class.getClassLoader().getResourceAsStream("karte.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
//		getContentPane().add(new JLabel(new ImageIcon("karte.jpg")));
//		pack();
//		ImageIcon icon = new ImageIcon("karte.jpg");
		JLabel label = new JLabel(new ImageIcon("file: assets/img/karte.jpg"));
//		JLabel label = new JLabel(new ImageIcon(this.getClass().getResource("karte.jpg")));
//		JLabel label = new JLabel(new ImageIcon(this.getClass().getResource("karte.jpg")));
//		JLabel label = new JLabel(icon);
//		JLabel label = new JLabel(showImg());
//        JPanel panel = new JPanel();
		this.setLayout(new GridLayout(2, 1));
		this.add(label);
        this.add(new JLabel("Test"));
        this.setSize(400,400);
//        this.add(panel);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setTitle("Bild laden bei Swing");
//        this.setSize(600, 400);
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);
        
	}

//	    private ImageIcon showImg() {
//	        BufferedImage img = null;
//	        try {
//	        	ImageIO.read(getClass().getResource("karte.jpg"));
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        return new ImageIcon(img);
//	    }
	
	public void paintComponent(Graphics g) {
		g.drawImage(karte, 0, 0, 200, 100, null);
//		g.drawString(this.getBounds().toString(), 5, 15);
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

package ris.local.ui.gui.swing.panels;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private ImageIcon icon;

    public ImagePanel() {

        loadImage();
        initPanel();
    }

    private void loadImage() {

        icon = new ImageIcon("src/ris/local/ui/gui/swing/panels/karte.jpg");
    }
    
    private void initPanel() {

        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        setPreferredSize(new Dimension(w, h));

    }    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        icon.paintIcon(this, g, 0, 0);
    }
    
   
}


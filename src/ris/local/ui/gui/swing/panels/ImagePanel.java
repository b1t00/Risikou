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

        icon = new ImageIcon("...assets/img/karte.jpg");
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
    
    public class PaintingIconEx extends JFrame {

        public PaintingIconEx() {

            initUI();
        }

        private void initUI() {

            DrawingPanel dpnl = new DrawingPanel();

            createLayout(dpnl);

            setTitle("Image");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        private void createLayout(JComponent... arg) {

            Container pane = getContentPane();
            GroupLayout gl = new GroupLayout(pane);
            pane.setLayout(gl);

            gl.setHorizontalGroup(gl.createSequentialGroup()
                    .addComponent(arg[0])
            );

            gl.setVerticalGroup(gl.createParallelGroup()
                    .addComponent(arg[0])
            );

            pack();
        }

        public static void main(String[] args) {

            EventQueue.invokeLater(() -> {
                JFrame ex = new PaintingIconEx();
                ex.setVisible(true);
            });
        }
    }
}


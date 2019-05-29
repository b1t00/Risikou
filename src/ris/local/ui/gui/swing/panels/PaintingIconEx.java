package ris.local.ui.gui.swing.panels;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class PaintingIconEx extends JFrame {

    public PaintingIconEx() {

        initUI();
    }

    private void initUI() {

        ImagePanel imgPl = new ImagePanel();

        createLayout(imgPl);

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

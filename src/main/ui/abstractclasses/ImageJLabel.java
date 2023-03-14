package ui.abstractclasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ImageJLabel extends JLabel implements ActionListener {

    private final Timer timer = new Timer(10,this);
    private static int corY = 816;
    private int velY = 10;
    private static String status = "Showing";
    private final String source;
    private final Image board;

    // EFFECTS: construct the Main Menu Panel and printout the content
    public ImageJLabel() {
        this.source = "";
        this.board = new ImageIcon(source).getImage();
        setVisible(true);
        setBounds(435,60,600,1500);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(board, 0, corY, 600, 700, this);
        timer.start();

    }

    public void setCorY(int n) {
        corY = n;
    }

    public void setStatus(String s) {
        status = s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (corY > 0 && corY <= 816 && status.equals("Moving Up")) {
            setVisible(true);
            corY -= velY;
            velY += 1;
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
            corY = 0;
            repaint();
        } else if (corY >= 0 && corY < 816 && status.equals("Moving Down")) {
            setVisible(true);
            corY += velY;
            velY += 1;
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
            corY = 816;
            repaint();
        }
    }
}

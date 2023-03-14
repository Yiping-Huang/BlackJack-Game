package ui.background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JokerJLabel extends JLabel implements ActionListener {

    private final Timer timer = new Timer(10,this);
    private static int corX = 0;
    private int velX = 10;
    private static String status = "Showing";
    private final Image board = new ImageIcon("./images/JokerImage.png").getImage();

    public JokerJLabel() {
        setVisible(true);
        setBounds(-50,80,480,640);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(board, corX, 0, 480, 640, this);
        timer.start();

    }

    public void setCorX(int n) {
        corX = n;
    }

    public void setStatus(String s) {
        status = s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (corX < 0 && corX >= -800 && status.equals("Moving Right")) {
            setVisible(true);
            corX += velX;
            velX += 1;
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
        } else if (corX <= 0 && corX > -800 && status.equals("Moving Left")) {
            setVisible(true);
            corX -= velX;
            velX += 1;
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}


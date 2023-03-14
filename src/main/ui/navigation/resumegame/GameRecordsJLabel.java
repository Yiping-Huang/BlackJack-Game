package ui.navigation.resumegame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameRecordsJLabel extends JLabel implements ActionListener {

    private static int corY = 100;
    private int velY = 10;
    private static String status = "Hiding";

    public GameRecordsJLabel() {
        setBounds(485,100,500,380);
        setLocation(485, corY);
        setSize(500,380);
        setVisible(false);
        Timer timer = new Timer(10, this);
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
        if (corY >= 100 && corY < 900 && status.equals("Moving Down")) {
            setVisible(true);
            setLocation(getX(), corY += velY);
            velY += 1;
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}

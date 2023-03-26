package ui.navigationphase.resumegamepage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameRecordsJLabel extends JLabel implements ActionListener {

    private int corY = 100;
    private static String status = "Showing";
    private final Timer timer = new Timer(10,this);

    public GameRecordsJLabel() {
        setBounds(485,100,500,380);
        setLocation(485, corY);
        setSize(500,380);
        setVisible(false);
        timer.start();
    }

    public void setCorY(int n) {
        corY = n;
    }

    public void setStatus(String s) {
        status = s;
    }

    public void stopTimer() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int velY = 32;
        if (corY >= 100 && corY < 900 && status.equals("Moving Down")) {
            setVisible(true);
            setLocation(getX(), corY += velY);
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}

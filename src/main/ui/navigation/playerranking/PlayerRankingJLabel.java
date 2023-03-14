package ui.navigation.playerranking;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerRankingJLabel extends JLabel implements ActionListener {

    private static String status = "Hiding";

    public PlayerRankingJLabel() {
        setBounds(485,160,500,500);
        setLocation(485, 160);
        setSize(500,480);
        setVisible(false);
        Timer timer = new Timer(10, this);
        timer.start();
    }

    public void setStatus(String s) {
        status = s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (status.equals("Showing")) {
            setVisible(true);
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}

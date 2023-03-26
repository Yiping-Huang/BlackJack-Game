package ui.navigationphase.startnewgamepage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartNewGameJTextField extends JTextField implements ActionListener {

    private static int corY = 350;
    private static String status = "Showing";
    private final Timer timer = new Timer(10,this);

    public StartNewGameJTextField() {
        setBounds(485,350,450,70);
        setLocation(485, corY);
        setSize(500, 70);
        setFont(new Font("Times New Roman", Font.BOLD, 30));
        setForeground(Color.BLACK);
        setBackground(new Color(255, 255, 224));
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
        if (corY >= 350 && corY < 1150 && status.equals("Moving Down")) {
            setVisible(true);
            setLocation(getX(), corY += velY);
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
            setLocation(getX(), corY);
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}

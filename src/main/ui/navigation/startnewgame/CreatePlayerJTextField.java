package ui.navigation.startnewgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePlayerJTextField extends JTextField implements ActionListener {

    private static int corY = 350;
    private int velY = 10;
    private static String status = "Hiding";

    public CreatePlayerJTextField() {
        setBounds(485,350,450,70);
        setLocation(485, corY);
        setSize(500, 70);
        setFont(new Font("Times New Roman", Font.BOLD, 30));
        setForeground(Color.BLACK);
        setBackground(new Color(255, 255, 224));
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
        if (corY >= 350 && corY < 1150 && status.equals("Moving Down")) {
            setVisible(true);
            setLocation(getX(), corY += velY);
            velY += 1;
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
            corY = 350;
            setLocation(getX(), corY);
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
        }
    }
}

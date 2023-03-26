package ui.navigationphase.startnewgamepage;

// This class represents the display interface of Create Player Panel. User can create a new player on this panel
// with the name that user types in

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StartNewGameHeaderJLabel extends JLabel implements ActionListener {

    private static int corY = 160;
    private static String status = "Showing";
    private final Timer timer = new Timer(10,this);

    public StartNewGameHeaderJLabel() {
        super("<html><body>&nbsp;&nbsp;&nbsp;Enter the Name for "
                + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a New Player</body></html>");
        setFont(new Font("Times New Roman", Font.BOLD, 50));
        setBounds(500,160,500,150);
        setLocation(500, 160);
        setVisible(false);
        setForeground(Color.BLACK);
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
        if (corY >= 160 && corY < (160 + 800) && status.equals("Moving Down")) {
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

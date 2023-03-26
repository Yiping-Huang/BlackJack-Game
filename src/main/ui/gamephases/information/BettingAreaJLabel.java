package ui.gamephases.information;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class represents the display interface of the amount of money in the betting box

public class BettingAreaJLabel extends JLabel implements ActionListener {

    private static String status = "Showing";
    private static int counter = 0;
    private final Timer timer = new Timer(10, this);

    // EFFECTS: construct a betting area label with the given gameState
    public BettingAreaJLabel(GameState gameState) {
        super("<html><body>Bet $&nbsp;&nbsp;" + gameState.getBettingBox() + "</body></html>");
        setFont(new Font("Times New Roman", Font.BOLD, 50));
        setBounds(1125,50,500,150);
        setLocation(1125, 50);
        setVisible(false);
        setForeground(Color.ORANGE);
        timer.start();
    }

    public void setStatus(String s) {
        status = s;
    }

    public void stopTimer() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (status.equals("Showing")) {
            setVisible(true);
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
            repaint();
        } else if (counter >= 0 && 40 > counter && status.equals("Delay Showing")) {
            counter += 1;
        } else if (counter == 40 && status.equals("Delay Showing")) {
            counter = 0;
            status = "Showing";
        } else if (status.equals("Raise")) {
            setVisible(true);
            repaint();
        }
    }
}

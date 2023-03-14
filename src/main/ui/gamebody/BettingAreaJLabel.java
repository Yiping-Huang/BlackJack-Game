package ui.gamebody;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class represents the display interface of the amount of money in the betting box

public class BettingAreaJLabel extends JLabel implements ActionListener {

    private GameState gameState;
    private static String status = "Hiding";
    private static int counter = 0;

    // EFFECTS: construct a betting area label with the given gameState
    public BettingAreaJLabel(GameState gameState) {
        super("<html><body>Bet $&nbsp;&nbsp;" + gameState.getBettingBox() + "</body></html>");
        this.gameState = gameState;
        setFont(new Font("Times New Roman", Font.BOLD, 50));
        setBounds(1150,20,500,150);
        setLocation(1150, 20);
        setVisible(false);
        setForeground(Color.ORANGE);
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
        } else if (status.equals("Hiding")) {
            setVisible(false);
        } else if (counter >= 0 && 100 > counter && status.equals("Delay Showing")) {
            counter += 1;
        } else if (counter == 100 && status.equals("Delay Showing")) {
            counter = 0;
            status = "Showing";
        } else if (status.equals("Raise")) {
            setVisible(true);
            repaint();
        }
    }
}

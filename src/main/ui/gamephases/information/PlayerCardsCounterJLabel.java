package ui.gamephases.information;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class is used to display the sum of the face-up cards on player's hand

public class PlayerCardsCounterJLabel extends JLabel implements ActionListener {

    private static String status = "Showing";
    private final Timer timer = new Timer(10, this);

    // EFFECTS:  construct a player cards counter label with the given gameState
    public PlayerCardsCounterJLabel(GameState gameState, int corX, int corY) {
        super("Player: " + gameState.getPlayerCards().sumPlayerCards());
        setFont(new Font("Times New Roman", Font.BOLD, 40));
        setBounds(corX,corY,300,50);
        setLocation(corX, corY);
        setVisible(false);
        setForeground(Color.BLACK);
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
        }
    }
}

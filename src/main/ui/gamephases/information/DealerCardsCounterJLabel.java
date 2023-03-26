package ui.gamephases.information;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class is used to display the sum of the face-up cards on dealer's hand

public class DealerCardsCounterJLabel extends JLabel implements ActionListener {

    private static String status = "Showing";
    private final Timer timer = new Timer(10, this);

    // EFFECTS:  construct a dealer cards counter label with the given gameState
    public DealerCardsCounterJLabel(GameState gameState, int corX, int corY) {
        super("Dealer: " + gameState.getDealerCards().sumFaceUpDealerCards());
        setFont(new Font("Times New Roman", Font.BOLD, 40));
        setBounds(corX,corY,300,70);
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

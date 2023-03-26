package ui.gamephases.information;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class represents the display interface of player information; this is used at the game stage

public class PlayerInfoTextJLabel extends JLabel implements ActionListener {

    private static String status = "Showing";
    private static int counter = 0;
    private final Timer timer = new Timer(10, this);

    // EFFECTS:  construct a player info label with the given gameState
    public PlayerInfoTextJLabel(GameState gameState) {
        super("<html><body>Player Name&nbsp:&nbsp;"
                + gameState.getPlayerName()
                + "<br>Player Assets:&nbsp;$" + gameState.getPlayerAssets()
                + "<br>Player Rounds:&nbsp;" + gameState.getPlayerRounds() + "</body></html>");
        setFont(new Font("Times New Roman", Font.BOLD, 28));
        setBounds(1125,160,500,150);
        setLocation(1125, 160);
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
        } else if (counter >= 0 && 40 > counter && status.equals("Delay Showing")) {
            counter += 1;
        } else if (counter == 40 && status.equals("Delay Showing")) {
            counter = 0;
            status = "Showing";
        }
    }
}

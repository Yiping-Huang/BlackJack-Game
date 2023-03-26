package ui.gamephases.cards;

import model.Card;
import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class is used to represent the tenth card on Player's hand.

public class PlayerCardJLabel10 extends JLabel implements ActionListener {

    private String status = "Hiding";
    private int corY;
    private int velY = 10;
    private final Image cardImg;
    private final Timer timer = new Timer(10, this);

    // EFFECTS: construct the tenth player card with the given gameState
    public PlayerCardJLabel10(GameState gameState, int corX, int iniCorY, int corY, int index) {
        this.corY = corY;
        if (index + 1 <= gameState.getPlayerCards().getList().size()) {
            Card card = gameState.getPlayerCards().getList().get(index);
            String source = "./images/" + cardAdaptor(card) + ".png";
            cardImg = new ImageIcon(source).getImage();
            setVisible(false);
            setBounds(corX, iniCorY, 150, 1236);
            status = "Showing";
            timer.start();
        } else {
            cardImg = null;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(cardImg, 0, corY, 150, 220, this);
    }

    public void setCorY(int n) {
        corY = n;
    }

    public int getCorY() {
        return corY;
    }

    public void setStatus(String s) {
        status = s;
    }

    public void stopTimer() {
        timer.stop();
    }

    // REQUIRES: card is not null
    // EFFECTS:  produce the correct image source according to the status of the card
    public String cardAdaptor(Card card) {
        if (card.getCardStatus().equals("face-up")) {
            return card.getCardSuit() + card.getCardNum();
        } else {
            return "PokerBack";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (corY > 0 && corY <= 816 && status.equals("Moving Up")) {
            setVisible(true);
            corY -= velY;
            velY += 1;
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
            corY = 0;
            repaint();
        } else if (corY >= 0 && corY < 816 && status.equals("Moving Down")) {
            setVisible(true);
            corY += velY;
            velY += 1;
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
            corY = 816;
            repaint();
        }
    }
}
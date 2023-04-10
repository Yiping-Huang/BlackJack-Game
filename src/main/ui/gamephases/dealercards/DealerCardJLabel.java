package ui.gamephases.dealercards;

import model.Card;
import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class DealerCardJLabel extends JLabel implements ActionListener {

    private String status = "Hiding";
    private int corY;
    private int velY = 10;
    private final Image cardImg;
    private int counter = 0;
    private int counterMax = 0;
    private final Timer timer = new Timer(10, this);


    // EFFECTS: construct the dealer card with the given gameState
    public DealerCardJLabel(GameState gameState, int corX, int iniCorY, int corY, int index) {
        this.corY = corY;
        if (index + 1 <= gameState.getDealerCards().getList().size()) {
            Card card = gameState.getDealerCards().getList().get(index);
            String source = "./images/" + cardAdaptor(card) + ".png";
            cardImg = new ImageIcon(source).getImage();
            setVisible(false);
            setBounds(corX,iniCorY,150,1236);
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

    public void setCounterMax(int max) {
        counterMax = max;
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

    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (corY <= 840 && corY > 0 && status.equals("Moving Up")) {
            setVisible(true);
            corY -= velY;
            velY += 1;
            repaint();
        } else if (status.equals("Showing")) {
            setVisible(true);
            repaint();
        } else if (corY < 840 && corY >= 0 && status.equals("Moving Down")) {
            setVisible(true);
            corY += velY;
            velY += 1;
            repaint();
        } else if (status.equals("Hiding")) {
            setVisible(false);
            repaint();
        } else if (counter >= 0 && counterMax > counter && status.equals("Delay Moving Down")) {
            counter += 1;
        } else if (counter == counterMax && status.equals("Delay Moving Down")) {
            counter = 0;
            status = "Moving Down";
        }
    }
}

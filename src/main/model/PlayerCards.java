package model;

import java.util.ArrayList;

public class PlayerCards {
    ArrayList<Card> playerCards;

    public PlayerCards() {
        playerCards = new ArrayList<>();
    }

    public ArrayList<Card> getList() {
        return playerCards;
    }

    public void addPlayerCard(Card card) {
        playerCards.add(card);
    }

    public void clearPlayerCards() {
        playerCards.clear();
    }

    public int sumPlayerCards() {
        int initialSum = 0;
        int numOfPokerA = 0;
        int finalSum;
        for (Card c1 : playerCards) {
            if (c1.getCardNum() == 1) {
                numOfPokerA = numOfPokerA + 1;
            }
        }
        for (Card c2 : playerCards) {
            initialSum = initialSum + c2.getCardValue();
        }
        if ((initialSum <= 11) && (numOfPokerA >= 1)) {
            finalSum = initialSum + 10;
        } else if (numOfPokerA == 0) {
            finalSum = initialSum;
        } else {
            finalSum = initialSum;
        }
        return finalSum;
    }

    public boolean bustOrNot() {
        return this.sumPlayerCards() > 21;
    }
}

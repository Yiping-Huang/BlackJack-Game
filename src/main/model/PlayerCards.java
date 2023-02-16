package model;

import java.util.ArrayList;

// This class represent the set of cards that the player currently has on hand

public class PlayerCards {
    ArrayList<Card> playerCards;

    // EFFECTS: construct an empty list as Player's Cards
    public PlayerCards() {
        playerCards = new ArrayList<>();
    }

    public ArrayList<Card> getList() {
        return playerCards;
    }

    // MODIFIES: this
    // EFFECTS:  add a new card to Player's Cards
    public void addPlayerCard(Card card) {
        playerCards.add(card);
    }

    // MODIFIES: this
    // EFFECTS:  clear all the existing cards on Player's hand
    public void clearPlayerCards() {
        playerCards.clear();
    }

    // EFFECTS:  sum the value of all the cards on Player's hand; Poker A's value could be 1 or 11; the exact value of
    // Poker A depends on in which value that the sum of Player's cards can be maximized without busting
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

    // EFFECTS:  return whether the sum of Player's cards is over 21
    public boolean bustOrNot() {
        return this.sumPlayerCards() > 21;
    }
}

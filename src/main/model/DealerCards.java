package model;

import java.util.ArrayList;

// This class represent the set of cards that the dealer currently has on hand

public class DealerCards {
    ArrayList<Card> dealerCards;

    // EFFECTS: construct an empty list as Dealer's Cards
    public DealerCards() {
        dealerCards = new ArrayList<>();
    }

    public ArrayList<Card> getList() {
        return dealerCards;
    }

    // MODIFIES: this
    // EFFECTS:  add a new card to Dealer's Cards
    public void addDealerCard(Card card) {
        dealerCards.add(card);
    }

    // MODIFIES: this
    // EFFECTS:  clear all the existing cards on Dealer's hand
    public void clearDealerCards() {
        dealerCards.clear();
    }

    // REQUIRES: the length of dealCards is larger than or equal to 2
    // MODIFIES: this
    // EFFECTS:  set the card status of the second card on dealer's hand
    public void setSecondCardStatus(String cardStatus) {
        Card secondCard = dealerCards.get(1);
        secondCard.setCardStatus(cardStatus);
        dealerCards.set(1, secondCard);
    }

    // EFFECTS:  sum the value of all the cards on Dealer's hand; Poker A's value could be 1 or 11; the exact value of
    // Poker A depends on in which value that the sum of Dealer's cards can be maximized without busting
    public int sumDealerCards() {
        int initialSum = 0;
        int numOfPokerA = 0;
        int finalSum;
        for (Card c1 : dealerCards) {
            if (c1.getCardNum() == 1) {
                numOfPokerA = numOfPokerA + 1;
            }
        }
        for (Card c2 : dealerCards) {
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

    // EFFECTS:  sum the value of all the face-up cards on dealer's hand; Poker A's value could be 1 or 11; the exact
    // value of Poker A depends on in which value that the sum of dealer's cards can be maximized without busting
    public int sumFaceUpDealerCards() {
        int initialSum = 0;
        int numOfPokerA = 0;
        int finalSum;
        for (Card c1 : dealerCards) {
            if (c1.getCardNum() == 1 && c1.getCardStatus().equals("face-up")) {
                numOfPokerA = numOfPokerA + 1;
            }
        }
        for (Card c2 : dealerCards) {
            if (c2.getCardStatus().equals("face-up")) {
                initialSum = initialSum + c2.getCardValue();
            }
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

    // EFFECTS:  return whether the sum of Dealer's cards is over 21
    public boolean bustOrNot() {
        return this.sumDealerCards() > 21;
    }
}
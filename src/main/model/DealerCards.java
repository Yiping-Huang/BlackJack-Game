package model;

import java.util.ArrayList;

public class DealerCards {
    ArrayList<Card> dealerCards;

    public DealerCards() {
        dealerCards = new ArrayList<>();
    }

    public ArrayList<Card> getList() {
        return dealerCards;
    }

    public void addDealerCard(Card card) {
        dealerCards.add(card);
    }

    public void clearDealerCards() {
        dealerCards.clear();
    }

    public void setSecondCardStatus(String cardStatus) {
        Card secondCard = dealerCards.get(1);
        secondCard.setCardStatus(cardStatus);
        dealerCards.set(1, secondCard);
    }

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

    public boolean bustOrNot() {
        return this.sumDealerCards() > 21;
    }
}
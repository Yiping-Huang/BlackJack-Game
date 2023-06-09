package model;

// This class represent an individual poker card with its card number, suit, and status (face-up or face-down).

public class Card {
    private final int cardNum;
    private final String cardSuit;
    private String cardStatus;

    // REQUIRES: cardNum is among [0,13]; card suit is one of "Diamonds", "Clubs", "Hearts", and "Spades"; card status
    // is either "face-up" or "face-down"
    // EFFECTS:  construct a new card with its cardNum, cardSuit, and cardStatus
    public Card(int cardNum, String cardSuit, String cardStatus) {
        this.cardNum = cardNum;
        this.cardSuit = cardSuit;
        this.cardStatus = cardStatus;
    }

    public int getCardNum() {
        return cardNum;
    }

    public String getCardSuit() {
        return cardSuit;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    // EFFECTS:  if the cardNum is among [1,10], cardValue is the same as cardNum; if the cardNum is larger than 10,
    // cardValue is 10; return cardValue
    public int getCardValue() {
        int cardValue;
        if ((cardNum <= 10) && (cardNum >= 1)) {
            cardValue = cardNum;
        } else {
            cardValue = 10;
        }
        return cardValue;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

}

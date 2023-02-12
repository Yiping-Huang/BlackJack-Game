package model;

public class Card {
    private final int cardNum;
    private final String cardSuit;
    private String cardStatus;

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

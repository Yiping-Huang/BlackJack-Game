package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the first card on Dealer's hand.

public class DealerCardLabel1 {
    private final GameState gameState;

    // EFFECTS: construct the first dealer card with the given gameState
    public DealerCardLabel1(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the first element of dealer cards is not empty
    // MODIFIES: this
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getDealerCards().getList().get(0);
        String cardDescription;
        if (card.getCardNum() == 1 && card.getCardStatus().equals("face-up")) {
            cardDescription = card.getCardSuit() + " A";
            return cardDescription;
        } else if (card.getCardNum() == 11 && card.getCardStatus().equals("face-up")) {
            cardDescription = card.getCardSuit() + " J";
            return cardDescription;
        } else if (card.getCardNum() == 12 && card.getCardStatus().equals("face-up")) {
            cardDescription = card.getCardSuit() + " Q";
            return cardDescription;
        } else if (card.getCardNum() == 13 && card.getCardStatus().equals("face-up")) {
            cardDescription = card.getCardSuit() + " K";
            return cardDescription;
        } else if (card.getCardStatus().equals("face-down")) {
            cardDescription = "This card is facing down.";
            return cardDescription;
        } else {
            cardDescription = card.getCardSuit() + " " + card.getCardNum();
            return cardDescription;
        }
    }

    // REQUIRES: the first element of dealer cards is not empty
    // MODIFIES: this
    // EFFECTS:  display the card description of the first dealer card
    public void display() {
        System.out.println("Dealer Card #1: " + cardAdaptor(gameState));
    }
}
package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the second card on Dealer's hand.

public class DealerCardLabel2 {
    private final GameState gameState;

    // EFFECTS: construct the second dealer card with the given gameState
    public DealerCardLabel2(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the second element of dealer cards is not empty
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getDealerCards().getList().get(1);
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

    // REQUIRES: the second element of dealer cards is not empty
    // EFFECTS:  display the card description of the second dealer card
    public void display() {
        System.out.println("Dealer Card #2: " + cardAdaptor(gameState));
    }
}

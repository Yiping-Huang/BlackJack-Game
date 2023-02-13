package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the fifth card on Dealer's hand.

public class DealerCardLabel5 {
    private final GameState gameState;

    // EFFECTS: construct the fifth dealer card with the given gameState
    public DealerCardLabel5(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the fifth element of dealer cards is not empty
    // MODIFIES: this
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getDealerCards().getList().get(4);
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

    // MODIFIES: this
    // EFFECTS:  if dealer has 5 or more cards on hand, display the card description of the fifth dealer card; if not,
    // display nothing
    public void display() {
        if (gameState.getDealerCards().getList().size() >= 5) {
            System.out.println("Dealer Card #5: " + cardAdaptor(gameState));
        }
    }
}
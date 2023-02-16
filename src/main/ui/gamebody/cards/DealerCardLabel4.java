package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the fourth card on Dealer's hand.

public class DealerCardLabel4 {
    private final GameState gameState;

    // EFFECTS: construct the fourth dealer card with the given gameState
    public DealerCardLabel4(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the fourth element of dealer cards is not empty
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getDealerCards().getList().get(3);
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

    // EFFECTS:  if dealer has 4 or more cards on hand, display the card description of the fourth dealer card; if not,
    // display nothing
    public void display() {
        if (gameState.getDealerCards().getList().size() >= 4) {
            System.out.println("Dealer Card #4: " + cardAdaptor(gameState));
        }
    }
}

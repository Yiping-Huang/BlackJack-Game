package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the third card on Dealer's hand.

public class DealerCardLabel3 {
    private final GameState gameState;

    // EFFECTS: construct the third dealer card with the given gameState
    public DealerCardLabel3(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the third element of dealer cards is not empty
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getDealerCards().getList().get(2);
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

    // EFFECTS:  if dealer has 3 or more cards on hand, display the card description of the third dealer card; if not,
    // display nothing
    public void display() {
        if (gameState.getDealerCards().getList().size() >= 3) {
            System.out.println("Dealer Card #3: " + cardAdaptor(gameState));
        }
    }
}

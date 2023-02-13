package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the sixth card on Player's hand.

public class PlayerCardLabel6 {
    private final GameState gameState;

    // EFFECTS: construct the sixth player card with the given gameState
    public PlayerCardLabel6(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the sixth element of player cards is not empty
    // MODIFIES: this
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getPlayerCards().getList().get(5);
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
    // EFFECTS:  if player has 6 or more cards on hand, display the card description of the sixth player card; if not,
    // display nothing
    public void display() {
        if (gameState.getPlayerCards().getList().size() >= 6) {
            System.out.println("Player Card #6: " + cardAdaptor(gameState));
        }
    }
}

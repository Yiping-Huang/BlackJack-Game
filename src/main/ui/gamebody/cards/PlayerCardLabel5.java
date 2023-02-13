package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the fifth card on Player's hand.

public class PlayerCardLabel5 {
    private final GameState gameState;

    // EFFECTS: construct the fifth player card with the given gameState
    public PlayerCardLabel5(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the fifth element of player cards is not empty
    // MODIFIES: this
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getPlayerCards().getList().get(4);
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
    // EFFECTS:  if player has 5 or more cards on hand, display the card description of the fifth player card; if not,
    // display nothing
    public void display() {
        if (gameState.getPlayerCards().getList().size() >= 5) {
            System.out.println("Player Card #5: " + cardAdaptor(gameState));
        }
    }
}

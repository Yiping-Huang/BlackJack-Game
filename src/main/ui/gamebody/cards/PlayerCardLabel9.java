package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the ninth card on Player's hand.

public class PlayerCardLabel9 {
    private final GameState gameState;

    // EFFECTS: construct the ninth player card with the given gameState
    public PlayerCardLabel9(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the ninth element of player cards is not empty
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getPlayerCards().getList().get(8);
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

    // EFFECTS:  if player has 9 or more cards on hand, display the card description of the ninth player card; if not,
    // display nothing
    public void display() {
        if (gameState.getPlayerCards().getList().size() >= 9) {
            System.out.println("Player Card #9: " + cardAdaptor(gameState));
        }
    }
}

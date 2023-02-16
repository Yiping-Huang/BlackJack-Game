package ui.gamebody.cards;

import model.Card;
import model.GameState;

// This class is used to represent the seventh card on Player's hand.

public class PlayerCardLabel7 {
    private final GameState gameState;

    // EFFECTS: construct the seventh player card with the given gameState
    public PlayerCardLabel7(GameState gameState) {
        this.gameState = gameState;
    }

    // REQUIRES: the seventh element of player cards is not empty
    // EFFECTS:  adapt the current card info into real-world description of the card
    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getPlayerCards().getList().get(6);
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

    // EFFECTS:  if player has 7 or more cards on hand, display the card description of the seventh player card; if not,
    // display nothing
    public void display() {
        if (gameState.getPlayerCards().getList().size() >= 7) {
            System.out.println("Player Card #7: " + cardAdaptor(gameState));
        }
    }
}

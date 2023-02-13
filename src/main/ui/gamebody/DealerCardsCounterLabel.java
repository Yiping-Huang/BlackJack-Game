package ui.gamebody;

import model.GameState;

// This class is used to display the sum of the face-up cards on dealer's hand

public class DealerCardsCounterLabel {
    private final GameState gameState;

    // EFFECTS:  construct a dealer cards counter label with the given gameState
    public DealerCardsCounterLabel(GameState gamestate) {
        this.gameState = gamestate;
    }

    // MODIFIES: this
    // EFFECTS:  display the sum of the face-up cards on dealer's hand
    public void display() {
        System.out.println("The sum of dealer's face-up cards: " + gameState.getDealerCards().sumFaceUpDealerCards());
    }
}

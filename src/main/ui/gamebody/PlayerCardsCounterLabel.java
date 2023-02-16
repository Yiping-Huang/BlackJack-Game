package ui.gamebody;

import model.GameState;

// This class is used to display the sum of the face-up cards on player's hand

public class PlayerCardsCounterLabel {
    private final GameState gameState;

    // EFFECTS:  construct a player cards counter label with the given gameState
    public PlayerCardsCounterLabel(GameState gamestate) {
        this.gameState = gamestate;
    }

    // EFFECTS:  display the sum of the face-up cards on player's hand
    public void display() {
        System.out.println("The sum of player's face-up cards: " + gameState.getPlayerCards().sumPlayerCards());
    }
}

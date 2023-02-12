package ui.gamebody;

import model.GameState;

public class PlayerCardsCounter {
    private final GameState gameState;

    public PlayerCardsCounter(GameState gamestate) {
        this.gameState = gamestate;
    }

    public void display() {
        System.out.println("The sum of player's face-up cards: " + gameState.getPlayerCards().sumPlayerCards());
    }
}

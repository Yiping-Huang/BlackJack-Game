package ui.gamebody;

import model.GameState;

public class PlayerInfoLabel {
    private final GameState gameState;

    public PlayerInfoLabel(GameState gameState) {
        this.gameState = gameState;
    }

    public void display() {
        System.out.println("\n-------------------------------------");
        System.out.println("Player Name: " + gameState.getPlayerName());
        System.out.println("Player Assets: $" + gameState.getPlayerAssets());
        System.out.println("Player Rounds: " + gameState.getPlayerRounds());
    }
}

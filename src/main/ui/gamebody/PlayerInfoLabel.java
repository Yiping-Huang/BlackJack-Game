package ui.gamebody;

import model.GameState;

// This class represents the display interface of player information; this is used at the game stage

public class PlayerInfoLabel {
    private final GameState gameState;

    // EFFECTS:  construct a player info label with the given gameState
    public PlayerInfoLabel(GameState gameState) {
        this.gameState = gameState;
    }

    // EFFECTS:  display the name, assets, and rounds of current player
    public void display() {
        System.out.println("\n-------------------------------------");
        System.out.println("Player Name: " + gameState.getPlayerName());
        System.out.println("Player Assets: $" + gameState.getPlayerAssets());
        System.out.println("Player Rounds: " + gameState.getPlayerRounds());
    }
}

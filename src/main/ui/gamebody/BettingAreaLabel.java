package ui.gamebody;

import model.GameState;

public class BettingAreaLabel {
    private final GameState gameState;

    public BettingAreaLabel(GameState gameState) {
        this.gameState = gameState;
    }

    public void display() {
        System.out.println("-------------------------------------");
        System.out.println("Bet: $" + gameState.getBettingBox());
        System.out.println("-------------------------------------");
    }
}

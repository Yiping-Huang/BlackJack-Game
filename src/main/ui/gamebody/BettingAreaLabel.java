package ui.gamebody;

import model.GameState;

// This class represents the display interface of the amount of money in the betting box

public class BettingAreaLabel {
    private final GameState gameState;

    // EFFECTS: construct a betting area label with the given gameState
    public BettingAreaLabel(GameState gameState) {
        this.gameState = gameState;
    }

    // MODIFIES: this
    // EFFECTS:  display the amount of money in the betting box
    public void display() {
        System.out.println("-------------------------------------");
        System.out.println("Bet: $" + gameState.getBettingBox());
        System.out.println("-------------------------------------");
    }
}

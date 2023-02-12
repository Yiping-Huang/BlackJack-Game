package ui.gamebody;

import model.GameState;

public class DealerCardsCounter {
    private final GameState gameState;

    public DealerCardsCounter(GameState gamestate) {
        this.gameState = gamestate;
    }

    public void display() {
        System.out.println("The sum of dealer's face-up cards: " + gameState.getDealerCards().sumFaceUpDealerCards());
    }
}

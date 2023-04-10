package ui.gamephases.playercards;

import model.GameState;

// This class is used to represent the first card on Player's hand.

public class PlayerCardJLabel1 extends PlayerCardJLabel {

    // EFFECTS: construct the first player card with the given gameState
    public PlayerCardJLabel1(GameState gameState, int corX, int iniCorY, int corY, int index) {
        super(gameState, corX, iniCorY, corY, index);
    }
}

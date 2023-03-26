package ui.gamephases.cards;

import model.GameState;
import ui.abstractclasses.DealerCardJLabel;

// This class is used to represent the first card on Dealer's hand.

public class DealerCardJLabel1 extends DealerCardJLabel {

    // EFFECTS: construct the first dealer card with the given gameState
    public DealerCardJLabel1(GameState gameState, int corX, int iniCorY, int corY, int index) {
        super(gameState, corX, iniCorY, corY, index);
        super.setCounterMax(40);
    }
}

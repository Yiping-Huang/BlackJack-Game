package ui.gamephases.operationbuttons;

// This class represent a button that will be used to call for hit action.

import ui.abstractclasses.OperationJButton;

public class HitJButton extends OperationJButton {

    // EFFECTS: construct a hit button
    public HitJButton(int corX, int corY) {
        super(corX, corY);
        super.setText("Hit");
    }
}

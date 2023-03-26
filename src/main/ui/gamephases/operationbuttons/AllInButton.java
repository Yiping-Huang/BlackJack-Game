package ui.gamephases.operationbuttons;

// This class represent a button that will be used to call for all-in action.

import ui.abstractclasses.OperationJButton;

public class AllInButton extends OperationJButton {

    // EFFECTS: construct an all-in button
    public AllInButton(int corX, int corY) {
        super(corX, corY);
        super.setText("All In");
    }
}

package ui.gamephases.operationbuttons;

// This class represent a button that will be used to call for stop-betting action.

import ui.abstractclasses.OperationJButton;

public class StopBettingJButton extends OperationJButton {

    // EFFECTS: construct a stop-betting button
    public StopBettingJButton(int corX, int corY) {
        super(corX, corY);
        super.setText("Stop Betting");
    }
}

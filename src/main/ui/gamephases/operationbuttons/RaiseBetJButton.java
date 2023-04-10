package ui.gamephases.operationbuttons;

// This class represent a button that will be used to call for raise-bet action.

public class RaiseBetJButton extends OperationJButton {

    // EFFECTS: construct a raise-bet button
    public RaiseBetJButton(int corX, int corY) {
        super(corX, corY);
        super.setText("Raise Bet");
    }
}

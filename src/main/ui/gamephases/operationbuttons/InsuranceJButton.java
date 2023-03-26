package ui.gamephases.operationbuttons;

// This class represent a button that will be used to call for insurance action.

import ui.abstractclasses.OperationJButton;

public class InsuranceJButton extends OperationJButton {

    // EFFECTS: construct an insurance button
    public InsuranceJButton(int corX, int corY) {
        super(corX, corY);
        super.setText("Insurance");
    }

}

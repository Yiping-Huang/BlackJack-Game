package ui.gamephases.operationbuttons;

// This class represent a button that will be used to call for no-insurance action.

public class SkipInsuranceJButton extends OperationJButton {

    // EFFECTS: construct a no-insurance button
    public SkipInsuranceJButton(int corX, int corY) {
        super(corX, corY);
        super.setText("Skip");
    }
}

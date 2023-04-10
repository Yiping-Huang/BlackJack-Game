package ui.gamephases.operationbuttons;

// This class represent a button that will be used to call for back-to-main-menu action.

public class MainMenuJButton extends OperationJButton {

    // EFFECTS: construct a back-to-main-menu button
    public MainMenuJButton(int corX, int corY) {
        super(corX, corY);
        super.setText("Main Menu");
    }
}

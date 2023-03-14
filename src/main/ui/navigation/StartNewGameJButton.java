package ui.navigation;

import ui.abstractclasses.NavigationJButton;

public class StartNewGameJButton extends NavigationJButton {

    public StartNewGameJButton(int corX, int corY, int iniCorY) {
        super(corX, corY, iniCorY);
        super.setText("Start New Game");
        super.setSize(500,70);
    }
}

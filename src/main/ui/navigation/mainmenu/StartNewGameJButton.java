package ui.navigation.mainmenu;

import ui.navigation.NavigationJButton;

public class StartNewGameJButton extends NavigationJButton {

    public StartNewGameJButton(int corX, int corY, int iniCorY) {
        super(corX, corY, iniCorY);
        super.setText("Start New Game");
        super.setSize(500,70);
    }
}

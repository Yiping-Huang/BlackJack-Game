package ui.navigation.mainmenu;

import ui.navigation.NavigationJButton;

public class ExitGameJButton extends NavigationJButton {

    public ExitGameJButton(int corX, int corY, int iniCorY) {
        super(corX, corY, iniCorY);
        super.setText("Exit Game");
        super.setSize(500,70);
    }
}

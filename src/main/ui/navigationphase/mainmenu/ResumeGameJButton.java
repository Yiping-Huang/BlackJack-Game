package ui.navigationphase.mainmenu;

import ui.abstractclasses.NavigationJButton;

public class ResumeGameJButton extends NavigationJButton {

    public ResumeGameJButton(int corX, int corY, int iniCorY) {
        super(corX, corY, iniCorY);
        super.setText("Resume Game");
        super.setSize(500,70);
    }
}

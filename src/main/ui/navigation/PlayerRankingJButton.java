package ui.navigation;

import ui.abstractclasses.NavigationJButton;

public class PlayerRankingJButton extends NavigationJButton {

    public PlayerRankingJButton(int corX, int corY, int iniCorY) {
        super(corX, corY, iniCorY);
        super.setText("Player Ranking");
        super.setSize(500,70);
    }
}

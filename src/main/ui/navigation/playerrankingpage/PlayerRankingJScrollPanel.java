package ui.navigation.playerrankingpage;

import javax.swing.*;

public class PlayerRankingJScrollPanel extends JScrollPane {

    public PlayerRankingJScrollPanel(JList<String> list) {
        setBounds(0,0,500,480);
        setSize(500,480);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setViewportView(list);
    }
}

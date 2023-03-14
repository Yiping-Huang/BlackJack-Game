package ui.navigation.resumegame;

import javax.swing.*;

public class GameRecordsJScrollPanel extends JScrollPane {

    public GameRecordsJScrollPanel(JList<String> list) {
        setBounds(0,0,500,380);
        setSize(500,380);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setViewportView(list);
    }
}

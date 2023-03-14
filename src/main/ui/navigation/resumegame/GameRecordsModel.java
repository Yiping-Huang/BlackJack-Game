package ui.navigation.resumegame;

import model.GameRecords;
import model.Player;

import javax.swing.*;
import java.util.ArrayList;

// This class represents the display interface of Game Records Panel. This panel display the existing game records.

public class GameRecordsModel {

    // REQUIRES: gameRecords is not empty
    // EFFECTS:  construct the game record panel with the given gameRecords and call to display the content
    public GameRecordsModel() {

    }

    public DefaultListModel<String> getGameRecordsModel(GameRecords gameRecords) {
        ArrayList<Player> originalList = gameRecords.getList();
        ArrayList<Player> gameRecordsList = new ArrayList<>(originalList);

        DefaultListModel<String> model = new DefaultListModel<>();

        int recordIndex = 1;
        for (Player p: gameRecordsList) {
            String item = "<html><body><br>" + "\n&nbsp;&nbsp;&nbsp;&nbsp;Game Record Index #" + recordIndex;
            recordIndex++;
            item = item + "\n<br>&nbsp;&nbsp;&nbsp;&nbsp;Player Name: " + p.getName()
                    + "\n<br>&nbsp;&nbsp;&nbsp;&nbsp;Player Assets: $" + p.getAssets()
                    + "\n<br>&nbsp;&nbsp;&nbsp;&nbsp;Player Rounds: " + p.getRounds()
                    + "<br></body></html>";
            model.addElement(item);
        }
        return model;
    }
}


package ui;

import model.GameRecords;
import model.Player;

// This class represents the display interface of Game Records Panel. This panel display the existing game records.

public class GameRecordsPanel {

    // REQUIRES: gameRecords is not empty
    // EFFECTS:  construct the game record panel with the given gameRecords and call to display the content
    public GameRecordsPanel(GameRecords gameRecords) {
        displayGameRecords(gameRecords);
    }

    // REQUIRES: gameRecords is not empty
    // MODIFIES: this
    // EFFECTS:  display the existing game records followed the time order
    public void displayGameRecords(GameRecords gameRecords) {
        int recordIndex = 1;
        System.out.println("\n-------------------------------------");
        System.out.println("Game Records");
        System.out.println("-------------------------------------");
        for (Player p : gameRecords.getList()) {
            System.out.println("\nGame Record Index " + recordIndex);
            System.out.println("Player Name: " + p.getName());
            System.out.println("Player Assets: $" + p.getAssets());
            System.out.println("Player Rounds: " + p.getRounds());
            recordIndex++;
        }
        System.out.println("\nSelect from:");
        System.out.println("\tl -> Load Game Record");
        System.out.println("\td -> Delete Game Record");
        System.out.println("\tb -> Back to the Main Menu");
    }
}


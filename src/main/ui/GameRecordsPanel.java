package ui;

import model.GameRecords;
import model.Player;

public class GameRecordsPanel {

    public GameRecordsPanel(GameRecords gameRecords) {
        displayGameRecords(gameRecords);
    }

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


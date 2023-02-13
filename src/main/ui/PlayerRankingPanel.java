package ui;

import model.GameRecords;
import model.Player;

import java.util.ArrayList;

// This class represent the display interface of Player Ranking. This is a ranking board that rank the players
// on records according to their amount of assets from highest to the lowest.

public class PlayerRankingPanel {

    // REQUIRES: gameRecords is not empty
    // EFFECTS:  construct a player ranking panel with the given gameRecords and call to display the content
    public PlayerRankingPanel(GameRecords gameRecords) {
        displayPlayerRanking(gameRecords);
    }

    // REQUIRES: gameRecords is not empty
    // MODIFIES: this
    // EFFECTS:  sort the gameRecords according to the amount of assets of each player, display the result, and give the
    // option to go back to the Main Menu
    public void displayPlayerRanking(GameRecords gameRecords) {
        ArrayList<Player> originalList = gameRecords.getList();
        ArrayList<Player> playerRanking = new ArrayList<>(originalList);
        playerRanking.sort((p1, p2) -> p2.getAssets() - p1.getAssets());
        System.out.println("\n-------------------------------------");
        System.out.println("Player Ranking");
        System.out.println("-------------------------------------");
        int playerRank = 1;
        for (Player p: playerRanking) {
            System.out.println("\nPlayer Rank #" + playerRank);
            playerRank++;
            System.out.println("Player Name: " + p.getName());
            System.out.println("Player Assets: $" + p.getAssets());
            System.out.println("Player Rounds: " + p.getRounds());
        }

        System.out.println("\nb -> Back to the Main Menu");
    }
}

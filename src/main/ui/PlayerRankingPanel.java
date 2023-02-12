package ui;

import model.GameRecords;
import model.Player;

import java.util.ArrayList;

public class PlayerRankingPanel {

    public PlayerRankingPanel(GameRecords gameRecords) {
        displayPlayerRanking(gameRecords);
    }

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

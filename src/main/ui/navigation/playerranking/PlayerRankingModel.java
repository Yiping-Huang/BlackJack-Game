package ui.navigation.playerranking;

import model.GameRecords;
import model.Player;

import javax.swing.*;
import java.util.ArrayList;

// This class represent the display interface of Player Ranking. This is a ranking board that rank the players
// on records according to their amount of assets from highest to the lowest.

public class PlayerRankingModel {

    // REQUIRES: gameRecords is not empty
    // EFFECTS:  construct a player ranking panel with the given gameRecords and call to display the content
    public PlayerRankingModel() {}

    public DefaultListModel<String> getPlayerRankingModel(GameRecords gameRecords) {
        ArrayList<Player> originalList = gameRecords.getList();
        ArrayList<Player> playerRanking = new ArrayList<>(originalList);
        playerRanking.sort((p1, p2) -> p2.getAssets() - p1.getAssets());

        DefaultListModel<String> model = new DefaultListModel<>();

        int playerRank = 1;
        for (Player p: playerRanking) {
            String item = "<html><body><br>" + "\n&nbsp;&nbsp;&nbsp;&nbsp;Player Rank #" + playerRank;
            playerRank++;
            item = item + "\n<br>&nbsp;&nbsp;&nbsp;&nbsp;Player Name: " + p.getName()
                    + "\n<br>&nbsp;&nbsp;&nbsp;&nbsp;Player Assets: $" + p.getAssets()
                    + "\n<br>&nbsp;&nbsp;&nbsp;&nbsp;Player Rounds: " + p.getRounds()
                    + "<br></body></html>";
            model.addElement(item);
        }
        return model;
    }
}

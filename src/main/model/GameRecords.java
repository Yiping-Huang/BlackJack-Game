package model;

import java.util.ArrayList;

// This class represents the past game records saved by previous players which is a list of player.

public class GameRecords {
    private static ArrayList<Player> gameRecords;

    // EFFECTS: construct an empty list as the gameRecords
    public GameRecords() {
        gameRecords = new ArrayList<>();
    }

    public ArrayList<Player> getList() {
        return gameRecords;
    }

    public Player getRecord(int recordIndex) {
        return gameRecords.get(recordIndex);
    }

    // MODIFIES: this
    // EFFECTS:  add a new player to the list
    public void addRecord(Player player) {
        gameRecords.add(player);
    }

    // REQUIRES: recordIndex is smaller than the length of the list
    // MODIFIES: this
    // EFFECTS:  remove a specific player from the list according to the recordIndex
    public void deleteRecord(int recordIndex) {
        gameRecords.remove(recordIndex);
    }

    // REQUIRES: currentGameRecordIndex is smaller than the length of the list
    // MODIFIES: this
    // EFFECTS:  replace the old player with a new player of the same name
    public void saveOldRecord(int currentGameRecordIndex, Player player) {
        gameRecords.set(currentGameRecordIndex, player);
    }
}

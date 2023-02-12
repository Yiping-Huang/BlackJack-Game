package model;

import java.util.ArrayList;

public class GameRecords {
    private static ArrayList<Player> gameRecords;

    public GameRecords() {
        gameRecords = new ArrayList<>();
    }

    public ArrayList<Player> getList() {
        return gameRecords;
    }

    public Player getRecord(int recordIndex) {
        return gameRecords.get(recordIndex);
    }

    public void addRecord(Player player) {
        gameRecords.add(player);
    }

    public void deleteRecord(int recordIndex) {
        gameRecords.remove(recordIndex);
    }

    public void saveOldRecord(int currentGameRecordIndex, Player player) {
        gameRecords.set(currentGameRecordIndex, player);
    }
}

package model;

import java.util.ArrayList;
import java.util.Iterator;

// This class represents the past game records saved by previous players which is a list of player.

public class GameRecords implements Iterable<Player> {
    private static ArrayList<Player> players;

    // EFFECTS: construct an empty list as the gameRecords
    public GameRecords() {
        players = new ArrayList<>();
    }

    public ArrayList<Player> getList() {
        return players;
    }

    public Player getRecord(int recordIndex) {
        return players.get(recordIndex);
    }

    // MODIFIES: this
    // EFFECTS:  add a new player to the list
    public void addRecord(Player player) {
        players.add(player);
    }

    // REQUIRES: recordIndex is smaller than the length of the list
    // MODIFIES: this
    // EFFECTS:  remove a specific player from the list according to the recordIndex
    public void deleteRecord(int recordIndex) {
        players.remove(recordIndex);
    }

    // REQUIRES: currentGameRecordIndex is smaller than the length of the list
    // MODIFIES: this
    // EFFECTS:  replace the old player with a new player of the same name
    public void saveOldRecord(int currentGameRecordIndex, Player player) {
        players.set(currentGameRecordIndex, player);
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }
}

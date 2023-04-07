package persistence;

import model.GameRecords;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {

    private final String source;
    private final GameRecords gameRecords;

    // REQUIRES: source is a valid source of file
    // EFFECTS:  construct a JsonWriter with the given gameRecords and source
    public JsonWriter(GameRecords gameRecords, String source) {
        this.gameRecords = gameRecords;
        this.source = source;
    }

    // EFFECTS:  transfer the given gameRecords into Jason String, write this Sting in the file according to the
    // given source, and return true for the test purpose; if the source is not a valid source of file, throw
    // IOException, catch it and return false for the test purpose
    public Boolean write() {
        try {
            JSONArray jsonArray = new JSONArray();

            for (Player player : gameRecords) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Player Name", player.getName());
                jsonObject.put("Player Assets", player.getAssets());
                jsonObject.put("Player Rounds", player.getRounds());
                jsonArray.put(jsonObject);
            }

            FileWriter file = new FileWriter(source);
            file.write(jsonArray.toString());
            file.close();
            return true;
        } catch (IOException e) {
            System.out.println("File source is not valid.");
            return false;
        }
    }
}
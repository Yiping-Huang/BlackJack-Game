package persistence;

import java.io.FileReader;
import java.io.IOException;

import model.GameRecords;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonReader {

    private final String source;

    // REQUIRES: source is a valid source of file
    // EFFECTS:  construct a JsonReader with the given source
    public JsonReader(String source) {
        this.source = source;
    }

    // MODIFIES: this
    // EFFECTS:  read the file according to the given source, transfer the JavaString into a GameRecords, and return
    // the corresponding GameRecords; if the source is not valid, throw IOException, catch it, and return an empty
    // GameRecords
    public GameRecords getGameRecords() throws IOException {
        try {
            FileReader reader = new FileReader(source);
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            String jsonString = stringBuilder.toString();

            JSONArray jsonArray = new JSONArray(jsonString);

            GameRecords gameRecords = new GameRecords();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String playerName = jsonObject.getString("Player Name");
                int playerAssets = jsonObject.getInt("Player Assets");
                int playerRounds = jsonObject.getInt("Player Rounds");
                gameRecords.addRecord(new Player(playerName, playerAssets, playerRounds));
            }
            return gameRecords;
        } catch (IOException e) {
            return new GameRecords();
        }
    }
}

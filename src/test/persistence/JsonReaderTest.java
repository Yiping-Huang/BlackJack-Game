package persistence;

import model.GameRecords;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testGetGameRecordsNoException() {
        try {
            JsonReader jsonReader = new JsonReader("./data/testJsonReaderGetGameRecords.json");
            GameRecords restoredGameRecords = jsonReader.getGameRecords();
            Player restoredPlayer1 = restoredGameRecords.getRecord(0);
            Player restoredPlayer2 = restoredGameRecords.getRecord(1);
            assertEquals("Alice", restoredPlayer1.getName());
            assertEquals(1200, restoredPlayer1.getAssets());
            assertEquals(5, restoredPlayer1.getRounds());
            assertEquals("Frank", restoredPlayer2.getName());
            assertEquals(1000, restoredPlayer2.getAssets());
            assertEquals(4, restoredPlayer2.getRounds());
        } catch (IOException e) {
            fail("IOException should not be caught here.");
        }
    }

    @Test
    void testGetGameRecordsException() {
        try {
            GameRecords restoredGameRecords = new JsonReader("./data/wrongFilename.json").getGameRecords();
            assertTrue(restoredGameRecords.getList().isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

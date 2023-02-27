package persistence;

import model.GameRecords;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    private GameRecords gameRecords;

    @BeforeEach
    void runBefore() {
        Player player1 = new Player("Alice", 1200, 5);
        Player player2 = new Player("Frank", 1000, 4);
        gameRecords = new GameRecords();
        gameRecords.addRecord(player1);
        gameRecords.addRecord(player2);
    }

    @Test
    void testConstructorNoException() {
        try {
            Boolean writingStatus = new JsonWriter(gameRecords, "./data/testJsonWriterConstructor.json").write();
            JsonReader jsonReader = new JsonReader("./data/testJsonWriterConstructor.json");
            GameRecords restoredGameRecords = jsonReader.getGameRecords();
            Player restoredPlayer1 = restoredGameRecords.getRecord(0);
            Player restoredPlayer2 = restoredGameRecords.getRecord(1);
            assertEquals("Alice", restoredPlayer1.getName());
            assertEquals(1200, restoredPlayer1.getAssets());
            assertEquals(5, restoredPlayer1.getRounds());
            assertEquals("Frank", restoredPlayer2.getName());
            assertEquals(1000, restoredPlayer2.getAssets());
            assertEquals(4, restoredPlayer2.getRounds());
            assertTrue(writingStatus);
        } catch (IOException e) {
            fail("IOException should not be caught here.");
        }
    }

    @Test
    void testConstructorException() {
        Boolean writingStatus = new JsonWriter(gameRecords, "./data/wrong\0Filename.json").write();
        assertFalse(writingStatus);
    }
}

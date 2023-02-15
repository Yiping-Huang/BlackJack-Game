package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameRecordsTest {
    private GameRecords gameRecords;

    @BeforeEach
    void runBefore() {
        gameRecords = new GameRecords();
    }

    @Test
    void testConstructor() {
        assertEquals(gameRecords.getList().getClass(), ArrayList.class);
        assertNotNull(gameRecords.getList());
        assertEquals(0, gameRecords.getList().size());
    }

    @Test
    void testGetList() {
        assertEquals(new ArrayList<>(), gameRecords.getList());
    }

    @Test
    void testGetRecord() {
        gameRecords.addRecord(new Player("Francis", 10000, 0));
        gameRecords.addRecord(new Player("Alice", 20000, 25));
        Player p = gameRecords.getRecord(0);
        assertEquals("Francis", p.getName());
        assertEquals(10000, p.getAssets());
        assertEquals(0, p.getRounds());
    }

    @Test
    void testAddRecord() {
        gameRecords.addRecord(new Player("Francis", 10000, 0));
        assertEquals(1, gameRecords.getList().size());
        Player p = gameRecords.getRecord(0);
        assertEquals("Francis", p.getName());
        assertEquals(10000, p.getAssets());
        assertEquals(0, p.getRounds());
    }

    @Test
    void testDeleteRecord() {
        gameRecords.addRecord(new Player("Francis", 10000, 0));
        gameRecords.addRecord(new Player("Alice", 20000, 25));
        assertEquals(2, gameRecords.getList().size());
        gameRecords.deleteRecord(0);
        assertEquals(1, gameRecords.getList().size());
        Player p = gameRecords.getRecord(0);
        assertEquals("Alice", p.getName());
        assertEquals(20000, p.getAssets());
        assertEquals(25, p.getRounds());
    }

    @Test
    void testSaveOldRecord() {
        gameRecords.addRecord(new Player("Francis", 10000, 0));
        gameRecords.saveOldRecord(0, new Player("Frank", 500, 10));
        assertEquals("Frank", gameRecords.getList().get(0).getName());
        assertEquals(500, gameRecords.getList().get(0).getAssets());
        assertEquals(10, gameRecords.getList().get(0).getRounds());
    }
}

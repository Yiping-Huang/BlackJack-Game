package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void runBefore() {
        player = new Player("Francis", 10000, 0);
    }

    @Test
    void testConstructor() {
        assertEquals("Francis", player.getName());
        assertEquals(10000, player.getAssets());
        assertEquals(0, player.getRounds());
    }

    @Test
    void testGetName() {
        assertEquals("Francis", player.getName());
    }

    @Test
    void testGetAssets() {
        assertEquals(10000, player.getAssets());
    }

    @Test
    void testGetRounds() {
        assertEquals(0, player.getRounds());
    }

    @Test
    void testSetName() {
        player.setName("Frank");
        assertEquals("Frank", player.getName());
    }

    @Test
    void testSetAssets() {
        player.setAssets(20000);
        assertEquals(20000, player.getAssets());
    }

    @Test
    void testSetRounds() {
        player.setRounds(10);
        assertEquals(10, player.getRounds());
    }
}

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCardsTest {
    private PlayerCards playerCards;

    @BeforeEach
    void runBefore() {
        playerCards = new PlayerCards();
    }

    @Test
    void testConstructor() {
        assertEquals(playerCards.getList().getClass(), ArrayList.class);
        assertTrue(playerCards.getList().isEmpty());
    }

    @Test
    void testGetList() {
        assertEquals(new ArrayList<Card>(), playerCards.getList());
    }

    @Test
    void testAddPlayerCardOnce() {
        playerCards.addPlayerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(1, playerCards.getList().get(0).getCardNum());
        assertEquals("Diamonds", playerCards.getList().get(0).getCardSuit());
        assertEquals("face-up", playerCards.getList().get(0).getCardStatus());
        assertEquals(1,playerCards.getList().size());
    }

    @Test
    void testAddPlayerCardMultipleTimes() {
        playerCards.addPlayerCard(new Card(1, "Hearts", "face-up"));
        assertEquals(1, playerCards.getList().get(0).getCardNum());
        assertEquals("Hearts", playerCards.getList().get(0).getCardSuit());
        assertEquals("face-up", playerCards.getList().get(0).getCardStatus());
        assertEquals(1,playerCards.getList().size());
        playerCards.addPlayerCard(new Card(2, "Hearts", "face-up"));
        assertEquals(2, playerCards.getList().get(1).getCardNum());
        assertEquals("Hearts", playerCards.getList().get(1).getCardSuit());
        assertEquals("face-up", playerCards.getList().get(1).getCardStatus());
        assertEquals(2,playerCards.getList().size());
        playerCards.addPlayerCard(new Card(3, "Hearts", "face-up"));
        assertEquals(3, playerCards.getList().get(2).getCardNum());
        assertEquals("Hearts", playerCards.getList().get(2).getCardSuit());
        assertEquals("face-up", playerCards.getList().get(2).getCardStatus());
        assertEquals(3,playerCards.getList().size());
    }

    @Test
    void testClearPlayerCards() {
        playerCards.addPlayerCard(new Card(1, "Diamonds", "face-up"));
        playerCards.addPlayerCard(new Card(2, "Diamonds", "face-up"));
        playerCards.addPlayerCard(new Card(3, "Diamonds", "face-up"));
        assertEquals(3,playerCards.getList().size());
        playerCards.clearPlayerCards();
        assertTrue(playerCards.getList().isEmpty());
    }

    @Test
    void testSumPlayerCardsOnce() {
        playerCards.addPlayerCard(new Card(7, "Diamonds", "face-up"));
        assertEquals(7, playerCards.sumPlayerCards());
    }

    @Test
    void testSumPlayerCardsTwice() {
        playerCards.addPlayerCard(new Card(10, "Diamonds", "face-up"));
        assertEquals(10, playerCards.sumPlayerCards());
        playerCards.addPlayerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(21, playerCards.sumPlayerCards());
    }

    @Test
    void testSumPlayerCardsMultipleTimes() {
        playerCards.addPlayerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(11, playerCards.sumPlayerCards());
        playerCards.addPlayerCard(new Card(1, "Hearts", "face-up"));
        assertEquals(12, playerCards.sumPlayerCards());
        playerCards.addPlayerCard(new Card(11, "Diamonds", "face-up"));
        assertEquals(12, playerCards.sumPlayerCards());
        playerCards.addPlayerCard(new Card(1, "Clubs", "face-up"));
        assertEquals(13, playerCards.sumPlayerCards());
    }

    @Test
    void testBustOrNot() {
        playerCards.addPlayerCard(new Card(10, "Diamonds", "face-up"));
        assertFalse(playerCards.bustOrNot());
        playerCards.addPlayerCard(new Card(1, "Diamonds", "face-up"));
        assertFalse(playerCards.bustOrNot());
        playerCards.addPlayerCard(new Card(5, "Diamonds", "face-up"));
        assertFalse(playerCards.bustOrNot());
        playerCards.addPlayerCard(new Card(8, "Diamonds", "face-up"));
        assertTrue(playerCards.bustOrNot());
    }
}

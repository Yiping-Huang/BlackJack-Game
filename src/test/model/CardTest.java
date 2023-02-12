package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    private Card card1;
    private Card card2;

    @BeforeEach
    void runBefore() {
        card1 = new Card(13, "Diamonds", "face-up");
        card2 = new Card(8, "Hearts", "face-up");
    }

    @Test
    void testConstructor() {
        assertEquals(13, card1.getCardNum());
        assertEquals("Diamonds", card1.getCardSuit());
        assertEquals("face-up", card1.getCardStatus());
    }

    @Test
    void testGetCardNum() {
        assertEquals(13, card1.getCardNum());
    }

    @Test
    void testGetCardSuit() {
        assertEquals("Diamonds", card1.getCardSuit());
    }

    @Test
    void testGetCardStatus() {
        assertEquals("face-up", card1.getCardStatus());
    }

    @Test
    void testGetCardValue() {
        assertEquals(10, card1.getCardValue());
        assertEquals(8, card2.getCardValue());
    }

    @Test
    void testSetCardStatus() {
        card1.setCardStatus("face-down");
        assertEquals("face-down", card1.getCardStatus());
    }
}

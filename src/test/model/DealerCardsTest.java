package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DealerCardsTest {
    private DealerCards dealerCards;

    @BeforeEach
    void runBefore() {
        dealerCards = new DealerCards();
    }

    @Test
    void testConstructor() {
        assertEquals(dealerCards.getList().getClass(), ArrayList.class);
        assertTrue(dealerCards.getList().isEmpty());
    }

    @Test
    void testGetList() {
        assertEquals(new ArrayList<Card>(), dealerCards.getList());
    }

    @Test
    void testAddDealerCardOnce() {
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(1, dealerCards.getList().get(0).getCardNum());
        assertEquals("Diamonds", dealerCards.getList().get(0).getCardSuit());
        assertEquals("face-up", dealerCards.getList().get(0).getCardStatus());
        assertEquals(1, dealerCards.getList().size());
    }

    @Test
    void testAddDealerCardMultipleTimes() {
        dealerCards.addDealerCard(new Card(1, "Hearts", "face-up"));
        assertEquals(1, dealerCards.getList().get(0).getCardNum());
        assertEquals("Hearts", dealerCards.getList().get(0).getCardSuit());
        assertEquals("face-up", dealerCards.getList().get(0).getCardStatus());
        assertEquals(1, dealerCards.getList().size());
        dealerCards.addDealerCard(new Card(2, "Hearts", "face-up"));
        assertEquals(2, dealerCards.getList().get(1).getCardNum());
        assertEquals("Hearts", dealerCards.getList().get(1).getCardSuit());
        assertEquals("face-up", dealerCards.getList().get(1).getCardStatus());
        assertEquals(2, dealerCards.getList().size());
        dealerCards.addDealerCard(new Card(3, "Hearts", "face-up"));
        assertEquals(3, dealerCards.getList().get(2).getCardNum());
        assertEquals("Hearts", dealerCards.getList().get(2).getCardSuit());
        assertEquals("face-up", dealerCards.getList().get(2).getCardStatus());
        assertEquals(3, dealerCards.getList().size());
    }

    @Test
    void testClearDealerCards() {
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        dealerCards.addDealerCard(new Card(2, "Diamonds", "face-up"));
        dealerCards.addDealerCard(new Card(3, "Diamonds", "face-up"));
        assertEquals(3, dealerCards.getList().size());
        dealerCards.clearDealerCards();
        assertTrue(dealerCards.getList().isEmpty());
    }

    @Test
    void testSetSecondCardStatus() {
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        dealerCards.addDealerCard(new Card(2, "Diamonds", "face-up"));
        dealerCards.addDealerCard(new Card(3, "Diamonds", "face-up"));
        dealerCards.setSecondCardStatus("face-down");
        assertEquals("face-down", dealerCards.getList().get(1).getCardStatus());
    }

    @Test
    void testSumDealerCardsOnce() {
        dealerCards.addDealerCard(new Card(7, "Diamonds", "face-up"));
        assertEquals(7, dealerCards.sumDealerCards());
    }

    @Test
    void testSumDealerCardsTwice() {
        dealerCards.addDealerCard(new Card(10, "Diamonds", "face-up"));
        assertEquals(10, dealerCards.sumDealerCards());
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(21, dealerCards.sumDealerCards());
    }

    @Test
    void testSumDealerCardsMultipleTimes() {
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(11, dealerCards.sumDealerCards());
        dealerCards.addDealerCard(new Card(1, "Hearts", "face-up"));
        assertEquals(12, dealerCards.sumDealerCards());
        dealerCards.addDealerCard(new Card(11, "Diamonds", "face-up"));
        assertEquals(12, dealerCards.sumDealerCards());
        dealerCards.addDealerCard(new Card(1, "Clubs", "face-up"));
        assertEquals(13, dealerCards.sumDealerCards());
    }

    @Test
    void testSumFaceUpDealerCardsOnce1() {
        dealerCards.addDealerCard(new Card(7, "Diamonds", "face-down"));
        assertEquals(0, dealerCards.sumFaceUpDealerCards());
    }

    @Test
    void testSumFaceUpDealerCardsOnce2() {
        dealerCards.addDealerCard(new Card(7, "Diamonds", "face-up"));
        assertEquals(7, dealerCards.sumFaceUpDealerCards());
    }

    @Test
    void testSumFaceUpDealerCardsTwice1() {
        dealerCards.addDealerCard(new Card(8, "Diamonds", "face-up"));
        assertEquals(8, dealerCards.sumFaceUpDealerCards());
        dealerCards.addDealerCard(new Card(7, "Diamonds", "face-down"));
        assertEquals(8, dealerCards.sumFaceUpDealerCards());
    }

    @Test
    void testSumFaceUpDealerCardsTwice2() {
        dealerCards.addDealerCard(new Card(8, "Diamonds", "face-up"));
        assertEquals(8, dealerCards.sumFaceUpDealerCards());
        dealerCards.addDealerCard(new Card(7, "Diamonds", "face-up"));
        assertEquals(15, dealerCards.sumFaceUpDealerCards());
    }

    @Test
    void testSumFaceUpDealerCardsMultipleTimes1() {
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(11, dealerCards.sumFaceUpDealerCards());
        dealerCards.addDealerCard(new Card(1, "Hearts", "face-down"));
        assertEquals(11, dealerCards.sumFaceUpDealerCards());
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(12, dealerCards.sumFaceUpDealerCards());
        dealerCards.addDealerCard(new Card(1, "Clubs", "face-down"));
        assertEquals(12, dealerCards.sumFaceUpDealerCards());
    }

    @Test
    void testSumFaceUpDealerCardsMultipleTimes2() {
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        assertEquals(11, dealerCards.sumFaceUpDealerCards());
        dealerCards.addDealerCard(new Card(1, "Hearts", "face-up"));
        assertEquals(12, dealerCards.sumFaceUpDealerCards());
        dealerCards.addDealerCard(new Card(11, "Diamonds", "face-up"));
        assertEquals(12, dealerCards.sumFaceUpDealerCards());
        dealerCards.addDealerCard(new Card(1, "Clubs", "face-up"));
        assertEquals(13, dealerCards.sumFaceUpDealerCards());
    }

    @Test
    void testBustOrNot() {
        dealerCards.addDealerCard(new Card(10, "Diamonds", "face-up"));
        assertFalse(dealerCards.bustOrNot());
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        assertFalse(dealerCards.bustOrNot());
        dealerCards.addDealerCard(new Card(5, "Diamonds", "face-up"));
        assertFalse(dealerCards.bustOrNot());
        dealerCards.addDealerCard(new Card(8, "Diamonds", "face-up"));
        assertTrue(dealerCards.bustOrNot());
    }
}

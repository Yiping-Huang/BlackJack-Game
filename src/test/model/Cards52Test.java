package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class Cards52Test {
    private Cards52 cards52;

    @BeforeEach
    void runBefore() {
        cards52 = new Cards52();
    }

    @Test
    void testConstructor() {
        ArrayList<Card> cardsSet = new ArrayList<>();
        cardsSet.add(new Card(1,"Diamonds","face-up"));
        cardsSet.add(new Card(2,"Diamonds","face-up"));
        cardsSet.add(new Card(3,"Diamonds","face-up"));
        cardsSet.add(new Card(4,"Diamonds","face-up"));
        cardsSet.add(new Card(5,"Diamonds","face-up"));
        cardsSet.add(new Card(6,"Diamonds","face-up"));
        cardsSet.add(new Card(7,"Diamonds","face-up"));
        cardsSet.add(new Card(8,"Diamonds","face-up"));
        cardsSet.add(new Card(9,"Diamonds","face-up"));
        cardsSet.add(new Card(10,"Diamonds","face-up"));
        cardsSet.add(new Card(11,"Diamonds","face-up"));
        cardsSet.add(new Card(12,"Diamonds","face-up"));
        cardsSet.add(new Card(13,"Diamonds","face-up"));
        cardsSet.add(new Card(1,"Clubs","face-up"));
        cardsSet.add(new Card(2,"Clubs","face-up"));
        cardsSet.add(new Card(3,"Clubs","face-up"));
        cardsSet.add(new Card(4,"Clubs","face-up"));
        cardsSet.add(new Card(5,"Clubs","face-up"));
        cardsSet.add(new Card(6,"Clubs","face-up"));
        cardsSet.add(new Card(7,"Clubs","face-up"));
        cardsSet.add(new Card(8,"Clubs","face-up"));
        cardsSet.add(new Card(9,"Clubs","face-up"));
        cardsSet.add(new Card(10,"Clubs","face-up"));
        cardsSet.add(new Card(11,"Clubs","face-up"));
        cardsSet.add(new Card(12,"Clubs","face-up"));
        cardsSet.add(new Card(13,"Clubs","face-up"));
        cardsSet.add(new Card(1,"Hearts","face-up"));
        cardsSet.add(new Card(2,"Hearts","face-up"));
        cardsSet.add(new Card(3,"Hearts","face-up"));
        cardsSet.add(new Card(4,"Hearts","face-up"));
        cardsSet.add(new Card(5,"Hearts","face-up"));
        cardsSet.add(new Card(6,"Hearts","face-up"));
        cardsSet.add(new Card(7,"Hearts","face-up"));
        cardsSet.add(new Card(8,"Hearts","face-up"));
        cardsSet.add(new Card(9,"Hearts","face-up"));
        cardsSet.add(new Card(10,"Hearts","face-up"));
        cardsSet.add(new Card(11,"Hearts","face-up"));
        cardsSet.add(new Card(12,"Hearts","face-up"));
        cardsSet.add(new Card(13,"Hearts","face-up"));
        cardsSet.add(new Card(1,"Spades","face-up"));
        cardsSet.add(new Card(2,"Spades","face-up"));
        cardsSet.add(new Card(3,"Spades","face-up"));
        cardsSet.add(new Card(4,"Spades","face-up"));
        cardsSet.add(new Card(5,"Spades","face-up"));
        cardsSet.add(new Card(6,"Spades","face-up"));
        cardsSet.add(new Card(7,"Spades","face-up"));
        cardsSet.add(new Card(8,"Spades","face-up"));
        cardsSet.add(new Card(9,"Spades","face-up"));
        cardsSet.add(new Card(10,"Spades","face-up"));
        cardsSet.add(new Card(11,"Spades","face-up"));
        cardsSet.add(new Card(12,"Spades","face-up"));
        cardsSet.add(new Card(13,"Spades","face-up"));
        assertEquals(cardsSet.size(), cards52.getList().size());
        assertEquals(52, cards52.getList().size());
        Iterator<Card> iterator1 = cardsSet.iterator();
        Iterator<Card> iterator2 = cards52.getList().iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            Card c1 = iterator1.next();
            Card c2 = iterator2.next();
            assertEquals(c1.getCardNum(), c2.getCardNum());
            assertEquals(c1.getCardSuit(), c2.getCardSuit());
            assertEquals(c1.getCardStatus(), c2.getCardStatus());
        }
    }

    @Test
    void testPickRandomCardOnce() {
        assertEquals(cards52.pickRandomCard().getClass(), Card.class);
        assertEquals(51, cards52.getList().size());
    }

    @Test
    void testPickRandomCardMultipleTimes() {
        assertEquals(cards52.pickRandomCard().getClass(), Card.class);
        assertEquals(51, cards52.getList().size());
        assertEquals(cards52.pickRandomCard().getClass(), Card.class);
        assertEquals(50, cards52.getList().size());
        assertEquals(cards52.pickRandomCard().getClass(), Card.class);
        assertEquals(49, cards52.getList().size());
    }
}
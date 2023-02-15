package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {
    private GameState gameState;
    private DealerCards dealerCards;
    private PlayerCards playerCards;
    private Cards52 cardsPool;


    @BeforeEach
    void runBefore() {
        dealerCards = new DealerCards();
        playerCards = new PlayerCards();
        cardsPool = new Cards52();
        dealerCards.addDealerCard(cardsPool.pickRandomCard());
        dealerCards.addDealerCard(cardsPool.pickRandomCard());
        dealerCards.addDealerCard(cardsPool.pickRandomCard());
        playerCards.addPlayerCard(cardsPool.pickRandomCard());
        playerCards.addPlayerCard(cardsPool.pickRandomCard());
        playerCards.addPlayerCard(cardsPool.pickRandomCard());
        gameState = new GameState("Francis", 10000, 10, 500,
                dealerCards, playerCards, cardsPool);
    }

    @Test
    void testConstructor() {
        assertEquals("Francis", gameState.getPlayerName());
        assertEquals(10000, gameState.getPlayerAssets());
        assertEquals(10, gameState.getPlayerRounds());
        assertEquals(500, gameState.getBettingBox());
        assertEquals(dealerCards, gameState.getDealerCards());
        assertNotNull(gameState.getDealerCards().getList());
        assertEquals(3, gameState.getDealerCards().getList().size());
        assertEquals(playerCards, gameState.getPlayerCards());
        assertNotNull(gameState.getPlayerCards().getList());
        assertEquals(3, gameState.getPlayerCards().getList().size());
        assertEquals(cardsPool, gameState.getCardsPool());
        assertNotNull(gameState.getCardsPool().getList());
        assertEquals(46, gameState.getCardsPool().getList().size());
    }

    @Test
    void testGetPlayerName() {
        assertEquals("Francis", gameState.getPlayerName());
    }

    @Test
    void testGetPlayerAssets() {
        assertEquals(10000, gameState.getPlayerAssets());
    }

    @Test
    void testGetPlayerRounds() {
        assertEquals(10, gameState.getPlayerRounds());
    }

    @Test
    void testGetBettingBox() {
        assertEquals(500, gameState.getBettingBox());
    }

    @Test
    void testGetDealerCards() {
        assertEquals(dealerCards, gameState.getDealerCards());
    }

    @Test
    void testGetPlayerCards() {
        assertEquals(playerCards, gameState.getPlayerCards());
    }

    @Test
    void testGetCardsPool() {
        assertEquals(cardsPool, gameState.getCardsPool());
    }

    @Test
    void testSetPlayerName() {
        gameState.setPlayerName("Frank");
        assertEquals("Frank", gameState.getPlayerName());
    }

    @Test
    void testSetPlayerAssets() {
        gameState.setPlayerAssets(20000);
        assertEquals(20000, gameState.getPlayerAssets());
    }

    @Test
    void testSetPlayerRounds() {
        gameState.setPlayerRounds(20);
        assertEquals(20, gameState.getPlayerRounds());
    }

    @Test
    void testSetBettingBox() {
        gameState.setBettingBox(1000);
        assertEquals(1000, gameState.getBettingBox());
    }

    @Test
    void testSetDealerCards() {
        DealerCards newDealerCards = new DealerCards();
        Cards52 newCardsPool = new Cards52();
        newDealerCards.getList().add(newCardsPool.pickRandomCard());
        newDealerCards.getList().add(newCardsPool.pickRandomCard());
        gameState.setDealerCards(newDealerCards);
        assertEquals(newDealerCards, gameState.getDealerCards());
    }

    @Test
    void testSetPlayerCards() {
        PlayerCards newPlayerCards = new PlayerCards();
        Cards52 newCardsPool = new Cards52();
        newPlayerCards.getList().add(newCardsPool.pickRandomCard());
        newPlayerCards.getList().add(newCardsPool.pickRandomCard());
        gameState.setPlayerCards(newPlayerCards);
        assertEquals(newPlayerCards, gameState.getPlayerCards());
    }

    @Test
    void testSetCardsPool() {
        Cards52 newCardsPool = new Cards52();
        gameState.setCardsPool(newCardsPool);
        assertEquals(newCardsPool, gameState.getCardsPool());
    }

    @Test
    void testSetDealerSecondCardStatus() {
        DealerCards dealerCards = new DealerCards();
        dealerCards.addDealerCard(new Card(1, "Diamonds", "face-up"));
        dealerCards.addDealerCard(new Card(2, "Diamonds", "face-down"));
        gameState.setDealerCards(dealerCards);
        gameState.setDealerSecondCardStatus("face-up");
        assertEquals("face-up", gameState.getDealerCards().getList().get(1).getCardStatus());
    }
}

package model;

// This class mainly serve the game stage of the whole Black Jack Game; it is a state model that carries all the
// relevant parameters for the game stage; most phases of the game stage are operating and rendered according to this
// state model; it contains various parameters including the name of current player, the assets of current player, the
// number of rounds that current player has played, the amount of bets, the current cards on dealer's hand, the current
// cards on player's hand, and the current cards left in the cards pool; this is the most essential model for the game.

public class GameState {
    private String playerName;
    private int playerAssets;
    private int playerRounds;
    private int bettingBox;
    private DealerCards dealerCards;
    private PlayerCards playerCards;
    private Cards52 cardsPool;

    // EFFECTS: construct a new gameState with given player name, assets, rounds, amount in the betting box, a list of
    // cards on dealer's hand, a list of cards on player's hand, and a list of cards in the cards pool
    public GameState(String playerName, int playerAssets, int playerRounds, int bettingBox,
                     DealerCards dealerCards, PlayerCards playerCards, Cards52 cardsPool) {
        this.playerName = playerName;
        this.playerAssets = playerAssets;
        this.playerRounds = playerRounds;
        this.bettingBox = bettingBox;
        this.dealerCards = dealerCards;
        this.playerCards = playerCards;
        this.cardsPool = cardsPool;
    }

    //getMethods for the parameters of the current GameState
    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerAssets() {
        return playerAssets;
    }

    public int getPlayerRounds() {
        return playerRounds;
    }

    public int getBettingBox() {
        return bettingBox;
    }

    public DealerCards getDealerCards() {
        return dealerCards;
    }

    public PlayerCards getPlayerCards() {
        return playerCards;
    }

    public Cards52 getCardsPool() {
        return  cardsPool;
    }

    //setMethods for the parameters of the current GameState
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerAssets(int playerAssets) {
        this.playerAssets = playerAssets;
    }

    public void setPlayerRounds(int playerRounds) {
        this.playerRounds = playerRounds;
    }

    public void setBettingBox(int bettingBox) {
        this.bettingBox = bettingBox;
    }

    public void setDealerCards(DealerCards dealerCards) {
        this.dealerCards = dealerCards;
    }

    public void setPlayerCards(PlayerCards playerCards) {
        this.playerCards = playerCards;
    }

    public void setCardsPool(Cards52 cardsPool) {
        this.cardsPool = cardsPool;
    }

    // REQUIRES: the length of dealerCards is more than or equal to 2
    // MODIFIES: this, DealerCards
    // EFFECTS:  set card status of the second card in the dealerCards list with the given string
    public void setDealerSecondCardStatus(String cardStatus) {
        this.dealerCards.setSecondCardStatus(cardStatus);
    }
}

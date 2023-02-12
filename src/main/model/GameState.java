package model;

public class GameState {
    private String playerName;
    private int playerAssets;
    private int playerRounds;
    private int bettingBox;
    private DealerCards dealerCards;
    private PlayerCards playerCards;
    private Cards52 cardsPool;

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

    public void setDealerSecondCardStatus(String cardStatus) {
        this.dealerCards.setSecondCardStatus(cardStatus);
    }
}

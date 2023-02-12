package ui.gamebody.cards;

import model.Card;
import model.GameState;

public class PlayerCardLabel2 {
    private final GameState gameState;

    public PlayerCardLabel2(GameState gameState) {
        this.gameState = gameState;
    }

    public String cardAdaptor(GameState gameState) {
        Card card = gameState.getPlayerCards().getList().get(1);
        String cardDescription;
        if (card.getCardNum() == 1 && card.getCardStatus().equals("face-up")) {
            cardDescription = card.getCardSuit() + " A";
            return cardDescription;
        } else if (card.getCardNum() == 11 && card.getCardStatus().equals("face-up")) {
            cardDescription = card.getCardSuit() + " J";
            return cardDescription;
        } else if (card.getCardNum() == 12 && card.getCardStatus().equals("face-up")) {
            cardDescription = card.getCardSuit() + " Q";
            return cardDescription;
        } else if (card.getCardNum() == 13 && card.getCardStatus().equals("face-up")) {
            cardDescription = card.getCardSuit() + " K";
            return cardDescription;
        } else if (card.getCardStatus().equals("face-down")) {
            cardDescription = "This card is facing down.";
            return cardDescription;
        } else {
            cardDescription = card.getCardSuit() + " " + card.getCardNum();
            return cardDescription;
        }
    }

    public void display() {
        System.out.println("Player Card #2: " + cardAdaptor(gameState));
    }
}

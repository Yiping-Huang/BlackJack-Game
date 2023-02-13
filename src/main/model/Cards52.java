package model;

import java.util.ArrayList;
import java.util.Random;

// This class represent a deck of 52 poker cards without jokers

public class Cards52 {
    ArrayList<Card> cards52;

    // EFFECTS: construct a deck of 52 face-up poker cards, 13 cards for each kind of card suit
    public Cards52() {
        cards52 = new ArrayList<>();
        int diamondsCardNum = 1;
        while (diamondsCardNum <= 13) {
            cards52.add(new Card(diamondsCardNum, "Diamonds", "face-up"));
            diamondsCardNum = diamondsCardNum + 1;
        }
        int clubsCardNum = 1;
        while (clubsCardNum <= 13) {
            cards52.add(new Card(clubsCardNum, "Clubs", "face-up"));
            clubsCardNum = clubsCardNum + 1;
        }
        int heartsCardNum = 1;
        while (heartsCardNum <= 13) {
            cards52.add(new Card(heartsCardNum, "Hearts", "face-up"));
            heartsCardNum = heartsCardNum + 1;
        }
        int spadesCardNum = 1;
        while (spadesCardNum <= 13) {
            cards52.add(new Card(spadesCardNum, "Spades", "face-up"));
            spadesCardNum = spadesCardNum + 1;
        }
    }

    public ArrayList<Card> getList() {
        return cards52;
    }

    // REQUIRES: Cards52 is not empty
    // MODIFIES: this
    // EFFECTS:  take a random card out of the deck, remove it from the deck, and return it
    public Card pickRandomCard() {
        Random r = new Random();
        int i = r.nextInt(cards52.size() - 1);
        Card pickedCard = cards52.get(i);
        cards52.remove(i);
        return pickedCard;
    }
}

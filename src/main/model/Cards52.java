package model;

import java.util.ArrayList;
import java.util.Random;

public class Cards52 {
    ArrayList<Card> cards52;

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

    public Card pickRandomCard() {
        Random r = new Random();
        int i = r.nextInt(cards52.size() - 1);
        Card pickedCard = cards52.get(i);
        cards52.remove(i);
        return pickedCard;
    }
}

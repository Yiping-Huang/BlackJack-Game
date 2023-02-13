package ui.gamebody;

// This class is a game label which is used to display messages to the user as instructions

public class MessageAreaLabel {

    // EFFECTS:  construct a message area label
    public MessageAreaLabel() {}

    //REQUIRES: messageIndex is among [0, 13]
    // MODIFIES: this
    // EFFECTS:  display corresponding message according to the given messageIndex
    @SuppressWarnings("methodlength")
    public void display(int messageIndex) {
        System.out.println("\n-------------------------------------");
        if (messageIndex == 0) {
            System.out.println("\nBetting Stage");
        } else if (messageIndex == 1) {
            System.out.println("\nDealing Stage");
        } else if (messageIndex == 2) {
            System.out.println("\nYou Bust! Your lose all your bet.");
        } else if (messageIndex == 3) {
            System.out.println("\nPush! You get your bet back.");
        } else if (messageIndex == 4) {
            System.out.println("\nDealer wins! You lose all your bet!");
        } else if (messageIndex == 5) {
            System.out.println("\nBlack Jack! You earn double bet!");
        } else if (messageIndex == 6) {
            System.out.println("\nDealer Busts! You earn the bet!");
        } else if (messageIndex == 7) {
            System.out.println("\nYou win! You earn the bet!");
        } else if (messageIndex == 8) {
            System.out.println("\nGame over! You lose all your asset.");
        } else if (messageIndex == 9) {
            System.out.println("\n 5-card Charlie! You have already won! "
                    + "You may withdraw bonus right now or hit for a bigger bonus");
        } else if (messageIndex == 10) {
            System.out.println("\n5-card Charlie! You win big bonus!");
        } else if (messageIndex == 11) {
            System.out.println("\nDo you want to make an Insurance?");
        } else if (messageIndex == 12) {
            System.out.println("\nThe Dealer is Black Jack! You earn your insurance and bonus.");
        } else if (messageIndex == 13) {
            System.out.println("\nThe Dealer is not Black Jack. You lose your insurance.");
        }
    }
}

package ui;

// This class represent the Main Menu at the start of the Black Jack Game. This is a pop-up menu with different
// navigation buttons

public class MainMenuPanel {

    // EFFECTS: construct the Main Menu Panel and printout the content
    public MainMenuPanel() {
        System.out.println("\n-------------------------------------");
        System.out.println("Welcome to the BlackJack Casino!");
        System.out.println("-------------------------------------");
        System.out.println("\nSelect from:");
        System.out.println("\ts -> Start New Game");
        System.out.println("\tr -> Resume Game");
        System.out.println("\tp -> Player Ranking");
        System.out.println("\te -> Exit Game");
    }
}

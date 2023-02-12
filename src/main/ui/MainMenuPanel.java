package ui;

public class MainMenuPanel {

    public MainMenuPanel() {
        displayMainMenu();
    }

    public void displayMainMenu() {
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

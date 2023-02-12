package ui;

import model.*;
import ui.gamebody.*;
import ui.gamebody.buttons.*;
import ui.gamebody.cards.*;

import java.util.Scanner;

public class BlackJackGame {
    private static int panelIndex;
    private final Scanner input;
    private final Player placeHolder = new Player("null", 0, 0);
    private static GameRecords gameRecords;
    private static int currentGameRecordIndex;
    private GameState gameState;


    // The whole Black Jack Game
    public BlackJackGame() {
        panelIndex = 0;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        gameRecords = new GameRecords();
        renderNavigation(placeHolder, gameRecords, panelIndex);
    }

    // Render a series of navigation pages besides the game interface
    @SuppressWarnings("methodlength")
    public void renderNavigation(Player player, GameRecords gameRecords, int panelIndex) {
        if (panelIndex == 0) {
            new MainMenuPanel();
            processInputMainMenu(input.next().toLowerCase());
        } else if (panelIndex == 1) {
            new CreatePlayerPanel();
            processInputCreatePlayer(input.next());
        } else if (panelIndex == 2 && gameRecords.getList().isEmpty()) {
            System.out.println("\nThere is no player on record.\n");
            BlackJackGame.panelIndex = 0;
            renderNavigation(placeHolder, gameRecords, BlackJackGame.panelIndex);
        } else if (panelIndex == 2) {
            new GameRecordsPanel(gameRecords);
            processInputGameRecordsOptions(input.next().toLowerCase());
        } else if (panelIndex == 3) {
            new PlayerRankingPanel(gameRecords);
            processInputPlayerRanking(input.next().toLowerCase());
        } else if (panelIndex == 4 && !gameRecords.getList().contains(player)) {
            renderGamePhase0(player, gameRecords, false, currentGameRecordIndex);
        } else if (panelIndex == 4 && gameRecords.getList().contains(player)) {
            renderGamePhase0(player, gameRecords, true, currentGameRecordIndex);
        } else if (panelIndex == 5) {
            new LoadRecordPanel();
            currentGameRecordIndex = Integer.parseInt(input.next()) - 1;
            processInputLoadGameRecords(currentGameRecordIndex);
        } else if (panelIndex == 6) {
            new DeleteRecordPanel();
            int deleteGameRecordIndex = Integer.parseInt(input.next()) - 1;
            processInputDeleteGameRecords(deleteGameRecordIndex);
        }
    }

    // Input processor for the Main Menu page
    public void processInputMainMenu(String command) {
        switch (command) {
            case "s":
                panelIndex = 1;
                renderNavigation(placeHolder, gameRecords, panelIndex);
                break;
            case "r":
                panelIndex = 2;
                renderNavigation(placeHolder, gameRecords, panelIndex);
                break;
            case "p":
                panelIndex = 3;
                renderNavigation(placeHolder, gameRecords, panelIndex);
                break;
            case "e":
                System.out.println("\n-------------------------------------");
                System.out.println("Thank you for playing!");
                System.out.println("-------------------------------------\n");
                break;
        }
    }

    // Input processor for the Create Player page
    public void processInputCreatePlayer(String command) {
        panelIndex = 4;
        renderNavigation(new Player(command,10000, 0), gameRecords, panelIndex);
    }

    // Input processor for the Game Record page; this phase mainly deal with records management
    public void processInputGameRecordsOptions(String command) {
        switch (command) {
            case "l":
                panelIndex = 5;
                renderNavigation(placeHolder, gameRecords, panelIndex);
                break;
            case "d":
                panelIndex = 6;
                renderNavigation(placeHolder, gameRecords, panelIndex);
                break;
            case "b":
                panelIndex = 0;
                renderNavigation(placeHolder, gameRecords, panelIndex);
                break;
        }
    }

    // Input processor for the Game Record page; this phase deal with loading existing record
    public void processInputLoadGameRecords(int recordIndex) {
        panelIndex = 4;
        renderNavigation(gameRecords.getRecord(recordIndex), gameRecords, panelIndex);
    }

    // Input processor for the Game Record page; this phase deal with deleting existing record
    public void processInputDeleteGameRecords(int recordIndex) {
        panelIndex = 2;
        gameRecords.deleteRecord(recordIndex);
        renderNavigation(placeHolder, gameRecords, panelIndex);
    }

    // Input processor for the Player Ranking page
    public void processInputPlayerRanking(String command) {
        if (command.equals("b")) {
            panelIndex = 0;
            renderNavigation(placeHolder, gameRecords, panelIndex);
        }
    }

    // Initial Betting Phase (Game Interface)
    public void renderGamePhase0(Player player, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        gameState = new GameState(player.getName(), player.getAssets(), player.getRounds(),
                0, new DealerCards(), new PlayerCards(), new Cards52());
        new MessageAreaLabel().display(0);
        new PlayerInfoLabel(gameState).display();
        new BettingAreaLabel(gameState).display();
        new RaiseBetButton().display();
        new AllInButton().display();
        processInputGamePhase0(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                input.next().toLowerCase());
    }

    // Input processor for initial Betting Phase
    public void processInputGamePhase0(GameState gameState, GameRecords gameRecords, boolean loadedGameRecord,
                                       int currentGameRecordIndex, String command) {
        if (command.equals("r") && (gameState.getPlayerAssets() >= 100)) {
            gameState.setBettingBox(gameState.getBettingBox() + 100);
            gameState.setPlayerAssets(gameState.getPlayerAssets() - 100);
            renderGamePhase1(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("a") && (gameState.getPlayerAssets() >= 100)) {
            gameState.setBettingBox(gameState.getBettingBox() + gameState.getPlayerAssets());
            gameState.setPlayerAssets(0);
            renderGamePhase2(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else {
            System.out.println("You lose the game!");
        }
    }

    // Recurring Betting Phase
    public void renderGamePhase1(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        new MessageAreaLabel().display(0);
        new PlayerInfoLabel(gameState).display();
        new BettingAreaLabel(gameState).display();
        new RaiseBetButton().display();
        new AllInButton().display();
        new StopBettingButton().display();
        processInputGamePhase1(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                input.next().toLowerCase());
    }

    // Input processor for Recurring Betting Phase
    public void processInputGamePhase1(GameState gameState, GameRecords gameRecords, boolean loadedGameRecord,
                                       int currentGameRecordIndex, String command) {
        if (command.equals("r") && (gameState.getPlayerAssets() >= 100)) {
            gameState.setBettingBox(gameState.getBettingBox() + 100);
            gameState.setPlayerAssets(gameState.getPlayerAssets() - 100);
            renderGamePhase1(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("a") && (gameState.getPlayerAssets() >= 100)) {
            gameState.setBettingBox(gameState.getBettingBox() + gameState.getPlayerAssets());
            gameState.setPlayerAssets(0);
            renderGamePhase2(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("s")) {
            renderGamePhase2(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // The initial Dealing Phase
    @SuppressWarnings("methodlength")
    public void renderGamePhase2(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        DealerCards dealerCards = gameState.getDealerCards();
        PlayerCards playerCards = gameState.getPlayerCards();
        Cards52 cardsPool = gameState.getCardsPool();

        dealerCards.addDealerCard(cardsPool.pickRandomCard());

        Card cardDealer2 = cardsPool.pickRandomCard();
        cardDealer2.setCardStatus("face-down");
        dealerCards.addDealerCard(cardDealer2);

        playerCards.addPlayerCard(cardsPool.pickRandomCard());

        playerCards.addPlayerCard(cardsPool.pickRandomCard());

        this.gameState = new GameState(gameState.getPlayerName(), gameState.getPlayerAssets(),
                gameState.getPlayerRounds(), gameState.getBettingBox(), dealerCards, playerCards, cardsPool);

        if (dealerCards.getList().get(0).getCardNum() == 1
                && gameState.getPlayerAssets() >= gameState.getBettingBox()) {
            new MessageAreaLabel().display(11);
            renderGamePhase2Helper1(this.gameState);
            new InsuranceButton().display();
            new NoInsuranceButton().display();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        } else if ((dealerCards.sumDealerCards() == 21) || (playerCards.sumPlayerCards() == 21)) {
            renderBlackJackPhase(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (this.gameState.getPlayerAssets() >= this.gameState.getBettingBox()) {
            new MessageAreaLabel().display(1);
            renderGamePhase2Helper1(this.gameState);
            renderGamePhase2Helper2();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        } else {
            new MessageAreaLabel().display(1);
            renderGamePhase2Helper1(this.gameState);
            renderGamePhase2Helper3();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        }
    }

    // The sub phase of the initial Dealing Phase if the user encounter the insurance situation
    public void renderAfterInsurancePhase(GameState gameState, GameRecords gameRecords,
                                          boolean loadedGameRecord, int currentGameRecordIndex) {
        DealerCards dealerCards = gameState.getDealerCards();
        PlayerCards playerCards = gameState.getPlayerCards();
        if ((dealerCards.sumDealerCards() == 21) || (playerCards.sumPlayerCards() == 21)) {
            renderBlackJackPhase(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (this.gameState.getPlayerAssets() >= this.gameState.getBettingBox()) {
            new MessageAreaLabel().display(1);
            renderGamePhase2Helper1(this.gameState);
            renderGamePhase2Helper2();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        } else {
            new MessageAreaLabel().display(1);
            renderGamePhase2Helper1(this.gameState);
            renderGamePhase2Helper3();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        }
    }

    // The sub phase of the initial Dealing Phase if the user encounter the Black Jack situation
    public void renderBlackJackPhase(GameState gameState, GameRecords gameRecords,
                                     boolean loadedGameRecord, int currentGameRecordIndex) {
        int sumDealerCards = gameState.getDealerCards().sumDealerCards();
        int sumPlayerCards = gameState.getPlayerCards().sumPlayerCards();
        gameState.setDealerSecondCardStatus("face-up");
        if ((sumDealerCards == 21) && (sumPlayerCards == 21)) {
            new MessageAreaLabel().display(3);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + gameState.getBettingBox());
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (sumDealerCards == 21) {
            new MessageAreaLabel().display(4);
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (sumPlayerCards == 21) {
            new MessageAreaLabel().display(5);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + 3 * gameState.getBettingBox());
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // The render helper of routine info elements at the initial Dealing Phase
    public void renderGamePhase2Helper1(GameState gameState) {
        new PlayerInfoLabel(gameState).display();
        new BettingAreaLabel(gameState).display();
        new DealerCardLabel1(gameState).display();
        new DealerCardLabel2(gameState).display();
        new DealerCardsCounter(gameState).display();
        new PlayerCardLabel1(gameState).display();
        new PlayerCardLabel2(gameState).display();
        new PlayerCardsCounter(gameState).display();
    }

    // The render helper of action elements at the initial Dealing Phase
    public void renderGamePhase2Helper2() {
        new HitButton().display();
        new DoubleButton().display();
        new StandButton().display();
    }

    // The render helper of action elements at the initial Dealing Phase
    public void renderGamePhase2Helper3() {
        new HitButton().display();
        new StandButton().display();
    }

    // Input processor for the initial Dealing Phase
    @SuppressWarnings("methodlength")
    public void processInputGamePhase2(GameState gameState, GameRecords gameRecords, boolean loadedGameRecord,
                                       int currentGameRecordIndex, String command) {
        PlayerCards playerCards = gameState.getPlayerCards();
        DealerCards dealerCards = gameState.getDealerCards();
        Cards52 cardsPool = gameState.getCardsPool();
        int bettingBox = gameState.getBettingBox();
        int playerAssets = gameState.getPlayerAssets();
        if (command.equals("h")) {
            playerCards.addPlayerCard(cardsPool.pickRandomCard());
            this.gameState = new GameState(gameState.getPlayerName(), gameState.getPlayerAssets(),
                    gameState.getPlayerRounds(), gameState.getBettingBox(), gameState.getDealerCards(),
                    playerCards, cardsPool);
            renderGamePhase3(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("d")) {
            playerCards.addPlayerCard(cardsPool.pickRandomCard());
            playerAssets = playerAssets - bettingBox;
            bettingBox = 2 * bettingBox;
            this.gameState = new GameState(gameState.getPlayerName(), playerAssets, gameState.getPlayerRounds(),
                    bettingBox, gameState.getDealerCards(), playerCards, cardsPool);
            renderGamePhase4(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("s") && (dealerCards.sumDealerCards() < 17)) {
            dealerCards.addDealerCard(cardsPool.pickRandomCard());
            renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("s")) {
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("i") && gameState.getDealerCards().sumDealerCards() == 21) {
            gameState.setPlayerAssets(gameState.getPlayerAssets() + 2 * gameState.getBettingBox());
            new MessageAreaLabel().display(12);
            renderAfterInsurancePhase(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("i") && gameState.getDealerCards().sumDealerCards() != 21) {
            gameState.setPlayerAssets(gameState.getPlayerAssets() - gameState.getBettingBox());
            new MessageAreaLabel().display(13);
            renderAfterInsurancePhase(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("n")) {
            renderAfterInsurancePhase(gameState,gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // The Player Recurring Hitting Phase
    @SuppressWarnings("methodlength")
    public void renderGamePhase3(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        if (gameState.getPlayerCards().bustOrNot()) {
            new MessageAreaLabel().display(2);
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if ((gameState.getPlayerCards().getList().size() >= 5)
                && (gameState.getPlayerCards().sumPlayerCards() == 21)) {
            new MessageAreaLabel().display(10);
            gameState.setBettingBox(gameState.getBettingBox() * 2);
            gameState.setPlayerAssets(gameState.getPlayerAssets()
                    + (gameState.getBettingBox() * (1 + gameState.getPlayerCards().getList().size())));
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (gameState.getPlayerCards().getList().size() >= 5) {
            new MessageAreaLabel().display(9);
            new PlayerInfoLabel(gameState).display();
            new BettingAreaLabel(gameState).display();
            renderAllCardsHelper(gameState);
            new HitButton().display();
            new WithdrawButton().display();
            processInputGamePhase3(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        } else if (gameState.getPlayerCards().sumPlayerCards() == 21) {
            renderBlackJackPhase(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else {
            new PlayerInfoLabel(gameState).display();
            new BettingAreaLabel(gameState).display();
            renderAllCardsHelper(gameState);
            new HitButton().display();
            new StandButton().display();
            processInputGamePhase3(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        }
    }

    // The render helper of cards elements at the Player Recurring Hitting Phase
    public void renderAllCardsHelper(GameState gameState) {
        new DealerCardLabel1(gameState).display();
        new DealerCardLabel2(gameState).display();
        new DealerCardLabel3(gameState).display();
        new DealerCardLabel4(gameState).display();
        new DealerCardLabel5(gameState).display();
        new DealerCardsCounter(gameState).display();
        new PlayerCardLabel1(gameState).display();
        new PlayerCardLabel2(gameState).display();
        new PlayerCardLabel3(gameState).display();
        new PlayerCardLabel4(gameState).display();
        new PlayerCardLabel5(gameState).display();
        new PlayerCardLabel6(gameState).display();
        new PlayerCardLabel7(gameState).display();
        new PlayerCardLabel8(gameState).display();
        new PlayerCardLabel9(gameState).display();
        new PlayerCardLabel10(gameState).display();
        new PlayerCardLabel11(gameState).display();
        new PlayerCardsCounter(gameState).display();
    }

    // Input processor for the Player Recurring Hitting Phase
    public void processInputGamePhase3(GameState gameState, GameRecords gameRecords,
                                       boolean loadedGameRecord, int currentGameRecordIndex, String command) {
        PlayerCards playerCards = gameState.getPlayerCards();
        DealerCards dealerCards = gameState.getDealerCards();
        Cards52 cardsPool = gameState.getCardsPool();
        if (command.equals("h")) {
            playerCards.addPlayerCard(cardsPool.pickRandomCard());
            this.gameState = new GameState(gameState.getPlayerName(), gameState.getPlayerAssets(),
                    gameState.getPlayerRounds(), gameState.getBettingBox(), gameState.getDealerCards(),
                    playerCards, cardsPool);
            renderGamePhase3(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("s") && (dealerCards.sumDealerCards() < 17)) {
            dealerCards.addDealerCard(cardsPool.pickRandomCard());
            renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("s")) {
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("w")) {
            new MessageAreaLabel().display(10);
            gameState.setPlayerAssets(gameState.getPlayerAssets()
                    + (gameState.getBettingBox() * (1 + gameState.getPlayerCards().getList().size())));
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // The Dealer Final Recurring Dealing Phase
    @SuppressWarnings("methodlength")
    public void renderGamePhase4(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        int dealerPoints = gameState.getDealerCards().sumDealerCards();
        int playerPoints = gameState.getPlayerCards().sumPlayerCards();
        if (gameState.getDealerCards().bustOrNot()) {
            new MessageAreaLabel().display(6);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + 2 * gameState.getBettingBox());
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (gameState.getPlayerCards().bustOrNot()) {
            new MessageAreaLabel().display(2);
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (gameState.getPlayerCards().sumPlayerCards() == 21) {
            // handle the case of double + blackjack
            renderBlackJackPhase(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if ((dealerPoints < 17) && (gameState.getDealerCards().getList().size() < 5)) {
            processGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (gameState.getDealerCards().getList().size() == 5) {
            new MessageAreaLabel().display(4);
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if ((dealerPoints >= 17) && (dealerPoints > playerPoints)) {
            new MessageAreaLabel().display(4);
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if ((dealerPoints >= 17) && (dealerPoints == playerPoints)) {
            new MessageAreaLabel().display(3);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + gameState.getBettingBox());
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (dealerPoints >= 17) {
            new MessageAreaLabel().display(7);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + 2 * gameState.getBettingBox());
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // The processor to keep the Dealer Final Recurring Dealing Phase recurring
    public void processGamePhase4(GameState gameState, GameRecords gameRecords,
                                  boolean loadedGameRecord, int currentGameRecordIndex) {
        DealerCards dealerCards = gameState.getDealerCards();
        Cards52 cardsPool = gameState.getCardsPool();
        dealerCards.addDealerCard(cardsPool.pickRandomCard());
        renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
    }

    // The Closing Phase
    public void renderGamePhase5(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        if (gameState.getPlayerAssets() == 0) {
            new MessageAreaLabel().display(8);
            new PlayerInfoLabel(gameState).display();
            renderAllCardsHelper(gameState);
        } else {
            new PlayerInfoLabel(gameState).display();
            renderAllCardsHelper(gameState);
            new NewRoundButton().display();
            new SaveGameRecordButton().display();
        }
        new BackToMainMenuButton().display();
        processInputGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                input.next().toLowerCase());
    }

    // Input processor for the Closing Phase
    public void processInputGamePhase5(GameState gameState, GameRecords gameRecords,
                                       boolean loadedGameRecord, int currentGameRecordIndex, String command) {
        Player currentPlayer = new Player(gameState.getPlayerName(),
                gameState.getPlayerAssets(),gameState.getPlayerRounds() + 1);
        if (command.equals("n")) {
            renderGamePhase0(currentPlayer, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("s") && loadedGameRecord) {
            gameRecords.saveOldRecord(currentGameRecordIndex, currentPlayer);
            renderGamePhase5(gameState, gameRecords, true, currentGameRecordIndex);
        } else if (command.equals("s")) {
            gameRecords.addRecord(currentPlayer);
            currentGameRecordIndex = 0;
            renderGamePhase5(gameState, gameRecords, true, currentGameRecordIndex);
        } else if (command.equals("b")) {
            BlackJackGame.gameRecords = gameRecords;
            renderNavigation(placeHolder, BlackJackGame.gameRecords, 0);
        }
    }

    public static void main(String[] args) {
        new BlackJackGame();
    }
}
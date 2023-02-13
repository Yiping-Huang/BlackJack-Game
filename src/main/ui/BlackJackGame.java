package ui;

import model.*;
import ui.gamebody.*;
import ui.gamebody.buttons.*;
import ui.gamebody.cards.*;

import java.util.Scanner;

// This is the cardinal ui class that manage the operation of the whole BlackJack Game. The game will start with a
// main menu and then user will be navigated to different pages. These pages including the main menu are represented by
// separate panels and running like pop-up panels above the main game frame. Through the navigation, the game will
// start and these pop-up panels will be closed. The cards game will be rendered on the main game frame. There are
// buttons on the different panels and the main game frame to help user navigate and also choose game action.

public class BlackJackGame {
    private static int panelIndex;
    private final Scanner input;
    private final Player placeHolder = new Player("null", 0, 0);
    private static GameRecords gameRecords;
    private static int currentGameRecordIndex;
    private GameState gameState;

    // EFFECTS: constructs BlackJackGame and initialize the input and the game records
    public BlackJackGame() {
        panelIndex = 0;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        gameRecords = new GameRecords();
        renderNavigation(placeHolder, gameRecords, panelIndex);
    }

    // REQUIRES: panelIndex is an int among [0, 6]
    // MODIFIES: this
    // EFFECTS:  render a series of navigation pages except the card game and call for the corresponding input
    // processor; if the panel index is 0, render the main menu; if the panel index is 1, render the Create Player; if
    // the panel index is 2, render the game records; if the panel index is 3, render the player ranking; if the
    // panel index is 4, start the game; if the panel index is 5, render Load Game; if the panel index is 6, render
    // Delete Game
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

    // REQUIRES: command is one of "s", "r", "p", and "e"
    // MODIFIES: this
    // EFFECTS:  process the input from the Main Menu page and generate the corresponding panel index; call the
    // navigation renderer with the panel index or end the game
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

    // MODIFIES: this
    // EFFECTS: process the input from the Create Player page, create a new Player with the input string as its name,
    // and start a new game with this new player
    public void processInputCreatePlayer(String command) {
        panelIndex = 4;
        renderNavigation(new Player(command,1000, 0), gameRecords, panelIndex);
    }

    // REQUIRES: command is one of "l", "d", and "b"
    // MODIFIES: this
    // EFFECTS: process input for the Game Record page; if command is "l", render Load Record Panel; if command is
    // "d", render Delete Record Panel; if command is "b", send user back to the main menu
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

    // REQUIRES: recordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  process the input from the Game Record page and load the game record according to the input
    public void processInputLoadGameRecords(int recordIndex) {
        panelIndex = 4;
        renderNavigation(gameRecords.getRecord(recordIndex), gameRecords, panelIndex);
    }

    // REQUIRES: recordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  process the input from the Game Record page, delete the game record according to the input, and send
    // user back to the previous Game Records Panel
    public void processInputDeleteGameRecords(int recordIndex) {
        panelIndex = 2;
        gameRecords.deleteRecord(recordIndex);
        renderNavigation(placeHolder, gameRecords, panelIndex);
    }

    // REQUIRES: command is "b"
    // MODIFIES: this
    // EFFECTS:  process input from the Player Ranking page and send user back to the Main Menu Panel
    public void processInputPlayerRanking(String command) {
        if (command.equals("b")) {
            panelIndex = 0;
            renderNavigation(placeHolder, gameRecords, panelIndex);
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the initial Betting Phase (Game Interface) and call for the corresponding input processor
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

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  process the input from the initial Betting Phase; if command is "r", raise the bet by $100; if command
    // is "a", all in and start Dealing Phase;
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
            System.out.println("\nYou lose the Game!");
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the recurring Betting Phase (Game Interface) and call for the corresponding input processor
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

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  process the input from the recurring Betting Phase; if command is "r" and player still has more than
    // $100 assets, raise the bet by $100; if command is "a", all in and start the Dealing Phase; if command is "s",
    // start the Dealing Phase; if command is "r" and player only has $100 left, raise the bet by $100 and start the
    // Dealing Phase
    public void processInputGamePhase1(GameState gameState, GameRecords gameRecords, boolean loadedGameRecord,
                                       int currentGameRecordIndex, String command) {
        if (command.equals("r") && (gameState.getPlayerAssets() > 100)) {
            gameState.setBettingBox(gameState.getBettingBox() + 100);
            gameState.setPlayerAssets(gameState.getPlayerAssets() - 100);
            renderGamePhase1(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("a") && (gameState.getPlayerAssets() >= 100)) {
            gameState.setBettingBox(gameState.getBettingBox() + gameState.getPlayerAssets());
            gameState.setPlayerAssets(0);
            renderGamePhase2(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("s")) {
            renderGamePhase2(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (command.equals("r") && (gameState.getPlayerAssets() == 100)) {
            gameState.setBettingBox(gameState.getBettingBox() + 100);
            gameState.setPlayerAssets(gameState.getPlayerAssets() - 100);
            renderGamePhase2(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the initial Dealing Phase (Game Interface), initialize the game state; handle different
    // situations including insurance case, blackjack case, normal case with double, and normal case without double;
    // call for the corresponding input processor in different situations
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
                && gameState.getPlayerAssets() >= gameState.getBettingBox()) { // insurance case
            new MessageAreaLabel().display(11);
            renderGamePhase2Helper1(this.gameState);
            new InsuranceButton().display();
            new NoInsuranceButton().display();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        } else if ((dealerCards.sumDealerCards() == 21) || (playerCards.sumPlayerCards() == 21)) { // blackjack case
            renderBlackJackPhase(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (this.gameState.getPlayerAssets() >= this.gameState.getBettingBox()) { // normal case with double
            new MessageAreaLabel().display(1);
            renderGamePhase2Helper1(this.gameState);
            renderGamePhase2Helper2();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        } else { // normal case without double
            new MessageAreaLabel().display(1);
            renderGamePhase2Helper1(this.gameState);
            renderGamePhase2Helper3();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the sub phase of the initial Dealing Phase if the user encounter the insurance case and also
    // chose to make the insurance; handle different situation including blackjack case, normal case with double, and
    // normal case without double; call for the corresponding input processor or renderer in different situations
    public void renderAfterInsurancePhase(GameState gameState, GameRecords gameRecords,
                                          boolean loadedGameRecord, int currentGameRecordIndex) {
        DealerCards dealerCards = gameState.getDealerCards();
        PlayerCards playerCards = gameState.getPlayerCards();
        if ((dealerCards.sumDealerCards() == 21) || (playerCards.sumPlayerCards() == 21)) { // blackjack case
            renderBlackJackPhase(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (this.gameState.getPlayerAssets() >= this.gameState.getBettingBox()) { // normal case with double
            new MessageAreaLabel().display(1);
            renderGamePhase2Helper1(this.gameState);
            renderGamePhase2Helper2();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        } else { // normal case without double
            new MessageAreaLabel().display(1);
            renderGamePhase2Helper1(this.gameState);
            renderGamePhase2Helper3();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    input.next().toLowerCase());
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the sub phase of the initial Dealing Phase if the user encounter the Black Jack situation in the
    // initial Dealing Phase or After Insurance Phase; handle different situation including push case, dealer blackjack
    // case, and player blackjack case; call for the renderer of the Closing Phase
    public void renderBlackJackPhase(GameState gameState, GameRecords gameRecords,
                                     boolean loadedGameRecord, int currentGameRecordIndex) {
        int sumDealerCards = gameState.getDealerCards().sumDealerCards();
        int sumPlayerCards = gameState.getPlayerCards().sumPlayerCards();
        gameState.setDealerSecondCardStatus("face-up");
        if ((sumDealerCards == 21) && (sumPlayerCards == 21)) { // push case
            new MessageAreaLabel().display(3);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + gameState.getBettingBox());
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (sumDealerCards == 21) { // dealer blackjack case
            new MessageAreaLabel().display(4);
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (sumPlayerCards == 21) { // player blackjack case
            new MessageAreaLabel().display(5);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + 3 * gameState.getBettingBox());
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // MODIFIES: this
    // EFFECTS:  help the initial Dealing Phase renderer render routine info elements
    public void renderGamePhase2Helper1(GameState gameState) {
        new PlayerInfoLabel(gameState).display();
        new BettingAreaLabel(gameState).display();
        new DealerCardLabel1(gameState).display();
        new DealerCardLabel2(gameState).display();
        new DealerCardsCounterLabel(gameState).display();
        new PlayerCardLabel1(gameState).display();
        new PlayerCardLabel2(gameState).display();
        new PlayerCardsCounterLabel(gameState).display();
    }

    // MODIFIES: this
    // EFFECTS:  help the initial Dealing Phase renderer render action elements
    public void renderGamePhase2Helper2() {
        new HitButton().display();
        new DoubleButton().display();
        new StandButton().display();
    }

    // MODIFIES: this
    // EFFECTS:  help the initial Dealing Phase renderer render action elements
    public void renderGamePhase2Helper3() {
        new HitButton().display();
        new StandButton().display();
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  process input from the initial Dealing Phase; if command is "h", hit for one more card and call for
    // the renderer of Player Recurring Hitting Phase; if command is "d", double the bet, hit for one more card, and
    // call for the renderer of Closing Phase; if command is "s", call for the renderer of Dealer Final Recurring
    // Dealing Phase; if command is "i", make the insurance and call for the renderer of After Insurance Phase; if
    // command is "n", make no insurance and call for the renderer of After Insurance Phase
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

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the Player Recurring Hitting Phase (Game Interface); if player busts, call for the renderer of
    // the Closing Phase; if 5-card Charlie & Blackjack, call for the renderer of the Closing Phase; if 5-card Charlie
    // solely, call for the corresponding input processor; if Blackjack, call for the renderer of Black Jack Phase; else
    // call for the corresponding input processor of this phase
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

    // MODIFIES: this
    // EFFECTS:  help the Player Recurring Hitting Phase renderer render cards elements
    public void renderAllCardsHelper(GameState gameState) {
        new DealerCardLabel1(gameState).display();
        new DealerCardLabel2(gameState).display();
        new DealerCardLabel3(gameState).display();
        new DealerCardLabel4(gameState).display();
        new DealerCardLabel5(gameState).display();
        new DealerCardsCounterLabel(gameState).display();
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
        new PlayerCardsCounterLabel(gameState).display();
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  process the input from the Player Recurring Hitting Phase; if command is "h", hit for another card and
    // call for the Player Recurring Hitting Phase renderer; if command is "s", call for the Dealer Final Recurring
    // Dealing Phase renderer; if command is "w", withdraw bets and bonus to the assets and call for the Closing Phase
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

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the Dealer Final Recurring Dealing Phase; if dealer or player busts, call for the Closing Phase
    // renderer; if Blackjack, call for the Black Jack Phase renderer; if the sum of dealer cards is less than 17 and
    // less than 5 cards, keep hitting dealer cards until the sum is more than or equal to 17; if dealer has 5 cards
    // without busting, call for the Closing Phase; if the sum of dealer cards is more than 17, compare dealer cards
    // and player cards and call for the Closing Phase
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

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  keep the Dealer Final Recurring Dealing Phase recurring
    public void processGamePhase4(GameState gameState, GameRecords gameRecords,
                                  boolean loadedGameRecord, int currentGameRecordIndex) {
        DealerCards dealerCards = gameState.getDealerCards();
        Cards52 cardsPool = gameState.getCardsPool();
        dealerCards.addDealerCard(cardsPool.pickRandomCard());
        renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render The Closing Phase and provide navigation to start new round, save game record, and go back to
    // the main menu
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

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS: process inputs from the Closing Phase; if command is "n", start a new round and call for the initial
    // Betting Phase render; if command is "s", save the game record and come back to the same page; if command is "b",
    // call the Navigation renderer
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

}
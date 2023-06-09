package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.background.*;
import ui.gamephases.dealercards.*;
import ui.gamephases.information.*;
import ui.gamephases.operationbuttons.*;
import ui.gamephases.playercards.*;
import ui.navigation.mainmenu.*;
import ui.navigation.backtomainmenu.BackToMainMenuJButton;
import ui.navigation.playerrankingpage.*;
import ui.navigation.resumegamepage.*;
import ui.navigation.startnewgamepage.StartNewGameJTextField;
import ui.navigation.startnewgamepage.StartNewGameHeaderJLabel;
import ui.navigation.mainmenu.StartNewGameJButton;
import ui.navigation.startnewgamepage.SubmitNewPlayerJButton;
import ui.soundeffect.BackgroundMusic;
import ui.soundeffect.Clicking;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// This is the cardinal ui class that manage the operation of the whole BlackJack Game. The game will start with a
// main menu and then user will be navigated to different pages. These pages including the main menu are represented by
// separate JLabels and running like pop-up pages above the main game JFrame. Through the navigation, the game will
// start and these pop-up pages will be closed. The cards game will be rendered on the main game JFrame. There are
// JButtons on the different JLabels and the main game JFrame to help user navigate and also choose game action.

public class BlackJackGame extends JFrame {
    // General
    private int panelIndex;
    private boolean hasClickedRecord;
    private int currentGameRecordIndex;
    private boolean loadedGameRecord;
    private GameState gameState;
    private GameRecords gameRecords;
    private final Player placeHolder = new Player("Delete It", 100, 0);
    private Player currentPlayer;

    // Following fields are typically used for GUI
    private final Color listBackground = new Color(255, 255, 224);
    // Sound Effect
    private final BackgroundMusic backgroundMusic;
    private final Clicking clicking;
    // Main Menu Components
    private StartNewGameJButton startNewGameJButton;
    private ResumeGameJButton resumeGameJButton;
    private PlayerRankingJButton playerRankingJButton;
    private ExitGameJButton exitGameJButton;
    private MainMenuHeaderJLabel mainMenuHeaderJLabel;
    // Start New Game Page Components
    private StartNewGameHeaderJLabel startNewGameHeaderJLabel;
    private StartNewGameJTextField startNewGameJTextField;
    private SubmitNewPlayerJButton submitNewPlayerJButton;
    // Resume Game Page Components
    private JList<String> gameRecordsJList;
    private GameRecordsJLabel gameRecordsJLabel;
    private LoadRecordJButton loadRecordJButton;
    private DeleteRecordJButton deleteRecordJButton;
    // Player Ranking Page Components
    private PlayerRankingHeaderJLabel playerRankingHeaderJLabel;
    private PlayerRankingJLabel playerRankingJLabel;
    // Background Components
    private LeftJokerJLabel leftJokerJLabel;
    private RightJokerJLabel rightJokerJLabel;
    private NavigationBoardJLabel navigationBoardJLabel;
    private BackgroundColorJPanel backgroundColorJPanel;
    // Back to Main Menu JButton
    private BackToMainMenuJButton backToMainMenuJButton;
    // Game Phases Information Components
    private BettingAreaJLabel bettingAreaJLabel;
    private PlayerInfoTextJLabel playerInfoJLabel;
    private PlayerInfoBoardJLabel playerInfoBoardJLabel;
    private MessageAreaBoardJLabel messageAreaBoardJLabel;
    private MessageAreaTextJLabel messageAreaTextJLabel;
    private DealerCardsCounterJLabel dealerCardsCounterJLabel;
    private PlayerCardsCounterJLabel playerCardsCounterJLabel;
    // Game Betting Stage JButton Components (Phase 0 and Phase 1)
    private RaiseBetJButton raiseBetJButton;
    private AllInJButton allInJButton;
    private StopBettingJButton stopBettingJButton;
    // Game Dealing Stage JButton Components (Phase 2 to Phase 4)
    private InsuranceJButton insuranceJButton;
    private SkipInsuranceJButton skipInsuranceJButton;
    private HitJButton hitJButton;
    private DoubleJButton doubleJButton;
    private StandJButton standJButton;
    private WithdrawJButton withdrawJButton;
    // Game Closing Stage JButton Components (Phase 5)
    private NewRoundJButton newRoundJButton;
    private SaveGameRecordJButton saveGameRecordJButton;
    private MainMenuJButton mainMenuJButton;
    // Game Phases Cards Components
    private DealerCardJLabel1 dealerCardJLabel1;
    private DealerCardJLabel2 dealerCardJLabel2;
    private DealerCardJLabel3 dealerCardJLabel3;
    private DealerCardJLabel4 dealerCardJLabel4;
    private DealerCardJLabel5 dealerCardJLabel5;
    private PlayerCardJLabel1 playerCardJLabel1;
    private PlayerCardJLabel2 playerCardJLabel2;
    private PlayerCardJLabel3 playerCardJLabel3;
    private PlayerCardJLabel4 playerCardJLabel4;
    private PlayerCardJLabel5 playerCardJLabel5;
    private PlayerCardJLabel6 playerCardJLabel6;
    private PlayerCardJLabel7 playerCardJLabel7;
    private PlayerCardJLabel8 playerCardJLabel8;
    private PlayerCardJLabel9 playerCardJLabel9;
    private PlayerCardJLabel10 playerCardJLabel10;
    private PlayerCardJLabel11 playerCardJLabel11;


    // EFFECTS: constructs BlackJackGame and initialize the input and the game records
    @SuppressWarnings("methodlength")
    public BlackJackGame() {
        // Frame title setting
        super("Blackjack Game");
        // Frame setting
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1470,842);
        Color colorBackground = new Color(51, 153, 51);
        setBackground(colorBackground);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setResizable(false);
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2); // Center on the screen
        // Set Sound Effect
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playMusic();
        clicking = new Clicking();
        // Load game records
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get("./data/gameRecords.json")));
            if (fileContent.trim().isEmpty()) {
                gameRecords = new GameRecords();
            } else {
                gameRecords = new JsonReader("./data/gameRecords.json").getGameRecords();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // start rendering
        panelIndex = 0;
        renderNavigation(placeHolder, gameRecords, panelIndex);
        // This must be put at last or all components cannot be seen
        setVisible(true);
    }

    // REQUIRES: panelIndex is an int among [0, 5]
    // MODIFIES: this, gameRecordsJList, gameRecordsJLabel, playerRankingJLabel, createPlayerHeaderJLabel,
    // createPlayerJTextField, submitNewPlayerJButton, backToMainMenuJButton, navigationBoardJLabel, rightJokerJLabel,
    // loadRecordJButton, deleteRecordJButton
    // EFFECTS:  render a series of navigation pages except the card game and call for the corresponding input
    // processor; if the panelIndex is 0, render the initial main menu; if the panelIndex is 1, render the recurring
    // main menu when the user go back from other navigation pages; if the panelIndex is 2, render the Create
    // Player page; if the panelIndex is 3, render the Resume Game page; if the panelIndex is 4, render the Player
    // Ranking page; if the panelIndex is 5, call the Game Phase 0 renderer with the correct boolean value of
    // loadedGameRecord;
    @SuppressWarnings("methodlength")
    private void renderNavigation(Player player, GameRecords gameRecords, int panelIndex) {
        if (gameRecords.getList().isEmpty()) {
            gameRecords.addRecord(placeHolder);// Without this, the game will occasionally has non-compiler errors
        }
        if (panelIndex == 0) { // Initial Main Menu
            addMainMenuComponents();// Add Main Menu Components
            addNavigationBoardAndRightJoker();// Add Main Menu Board and Right Joker
            addBackgroundColorAndLeftJoker();// Add Background Color and Left Joker
            processInputMainMenu();// Call for the Main Menu processor
        } else if (panelIndex == 1) { // From other navigation pages back to the Main Menu
            removeNavigationBoardAndRightJoker();// Remove Main Menu Board and Right Joker
            removeBackgroundColorAndLeftJoker();// Remove Background Color and Left Joker
            addMainMenuComponents();// Add Main Menu Components
            addNavigationBoardAndRightJoker();// Add Main Menu Board and Right Joker
            addBackgroundColorAndLeftJoker();// Add Background Color and Left Joker
            repaint();
            processInputMainMenu();// Call for the Main Menu processor
        } else if (panelIndex == 2) { // From Main Menu to Start New Game page
            removeMainMenuComponents();// Remove Main Menu Components
            removeNavigationBoardAndRightJoker();// Remove Main Menu Board and Right Joker
            removeBackgroundColorAndLeftJoker();// Remove Background Color and Left Joker
            addStartNewGameComponents();// Add Start New Game Page Components
            addBackToMainMenuJButton();// Add Back to Main Menu JButton
            addNavigationBoardAndRightJoker();// Add Main Menu Board and Right Joker
            addBackgroundColorAndLeftJoker();// Add Background Color and Left Joker
            repaint();
            processInputStartNewGame();// Call for the Start New Game Page processor
        } else if (panelIndex == 3 && gameRecords.getList().isEmpty()) { //
            // No game progress on record, nothing rendered, process input at the Main Menu again
            processInputMainMenu();
        } else if (panelIndex == 3) { // Resume Game Page
            removeMainMenuComponents();// Remove Main Menu Components
            removeNavigationBoardAndRightJoker();// Remove Main Menu Board and Right Joker
            removeBackgroundColorAndLeftJoker();// Remove Background Color and Left Joker
            // Set up the Game Record List and Add it
            gameRecordsJLabel = new GameRecordsJLabel();
            DefaultListModel<String> gameRecordsModel = new GameRecordsModel().getGameRecordsModel(gameRecords);
            gameRecordsJList = new JList<>(gameRecordsModel);
            Font font = new Font("Time New Roman", Font.PLAIN,18);
            gameRecordsJList.setFont(font);
            gameRecordsJList.setBackground(listBackground);
            gameRecordsJList.setSize(500, 380);
            GameRecordsJScrollPanel grs = new GameRecordsJScrollPanel(gameRecordsJList);
            gameRecordsJLabel.add(grs);
            gameRecordsJLabel.setCorY(100);
            gameRecordsJLabel.setStatus("Showing");
            hasClickedRecord = false;
            add(gameRecordsJLabel);
            addResumeGameComponents();// Add Resume Game Page Components
            addBackToMainMenuJButton();// Add Back to Main Menu JButton
            addNavigationBoardAndRightJoker();// Add Main Menu Board and Right Joker
            addBackgroundColorAndLeftJoker();// Add Background Color and Left Joker
            repaint();
            processInputResumeGame();// Call for the Resume Game processor
        } else if (panelIndex == 4 && gameRecords.getList().isEmpty()) {
            // No game progress on record, nothing rendered, process input at the Main Menu again
            processInputMainMenu();
        } else if (panelIndex == 4) { // Player Ranking Page
            removeMainMenuComponents();// Remove Main Menu Components
            removeNavigationBoardAndRightJoker();// Remove Main Menu Board and Right Joker
            removeBackgroundColorAndLeftJoker();// Remove Background Color and Left Joker
            // Set up the Player Ranking Board
            playerRankingJLabel = new PlayerRankingJLabel();
            DefaultListModel<String> playerRankingModel = new PlayerRankingModel().getPlayerRankingModel(gameRecords);
            JList<String> playerRankingList = new JList<>(playerRankingModel);
            Font font = new Font("Time New Roman", Font.PLAIN,18);
            playerRankingList.setFont(font);
            playerRankingList.setBackground(listBackground);
            playerRankingList.setSize(500,600);
            PlayerRankingJScrollPanel prs = new PlayerRankingJScrollPanel(playerRankingList);
            playerRankingJLabel.add(prs);
            playerRankingJLabel.setStatus("Showing");
            add(playerRankingJLabel);
            removeMainMenuComponents();// Remove Main Menu Components
            addPlayerRankingComponents();// Add Player Ranking Page Components
            addBackToMainMenuJButton();// Add Back to Main Menu JButton
            addNavigationBoardAndRightJoker();// Add Main Menu Board and Right Joker
            addBackgroundColorAndLeftJoker();// Add Background Color and Left Joker
            repaint();
            processInputPlayerRanking();// Call for the Player Ranking processor
        } else if (panelIndex == 5 && gameRecords.getList().isEmpty()) { // Start a New Game with empty Game Records
            // Moving down the components of Start New game navigation stage
            startNewGameHeaderJLabel.setStatus("Moving Down");
            startNewGameJTextField.setStatus("Moving Down");
            submitNewPlayerJButton.setStatus("Moving Down");
            backToMainMenuJButton.setStatus("Moving Down");
            navigationBoardJLabel.setStatus("Moving Down");
            // Moving Right Background Component
            rightJokerJLabel.setStatus("Moving Right");
            // Go to the Game Phase 0 (Betting Stage)
            loadedGameRecord = false;
            repaint();
            renderGamePhase0(player, gameRecords, false, currentGameRecordIndex,
                    false, false);
        } else if (panelIndex == 5 && !gameRecords.getList().isEmpty()) { // Start the Game with existing Game Records
            if (!gameRecords.getList().contains(player)) { // From Start New Game Page (Create New Player)
                // Moving down the components of Start New game navigation stage
                startNewGameHeaderJLabel.setStatus("Moving Down");
                startNewGameJTextField.setStatus("Moving Down");
                submitNewPlayerJButton.setStatus("Moving Down");
                backToMainMenuJButton.setStatus("Moving Down");
                navigationBoardJLabel.setStatus("Moving Down");
                // Moving Right Background Component
                rightJokerJLabel.setStatus("Moving Right");
                // Go to the Game Phase 0 (Betting Stage)
                loadedGameRecord = false;
                repaint();
                renderGamePhase0(player, gameRecords, false, currentGameRecordIndex,
                        false, false);
            } else { // From Resume Game Page (Load Record)
                // Moving down the components of Resume Game navigation stage
                navigationBoardJLabel.setStatus("Moving Down");
                gameRecordsJLabel.setStatus("Moving Down");
                loadRecordJButton.setStatus("Moving Down");
                deleteRecordJButton.setStatus("Moving Down");
                backToMainMenuJButton.setStatus("Moving Down");
                hasClickedRecord = false;
                // Moving Right Background Component
                rightJokerJLabel.setStatus("Moving Right");
                // Go to the Game Phase 0 (Betting Stage)
                loadedGameRecord = true;
                repaint();
                renderGamePhase0(player, gameRecords, true, currentGameRecordIndex,
                        false, false);
            }
        }
    }

    // MODIFIES: this, startNewGameJButton, resumeGameJButton, playerRankingJButton, exitGameJButton
    // EFFECTS:  process the clicking action from the Main Menu page and generate the corresponding panelIndex; call
    // the navigation renderer with the corresponding panelIndex (2 for startNewGameJButton, 3 for resumeGameJButton,
    // and 4 for playerRankingJButton); when exitGameJButton is clicked, end the program directly
    private void processInputMainMenu() {
        startNewGameJButton.addActionListener(e -> {
            clicking.playMusic();
            panelIndex = 2;
            renderNavigation(placeHolder, gameRecords, panelIndex);
        });
        resumeGameJButton.addActionListener(e -> {
            clicking.playMusic();
            panelIndex = 3;
            renderNavigation(placeHolder, gameRecords, panelIndex);
        });
        playerRankingJButton.addActionListener(e -> {
            clicking.playMusic();
            panelIndex = 4;
            renderNavigation(placeHolder, gameRecords, panelIndex);
        });
        exitGameJButton.addActionListener(e -> {
            clicking.playMusic();
            backgroundMusic.stopMusic();
            for (Event next : EventLog.getInstance()) {
                System.out.println(next.toString() + "\n");
            }
            System.exit(0);
        });
    }

    // MODIFIES: this, submitNewPlayerJButton, backToMainMenuJButton
    // EFFECTS: process the text input and clicking action from the Start New Game page; when the user type something
    // in the startNewGameJTextField and also click submitNewPlayerJButton, create a new Player with the input string
    // as its name and call for the navigation renderer with panelIndex 5; when backToMainMenuJButton is clicked,
    // call for the navigation renderer with panelIndex 1 (go back to the Main Menu)
    private void processInputStartNewGame() {
        submitNewPlayerJButton.addActionListener(e -> {
            clicking.playMusic();
            if (!startNewGameJTextField.getText().equals("")) {
                panelIndex = 5;
                currentPlayer = new Player(startNewGameJTextField.getText(), 1000, 0);
                currentGameRecordIndex = 0;
                renderNavigation(currentPlayer, gameRecords, panelIndex);
            }
        });
        backToMainMenuJButton.addActionListener(e -> {
            clicking.playMusic();
            removeStartNewGameComponents();// Remove Start New Game Components
            removeBackToMainMenuJButton();// Remove Back to Main Menu Component
            panelIndex = 1;
            renderNavigation(placeHolder, gameRecords, panelIndex);
        });
    }

    // MODIFIES: this, gameRecordsJList, loadRecordJButton, deleteRecordJButton, backToMainMenuJButton
    // EFFECTS:  process the clicking action from the Resume Game page; when the user has chosen a game record on the
    // gameRecordsJList and also click the loadRecordJButton, call for the navigation renderer with panelIndex 5;
    // when the user has chosen a game record on the gameRecordsJList and also click the deleteRecordJButton, delete
    // the chosen game record from gameRecords and call for the JsonWriter to overwrite previous json data. If after the
    // deleting there is no game progress on record, call for the navigation renderer with panelIndex 1. If there are
    // still some game progresses on record, call for the navigation renderer with panelIndex 3; when
    // backToMainMenuJButton is clicked, call for the navigation renderer with panelIndex 1 (go back to the Main Menu)
    @SuppressWarnings("methodlength")
    private void processInputResumeGame() {
        gameRecordsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hasClickedRecord = true;
                currentGameRecordIndex = gameRecordsJList.getSelectedIndex();
            }
        });
        loadRecordJButton.addActionListener(e -> {
            clicking.playMusic();
            if (hasClickedRecord) {
                panelIndex = 5;
                renderNavigation(gameRecords.getRecord(currentGameRecordIndex), gameRecords, panelIndex);
            }
        });
        deleteRecordJButton.addActionListener(e -> {
            clicking.playMusic();
            if (hasClickedRecord) {
                if (gameRecords.getList().size() <= 1) {
                    panelIndex = 1;
                } else {
                    panelIndex = 3;
                }
                removeResumeGameComponents();// Remove Resume Game Components
                removeBackToMainMenuJButton();// Remove Back to Main Menu Component
                gameRecords.deleteRecord(currentGameRecordIndex);
                new JsonWriter(gameRecords,"./data/gameRecords.json").write();
                renderNavigation(placeHolder, gameRecords, panelIndex);
            }
        });
        backToMainMenuJButton.addActionListener(e -> {
            clicking.playMusic();
            removeResumeGameComponents();// Remove Resume Game Components
            removeBackToMainMenuJButton();// Remove Back to Main Menu Component
            panelIndex = 1;
            renderNavigation(placeHolder, gameRecords, panelIndex);
        });
    }

    // MODIFIES: this, backToMainMenuJButton
    // EFFECTS:  process the clicking action from the Player Ranking page; when backToMainMenuJButton is clicked,
    // call for the navigation renderer with panelIndex 1 (go back to the Main Menu)
    private void processInputPlayerRanking() {
        backToMainMenuJButton.addActionListener(e -> {
            clicking.playMusic();
            removePlayerRankingComponents();// Remove Player Ranking Components
            removeBackToMainMenuJButton();// Remove Back to Main Menu Component
            panelIndex = 1;
            renderNavigation(placeHolder, gameRecords, panelIndex);
        });
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, messageAreaTextJLabel, messageAreaBoardJLabel
    // EFFECTS:  render the Game Phase 0 (Initial Betting Stage); remove all the Swing components left by the previous
    // Phase and add the initial Betting Stage components in proper order; if this renderer is called from Game Phase 5,
    // set the status of messageAreaTextJLabel and messageAreaBoardJLabel as "Showing", otherwise set the status of
    // messageAreaTextJLabel and messageAreaBoardJLabel as "Delay Showing" to avoid the overlapping with previous
    // Phase's components; call for the corresponding processor of this renderer
    private void renderGamePhase0(Player player, GameRecords gameRecords, boolean loadedGameRecord,
                                 int currentGameRecordIndex, boolean played, boolean fromPhase5) {
        gameState = new GameState(player.getName(), player.getAssets(), player.getRounds(),
                0, new DealerCards(), new PlayerCards(), new Cards52());
        removeBackgroundColorAndLeftJoker();
        if (fromPhase5) {
            addMessageAreaShowingHelper(0);
            addGameInfoAndBettingStageJButtonsFromGamePhase5();
        } else {
            messageAreaTextJLabel = new MessageAreaTextJLabel(0);
            messageAreaTextJLabel.setStatus("Delay Showing");
            add(messageAreaTextJLabel);
            messageAreaBoardJLabel = new MessageAreaBoardJLabel();
            messageAreaBoardJLabel.setStatus("Delay Showing");
            add(messageAreaBoardJLabel);
            addGameInfoAndBettingStageJButtonsFromNavigationStage();
        }
        addBackgroundColorAndLeftJoker();
        repaint();
        processInputGamePhase0(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex, played);
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, gameState, raiseBetJButton, allInButton
    // EFFECTS:  process the input from the initial Betting Phase; when the raiseBetJButton is clicked, raise the bet
    // by $100. If "played" is false which means this Betting Stage is called from the Navigation Stage, call the
    // cleanOffLeftoverNavigationComponentsRaiseBet to clean off leftover components. If "played" is true which
    // means this Betting Stage is called from last Game Phase 5 and there are no components left from the Navigation
    // Stage, call the Game Phase 1 (Recurring Betting Stage) renderer directly; when the allInButton is clicked, raise
    // the bet by $100. If "played" is false, call the cleanOffLeftoverNavigationComponentsAllIn to clean off the
    // leftover components. If "played" is true, call the Game Phase 2 (Initial Dealing Stage) renderer directly
    @SuppressWarnings("methodlength")
    private void processInputGamePhase0(GameState gameState, GameRecords gameRecords, boolean loadedGameRecord,
                                       int currentGameRecordIndex, boolean played) {
        this.gameState = gameState;
        this.gameRecords = gameRecords;
        this.loadedGameRecord = loadedGameRecord;
        this.currentGameRecordIndex = currentGameRecordIndex;
        raiseBetJButton.addActionListener(e -> {
            clicking.playMusic();
            if (this.gameState.getPlayerAssets() >= 100 && !played) {
                this.gameState.setBettingBox(this.gameState.getBettingBox() + 100);
                this.gameState.setPlayerAssets(this.gameState.getPlayerAssets() - 100);
                cleanOffLeftoverNavigationComponentsRaiseBet(this.gameState, this.gameRecords, this.loadedGameRecord,
                        this.currentGameRecordIndex);
            } else if (this.gameState.getPlayerAssets() >= 100 && played) {
                this.gameState.setBettingBox(this.gameState.getBettingBox() + 100);
                this.gameState.setPlayerAssets(this.gameState.getPlayerAssets() - 100);
                renderGamePhase1(this.gameState, this.gameRecords, this.loadedGameRecord, this.currentGameRecordIndex);
            }
        });
        allInJButton.addActionListener(e -> {
            clicking.playMusic();
            if (this.gameState.getPlayerAssets() >= 100 && !played) {
                this.gameState.setBettingBox(this.gameState.getBettingBox() + this.gameState.getPlayerAssets());
                this.gameState.setPlayerAssets(0);
                cleanOffLeftoverNavigationComponentsAllIn(this.gameState, this.gameRecords, this.loadedGameRecord,
                        this.currentGameRecordIndex);
            } else if (this.gameState.getPlayerAssets() >= 100 && played) {
                this.gameState.setBettingBox(this.gameState.getBettingBox() + this.gameState.getPlayerAssets());
                this.gameState.setPlayerAssets(0);
                renderGamePhase2(this.gameState, this.gameRecords, this.loadedGameRecord, this.currentGameRecordIndex);
            }
        });
    }

    // EFFECTS:  if "loadedGameRecord" is true which means the user start the game by loading an existing game record,
    // remove the leftover components of the Resume Game page from this BlackJackGame JFrame and call for the Game
    // Phase 1 renderer; otherwise which means the user start the game by creating a new player, remove the leftover
    // components of the Start New Game page from this BlackJackGame JFrame and call for the Game Phase 1 renderer
    private void cleanOffLeftoverNavigationComponentsRaiseBet(GameState gameState, GameRecords gameRecords,
                                                              boolean loadedGameRecord, int currentGameRecordIndex) {
        if (loadedGameRecord) {
            removeNavigationBoardAndRightJoker();
            removeResumeGameComponents();
            removeBackToMainMenuJButton();
            renderGamePhase1(gameState, gameRecords, true, currentGameRecordIndex);
        } else {
            removeNavigationBoardAndRightJoker();
            removeStartNewGameComponents();
            removeBackToMainMenuJButton();
            renderGamePhase1(gameState, gameRecords, false, currentGameRecordIndex);
        }
    }

    // EFFECTS:  if "loadedGameRecord" is true which means the user start the game by loading an existing game record,
    // remove the leftover components of the Resume Game page from this BlackJackGame JFrame and call for the Game
    // Phase 2 renderer; otherwise which means the user start the game by creating a new player, remove the leftover
    // components of the Start New Game page from this BlackJackGame JFrame and call for the Game Phase 2 renderer
    private void cleanOffLeftoverNavigationComponentsAllIn(GameState gameState, GameRecords gameRecords,
                                                           boolean loadedGameRecord, int currentGameRecordIndex) {
        if (loadedGameRecord) {
            removeNavigationBoardAndRightJoker();
            removeResumeGameComponents();
            removeBackToMainMenuJButton();
            renderGamePhase2(gameState, gameRecords, true, currentGameRecordIndex);
        } else {
            removeNavigationBoardAndRightJoker();
            removeStartNewGameComponents();
            removeBackToMainMenuJButton();
            renderGamePhase2(gameState, gameRecords, false, currentGameRecordIndex);
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the Game Phase 1 (Recurring Betting Stage); remove all the Swing components left by the Game
    // Phase 0 and add all the Swing components of the Recurring Betting Stage; call for the corresponding input
    // processor of this render
    private void renderGamePhase1(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        removeBackgroundColorAndLeftJoker();
        removeGameInfoAndBettingStageJButtons();
        addGameInfoAndBettingStageJButtonsRecurringBetting();
        addBackgroundColorAndLeftJoker();
        repaint();
        processInputGamePhase1(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, gameState, raiseBetJButton, allInButton, stopBettingJButton
    // EFFECTS:  process the input from the Game Phase 1 (Recurring Betting Phase); when raiseBetJButton is clicked, if
    // player still has more than $100 assets, raise the Betting Box by $100, and if player only has $100 left, raise
    // the Betting Box by $100 and render the Game Phase 2 (Initial Dealing Stage); when allInButton is clicked, all in
    // and render the Game Phase 2; when stopBettingJButton is clicked, render the Game Phase 2
    @SuppressWarnings("methodlength")
    private void processInputGamePhase1(GameState gameState, GameRecords gameRecords, boolean loadedGameRecord,
                                       int currentGameRecordIndex) {
        this.gameState = gameState;
        this.gameRecords = gameRecords;
        this.loadedGameRecord = loadedGameRecord;
        this.currentGameRecordIndex = currentGameRecordIndex;
        raiseBetJButton.addActionListener(e -> {
            clicking.playMusic();
            if (this.gameState.getPlayerAssets() > 100) {
                this.gameState.setBettingBox(this.gameState.getBettingBox() + 100);
                this.gameState.setPlayerAssets(this.gameState.getPlayerAssets() - 100);
                renderGamePhase1(this.gameState, this.gameRecords, this.loadedGameRecord, this.currentGameRecordIndex);
            } else if (this.gameState.getPlayerAssets() == 100) {
                this.gameState.setBettingBox(this.gameState.getBettingBox() + 100);
                this.gameState.setPlayerAssets(this.gameState.getPlayerAssets() - 100);
                renderGamePhase2(this.gameState, this.gameRecords, this.loadedGameRecord, this.currentGameRecordIndex);
            }
        });
        allInJButton.addActionListener(e -> {
            clicking.playMusic();
            if (this.gameState.getPlayerAssets() >= 100) {
                this.gameState.setBettingBox(this.gameState.getBettingBox() + this.gameState.getPlayerAssets());
                this.gameState.setPlayerAssets(0);
                renderGamePhase2(this.gameState, this.gameRecords, this.loadedGameRecord, this.currentGameRecordIndex);
            }
        });
        stopBettingJButton.addActionListener(e -> {
            clicking.playMusic();
            renderGamePhase2(this.gameState, this.gameRecords,
                    this.loadedGameRecord, this.currentGameRecordIndex);
        });
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, gameState
    // EFFECTS:  render the Game Phase 2 (Initial Dealing Stage); initialize the game state which means picking four
    // random cards from the Cards Pool (52 Poker cards), assigning two cards to the Dealer (one is face-up, one is
    // face-down), assigning two cards to the Player (all are face-up); remove all the Swing components left by the
    // previous Phase; handle and render different situations including insurance case, blackjack case, normal case with
    // double, and normal case without double; call for the corresponding input processor in different situations
    @SuppressWarnings("methodlength")
    private void renderGamePhase2(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        // Initialize DealerCards, PlayerCards, and Cards52
        DealerCards dealerCards = gameState.getDealerCards();
        PlayerCards playerCards = gameState.getPlayerCards();
        Cards52 cardsPool = gameState.getCardsPool();
        // Add the first face-up card to DealerCards
        dealerCards.addDealerCard(cardsPool.pickRandomCard());
        // Add the second face-down card to DealerCards
        Card cardDealer2 = cardsPool.pickRandomCard();
        cardDealer2.setCardStatus("face-down");
        dealerCards.addDealerCard(cardDealer2);
        // Add the first and second face-up cards to PlayerCards
        playerCards.addPlayerCard(cardsPool.pickRandomCard());
        playerCards.addPlayerCard(cardsPool.pickRandomCard());

        this.gameState = new GameState(gameState.getPlayerName(), gameState.getPlayerAssets(),
                gameState.getPlayerRounds(), gameState.getBettingBox(), dealerCards, playerCards, cardsPool);

        removeGameInfoAndBettingStageJButtons();
        removeMessageAreaHelper();
        removeBackgroundColorAndLeftJoker();

        if (dealerCards.getList().get(0).getCardNum() == 1
                && gameState.getPlayerAssets() >= gameState.getBettingBox()) { // insurance case
            addMessageAreaShowingHelper(11);
            addInitialCardsAndCountersHelper(this.gameState);
            addInsuranceStageJButtonsHelper();
            addDealingStagePlayerInfoHelper();
            addBackgroundColorAndLeftJoker();
            repaint();
            processInputInsuranceStage(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if ((dealerCards.sumDealerCards() == 21) || (playerCards.sumPlayerCards() == 21)) { // blackjack case
            addMessageAreaShowingHelper(0);
            addDealingStagePlayerInfoHelper();
            renderBlackJackPhase(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex, true,
                    false);
        } else if (this.gameState.getPlayerAssets() >= this.gameState.getBettingBox()) { // normal case with double
            addMessageAreaShowingHelper(1);
            addInitialCardsAndCountersHelper(this.gameState);
            addAllDealingStageJButtonsNoWithdrawHelper();
            addDealingStagePlayerInfoHelper();
            addBackgroundColorAndLeftJoker();
            repaint();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else { // normal case without double
            addMessageAreaShowingHelper(1);
            addInitialCardsAndCountersHelper(this.gameState);
            addAllDealingStageJButtonsNoDoubleNoWithdrawHelper();
            addDealingStagePlayerInfoHelper();
            addBackgroundColorAndLeftJoker();
            repaint();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this
    // EFFECTS:  render the sub Phase (Insurance Stage) of the Game Phase 2 (Initial Dealing Stage) if the user
    // encountered the insurance case and also chose to make the insurance; remove the Swing components left by the Game
    // Phase 2; handle and render different situation including blackjack case, normal case with double, and normal case
    // without double; call for the corresponding input processor or the renderer of BlackJack Phase
    private void renderAfterInsurancePhase(GameState gameState, GameRecords gameRecords,
                                          boolean loadedGameRecord, int currentGameRecordIndex) {
        DealerCards dealerCards = gameState.getDealerCards();
        PlayerCards playerCards = gameState.getPlayerCards();
        if ((dealerCards.sumDealerCards() == 21) || (playerCards.sumPlayerCards() == 21)) { // blackjack case
            renderBlackJackPhase(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex, false,
                    false);
        } else if (this.gameState.getPlayerAssets() >= this.gameState.getBettingBox()) { // normal case with double
            removeDealingStagePlayerInfoHelper();
            addDealingStagePlayerInfoHelper();
            addAllDealingStageJButtonsNoWithdrawHelper();
            addAllCardsAndCountersBothDealerAndPlayerAutoRenderHelper(gameState);
            addBackgroundColorAndLeftJoker();
            repaint();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else { // normal case without double
            removeDealingStagePlayerInfoHelper();
            addDealingStagePlayerInfoHelper();
            addAllDealingStageJButtonsNoDoubleNoWithdrawHelper();
            addAllCardsAndCountersBothDealerAndPlayerAutoRenderHelper(gameState);
            addBackgroundColorAndLeftJoker();
            repaint();
            processInputGamePhase2(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, gameState
    // EFFECTS:  render the sub Phase (Black Jack Stage) of the Dealing Stage if the user encounter the Black Jack
    // situation during the Dealing Stage (including Initial Dealing Stage); remove the Swing components left by the
    // previous Game Phase; handle and render different situations including push case, dealer blackjack case, and
    // player blackjack case; call for the renderer of the Game Phase 5 (Closing Stage)
    @SuppressWarnings("methodlength")
    private void renderBlackJackPhase(GameState gameState, GameRecords gameRecords,
                                     boolean loadedGameRecord, int currentGameRecordIndex,
                                     boolean fromPhase2, boolean fromPhase34) {
        int sumDealerCards = gameState.getDealerCards().sumDealerCards();
        int sumPlayerCards = gameState.getPlayerCards().sumPlayerCards();
        gameState.setDealerSecondCardStatus("face-up");
        removeDealingStagePlayerInfoHelper();
        addDealingStagePlayerInfoHelper();
        if ((sumDealerCards == 21) && (sumPlayerCards == 21)) { // push case
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(3);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + gameState.getBettingBox());
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    fromPhase2, fromPhase34, false);
        } else if (sumDealerCards == 21) { // dealer blackjack case
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(12);
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    fromPhase2, fromPhase34,false);
        } else if (sumPlayerCards == 21) { // player blackjack case
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(5);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + 3 * gameState.getBettingBox());
            gameState.setBettingBox(0);
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    fromPhase2, fromPhase34, false);
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, hitJButton, doubleJButton, standJButton, gameState
    // EFFECTS:  process input from the Game Phase 2 (Initial Dealing Stage); when hitJButton is clicked, hit for one
    // more card and call for the renderer of Game Phase 3 (Dealing Stage | Player Hitting); when doubleJButton is
    // clicked, double the bet, hit for one more card, and call for the renderer of Game Phase 4 (Dealing Stage | Dealer
    // Dealing); when standJButton is clicked, call for the renderer of Game Phase 4
    @SuppressWarnings("methodlength")
    private void processInputGamePhase2(GameState gameState, GameRecords gameRecords, boolean loadedGameRecord,
                                       int currentGameRecordIndex) {
        PlayerCards playerCards = gameState.getPlayerCards();
        DealerCards dealerCards = gameState.getDealerCards();
        Cards52 cardsPool = gameState.getCardsPool();
        int bettingBox = gameState.getBettingBox();
        int playerAssets = gameState.getPlayerAssets();
        int changedAssets = playerAssets - bettingBox;
        int changedBettingBox = 2 * bettingBox;

        hitJButton.addActionListener(e -> {
            clicking.playMusic();
            playerCards.addPlayerCard(cardsPool.pickRandomCard());
            this.gameState = new GameState(gameState.getPlayerName(), gameState.getPlayerAssets(),
                    gameState.getPlayerRounds(), gameState.getBettingBox(), gameState.getDealerCards(),
                    playerCards, cardsPool);
            removeInitialCardsAndCountersHelper();
            removeDealingStageAllJButtonsHelper();
            removeBackgroundColorAndLeftJoker();
            renderGamePhase3(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        });
        doubleJButton.addActionListener(e -> {
            clicking.playMusic();
            playerCards.addPlayerCard(cardsPool.pickRandomCard());
            this.gameState = new GameState(gameState.getPlayerName(), changedAssets, gameState.getPlayerRounds(),
                    changedBettingBox, gameState.getDealerCards(), playerCards, cardsPool);
            removeInitialCardsAndCountersHelper();
            removeDealingStageAllJButtonsHelper();
            removeBackgroundColorAndLeftJoker();
            renderGamePhase4(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        });
        standJButton.addActionListener(e -> {
            clicking.playMusic();
            if (dealerCards.sumDealerCards() < 17) {
                dealerCards.addDealerCard(cardsPool.pickRandomCard());
            } else {
                gameState.setDealerSecondCardStatus("face-up");
            }
            removeInitialCardsAndCountersHelper();
            removeDealingStageAllJButtonsHelper();
            removeBackgroundColorAndLeftJoker();
            renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        });
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, insuranceJButton, skipInsuranceJButton, gameState
    // EFFECTS:  process input from the Insurance Stage of the Game Phase 2 (Initial Dealing Stage); when
    // insuranceJButton is clicked, make the insurance and call for the renderer of the After Insurance Phase; when
    // skipInsuranceJButton is clicked, make no insurance and call for the renderer of the After Insurance Phase
    @SuppressWarnings("methodlength")
    private void processInputInsuranceStage(GameState gameState, GameRecords gameRecords, boolean loadedGameRecord,
                                       int currentGameRecordIndex) {
        insuranceJButton.addActionListener(e -> {
            clicking.playMusic();
            removeMessageAreaHelper();
            if (gameState.getDealerCards().sumDealerCards() == 21) {
                gameState.setPlayerAssets(gameState.getPlayerAssets() + 2 * gameState.getBettingBox());
            } else if (gameState.getDealerCards().sumDealerCards() != 21) {
                gameState.setPlayerAssets(gameState.getPlayerAssets() - gameState.getBettingBox());
                addMessageAreaShowingHelper(13);
            }
            removeInitialCardsAndCountersHelper();
            removeInsuranceStageJButtonsHelper();
            removeBackgroundColorAndLeftJoker();
            renderAfterInsurancePhase(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        });
        skipInsuranceJButton.addActionListener(e -> {
            clicking.playMusic();
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(1);
            removeInitialCardsAndCountersHelper();
            removeInsuranceStageJButtonsHelper();
            removeBackgroundColorAndLeftJoker();
            renderAfterInsurancePhase(gameState,gameRecords, loadedGameRecord, currentGameRecordIndex);
        });
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, gameState
    // EFFECTS:  render the Game Phase 3 (Dealing Stage | Player Hitting); if player busts, call for the renderer of
    // the Game Phase 5 (Closing Stage); if 5-card Charlie & Blackjack, call for the renderer of the Game Phase 5; if
    // 5-card Charlie solely, call for the corresponding input processor; if Blackjack, call for the renderer of the
    // Black Jack Phase; else call for the corresponding input processor of this phase
    @SuppressWarnings("methodlength")
    private void renderGamePhase3(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        if (gameState.getPlayerCards().bustOrNot()) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(2);
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, true, false);
        } else if ((gameState.getPlayerCards().getList().size() >= 5)
                && (gameState.getPlayerCards().sumPlayerCards() == 21)) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(10);
            gameState.setBettingBox(gameState.getBettingBox() * 2);
            gameState.setPlayerAssets(gameState.getPlayerAssets()
                    + (gameState.getBettingBox() * (1 + gameState.getPlayerCards().getList().size())));
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, true, false);
        } else if (gameState.getPlayerCards().getList().size() >= 5) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(9);
            addAllCardsAndCountersBothDealerAndPlayerAutoRenderHelper(gameState);
            addAllDealingStageJButtonsNoDoubleNoStandHelper();
            addBackgroundColorAndLeftJoker();
            repaint();
            processInputGamePhase3(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (gameState.getPlayerCards().sumPlayerCards() == 21) {
            renderBlackJackPhase(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex, false,
                    true);
        } else {
            addAllCardsAndCountersBothDealerAndPlayerAutoRenderHelper(gameState);
            addAllDealingStageJButtonsNoDoubleNoWithdrawHelper();
            addBackgroundColorAndLeftJoker();
            repaint();
            processInputGamePhase3(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, hitJButton, standJButton, withdrawJButton, gameState
    // EFFECTS:  process the input from the Game Phase 3 (Dealing Stage | Player Hitting); when hitJButton is clocked,
    // hit for another card and call for the renderer of the Game Phase 3; when standJButton is clicked, call for the
    // renderer of the Game Phase 4 (Dealing Stage | Dealer Dealing); when withdrawJButton is clicked, withdraw bets and
    // bonus to the assets and call for the renderer of the Game Phase 5 (Closing Stage)
    @SuppressWarnings("methodlength")
    private void processInputGamePhase3(GameState gameState, GameRecords gameRecords,
                                       boolean loadedGameRecord, int currentGameRecordIndex) {
        PlayerCards playerCards = gameState.getPlayerCards();
        DealerCards dealerCards = gameState.getDealerCards();
        Cards52 cardsPool = gameState.getCardsPool();

        hitJButton.addActionListener(e -> {
            clicking.playMusic();
            playerCards.addPlayerCard(cardsPool.pickRandomCard());
            this.gameState = new GameState(gameState.getPlayerName(), gameState.getPlayerAssets(),
                    gameState.getPlayerRounds(), gameState.getBettingBox(), gameState.getDealerCards(),
                    playerCards, cardsPool);
            removeAllCardsAndCountersHelper();
            removeDealingStageAllJButtonsHelper();
            removeBackgroundColorAndLeftJoker();
            renderGamePhase3(this.gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        });
        standJButton.addActionListener(e -> {
            clicking.playMusic();
            if (dealerCards.sumDealerCards() < 17) {
                dealerCards.addDealerCard(cardsPool.pickRandomCard());
            } else {
                gameState.setDealerSecondCardStatus("face-up");
            }
            removeAllCardsAndCountersHelper();
            removeDealingStageAllJButtonsHelper();
            removeBackgroundColorAndLeftJoker();
            renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        });
        withdrawJButton.addActionListener(e -> {
            clicking.playMusic();
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(10);
            gameState.setPlayerAssets(gameState.getPlayerAssets()
                    + (gameState.getBettingBox() * (1 + gameState.getPlayerCards().getList().size())));
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            removeAllCardsAndCountersHelper();
            removeDealingStageAllJButtonsHelper();
            removeBackgroundColorAndLeftJoker();
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, false, false);
        });
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, gameState
    // EFFECTS:  render the Game Phase 4 (Dealing Stage | Dealer Dealing); if dealer or player busts, call for the
    // render of the Game Phase 5 (Closing Stage); if Blackjack, call for the Black Jack Phase renderer; if the sum of
    // dealer cards is less than 17 and less than 5 cards, keep hitting dealer cards until the sum is more than or equal
    // to 17 by calling for the processor of this renderer; if dealer has 5 cards without busting, call for the Game
    // Phase 5; if the sum of dealer cards is more than 17, compare dealer cards and player cards and call for the
    // Game Phase 5
    @SuppressWarnings("methodlength")
    private void renderGamePhase4(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex) {
        int dealerPoints = gameState.getDealerCards().sumDealerCards();
        int playerPoints = gameState.getPlayerCards().sumPlayerCards();
        if (gameState.getDealerCards().bustOrNot()) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(6);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + 2 * gameState.getBettingBox());
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, false, false);
        } else if (gameState.getPlayerCards().bustOrNot()) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(2);
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, false, false);
        } else if (gameState.getPlayerCards().sumPlayerCards() == 21) {
            // handle the case of double + blackjack
            renderBlackJackPhase(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex, false,
                    true);
        } else if ((dealerPoints < 17) && (gameState.getDealerCards().getList().size() < 5)) {
            processGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
        } else if (gameState.getDealerCards().getList().size() == 5) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(4);
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, false, false);
        } else if ((dealerPoints >= 17) && (dealerPoints > playerPoints)) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(4);
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, false, false);
        } else if ((dealerPoints >= 17) && (dealerPoints == playerPoints)) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(3);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + gameState.getBettingBox());
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, false, false);
        } else if (dealerPoints >= 17) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(7);
            gameState.setPlayerAssets(gameState.getPlayerAssets() + 2 * gameState.getBettingBox());
            gameState.setBettingBox(0);
            gameState.setDealerSecondCardStatus("face-up");
            renderGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    false, false, false);
        }
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, gameState
    // EFFECTS:  keep the renderer of the Game Phase 4 (Dealing Stage | Dealer Dealing) recurring
    private void processGamePhase4(GameState gameState, GameRecords gameRecords,
                                  boolean loadedGameRecord, int currentGameRecordIndex) {
        DealerCards dealerCards = gameState.getDealerCards();
        Cards52 cardsPool = gameState.getCardsPool();
        dealerCards.addDealerCard(cardsPool.pickRandomCard());
        renderGamePhase4(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex);
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, newRoundJButton, saveGameRecordJButton, mainMenuJButton, gameState
    // EFFECTS:  render the Game Phase 5 (Closing Stage); if user still have assets, render the Closing Phase and
    // provide navigation to start new round, save game record, and go back to the main menu; if user lost all assets
    // and user start the game by loading a game record, delete this record, render the Closing Phase and provide
    // navigation to back to the main menu; if user started a new game and lost all assets, just render the Closing
    // Phase and provide the navigation to go back to the main menu; if this renderer is called from the Game Phase 2,
    // just render the initial four cards; if this renderer is called from the Game Phase 3 or 4, render all the cards
    // hold by both parties and add Speeding Animation Effect for all the new cards hold by the both parties; if the
    // user just clicked to save the record and refresh the Game Page, just normally render all the cards without
    // animation; otherwise render all the cards with only Dealer's new cards have Speeding Animation Effect; call for
    // the corresponding processor of this renderer
    @SuppressWarnings("methodlength")
    private void renderGamePhase5(GameState gameState, GameRecords gameRecords,
                                 boolean loadedGameRecord, int currentGameRecordIndex,
                                 boolean fromPhase2, boolean fromPhase34, boolean saved) {
        if (gameState.getPlayerAssets() == 0 && loadedGameRecord) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(8);
            gameRecords.deleteRecord(currentGameRecordIndex);
            new JsonWriter(gameRecords,"./data/gameRecords.json").write();
            newRoundJButton = new NewRoundJButton(1172, 800);
            newRoundJButton.setStatus("Hiding");
            add(newRoundJButton);
            saveGameRecordJButton = new SaveGameRecordJButton(1172, 900);
            saveGameRecordJButton.setStatus("Hiding");
            add(saveGameRecordJButton);
        } else if (gameState.getPlayerAssets() == 0) {
            removeMessageAreaHelper();
            addMessageAreaShowingHelper(8);
            newRoundJButton = new NewRoundJButton(1172, 800);
            newRoundJButton.setStatus("Hiding");
            add(newRoundJButton);
            saveGameRecordJButton = new SaveGameRecordJButton(1172, 900);
            saveGameRecordJButton.setStatus("Hiding");
            add(saveGameRecordJButton);
        } else {
            newRoundJButton = new NewRoundJButton(1172, 350);
            newRoundJButton.setStatus("Showing");
            add(newRoundJButton);
            saveGameRecordJButton = new SaveGameRecordJButton(1172, 430);
            saveGameRecordJButton.setStatus("Showing");
            add(saveGameRecordJButton);
        }
        if (fromPhase2) {
            addInitialCardsAndCountersHelper(gameState);
        } else if (fromPhase34) {
            addAllCardsAndCountersBothDealerAndPlayerAutoRenderHelper(gameState);
        } else if (saved) {
            addAllCardsAndCountersNormalRenderHelper(gameState);
        } else {
            addAllCardsAndCountersOnlyDealerAutoRenderHelper(gameState);
        }
        removeDealingStagePlayerInfoHelper();
        addDealingStagePlayerInfoHelper();
        mainMenuJButton = new MainMenuJButton(1172, 510);
        mainMenuJButton.setStatus("Showing");
        add(mainMenuJButton);
        addBackgroundColorAndLeftJoker();
        repaint();
        processInputGamePhase5(gameState, gameRecords, loadedGameRecord, currentGameRecordIndex, fromPhase2);
    }

    // REQUIRES: currentGameRecordIndex is smaller than or equal to the length of the game records list
    // MODIFIES: this, newRoundJButton, saveGameRecordJButton, mainMenuJButton
    // EFFECTS: process inputs from the Game Phase 5 (Closing Stage); when newRoundJButton is clicked, start a new round
    // and call for the Game Phase 0 (Initial Betting Stage) render; when saveGameRecordJButton is clicked, save or
    // overwrite the game record and come back to the same page; when mainMenuJButton is clicked, call the Navigation
    // Stage renderer
    @SuppressWarnings("methodlength")
    private void processInputGamePhase5(GameState gameState, GameRecords gameRecords,
                                       boolean loadedGameRecord, int currentGameRecordIndex, boolean fromPhase2) {
        Player currentPlayer = new Player(gameState.getPlayerName(),
                gameState.getPlayerAssets(),gameState.getPlayerRounds() + 1);
        newRoundJButton.addActionListener(e -> {
            clicking.playMusic();
            if (fromPhase2) {
                removeInitialCardsAndCountersHelper();
            } else {
                removeAllCardsAndCountersHelper();
            }
            removeMessageAreaHelper();
            removeAllClosingJButtonsHelper();
            removeDealingStagePlayerInfoHelper();
            renderGamePhase0(currentPlayer, gameRecords, loadedGameRecord, currentGameRecordIndex,
                    true, true);
        });
        saveGameRecordJButton.addActionListener(e -> {
            clicking.playMusic();
            if (loadedGameRecord) {
                gameRecords.saveOldRecord(currentGameRecordIndex, currentPlayer);
                new JsonWriter(gameRecords,"./data/gameRecords.json").write();
                if (fromPhase2) {
                    removeInitialCardsAndCountersHelper();
                } else {
                    removeAllCardsAndCountersHelper();
                }
                removeAllClosingJButtonsHelper();
                removeBackgroundColorAndLeftJoker();
                renderGamePhase5(gameState, gameRecords, true,
                        currentGameRecordIndex, false, false, true);
            } else {
                gameRecords.addRecord(currentPlayer);
                new JsonWriter(gameRecords,"./data/gameRecords.json").write();
                this.currentGameRecordIndex = gameRecords.getList().size() - 1;
                if (fromPhase2) {
                    removeInitialCardsAndCountersHelper();
                } else {
                    removeAllCardsAndCountersHelper();
                }
                removeAllClosingJButtonsHelper();
                removeBackgroundColorAndLeftJoker();
                renderGamePhase5(gameState, gameRecords, true,
                        this.currentGameRecordIndex, false, false,true);
            }
        });
        mainMenuJButton.addActionListener(e -> {
            clicking.playMusic();
            this.gameRecords = gameRecords;
            if (fromPhase2) {
                removeInitialCardsAndCountersHelper();
            } else {
                removeAllCardsAndCountersHelper();
            }
            removeMessageAreaHelper();
            removeAllClosingJButtonsHelper();
            removeDealingStagePlayerInfoHelper();
            removeBackgroundColorAndLeftJoker();
            renderNavigation(placeHolder, this.gameRecords, 0);
        });
    }

    // Navigation Stage Rendering Helpers

    // MODIFIES: this, mainMenuHeaderJLabel, startNewGameJButton, resumeGameJButton, playerRankingJButton,
    // exitGameJButton
    // EFFECTS:  add the Main Menu Header JLabel, Start New Game JButton, Resume Game JButton, Player Ranking JButton,
    // Exit Game JButton to this BlackJackGame JFrame
    private void addMainMenuComponents() {
        mainMenuHeaderJLabel = new MainMenuHeaderJLabel();
        mainMenuHeaderJLabel.setStatus("Showing");
        add(mainMenuHeaderJLabel);
        startNewGameJButton = new StartNewGameJButton(485, 350, 350);
        startNewGameJButton.setStatus("Showing");
        add(startNewGameJButton);
        resumeGameJButton = new ResumeGameJButton(485, 450, 450);
        resumeGameJButton.setStatus("Showing");
        add(resumeGameJButton);
        playerRankingJButton = new PlayerRankingJButton(485, 550, 550);
        playerRankingJButton.setStatus("Showing");
        add(playerRankingJButton);
        exitGameJButton = new ExitGameJButton(485, 650, 650);
        exitGameJButton.setStatus("Showing");
        add(exitGameJButton);
    }

    // MODIFIES: this
    // EFFECTS:  remove the Main Menu Header JLabel, Start New Game JButton, Resume Game JButton, Player Ranking
    // JButton, Exit Game JButton from this BlackJackGame JFrame
    private void removeMainMenuComponents() {
        mainMenuHeaderJLabel.stopTimer();
        startNewGameJButton.stopTimer();
        resumeGameJButton.stopTimer();
        playerRankingJButton.stopTimer();
        exitGameJButton.stopTimer();
        remove(mainMenuHeaderJLabel);
        remove(startNewGameJButton);
        remove(resumeGameJButton);
        remove(playerRankingJButton);
        remove(exitGameJButton);
    }

    // MODIFIES: this, startNewGameHeaderJLabel, startNewGameJTextField, submitNewPlayerJButton
    // EFFECTS:  add the Start New Game Header JLabel, Start New Game JTextField, Submit New Player JButton to this
    // BlackJackGame JFrame
    private void addStartNewGameComponents() {
        startNewGameHeaderJLabel = new StartNewGameHeaderJLabel();
        startNewGameHeaderJLabel.setCorY(160);
        startNewGameHeaderJLabel.setStatus("Showing");
        add(startNewGameHeaderJLabel);
        startNewGameJTextField = new StartNewGameJTextField();
        startNewGameJTextField.setCorY(350);
        startNewGameJTextField.setStatus("Showing");
        startNewGameJTextField.setText(""); // Clear up the TextField whenever we open the Start New Game Page
        add(startNewGameJTextField);
        submitNewPlayerJButton = new SubmitNewPlayerJButton(485, 430, 430);
        submitNewPlayerJButton.setStatus("Showing");
        add(submitNewPlayerJButton);
    }

    // MODIFIES: this
    // EFFECTS:  remove the Start New Game Header JLabel, Start New Game JTextField, Submit New Player JButton from
    // this BlackJackGame JFrame
    private void removeStartNewGameComponents() {
        startNewGameHeaderJLabel.stopTimer();
        startNewGameJTextField.stopTimer();
        submitNewPlayerJButton.stopTimer();
        remove(startNewGameHeaderJLabel);
        remove(startNewGameJTextField);
        remove(submitNewPlayerJButton);
    }

    // MODIFIES: this, loadRecordJButton, deleteRecordJButton
    // EFFECTS:  add the Load Record JButton and Delete Record JButton to this BlackJackGame JFrame
    private void addResumeGameComponents() {
        loadRecordJButton = new LoadRecordJButton(485, 520, 520);
        loadRecordJButton.setStatus("Showing");
        add(loadRecordJButton);
        deleteRecordJButton = new DeleteRecordJButton(485, 595, 595);
        deleteRecordJButton.setStatus("Showing");
        add(deleteRecordJButton);
    }

    // MODIFIES: this
    // EFFECTS:  remove the Load Record JButton and Delete Record JButton from this BlackJackGame JFrame
    private void removeResumeGameComponents() {
        gameRecordsJLabel.stopTimer();
        loadRecordJButton.stopTimer();
        deleteRecordJButton.stopTimer();
        remove(gameRecordsJLabel);
        remove(loadRecordJButton);
        remove(deleteRecordJButton);
    }

    // MODIFIES: this, playerRankingHeaderJLabel
    // EFFECTS:  add the Player Ranking Header JLabel to this BlackJackGame JFrame
    private void addPlayerRankingComponents() {
        playerRankingHeaderJLabel = new PlayerRankingHeaderJLabel();
        playerRankingHeaderJLabel.setStatus("Showing");
        add(playerRankingHeaderJLabel);
    }

    // MODIFIES: this
    // EFFECTS:  remove the Player Ranking Header JLabel from this BlackJackGame JFrame
    private void removePlayerRankingComponents() {
        playerRankingHeaderJLabel.stopTimer();
        playerRankingJLabel.stopTimer();
        remove(playerRankingHeaderJLabel);
        remove(playerRankingJLabel);
    }

    // MODIFIES: this, backToMainMenuJButton
    // EFFECTS:  add the Back To Main Menu JButton to this BlackJackGame JFrame
    private void addBackToMainMenuJButton() {
        backToMainMenuJButton = new BackToMainMenuJButton(485, 670, 670);
        backToMainMenuJButton.setStatus("Showing");
        add(backToMainMenuJButton);
    }

    // MODIFIES: this
    // EFFECTS:  remove the Back To Main Menu JButton from this BlackJackGame JFrame
    private void removeBackToMainMenuJButton() {
        backToMainMenuJButton.stopTimer();
        remove(backToMainMenuJButton);
    }

    // MODIFIES: this, navigationBoardJLabel, rightJokerJLabel
    // EFFECTS:  add the Navigation Board JLabel and Right Joker JLabel to this BlackJackGame JFrame
    private void addNavigationBoardAndRightJoker() {
        navigationBoardJLabel = new NavigationBoardJLabel();
        navigationBoardJLabel.setCorY(0);
        navigationBoardJLabel.setStatus("Showing");
        add(navigationBoardJLabel);
        rightJokerJLabel = new RightJokerJLabel();
        rightJokerJLabel.setCorX(0);
        rightJokerJLabel.restoreVelX();
        rightJokerJLabel.setStatus("Showing");
        add(rightJokerJLabel);
    }

    // MODIFIES: this
    // EFFECTS:  remove the Navigation Board JLabel and Right Joker JLabel from this BlackJackGame JFrame
    private void removeNavigationBoardAndRightJoker() {
        navigationBoardJLabel.stopTimer();
        rightJokerJLabel.stopTimer();
        remove(navigationBoardJLabel);
        remove(rightJokerJLabel);
    }

    // MODIFIES: this, leftJokerJLabel
    // EFFECTS:  add the Left Joker JLabel and Background Color JPanel to this BlackJackGame JFrame
    private void addBackgroundColorAndLeftJoker() {
        leftJokerJLabel = new LeftJokerJLabel();
        leftJokerJLabel.setStatus("Showing");
        add(leftJokerJLabel);
        backgroundColorJPanel = new BackgroundColorJPanel();
        add(backgroundColorJPanel);
    }

    // MODIFIES: this
    // EFFECTS:  remove the Left Joker JLabel and Background Color JPanel from this BlackJackGame JFrame
    private void removeBackgroundColorAndLeftJoker() {
        leftJokerJLabel.stopTimer();
        remove(leftJokerJLabel);
        remove(backgroundColorJPanel);
    }

    // General Game Phases Rendering Helpers

    // REQUIRES: messageIndex is within the range of [0, 13]
    // MODIFIES: this, messageAreaTextJLabel, messageAreaBoardJLabel
    // EFFECTS:  add messageAreaTextJLabel and messageAreaBoardJLabel to this BlackJackGame JFrame; set the status of
    // messageAreaTextJLabel and messageAreaBoardJLabel as "Showing"
    private void addMessageAreaShowingHelper(int messageIndex) {
        messageAreaTextJLabel = new MessageAreaTextJLabel(messageIndex);
        messageAreaTextJLabel.setStatus("Showing");
        add(messageAreaTextJLabel);
        messageAreaBoardJLabel = new MessageAreaBoardJLabel();
        messageAreaBoardJLabel.setStatus("Showing");
        add(messageAreaBoardJLabel);
    }

    // EFFECTS:  remove messageAreaTextJLabel and messageAreaBoardJLabel from this BlackJackGame JFrame
    private void removeMessageAreaHelper() {
        remove(messageAreaTextJLabel);
        remove(messageAreaBoardJLabel);
    }

    // Betting Stage Rendering Helpers (Game Phase 0 and Game Phase 1)

    // MODIFIES: this, playerInfoJLabel, playerInfoBoardJLabel, bettingAreaJLabel, raiseBetJButton, allInButton,
    // stopBettingJButton
    // EFFECTS:  add the Player Info JLabel, Player Info Board JLabel, Betting Area JLabel, Raise Bet JButton, All-in
    // JButton, and Stop Betting JButton to this BlackJackGame JFrame; set the Stop Betting JButton as "Hiding" since it
    // is just a placeholder here; set other components to be "Showing" since we need to render the GUI as user starts
    // a new round which means the Game Phase 0 renderer is called from Game Phase 5
    private void addGameInfoAndBettingStageJButtonsFromGamePhase5() {
        playerInfoJLabel = new PlayerInfoTextJLabel(gameState);
        playerInfoJLabel.setStatus("Showing");
        add(playerInfoJLabel);
        playerInfoBoardJLabel = new PlayerInfoBoardJLabel();
        playerInfoBoardJLabel.setStatus("Showing");
        add(playerInfoBoardJLabel);
        bettingAreaJLabel = new BettingAreaJLabel(gameState);
        bettingAreaJLabel.setStatus("Showing");
        add(bettingAreaJLabel);
        raiseBetJButton = new RaiseBetJButton(1172,350);
        raiseBetJButton.setStatus("Showing");
        add(raiseBetJButton);
        allInJButton = new AllInJButton(1172,430);
        allInJButton.setStatus("Showing");
        add(allInJButton);
        stopBettingJButton = new StopBettingJButton(1172,510);
        stopBettingJButton.setStatus("Hiding");
        add(stopBettingJButton);
    }

    // MODIFIES: this, playerInfoJLabel, playerInfoBoardJLabel, bettingAreaJLabel, raiseBetJButton, allInButton,
    // stopBettingJButton
    // EFFECTS:  add the Player Info JLabel, Player Info Board JLabel, Betting Area JLabel, Raise Bet JButton, All-in
    // JButton, and Stop Betting JButton to this BlackJackGame JFrame; set the Stop Betting JButton as "Hiding" since it
    // is just a placeholder here; set other components to be "Delay Showing" to avoid the overlapping with "Moving
    // Right" Right Joker since we are calling Game Phase 0 renderer from previous Navigation stage
    private void addGameInfoAndBettingStageJButtonsFromNavigationStage() {
        playerInfoJLabel = new PlayerInfoTextJLabel(gameState);
        playerInfoJLabel.setStatus("Delay Showing");
        add(playerInfoJLabel);
        playerInfoBoardJLabel = new PlayerInfoBoardJLabel();
        playerInfoBoardJLabel.setStatus("Delay Showing");
        add(playerInfoBoardJLabel);
        bettingAreaJLabel = new BettingAreaJLabel(gameState);
        bettingAreaJLabel.setStatus("Delay Showing");
        add(bettingAreaJLabel);
        raiseBetJButton = new RaiseBetJButton(1172,350);
        raiseBetJButton.setStatus("Delay Showing");
        add(raiseBetJButton);
        allInJButton = new AllInJButton(1172,430);
        allInJButton.setStatus("Delay Showing");
        add(allInJButton);
        stopBettingJButton = new StopBettingJButton(1172,510);
        stopBettingJButton.setStatus("Hiding");
        add(stopBettingJButton);
    }

    // MODIFIES: this, playerInfoJLabel, playerInfoBoardJLabel, bettingAreaJLabel, raiseBetJButton, allInButton,
    // stopBettingJButton
    // EFFECTS:  add the Player Info JLabel, Player Info Board JLabel, Betting Area JLabel, Raise Bet JButton, All-in
    // JButton, and Stop Betting JButton to this BlackJackGame JFrame; set all components as "Showing" since we are
    // calling Game Phase 1 renderer from previous Game Phase 0 which means we are in the Recurring Betting Stage
    private void addGameInfoAndBettingStageJButtonsRecurringBetting() {
        playerInfoJLabel = new PlayerInfoTextJLabel(gameState);
        playerInfoJLabel.setStatus("Showing");
        add(playerInfoJLabel);
        playerInfoBoardJLabel = new PlayerInfoBoardJLabel();
        playerInfoBoardJLabel.setStatus("Showing");
        add(playerInfoBoardJLabel);
        bettingAreaJLabel = new BettingAreaJLabel(gameState);
        bettingAreaJLabel.setStatus("Showing");
        add(bettingAreaJLabel);
        raiseBetJButton = new RaiseBetJButton(1172,350);
        raiseBetJButton.setStatus("Showing");
        add(raiseBetJButton);
        allInJButton = new AllInJButton(1172,430);
        allInJButton.setStatus("Showing");
        add(allInJButton);
        stopBettingJButton = new StopBettingJButton(1172,510);
        stopBettingJButton.setStatus("Showing");
        add(stopBettingJButton);
    }

    // MODIFIES: this
    // EFFECTS:  remove the Player Info JLabel, Player Info Board JLabel, Betting Area JLabel, Raise Bet JButton,
    // All-in JButton, and Stop Betting JButton from this BlackJackGame JFrame
    private void removeGameInfoAndBettingStageJButtons() {
        playerInfoJLabel.stopTimer();
        playerInfoBoardJLabel.stopTimer();
        bettingAreaJLabel.stopTimer();
        raiseBetJButton.stopTimer();
        allInJButton.stopTimer();
        stopBettingJButton.stopTimer();
        remove(playerInfoJLabel);
        remove(playerInfoBoardJLabel);
        remove(bettingAreaJLabel);
        remove(raiseBetJButton);
        remove(allInJButton);
        remove(stopBettingJButton);
    }

    // Dealing Stage Rendering Helper Including Insurance Stage (Game Phase 2, Game Phase 3, and Game Phase 4)

    @SuppressWarnings("methodlength")
    // MODIFIES: this, dealerCardJLabel2, dealerCardJLabel1, dealerCardsCounterJLabel, playerCardJLabel2,
    // playerCardJLabel1, playerCardsCounterJLabel
    // EFFECTS:  help the Game Phase 2 (Initial Dealing Stage) add dealerCardJLabel1, dealerCardJLabel2,
    // dealerCardsCounterJLabel, playerCardJLabel1, playerCardJLabel2, and playerCardsCounterJLabel to this
    // BlackJackGame JFrame; set the status of the first card of both Dealer and Player as "Moving Down"; set the status
    // of the second card of both Dealer and Player as "Delay Moving Down"; set the status of both Counter as "Showing"
    private void addInitialCardsAndCountersHelper(GameState gameState) {
        dealerCardJLabel2 = new DealerCardJLabel2(gameState, 641, -740, 0,1);
        dealerCardJLabel2.setStatus("Delay Moving Down");
        add(dealerCardJLabel2);
        dealerCardJLabel1 = new DealerCardJLabel1(gameState,615, -740, 0, 0);
        dealerCardJLabel1.setStatus("Moving Down");
        add(dealerCardJLabel1);
        dealerCardsCounterJLabel = new DealerCardsCounterJLabel(gameState, 660, 25);
        dealerCardsCounterJLabel.setStatus("Showing");
        add(dealerCardsCounterJLabel);
        playerCardJLabel2 = new PlayerCardJLabel2(gameState, 641, 465, 816,1);
        playerCardJLabel2.setStatus("Delay Moving Up");
        add(playerCardJLabel2);
        playerCardJLabel1 = new PlayerCardJLabel1(gameState, 615, 465, 816, 0);
        playerCardJLabel1.setStatus("Moving Up");
        add(playerCardJLabel1);
        playerCardsCounterJLabel = new PlayerCardsCounterJLabel(gameState, 660, 720);
        playerCardsCounterJLabel.setStatus("Showing");
        add(playerCardsCounterJLabel);
    }

    // EFFECTS:  remove dealerCardJLabel1, dealerCardJLabel2, dealerCardsCounterJLabel, playerCardJLabel1,
    // playerCardJLabel2, and playerCardsCounterJLabel from this BlackJackGame JFrame
    private void removeInitialCardsAndCountersHelper() {
        remove(dealerCardJLabel2);
        remove(dealerCardJLabel1);
        remove(dealerCardsCounterJLabel);
        remove(playerCardJLabel2);
        remove(playerCardJLabel1);
        remove(playerCardsCounterJLabel);
    }

    // MODIFIES: this, insuranceJButton, skipInsuranceJButton
    // EFFECTS:  help the Game Phase 2 (Initial Dealing Stage) add insuranceJButton and skipInsuranceJButton to this
    // BlackJackGame JFrame; set the status of insuranceJButton and skipInsuranceJButton as "Showing"
    private void addInsuranceStageJButtonsHelper() {
        insuranceJButton = new InsuranceJButton(1172, 350);
        insuranceJButton.setStatus("Showing");
        add(insuranceJButton);
        skipInsuranceJButton = new SkipInsuranceJButton(1172, 430);
        skipInsuranceJButton.setStatus("Showing");
        add(skipInsuranceJButton);
    }

    // EFFECTS:  remove insuranceJButton and skipInsuranceJButton from this BlackJackGame JFrame
    private void removeInsuranceStageJButtonsHelper() {
        remove(insuranceJButton);
        remove(skipInsuranceJButton);
    }

    // MODIFIES: this, hitJButton, doubleJButton, standJButton, withdrawJButton
    // EFFECTS:  render the JButtons when there should be a Double option; add hitJButton, doubleJButton, standJButton,
    // and withdrawJButton to this BlackJackGame JFrame; set the status of hitJButton, doubleJButton, and standJButton
    // as "Showing"; set the status of withdrawJButton as "Hiding"
    private void addAllDealingStageJButtonsNoWithdrawHelper() {
        hitJButton = new HitJButton(1172, 350);
        hitJButton.setStatus("Showing");
        add(hitJButton);
        doubleJButton = new DoubleJButton(1172, 430);
        doubleJButton.setStatus("Showing");
        add(doubleJButton);
        standJButton = new StandJButton(1172, 510);
        standJButton.setStatus("Showing");
        add(standJButton);
        withdrawJButton = new WithdrawJButton(1172, 590);
        withdrawJButton.setStatus("Hiding");
        add(withdrawJButton);
    }

    // MODIFIES: this, hitJButton, doubleJButton, standJButton, withdrawJButton
    // EFFECTS:  render the JButtons when there should not be a Double option; add hitJButton, doubleJButton,
    // standJButton, and withdrawJButton to this BlackJackGame JFrame; set the status of hitJButton and standJButton
    // as "Showing"; set the status of doubleJButton and withdrawJButton as "Hiding"
    private void addAllDealingStageJButtonsNoDoubleNoWithdrawHelper() {
        hitJButton = new HitJButton(1172, 350);
        hitJButton.setStatus("Showing");
        add(hitJButton);
        doubleJButton = new DoubleJButton(1172, 430);
        doubleJButton.setStatus("Hiding");
        add(doubleJButton);
        standJButton = new StandJButton(1172, 510);
        standJButton.setStatus("Showing");
        add(standJButton);
        withdrawJButton = new WithdrawJButton(1172, 590);
        withdrawJButton.setStatus("Hiding");
        add(withdrawJButton);
    }

    // MODIFIES: this, hitJButton, doubleJButton, standJButton, withdrawJButton
    // EFFECTS:  render the JButtons when Player is 5-card Charlie and not reach Black Jack; add hitJButton,
    // doubleJButton, standJButton, and withdrawJButton to this BlackJackGame JFrame; set the status of hitJButton and
    // withdrawJButton as "Showing"; set the status of doubleJButton and standJButton as "Hiding"
    private void addAllDealingStageJButtonsNoDoubleNoStandHelper() {
        hitJButton = new HitJButton(1172, 350);
        hitJButton.setStatus("Showing");
        add(hitJButton);
        doubleJButton = new DoubleJButton(1172, 430);
        doubleJButton.setStatus("Hiding");
        add(doubleJButton);
        standJButton = new StandJButton(1172, 510);
        standJButton.setStatus("Hiding");
        add(standJButton);
        withdrawJButton = new WithdrawJButton(1172, 590);
        withdrawJButton.setStatus("Showing");
        add(withdrawJButton);
    }

    // EFFECTS:  remove hitJButton, doubleJButton, standJButton, and withdrawJButton from this BlackJackGame JFrame
    private void removeDealingStageAllJButtonsHelper() {
        remove(hitJButton);
        remove(doubleJButton);
        remove(withdrawJButton);
        remove(standJButton);
    }

    // MODIFIES: this, bettingAreaJLabel, playerInfoJLabel, playerInfoBoardJLabel
    // EFFECTS:  render information components in the right-up corner of the JFrame during the Dealing Stage; add
    // bettingAreaJLabel, playerInfoJLabel, and playerInfoBoardJLabel to this BlackJackGame JFrame; set the status of
    // bettingAreaJLabel, playerInfoJLabel, and playerInfoBoardJLabel as "Showing"
    private void addDealingStagePlayerInfoHelper() {
        bettingAreaJLabel = new BettingAreaJLabel(gameState);
        bettingAreaJLabel.setStatus("Showing");
        add(bettingAreaJLabel);
        playerInfoJLabel = new PlayerInfoTextJLabel(gameState);
        playerInfoJLabel.setStatus("Showing");
        add(playerInfoJLabel);
        playerInfoBoardJLabel = new PlayerInfoBoardJLabel();
        playerInfoBoardJLabel.setStatus("Showing");
        add(playerInfoBoardJLabel);
    }

    // EFFECTS:  remove bettingAreaJLabel, playerInfoJLabel, and playerInfoBoardJLabel from this BlackJackGame JFrame
    private void removeDealingStagePlayerInfoHelper() {
        remove(bettingAreaJLabel);
        remove(playerInfoJLabel);
        remove(playerInfoBoardJLabel);
    }

    // Both Dealing Stage and Closing Stage Rendering Helpers

    @SuppressWarnings("methodlength")
    // MODIFIES: this, dealerCardsCounterJLabel, playerCardsCounterJLabel, dealerCardJLabel3, dealerCardJLabel4,
    // dealerCardJLabel5, playerCardJLabel3, playerCardJLabel4, playerCardJLabel5, playerCardJLabel6, playerCardJLabel7,
    // playerCardJLabel8, playerCardJLabel9, playerCardJLabel10, playerCardJLabel11
    // EFFECTS:  help both the Dealing Stage and the Closing Stage automatically detect the change of both parties'
    // cards, render all the cards and counters, and add Speeding Animation Effect to all the new cards except initial
    // cards
    private void addAllCardsAndCountersBothDealerAndPlayerAutoRenderHelper(GameState gameState) {
        int dealerMovingIndex = gameState.getDealerCards().getList().size() - 1;
        int playerMovingIndex = gameState.getPlayerCards().getList().size() - 1;
        dealerCardJLabel5 = new DealerCardJLabel5(gameState, 719, -740, 858, 4);
        dealerCardJLabel4 = new DealerCardJLabel4(gameState, 693, -740, 858, 3);
        dealerCardJLabel3 = new DealerCardJLabel3(gameState, 667, -740, 858, 2);
        dealerCardJLabel2 = new DealerCardJLabel2(gameState, 641, -740, 858,1);
        dealerCardJLabel1 = new DealerCardJLabel1(gameState,615, -740, 858, 0);
        dealerCardsCounterJLabel = new DealerCardsCounterJLabel(gameState, 660, 25);
        dealerCardsCounterJLabel.setStatus("Showing");
        playerCardJLabel11 = new PlayerCardJLabel11(gameState, 875, 465, 816, 10);
        playerCardJLabel10 = new PlayerCardJLabel10(gameState, 849, 465, 816, 9);
        playerCardJLabel9 = new PlayerCardJLabel9(gameState, 823, 465, 816, 8);
        playerCardJLabel8 = new PlayerCardJLabel8(gameState, 797, 465, 816, 7);
        playerCardJLabel7 = new PlayerCardJLabel7(gameState, 771, 465, 816, 6);
        playerCardJLabel6 = new PlayerCardJLabel6(gameState, 745, 465, 816, 5);
        playerCardJLabel5 = new PlayerCardJLabel5(gameState, 719, 465, 816, 4);
        playerCardJLabel4 = new PlayerCardJLabel4(gameState, 693, 465, 816, 3);
        playerCardJLabel3 = new PlayerCardJLabel3(gameState,667, 465, 816, 2);
        playerCardJLabel2 = new PlayerCardJLabel2(gameState, 641, 465, 816,1);
        playerCardJLabel1 = new PlayerCardJLabel1(gameState, 615, 465, 816, 0);
        playerCardsCounterJLabel = new PlayerCardsCounterJLabel(gameState, 660, 720);
        playerCardsCounterJLabel.setStatus("Showing");
        switch (dealerMovingIndex) {
            case 2:
                dealerCardJLabel3.setCorY(0);
                dealerCardJLabel3.setStatus("Moving Down");
                break;
            case 3:
                dealerCardJLabel4.setCorY(0);
                dealerCardJLabel4.setStatus("Moving Down");
                break;
            case 4:
                dealerCardJLabel5.setCorY(0);
                dealerCardJLabel5.setStatus("Moving Down");
                break;
        }
        switch (playerMovingIndex) {
            case 2:
                playerCardJLabel3.setStatus("Moving Up");
                break;
            case 3:
                playerCardJLabel4.setStatus("Moving Up");
                break;
            case 4:
                playerCardJLabel5.setStatus("Moving Up");
                break;
            case 5:
                playerCardJLabel6.setStatus("Moving Up");
                break;
            case 6:
                playerCardJLabel7.setStatus("Moving Up");
                break;
            case 7:
                playerCardJLabel8.setStatus("Moving Up");
                break;
            case 8:
                playerCardJLabel9.setStatus("Moving Up");
                break;
            case 9:
                playerCardJLabel10.setStatus("Moving Up");
                break;
            case 10:
                playerCardJLabel11.setStatus("Moving Up");
                break;
        }
        add(dealerCardJLabel5);
        add(dealerCardJLabel4);
        add(dealerCardJLabel3);
        add(dealerCardJLabel2);
        add(dealerCardJLabel1);
        add(dealerCardsCounterJLabel);
        add(playerCardJLabel11);
        add(playerCardJLabel10);
        add(playerCardJLabel9);
        add(playerCardJLabel8);
        add(playerCardJLabel7);
        add(playerCardJLabel6);
        add(playerCardJLabel5);
        add(playerCardJLabel4);
        add(playerCardJLabel3);
        add(playerCardJLabel2);
        add(playerCardJLabel1);
        add(playerCardsCounterJLabel);
    }

    // EFFECTS:  stop the timers of all cards and counters, and remove all of them from this BlackJackGame JFrame
    @SuppressWarnings("methodlength")
    private void removeAllCardsAndCountersHelper() {
        dealerCardJLabel5.stopTimer();
        dealerCardJLabel4.stopTimer();
        dealerCardJLabel3.stopTimer();
        dealerCardJLabel2.stopTimer();
        dealerCardJLabel1.stopTimer();
        dealerCardsCounterJLabel.stopTimer();
        playerCardJLabel11.stopTimer();
        playerCardJLabel10.stopTimer();
        playerCardJLabel9.stopTimer();
        playerCardJLabel8.stopTimer();
        playerCardJLabel7.stopTimer();
        playerCardJLabel6.stopTimer();
        playerCardJLabel5.stopTimer();
        playerCardJLabel4.stopTimer();
        playerCardJLabel3.stopTimer();
        playerCardJLabel2.stopTimer();
        playerCardJLabel1.stopTimer();
        playerCardsCounterJLabel.stopTimer();
        remove(dealerCardJLabel5);
        remove(dealerCardJLabel4);
        remove(dealerCardJLabel3);
        remove(dealerCardJLabel2);
        remove(dealerCardJLabel1);
        remove(dealerCardsCounterJLabel);
        remove(playerCardJLabel11);
        remove(playerCardJLabel10);
        remove(playerCardJLabel9);
        remove(playerCardJLabel8);
        remove(playerCardJLabel7);
        remove(playerCardJLabel6);
        remove(playerCardJLabel5);
        remove(playerCardJLabel4);
        remove(playerCardJLabel3);
        remove(playerCardJLabel2);
        remove(playerCardJLabel1);
        remove(playerCardsCounterJLabel);
    }

    // Closing Stage Rendering Helpers

    // EFFECTS:  remove newRoundJButton, saveGameRecordJButton, and mainMenuJButton from this BlackJackGame JFrame
    private void removeAllClosingJButtonsHelper() {
        remove(newRoundJButton);
        remove(saveGameRecordJButton);
        remove(mainMenuJButton);
    }


    @SuppressWarnings("methodlength")
    // MODIFIES: this, dealerCardsCounterJLabel, playerCardsCounterJLabel, dealerCardJLabel3, dealerCardJLabel4,
    // dealerCardJLabel5
    // EFFECTS:  help the Game Phase 5 (Closing Stage) automatically detect the change of Dealer's cards, render all the
    // cards and counters, and add Speeding Animation Effect to all the new cards of Dealer except initial cards
    private void addAllCardsAndCountersOnlyDealerAutoRenderHelper(GameState gameState) {
        dealerCardJLabel5 = new DealerCardJLabel5(gameState, 719, -740, 0, 4);
        dealerCardJLabel5.setStatus("Moving Down");
        dealerCardJLabel4 = new DealerCardJLabel4(gameState, 693, -740, 0, 3);
        dealerCardJLabel4.setStatus("Moving Down");
        dealerCardJLabel3 = new DealerCardJLabel3(gameState, 667, -740, 0, 2);
        dealerCardJLabel3.setStatus("Moving Down");
        dealerCardJLabel2 = new DealerCardJLabel2(gameState, 641, -740, 858,1);
        dealerCardJLabel1 = new DealerCardJLabel1(gameState,615, -740, 858, 0);
        dealerCardsCounterJLabel = new DealerCardsCounterJLabel(gameState, 660, 25);
        dealerCardsCounterJLabel.setStatus("Showing");
        playerCardJLabel11 = new PlayerCardJLabel11(gameState, 875, 465, 816, 10);
        playerCardJLabel10 = new PlayerCardJLabel10(gameState, 849, 465, 816, 9);
        playerCardJLabel9 = new PlayerCardJLabel9(gameState, 823, 465, 816, 8);
        playerCardJLabel8 = new PlayerCardJLabel8(gameState, 797, 465, 816, 7);
        playerCardJLabel7 = new PlayerCardJLabel7(gameState, 771, 465, 816, 6);
        playerCardJLabel6 = new PlayerCardJLabel6(gameState, 745, 465, 816, 5);
        playerCardJLabel5 = new PlayerCardJLabel5(gameState, 719, 465, 816, 4);
        playerCardJLabel4 = new PlayerCardJLabel4(gameState, 693, 465, 816, 3);
        playerCardJLabel3 = new PlayerCardJLabel3(gameState,667, 465, 816, 2);
        playerCardJLabel2 = new PlayerCardJLabel2(gameState, 641, 465, 816,1);
        playerCardJLabel1 = new PlayerCardJLabel1(gameState, 615, 465, 816, 0);
        playerCardsCounterJLabel = new PlayerCardsCounterJLabel(gameState, 660, 720);
        playerCardsCounterJLabel.setStatus("Showing");
        add(dealerCardJLabel5);
        add(dealerCardJLabel4);
        add(dealerCardJLabel3);
        add(dealerCardJLabel2);
        add(dealerCardJLabel1);
        add(dealerCardsCounterJLabel);
        add(playerCardJLabel11);
        add(playerCardJLabel10);
        add(playerCardJLabel9);
        add(playerCardJLabel8);
        add(playerCardJLabel7);
        add(playerCardJLabel6);
        add(playerCardJLabel5);
        add(playerCardJLabel4);
        add(playerCardJLabel3);
        add(playerCardJLabel2);
        add(playerCardJLabel1);
        add(playerCardsCounterJLabel);
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this, dealerCardsCounterJLabel, playerCardsCounterJLabel
    // EFFECTS:  help the Game Phase 5 (Closing Stage) render all the counters and cards held by the both parties
    private void addAllCardsAndCountersNormalRenderHelper(GameState gameState) {
        dealerCardJLabel5 = new DealerCardJLabel5(gameState, 719, -740, 858, 4);
        dealerCardJLabel4 = new DealerCardJLabel4(gameState, 693, -740, 858, 3);
        dealerCardJLabel3 = new DealerCardJLabel3(gameState, 667, -740, 858, 2);
        dealerCardJLabel2 = new DealerCardJLabel2(gameState, 641, -740, 858,1);
        dealerCardJLabel1 = new DealerCardJLabel1(gameState,615, -740, 858, 0);
        dealerCardsCounterJLabel = new DealerCardsCounterJLabel(gameState, 660, 25);
        dealerCardsCounterJLabel.setStatus("Showing");
        playerCardJLabel11 = new PlayerCardJLabel11(gameState, 875, 465, 816, 10);
        playerCardJLabel10 = new PlayerCardJLabel10(gameState, 849, 465, 816, 9);
        playerCardJLabel9 = new PlayerCardJLabel9(gameState, 823, 465, 816, 8);
        playerCardJLabel8 = new PlayerCardJLabel8(gameState, 797, 465, 816, 7);
        playerCardJLabel7 = new PlayerCardJLabel7(gameState, 771, 465, 816, 6);
        playerCardJLabel6 = new PlayerCardJLabel6(gameState, 745, 465, 816, 5);
        playerCardJLabel5 = new PlayerCardJLabel5(gameState, 719, 465, 816, 4);
        playerCardJLabel4 = new PlayerCardJLabel4(gameState, 693, 465, 816, 3);
        playerCardJLabel3 = new PlayerCardJLabel3(gameState,667, 465, 816, 2);
        playerCardJLabel2 = new PlayerCardJLabel2(gameState, 641, 465, 816,1);
        playerCardJLabel1 = new PlayerCardJLabel1(gameState, 615, 465, 816, 0);
        playerCardsCounterJLabel = new PlayerCardsCounterJLabel(gameState, 660, 720);
        playerCardsCounterJLabel.setStatus("Showing");
        add(dealerCardJLabel5);
        add(dealerCardJLabel4);
        add(dealerCardJLabel3);
        add(dealerCardJLabel2);
        add(dealerCardJLabel1);
        add(dealerCardsCounterJLabel);
        add(playerCardJLabel11);
        add(playerCardJLabel10);
        add(playerCardJLabel9);
        add(playerCardJLabel8);
        add(playerCardJLabel7);
        add(playerCardJLabel6);
        add(playerCardJLabel5);
        add(playerCardJLabel4);
        add(playerCardJLabel3);
        add(playerCardJLabel2);
        add(playerCardJLabel1);
        add(playerCardsCounterJLabel);
    }


    public static void main(String[] args) {
        new BlackJackGame();
    }
}
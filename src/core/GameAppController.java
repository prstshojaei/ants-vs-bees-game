package core;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Parastoo , Date 20/04/2026
 *         GameAppController: Controls the main application flow between
 *         the main menu, the game screen, and the pause menu overlay.
 *         This class is designed so the menu system can be extended later
 *         by the rest of the team.
 */
public class GameAppController {

    private Stage stage;
    private Scene scene;

    private MainMenuPane mainMenuPane;
    private GameScreenPane gameScreenPane;
    private PauseMenuPane pauseMenuPane;

    private AntGame antGame;
    private int level = 1;
    /**
     * @author Parastoo , Date 26/04/2026
     *         Stores the currently selected user account.
     *         Used to save game results after the game ends.
     */
    private String selectedUserName = null;
    // Parastoo: code change ends here.

    /**
     * @author Parastoo , Date 03/05/2026
     *         Stores the current game seed selected from the menu.
     */
    private long currentSeed = SeedGenerator.seedGenerator();
    // Parastoo: code change ends here.

    /*
     * Creates a controller for the main game application.
     * 
     * @param stage the primary application stage
     */
    public GameAppController(Stage stage, long seed) {
        this.stage = stage;
        currentSeed = seed;
    }

    /*
     * Shows the main menu screen.
     * This is the first screen shown when the application starts.
     */
    public void showMainMenu() {
        mainMenuPane = new MainMenuPane();

        /**
         * @author Parastoo , Date 20/04/2026
         *         Original scene creation logic kept for reference.
         *         Commented out by Parastoo , Date 20/04/2026
         *         Replaced to support reusing the same Scene instance when returning
         *         from the game back to the menu.
         */
        // scene = new Scene(mainMenuPane, 1024, 768);
        // stage.setScene(scene);
        // stage.show();
        // Parastoo: original menu scene creation commented out here.

        /*
         * Reuses the existing Scene when possible.
         * If no Scene exists yet, create it for the first launch.
         */
        if (scene == null) {
            scene = new Scene(mainMenuPane, 1024, 768);
            stage.setScene(scene);
            stage.show();
        } else {
            scene.setRoot(mainMenuPane);
        }

        /*
         * Connects the Start Game button to begin a new game.
         */
        mainMenuPane.getStartGameButton().setOnAction(e -> startNewGame());

        /*
         * Connects the Exit Game button to close the application.
         */
        mainMenuPane.getExitGameButton().setOnAction(e -> Platform.exit());

        /**
         * @author Parastoo , Date 26/04/2026
         *         Connects the View Score Screen button to the ScoreScreenPane.
         */
        mainMenuPane.getScoreScreenButton().setOnAction(e -> {
            ScoreScreenPane scoreScreen = new ScoreScreenPane(() -> showMainMenu());
            scene.setRoot(scoreScreen);
        });
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 05/05/2026
         *         Opens settings screen with music on/off toggle.
         */
        mainMenuPane.getSettingsButton().setOnAction(e -> {
            SettingsPane settingsPane = new SettingsPane(() -> scene.setRoot(mainMenuPane));
            scene.setRoot(settingsPane);
        });
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 05/05/2026
         *         Original level listener kept for reference.
         *         Commented out by Parastoo , Date 05/05/2026
         *         Replaced to handle null value safely.
         */
        // mainMenuPane.getLevelSelectDropdown().valueProperty().addListener(...)
        // Parastoo: original level listener commented out here.

        /**
         * @author Parastoo , Date 05/05/2026
         *         Updated level listener with null safety check.
         */
        /**
         * @author Parastoo , Date 08/05/2026
         * Updated to match renamed difficulty dropdown options.
         */
        mainMenuPane.getLevelSelectDropdown().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue.equals("Difficulty 1"))
                level = 1;
            else if (newValue.equals("Difficulty 2"))
                level = 2;
            else if (newValue.equals("Difficulty 3"))
                level = 3;
            System.out.println("Difficulty selected: " + level);
        });
        // Parastoo: code change ends here.
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 25/04/2026
         *         Connects the user management UI to UserManager.
         *         Loads existing users into the dropdown on menu load.
         *         Allows creating new users and selecting existing ones.
         */
        // Load existing users into dropdown
        for (UserAccount u : UserManager.getAllUsers()) {
            mainMenuPane.getSelectUserDropdown().getItems().add(u.getName());
        }

        /**
         * @author Parastoo , Date 08/05/2026
         * Checks for duplicate username (case-insensitive) before creating.
         * Shows a red error if the name already exists, otherwise creates the user.
         */
        mainMenuPane.getNewUserButton().setOnAction(e -> {
            String newName = mainMenuPane.getNewUserField().getText().trim();
            if (!newName.isEmpty()) {
                if (UserManager.getUser(newName) != null) {
                    mainMenuPane.getUserErrorLabel().setText("Username already exists.");
                } else {
                    mainMenuPane.getUserErrorLabel().setText("");
                    UserManager.createUser(newName);
                    mainMenuPane.getSelectUserDropdown().getItems().clear();
                    for (UserAccount u : UserManager.getAllUsers()) {
                        mainMenuPane.getSelectUserDropdown().getItems().add(u.getName());
                    }
                    mainMenuPane.getSelectUserDropdown().setValue(newName);
                    mainMenuPane.getNewUserField().clear();
                }
            }
        });
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 26/04/2026
         *         Saves the selected username whenever the player picks one from the
         *         dropdown.
         */
        mainMenuPane.getSelectUserDropdown().setOnAction(e -> {
            selectedUserName = mainMenuPane.getSelectUserDropdown().getValue();
            System.out.println("Selected user: " + selectedUserName);
        });
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 03/05/2026
         *         Reads the seed from the seed field when the player changes it.
         */
        mainMenuPane.getSeedField().textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                currentSeed = Long.parseLong(newVal);
            } catch (NumberFormatException ignored) {
            }
        });

        currentSeed = SeedGenerator.seedGenerator();
        mainMenuPane.getSeedField().setText(String.valueOf(currentSeed));
        // Parastoo: code change ends here.
    }

    /*
     * Starts a brand new game by creating fresh game model objects
     * and switching to the game screen.
     */
    public void startNewGame() {

        /*
         * Creates a fresh colony using the same setup values
         * currently used in the main game launcher.
         */
        AntColony colony = new AntColony(5, 10, 3, 10, getSeed(), level);

        /*
         * Creates the hive using the current full hive setup.
         */
        // Hive hive = Hive.makeFullHive();

        // Paddy 04/05/2026:
        Hive hive = Hive.makeRandomLevelHive(level, getSeed());

        /**
         * @author Parastoo , Date 05/05/2026
         *         Original direct game start kept for reference.
         *         Commented out by Parastoo , Date 05/05/2026
         *         Replaced to show help screen before game starts.
         */
        // scene.setRoot(gameRoot);
        // antGame.startGameLoop();
        // Parastoo: original direct start commented out here.

        /**
         * @author Parastoo , Date 05/05/2026
         *         Shows help screen for 5 seconds before starting the game.
         */
        antGame = new AntGame(colony, hive, stage, level);
        gameScreenPane = new GameScreenPane(antGame, stage);

        StackPane gameRoot = new StackPane();
        gameRoot.getChildren().add(gameScreenPane);

        HelpScreenPane helpScreen = new HelpScreenPane(() -> {
            if (scene == null) {
                scene = new Scene(gameRoot, 1920, 1080);
                stage.setScene(scene);
                stage.show();
            } else {
                scene.setRoot(gameRoot);
            }
            antGame.startGameLoop();
        });

        if (scene == null) {
            scene = new Scene(helpScreen, 1920, 1080);
            stage.setScene(scene);
            stage.show();
        } else {
            scene.setRoot(helpScreen);
        }
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 03/05/2026
         *         Original game end callback kept for reference.
         *         Commented out by Parastoo , Date 03/05/2026
         *         Replaced to also save seed.
         */
        // antGame.setOnGameEnd(() -> {
        // if (selectedUserName != null) {
        // UserManager.updateTime(selectedUserName, GameTimer.getElapsedSeconds());
        // }
        // });
        // Parastoo: original game end callback commented out here.

        /**
         * @author Parastoo , Date 05/05/2026
         *         Updated to save time, seed, level and score when the game ends.
         *         Note: passed status is saved in setOnGameOver where playerWon is
         *         available.
         */
        antGame.setOnGameEnd(() -> {
            if (selectedUserName != null) {
                UserManager.updateTime(selectedUserName, GameTimer.getElapsedSeconds());
                UserManager.updateSeed(selectedUserName, currentSeed);
                UserManager.updateLevel(selectedUserName, level);
            }
        });
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 05/05/2026
         *         Updated to save score and passed status when game ends.
         */
        antGame.setOnGameOver(playerWon -> {
            if (selectedUserName != null) {
                UserManager.updateScore(selectedUserName, ScoreManager.getScore());
                UserManager.setPassed(selectedUserName, playerWon);
            }
            GameOverPane gameOverPane = new GameOverPane(playerWon, () -> {
                ScoreScreenPane scoreScreen = new ScoreScreenPane(() -> showMainMenu());
                scene.setRoot(scoreScreen);
            });
            scene.setRoot(gameOverPane);
        });
        // Parastoo: code change ends here.

        /*
         * Connects the Pause button to show the pause menu overlay.
         */
        gameScreenPane.getPauseButton().setOnAction(e -> showPauseMenu(gameRoot));

        /*
         * Supports opening the pause menu with the Escape key.
         * This matches the intended game flow from the menu diagram.
         */
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                showPauseMenu(gameRoot);
            }
        });
    }

    /*
     * Shows the pause menu overlay on top of the current game screen.
     * 
     * @param gameRoot the root StackPane containing the current game screen
     */
    private void showPauseMenu(StackPane gameRoot) {
        if (antGame == null || antGame.isPaused()) {
            return;
        }

        antGame.pauseGame();

        pauseMenuPane = new PauseMenuPane();
        gameRoot.getChildren().add(pauseMenuPane);

        /*
         * Connects the Resume button to remove the pause menu
         * and continue the game.
         */
        pauseMenuPane.getResumeButton().setOnAction(e -> {
            gameRoot.getChildren().remove(pauseMenuPane);
            antGame.resumeGame();
        });

        /*
         * Connects the Reset Game button to stop the current game
         * and start a fresh new game.
         */
        pauseMenuPane.getResetGameButton().setOnAction(e -> {
            if (antGame != null) {
                antGame.stopGame();
            }
            currentSeed = SeedGenerator.seedGenerator();
            startNewGame();
        });

        /**
         * @author Parastoo , Date 08/05/2026
         * Opens SettingsPane from the pause menu. Back button returns to the pause overlay.
         */
        pauseMenuPane.getSettingsButton().setOnAction(e -> {
            SettingsPane settingsPane = new SettingsPane(() -> scene.setRoot(gameRoot));
            scene.setRoot(settingsPane);
        });
        // Parastoo: code change ends here.

        /*
         * Connects the Return to Main Menu button to stop the current game,
         * save the unfinished game as a loss, and switch back to the main menu.
         */
        pauseMenuPane.getReturnToMainMenuButton().setOnAction(e -> {
            if (antGame != null) {
                antGame.stopGame();
            }
            if (selectedUserName != null) {
                UserManager.updateTime(selectedUserName, GameTimer.getElapsedSeconds());
                UserManager.updateSeed(selectedUserName, currentSeed);
                UserManager.updateLevel(selectedUserName, level);
                UserManager.updateScore(selectedUserName, ScoreManager.getScore());
                UserManager.setPassed(selectedUserName, false);
            }
            showMainMenu();
        });
    }

    public long getSeed() {
        return currentSeed;
    }

    public int getLevel() {
        return level;
    }
}
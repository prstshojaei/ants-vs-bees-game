package core;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Background;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Parastoo , Date 19/04/2026
 *         MainMenuPane: Displays the main menu for the game.
 *         This menu is designed to be extendable so the rest of the team
 *         can add more options later such as users, scores, settings, and level
 *         selection.
 */
public class MainMenuPane extends VBox {

    private Button startGameButton;
    private Button settingsButton;
    private Button scoreScreenButton;
    private Button exitGameButton;

    private ComboBox<String> selectUserDropdown;
    private TextField newUserField;
    private Button newUserButton;
    private ComboBox<String> levelSelectDropdown;
    private TextField levelSeedField;
    /**
     * @author Parastoo , Date 08/05/2026
     * Shows a red error message when the user tries to create a duplicate username.
     */
    private Label userErrorLabel;
    // Parastoo: code change ends here.
    /**
     * @author Parastoo , Date 03/05/2026
     *         Text field for entering or displaying the game seed.
     */
    private TextField seedField;
    // Parastoo: code change ends here.

    /*
     * Creates the main menu layout and all menu controls.
     * The layout is intentionally simple and extendable.
     */
    public MainMenuPane() {
        super(12);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(30));
        setPrefSize(1024, 768);
        /**
         * @author Parastoo , Date 03/05/2026
         *         Original white background kept for reference.
         *         Commented out by Parastoo , Date 03/05/2026
         *         Replaced with game background image.
         */
        // setStyle("-fx-background-color: white;");
        // Parastoo: original background commented out here.

        /**
         * @author Parastoo , Date 03/05/2026
         *         Sets the menu background image using JavaFX BackgroundImage.
         */
        Image bgImage = new Image("file:img/menu_bg.png");
        BackgroundImage bg = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        setBackground(new Background(bg));
        // Parastoo: code change ends here.

        /*
         * Main title label for the game menu.
         */
        Label titleLabel = new Label("Ants vs. Some Bees");
        titleLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 30));

        /*
         * Start Game button.
         * This will later connect to the game screen.
         */
        startGameButton = new Button("Start Game");
        startGameButton.setPrefWidth(220);

        /**
         * @author Parastoo , Date 19/04/2026
         *         Select User dropdown placeholder.
         *         This is included so the team can later connect user profile
         *         functionality.
         */
        selectUserDropdown = new ComboBox<>();
        /**
         * @author Parastoo , Date 08/05/2026
         * Prompt text shows Guest so the field is never visually empty.
         */
        selectUserDropdown.setPromptText("Guest");
        // Parastoo: code change ends here.
        selectUserDropdown.setPrefWidth(220);

        /*
         * New User text field and button placeholder.
         * These are included for future user creation support.
         */
        newUserField = new TextField();
        newUserField.setPromptText("Enter New User");
        newUserField.setMaxWidth(220);

        newUserButton = new Button("Create New User");
        newUserButton.setPrefWidth(220);

        /**
         * @author Parastoo , Date 08/05/2026
         * Red error label shown when a duplicate username is entered. Hidden by default.
         */
        userErrorLabel = new Label("");
        userErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 20/04/2026
         *         Original empty Level Select dropdown kept for reference.
         *         Commented out by Parastoo , Date 20/04/2026
         *         Replaced with fixed selectable game levels for better usability.
         */
        // levelSelectDropdown = new ComboBox<>();
        // levelSelectDropdown.setPromptText("Level Select");
        // levelSelectDropdown.setPrefWidth(220);
        // Parastoo: original empty level dropdown commented out here.

        /**
         * Creates a fixed Level Select dropdown with predefined levels.
         * This is clearer for the player than manually entering a level value.
         */
        /**
         * @author Parastoo , Date 08/05/2026
         * Renamed level options to difficulty for clarity.
         */
        levelSelectDropdown = new ComboBox<>();
        levelSelectDropdown.getItems().addAll("Difficulty 1", "Difficulty 2", "Difficulty 3");
        levelSelectDropdown.setPromptText("Select Difficulty");
        // Parastoo: code change ends here.
        levelSelectDropdown.setPrefWidth(220);
        /**
         * @author Parastoo , Date 03/05/2026
         *         Seed input field pre-filled with the current date/time seed.
         *         Players can change it to replay a specific game configuration.
         */
        seedField = new TextField(String.valueOf(SeedGenerator.seedGenerator()));
        seedField.setPromptText("Enter 12-digit Seed");
        seedField.setMaxWidth(220);
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 20/04/2026
         *         Original Level Seed field kept for reference.
         *         Commented out by Parastoo , Date 20/04/2026
         *         Hidden for now because predefined level selection is clearer
         *         for the current menu implementation.
         */
        // levelSeedField = new TextField();
        // levelSeedField.setPromptText("Level Seed");
        // levelSeedField.setMaxWidth(220);
        // Parastoo: original level seed field commented out here.

        /**
         * @author Parastoo , Date 20/04/2026
         *         Original disabled Settings button kept for reference.
         *         Commented out by Parastoo , Date 20/04/2026
         *         Replaced so the button remains fully visible in the menu.
         */
        // settingsButton = new Button("Settings");
        // settingsButton.setPrefWidth(220);
        // settingsButton.setDisable(true);
        // Parastoo: original disabled settings button commented out here.

        /*
         * Keeps the Settings button visually active in the menu.
         * Functionality can be connected later by the team.
         */
        settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(220);

        /**
         * @author Parastoo , Date 20/04/2026
         *         Original disabled View Score Screen button kept for reference.
         *         Commented out by Parastoo , Date 20/04/2026
         *         Replaced so the button remains fully visible in the menu.
         */
        // scoreScreenButton = new Button("View Score Screen");
        // scoreScreenButton.setPrefWidth(220);
        // scoreScreenButton.setDisable(true);
        // Parastoo: original disabled score button commented out here.

        /*
         * Keeps the View Score Screen button visually active in the menu.
         * Functionality can be connected later by the team.
         */
        scoreScreenButton = new Button("View Score Screen");
        scoreScreenButton.setPrefWidth(220);

        /*
         * Exit Game button.
         * This will later close the application.
         */
        exitGameButton = new Button("Exit Game");
        exitGameButton.setPrefWidth(220);

        /**
         * @author Parastoo , Date 20/04/2026
         *         Original menu layout kept for reference.
         *         Commented out by Parastoo , Date 20/04/2026
         *         Replaced to remove the Level Seed field from the visible menu.
         */
        // getChildren().addAll(
        // titleLabel,
        // startGameButton,
        // selectUserDropdown,
        // newUserField,
        // newUserButton,
        // levelSelectDropdown,
        // levelSeedField,
        // settingsButton,
        // scoreScreenButton,
        // exitGameButton
        // );
        // Parastoo: original menu layout commented out here.

        getChildren().addAll(
                titleLabel,
                startGameButton,
                selectUserDropdown,
                newUserField,
                newUserButton,
                userErrorLabel,
                levelSelectDropdown,
                seedField,
                settingsButton,
                scoreScreenButton,
                exitGameButton);
    }

    /*
     * Returns the Start Game button so external controllers can attach
     * actions.
     * 
     * @return the Start Game button
     */
    public Button getStartGameButton() {
        return startGameButton;
    }

    /*
     * Returns the Exit Game button so external controllers can attach
     * actions.
     * 
     * @return the Exit Game button
     */
    public Button getExitGameButton() {
        return exitGameButton;
    }

    /*
     * Returns the Select User dropdown for future extensions.
     * 
     * @return the Select User combo box
     */
    public ComboBox<String> getSelectUserDropdown() {
        return selectUserDropdown;
    }

    /*
     * Returns the New User text field for future extensions.
     * 
     * @return the New User text field
     */
    public TextField getNewUserField() {
        return newUserField;
    }

    /*
     * Returns the Create New User button for future extensions.
     * 
     * @return the Create New User button
     */
    public Button getNewUserButton() {
        return newUserButton;
    }

    /**
     * @author Parastoo , Date 08/05/2026
     * Returns the error label so the controller can show or clear duplicate username errors.
     */
    public Label getUserErrorLabel() {
        return userErrorLabel;
    }
    // Parastoo: code change ends here.

    /*
     * Returns the Level Select dropdown for future extensions.
     * 
     * @return the Level Select combo box
     */
    public ComboBox<String> getLevelSelectDropdown() {
        return levelSelectDropdown;
    }

    /*
     * Returns the Level Seed text field for future extensions.
     * 
     * @return the Level Seed text field
     */
    public TextField getLevelSeedField() {
        return levelSeedField;
    }

    /*
     * Returns the Settings button for future extensions.
     * 
     * @return the Settings button
     */
    public Button getSettingsButton() {
        return settingsButton;
    }

    /*
     * Returns the View Score Screen button for future extensions.
     * 
     * @return the View Score Screen button
     */
    public Button getScoreScreenButton() {
        return scoreScreenButton;
    }
    // Parastoo: code change ends here.


    /**
     * @author Parastoo , Date 03/05/2026
     *         Returns the seed field for external access.
     * @return the seed TextField
     */
    public TextField getSeedField() {
        return seedField;
    }
    // Parastoo: code change ends here.
}
package core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Parastoo , Date 19/04/2026
 *         PauseMenuPane: Displays the pause menu overlay for the game.
 *         This menu provides controls for resuming, resetting,
 *         returning to the main menu, and future settings support.
 */
public class PauseMenuPane extends VBox {

    private Button resumeButton;
    private Button resetGameButton;
    private Button returnToMainMenuButton;
    private Button settingsButton;

    /*
     * Creates the pause menu layout and all pause controls.
     * This pane is designed to appear as an overlay on top of the game screen.
     */
    public PauseMenuPane() {
        super(15);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(25));
        setMaxWidth(320);
        setStyle(
                "-fx-background-color: rgba(255,255,255,0.95);" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;");

        /*
         * Pause menu title label.
         */
        Label titleLabel = new Label("Paused");
        titleLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 26));

        /*
         * Resume button.
         * This will continue gameplay when connected later.
         */
        resumeButton = new Button("Resume");
        resumeButton.setPrefWidth(220);

        /*
         * Reset Game button.
         * This will restart the game from the beginning when connected later.
         */
        resetGameButton = new Button("Reset Game");
        resetGameButton.setPrefWidth(220);

        /*
         * Return to Main Menu button.
         * This will return the player to the main menu when connected later.
         */
        returnToMainMenuButton = new Button("Return to Main Menu");
        returnToMainMenuButton.setPrefWidth(220);

        /**
         * @author Parastoo , Date 20/04/2026
         *         Original disabled Settings button kept for reference.
         *         Commented out by Parastoo , Date 20/04/2026
         *         Replaced so the button remains fully visible in the pause menu.
         */
        // settingsButton = new Button("Settings");
        // settingsButton.setPrefWidth(220);
        // settingsButton.setDisable(true);
        // Parastoo: original disabled pause settings button commented out here.

        /*
         * Keeps the Settings button visually active in the pause menu.
         * Functionality can be connected later by the team.
         */
        settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(220);

        getChildren().addAll(
                titleLabel,
                resumeButton,
                resetGameButton,
                returnToMainMenuButton,
                settingsButton);
    }

    /*
     * Returns the Resume button so external controllers can attach actions.
     * 
     * @return the Resume button
     */
    public Button getResumeButton() {
        return resumeButton;
    }

    /*
     * Returns the Reset Game button so external controllers can attach actions.
     * 
     * @return the Reset Game button
     */
    public Button getResetGameButton() {
        return resetGameButton;
    }

    /*
     * Returns the Return to Main Menu button so external controllers can attach
     * actions.
     * 
     * @return the Return to Main Menu button
     */
    public Button getReturnToMainMenuButton() {
        return returnToMainMenuButton;
    }

    /*
     * Returns the Settings button for future extensions.
     * 
     * @return the Settings button
     */
    public Button getSettingsButton() {
        return settingsButton;
    }
    // Parastoo: code change ends here.
}
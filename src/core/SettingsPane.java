package core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Parastoo , Date 05/05/2026
 *         SettingsPane: Displays the settings screen with music on/off toggle.
 *         Accessible from the main menu via the Settings button.
 */
public class SettingsPane extends VBox {

    /**
     * Creates the settings screen.
     * 
     * @param onBack called when the Back button is clicked
     */
    public SettingsPane(Runnable onBack) {
        super(20);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(40));
        setPrefSize(1024, 768);
        setStyle("-fx-background-color: #f5f0e8;");

        // Title
        Label titleLabel = new Label("Settings");
        titleLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 32));

        // Music toggle
        Label musicLabel = new Label("Background Music:");
        musicLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));

        /**
         * @author Parastoo , Date 08/05/2026
         *         Toggle initialises from the current music state so it stays in sync
         *         whether opened from the main menu or the pause menu.
         */
        boolean currentlyOn = AntGame.isMusicEnabled();
        ToggleButton musicToggle = new ToggleButton(currentlyOn ? "Music: ON" : "Music: OFF");
        musicToggle.setSelected(currentlyOn);
        musicToggle.setPrefWidth(200);
        musicToggle.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));
        musicToggle.setStyle(currentlyOn
                ? "-fx-background-color: #4CAF50; -fx-text-fill: white;"
                : "-fx-background-color: #f44336; -fx-text-fill: white;");

        musicToggle.setOnAction(e -> {
            if (musicToggle.isSelected()) {
                musicToggle.setText("Music: ON");
                musicToggle.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                AntGame.setMusicEnabled(true);
            } else {
                musicToggle.setText("Music: OFF");
                musicToggle.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                AntGame.setMusicEnabled(false);
            }
        });
        // Parastoo: code change ends here.

        // Speed selection
        Label speedLabel = new Label("Game Speed:");
        speedLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));

        /**
         * @author Parastoo , Date 12/05/2026
         *         Two-button speed selector: SPEED 1 (slow, 2 sec/turn) and
         *         SPEED 2 (fast, 1 sec/turn). Highlights the active selection.
         */
        Button speed1Button = new Button("SPEED 1  (Slow)");
        speed1Button.setPrefWidth(200);
        speed1Button.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));

        Button speed2Button = new Button("SPEED 2  (Fast)");
        speed2Button.setPrefWidth(200);
        speed2Button.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));

        Runnable updateSpeedStyles = () -> {
            boolean isSlow = AntGame.getTurnSeconds() == 2;
            speed1Button.setStyle(isSlow
                    ? "-fx-background-color: #4CAF50; -fx-text-fill: white;"
                    : "-fx-background-color: #cccccc; -fx-text-fill: black;");
            speed2Button.setStyle(isSlow
                    ? "-fx-background-color: #cccccc; -fx-text-fill: black;"
                    : "-fx-background-color: #2196F3; -fx-text-fill: white;");
        };
        updateSpeedStyles.run();

        speed1Button.setOnAction(e -> {
            AntGame.setTurnSeconds(2);
            updateSpeedStyles.run();
        });
        speed2Button.setOnAction(e -> {
            AntGame.setTurnSeconds(1);
            updateSpeedStyles.run();
        });
        // Parastoo: code change ends here.

        // Back button
        Button backButton = new Button("Back");
        backButton.setPrefWidth(200);
        backButton.setFont(Font.font("SansSerif", 16));
        backButton.setOnAction(e -> onBack.run());

        getChildren().addAll(titleLabel, musicLabel, musicToggle,
                speedLabel, speed1Button, speed2Button, backButton);
    }
}
// Parastoo: code change ends here.
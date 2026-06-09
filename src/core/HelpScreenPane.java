package core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Parastoo , Date 08/05/2026
 * HelpScreenPane: Displays the game help/tutorial screen.
 * Stays on screen until the player clicks the PLAY button.
 */
public class HelpScreenPane extends StackPane {

    /**
     * Creates the help screen with a PLAY button the player must click to start.
     *
     * @param onFinished called when the player clicks PLAY
     */
    public HelpScreenPane(Runnable onFinished) {
        setPrefSize(1024, 768);

        /**
         * @author Parastoo , Date 08/05/2026
         * Loads and displays the help guide image filling the full screen.
         */
        ImageView helpImage = new ImageView(new Image("file:img/help_screen.png"));
        helpImage.setPreserveRatio(false);
        helpImage.fitWidthProperty().bind(this.widthProperty());
        helpImage.fitHeightProperty().bind(this.heightProperty());
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 08/05/2026
         * Green PLAY button replaces the old 5-second auto-close timer.
         * Player must click it after reading the guide to start the game.
         */
        Button playButton = new Button("PLAY");
        playButton.setFont(Font.font("SansSerif", FontWeight.BOLD, 24));
        playButton.setPrefSize(160, 55);
        playButton.setStyle(
                "-fx-background-color: #4CAF50;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;");
        playButton.setOnAction(e -> onFinished.run());
        // Parastoo: code change ends here.

        StackPane.setAlignment(playButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(playButton, new Insets(0, 0, 40, 0));

        getChildren().addAll(helpImage, playButton);
        setAlignment(Pos.CENTER);
    }
}
// Parastoo: code change ends here.

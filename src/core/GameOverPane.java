package core;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * @author Parastoo , Date 03/05/2026
 *         GameOverPane: Displays the game end screen (win or lose).
 *         Shows a background image and result message for a few seconds,
 *         then automatically navigates to the Score Screen.
 *
 *         Usage:
 *         GameOverPane pane = new GameOverPane(true, () -> showScoreScreen());
 *         scene.setRoot(pane);
 */
public class GameOverPane extends StackPane {

    /**
     * Creates a new game end screen.
     * 
     * @param playerWon  true if the player won, false if they lost
     * @param onFinished a Runnable called after the screen auto-closes
     */
    public GameOverPane(boolean playerWon, Runnable onFinished) {

        setPrefSize(1024, 768);

        /**
         * @author Parastoo , Date 03/05/2026
         *         Original BackgroundImage kept for reference.
         *         Commented out by Parastoo , Date 03/05/2026
         *         Replaced with ImageView for reliable background display.
         */
        // String imagePath = playerWon ? "file:img/win_bg.png" :
        // "file:img/gameover_bg.png";
        // Image bgImage = new Image(imagePath);
        // BackgroundImage bg = new BackgroundImage(...);
        // setBackground(new Background(bg));
        // Parastoo: original background commented out here.

        /**
         * @author Parastoo , Date 03/05/2026
         *         Uses ImageView as background that stretches to fill the screen.
         */
        String imagePath = playerWon ? "file:img/win_bg.png" : "file:img/gameover_bg.png";
        javafx.scene.image.ImageView bgView = new javafx.scene.image.ImageView(new Image(imagePath));
        bgView.setPreserveRatio(false);
        bgView.fitWidthProperty().bind(this.widthProperty());
        bgView.fitHeightProperty().bind(this.heightProperty());
        getChildren().add(bgView);
        // Parastoo: code change ends here.

        /*
         * Main result text.
         */
        Text resultText = new Text(playerWon ? "You Win!" : "Game Over!");
        resultText.setFont(Font.font("SansSerif", FontWeight.BOLD, 72));
        resultText.setFill(playerWon ? Color.GOLD : Color.RED);
        resultText.setTextAlignment(TextAlignment.CENTER);
        resultText.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.8, 0, 0);");

        /*
         * Subtitle text.
         */
        /**
         * @author Parastoo , Date 03/05/2026
         *         Original subtitle text kept for reference.
         *         Commented out by Parastoo , Date 03/05/2026
         *         Simplified to remove extra description.
         */
        // Text subText = new Text(playerWon
        // ? "All bees have been vanquished!\nHeading to Score Screen..."
        // : "The ant queen has perished!\nHeading to Score Screen...");
        // Parastoo: original subtitle commented out here.

        /**
         * @author Parastoo , Date 03/05/2026
         *         Simplified subtitle showing only navigation message.
         */
        Text subText = new Text("Heading to Score Screen...");
        // Parastoo: code change ends here.
        subText.setFont(Font.font("SansSerif", FontWeight.NORMAL, 28));
        subText.setFill(Color.WHITE);
        subText.setTextAlignment(TextAlignment.CENTER);
        subText.setStyle("-fx-effect: dropshadow(gaussian, black, 8, 0.8, 0, 0);");

        javafx.scene.layout.VBox textBox = new javafx.scene.layout.VBox(20);
        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().addAll(resultText, subText);

        getChildren().add(textBox);
        setAlignment(Pos.CENTER);

        /*
         * Auto-navigate to Score Screen after 4 seconds.
         */
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished(e -> onFinished.run());
        delay.play();
    }
}
// Parastoo: code change ends here.
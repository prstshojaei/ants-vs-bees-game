package core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author Parastoo , Date 19/04/2026
 *         GameScreenPane: Wraps the AntGame inside a JavaFX layout.
 *         This allows the game to be shown together with menu controls
 *         such as pause, reset, and return-to-menu options.
 */
public class GameScreenPane extends BorderPane {

    private AntGame antGame;
    private Button pauseButton;

    /*
     * Creates a new game screen layout and places the AntGame in the center.
     * Also creates a top control bar with a Pause button for future menu actions.
     * 
     * @param antGame the current AntGame instance to display
     */
    public GameScreenPane(AntGame antGame, Stage stage) {
        this.antGame = antGame;

        setPrefSize(1024, 768);
        setStyle("-fx-background-color: white;");

        /*
         * Creates the top control bar for future game menu controls.
         */
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);
        

        /*
         * Pause button used to open the pause menu later.
         */
        pauseButton = new Button("Pause");
        pauseButton.setPrefWidth(120);
        pauseButton.prefWidthProperty().bind(stage.widthProperty().multiply(120 /AntGame.SCENE_WIDTH));
        pauseButton.prefHeightProperty().bind(stage.widthProperty().multiply(20 / AntGame.SCENE_HEIGHT));

        topBar.getChildren().add(pauseButton);
        /*
         * Places the control bar at the top and the game in the center.
         */
        setTop(topBar);
        setCenter(antGame);
        antGame.getChildren().add(topBar);
        topBar.layoutXProperty().bind(stage.widthProperty().multiply(1010 / AntGame.SCENE_WIDTH));
        topBar.layoutYProperty().bind(stage.heightProperty().multiply(200 / AntGame.SCENE_HEIGHT));
    }

    /*
     * Returns the AntGame instance displayed inside this screen.
     * 
     * @return the AntGame instance
     */
    public AntGame getAntGame() {
        return antGame;
    }

    /*
     * Returns the Pause button so external controllers can attach actions.
     * 
     * @return the Pause button
     */
    public Button getPauseButton() {
        return pauseButton;
    }

}

// Parastoo: code change ends here.
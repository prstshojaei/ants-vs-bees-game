package core;

import javafx.application.Application;
/**
 * @author Parastoo , Date 20/04/2026
 * Original Scene import kept for reference.
 * Commented out by Parastoo , Date 20/04/2026
 * Scene creation is now handled inside GameAppController.
 */
// import javafx.scene.Scene;
//Parastoo: original Scene import commented out here.

import javafx.stage.Stage;

/**
 * A driver for the Ants vs. Some-Bees game
 * mvn javafx:run - to run in terminal
 */
public class AntsVsSomeBees extends Application {
    @Override
    public void start(Stage primaryStage) {
        /**
         * @author Parastoo , Date 20/04/2026
         *         Original direct game launch logic kept for reference.
         *         Commented out by Parastoo , Date 20/04/2026
         *         Replaced to integrate the new menu system controller.
         */
        // AntColony colony = new AntColony(3, 8, 3, 20); //specify the colony [tunnels,
        // length, moats, food]
        //
        // //Hive hive = Hive.makeTestHive(); //specify the attackers (the hive)
        // Hive hive = Hive.makeFullHive();
        // //Hive hive = Hive.makeInsaneHive();
        //
        // // AntGame now extends a JavaFX Pane
        // AntGame gameRoot = new AntGame(colony, hive);
        //
        // // Set up the JavaFX Scene using the dimensions from the original AntGame
        // Scene scene = new Scene(gameRoot, 1920, 1080);
        //
        // primaryStage.setTitle("Ants vs. Some-Bees");
        // primaryStage.setScene(scene);
        // primaryStage.setResizable(false);
        // primaryStage.show();
        // primaryStage.setMaximized(true);
        // // Launch the JavaFX AnimationTimer
        // gameRoot.startGameLoop();
        // Parastoo: original direct launch code commented out here.

        /**
         * @author Parastoo , Date 20/04/2026
         *         Initializes the application through the GameAppController
         *         so the game now starts from the Main Menu screen.
         */
        primaryStage.setTitle("Ants vs. Some-Bees");
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        primaryStage.setMaxWidth(1920);
        primaryStage.setMaxHeight(1080);
        

        GameAppController controller = new GameAppController(primaryStage, SeedGenerator.seedGenerator());
        controller.showMainMenu();
        // Parastoo: code change ends here.
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application lifecycle
    }
}
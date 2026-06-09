package core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import ants.ThrowerAnt;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Shah Abdul Qadir , Date 13/04/2026
 * Added imports for background music and sound effects
 */
import javafx.scene.media.AudioClip;
// Shah Abdul Qadir: code change ends here

/**
 * A class that controls the graphical game of Ants vs. Some-Bees. Game
 * simulation system and GUI interaction are intermixed.
 * * @author Joel (Refactored for JavaFX)
 * * @version Fa2014 / 2026
 */
public class AntGame extends Pane {
    private Stage primaryStage;
    /**
     * @author Shah Abdul Qadir , Date 03/04/2026
     *         Static instance to allow ants to trigger UI effects directly
     */
    public static AntGame instance;
    // Shah Abdul Qadir: code change ends here

    /**
     * @author Paddy , Date 26/03/2026
     *         Please see setFeedbackUI() and setFeedbackUI()
     */
    private String feedbackUI;
    // Paddy , Date 26/03/2026 : code change ends here.

    /**
     * @author Parastoo , Date 30/03/2026
     *         Stores the type of feedback message (info, success, error)
     *         and controls how long the message is displayed on screen.
     */
    private String feedbackType = "info";
    private long feedbackExpiryTime = 0;
    private TextArea feedbackArea;
    // Parastoo: code change ends here

    // game models
    private AntColony colony;
    private Hive hive;
    private static final String ANT_FILE = "antlist.properties";
    private static final String ANT_PKG = "ants";

    // game clock & speed
    public static final int FPS = 30; // target frames per second
    public static int TURN_SECONDS = 2; // seconds per turn (1 = fast, 2 = slow)
    public static final double LEAF_SPEED = .3; // in seconds
    private int turn; // current game turn
    private int frame; // time elapsed since last turn
    private AnimationTimer clock;
    private boolean isRunning = false;

    /**
     * @author Parastoo , Date 19/04/2026
     *         Tracks whether the game is currently paused.
     *         This is used by the menu system to freeze and resume gameplay.
     */
    private boolean isPaused = false;
    // Parastoo: code change ends here.
    /**
     * @author Parastoo , Date 26/04/2026
     *         Callback that runs when the game ends (win or lose).
     *         Used by GameAppController to save game results to the selected user.
     */
    private Runnable onGameEnd;

    /**
     * @author Parastoo , Date 03/05/2026
     *         Callback that navigates to the Game Over screen.
     *         Receives true if player won, false if lost.
     */
    private java.util.function.Consumer<Boolean> onGameOver;

    public void setOnGameOver(java.util.function.Consumer<Boolean> onGameOver) {
        this.onGameOver = onGameOver;
    }
    // Parastoo: code change ends here.

    public void setOnGameEnd(Runnable onGameEnd) {
        this.onGameEnd = onGameEnd;
    }
    // Parastoo: code change ends here.

    // ant properties (loaded from external files, stored as member variables)
    private TextArea colonyReportsArea;
    private TextArea hiveReportsArea;
    private TextArea antDescriptorArea;
    private TextArea beeDescriptorArea;
    private TextArea mouseOverToolTip;

    // ant properties (loaded from external files, stored as member variables)
    private final ArrayList<String> ANT_TYPES;
    private final Map<String, Image> ANT_IMAGES;
    private final Map<String, Color> LEAF_COLORS;

    // other images (stored as member variables)
    private final Image WATER_IMAGE = FileUtils.loadImage("/img/water3.png"); // Paddy 01/04/2026 - see drawColony()
    private final Image TUNNEL_IMAGE = FileUtils.loadImage("/img/tunnel.gif");
    private final Image BEE_IMAGE = FileUtils.loadImage("/img/bee.gif");
    private final Image REMOVER_IMAGE = FileUtils.loadImage("/img/remover.gif");

    /**
     * @author Shah Abdul Qadir , Date 14/04/2026
     *         Custom enemy unit assets.
     *         Note: Sprite sheets generated by Gemini AI and sliced into GIFs.
     */
    private final Image GHOST_BEE_IMAGE = FileUtils.loadImage("/img/bee_ghost.gif");
    private final Image ZOMBIE_BEE_IMAGE = FileUtils.loadImage("/img/bee_zombie.gif");
    // Shah Abdul Qadir: code change ends here

    /**
     * @author Shah Abdul Qadir , Date 28/04/2026
     *         Cache for temporary effects to prevent mid-game freezing.
     */
    private final Map<String, Image> effectCache = new HashMap<>();
    // Shah Abdul Qadir: code change ends here

    /**
     * @author Shah Abdul Qadir , Date 18/04/2026
     *         Renamed SCENE constants to SCENE to adopt JavaFX terminology over
     *         Swing.
     *         Updated resolution to widescreen 1920x1080 and adjusted positions.
     */
    public static final double SCENE_WIDTH = 1920;
    public static final double SCENE_HEIGHT = 1080;
    public static final double ANT_IMAGE_WIDTH = 66;
    public static final double ANT_IMAGE_HEIGHT = 71;
    public static final int BEE_IMAGE_WIDTH = 58;
    public static final double PANEL_POS_X = 80;
    public static final double PANEL_POS_Y = 40;
    public static final double PANEL_PADDING_W = 2;
    public static final double PANEL_PADDING_H = 4;
    public static final double PLACE_POS_X = 40;
    public static final double PLACE_POS_Y = 200;
    public static final double PLACE_PADDING_W = 10;
    public static final double PLACE_PADDING_H = 10;
    public static final int PLACE_MARGIN = 10;
    public static final double HIVE_POS_X = 1050;
    public static final double HIVE_POS_Y = 320;
    public static final int CRYPT_HEIGHT = 700;
    public static final double LEAF_START_OFFSET_W = 30;
    public static final double LEAF_START_OFFSET_H = 30;
    public static final double LEAF_END_OFFSET_W = 50;
    public static final double LEAF_END_OFFSET_H = 30;
    public static final int LEAF_SIZE = 40;

    // Paddy 25/04/2026: holds the normalized values for screen scaling
    public double normalizedWidth = 1;
    public double normalizedHeight = 1;
    public int level;

    // areas that can be clicked
    private Map<Rectangle, Place> colonyAreas;
    private Map<Place, Rectangle> colonyRects;
    private Map<Rectangle, Ant> antSelectorAreas;
    private Map<String, Rectangle> beeSpawnAreas;
    private Map<String, Image> beeSpawnImages;
    private Rectangle removerArea;
    private Place tunnelEnd;
    private Ant selectedAnt;

    // variables tracking animations
    private Map<Bee, AnimPosition> allBeePositions;
    private ArrayList<AnimPosition> leaves;

    private Canvas canvas;

    /**
     * @author Shah Abdul Qadir , Date 13/04/2026
     *         MediaPlayer instance to handle the background music loop
     */
    /**
     * @author Parastoo , Date 03/05/2026
     *         Original non-static MediaPlayer kept for reference.
     *         Commented out by Parastoo , Date 03/05/2026
     *         Made static so music persists and can be stopped across game
     *         restarts.
     */
    // private MediaPlayer mediaPlayer;
    // Parastoo: original mediaPlayer commented out here.

    /**
     * @author Parastoo , Date 03/05/2026
     *         Static MediaPlayer so only one instance of music plays at a time.
     */
    private static MediaPlayer mediaPlayer;
    // Parastoo: code change ends here.

    /**
     * @author Parastoo , Date 05/05/2026
     *         Tracks whether music is enabled or disabled via Settings.
     */
    private static boolean musicEnabled = true;

    /**
     * @author Parastoo , Date 05/05/2026
     *         Enables or disables background music.
     *         Called from SettingsPane.
     */
    public static void setMusicEnabled(boolean enabled) {
        musicEnabled = enabled;
        if (mediaPlayer != null) {
            if (enabled) {
                mediaPlayer.play();
            } else {
                mediaPlayer.pause();
            }
        }
    }

    /**
     * @author Parastoo , Date 08/05/2026
     * Returns the current music enabled state so Settings UI can reflect it.
     */
    public static boolean isMusicEnabled() {
        return musicEnabled;
    }

    /**
     * @author Parastoo , Date 12/05/2026
     *         Sets game speed: 1 = fast (1 sec/turn), 2 = slow (2 sec/turn).
     *         Called from SettingsPane.
     */
    public static void setTurnSeconds(int seconds) {
        TURN_SECONDS = seconds;
    }

    public static int getTurnSeconds() {
        return TURN_SECONDS;
    }
    // Parastoo: code change ends here.

    private AudioClip popSound;
    private AudioClip errorSound;
    private AudioClip slashSound; // Shah Abdul Qadir: Added combat sound
    private AudioClip explosionSound; // Shah Abdul Qadir: Added combat sound
    // Shah Abdul Qadir: code change ends here

    /**
     * Creates a new game of Ants vs. Some-Bees, with the given colony and hive
     * setup
     * * @param colony The ant colony for the game
     * 
     * @param hive The hive (and attack plan) for the game
     */
    public AntGame(AntColony colony, Hive hive, Stage stage, int level) {

        /**
         * @author Shah Abdul Qadir , Date 03/04/2026
         *         Initialize the static instance so ants can access it
         */
        this.level = level;
        instance = this;
        // Shah Abdul Qadir: code change ends here

        /**
         * @author Parastoo , Date 05/05/2026
         *         Sets level in ScoreManager immediately when game is created.
         */
        ScoreManager.reset();
        ScoreManager.setLevel(level);
        // Parastoo: code change ends here.

        // game init stuff
        this.colony = colony;
        this.hive = hive;

        // game clock tracking
        this.frame = 0;
        this.turn = 0;

        // member ant property storage variables
        ANT_TYPES = new ArrayList<String>();
        ANT_IMAGES = new HashMap<String, Image>();
        LEAF_COLORS = new HashMap<String, Color>();
        initializeAnts();

        // tracking bee animations
        allBeePositions = new HashMap<Bee, AnimPosition>();
        beeSpawnAreas = new HashMap<String, Rectangle>();
        beeSpawnImages = new HashMap<String, Image>();
        initializeBees();
        leaves = new ArrayList<AnimPosition>();

        // map clickable areas
        antSelectorAreas = new HashMap<Rectangle, Ant>();
        colonyAreas = new HashMap<Rectangle, Place>();
        colonyRects = new HashMap<Place, Rectangle>();

        initializeColony();
        initializeAntSelector();

        // UI Setup

        this.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT);
        /**
         * @author Paddy 25/02/2026
         *         These 2 listners track the stage width and height for window
         *         resizing.
         *         the newVal can be used to calculate the normalized values to scale
         *         the windows.
         *         newVal/originalVal
         */
        setStage(stage);
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            normalizedWidth = (double) newVal / SCENE_WIDTH;
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            normalizedHeight = (double) newVal / SCENE_HEIGHT;
        });
        // Paddy 25/04/2026: edit ends here.

        this.getChildren().add(canvas);

        // adding interaction
        this.setOnMousePressed(this::handleClick);
        this.setOnMouseMoved(this::handleMouseOver);

        /**
         * @author Paddy 26/04/2026
         *         replaced alot of the manually created text boxes with a generic
         *         TextArea initializer.
         *         it takes the possition and size of the text box and its color along
         *         with a TextArea to
         *         build on and add to the canvas.
         */
        beeDescriptorArea = new TextArea();
        antDescriptorArea = new TextArea();
        colonyReportsArea = new TextArea();
        hiveReportsArea = new TextArea();
        feedbackArea = new TextArea();
        mouseOverToolTip = new TextArea();
        initTextAreas(colonyReportsArea, 80, 770, 500, 250, 14, "rgb(10, 212, 165);", "Colony Report:\n");
        initTextAreas(antDescriptorArea, 620, 770, 500, 250, 14, "rgb(10, 212, 165);",
                "Ant Description:\nMouse over Ants to see details here.");
        initTextAreas(hiveReportsArea, 1200, 460, 500, 250, 14, "rgb(231, 91, 76);", "Hive Report:\n");
        initTextAreas(beeDescriptorArea, 1200, 180, 500, 250, 14, "rgb(231, 91, 76);",
                "Bee Description:\nMouse over Bees to see details here.");
        initTextAreas(feedbackArea, 300, 140, 650, 45, 24, "rgb(76, 179, 231);", feedbackUI);
        // paddy 26/04/2026

        initTextAreas(mouseOverToolTip,0,0,150,50,14,"rgb(76, 179, 231);", "tooltip");
        mouseOverToolTip.layoutXProperty().unbind();
        mouseOverToolTip.layoutYProperty().unbind();
        mouseOverToolTip.setStyle("-fx-font-weight: bold;");



        // Set up the Game Loop
        clock = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // Throttle to roughly 30 FPS (33.3 million nanoseconds)
                if (now - lastUpdate >= 33_333_333) {
                    update(); // Shah Abdul Qadir 18/04/2026: Changed from nextFrame()
                    render();
                    lastUpdate = now;
                }
            }
        };

        /**
         * @author Parastoo , Date 03/05/2026
         *         Stops any existing music before starting a new instance.
         *         Fixes the music multiplication bug when the game is restarted.
         */
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        // Parastoo: code change ends here.
        /**
         * @author Shah Abdul Qadir , Date 28/04/2026
         *         Offloaded all audio initialization to a background thread.
         *         This prevents the main thread from blocking, fixing the freeze when
         *         the game starts up.
         */
        new Thread(() -> {
            try {
                //String musicFile = getClass().getResource("audio/bgm.wav").toExternalForm();
                //Media sound = new Media(musicFile);
                //MediaPlayer player = new MediaPlayer(sound);
                MediaPlayer player = FileUtils.loadMediaPlayer("audio/bgm.wav");
                if (player == null) {
                     System.err.println("Failed to load background music!");
                    return;
                }
                player.setCycleCount(MediaPlayer.INDEFINITE);
                player.setVolume(0.1);

                // Pre-load the sound effects into memory
                AudioClip pop = FileUtils.loadAudioClip("audio/pop.wav");
                AudioClip error = FileUtils.loadAudioClip("audio/error.wav");
                AudioClip slash = FileUtils.loadAudioClip("audio/slash.wav");
                AudioClip explosion = FileUtils.loadAudioClip("audio/explosion.wav");

                // AudioClip error = new AudioClip(new File("audio/error.wav").toURI().toString());
                // AudioClip slash = new AudioClip(new File("audio/slash.wav").toURI().toString()); // Added
                // AudioClip explosion = new AudioClip(new File("audio/explosion.wav").toURI().toString()); // Added

                // Safely assign back to the main thread
                Platform.runLater(() -> {
                    mediaPlayer = player;
                    mediaPlayer.play();
                    popSound = pop;
                    errorSound = error;
                    slashSound = slash; // Added
                    explosionSound = explosion; // Added
                });
            } catch (Exception e) {
                System.out.println("Audio Error: " + e.getMessage());
            }
        }).start();
        // Shah Abdul Qadir: code change ends here

        // Initial render before loop starts
        render();
    }

    /**
     * @author Parastoo , Date 19/04/2026
     *         Original startGameLoop method kept for reference.
     *         Commented out by Parastoo
     *         Replaced to ensure the paused state is cleared whenever the game loop
     *         starts.
     */
    // public void startGameLoop() {
    // clock.start();
    // isRunning = true;
    // }
    // Parastoo: original startGameLoop method commented out here.

    /**
     * @author Parastoo , Date 19/04/2026
     *         Starts the main game loop.
     *         If the game was previously paused, this also clears the paused state.
     */
    public void startGameLoop() {
        if (clock != null) {
            clock.start();
        }
        isRunning = true;
        isPaused = false;
        /**
         * @author Parastoo , Date 26/04/2026
         *         Starts the game timer when the game loop begins.
         */
        GameTimer.start();
        // Parastoo: code change ends here.
        /**
         * @author Parastoo , Date 05/05/2026
         *         Resets score and sets level when game starts.
         */
        ScoreManager.reset();
        ScoreManager.setLevel(level);
        // Parastoo: code change ends here.
    }
    // Parastoo: code change ends here.

    

    public void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        /**
         * @author Shah Abdul Qadir , Date 28/04/2026
         *         Dynamically calculate scale so the game board doesn't wait for a
         *         window resize event.
         */
        if (this.getWidth() > 0 && this.getHeight() > 0) {
            normalizedWidth = this.getWidth() / SCENE_WIDTH;
            normalizedHeight = this.getHeight() / SCENE_HEIGHT;
        }
        // Shah Abdul Qadir: code change ends here

        // paddy 25/04/2026: scales the width of the screen by normalized values
        gc.save();
        gc.scale(normalizedWidth, normalizedHeight);
        // paddy 25/04/2026 edit ends here

        // clear to background color (White)
        gc.clearRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT);

        drawAntSelector(gc);

        // text displays
        String antString = "none";
        if (selectedAnt != null) {
            antString = selectedAnt.getClass().getName();
            antString = antString.substring(0, antString.length() - 3); // remove the word "ant"
        }

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("SansSerif", 12));
        gc.fillText("Ant selected: " + antString, 80, 20);
        gc.fillText("Food: " + colony.getFood() + ", Turn: " + turn, 80, 140);
        /**
         * @author Parastoo , Date 05/05/2026
         *         Displays current score and player name on screen during gameplay.
         */
        gc.setFill(Color.DARKGREEN);
        gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 14));
        gc.fillText(ScoreManager.getScoreString() + " (x" + level + ") | Level: " + level, 80, 155);
        // Parastoo: code change ends here.

        /**
         * @author Parastoo , Date 30/03/2026
         *         Added instructional UI text to guide the player.
         *         This improves user experience by explaining how to interact with the
         *         game.
         */
        // Move instructions below the food/turn text for better layout
        gc.setFill(Color.DARKBLUE);
        gc.setFont(Font.font("SansSerif", FontWeight.NORMAL, 13));

        // Instruction for placing ants
        gc.fillText("Select an ant and click a tunnel", 80, 170);

        // Instruction for removing ants
        gc.fillText("Click an occupied tunnel to remove it", 80, 190);
        // Parastoo: code change ends here

        drawColony(gc);
        drawBees(gc);
        drawLeaves(gc);

        /**
         * @author Parastoo , Date 30/03/2026
         *         Calls the feedback UI method to display messages on the screen.
         *         This ensures that user feedback is rendered during each frame.
         */
        drawFeedbackBox(gc);
        // Parastoo: code change ends here.

        // drawTurnTerminalAnts(gc);
        // drawTurnTerminalBees(gc);
        // drawAntDescriptorTerminal(gc);
        // drawBeeDescriptorTerminal(gc);

        if (!isRunning) { // start text
            gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 32));
            gc.setFill(Color.RED);
            gc.fillText("CLICK TO START", SCENE_WIDTH / 2 - 100, SCENE_HEIGHT / 2);
        }
        gc.restore();
    }

    /**
     * @author Shah Abdul Qadir , Date 18/04/2026
     *         Renamed from nextFrame() to update() to align with JavaFX/Game Loop
     *         terminology.
     *         Runs the actual game, processing what occurs on every frame of the
     *         game.
     */
    private void update() {
        if (frame == 0) // at the start of a turn
        {
            System.out.println("TURN: " + turn);

            // ants take action!
            for (Ant ant : colony.getAllAnts()) {
                if (ant instanceof ThrowerAnt) {
                    Bee target = ((ThrowerAnt) ant).getTarget();
                    if (target != null)
                        createLeaf(ant, target);
                }
                ant.action(colony);
            }

            // bees take action!
            for (Bee bee : colony.getAllBees()) {
                bee.action(colony);
                startAnimation(bee);
            }

            // new invaders attack!
            Bee[] invaders = hive.invade(colony, turn);
            for (Bee bee : invaders)
                startAnimation(bee);
        }

        if (frame == (int) (LEAF_SPEED * FPS)) // after leaves animate
        {
            for (Map.Entry<Bee, AnimPosition> entry : allBeePositions.entrySet()) {
                if (entry.getKey().getArmor() <= 0) { // if dead bee
                    AnimPosition pos = entry.getValue();
                    pos.animateTo((int) pos.x, CRYPT_HEIGHT, FPS * TURN_SECONDS);
                }
            }
        }

        // every frame
        for (AnimPosition pos : allBeePositions.values()) {
            if (pos.framesLeft > 0)
                pos.step();
        }
        Iterator<AnimPosition> iter = leaves.iterator();
        while (iter.hasNext()) {
            AnimPosition leaf = iter.next();
            if (leaf.framesLeft > 0)
                leaf.step();
            else
                iter.remove();
        }

        // ADVANCE THE CLOCK COUNTERS
        frame++;
        if (frame == FPS * TURN_SECONDS) {
            turn++;
            frame = 0;
            updateColonyReports();
        }

        if (frame == (int) (TURN_SECONDS * FPS / 2)) // wait half a turn (1.5 sec) before ending
        {
            // check for end condition before proceeding
            if (colony.queenHasBees()) { // we lost!
                clock.stop();
                /**
                 * @author Parastoo , Date 26/04/2026
                 *         Stops the game timer when the player loses.
                 */
                GameTimer.stop();
                // Parastoo: code change ends here.

                /**
                 * @author Parastoo , Date 26/04/2026
                 *         Fires the onGameEnd callback to save game results before closing.
                 */
                if (onGameEnd != null)
                    onGameEnd.run();
                // Parastoo: code change ends here.

                /**
                 * @author Parastoo , Date 03/05/2026
                 *         Shows the Game Over screen when the player loses.
                 */
                if (onGameOver != null)
                    Platform.runLater(() -> onGameOver.accept(false));
                // Parastoo: code change ends here.
            }
            if (hive.getBees().length + colony.getAllBees().size() == 0) { // no more bees--we won!
                clock.stop();
                /**
                 * @author Parastoo , Date 26/04/2026
                 *         Stops the game timer when the player wins.
                 */
                GameTimer.stop();
                // Parastoo: code change ends here.

                /**
                 * @author Parastoo , Date 26/04/2026
                 *         Fires the onGameEnd callback to save game results before closing.
                 */
                if (onGameEnd != null)
                    onGameEnd.run();
                // Parastoo: code change ends here.

                /**
                 * @author Parastoo , Date 03/05/2026
                 *         Shows the Win screen when the player wins.
                 */
                if (onGameOver != null)
                    Platform.runLater(() -> onGameOver.accept(true));
                // Parastoo: code change ends here.

            }
            appendColonyReport();
        }

    }
    // Shah Abdul Qadir: code change ends here

    /**
     * Handles clicking on the screen
     */
    private synchronized void handleClick(MouseEvent e) {
        /**
         * @author Shah Abdul Qadir , Date 28/04/2026
         *         Moved the startup check to the VERY TOP of the click handler.
         *         This ensures the game always starts immediately on the first click,
         *         without getting blocked by the grid logic.
         */
        if (!isRunning) {
            startGameLoop();
        }

        /**
         * @author Shah Abdul Qadir , Date 28/04/2026
         *         Mouse coordinates must be divided by the scale to match the logical
         *         grid when resized.
         *         Also fixed deployment logic so ants can be placed on empty tiles.
         */
        double x = e.getX() / normalizedWidth;
        double y = e.getY() / normalizedHeight;

        // check if deploying an ant
        for (Rectangle rect : colonyAreas.keySet()) {
            if (rect.contains(x, y)) {
                Place targetPlace = colonyAreas.get(rect);
                if (selectedAnt == null) {
                    feedbackUI = colony.removeAnt(targetPlace);
                    return;
                } else {
                    Ant deployable = buildAnt(selectedAnt.getClass().getName());

                    // 1. Check if the tile is occupied and if they can share (Bodyguard logic)
                    if (targetPlace.getAnt() != null) {
                        Ant existingAnt = targetPlace.getAnt();
                        boolean validSharedPlacement = (existingAnt.canContainAnt()
                                && existingAnt.getContainedAnt() == null && deployable.canBeContained())
                                || (deployable.canContainAnt() && deployable.getContainedAnt() == null
                                        && existingAnt.canBeContained());

                        // If they can't share the tile, reject it immediately and stop
                        if (!validSharedPlacement) {
                            setFeedbackUI("Cannot deploy: Tile is already occupied!", "error");
                            playSoundEffect("error.wav");
                            return;
                        }
                    }

                    // 2. Deploy the ant (Works for empty tiles AND valid shared tiles)
                    String result = colony.deployAnt(targetPlace, deployable);

                    if (result.toLowerCase().contains("success")) {
                        setFeedbackUI(result, "success");
                        playSoundEffect("pop.wav");
                    } else {
                        setFeedbackUI(result, "error");
                        playSoundEffect("error.wav");
                    }
                }
                return; // Stop checking other rectangles once we've handled the click
            }
        }
        // Shah Abdul Qadir: code change ends here

        // check if selecting an ant
        for (Rectangle rect : antSelectorAreas.keySet()) {
            if (rect.contains(x, y)) {
                selectedAnt = antSelectorAreas.get(rect);
                return;
            }
        }
        // check if remover
        if (removerArea.contains(x, y)) {
            selectedAnt = null;
            return;
        }
    }

    private void startAnimation(Bee b) {
        AnimPosition anim = allBeePositions.get(b);
        if (anim.framesLeft == 0) {
            Rectangle rect = colonyRects.get(b.getPlace());
            if (rect != null && !rect.contains(anim.x, anim.y))
                anim.animateTo((int) (rect.getX() + PLACE_PADDING_W), (int) (rect.getY() + PLACE_PADDING_H),
                        FPS * TURN_SECONDS);
        }
    }

    private void createLeaf(Ant source, Bee target) {
        Rectangle antRect = colonyRects.get(source.getPlace());
        Rectangle beeRect = colonyRects.get(target.getPlace());
        int startX = (int) (antRect.getX() + LEAF_START_OFFSET_W);
        int startY = (int) (antRect.getY() + LEAF_START_OFFSET_H);
        int endX = (int) (beeRect.getX() + LEAF_END_OFFSET_W);
        int endY = (int) (beeRect.getY() + LEAF_END_OFFSET_H);

        AnimPosition leaf = new AnimPosition(startX, startY);
        leaf.animateTo(endX, endY, (int) (LEAF_SPEED * FPS));
        leaf.color = LEAF_COLORS.get(source.getClass().getName());

        leaves.add(leaf);
    }

    private void drawColony(GraphicsContext gc) {
        for (Map.Entry<Rectangle, Place> entry : colonyAreas.entrySet()) {
            Rectangle rect = entry.getKey();
            Place place = entry.getValue();

            gc.setStroke(Color.BLACK);
            gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

            if (place != tunnelEnd && TUNNEL_IMAGE != null)
                gc.drawImage(TUNNEL_IMAGE, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

            /**
             * @author paddy 01/04/2026:
             *         puts a water image generated by AI at this possition if place.iswater
             *         is set to true.
             */
            if (place.getIsWater()) {
                gc.drawImage(WATER_IMAGE, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
            // paddy 01/04/2026: edit ends here.

            /**
             * @author Parastoo , Date 25/04/2026
             *         Original drawColony ant drawing kept for reference.
             *         Commented out by Parastoo , Date 25/04/2026
             *         Replaced to show contained ant icon when BodyguardAnt is present.
             */
            // Ant ant = place.getAnt();
            // if (ant != null) {
            // Image img = ANT_IMAGES.get(ant.getClass().getName());
            // if (img != null) {
            // gc.drawImage(img, rect.getX() + PLACE_PADDING_W, rect.getY() +
            // PLACE_PADDING_H);
            // }
            // }
            // Parastoo: original drawColony ant drawing commented out here.

            /**
             * @author Parastoo , Date 25/04/2026
             *         Updated to show contained ant icon (small) when BodyguardAnt is
             *         present.
             *         Both the Bodyguard and the protected ant are now visible on the tile.
             */
            Ant ant = place.getAnt();
            if (ant != null) {
                Ant contained = ant.getContainedAnt();
                if (contained != null) {
                    Image containedImg = ANT_IMAGES.get(contained.getClass().getName());
                    if (containedImg != null) {
                        gc.drawImage(containedImg,
                                rect.getX() + PLACE_PADDING_W,
                                rect.getY() + PLACE_PADDING_H + 20,
                                ANT_IMAGE_WIDTH * 0.55,
                                ANT_IMAGE_HEIGHT * 0.55);
                    }
                }
                Image img = ANT_IMAGES.get(ant.getClass().getName());
                if (img != null) {
                    gc.drawImage(img, rect.getX() + PLACE_PADDING_W, rect.getY() + PLACE_PADDING_H, ANT_IMAGE_WIDTH,
                            ANT_IMAGE_HEIGHT);
                }
            }
            // Parastoo: code change ends here.

        }
    }

    /**
     * @author Shah Abdul Qadir , Date 14/04/2026
     *         Updated drawBees to render the custom AI-generated GIFs for Ghost and
     *         Zombie bees.
     */
    private void drawBees(GraphicsContext gc) {
        for (Map.Entry<Bee, AnimPosition> entry : allBeePositions.entrySet()) {
            Bee bee = entry.getKey();
            AnimPosition pos = entry.getValue();

            /**
             * @author Shah Abdul Qadir , Date 28/04/2026
             *         Fixed 'instanceof' syntax bug for ZombieBee.
             */
            // 1. Draw Ghost Bee
            if (bee instanceof GhostBee && GHOST_BEE_IMAGE != null) {
                gc.drawImage(GHOST_BEE_IMAGE, pos.x, pos.y, BEE_IMAGE_WIDTH, ANT_IMAGE_HEIGHT);
            }
            // 2. Draw Zombie Bee
            else if (bee instanceof ZombieBee && ZOMBIE_BEE_IMAGE != null) {
                gc.drawImage(ZOMBIE_BEE_IMAGE, pos.x, pos.y, BEE_IMAGE_WIDTH, ANT_IMAGE_HEIGHT);
            }
            // 3. Draw Standard Bee
            else if (BEE_IMAGE != null) {
                gc.drawImage(BEE_IMAGE, pos.x, pos.y, BEE_IMAGE_WIDTH, ANT_IMAGE_HEIGHT);
            }
            // Shah Abdul Qadir: code change ends here
        }
        for (Map.Entry<String, Rectangle> entry : beeSpawnAreas.entrySet()) {
            Rectangle rect = entry.getValue();
            gc.setStroke(Color.BLACK);
            gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            gc.drawImage(beeSpawnImages.get(entry.getKey()), rect.getX() + 10, rect.getY());
        }
    }
    // Shah Abdul Qadir: code change ends here

    private void drawLeaves(GraphicsContext gc) {
        for (AnimPosition leafPos : leaves) {
            double angle = leafPos.framesLeft * Math.PI / 8;

            double[] a = { angle - Math.PI, angle - 3 * Math.PI / 4, angle - Math.PI / 2, angle - Math.PI / 4, angle,
                    angle + Math.PI / 4, angle + Math.PI / 2, angle + 3 * Math.PI / 4 };
            double[] d = { LEAF_SIZE / 3.0, LEAF_SIZE / 2.5, LEAF_SIZE / 2.0, LEAF_SIZE / 1.5, LEAF_SIZE,
                    LEAF_SIZE / 1.5, LEAF_SIZE / 2.0, LEAF_SIZE / 2.5 };

            gc.setFill(leafPos.color);
            gc.beginPath();
            gc.moveTo(leafPos.x + Math.cos(a[0]) * d[0], leafPos.y + Math.sin(a[0]) * d[0]);
            gc.quadraticCurveTo(leafPos.x + Math.cos(a[1]) * d[1], leafPos.y + Math.sin(a[1]) * d[1],
                    leafPos.x + Math.cos(a[2]) * d[2], leafPos.y + Math.sin(a[2]) * d[2]);
            gc.quadraticCurveTo(leafPos.x + Math.cos(a[3]) * d[3], leafPos.y + Math.sin(a[3]) * d[3],
                    leafPos.x + Math.cos(a[4]) * d[4], leafPos.y + Math.sin(a[4]) * d[4]);
            gc.quadraticCurveTo(leafPos.x + Math.cos(a[5]) * d[5], leafPos.y + Math.sin(a[5]) * d[5],
                    leafPos.x + Math.cos(a[6]) * d[6], leafPos.y + Math.sin(a[6]) * d[6]);
            gc.quadraticCurveTo(leafPos.x + Math.cos(a[7]) * d[7], leafPos.y + Math.sin(a[7]) * d[7],
                    leafPos.x + Math.cos(a[0]) * d[0], leafPos.y + Math.sin(a[0]) * d[0]);
            gc.fill();
        }
    }

    /**
     * @author Parastoo , Date 30/03/2026
     *         Draws a feedback message box on the screen.
     *         The box is shown only when a feedback message exists
     *         and disappears automatically after a short time.
     */
    private void drawFeedbackBox(GraphicsContext gc) {
        // paddy 26/04/2026: removed the manual drawing of text boxes in favour of the
        // text areas
        // this function now just updates the text area.
        // Do not draw anything if there is no message
        if (feedbackUI == null || feedbackUI.isEmpty()) {
            feedbackArea.setVisible(false);
            return;
        }
        // Do not draw if the message display time has expired
        if (System.currentTimeMillis() > feedbackExpiryTime) {
            feedbackArea.setVisible(false);
            return;
        }
        // double boxX = 20;
        // double boxY = 690;
        // double boxW = 700;
        // double boxH = 45;
        // Choose a different background color depending on message type
        if ("error".equalsIgnoreCase(feedbackType))
            feedbackArea.setStyle("-fx-control-inner-background: " + "rgb(255, 220, 220);");
        // gc.setFill(Color.rgb(255, 220, 220));
        else if ("success".equalsIgnoreCase(feedbackType))
            feedbackArea.setStyle("-fx-control-inner-background: " + "rgb(220, 255, 220);");
        // gc.setFill(Color.rgb(220, 255, 220));
        else
            feedbackArea.setStyle("-fx-control-inner-background: " + "rgb(255, 245, 200);");
        // gc.setFill(Color.rgb(255, 245, 200));
        feedbackArea.setText(feedbackUI);
        feedbackArea.setVisible(true);
        // // Draw the feedback box
        // gc.fillRoundRect(boxX, boxY, boxW, boxH, 15, 15);
        // gc.setStroke(Color.BLACK);
        // gc.strokeRoundRect(boxX, boxY, boxW, boxH, 15, 15);
        // // Draw the feedback text inside the box
        // gc.setFill(Color.BLACK);
        // gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 14));
        // gc.fillText(feedbackUI, boxX + 15, boxY + 28);
    }
    // Parastoo: code change ends here.

    private void drawAntSelector(GraphicsContext gc) {
        for (Map.Entry<Rectangle, Ant> entry : antSelectorAreas.entrySet()) {
            Rectangle rect = entry.getKey();
            Ant ant = entry.getValue();

            // box status
            if (ant.getFoodCost() > colony.getFood())
                gc.setFill(Color.GRAY);
            else if (ant == this.selectedAnt)
                gc.setFill(Color.BLUE);
            else
                gc.setFill(Color.WHITE);

            gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

            // box outline
            gc.setStroke(Color.BLACK);
            gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

            // ant image
            Image img = ANT_IMAGES.get(ant.getClass().getName());
            if (img != null) {
                /**
                 * @author Shah Abdul Qadir , Date 04/04/2026
                 *         Remark: Scaled image to ANT_IMAGE_WIDTH/HEIGHT to prevent selection
                 *         tile overflow.
                 */
                gc.drawImage(img,
                        rect.getX() + PANEL_PADDING_W,
                        rect.getY() + PANEL_PADDING_H,
                        ANT_IMAGE_WIDTH,
                        ANT_IMAGE_HEIGHT);
                // Shah Abdul Qadir: code change ends here
            }

            // food cost
            gc.setFill(Color.BLACK);
            gc.fillText("" + ant.getFoodCost(), rect.getX() + (rect.getWidth() / 2) - 5,
                    rect.getY() + ANT_IMAGE_HEIGHT + 4 + PANEL_PADDING_H);
        }

        // for removing an ant
        if (this.selectedAnt == null) {
            gc.setFill(Color.BLUE);
            gc.fillRect(removerArea.getX(), removerArea.getY(), removerArea.getWidth(), removerArea.getHeight());
        }
        gc.setStroke(Color.BLACK);
        gc.strokeRect(removerArea.getX(), removerArea.getY(), removerArea.getWidth(), removerArea.getHeight());

        if (REMOVER_IMAGE != null) {
            gc.drawImage(REMOVER_IMAGE, removerArea.getX() + PANEL_PADDING_W, removerArea.getY() + PANEL_PADDING_H);
        }
    }

    private void initializeAnts() {
        try {
            Scanner sc = new Scanner(new File(ANT_FILE));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.matches("\\w.*")) {
                    String[] parts = line.split(",");
                    String antType = ANT_PKG + "." + parts[0].trim();
                    try {
                        Class.forName(antType);
                        ANT_TYPES.add(antType);
                        ANT_IMAGES.put(antType, FileUtils.loadImage("/" + parts[1].trim()));
                        if (parts.length > 2) {
                            int rgb = Integer.parseInt(parts[2].trim());
                            Color c = Color.rgb((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
                            LEAF_COLORS.put(antType, c);
                        }
                    } catch (ClassNotFoundException e) {
                        System.err.println(e.getMessage() + " making ant failed");
                    }
                }
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("Error loading insect gui properties: " + e);
        }

        /**
         * @author Parastoo , Date 04/04/2026
         *         Adding images for WallAnt and HungryAnt.
         */
        ANT_IMAGES.put("ants.WallAnt", FileUtils.loadImage("/img/ant_wall.gif")); // WallAnt image
        ANT_IMAGES.put("ants.HungryAnt", FileUtils.loadImage("/img/ant_hungry.gif")); // HungryAnt image

        // Parastoo: code change ends here.

    }

    private void initializeBees() {
        Bee[] bees = this.hive.getBees();
        double width = 80;
        double height = 80;
        int nudge = 10;
        int offset = 100;
        for (int i = 0; i < bees.length; i++) {
            if (bees[i].getInsectName().toLowerCase().equals("bee")) {
                allBeePositions.put(bees[i], new AnimPosition((int) (HIVE_POS_X), (int) (HIVE_POS_Y)));
                if (beeSpawnAreas.get("Bee") == null) {
                    Rectangle area = new Rectangle(HIVE_POS_X - nudge, HIVE_POS_Y, width, height);
                    beeSpawnAreas.put("Bee", area);
                    beeSpawnImages.put("Bee", BEE_IMAGE);
                }
            } else if (bees[i].getInsectName().toLowerCase().equals("ghost bee")) {
                allBeePositions.put(bees[i], new AnimPosition((int) (HIVE_POS_X), (int) (HIVE_POS_Y + offset)));
                if (beeSpawnAreas.get("Ghost Bee") == null) {
                    Rectangle area = new Rectangle(HIVE_POS_X - nudge, (HIVE_POS_Y + offset), width, height);
                    beeSpawnAreas.put("Ghost Bee", area);
                    beeSpawnImages.put("Ghost Bee", GHOST_BEE_IMAGE);
                }
            } else if (bees[i].getInsectName().toLowerCase().equals("zombie bee")) {
                allBeePositions.put(bees[i], new AnimPosition((int) (HIVE_POS_X), (int) (HIVE_POS_Y + (offset * 2))));
                if (beeSpawnAreas.get("Zombie Bee") == null) {
                    Rectangle area = new Rectangle(HIVE_POS_X - nudge, (HIVE_POS_Y + (offset * 2)), width, height);
                    beeSpawnAreas.put("Zombie Bee", area);
                    beeSpawnImages.put("Zombie Bee", ZOMBIE_BEE_IMAGE);
                }
            } else {
                allBeePositions.put(bees[i],
                        new AnimPosition((int) (HIVE_POS_X + (20 * Math.random() - 10)),
                                (int) (HIVE_POS_Y + (100 * Math.random() - 50))));
            }

        }

    }

    private void initializeColony() {
        double currentX = PLACE_POS_X;
        double currentY = PLACE_POS_Y;

        double width = BEE_IMAGE_WIDTH + 2 * PLACE_PADDING_W;
        double height = ANT_IMAGE_HEIGHT + 2 * PLACE_PADDING_H;
        int row = 0;

        currentX += (width + PLACE_MARGIN) / 2; // extra shift to make room for queen

        for (Place place : colony.getPlaces()) {
            if (place.getExit() == colony.getQueenPlace()) {
                currentX = PLACE_POS_X;
                currentY = PLACE_POS_Y + row * (height + PLACE_MARGIN);
                currentX += (width + PLACE_MARGIN) / 2;
                row++;
            }

            Rectangle clickable = new Rectangle(currentX, currentY, width, height);
            this.colonyAreas.put(clickable, place);
            this.colonyRects.put(place, clickable);

            currentX += width + PLACE_MARGIN;
        }

        // make queen location
        currentX = 0;
        currentY = PLACE_POS_Y + (row - 1) * (height + PLACE_MARGIN) / 2;
        Rectangle queenRect = new Rectangle(currentX, currentY, 0, 0);
        tunnelEnd = colony.getQueenPlace();
        this.colonyAreas.put(queenRect, tunnelEnd);
        this.colonyRects.put(tunnelEnd, queenRect);
    }

    private void initializeAntSelector() {
        double currentX = PANEL_POS_X;
        double currentY = PANEL_POS_Y;
        double width = ANT_IMAGE_WIDTH + 2 * PANEL_PADDING_W;
        double height = ANT_IMAGE_HEIGHT + 2 * PANEL_PADDING_H;

        removerArea = new Rectangle(currentX, currentY, width, height);
        currentX += width + 2;

        for (String antType : ANT_TYPES) {
            Rectangle clickable = new Rectangle(currentX, currentY, width, height);
            Ant ant = buildAnt(antType);
            this.antSelectorAreas.put(clickable, ant);

            currentX += width + 2;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Ant buildAnt(String antType) {
        System.out.println("printing ant types = " + antType);
        Ant ant = null;
        try {
            // Paddy 10/04/2026 when building an ant this now checks if the queen is being
            // build and returns an instance copy instead of a new version.
            if (!antType.equals("ants.QueenAnt")) {
                Class antClass = Class.forName(antType);
                Constructor constructor = antClass.getConstructor();
                ant = (Ant) constructor.newInstance();
            } else {
                System.out.println("returned the queen - buildAnt()");
                ant = (Ant) colony.getQueenAnt();
                return ant;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return ant;
    }

    /**
     * An inner class that encapsulates location information for animation
     */
    private static class AnimPosition {
        private double x, y;
        private double dx, dy;
        private int framesLeft;
        private Color color;

        public AnimPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void step() {
            x += dx;
            y += dy;
            framesLeft--;
        }

        public void animateTo(int nx, int ny, int frames) {
            framesLeft = frames;
            dx = (nx - x) / framesLeft;
            dy = (ny - y) / framesLeft;
        }

        public String toString() {
            return "AnimPosition[x=" + x + ",y=" + y + ",dx=" + dx + ",dy=" + dy + ",framesLeft=" + framesLeft + "]";
        }
    }

    /**
     * @author Parastoo , Date 30/03/2026
     *         Sets a feedback message for the UI and stores it for timed display.
     */
    public void setFeedbackUI(String feedback) {
        feedbackUI = feedback;
        feedbackType = "info";
        feedbackExpiryTime = System.currentTimeMillis() + 3000;
    }
    // Parastoo: code change ends here.

    /**
     * @author Parastoo , Date 30/03/2026
     *         Sets a feedback message with a specific type such as info, success,
     *         or error.
     */
    public void setFeedbackUI(String feedback, String type) {
        feedbackUI = feedback;
        feedbackType = type;
        feedbackExpiryTime = System.currentTimeMillis() + 3000;
    }
    // Parastoo: code change ends here

    /**
     * @author Parastoo , Date 30/03/2026
     *         Returns the current feedback message for UI display.
     */
    public String getFeedbackUI() {
        return feedbackUI;
    }
    // Parastoo: code change ends here

    /**
     * @author Shah Abdul Qadir , Date 03/04/2026
     *         Plays a temporary GIF effect over a specific Place.
     *         Uses Platform.runLater to ensure it runs safely on the JavaFX UI
     *         thread,
     *         and forces it to sit on top of the Canvas.
     *         * Update 28/04/2026 (Shah Abdul Qadir): Added scaling multiplication
     *         and effectCache
     *         to completely remove mid-game freezing.
     */
    public void playTemporaryEffect(Place place, String gifName, int durationMs) {
        Rectangle rect = colonyRects.get(place);
        if (rect == null)
            return;

        // Platform.runLater is crucial when mixing game loops with JavaFX UI updates
        Platform.runLater(() -> {
            try {
                // Fetch the image from cache instead of reading the hard drive
                Image effect = effectCache.get(gifName);
                if (effect == null) {
                    effect = new Image("file:img/" + gifName, 0, 0, false, false);
                    effectCache.put(gifName, effect);
                }

                ImageView effectView = new ImageView(effect);

                // FIX: Multiply the coordinates and size by the scale factors so it lines up
                // with resized window
                effectView.setX((rect.getX() + PLACE_PADDING_W) * normalizedWidth);
                effectView.setY((rect.getY() + PLACE_PADDING_H) * normalizedHeight);
                effectView.setFitWidth(ANT_IMAGE_WIDTH * normalizedWidth);
                effectView.setFitHeight(ANT_IMAGE_HEIGHT * normalizedHeight);

                // Add to the pane. Because this happens AFTER the Canvas is added,
                // it naturally layers on top of the Canvas.
                this.getChildren().add(effectView);

                // Remove the GIF after the animation finishes
                PauseTransition delay = new PauseTransition(Duration.millis(durationMs));
                delay.setOnFinished(event -> this.getChildren().remove(effectView));
                delay.play();

            } catch (Exception e) {
                System.out.println("Could not load effect: " + gifName);
                e.printStackTrace();
            }
        });
    }
    // Shah Abdul Qadir: code change ends here

    /**
     * @author Shah Abdul Qadir , Date 27/04/2026
     *         Plays a short sound effect for game actions.
     *         Uses pre-loaded AudioClip so multiple sound effects can overlap
     *         without cutting each other off or causing lag.
     */
    public void playSoundEffect(String filename) {
        try {
            if (filename.equals("pop.wav") && popSound != null) {
                popSound.play(0.4);
            } else if (filename.equals("error.wav") && errorSound != null) {
                errorSound.play(0.4);
            } else if (filename.equals("slash.wav") && slashSound != null) {
                slashSound.play(0.4);
            } else if (filename.equals("explosion.wav") && explosionSound != null) {
                explosionSound.play(0.4);
            }
        } catch (Exception e) {
            System.out.println("Audio Error: Could not play sound effect - " + filename);
        }
    }
    // Shah Abdul Qadir: code change ends here

    // Paddy 28/04/2026 :turn terminal removed

    private void updateColonyReports() {
        colonyReportsArea.appendText("Turn: " + turn + "\n");
        hiveReportsArea.appendText("Turn: " + turn + "\n");
    }

    private void appendColonyReport() {
        for (Ant ant : colony.getAllAnts()) {
            if (!ant.getReport().matches(".*\\bnull\\b.*")) {
                colonyReportsArea.appendText(ant.getReport() + "\n");
            }
        }
        for (Bee bee : hive.getAllBees()) {
            if (!bee.getReport().matches(".*\\bnull\\b.*")) {
                hiveReportsArea.appendText(bee.getReport() + "\n");
            }
        }
    }

    /**
     * @author Parastoo , Date 11/04/2026
     *         Removes a Bee instantly from the animation map so it disappears
     *         immediately from the UI instead of falling down the screen.
     *         This is used by HungryAnt to simulate eating a Bee.
     * @param bee the Bee to remove from the UI
     */
    public void removeBeeInstantly(Bee bee) {
        if (bee != null) {
            allBeePositions.remove(bee);
        }
    }
    // Parastoo: code change ends here.

    /**
     * @author paddy , Date 11/04/2026
     *         Sets the descriptor for the currently hovered ant in the selector
     *         panel for the ants.
     * @param descriptor
     */
    public void setFeedbackAntDescriptor(String descriptor) {
        antDescriptorArea.setText(descriptor);
    }

    // Parastoo: code change ends here.

    /**
     * @author Parastoo , Date 19/04/2026
     *         Pauses the game by stopping the animation timer.
     *         This allows the menu system to temporarily freeze gameplay.
     */
    public void pauseGame() {
        if (clock != null && isRunning && !isPaused) {
            clock.stop();
            isPaused = true;
        }
    }
    // Parastoo: code change ends here.

    /**
     * @author Parastoo , Date 19/04/2026
     *         Resumes the game after a pause by restarting the animation timer.
     */
    public void resumeGame() {
        if (clock != null && isRunning && isPaused) {
            clock.start();
            isPaused = false;
        }
    }
    // Parastoo: code change ends here.

    /**
     * @author Parastoo , Date 19/04/2026
     *         Stops the game completely.
     *         This is used when leaving the current game screen,
     *         such as returning to the main menu or resetting the game.
     */
    public void stopGame() {
        if (clock != null) {
            clock.stop();
        }
        isRunning = false;
        isPaused = false;
        /**
         * @author Parastoo , Date 26/04/2026
         *         Stops the game timer when the game ends.
         */
        GameTimer.stop();
        // Parastoo: code change ends here.
        /**
         * @author Parastoo , Date 05/05/2026
         *         Stops music when returning to main menu.
         */
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        // Parastoo: code change ends here.
    }
    // Parastoo: code change ends here.

    /**
     * @author Parastoo , Date 19/04/2026
     *         Returns whether the game is currently paused.
     * @return true if the game is paused, otherwise false
     */
    public boolean isPaused() {
        return isPaused;
    }

    // Parastoo: code change ends here.
    /**
     * @author paddy 25/04/2026
     * @param descriptor - used to set the terminal string for the bees
     */
    public void setFeedbackBeeDescriptor(String descriptor) {
        beeDescriptorArea.setText(descriptor);
    }

    /**
     * @author paddy , Date 11/04/2026
     *         Handles mouse movement to update the ant descriptor feedback when
     *         hovering over ants in the selector.
     * @param e - represents mouse events as it moves around the stage.
     *          cycles through both Bee and Ant Rectangles to check if the mouse is
     *          in thier area.
     */
    private synchronized void handleMouseOver(MouseEvent e) {

        double x = e.getX() / normalizedWidth;
        double y = e.getY() / normalizedHeight;

        mouseOverToolTip.setLayoutX(x + 10);
        mouseOverToolTip.setLayoutY(y);

        for (Rectangle rect : antSelectorAreas.keySet()) {
            if (rect.contains(x, y)) {
                mouseOverToolTip.setVisible(true);
                Ant ant = antSelectorAreas.get(rect);
                if (ant != null) {
                    mouseOverToolTip.setText(ant.getInsectName());
                    setFeedbackAntDescriptor(
                            "Mouse over Ant to show description. \nAnt Description: \n" + ant.getDescriptor());
                    return;
                }
            }
        }
        for (Map.Entry<String, Rectangle> entry : beeSpawnAreas.entrySet()) {
            if (entry.getValue().contains(x, y)) {
                mouseOverToolTip.setVisible(true);
                switch (entry.getKey()) {
                    case "Bee":
                        mouseOverToolTip.setText("Bee");
                        setFeedbackBeeDescriptor(
                                "Mouse over Bee to show description. \nBee Description: \n" + Bee.getBeeDescriptor());
                        break;
                    case "Ghost Bee":
                        mouseOverToolTip.setText("Ghost Bee");
                        setFeedbackBeeDescriptor("Mouse over Bee to show description. \nBee Description: \n"
                                + GhostBee.getGhostBeeDescriptor());
                        break;
                    case "Zombie Bee":
                        mouseOverToolTip.setText("Zombie Bee");
                        setFeedbackBeeDescriptor("Mouse over Bee to show description. \nBee Description: \n"
                                + ZombieBee.getZombieBeeDescriptor());
                        break;
                    default:
                        System.out.println(entry.getKey() + "got here 4");
                }
                return;
            }
        }
        mouseOverToolTip.setVisible(false);
    }

    /**
     * @author paddy 25/04/2026: the stage is needed to track screen resizing and
     *         get normalized results for scaling graphics.
     * @param stage
     */
    protected void setStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * @author Paddy 25/04/2026
     * @param area     - represents the TextArea that needs to be set up
     * @param originX  - the origin pixel on the x axis
     * @param originY  - the origin pixel on the y axis
     * @param width    - width in pixels
     * @param height   - height in pixels
     * @param fontSize - original font size
     * @param rbg      - what color is needed for the background. red for bees and
     *                 blue for ants
     *                 originally I created 4 different text initializers for each
     *                 text area when i realised it was a
     *                 repeating pattern and a gernic method would suffice for set
     *                 up.
     *                 the method bellow creates the text field and then ensures the
     *                 text box is bound to the stage width and height through
     *                 listeners
     *                 a listner is also set up to watch and update the font size
     *                 with scaling.
     */
    protected void initTextAreas(
            TextArea area, double originX, double originY, double width, double height, double fontSize, String rbg,
            String initText) {
        // Bind position and size to stage
        area.layoutXProperty().bind(primaryStage.widthProperty().multiply(originX / SCENE_WIDTH));
        area.layoutYProperty().bind(primaryStage.heightProperty().multiply(originY / SCENE_HEIGHT));
        area.prefWidthProperty().bind(primaryStage.widthProperty().multiply(width / SCENE_WIDTH));
        area.prefHeightProperty().bind(primaryStage.heightProperty().multiply(height / SCENE_HEIGHT));
        // Bind font size to stage width (scales proportionally)
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            double scaledFontSize = (double) newVal / SCENE_WIDTH * fontSize;
            updateTextAreaStyle(scaledFontSize, area);
        });

        /**
         * @author Shah Abdul Qadir , Date 28/04/2026
         *         Force the initial font size immediately on startup before a resize
         *         event happens.
         */
        if (primaryStage.getWidth() > 0) {
            double initialFontSize = primaryStage.getWidth() / SCENE_WIDTH * fontSize;
            updateTextAreaStyle(initialFontSize, area);
        }
        // Shah Abdul Qadir: code change ends here

        area.setEditable(false);
        area.setWrapText(true);
        area.setFocusTraversable(true);
        area.setMouseTransparent(false);
        area.setStyle(
                "-fx-control-inner-background: " + rbg +
                        "-fx-text-fill: black;" +
                        "-fx-font-family: SansSerif;" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-padding: 10;" +
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius: 15;" +
                        "-fx-focus-color: transparent;" +
                        "-fx-faint-focus-color: transparent;" +
                        "-fx-focused-base-color: rgb(10, 158, 212);" +
                        "-fx-selection-bar: transparent;" +
                        "-fx-selection-bar-non-focused: transparent;");
        area.setText(initText);
        this.getChildren().add(area);
    }

    /**
     * @author Paddy 25/04/2026
     * @param fontSize - represents the new font size updated from when the window
     *                 scales
     * @param area     - represents the text area that needs updating.
     */
    private void updateTextAreaStyle(double fontSize, TextArea area) {
        area.setStyle(area.getStyle() +
                "-fx-font-size: " + fontSize + ";");
    }

    public int getLevel(){
        return level;
    }



}
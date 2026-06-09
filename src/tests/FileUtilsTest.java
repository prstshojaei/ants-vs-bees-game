package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.InputStream;
import javafx.scene.media.AudioClip;
import javafx.scene.image.Image;
import java.net.URL;
import java.beans.Transient;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import core.FileUtils;
import javafx.application.Platform;

/**
 * @author Paddy 08/05/2026
 * This class tests the FileUtils class to ensure that files can be loaded correctly both in development and once packaged in a .jar file. It includes tests for loading images, audio clips, and media players.
 */
public class FileUtilsTest {
    
    /**
     * Initialises the JavaFX toolkit before any tests are run.
     *
     * JavaFX components (such as Image) require the JavaFX runtime
     * environment to be started. When running tests with Maven (Surefire),
     * the JavaFX toolkit is not automatically initialised, which causes
     * errors like "Toolkit not initialized".
     *
     * Platform.startup() manually starts the JavaFX runtime in a headless
     * test environment so that JavaFX classes can be safely used in unit tests.
     *
     * Note: This should only be called once per JVM lifecycle.
     */

    @BeforeAll
    static void initJfx() {
        Platform.startup(() -> {});
    }
    
   @Test
    public void testLoadImageFromValidPath() {
        // checks "Group1-CSYM026-AntsVsBees\out\img - to load files"
        Image image = FileUtils.loadImage("/img/ant_wall.gif");
        System.out.println("did this work? :"+FileUtils.class.getResource("/img/ant_wall.gif"));
        assertNotNull(image);
    }
    @Test
    public void testLoadAudioClipFromValidPath() {
        AudioClip audioClip = FileUtils.loadAudioClip("audio/pop.wav");
        assertNotNull(audioClip);
    }
    @Test   
    public void testLoadMediaFromValidPath() {
        new Thread(() -> {
        MediaPlayer player = FileUtils.loadMediaPlayer("audio/bgm.wav");
        assertNotNull(player);
        }).start();
    }

}

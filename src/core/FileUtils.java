package core;

import java.io.InputStream;
import javafx.scene.media.AudioClip;
import javafx.scene.image.Image;
import java.net.URL;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class FileUtils {

    /**
     * @author Paddy 08/05/2026
     * The load image replaced the old ImageUtils classs to enable building to .jar executable
     * by using FileUtils.class.getResourceAsStream the file can be loaded both in development and once packaged in .jar
     * @param filePath - represents the path to the image being loaded.
     * @return - return the image or null plus an exception message if the file is not found.
     */
        public static Image loadImage(String filePath) {
             try {
            InputStream inputStream = FileUtils.class.getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + filePath);
            }
            return new Image(inputStream);
            } catch (Exception e) {
                System.err.println("Error loading \'" + filePath + "\': " + e.getMessage());
                return null;
            }
        }

        /**
         * @author Paddy 08/05/2026
         * Loads an audio clip from a file. the method first checks if the file exists on the drive for development builds
         * if the files does not exist it then tries to load the resource from the bundled .jar file
         * both methods are needed as they work differently depending on how the project is compiled.
         * @param filePath represents the file path to the audio clip
         * @return - the audio file or null and its exception.
         */
        public static AudioClip loadAudioClip(String filePath) {
        // Try file path first (for development with mvn javafx:run)
        File file = new File(filePath);
        if (file.exists()) {
            return new AudioClip(file.toURI().toString());
        }
        // Fall back to classpath (for JAR execution)
        try {
            URL resource = FileUtils.class.getResource("/" + filePath);
            if (resource != null) {
                return new AudioClip(resource.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Could not load audio: " + filePath);
        }
        
        return null;
    }

     /**
     * @author Paddy 08/05/2026
     * Loads an Media File from a file. the method first checks if the file exists on the drive for development builds
     * if the files does not exist it then tries to load the resource from the bundled .jar file
     * both methods are needed as they work differently depending on how the project is compiled.
     * @param filePath represents the file path to the media file
     * @return - the audio file or null and its exception.
     */    
    public static MediaPlayer loadMediaPlayer(String filePath) {
        // Try file path first (for development with mvn javafx:run)
        File file = new File(filePath);
        if (file.exists()) {
            String musicFile = file.toURI().toString();
            Media sound = new Media(musicFile);
            return new MediaPlayer(sound);
        }
        // Fall back to classpath (for JAR execution)
        try {
            URL resource = FileUtils.class.getResource("/" + filePath);
            if (resource != null) {
                String musicFile = resource.toExternalForm();
                Media sound = new Media(musicFile);
                return new MediaPlayer(sound);
            }
        } catch (Exception e) {
            System.err.println("Could not load audio: " + filePath);
        }
        
        return null;
    }
}

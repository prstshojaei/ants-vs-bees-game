package core;

/**
 * @author Parastoo , Date 26/04/2026
 *         GameTimer: Tracks how long a game session lasts in seconds.
 *         This class is static so any team member can call it from anywhere.
 *
 *         Usage:
 *         - Call GameTimer.start() when the game begins
 *         - Call GameTimer.stop() when the game ends (win or lose)
 *         - Call GameTimer.getElapsedSeconds() to get the total time
 *         - Paddy can call GameTimer.getElapsedSeconds() to save time to
 *         UserAccount
 *
 *         Example:
 *         GameTimer.start();
 *         ... game runs ...
 *         GameTimer.stop();
 *         long time = GameTimer.getElapsedSeconds();
 *         UserManager.updateTime("Parastoo", time);
 */
public class GameTimer {

    private static long startTime = 0; // System time when game started
    private static long endTime = 0; // System time when game ended
    private static boolean running = false; // Whether the timer is active

    /**
     * Starts the timer. Call this when the game loop begins.
     * If the timer is already running, this resets it.
     */
    public static void start() {
        startTime = System.currentTimeMillis();
        endTime = 0;
        running = true;
        System.out.println("GameTimer started.");
    }

    /**
     * Stops the timer. Call this when the game ends (win or lose).
     * After stopping, call getElapsedSeconds() to get the result.
     */
    public static void stop() {
        if (running) {
            endTime = System.currentTimeMillis();
            running = false;
            System.out.println("GameTimer stopped. Time: " + getElapsedSeconds() + "s");
        }
    }

    /**
     * Returns how many seconds have passed since the timer started.
     * If the timer is still running, returns time so far.
     * If the timer has stopped, returns the total session time.
     * 
     * @return elapsed time in seconds
     */
    public static long getElapsedSeconds() {
        if (startTime == 0)
            return 0;
        long end = running ? System.currentTimeMillis() : endTime;
        return (end - startTime) / 1000;
    }

    /**
     * Returns whether the timer is currently running.
     * 
     * @return true if the timer is active
     */
    public static boolean isRunning() {
        return running;
    }

    /**
     * Resets the timer completely.
     * Call this when starting a fresh game session.
     */
    public static void reset() {
        startTime = 0;
        endTime = 0;
        running = false;
    }

    /**
     * Returns a formatted time string for display on screen.
     * Format: MM:SS (e.g. "02:45")
     * 
     * @return formatted time string
     */
    public static String getFormattedTime() {
        long seconds = getElapsedSeconds();
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
}
// Parastoo: code change ends here.
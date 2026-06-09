package core;

/**
 * @author Parastoo , Date 25/04/2026
 *         UserAccount: Represents a single player account in the Ants vs
 *         Some-Bees game.
 *         Each account stores the player's name, the level they played,
 *         their score, whether they passed the level, and how long they took.
 *
 *         This class is designed to be used with UserManager.java for saving
 *         and loading.
 *         Other team members can use the getters and setters to update account
 *         data.
 *         For example, Paddy can call setScore() when the game ends to record
 *         the score.
 */
public class UserAccount {

    private String name; // The player's display name
    private int level; // The level the player played (1, 2, or 3)
    private int score; // The player's score - set by Paddy's scoring system
    private boolean passed; // True if the player successfully completed the level
    private long timeTaken; // How long the level took in seconds

    /**
     * @author Parastoo , Date 03/05/2026
     *         Stores the seed used for this game session.
     *         Allows players to replay the same game configuration.
     */
    private long seed;
    // Parastoo: code change ends here.

    /**
     * Creates a new UserAccount with default values.
     * All game stats start at zero/false until updated after a game session.
     * 
     * @param name the player's name - must not be null or empty
     */
    public UserAccount(String name) {
        this.name = name;
        this.level = 0;
        this.score = 0;
        this.passed = false;
        this.timeTaken = 0;
    }

    /**
     * Returns the player's name.
     * 
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the level the player played.
     * 
     * @return level number (1, 2, or 3)
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level the player played.
     * Called by GameAppController when a level is selected.
     * 
     * @param level the level number (1, 2, or 3)
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the player's score.
     * 
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     * To be called by Paddy's scoring system when the game ends.
     * 
     * @param score the final score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns whether the player passed the level.
     * 
     * @return true if the player won, false if they lost
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Sets whether the player passed the level.
     * Called when the game ends - true if all bees were defeated.
     * 
     * @param passed true if the player won, false if they lost
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    /**
     * Returns how long the level took in seconds.
     * 
     * @return time taken in seconds
     */
    public long getTimeTaken() {
        return timeTaken;
    }

    /**
     * Sets how long the level took in seconds.
     * Called when the game ends to record the session duration.
     * 
     * @param timeTaken time in seconds
     */
    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    /**
     * Returns a readable summary of this account.
     * Useful for debugging and the in-game account display screen.
     */

     /**
     * @author Parastoo , Date 03/05/2026
     *         Returns the seed used for this game session.
     * @return the game seed
     */
    public long getSeed() {
        return seed;
    }

    /**
     * @author Parastoo , Date 03/05/2026
     *         Sets the seed used for this game session.
     * @param seed the game seed
     */
    public void setSeed(long seed) {
        this.seed = seed;
    }

    // Parastoo: code change ends here.
    @Override
    public String toString() {
        return "UserAccount[name=" + name +
                ", level=" + level +
                ", score=" + score +
                ", passed=" + passed +
                ", timeTaken=" + timeTaken + "s]";
    }
}
// Parastoo: code change ends here.
package core;

/**
 * @author Parastoo , Date 05/05/2026
 *         ScoreManager: Handles score calculation for the game.
 *         Score is based on bees killed and multiplied by level.
 *
 *         Scoring rules:
 *         - Standard Bee: 10 points
 *         - Ghost Bee: 20 points
 *         - Zombie Bee: 30 points
 *         - Level 1: score x1
 *         - Level 2: score x2
 *         - Level 3: score x3
 *
 *         Usage:
 *         ScoreManager.addKill(bee, level);
 *         int score = ScoreManager.getScore();
 *         ScoreManager.reset();
 */
public class ScoreManager {

    private static int score = 0;
    private static int level = 1;

    /**
     * Sets the current level multiplier.
     * Call this when the game starts.
     * 
     * @param level the current level (1, 2, or 3)
     */
    public static void setLevel(int level) {
        ScoreManager.level = level;
    }

    /**
     * Resets the score to zero.
     * Call this at the start of each new game.
     */
    public static void reset() {
        score = 0;
    }

    /**
     * Adds points for killing a bee.
     * Points are multiplied by the current level.
     * 
     * @param bee the bee that was killed
     */
    public static void addKill(Bee bee) {
        int points = 0;
        if (bee instanceof ZombieBee) {
            points = 30;
        } else if (bee instanceof GhostBee) {
            points = 20;
        } else {
            points = 10;
        }
        score += points * level;
        System.out.println("Kill! +" + (points * level) + " points. Total: " + score);
    }

    /**
     * Returns the current score.
     * 
     * @return the current score
     */
    public static int getScore() {
        return score;
    }

    /**
     * Returns a formatted score string for display.
     * 
     * @return score as string
     */
    public static String getScoreString() {
        return "Score: " + score;
    }
}
// Parastoo: code change ends here.
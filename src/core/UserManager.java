package core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Parastoo , Date 25/04/2026
 *         UserManager: Handles all user account operations for the game.
 *         Saves and loads user data from a JSON file called users.json.
 *
 *         All methods are static so other team members can call them directly
 *         without creating an instance. For example:
 *         - UserManager.createUser("Parastoo")
 *         - UserManager.updateScore("Parastoo", 100)
 *         - UserManager.getAllUsers()
 *
 *         Paddy can use updateScore() and setPassed() when the game ends.
 *         GameAppController can use createUser() and getUser() for the menu
 *         system.
 */
public class UserManager {

    private static final String FILE_PATH = "users.json";

    /**
     * Creates a new user account and saves it to users.json.
     * If a user with the same name already exists, no duplicate is created.
     * 
     * @param name the player's name - must not be null or empty
     * @return the newly created UserAccount, or the existing one if already present
     */
    public static UserAccount createUser(String name) {
        List<UserAccount> users = getAllUsers();
        for (UserAccount u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                System.out.println("User already exists: " + name);
                return u;
            }
        }
        UserAccount newUser = new UserAccount(name);
        users.add(newUser);
        saveAllUsers(users);
        System.out.println("User created: " + name);
        return newUser;
    }

    /**
     * Returns all user accounts loaded from users.json.
     * Returns an empty list if the file does not exist yet.
     * 
     * @return list of all UserAccounts
     */
    public static List<UserAccount> getAllUsers() {
        List<UserAccount> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists())
            return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            String json = sb.toString().trim();

            // Parse each user entry from the JSON array
            if (json.startsWith("{") && json.contains("\"users\"")) {
                int start = json.indexOf('[');
                int end = json.lastIndexOf(']');
                if (start == -1 || end == -1)
                    return users;
                String array = json.substring(start + 1, end).trim();
                if (array.isEmpty())
                    return users;

                // Split by user objects
                for (String entry : splitUserEntries(array)) {
                    UserAccount u = parseUser(entry.trim());
                    if (u != null)
                        users.add(u);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users.json: " + e.getMessage());
        }
        return users;
    }

    /**
     * Finds and returns a single user by name.
     * Returns null if no user with that name exists.
     * 
     * @param name the player's name to search for
     * @return the UserAccount, or null if not found
     */
    public static UserAccount getUser(String name) {
        for (UserAccount u : getAllUsers()) {
            if (u.getName().equalsIgnoreCase(name))
                return u;
        }
        return null;
    }

    /**
     * Updates the score for a user and saves to file.
     * To be called by Paddy's scoring system when the game ends.
     * 
     * @param name  the player's name
     * @param score the final score to record
     */
    public static void updateScore(String name, int score) {
        updateUser(name, score, -1, null, -1);
    }

    /**
     * Updates the level for a user and saves to file.
     * Called by GameAppController when the player selects a level.
     * 
     * @param name  the player's name
     * @param level the level number (1, 2, or 3)
     */
    public static void updateLevel(String name, int level) {
        updateUser(name, -1, level, null, -1);
    }

    /**
     * Sets whether the player passed the level and saves to file.
     * Called when the game ends.
     * 
     * @param name   the player's name
     * @param passed true if the player won, false if they lost
     */
    public static void setPassed(String name, boolean passed) {
        updateUser(name, -1, -1, passed, -1);
    }

    /**
     * Updates the time taken for a user and saves to file.
     * Called when the game ends to record session duration.
     * 
     * @param name      the player's name
     * @param timeTaken time in seconds
     */
    public static void updateTime(String name, long timeTaken) {
        updateUser(name, -1, -1, null, timeTaken);
    }



    /**
     * @author Parastoo , Date 03/05/2026
     *         Updates the seed for a user and saves to file.
     *         Called when a game session starts with a specific seed.
     * @param name the player's name
     * @param seed the game seed used
     */
    public static void updateSeed(String name, long seed) {
        List<UserAccount> users = getAllUsers();
        for (UserAccount u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                u.setSeed(seed);
                saveAllUsers(users);
                return;
            }
        }
        System.out.println("User not found: " + name);
    }

    // Parastoo: code change ends here.

    
    /**
     * Internal helper that updates one or more fields for a user and saves.
     * Pass -1 or null for any field you do not want to update.
     */
    private static void updateUser(String name, int score, int level, Boolean passed, long timeTaken) {
        List<UserAccount> users = getAllUsers();
        for (UserAccount u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                if (score >= 0)
                    u.setScore(score);
                if (level >= 0)
                    u.setLevel(level);
                if (passed != null)
                    u.setPassed(passed);
                if (timeTaken >= 0)
                    u.setTimeTaken(timeTaken);
                saveAllUsers(users);
                return;
            }
        }
        System.out.println("User not found: " + name);
    }

    /**
     * Saves all user accounts to users.json.
     * Overwrites the existing file completely.
     * 
     * @param users the list of UserAccounts to save
     */
    private static void saveAllUsers(List<UserAccount> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println("{");
            writer.println("  \"users\": [");
            for (int i = 0; i < users.size(); i++) {
                UserAccount u = users.get(i);
                writer.println("    {");
                writer.println("      \"name\": \"" + u.getName() + "\",");
                writer.println("      \"level\": " + u.getLevel() + ",");
                writer.println("      \"score\": " + u.getScore() + ",");
                writer.println("      \"passed\": " + u.isPassed() + ",");
                /**
                 * @author Parastoo , Date 03/05/2026
                 *         Original timeTaken line kept for reference.
                 *         Commented out by Parastoo , Date 03/05/2026
                 *         Replaced to add seed field to JSON.
                 */
                // writer.println(" \"timeTaken\": " + u.getTimeTaken());
                // Parastoo: original timeTaken line commented out here.

                /**
                 * @author Parastoo , Date 03/05/2026
                 *         Added seed field to saved user data.
                 */
                writer.println("      \"timeTaken\": " + u.getTimeTaken() + ",");
                writer.println("      \"seed\": " + u.getSeed());
                // Parastoo: code change ends here.

                writer.print("    }");
                if (i < users.size() - 1)
                    writer.println(",");
                else
                    writer.println();
            }
            writer.println("  ]");
            writer.println("}");
        } catch (IOException e) {
            System.out.println("Error saving users.json: " + e.getMessage());
        }
    }

    /**
     * Splits a JSON array string into individual user object strings.
     * Used internally during file parsing.
     */
    private static List<String> splitUserEntries(String array) {
        List<String> entries = new ArrayList<>();
        int depth = 0;
        int start = 0;
        for (int i = 0; i < array.length(); i++) {
            char c = array.charAt(i);
            if (c == '{') {
                if (depth == 0)
                    start = i;
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0)
                    entries.add(array.substring(start, i + 1));
            }
        }
        return entries;
    }

    /**
     * Parses a single JSON user object string into a UserAccount.
     * Used internally during file loading.
     */
    private static UserAccount parseUser(String json) {
        try {
            String name = extractString(json, "name");
            int level = Integer.parseInt(extractString(json, "level"));
            int score = Integer.parseInt(extractString(json, "score"));
            boolean passed = Boolean.parseBoolean(extractString(json, "passed"));
            long timeTaken = Long.parseLong(extractString(json, "timeTaken"));
            /**
             * @author Parastoo , Date 03/05/2026
             *         Loads seed from saved user data.
             */
            long seed = 0;
            try {
                seed = Long.parseLong(extractString(json, "seed"));
            } catch (Exception ignored) {
            }
            // Parastoo: code change ends here.

            UserAccount u = new UserAccount(name);
            u.setLevel(level);
            u.setScore(score);
            u.setPassed(passed);
            u.setTimeTaken(timeTaken);
            u.setSeed(seed);
            return u;
        } catch (Exception e) {
            System.out.println("Error parsing user entry: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extracts a value from a JSON string by key.
     * Works for both string and numeric values.
     */
    private static String extractString(String json, String key) {
        String search = "\"" + key + "\"";
        int keyIndex = json.indexOf(search);
        if (keyIndex == -1)
            return "";
        int colonIndex = json.indexOf(':', keyIndex);
        int start = colonIndex + 1;
        while (start < json.length() && json.charAt(start) == ' ')
            start++;
        if (json.charAt(start) == '"') {
            int end = json.indexOf('"', start + 1);
            return json.substring(start + 1, end);
        } else {
            int end = start;
            while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}')
                end++;
            return json.substring(start, end).trim();
        }
    }
}
// Parastoo: code change ends here.
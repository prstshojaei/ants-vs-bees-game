package core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Parastoo , Date 03/05/2026
 *         SeedGenerator: Generates a 12-digit seed based on the current date
 *         and time.
 *         The seed is used to create reproducible game sessions.
 *         Players can share seeds to play the same game configuration.
 *
 *         Usage:
 *         long seed = SeedGenerator.seedGenerator();
 *         String seedStr = String.valueOf(seed);
 */
public class SeedGenerator {

    /**
     * Generates a 12-digit seed from the current date and time.
     * Format: yyyyMMddHHmm (e.g., 202605031430)
     * 
     * @return a 12-digit long seed value
     */
    public static long seedGenerator() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        long dateTimeInt = Long.parseLong(LocalDateTime.now().format(formatter));
        return dateTimeInt;
    }
}
// Parastoo: code change ends here.
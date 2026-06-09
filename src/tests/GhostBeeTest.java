package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Shah Abdul Qadir , Date 14/04/2026
 * Unit tests verifying the mechanics of enemy units.
 */
public class GhostBeeTest {

    @Test
    public void testGhostBeeEvasionMechanic() {
        // Setup a GhostBee with massive armor so it doesn't die during the test
        int initialArmor = 1000;
        GhostBee ghost = new GhostBee(initialArmor);
        
        int damageTaken = 0;
        int iterations = 1000;

        // Hit the bee 1000 times
        for (int i = 0; i < iterations; i++) {
            int currentArmor = ghost.getArmor();
            ghost.reduceArmor(1);
            
            // If armor decreased, the dodge failed.
            if (ghost.getArmor() < currentArmor) {
                damageTaken++;
            }
        }

        // With a 50% dodge rate, damageTaken should be around 500. 
        // We use a safe statistical bound (400 to 600) to prevent random false-failures.
        assertTrue(damageTaken > 400 && damageTaken < 600, 
                   "Ghost Bee should evade roughly 50% of attacks. Hits taken: " + damageTaken);
    }
}
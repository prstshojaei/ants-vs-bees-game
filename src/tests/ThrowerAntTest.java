package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ants.ThrowerAnt;
import core.Bee;
import core.Place;

/**
 * @author Parastoo, Date 26/03/2026
 *
 * This test class is prepared for ThrowerAnt behaviour.
 * It includes a safe test for the no-target case and a functional
 * test for bee damage once the ant implementation is confirmed complete.
 */
public class ThrowerAntTest {

    /**
     * @author Parastoo, Date 26/03/2026
     *
     * This test verifies that ThrowerAnt does not crash
     * when there is no bee available to attack.
     */
    @Test
    void testThrowerAntNoTarget() {

        // Step 1: Create a place
        Place place = new Place("testPlace");

        // Step 2: Create a ThrowerAnt
        ThrowerAnt ant = new ThrowerAnt();

        /**
         * @author Parastoo
         * Added for testing: placing ThrowerAnt into the Place
         * without any bees present.
         */
        place.addInsect(ant);

        // Step 3: Verify that no exception is thrown
        assertDoesNotThrow(() -> ant.action(null),
                "ThrowerAnt should not fail when no target exists");
    }

    /**
     * @author Parastoo, Date 26/03/2026
     *
     * This test verifies that ThrowerAnt damages a bee by 1
     * when a valid target exists.
     *
     * Note: this test depends on the final implementation of ThrowerAnt.
     */
    @Test
    void testThrowerAntAttacksBee() {

        // Step 1: Create a place
        Place place = new Place("testPlace");

        // Step 2: Create a ThrowerAnt and a Bee
        ThrowerAnt ant = new ThrowerAnt();
        Bee bee = new Bee(3);

        /**
         * @author Parastoo
         * Added for testing: placing both ThrowerAnt and Bee
         * into the same Place to simulate an attack scenario.
         */
        place.addInsect(ant);
        place.addInsect(bee);

        // Step 3: Execute ant action
        ant.action(null);

        // Step 4: Verify bee armor was reduced from 3 to 2
        assertEquals(2, bee.getArmor(),
                "ThrowerAnt should reduce bee armor by 1");
    }
}
        // Parastoo: code change ends here.
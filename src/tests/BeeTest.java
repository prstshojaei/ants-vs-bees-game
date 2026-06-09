package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ants.HarvesterAnt;
import core.Bee;
import core.Place;

/**
 * @author Parastoo, Date 26/03/2026
 * 
 * This test class verifies the behaviour of the Bee class,
 * including attacking and moving logic.
 */
public class BeeTest {

    /**
     * @author Parastoo, Date 26/03/2026
     * 
     * Verifies that a bee attacks an ant when blocked.
     */
    @Test
    void testBeeStingsWhenBlocked() {

        // Step 1: Create a place
        Place place = new Place("testPlace");

        // Step 2: Create a bee and an ant
        Bee bee = new Bee(3);
        HarvesterAnt ant = new HarvesterAnt();

        // Step 3: Add both to the same place
        place.addInsect(bee);
        place.addInsect(ant);

        /**
         * @author Parastoo
         * Added for testing: simulate bee action when blocked by ant.
         */
        bee.action(null);


        // Step 4: Verify ant lost armor (1 damage)
        assertEquals(0, ant.getArmor(),
                "Bee should reduce ant armor when blocked");
    }

    /**
     * @author Parastoo, Date 26/03/2026
     * 
     * Verifies that a bee moves forward when not blocked.
     */
    @Test
    void testBeeMovesWhenNotBlocked() {

        // Step 1: Create two places (like a tunnel)
        Place next = new Place("next");
        Place current = new Place("current", next);

        // Step 2: Link entrance
        next.setEntrance(current);

        // Step 3: Create bee
        Bee bee = new Bee(3);

        // Step 4: Place bee in current location
        current.addInsect(bee);

        /**
         * @author Parastoo
         * Added for testing: simulate bee movement when not blocked.
         */
        bee.action(null);

        // Step 5: Verify bee moved to next place
        assertEquals(next, bee.getPlace(),
                "Bee should move to next place when not blocked");
    }
}
        // Parastoo: code change ends here.
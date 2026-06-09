package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ants.NinjaAnt;
import core.Bee;
import core.Place;

public class NinjaAntTest {

    /**
     * @author Shah Abdul Qadir, Date 01/04/2026
     * Verifies that the NinjaAnt does not block bees and deals 1 damage 
     * to all bees in its place when its action() is executed.
     */
    @Test
    void testNinjaAntActionAndStealth() {

        // Step 1: Create a test place for the insects
        Place place = new Place("Test Place");

        // Step 2: Create a NinjaAnt and bees
        NinjaAnt ninja = new NinjaAnt();
        Bee bee1 = new Bee(2);
        Bee bee2 = new Bee(2);

        // Step 3: Add the ant and bees to the place
        place.addInsect(ninja);
        place.addInsect(bee1);  
        place.addInsect(bee2);

        // Step 4: Store bees' armor before the Ninja's action
        int bee1Before = bee1.getArmor();
        int bee2Before = bee2.getArmor();

        // Step 5: Execute the ant's action (null colony since Ninja doesn't need it)
        ninja.action(null);

        // Step 6: Store bees' armor after action
        int bee1After = bee1.getArmor();
        int bee2After = bee2.getArmor();

        // Step 7: Verify the bees took 1 damage and the path is not blocked
        assertEquals(bee1Before - 1, bee1After, "Bee 1 should take 1 damage from Ninja slice");
        assertEquals(bee2Before - 1, bee2After, "Bee 2 should take 1 damage from Ninja slice");
        assertFalse(ninja.blocksPath(), "NinjaAnt should not block the path");
    }
}
// Shah Abdul Qadir: code change ends here.
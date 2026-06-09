package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ants.FireAnt;
import core.Bee;
import core.Place;

public class FireAntTest {

    /**
     * @author Shah Abdul Qadir, Date 01/04/2026
     * Verifies that the FireAnt explodes upon death, dealing 3 damage
     * to all bees currently occupying the same place.
     */
    @Test
    void testFireAntExplosion() {

        // Step 1: Create a test place for the insects
        Place place = new Place("Test Place");

        // Step 2: Create a FireAnt and bees (Bees need 4 armor to survive the 3 damage blast)
        FireAnt fire = new FireAnt();
        Bee bee1 = new Bee(4);
        Bee bee2 = new Bee(4);

        // Step 3: Add the ant and bees to the exact same place
        place.addInsect(fire);
        place.addInsect(bee1);
        place.addInsect(bee2);

        // Step 4: Store bees' armor before the explosion
        int bee1Before = bee1.getArmor();
        int bee2Before = bee2.getArmor();

        // Step 5: Execute the ant's death (reduce armor to 0 to trigger explosion)
        fire.reduceArmor(1);

        // Step 6: Store bees' armor after explosion
        int bee1After = bee1.getArmor();
        int bee2After = bee2.getArmor();

        // Step 7: Verify the FireAnt is dead and bees took exactly 3 damage
        assertEquals(0, fire.getArmor(), "FireAnt should be dead");
        assertEquals(bee1Before - 3, bee1After, "Bee 1 should take 3 damage from explosion");
        assertEquals(bee2Before - 3, bee2After, "Bee 2 should take 3 damage from explosion");
    }
}
// Shah Abdul Qadir: code change ends here.
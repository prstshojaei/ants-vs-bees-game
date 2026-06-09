/**
 * @author Parastoo, Date 25/04/2026
 * HungryAntTest: This test class verifies the behavior of the HungryAnt class,
 * including the ability to eat a bee, track digestion time, and not eat while digesting.
 * JavaFX is not required because AntGame interaction is handled via reflection inside HungryAnt.
 */
package tests;

import ants.HungryAnt;
import core.Bee;
import core.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HungryAntTest {

    private HungryAnt hungryAnt;
    private Place testPlace;

    /**
     * Set up a fresh HungryAnt and Place before each test.
     * The HungryAnt is placed inside the test Place to simulate real game
     * conditions.
     */
    @BeforeEach
    public void setUp() {
        hungryAnt = new HungryAnt();
        testPlace = new Place("testPlace");
        testPlace.addInsect(hungryAnt); // Place HungryAnt in the test location
    }

    /**
     * Verifies that the HungryAnt eats a Bee on action and starts digestion.
     * After eating, digestTimeLeft should be 3 and the Bee should be removed from
     * the place.
     */
    @Test
    public void testEatBee() {
        Bee bee = new Bee(3);
        testPlace.addInsect(bee); // Add bee to same place as HungryAnt

        hungryAnt.action(null); // HungryAnt should eat the bee

        assertEquals(3, hungryAnt.getDigestTimeLeft(),
                "Digest time left should be 3 after eating a bee");
        assertEquals(0, testPlace.getBees().length,
                "Bee should be removed from the place after being eaten");
    }

    /**
     * Verifies that the digestion timer decreases by 1 each turn while digesting.
     * After eating and then acting once more, digestTimeLeft should be 2.
     */
    @Test
    public void testDigestTimeReduction() {
        Bee bee = new Bee(3);
        testPlace.addInsect(bee);

        hungryAnt.action(null); // Eat the bee - digestTimeLeft becomes 3
        hungryAnt.action(null); // One digestion turn - digestTimeLeft should become 2

        assertEquals(2, hungryAnt.getDigestTimeLeft(),
                "Digest time left should be 2 after one turn of digestion");
    }

    /**
     * Verifies that HungryAnt does not eat a second Bee while still digesting.
     * The second Bee should remain in the place until digestion is complete.
     */
    @Test
    public void testDoesNotEatWhileDigesting() {
        Bee firstBee = new Bee(3);
        Bee secondBee = new Bee(3);

        testPlace.addInsect(firstBee);
        hungryAnt.action(null); // Eat first bee - start digesting

        testPlace.addInsect(secondBee);
        hungryAnt.action(null); // Still digesting - should NOT eat second bee

        assertEquals(2, hungryAnt.getDigestTimeLeft(),
                "Digest time left should decrease to 2 while digesting");
        assertEquals(1, testPlace.getBees().length,
                "Second bee should still be in the place while HungryAnt is digesting");
    }
}

// Parastoo: code change ends here.
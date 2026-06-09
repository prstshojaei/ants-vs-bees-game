/**
 * @author Parastoo , Date 13/04/2026
 * BodyguardAntTest: This test class verifies that BodyguardAnt can protect
 * another Ant, take damage first, and leave the contained Ant in place after dying.
 */

package tests;

import ants.BodyguardAnt;
import ants.ThrowerAnt;
import core.Bee;
import core.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BodyguardAntTest {

    private BodyguardAnt bodyguardAnt;
    private ThrowerAnt throwerAnt;
    private Bee bee;
    private Place testPlace;
    private Place nextPlace;

    /**
     * Sets up test objects before each test.
     * Creates a Place containing a BodyguardAnt protecting a ThrowerAnt,
     * plus a Bee that can sting them.
     */
    @BeforeEach
    public void setUp() {
        bodyguardAnt = new BodyguardAnt();
        throwerAnt = new ThrowerAnt();
        bee = new Bee(3);

        nextPlace = new Place("nextPlace");
        testPlace = new Place("testPlace", nextPlace);
        nextPlace.setEntrance(testPlace);

        testPlace.addInsect(throwerAnt);
        testPlace.addInsect(bodyguardAnt);
        testPlace.addInsect(bee);
    }

    /**
     * Verifies that BodyguardAnt correctly contains another Ant.
     */
    @Test
    public void testBodyguardContainsAnt() {
        assertEquals(bodyguardAnt, testPlace.getAnt(),
                "BodyguardAnt should be the visible ant in the place");
        assertEquals(throwerAnt, bodyguardAnt.getContainedAnt(),
                "BodyguardAnt should contain the ThrowerAnt");
    }

    /**
     * Verifies that the BodyguardAnt takes damage first when the Bee stings.
     */
    @Test
    public void testBodyguardTakesDamageFirst() {
        bee.action(null); // sting once

        assertEquals(1, bodyguardAnt.getArmor(),
                "BodyguardAnt should lose 1 armor after one sting");
        assertEquals(1, throwerAnt.getArmor(),
                "Contained ThrowerAnt should not take damage while BodyguardAnt is alive");
    }

    /**
     * Verifies that after two stings, BodyguardAnt dies
     * and the contained Ant remains in the Place.
     */
    @Test
    public void testContainedAntRemainsAfterBodyguardDies() {
        bee.action(null); // first sting
        bee.action(null); // second sting

        assertEquals(throwerAnt, testPlace.getAnt(),
                "ThrowerAnt should remain in the place after BodyguardAnt dies");
        assertNull(bodyguardAnt.getPlace(),
                "BodyguardAnt should be removed from the place after dying");
    }
}

//Parastoo: code change ends here.
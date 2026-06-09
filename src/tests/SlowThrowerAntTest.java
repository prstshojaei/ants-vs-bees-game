/**
 * @author Parastoo , Date 13/04/2026
 * SlowThrowerAntTest: This test class verifies the behavior of the SlowThrowerAnt class,
 * including applying the slow effect and causing Bees to skip every other turn.
 */
package tests;

import ants.SlowThrowerAnt;
import core.Bee;
import core.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SlowThrowerAntTest {

    private SlowThrowerAnt slowThrowerAnt;
    private Bee bee;
    private Place queenPlace;
    private Place antPlace;
    private Place beePlace;

    /*
     * Set up test objects before each test.
     * This creates a SlowThrowerAnt, a Bee, and linked Places for movement
     */
    @BeforeEach
    public void setUp() {
        slowThrowerAnt = new SlowThrowerAnt();
        bee = new Bee(3);

        queenPlace = new Place("queenPlace");
        antPlace = new Place("antPlace", queenPlace);
        beePlace = new Place("beePlace", antPlace);

        queenPlace.setEntrance(antPlace);
        antPlace.setEntrance(beePlace);

        antPlace.addInsect(slowThrowerAnt);
        beePlace.addInsect(bee);
    }

    /**
     * Verifies that SlowThrowerAnt applies the slow effect to a Bee.
     * After being slowed, the Bee should skip its first action turn.
     */
    @Test
    public void testApplySlowEffect() {
        slowThrowerAnt.action(null); // Apply slow to the Bee
        bee.action(null); // First slowed turn should be skipped

        assertEquals(beePlace, bee.getPlace(), "Bee should stay in the same place on the first slowed turn");
    }

    /*
     * Verifies that a slowed Bee acts on the second turn after skipping the first
     * After one skipped turn, the Bee should move forward on its next action
     */
    @Test
    public void testBeeActsEveryOtherTurnWhenSlowed() {
        slowThrowerAnt.action(null); // Apply slow to the Bee

        bee.action(null); // First slowed turn: skip
        assertEquals(beePlace, bee.getPlace(), "Bee should remain in the same place on the skipped turn");

        bee.action(null); // Second slowed turn: act
        assertEquals(antPlace, bee.getPlace(), "Bee should move forward on the next allowed turn");
    }
}

// Parastoo: code change ends here.
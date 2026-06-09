/**
 * @author Parastoo , Date 13/04/2026
 * StunThrowerAntTest: This test class verifies the behavior of the StunThrowerAnt class,
 * including applying the stun effect and ensuring the Bee skips one turn only.
 */

package tests;

import ants.StunThrowerAnt;
import core.Bee;
import core.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StunThrowerAntTest {

    private StunThrowerAnt stunAnt;
    private Bee bee;
    private Place queenPlace;
    private Place antPlace;
    private Place beePlace;

    /**
     * Set up test objects before each test.
     * Creates a simple 3-place tunnel:
     * beePlace -> antPlace -> queenPlace
     */
    @BeforeEach
    public void setUp() {
        stunAnt = new StunThrowerAnt();
        bee = new Bee(3);

        queenPlace = new Place("queenPlace");
        antPlace = new Place("antPlace", queenPlace);
        beePlace = new Place("beePlace", antPlace);

        queenPlace.setEntrance(antPlace);
        antPlace.setEntrance(beePlace);

        antPlace.addInsect(stunAnt);
        beePlace.addInsect(bee);
    }

    /**
     * Verifies that StunThrowerAnt applies stun correctly.
     * The Bee should skip its next action completely.
     */
    @Test
    public void testStunSkipsOneTurn() {
        stunAnt.action(null); // Apply stun

        bee.action(null); // Bee should skip
        assertEquals(beePlace, bee.getPlace(),
                "Bee should not move during the stunned turn");
    }

    /**
     * Verifies that after being stunned for one turn,
     * the Bee resumes normal behavior.
     */
    @Test
    public void testBeeRecoversAfterStun() {
        stunAnt.action(null); // Apply stun

        bee.action(null); // turn 1 → skip
        assertEquals(beePlace, bee.getPlace(),
                "Bee should stay during stunned turn");

        bee.action(null); // turn 2 → should move
        assertEquals(antPlace, bee.getPlace(),
                "Bee should move after stun effect ends");
    }
}

// Parastoo: code change ends here.
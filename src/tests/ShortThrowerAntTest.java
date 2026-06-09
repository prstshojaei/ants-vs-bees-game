/**
 * @author Parastoo , Date 13/04/2026
 * ShortThrowerAntTest: This test class verifies that ShortThrowerAnt
 * only targets Bees within a maximum range of 2 places.
 */

package tests;

import ants.ShortThrowerAnt;
import core.Bee;
import core.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShortThrowerAntTest {

    private ShortThrowerAnt ant;
    private Place p0, p1, p2, p3;

    /**
     * Set up a 4-place tunnel:
     * p3 -> p2 -> p1 -> p0
     * Ant is placed at p0
     */
    @BeforeEach
    public void setUp() {
        ant = new ShortThrowerAnt();

        p0 = new Place("p0");
        p1 = new Place("p1", p0);
        p2 = new Place("p2", p1);
        p3 = new Place("p3", p2);

        p0.setEntrance(p1);
        p1.setEntrance(p2);
        p2.setEntrance(p3);

        p0.addInsect(ant);
    }

    /**
     * Verifies that ShortThrowerAnt can target Bees within 2 places.
     */
    @Test
    public void testTargetWithinRange() {
        Bee bee = new Bee(3);
        p2.addInsect(bee); // distance = 2

        assertEquals(bee, ant.getTarget(),
                "ShortThrowerAnt should target bees within 2 places");
    }

    /**
     * Verifies that ShortThrowerAnt cannot target Bees beyond 2 places.
     */
    @Test
    public void testNoTargetOutOfRange() {
        Bee bee = new Bee(3);
        p3.addInsect(bee); // distance = 3

        assertNull(ant.getTarget(),
                "ShortThrowerAnt should NOT target bees beyond 2 places");
    }
}

//Parastoo: code change ends here.
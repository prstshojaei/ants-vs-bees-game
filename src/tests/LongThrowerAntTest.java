/**
 * @author Parastoo , Date 13/04/2026
 * LongThrowerAntTest: This test class verifies that LongThrowerAnt
 * only targets Bees at a minimum range of 4 places.
 */

package tests;

import ants.LongThrowerAnt;
import core.Bee;
import core.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LongThrowerAntTest {

    private LongThrowerAnt ant;
    private Place p0, p1, p2, p3, p4;

    /**
     * Set up a 5-place tunnel:
     * p4 -> p3 -> p2 -> p1 -> p0
     * Ant is placed at p0
     */
    @BeforeEach
    public void setUp() {
        ant = new LongThrowerAnt();

        p0 = new Place("p0");
        p1 = new Place("p1", p0);
        p2 = new Place("p2", p1);
        p3 = new Place("p3", p2);
        p4 = new Place("p4", p3);

        p0.setEntrance(p1);
        p1.setEntrance(p2);
        p2.setEntrance(p3);
        p3.setEntrance(p4);

        p0.addInsect(ant);
    }

    /**
     * Verifies that LongThrowerAnt cannot target Bees closer than 4 places.
     */
    @Test
    public void testNoTargetTooClose() {
        Bee bee = new Bee(3);
        p3.addInsect(bee); // distance = 3

        assertNull(ant.getTarget(),
                "LongThrowerAnt should NOT target bees closer than 4 places");
    }

    /**
     * Verifies that LongThrowerAnt can target Bees at distance 4.
     */
    @Test
    public void testTargetAtMinimumLongRange() {
        Bee bee = new Bee(3);
        p4.addInsect(bee); // distance = 4

        assertEquals(bee, ant.getTarget(),
                "LongThrowerAnt should target bees at least 4 places away");
    }
}

//Parastoo: code change ends here.
package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ants.HarvesterAnt;
import core.Place;

/**
 * @author Parastoo, Date 26/03/2026
 * 
 *         This test class verifies the behaviour of the Place class,
 *         specifically adding and removing ants.
 */
public class PlaceTest {

    /**
     * @author Parastoo, Date 26/03/2026
     * 
     *         Verifies that an ant can be successfully added to a Place.
     */
    @Test
    void testAddAntToPlace() {

        // Step 1: Create a new place
        Place place = new Place("testPlace");

        // Step 2: Create a HarvesterAnt
        HarvesterAnt ant = new HarvesterAnt();

        /**
         * @author Parastoo
         *         Added for testing: placing the ant into the Place.
         */
        place.addInsect(ant);
        // Parastoo: code change ends here.

        // Step 3: Verify the ant is now in the place
        assertEquals(ant, place.getAnt(),
                "Place should contain the ant after adding it");
    }

    /**
     * @author Parastoo, Date 26/03/2026
     * 
     *         Verifies that an ant can be removed from a Place.
     */
    @Test
    void testRemoveAntFromPlace() {

        // Step 1: Create a new place
        Place place = new Place("testPlace");

        // Step 2: Create a HarvesterAnt
        HarvesterAnt ant = new HarvesterAnt();

        // Step 3: Add the ant
        place.addInsect(ant);

        /**
         * @author Parastoo
         *         Added for testing: removing the ant from the Place.
         */
        place.removeInsect(ant);

        // Step 4: Verify the place is empty
        assertNull(place.getAnt(),
                "Place should be empty after removing the ant");
    }
}
// Parastoo: code change ends here.
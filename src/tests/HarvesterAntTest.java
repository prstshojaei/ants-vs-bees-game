package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ants.HarvesterAnt;
import core.AntColony;
import core.Place;

public class HarvesterAntTest {

    /**
     * @author Parastoo, Date 26/03/2026
     * Verifies that HarvesterAnt increases the colony's food by 1
     * when action() is executed.
     */
    @Test
    void testHarvesterAddsFood() {

        // Step 1: Create a colony with initial food = 5
        AntColony colony = new AntColony(1, 1, 0, 5, 10, 1);

        // Step 2: Get a place from the colony
        Place place = colony.getPlaces()[0];

        // Step 3: Create a HarvesterAnt
        HarvesterAnt ant = new HarvesterAnt();

        place.addInsect(ant);


        // Step 4: Store food before action
        int before = colony.getFood();

        // Step 5: Execute the ant's action
        ant.action(colony);

        // Step 6: Store food after action
        int after = colony.getFood();

        // Step 7: Verify food increased by 1
        assertEquals(before + 1, after,
                "HarvesterAnt should increase food by 1");
    }
}

        // Parastoo: code change ends here.
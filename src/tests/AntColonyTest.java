package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import core.AntColony;

/**
 * @author Parastoo, Date 26/03/2026
 * 
 * This test class verifies the basic food-related behaviour of the AntColony.
 * It ensures that food is initialized correctly and increases as expected.
 */
public class AntColonyTest {

    /**
     * @author Parastoo, Date 26/03/2026
     * 
     * This test verifies that calling increaseFood() correctly
     * adds the specified amount to the colony's food.
     */
    @Test
    void testIncreaseFood() {

        // Step 1: Create a colony with initial food = 5
        AntColony colony = new AntColony(1, 1, 0, 5, 10, 1);

        /**
         * @author Parastoo
         * Added for testing: increasing the colony's food by 2.
         */
        colony.increaseFood(2);

        // Step 2: Verify the final food value is correct (5 + 2 = 7)
        assertEquals(7, colony.getFood(),
                "AntColony should correctly increase food");
    }

    /**
     * @author Parastoo, Date 26/03/2026
     * 
     * This test verifies that the colony is initialized
     * with the correct starting food value.
     */
    @Test
    void testInitialFoodValue() {

        // Step 1: Create a colony with initial food = 3
        AntColony colony = new AntColony(1, 1, 0, 3, 10, 1);

        /**
         * @author Parastoo
         * Added for testing: verifying initial food value.
         */


        // Step 2: Check that initial food is stored correctly
        assertEquals(3, colony.getFood(),
                "AntColony should store the initial food value correctly");
    }
}
        // Parastoo: code change ends here.
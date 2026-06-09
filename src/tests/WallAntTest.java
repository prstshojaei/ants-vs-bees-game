/**
 * @author Parastoo , Date 05/04/2026
 * WallAntTest: This test class verifies the behavior of the WallAnt class,
 * including its constructor and action method.
 */ 
package tests;

import ants.WallAnt;
import core.AntColony;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WallAntTest {

    private WallAnt wallAnt;
    private AntColony colony;

    /**
     * Set up a new WallAnt and AntColony before each test.
     * This method is executed before each test to initialize the objects.
     */
    @BeforeEach
    public void setUp() {
        wallAnt = new WallAnt(); // Create a new instance of WallAnt
        colony = new AntColony(100, 100, 50, 10, 10, 1); // Create a new instance of AntColony with some parameters
    }

    /**
     * Verifies that the constructor of WallAnt initializes the ant correctly.
     * This test checks if the armor, food cost, and ant name are set correctly.
     */
    @Test
    public void testConstructor() {
        assertEquals(4, wallAnt.getArmor(), "Armor should be 4");
        assertEquals(4, wallAnt.getFoodCost(), "Food cost should be 4");
        assertEquals("Wall Ant", wallAnt.getInsectName(), "Ant name should be 'Wall Ant'");
    }

    /**
     * Verifies that the action method of WallAnt does not affect the colony.
     * Since WallAnt does nothing during its action, the colony's food should remain unchanged.
     */
    @Test
    public void testActionDoesNothing() {
        int initialFood = colony.getFood(); // Get the initial food in the colony

        wallAnt.action(colony); // Perform the action for WallAnt (it does nothing)

        // Verify that the colony's food remains the same after the action
        assertEquals(initialFood, colony.getFood(), "Colony's food should not change when WallAnt performs action");
    }
}

//Parastoo: code change ends here.
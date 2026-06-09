package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ants.ThrowerAnt;

/**
 * @author Shah Abdul Qadir , Date 14/04/2026
 * Unit tests verifying the Area of Effect (AoE) mechanics of custom enemy units.
 */
public class ZombieBeeTest {

    @Test
    public void testZombieBeeAoE() {
        // 1. Setup the spatial environment (Two connected tiles)
        Place queenTile = new Place("Queen Chamber", null);
        Place frontTile = new Place("Front Tunnel", queenTile); // Exit points to Queen Chamber

        // 2. Create standard ants (using ThrowerAnt as a dummy target)
        ThrowerAnt target1 = new ThrowerAnt();
        ThrowerAnt target2 = new ThrowerAnt();
        
        int startArmor1 = target1.getArmor();
        int startArmor2 = target2.getArmor();

        // 3. Place insects on the board
        frontTile.addInsect(target1);
        queenTile.addInsect(target2);
        
        ZombieBee zombie = new ZombieBee(3);
        frontTile.addInsect(zombie); // Zombie starts on the front tile

        // 4. Execute the action
        zombie.action(null);

        // 5. Verify both the current tile AND the exit tile took exactly 1 damage
        assertEquals(startArmor1 - 1, target1.getArmor(), "Target 1 (Current Tile) should take 1 damage");
        assertEquals(startArmor2 - 1, target2.getArmor(), "Target 2 (Exit Tile) should take 1 damage from AoE");
                     
        // 6. Verify the ZombieBee STAYED in its current tile because it spent its turn attacking
        assertEquals(frontTile, zombie.getPlace(), "Zombie bee should not move forward in the same turn it attacks");
    }
}
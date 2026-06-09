/* Author: Shah Abdul Qadir 01/04/2026 */
package ants;

import core.Ant;
import core.AntColony;
import core.Bee;
import core.Place;
import core.AntGame; // Added to access the UI bridge
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FireAnt extends Ant {

    public FireAnt() {
        super(1, 4); // 1 armor, 4 food cost
        this.insectName = "Fire";
        descriptor = "\nWhen this Ant dies, it deals 3 damage to all Bees in the same tile." +
        "\nThe bodyguard ant adds has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    @Override
    public void action(AntColony colony) {
        //Paddy 09/04/2026: added action reporting
        setReport("armor = " + this.getArmor() + " action: None");
        // The Fire Ant does not attack normally, so this remains empty.
    }

    @Override
    public void reduceArmor(int amount) {
        //Paddy 09/04/2026: added action reporting
        setReport("Fire Ant Detonates");
        // 1. Save a reference to the current tile BEFORE taking damage
        Place currentPlace = this.getPlace();
        
        // 2. Take the damage using the standard Insect logic
        super.reduceArmor(amount); 
        
        // 3. If the damage killed the Fire Ant, explode!
        if (this.getArmor() <= 0 && currentPlace != null) {
            Bee[] bees = currentPlace.getBees();
            List<Bee> targets = new ArrayList<>(Arrays.asList(bees));
            
            for (Bee bee : targets) {
                if (bee != null) {
                    // Deal 3 damage to every bee on the tile
                    bee.reduceArmor(3);
                }
            }
            
/**
 * @author Shah Abdul Qadir , Date 03/04/2026
 * Trigger the explosion GIF animation for 800ms using the AntGame bridge.
 * Uses explosion.gif from the img folder.
 * * Updated 27/04/2026 to include explosion.wav sound effect.
 */
            if (AntGame.instance != null) {
                AntGame.instance.playTemporaryEffect(currentPlace, "explosion.gif", 800);
                AntGame.instance.playSoundEffect("explosion.wav");
            }
// Shah Abdul Qadir: code change ends here
        }
    }
}
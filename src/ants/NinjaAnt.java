/* Author: Shah Abdul Qadir 01/04/2026 */
package ants;

import core.Ant;
import core.AntColony;
import core.Bee;
import core.AntGame; // Added to access the UI bridge
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NinjaAnt extends Ant {

    public NinjaAnt() {
        super(1, 6); // 1 armor, 6 food cost
        this.insectName = "Ninja";
        descriptor = "\nThis Ant cannot block Bees.\nIt damages all Bees that pass through its tile for 1 damage.\nThe ninja ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    @Override
    public boolean blocksPath() {
        // This is what allows bees to walk right past it!
        return false; 
    }

    @Override
    public void action(AntColony colony) {
        // Grab all the bees currently sharing the tile with the Ninja Ant
        if (this.getPlace() != null) {
            Bee[] bees = this.getPlace().getBees();
            
            // Only trigger the damage and animation if there are actually bees to hit
            if (bees.length > 0) {
                List<Bee> targets = new ArrayList<>(Arrays.asList(bees));
                
                for (Bee bee : targets) {
                    if (bee != null) {
                        // Slice every bee for 1 damage as they walk through
                        bee.reduceArmor(1);
                    }
                }
                
/**
 * @author Shah Abdul Qadir , Date 03/04/2026
 * Trigger the slash GIF animation for 400ms using the AntGame bridge.
 * Uses slash.gif from the img folder.
 * * Updated 27/04/2026 to include slash.wav sound effect.
 */
                if (AntGame.instance != null) {
                    AntGame.instance.playTemporaryEffect(this.getPlace(), "slash.gif", 400);
                    AntGame.instance.playSoundEffect("slash.wav");
                }
// Shah Abdul Qadir: code change ends here
//Paddy 09/04/2026: added action reporting
                setReport("armor = " + this.getArmor() + " action: Slash");
            } else{
                setReport("armor = " + this.getArmor() + " action: None");
            }
        }
    }
}
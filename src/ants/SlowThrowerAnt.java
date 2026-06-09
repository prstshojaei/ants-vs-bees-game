/**
 * @author Parastoo , Date 13/04/2026
 * SlowThrowerAnt: A ThrowerAnt that applies a slow effect to a Bee for 3 turns.
 * A slowed Bee only takes an action every other turn.
 */

package ants;

import core.AntColony;
import core.Bee;

public class SlowThrowerAnt extends ThrowerAnt {

    /**
     * Creates a new SlowThrowerAnt with 1 armor and 4 food cost.
     * Sets the ant name for UI and deployment feedback.
     */
    public SlowThrowerAnt() {
        super();
        this.foodCost = 4;
        this.armor = 1;
        this.insectName = "SlowThrowerAnt";
        descriptor = "\nA Thrower Ant that applies a slow effect to a Bee for 3 turns.\nA slowed Bee only takes an action every other turn.\nThe slow thrower ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    /*
     * Applies a slow effect to the target Bee for 3 turns.
     * This ant does not deal normal damage; it only applies the slow status effect.
     */
    @Override
    public void action(AntColony colony) {
        Bee target = getTarget();

        if (target != null) {
            target.applySlow(3);
            setReport("armor = " + armor + " action: Applied Slow");
        } else {
            setReport("armor = " + armor + " action: None");
        }
    }
}

// Parastoo: code change ends here.
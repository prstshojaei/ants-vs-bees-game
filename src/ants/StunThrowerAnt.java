/**
 * @author Parastoo , Date 13/04/2026
 * StunThrowerAnt: A ThrowerAnt that applies a stun effect to a Bee for 1 turn.
 * A stunned Bee takes no action during that turn.
 */

package ants;

import core.AntColony;
import core.Bee;

public class StunThrowerAnt extends ThrowerAnt {

    /*
     * Creates a new StunThrowerAnt with 1 armor and 6 food cost.
     * Sets the ant name for UI and deployment feedback.
     */
    public StunThrowerAnt() {
        super();
        this.foodCost = 6;
        this.armor = 1;
        this.insectName = "StunThrowerAnt";
        descriptor = "\nA Thrower Ant that applies a stun effect to a Bee for 1 turn.\nA stunned Bee takes no action during that turn.\nThe stun thrower ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    /*
     * Applies a stun effect to the target Bee for 1 turn.
     * This ant does not deal normal damage; it only applies the stun status effect.
     */
    @Override
    public void action(AntColony colony) {
        Bee target = getTarget();

        if (target != null) {
            target.applyStun(1);
            setReport("armor = " + armor + " action: Applied Stun");
        } else {
            setReport("armor = " + armor + " action: None");
        }
    }
}

//Parastoo: code change ends here.
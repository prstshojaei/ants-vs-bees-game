/**
 * @author Parastoo , Date 13/04/2026
 * LongThrowerAnt: A ThrowerAnt that only throws leaves at Bees
 * at least 4 places away.
 */

package ants;

import core.Bee;

public class LongThrowerAnt extends ThrowerAnt {

    /*
     * Creates a new LongThrowerAnt with 1 armor and 3 food cost.
     * Sets the ant name for UI and deployment feedback.
     */
    public LongThrowerAnt() {
        super();
        this.foodCost = 3;
        this.armor = 1;
        this.insectName = "LongThrowerAnt";
        descriptor = "\nA Thrower Ant that can only target Bees at least 4 places away.\nThe long thrower ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    /*
     * Returns a target Bee only if it is at least 4 places away.
     * Valid distances are 4 and above.
     * 
     * @return a Bee within long range
     */
    @Override
    public Bee getTarget() {
        return place.getClosestBee(4, Integer.MAX_VALUE);
    }
}

// Parastoo: code change ends here.
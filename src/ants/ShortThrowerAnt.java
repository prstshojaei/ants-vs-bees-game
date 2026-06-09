/**
 * @author Parastoo , Date 13/04/2026
 * ShortThrowerAnt: A ThrowerAnt that only throws leaves at Bees
 * at most 2 places away.
 */

package ants;

import core.Bee;

public class ShortThrowerAnt extends ThrowerAnt {

    /**
     * @author Parastoo , Date 13/04/2026
     * Creates a new ShortThrowerAnt with 1 armor and 3 food cost.
     * Sets the ant name for UI and deployment feedback.
     */
    public ShortThrowerAnt() {
        super();
        this.foodCost = 3;
        this.armor = 1;
        this.insectName = "ShortThrowerAnt";
        descriptor = "\nA Thrower Ant that can only target Bees at most 2 places away.\nThe short thrower ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    /**
     * @author Parastoo , Date 13/04/2026
     * Returns a target Bee only if it is at most 2 places away.
     * Valid distances are 0, 1, and 2.
     * @return a nearby Bee within short range
     */
    @Override
    public Bee getTarget() {
        return place.getClosestBee(0, 2);
    }
}

//Parastoo: code change ends here.
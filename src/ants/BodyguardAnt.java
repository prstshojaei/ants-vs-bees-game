/**
 * @author Parastoo , Date 13/04/2026
 * BodyguardAnt: An Ant that can contain another Ant and protect it.
 * It takes damage first before the contained Ant.
 */

package ants;

import core.Ant;
import core.AntColony;
import core.Place;

public class BodyguardAnt extends Ant {

    private Ant containedAnt; // The ant protected by the BodyguardAnt

    /*
     * Creates a new BodyguardAnt with 2 armor and 4 food cost.
     * Sets the ant name for UI and deployment feedback.
     */
    public BodyguardAnt() {
        super(2, 4);
        this.insectName = "BodyguardAnt";
        this.containedAnt = null;
        descriptor = "\nProtects another Ant from taking damage by absorbing hits.\nIt can be placed on top of any ant to increase its armor.\n" + 
        "The bodyguard ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    /*
     * This ant CAN contain another ant.
     * @return true because BodyguardAnt can protect another Ant
     */
    @Override
    public boolean canContainAnt() {
        return true;
    }

    /*
     * Stores another Ant inside this BodyguardAnt.
     * @param ant the Ant to protect
     */
    @Override
    public void containAnt(Ant ant) {
        this.containedAnt = ant;
    }

    /*
     * Returns the Ant currently protected by this BodyguardAnt.
     * @return the contained Ant
     */
    @Override
    public Ant getContainedAnt() {
        return containedAnt;
    }

    /*
     * Reduces the armor of the BodyguardAnt.
     * If the BodyguardAnt perishes, the contained Ant remains in the same Place.
     * This method stores the Place reference before removal so the contained Ant
     * can be restored correctly after the BodyguardAnt dies.
     * @param amount the amount of damage taken
     */
    @Override
    public void reduceArmor(int amount) {
        Place currentPlace = this.place; // Store place before BodyguardAnt is removed
        super.reduceArmor(amount);

        if (this.armor <= 0 && containedAnt != null && currentPlace != null) {
            currentPlace.addInsect(containedAnt);
        }
    }

    /*
     * BodyguardAnt does not attack directly.
     * If it contains another Ant, that Ant still performs its action.
     */
    @Override
    public void action(AntColony colony) {
        if (containedAnt != null) {
            containedAnt.action(colony);
            setReport("armor = " + armor + " action: Protected ant acted");
        } else {
            setReport("armor = " + armor + " action: None");
        }
    }
}

//Parastoo: code change ends here.
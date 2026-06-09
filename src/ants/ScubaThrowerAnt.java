package ants;

/**
 * @author Paddy 03/04/2026
 * @version 0.1
 * Implements the Scuba Ant with water safe and a higher food cost.
 */
public class ScubaThrowerAnt extends ThrowerAnt {

    public ScubaThrowerAnt(){
        super();
        foodCost = 5;
        armor = 1;
        this.insectName = "Scuba Ant";
        setIsWaterSafe(true);
        descriptor = "\nA Thrower Ant that is water safe and can be deployed on water tiles.\nIt has a range of 3.\nThe scuba thrower ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

}

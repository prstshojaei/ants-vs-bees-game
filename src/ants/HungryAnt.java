/**
 * @author Parastoo, Date 25/04/2026
 * This class represents the behavior of a HungryAnt.
 * The HungryAnt is an offensive type of ant that can eat one Bee at a time
 * and then enters a digestion period before it can eat again.
 * The digestion period is 3 turns long.
 * The ant starts with 4 armor and costs 4 food to deploy.
 * After eating, the ant enters a digestion state for 3 turns before it can eat again.
 * Note: AntGame UI interaction is done via reflection to avoid JavaFX dependency during unit testing.
 */
package ants;

import core.Ant;
import core.AntColony;
import core.Bee;


public class HungryAnt extends Ant {

    private int digestTimeLeft; // Tracks how many turns remain before the ant can eat again

    /**
     * Creates a HungryAnt with 1 armor and 4 food cost.
     * Sets the ant name for UI and game feedback.
     * Digestion timer starts at 0 meaning the ant is ready to eat immediately.
     */
    public HungryAnt() {
        super(1, 4); // HungryAnt has 1 armor and costs 4 food
        this.insectName = "Hungry Ant";
        this.insectName = "Hungry Ant";
        this.digestTimeLeft = 0; // Initially ready to eat
        descriptor = "\nEats the first Bee to endter its tile, then digests for 3 turns.\nDuring digestion it is defenseless and cannot eat again.\nThe hungry ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    /**
     * Eats a Bee instantly by removing it from the current place.
     * UI removal is handled via reflection so that JavaFX is not required during
     * unit testing.
     * After eating, starts a 3-turn digestion period.
     *
     * @param bee the Bee to be eaten; must not be null
     */
    public void eatBee(Bee bee) {
        if (bee != null) {
            bee.leavePlace(); // Remove the bee from the board

            /**
             * @author Parastoo , Date 05/05/2026
             *         Adds kill score when HungryAnt eats a bee.
             */
            core.ScoreManager.addKill(bee);
            // Parastoo: code change ends here.

            // Use reflection to call AntGame.removeBeeInstantly() only if AntGame is
            // available (i.e. not in test environment)
            try {
                Class<?> gameClass = Class.forName("core.AntGame");
                java.lang.reflect.Field instanceField = gameClass.getField("instance");
                Object gameInstance = instanceField.get(null);
                if (gameInstance != null) {
                    java.lang.reflect.Method method = gameClass.getMethod("removeBeeInstantly", Bee.class);
                    method.invoke(gameInstance, bee);
                }
            } catch (Exception e) {
                // AntGame is not available in test environment - safely ignored
            }

            this.digestTimeLeft = 3; // Start digesting for 3 turns
        }
    }

    /**
     * Returns the remaining number of digestion turns.
     *
     * @return number of turns left before HungryAnt can eat again
     */
    public int getDigestTimeLeft() {
        return digestTimeLeft;
    }

    /**
     * HungryAnt action executed each turn:
     * - If currently digesting, reduces digestion timer by 1.
     * - Otherwise, eats the first Bee found in the current place if one exists.
     *
     * @param colony the AntColony (not used directly but required by interface)
     */
    @Override
    public void action(AntColony colony) {
        if (digestTimeLeft > 0) {
            digestTimeLeft--; // Continue digesting
            setReport("armor = " + armor + " action: Digesting");
        } else {
            if (this.place != null) {
                Bee[] bees = this.place.getBees();

                if (bees.length > 0) {
                    eatBee(bees[0]); // Eat the first bee in the current place
                    setReport("armor = " + armor + " action: Ate Bee");
                } else {
                    setReport("armor = " + armor + " action: None"); // No bee to eat
                }
            } else {
                setReport("armor = " + armor + " action: None"); // No place assigned
            }
        }
    }
}

// Parastoo: code change ends here.
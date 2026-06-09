/**
 * @author Parastoo , Date 05/04/2026
 * WallAnt: A defensive ant with high armor but does nothing each turn.
 * This class represents a defensive ant with a high armor value. It costs 4 food to deploy
 * and has an armor value of 4. The ant does not perform any actions during the game.
 */

package ants;

import core.Ant;
import core.AntColony;


public class WallAnt extends Ant {

    /**
     * Creates a new Wall Ant with high armor and a food cost.
     * Armor: 4, Food: 4
     * The constructor initializes the WallAnt with specific armor and food cost values.
     */
    public WallAnt() {
        super(4, 4); // WallAnt has 4 armor and costs 4 food to deploy
        this.insectName = "Wall Ant"; // Set the name of this ant as "Wall Ant"
        descriptor = "\nA defensive ant with high armor but does nothing each turn.\nThe wall ant has " + armor + " armor and costs " + foodCost + " food.";
    }

    /**
     * Action: WallAnt does nothing each turn.
     * The action method is overridden here, but no operation is performed. 
     * This method does not affect the colony or the game state.
     */
    @Override
    public void action(AntColony colony) {
        // WallAnt does nothing each turn
        //Paddy 09/04/2026: added action reporting
        setReport("armor = " + armor + " action: None");
    }
}

//Parastoo: code change ends here.
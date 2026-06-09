package ants;

import core.Ant;
import core.AntColony;

/**
 * An Ant that harvests food
 * @author YOUR NAME HERE
 */
public class HarvesterAnt extends Ant
{
	/**
	 * Creates a new Harvester Ant
	 */
	public HarvesterAnt()
	{
		//Paddy 27/03/2026: added food cost and ant name.
		super(1,2);
		this.insectName = "Harvester Ant";
		descriptor = "\nThis Ant harvests 1 food per turn for the colony. \nThe harvester ant has " + armor + " armor and costs " + foodCost + " food.";
		setReport("Armor = " + armor + " Action Taken: None");
	}

	public void action(AntColony colony)
	{
		
		/**
		 * @author Paddy , Date 26/03/2026	
		 * increases food by 1 each turn.
		*/
		colony.increaseFood(1);
		//Paddy 09/04/2026: added action reporting
		setReport("armor = " + armor + " action taken: food +1");
		//Paddy : code change ends here.

	}	
}

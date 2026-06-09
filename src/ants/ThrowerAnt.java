package ants;
import core.Ant;
import core.AntColony;
import core.Bee;

/**
 * An ant who throws leaves at bees
 * @author YOUR NAME HERE
 */
public class ThrowerAnt extends Ant
{
	
	/**
	 * Creates a new Thrower Ant.
	 * Armor: 1, Food: 0, Damage: 1
	 */
	public ThrowerAnt()
	{
		//Paddy 27/03/2026: added food cost and ant name.
		super(1, 4); 
		this.damage = 1;
		this.insectName = "Thrower Ant";
		descriptor = "\nA Thrower Ant that throws leaves at Bees up to 3 places away.\nThe thrower ant has " + armor + " armor and costs " + foodCost + " food.";
		setReport("Armor = " + armor + " Action Taken: None");
	}
	
	/**
	 * Returns a target for this ant
	 * @return A bee to target
	 */
	public Bee getTarget()
	{
		return place.getClosestBee(0,3);
	}
	
	public void action(AntColony colony)
	{
		Bee target = getTarget();
		if(target != null)
		{
			target.reduceArmor(this.damage);
			//Paddy 09/04/2026: added action reporting
			setReport("armor = " + armor + " action:threw " + "damage:" + getDamage());
		}else{
			setReport("armor = " + armor + " action: None");
		}
	}
}

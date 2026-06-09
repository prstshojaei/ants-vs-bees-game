package core;

/**
 * Represents a Bee
 * @author YOUR NAME HERE
 */
public class Bee extends Insect
{
	private static final int DAMAGE = 1;
	/**
	 * @author Parastoo , Date 11/04/2026
	 * Tracks the remaining number of turns for the slow effect
	 * and whether the Bee should skip its current turn.
	 */
	private int slowTurnsLeft = 0;
	private boolean skipSlowTurn = true;
	//Parastoo: code change ends here.

	/**
	 * @author Parastoo , Date 13/04/2026
	 * Tracks the remaining number of turns for the stun effect.
	 * A stunned Bee skips its action completely.
	 */
	private int stunTurnsLeft = 0;
	//Parastoo: code change ends here.


	/**
	 * Creates a new bee with the given armor
	 * @param armor The bee's armor
	 */
	public Bee(int armor)
	{
		super(armor);
		setIsWaterSafe(true); //Paddy 01/04/2026: Bees fly so all are considered water safe.
		insectName = "Bee";
		setReport("Armor = " + armor + " Action Taken: None");
	}
	
	/**
	 * Deals damage to the given ant
	 * @param ant The ant to sting
	 */
	public void sting(Ant ant)
	{
		ant.reduceArmor(DAMAGE);
		//Paddy 09/04/2026: added action reporting
		setReport("armor = " + armor + " action: Stung Ant");
	}
	
	/**
	 * Moves to the given place
	 * @param place The place to move to
	 */
	public void moveTo(Place place)
	{
		this.place.removeInsect(this);
		place.addInsect(this);
		//Paddy 09/04/2026: added action reporting
		setReport("armor = " + armor + " action taken: Moved 1");
	}

	public void leavePlace()
	{
		this.place.removeInsect(this);
	}
	
	/**
	 * @author Parastoo , Date 11/04/2026
	 * Applies a slow effect to this Bee for the given number of turns.
	 * While slowed, the Bee only acts every other turn.
	 * @param turns the number of turns the slow effect lasts
	 */
	public void applySlow(int turns)
	{
		if(turns > slowTurnsLeft)
			slowTurnsLeft = turns;
	}
	//Parastoo: code change ends here.

	/**
	 * @author Parastoo , Date 13/04/2026
	 * Applies a stun effect to this Bee for the given number of turns.
	 * While stunned, the Bee takes no action.
	 * @param turns the number of turns the stun effect lasts
	 */
	public void applyStun(int turns)
	{
		if(turns > stunTurnsLeft)
			stunTurnsLeft = turns;
	}
	//Parastoo: code change ends here.


	/** Commented out by Shah Abdul Qadir 01/04/2026
	 * Returns true if the bee cannot advance (because an ant is in the way)
	 * @return if the bee can advance
	 */
		// public boolean isBlocked()
		// {
		// 	return this.place.getAnt() != null;
		// }

	/* Author: Shah Abdul Qadir 01/04/2026:
        //Updated isBlocked, Now it checks if there is an ant AND if that specific ant blocks the path
	 */
	public boolean isBlocked()
    {
        return this.place.getAnt() != null && this.place.getAnt().blocksPath();
    }
	// Shah Abdul Qadir: code change ends here.


	/**
	 * @author Parastoo , Date 05/05/2026
	 *         Overrides reduceArmor to track score when a bee is killed.
	 *         When armor reaches zero, adds kill points to ScoreManager.
	 */
	@Override
	public void reduceArmor(int amount) {
		this.armor -= amount;
		if (this.armor <= 0) {
			ScoreManager.addKill(this);
			System.out.println(this + " ran out of armor and expired");
			leavePlace();
		}
	}
	// Parastoo: code change ends here.

	/**
	 * @author Parastoo , Date 11/04/2026
	 * Original Bee action method kept for reference.
	 * Commented out by Parastoo , Date 11/04/2026
	 * Replaced to support the slow effect behaviour.
	 */
	// public void action(AntColony colony)
	// {
	// 	if(this.isBlocked())
	// 		sting(this.place.getAnt());
	// 	else if(this.armor > 0)
	// 		this.moveTo(this.place.getExit());
	// }
	//Parastoo: original action method commented out here.

	/**
	 * @author Parastoo , Date 13/04/2026
	 * Previous slow-only Bee action method kept for reference.
	 * Commented out by Parastoo , Date 13/04/2026
	 * Replaced to support both stun and slow effects.
	 */
	// public void action(AntColony colony)
	// {
	// 	/*
	// 	 * If the Bee is slowed, it skips every other turn.
	// 	 * The first slowed turn is skipped immediately after the effect is applied.
	// 	 */
	// 	if(slowTurnsLeft > 0)
	// 	{
	// 		slowTurnsLeft--;
	//
	// 		if(skipSlowTurn)
	// 		{
	// 			skipSlowTurn = false;
	// 			setReport("armor = " + armor + " action: Slowed - skipped turn");
	// 			return;
	// 		}
	// 		else
	// 		{
	// 			skipSlowTurn = true;
	// 		}
	// 	}
	// 	
	// 	if(this.isBlocked())
	// 		sting(this.place.getAnt());
	// 	else if(this.armor > 0)
	// 		this.moveTo(this.place.getExit());
	// }
	//Parastoo: previous slow-only action method commented out here.

	/**
	 * A bee's action is to sting the Ant that blocks its exit if it is blocked,
	 * otherwise it moves to the exit of its current place.
	 * @author Parastoo , Date 13/04/2026
	 * Updated to support both stun and slow effects.
	 * A stunned Bee takes no action.
	 * A slowed Bee only acts every other turn.
	 */



	 public void action(AntColony colony)
	{
		/*
		 * Stun takes priority over all other effects.
		 * If the Bee is stunned, it skips this turn completely.
		 */
		if(stunTurnsLeft > 0)
		{
			stunTurnsLeft--;
			setReport("armor = " + armor + " action: Stunned");
			return;
		}

		/*
		 * If the Bee is slowed, it skips every other turn.
		 * The first slowed turn is skipped immediately after the effect is applied.
		 */
		if(slowTurnsLeft > 0)
		{
			slowTurnsLeft--;

			if(skipSlowTurn)
			{
				skipSlowTurn = false;
				setReport("armor = " + armor + " action: Slowed - skipped turn");
				return;
			}
			else
			{
				skipSlowTurn = true;
			}
		}
		
		if(this.isBlocked())
			sting(this.place.getAnt());
		else if(this.armor > 0)
			this.moveTo(this.place.getExit());
	}
	//Parastoo: code change ends here.

	public static String getBeeDescriptor(){
		return "Bee:\nThis is a soldier bee.\n it moves at 1 tile per turn & does 1 damage per turn";
	}
}


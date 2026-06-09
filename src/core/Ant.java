package core;

/**
 * A class representing a basic Ant
 * 
 * @author YOUR NAME HERE
 */
public abstract class Ant extends Insect
{
	protected int foodCost; //the amount of food needed to make this ant
	protected int damage = 0; // paddy 10/04/2026: added a universal damage number for all ants.
	protected boolean isBuffed = false; // paddy 10/04/2026: added a way to check if an ant has been buffed.

	/**
	 * Creates a new Ant, with a food cost of 0.
	 * @param armor The armor of the ant.
	 */
	public Ant(int armor)
	{
		super(armor, null);
		this.foodCost = 0;
	}

	/**
	 * @author: Paddy 27/03/2026: added a new constructor to cover food cost creation.
	 * @param armor
	 * @param foodCost
	*/
	public Ant(int armor, int foodCost){
		super(armor, null);
		this.foodCost = foodCost;
	}

	/**
	 * Returns the ant's food cost
	 * @return the ant's good cost
	 */
	public int getFoodCost()
	{
		return foodCost;
	}

	/**
	 * Removes the ant from its current place
	 */
	public void leavePlace()
	{
		this.place.removeInsect(this);
	}	
    
        /** 
	
	 * Author: Shah Abdul Qadir 01/04/2026
     * Determines if this ant blocks bees from moving.
     * @return true by default.
     */
    public boolean blocksPath() {
        return true; 
    }
	// Shah Abdul Qadir: code change ends here.


		/**
	 * @author Parastoo , Date 13/04/2026
	 * Returns whether this Ant can contain another Ant.
	 * Normal ants cannot contain other ants.
	 * @return false by default
	 */
	public boolean canContainAnt() {
		return false;
	}

	/*
	 * Returns whether this Ant can be contained by another Ant.
	 * Normal ants can be contained by default.
	 * @return true by default
	 */
	public boolean canBeContained() {
		return true;
	}

	/*
	 * Stores another Ant inside this Ant.
	 * Default ants cannot contain another ant, so this has no effect here.
	 * Subclasses such as BodyguardAnt will override this method.
	 * @param ant the Ant to contain
	 */
	public void containAnt(Ant ant) {
		// Default ants cannot contain another ant
	}

	/*
	 * Returns the Ant contained inside this Ant, if any.
	 * Default ants do not contain another ant.
	 * @return null by default
	 */
	public Ant getContainedAnt() {
		return null;
	}
	//Parastoo: code change ends here.

	/**
	 * @author Paddy 10/04/2026
	 * @param damage - updates damage for ants who deal direct damage.
	 */
	public void setDamage(int damage){
		this.damage = damage;
	}
	/**
	 * @author Paddy 10/04/2026
	 * @return damage - return damage int
	 */
	public int getDamage(){
		return damage;
	}
	/**
	 * @author Paddy 10/04/2026
	 * @return the opposite of is buffed to check if a soldier is not buffed.
	 */
	public boolean isNotBuffed(){
		return !isBuffed;
	}
	/**
	 * @author Paddy 10/04/2026
	 * @param buff - updates buff so it can be checked
	 */
	public void setIsBuffed(boolean buff){
		isBuffed = buff;
	}
	//paddy 10/04/2026 edit ends here.
}
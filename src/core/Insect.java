package core;

/**
 * Represents an insect (e.g., an Ant or a Bee) in the game
 * @author Joel
 * @version Fall 2014
 */
public abstract class Insect
{
	protected int armor; //insect's current armor
	protected Place place; //insect's current location
	protected boolean isWaterSafe = false; // paddy 01/04/2026: check to see if an insect can occupy a space. false by default
	protected String report; // Paddy 09/04/2026 - used to report on insect actions adn status each turn.
	protected String insectName = "insect"; //Paddy 27/03/2026: added names for identifying ants and printing strings
	protected String descriptor = "This insect crawls"; //Paddy 17/04/2026: added a descriptor string for user feedback when mouse over is triggered
	
	/**
	 * Creates a new Insect with the given armor in the given location
	 * @param armor The insect's armor
	 * @param place The insect's location
	 */
	public Insect(int armor, Place place)
	{
		if(armor <= 0)
			throw new IllegalArgumentException("Cannot create an insect with armor of 0");
		this.armor = armor;
		this.place = place;
		//pady 09/04/2026: added action reporting

	}
	
	/**
	 * Creates an Insect with the given armor. The insect's location is null
	 * @param armor The insect's armor
	 */
	public Insect(int armor)
	{
		this(armor, null);
	}
		
	/**
	 * Set's the insect's current location
	 * @param place The insect's current location
	 */
	public void setPlace(Place place)
	{
		this.place = place;
	}
	
	/**
	 * Return's the insect's current location
	 * @return the insect's current location
	 */
	public Place getPlace()
	{
		return this.place;
	}

	/**
	 * Returns the insect's current armor
	 * @return the insect's current armor
	 */
	public int getArmor()
	{
		return this.armor;
	}
	
	/**
	 * Reduces the insect's current armor (e.g., through damage)
	 * @param amount The amount to decrease the armor by
	 */
	public void reduceArmor(int amount)
	{
		this.armor -= amount;
		if(this.armor <= 0)
		{
			System.out.println(this+" ran out of armor and expired");
			leavePlace();
		}
	}
	
	/**
	 * Has the insect move out of its current location. Abstract in case the insect takes action when it leaves
	 */
	public abstract void leavePlace();

	/**
	 * The insect takes an action on its turn
	 * @param colony The colony in which this action takes place (to support wide-spread effects)
	 */
	public abstract void action(AntColony colony);
	
	@Override
	public String toString()
	{
		return this.getClass().getName()+"["+armor+", "+place+"]"; //supports inheritance!
		//return this.getClass().getName()+"["+armor+", "+place+"]"; //supports inheritance!
	}

	/**
	 * @author paddy 01/04/2026
	 * @return isWaterSafe to check is an insect can ocupy a water tile.
	 */
	public boolean getIsWaterSafe(){
		return isWaterSafe;
	}
	/**
	 * @author paddy 01/04/2026
	 * @param isSafe - set isWaterSafe to true or fals
	 */
	public void setIsWaterSafe(boolean isSafe){
		isWaterSafe = isSafe;
	}
	//paddy 01/04/2026 edit ends here

	/**
	 * @author Paddy 09/04/2026: added a report string to each insect so that each turn then can update and report actions to the player.
	 * @param report - will be used to report to an in game turn terminal in the ant game.
	 */
	public void setReport(String report){
		this.report = insectName + " in " + place  +" "+ report;
	}
	public String getReport(){
		return report;
	}
	//Paddy 09/04/2026: edit ends here

	/**
	 * //Paddy 27/03/2026: added to retriev ant names.
	 */
	public String getInsectName(){
		return insectName;
	}

	/**
	 * @author paddy 17/04/2026: returns an insects descriptor which will be used for player feedback
	 * on insect purpose in the game.
	 * @return
	 */
	public String getDescriptor(){
		return getInsectName() + ": " + descriptor;
	}
}

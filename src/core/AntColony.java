package core;

import java.util.Random; // Paddy 01/04/2026
import ants.QueenAnt;//paddy 10/04/2026
import java.util.ArrayList;

/**
 * An entire colony of ants and their tunnels.
 * @author Joel
 * @version Fall 2014
 */
public class AntColony
{
	public static final String QUEEN_NAME = "AntQueen"; //name of the Queen's place
	public static final int MAX_TUNNEL_LENGTH = 10;
	
	private int food; //amount of food available
	private Place queenPlace; //where the queen is
	private Place colonyBase; // if base has bees we lose.
	private ArrayList<Place> places; //the places in the colony
	private ArrayList<Place> beeEntrances; //places which bees can enter (the starts of the tunnels)
	public final QueenAnt queenAnt = QueenAnt.getInstance(); //paddy 10/04/2026 creates the singleton queen instance.
	private boolean isQueenDeployed = false;

	// private int firstTunnelWater;
	// private int secondTunnelWater;
	// private int thirdTunnelWater;
	
	/**
	 * Creates a new ant colony with the given layout.
	 * @param numTunnels The number of tunnels (paths)
	 * @param tunnelLength The length of each tunnel
	 * @param moatFrequency The frequency of which moats (water areas) appear. 0 means that there are no moats
	 * @param startingFood The starting food for this colony.
	 */
	public AntColony(int numTunnels, int tunnelLength, int moatFrequency, int startingFood, long seed, int level)
	{
		//simulation values
		this.food = startingFood;		
		Random rand = new Random(seed);
		//init variables
		places = new ArrayList<Place>();
		beeEntrances = new ArrayList<Place>();
		queenPlace = new Place(QUEEN_NAME); //magic variable name
		colonyBase = queenPlace; //magic variable name

		tunnelLength = Math.min(tunnelLength, MAX_TUNNEL_LENGTH); //don't go off the screen!
		//set up tunnels, as a kind of linked-list
		Place curr, prev; //reference to current exit of the tunnelEnd
	
		for(int tunnel=0; tunnel<numTunnels; tunnel++)
		{
			curr = queenPlace; //start the tunnel's at the queen;
			for(int step=0; step<tunnelLength; step++)
			{
				prev = curr; //keep track of the previous guy (who we will exit to)

				curr = new Place("tunnel["+tunnel+"-"+step+"]", prev); //create new place with an exit that is the previous spot
				if(rand.nextInt(100) < (10*level)) {
    				curr.setIsWater(true);
				}
				prev.setEntrance(curr); //the previous person's entrance is the new spot
				places.add(curr); //add new place to the list
			}
			beeEntrances.add(curr); //current place is last item in the tunnel, so mark that it is a bee entrance
		} //loop to next tunnel
		
	}
	

	/**
	 * Returns an array of Places in this colony. Places are ordered by tunnel, with each tunnel's places listed start to end.
	 * @return The tunnels in this colony
	 */
	public Place[] getPlaces()
	{
		return places.toArray(new Place[0]);
	}
	
	/**
	 * Returns an array of places that the bees can enter into the colony
	 * @return Places the bees can enter
	 */
	public Place[] getBeeEntrances()
	{
		return beeEntrances.toArray(new Place[0]);
	}

	/**
	 * Returns the queen's location
	 * @return The queen's location
	 */
	public Place getQueenPlace()
	{
		return queenPlace;
	}
	
	/**
	 * Returns the amount of available food
	 * @return the amount of available food
	 */
	public int getFood()
	{
		return food;
	}
	
	/**
	 * Increases the amount of available food
	 * @param amount The amount to increase by
	 */
	public void increaseFood(int amount)
	{
		food += amount;
	}
	
	/**
	 * Returns if there are any bees in the queen's location (and so the game should be lost)
	 * @return if there are any bees in the queen's location
	 */
	public boolean queenHasBees()
	{
		boolean checkQueenBase = false;
		if (this.queenPlace.getBees().length  > 0 || this.colonyBase.getBees().length > 0) {
			checkQueenBase = true;	
		}
		return checkQueenBase;
	}
	
	//place an ant if there is enough food available
	/**
	 * Places the given ant in the given tunnel IF there is enough available food. Otherwise has no effect
	 * @param place Where to place the ant
	 * @param ant The ant to place
	 * Paddy 27/03/2026: change to return a string for UI feedback.
	 * paddy 01/04/2026: this now checks if the place is water and if it is it prevent ant deployment
	 * paddy 10/04/2026: added a check for deploying the queen.
	 */
	public String deployAnt(Place place, Ant ant) 
	{
		if(place.getIsWater() && !ant.getIsWaterSafe()){
			return "This ant cannot deploy here as it cannot swim.";
		}else if(this.food >= ant.getFoodCost())
		{
			if(ant.getInsectName().equals("QueenAnt") && !isQueenDeployed){
				this.food -= ant.getFoodCost();
				place.addInsect(ant);
				queenPlace = place;
				isQueenDeployed = true;
				return ant.getInsectName() +" Successfully Deployed."; 
			} else if(ant.getInsectName().equals("QueenAnt") && isQueenDeployed){
				return "There Can Be Only One Queen!";
			}
			/**
			 * @author Parastoo , Date 25/04/2026
			 *         Original non-queen deployment kept for reference.
			 *         Commented out by Parastoo , Date 25/04/2026
			 *         Logic is now handled in handleClick with BodyguardAnt support.
			 */
			// if (!ant.getAntName().equals("QueenAnt")){
			// this.food -= ant.getFoodCost();
			// place.addInsect(ant);
			// return ant.getAntName() +" Successfully Deployed.";
			// }
			// Parastoo: original deployment commented out here.
			/**
			 * @author Parastoo , Date 25/04/2026
			 *         Updated to support BodyguardAnt shared occupancy rules.
			 */
			if (!ant.getInsectName().equals("QueenAnt")) {
				this.food -= ant.getFoodCost();
				place.addInsect(ant);
				return ant.getInsectName() + " Successfully Deployed.";

			}
			// Parastoo: code change ends here.
		}else{
			System.out.println("Not enough food remains to place "+ ant);
			return "Your Colony Does Not Sufficient Food For " + ant.getInsectName() + ", You Need " + ant.getFoodCost() + " Food.";
		}
		return "Error in deployment";
	}

	/**
	 * Removes the ant inhabiting the given Place
	 * @param place Where to remove the ant from
	 * paddy 10/04/2026: added a check for not removing the queen
	 */
	public String removeAnt(Place place)
	{
		if(place.getAnt() != null && place.getAnt().getInsectName().equals("QueenAnt")){
			return "You Cannot Remove The Queen";
		}else if(place.getAnt() != null){
			place.removeInsect(place.getAnt());
			return "Ant successfully removed";
		}	
		return "error in ant removal";
	}
	
	/**
	 * Returns a list of all the ants currently in the colony
	 * @return a list of all the ants currently in the colony
	 */
	public ArrayList<Ant> getAllAnts()
	{
		ArrayList<Ant> ants = new ArrayList<Ant>();
		for(Place p : places)
		{
			if(p.getAnt() != null)
				ants.add(p.getAnt());
		}
		return ants;
	}
	
	/**
	 * Returns a list of all the bees currently in the colony
	 * @return a list of all the bees currently in the colony
	 */
	public ArrayList<Bee> getAllBees()
	{
		ArrayList<Bee> bees = new ArrayList<Bee>();
		for(Place p : places)
		{
			for(Bee b : p.getBees())
				bees.add(b);
		}
		return bees;
	}
	
	public String toString()
	{
		return "Food: "+this.food+"; "+getAllBees() + "; "+getAllAnts();
	}	



	/**
	 * @author Paddy 10/04/2026
	 * @return - returns the singleton queen instance.
	 */
	public QueenAnt getQueenAnt(){
		return queenAnt;
	}
}

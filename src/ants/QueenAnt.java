package ants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Ant;
import core.AntColony;
import core.Bee;
import core.Place;

public class QueenAnt extends ScubaThrowerAnt {
    /**
     * @author Paddy 11/05/2026
     * This constructor is set to private so it can only be called by the QuennAntSingletonHelper method to 
     * return only one instance of QueenAnt once initialized.
     * it sets all the standar ant variables.
     */
    //Bill Pugh Singleton method
    //QueenAnt queenAnt = QueenAnt.getInstance();
    private QueenAnt() {
        super();
        armor =1;
        foodCost = 6;
        damage = 1;
        insectName = "QueenAnt";
        setIsWaterSafe(true);
        System.out.println("Singleton instance created!");
        descriptor = "\nThe Queen Ant is the most important Ant in the colony.\nIf she dies, it's game over!\nThe Queen Ant cannot be moved once placed.\nThere can only be one true queen ant.\nThe queen ant has " + armor + " armor and costs " + foodCost + " food.";
        setReport("Armor = " + armor + " Action Taken: None");
    }
    /**
     * @author Paddy 11/05/2026
     * initialises a final static instance of the class QueenAnt initialises a final static instance of the class QueenAnt 
     * contained in this inner class and re-refers to it if it is called again. 
     */
    private static class QueenAntSingletonHelper {
        private static final QueenAnt INSTANCE = new QueenAnt();
    }
    /**
     * @auther Paddy 11/05/2026
     * @return an instance of QueenAnt created by the QueenAntSingletonHelper method.
     * since the method refers to an instance, once created the same instance is return with each access ensuring only one Queen.
     */
    public static QueenAnt getInstance() {
        return QueenAntSingletonHelper.INSTANCE;  // Triggers QuennAntSingletonHelper class load
    }

    /**
	 * @author Paddy 10/04/2026
	 * an actio overide that allows the queen to throw a leafe while also looking for ajacent ants to buff.
	 */
    @Override
    public void action(AntColony colony)
        {
            
            Bee target = getTarget();
            System.out.println("tried to shoot target = "+target);
            if(target != null)
            {
                System.out.println("shot");
                target.reduceArmor(this.damage);
                //Paddy 09/04/2026: added action reporting
                setReport("armor = " + armor + " action: threw leaf & buffed ants");
                System.out.println("this is my queens place = " + place.toString());
                int[] queenCoordinates = placeMatcher(place.toString());
                for (Place antPlace : colony.getPlaces()) {                 
                    if(queenCoordinates[1] == placeMatcher(antPlace.toString())[1]
                    && antPlace.getAnt() != null 
                    && !antPlace.getAnt().getInsectName().equals("QueenAnt") 
                    && antPlace.getAnt().isNotBuffed()){
                        System.out.println("place matercher =" + placeMatcher(antPlace.toString())[0] + placeMatcher(antPlace.toString())[1] );
                        Ant ant = antPlace.getAnt();
                        ant.setDamage(ant.getDamage()*2);
                        ant.setIsBuffed(true);
                        System.out.println("damage buffed for " + ant.toString());
                    }
                }
            }else{
                setReport("armor = " + armor + " action: None");
            }
            
        }
        
        /**
	     * @author Paddy 10/04/2026
         * @param placeName - allows the method to look at a place name string and break it down for its coordinates
         * @return - returns the tunnel and depth coordinate from the place name.
         */
        public int[] placeMatcher(String placeName){
            int[] returnString = new int[2];
            Pattern pattern = Pattern.compile("tunnel\\[(\\d)-(\\d)\\]");
            Matcher matcher = pattern.matcher(placeName);
            if (matcher.find()) {
                //tunnel
                returnString[0] = Integer.parseInt(matcher.group(1));      
                // depth
                returnString[1] = Integer.parseInt(matcher.group(2));
                System.out.println("tunnel: " + returnString[0] + ", depth: " + returnString[1]);
            }
            return returnString;
        }

}

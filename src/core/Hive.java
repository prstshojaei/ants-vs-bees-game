package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

 /**
 * Represents a hive--which contains the bees that will attack!
 * @author Joel
 * @version Fall 2014
 */
public class Hive extends Place
{
    public static final String NAME = "Hive";

    private int beeArmor; //armor for all the bees
    private Map<Integer,Bee[]> waves; //a mapping from attack times to the list of bees that will charge in

    /**
     * Creates a new hive, in which Bees have the given armor
     * @param beeArmor The armor of the bees
     */
    public Hive(int beeArmor)
    {
        super(NAME, null);
        this.beeArmor = beeArmor;
        this.waves = new HashMap<Integer, Bee[]>();
    }
    
    /**
     * Moves in the invaders who are attacking the colony at the given time.
     * @param colony The colony to attack
     * @param currentTime The current time
     * @return An array of the bees who invaded (for animation/processing)
     */
    public Bee[] invade(AntColony colony, int currentTime)
    {
        Place[] exits = colony.getBeeEntrances();

        Bee[] wave = waves.get(currentTime);
        if(wave == null)
            return new Bee[0]; //return empty set if no bees attacking now

        for(Bee b : wave) //move all the bees in
        {
            int randExit = (int)(Math.random()*exits.length);
            b.moveTo(exits[randExit]); //move b to a random exit from the hive (entrance to the colony)
        }
        return wave; //return who invaded
    }   

    /**
     * Adds a wave of attacking bees to this hive
     * @param attackTime When the bees will attack
     * @param numBees The number of bees to attack
     */
    public void addWave(int attackTime, int numBees)
    {
        Bee[] bees = new Bee[numBees];
        for(int i=0; i<bees.length; i++){
            bees[i] = new Bee(beeArmor);
            this.addInsect(bees[i]); //put the bee in Place
        }
        waves.put(attackTime,bees);
    }

    /**
     * @author Shah Abdul Qadir , Date 13/04/2026
     * Adds a custom wave of specific bee subclasses (like GhostBee or ZombieBee).
     * Required to test custom enemy units without hardcoding standard Bees.
     */
    public void addCustomWave(int attackTime, Bee... customBees) {
        for (Bee b : customBees) {
            this.addInsect(b); //put the custom bee in Place
        }
        waves.put(attackTime, customBees);
    }
    // Shah Abdul Qadir: code change ends here
    
    /**
     * Returns an array of all the bees who are part of the attack (whether they are currently in the hive or not!)
     * @return An array of Bees
     */
    public Bee[] getAllBees() 
    {
        ArrayList<Bee> bees = new ArrayList<Bee>(); //easy temp work
        for(Bee[] wave : waves.values())
        {
            for(int i=0; i<wave.length; i++)
                bees.add(wave[i]);
        }
        return bees.toArray(new Bee[0]);
    }
    
    /////////////////////////////////
    // Convenience factory methods //
    /////////////////////////////////
    
    /**
     * Makes a hive with two attacking bees
     * @return A filled hive
     */
    public static Hive makeTestHive()
    {
        Hive hive = new Hive(3);
        hive.addWave(2,1);

        /**
         * @author Shah Abdul Qadir , Date 13/04/2026
         * Replaced standard wave 3 with custom bees to test new evasion and AoE mechanics.
         */
        hive.addCustomWave(3, new GhostBee(3), new ZombieBee(4));
        // Shah Abdul Qadir: code change ends here

        return hive;
    }

     /**
     * Makes a hive filled with attacking bees
     * @return A filled hive
     */
    public static Hive makeFullHive()
    {
        Hive hive = new Hive(3);
        
        // Turn 2: Spawn one normal bee
        hive.addWave(2, 1);
        
        /**
         * @author Shah Abdul Qadir , Date 14/04/2026
         * Staggered the custom AI enemy units into separate turns (3 and 4) 
         * so their sprites don't randomly stack in the same tunnel.
         */
        hive.addCustomWave(3, new GhostBee(3));
        hive.addCustomWave(4, new ZombieBee(4));
        // Shah Abdul Qadir: code change ends here

        // Turn 6 to 14: Continue spawning normal bees 
        // (Changed the loop start from 5 to 6 to account for the Turn 4 zombie)
        for(int i=6; i<15; i+=2)
            hive.addWave(i,1);
            
        // Turn 15: The final swarm
        hive.addWave(15,8);
        
        return hive;
    }

    /**
     * Makes a hive filled with a huge number of powerful attacking bees
     * @return A filled, angry hive
     */
    public static Hive makeInsaneHive()
    {
        Hive hive = new Hive(4);
        hive.addWave(1,2);
        for(int i=3; i<15; i++)
            hive.addWave(i,1);
        hive.addWave(15,20);
        return hive;
    }

         /**
      * @author Paddy 30/04/2026
      * needed to create a new addWave to take into acount the different bee types
      * Pairs with instantiateBee helper class.
     * Adds a wave of attacking bees to this hive
     * @param attackTime When the bees will attack
     * @param numBees The number of bees to attack
     * @param Bee - give the method a type of bee to build.
     */
    public void addWave(int attackTime, int numBees, Bee type, int armor)
    {
        Bee[] bees = new Bee[numBees];
        for(int i=0; i<bees.length; i++){
            bees[i] = instantiateBee(type, armor);
            this.addInsect(bees[i]); //put the bee in Place
        }
        waves.put(attackTime,bees);
    }
    private Bee instantiateBee(Bee type , int armor) {
        if (type instanceof ZombieBee) {
            return new ZombieBee(armor);
        } else if (type instanceof GhostBee) {
            return new GhostBee(armor);
        } else {
            return new Bee(armor);
        }
    }
    public static Hive makeRandomLevelHive(int level, long seed){
        Random rand = new Random(seed);
        int numBees = level * 20;
        int beeDifficulty = level *2;
        Hive hive = new Hive(beeDifficulty);
        int counter = 1;

        int numbGhostBees = rand.nextInt((numBees / 3) - (2* level)) +  (2* level);
        int numbZombieBees = rand.nextInt((numBees / 3) - (2* level)) +  (2* level);
        int numbStandardBees = numBees - numbGhostBees - numbZombieBees;
        
        while(numBees > level * 5){
            System.out.println("number of bee total = " + numBees + 
                "\n number of standard bees  = " + numbStandardBees + 
                "\n number of zombie bees = " + numbZombieBees + 
                "\n number of ghost bee = " + numbGhostBees+
                "\n counter = " + counter
            );
            if(counter%2 == 0){
                // ensures first waves are normal bees.
                if(numbStandardBees > numbGhostBees || numbStandardBees > numbZombieBees){
                    hive.addWave(counter, level,new Bee(beeDifficulty), beeDifficulty);
                    numBees -= level;
                    numbStandardBees -= level;
                }else{
                double chance = rand.nextDouble(); 
                    if(chance < 0.6 && numbStandardBees > 5){
                        hive.addWave(counter, level,new Bee(beeDifficulty), beeDifficulty);
                        numbStandardBees--;
                        numBees--;
                    }else if(chance < 0.61 && numbGhostBees > 0){
                        hive.addWave(counter, level,new GhostBee(beeDifficulty), beeDifficulty);
                        numbGhostBees--;
                        numBees--;
                    }else if (numbZombieBees > 0){
                        hive.addWave(counter, level,new ZombieBee(beeDifficulty), beeDifficulty);
                        numbZombieBees--;
                        numBees--;
                    }
                }
            }   
            counter++;
        }
        // final wave: collect all remaining bees into one array so the waves map
        // entry isn't overwritten by repeated addCustomWave calls at the same turn.
        ArrayList<Bee> finalWaveBees = new ArrayList<>();
        for (int i = 0; i < numbStandardBees; i++) finalWaveBees.add(new Bee(beeDifficulty));
        for (int i = 0; i < numbZombieBees; i++) finalWaveBees.add(new ZombieBee(beeDifficulty));
        for (int i = 0; i < numbGhostBees; i++) finalWaveBees.add(new GhostBee(beeDifficulty));
        if (!finalWaveBees.isEmpty()) {
            hive.addCustomWave(counter, finalWaveBees.toArray(new Bee[0]));
        }
        return hive;
    }
}
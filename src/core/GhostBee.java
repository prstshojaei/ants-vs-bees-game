package core;

/**
 * @author Shah Abdul Qadir , Date 13/04/2026
 * GhostBee unit: Has a 50% chance to evade incoming damage.
 * Fulfills Developer Story requirement to increase game difficulty.
 */
public class GhostBee extends Bee {

    public GhostBee(int armor) {
        super(armor);
        insectName = "Ghost Bee";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    /**
     * Overrides the default damage method to implement the evasion mechanic.
     */
    @Override
    public void reduceArmor(int amount) {
        // Generate a random number between 0.0 and 1.0
        double dodgeChance = Math.random();

        // 50% evasion rate
        if (dodgeChance < 0.5) {
            /** * @author Shah Abdul Qadir , Date 13/04/2026
             * Integrates with Paddy's new action reporting terminal.
             */
            this.setReport("turned intangible and dodged the attack!");
        } else {
            // If the dodge fails, take damage normally
            super.reduceArmor(amount);
            
            if (this.getArmor() > 0) {
                this.setReport("was hit and has " + this.getArmor() + " armor left.");
            } else {
                this.setReport("was vanquished!");
            }
        }
    }
    public static String getGhostBeeDescriptor(){
		return "Ghost Bee:\nThis is a Ghost Bee.\n it moves at 1 tile per turn & does 1 damage per turn.\n It is hard to hit as it can turn intangible.";
	}
}
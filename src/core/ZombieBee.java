package core;

/**
 * @author Shah Abdul Qadir , Date 13/04/2026
 * ZombieBee unit: Attacks the ant in its current tile AND the ant in the adjacent tile.
 * Fulfills Developer Story requirement to add AoE (Area of Effect) units.
 */
public class ZombieBee extends Bee {

    public ZombieBee(int armor) {
        super(armor);
        insectName = "Zombie Bee";
        setReport("Armor = " + armor + " Action Taken: None");
    }

    /**
     * Overrides the default action to sting multiple targets before moving.
     */
    @Override
    public void action(AntColony colony) {
        if (this.getArmor() > 0) {
            Ant target1 = this.getPlace().getAnt();
            Ant target2 = null;

            // Grab the ant in the next tile forward (towards the queen)
            if (this.getPlace().getExit() != null) {
                target2 = this.getPlace().getExit().getAnt();
            }

            boolean attacked = false;

            // Attack current tile
            if (target1 != null) {
                target1.reduceArmor(1); 
                attacked = true;
            }

            // Attack adjacent tile (Cleave/AoE damage)
            if (target2 != null) {
                target2.reduceArmor(1);
                attacked = true;
            }

            /** * @author Shah Abdul Qadir , Date 13/04/2026
             * Integrates with Paddy's new action reporting terminal to show AoE trigger.
             */
            if (attacked) {
                this.setReport("unleashed a toxic cloud! Attacked multiple ants.");
            } else {
                // If there are no ants to attack, move forward normally
                this.moveTo(this.getPlace().getExit());
                this.setReport("shuffled forward.");
            }
        }
    }
    public static String getZombieBeeDescriptor(){
		return "Zombie Bee:\nThis is a Zombie Bee.\n it moves at 1 tile per turn & does 1 damage per turn.\nIt can hit multiple ants in one turn.";
	}
}
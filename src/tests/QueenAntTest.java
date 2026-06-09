package tests;
import static org.junit.jupiter.api.Assertions.*;
import java.beans.Transient;
import org.junit.jupiter.api.Test;
import ants.QueenAnt;
import ants.ScubaThrowerAnt;
import core.Ant;
import core.AntColony;
import core.Bee;
import core.Place;

public class QueenAntTest {
    @Test
    void TestQueen(){
        int tunnelLength = 8;
        // 1. create a colony
        AntColony colony = new AntColony(3, tunnelLength, 3, 20, 10, 1);
        //2. set places for the queen and her bee.
        Place place1 = colony.getPlaces()[0];
        Place place2 = colony.getPlaces()[tunnelLength];
        // 3. place a bee in every other square to target for the queen
        for (Place place : colony.getPlaces()) {
            place.addInsect(new Bee(1));
        }
        //4.create the queen and place her in the colony at the first possition of the first row
        QueenAnt queenAnt = QueenAnt.getInstance();
        colony.deployAnt(place1, queenAnt);
        //5. create and ant and place it in the colony at the first possition of the second row
        Ant ant = (Ant) new ScubaThrowerAnt();
        colony.deployAnt(place2, ant);

        //6. test pre and post action to see if the queen buffs her ant.
        System.out.println("is ant.isNotBuffed() true = " + ant.isNotBuffed());
        assertTrue(ant.isNotBuffed());
        System.out.println("is ant.getDamage() == 1 true = " + (ant.getDamage() == 1));
        assertTrue(ant.getDamage() == 1);
        queenAnt.action(colony);
        System.out.println("is ant.isNotBuffed() false = " + ant.isNotBuffed());
        assertFalse(ant.isNotBuffed());
        System.out.println("is ant.getDamage() == 2 true = " + (ant.getDamage() == 2));
        assertTrue(ant.getDamage() == 2);
    }
}

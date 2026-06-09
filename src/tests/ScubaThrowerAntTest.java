package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ants.ScubaThrowerAnt;
import ants.ThrowerAnt;
import core.AntColony;
import core.Bee;
import core.Place;

public class ScubaThrowerAntTest {
    
    @Test
    void testPlaceScubaAnt() {
        //step 1 create colony
        AntColony colony = new AntColony(1, 1,0, 5, 10, 1);

        //step 2: update to water Place
        Place place = colony.getPlaces()[0];

        //3: make place a water area
        place.setIsWater(true);

        //step 4: create scuba ant
        ScubaThrowerAnt scubaThrowerAntTest = new ScubaThrowerAnt();

        //step 5: create a normal ant
        ThrowerAnt throwerAnt = new ThrowerAnt();

        //Step 6: check if a normal Ant is denied deployment
        assertEquals("This ant cannot deploy here as it cannot swim.", colony.deployAnt(place, throwerAnt));
        // step 7: check is a scuba ant string success.
        assertEquals(scubaThrowerAntTest.getInsectName() +" Successfully Deployed.", 
                    colony.deployAnt(place, scubaThrowerAntTest));
    }
}

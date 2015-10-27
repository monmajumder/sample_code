package logic;

import com.resistance.theresistance.logic.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;

/**
Testing Missionary.
*/
@RunWith(Theories.class)
public class MissionaryTest {

   /** Fixture initialization (common initialization
    *  for all tests).
    */
   private interface Fixture {
      Missionary init();
   }

   /**
    * Creates Missionary.
    * */
   @DataPoint
   public static final Fixture Missionary = new Fixture() {
      public Missionary init() {
         Player p = new Player("Mindy");
         p.setType(Player.PlayerType.SPY);
         return new Missionary(p);
      }
   };

   /**
    * Verifying that the Missionary can vote on mission
    * @param fix Fixture to test
    * */
   @Theory public void testMissionaryVote(Fixture fix) {
      Missionary b = fix.init();
      boolean vote = b.voteForMission(true);
      assertEquals(vote, true);
   }
   
   /**
    * Verifies that we can change the missionary that is chosen in the missionary object
    */
   @Theory public void testChangeMissionary(Fixture fix){
      Player a = new Player("Andrew");
      Missionary b = fix.init();
      Missionary c = new Missionary(a);
      assert(b.getMissionary().getUsername().equals("Mindy"));
      b.changeMissionary(a);
      assert(b.getMissionary().getUsername().equals("Andrew"));
      assertEquals(c.getMissionary(), a);
   }
}
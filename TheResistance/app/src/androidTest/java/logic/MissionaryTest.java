package logic;

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
    *  for all tests). **/
   private interface Fixture {
      Missionary init(); 
   }
   /**
   Creates Missionary.
   */
   @DataPoint
    public static final Fixture hostObject = 
      new Fixture() {
         public Host init() {
            Player p = new Player("Mindy");
            p.setType("Spy");
            return new Missionary(p);
         }
      };

   /**
   Verifying that the Missionary can vote on mission
   @param fix Fixture to test
   */
   @Theory public void testMissionaryVote(Fixture fix) {
      Missionary b = fix.init();
      boolean vote = b.voteForMission(true);
      assertEquals(vote, true);
   }
   
   /**
   Verifying that the evictPlayer returns the
   player to be evicted.
   */
   @Theory public void testChangeMissionary(Fixture fix){
      Player a = new Player("Andrew");
      Missionary b = fix.init();
      Missionary c = new Missionary(a);
      assertEquals(b.player.username, "Mindy");
      b.changeMissionary(c);
      assertEquals(b.player.username, "Andrew");
      assertEquals(c.player, a);
   }
}
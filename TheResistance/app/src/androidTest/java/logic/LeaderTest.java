package logic;

import java.util.HashMap;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;

/**
Testing PriorityQueue.
*/
@RunWith(Theories.class)
public class LeaderTest {

   /** Fixture initialization (common initialization
    *  for all tests). **/
   private interface Fixture {
      Leader init(); 
   }
   /**
   Creates BinaryHeapPriorityQueue.
   */
   @DataPoint
    public static final Fixture hostObject = 
      new Fixture() {
         public Host init() {
            Player p = new Player("Mindy");
            return new Leader(p);
         }
      };

   /**
   Verifying that the Leader can choose missionaries
   @param fix Fixture to test
   */
   @Theory public void testChooseMissionaries(Fixture fix) {
      Leader b = fix.init();
      HashMap<String, Player> allPlayers = new HashMap<String, Player>();
      int i = 5;
      while (i > 0){
         Player temp = new Player("" + i);
         allPlayers.add(temp);
      i--;
      }
      ArrayList<Players> chosenMiss = b.chooseMissionaries(allPlayers, 2); 
      //Assuming we chose 1 and 2
      assertTrue(chosenMiss.contains("1"));
      assertTrue(chosenMiss.contains("2"));
      assertFalse(chosenMiss.contains("3"));
   }
   
   /**
   Verifying that the changeLeader method changes the
   Leader.
   */
   @Theory public void testChangeLeader(Fixture fix){
      Player a = new Player("Andrew");
      Leader b = fix.init();
      Leader c = new Leader(a);
      assertEquals(b.leader.username, "Mindy");
      b.changeMissionary(c);
      assertEquals(b.leader.username, "Andrew");
      assertEquals(c.leader, a);
   }
}
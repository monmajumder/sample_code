package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;

/**
Testing Player.
*/
@RunWith(Theories.class)
public class PlayerTest {

   /** Fixture initialization (common initialization
    *  for all tests). **/
    private interface Fixture {
        Player init(); 
    }
   /**
   Creates Player.
   */
    @DataPoint
    public static final Fixture hostObject = new Fixture() {
        public Player init() {
            return new Player("Mindy");
        }
    };

   /**
   Verifying that the Player constuctor works
   @param fix Fixture to test
   */
   @Theory public void testPlayer(Fixture fix) {
   Player p = fix.init();
   assertTrue(p.username.equals("Mindy"));
   assertEquals(p.isHost, false);
   assertNull(p.playerType);
   }
   
   /**
   Verifying that the the setType method sets the
   Player's type
   */
   @Theory public void testSetType(Fixture fix){
   Player p = fix.init();
   p.setType("Spy");
   assertEquals(p.playerType, "Spy");
   Player b = fix.init();
   b.setType("Resistor");
   assertTrue(!b.playerType.equals("Spy"));
   assertEquals(b.playerType, "Resistor");
   }
   /**
   Verifying the vote for voting for missinionary team.
   */
   @Theory public void testVoteMissionaries(Fixture fix){
   Player p = fix.init();
   boolean vote = p.voteForMissionaries(true);
   assertEquals(vote, true);
   }

   
   /**
   Verifying exception thrown when getting top from
   an empty queue.
   @param fix Fixture to test
   */
   /*@Theory @Test (expected = EmptyQueueException.class)
   public void cannotTopFromEmptyQueue(Fixture fix) {
   PriorityQueue<Integer> b = fix.init();
   b.top();
   }*/
}

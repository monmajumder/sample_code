package logic;

import java.util.Comparator;
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
public class HostTest {

   /** Fixture initialization (common initialization
    *  for all tests). **/
    private interface Fixture {
        Host init(); 
    }
   /**
   Creates BinaryHeapPriorityQueue.
   */
    @DataPoint
    public static final Fixture hostObject = new Fixture() {
        public Host init() {
            Player p = new Player("Mindy");
            return new Host(p);
        }
    };

   /**
   Verifying that the Host is a host
   @param fix Fixture to test
   */
   @Theory public void testIsHost(Fixture fix) {
   Host b = fix.init();
   assertTrue(b.host.isHost());
   }
   
   /**
   Verifying that the evictPlayer returns the
   player to be evicted.
   */
   @Theory public void testEvictPlayer(Fixture fix){
   Host b = fix.init();
   Player m = new Player("Mindy");
   assertEquals(b.evict(m), m);
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

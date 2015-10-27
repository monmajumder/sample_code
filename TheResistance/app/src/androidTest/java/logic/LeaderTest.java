package logic;

import com.resistance.theresistance.logic.*;
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
 * Testing Leader Object
 */
@RunWith(Theories.class)
public class LeaderTest {

   /** Fixture initialization (common initialization
    *  for all tests).
    */
   private interface Fixture {
      Leader init(); 
   }

    /**
     * Creates leader object
     */
   @DataPoint
    public static final Fixture hostObject = new Fixture() {
       public Leader init() {
           Player p = new Player("Mindy");
           return new Leader(p);
       }
   };
   
   /**
    * Verifying that the changeLeader method changes the Leader.
    */
   @Theory public void testChangeLeader(Fixture fix){
      Player a = new Player("Andrew");
      Leader b = fix.init();
      Leader c = new Leader(a);
      assert(b.getLeader().getUsername().equals("Mindy"));
      b.changeLeader(a);
      assert(b.getLeader().getUsername().equals("Andrew"));
   }
}
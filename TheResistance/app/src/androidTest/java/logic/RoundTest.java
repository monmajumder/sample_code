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
Testing Round.
*/
@RunWith(Theories.class)
public class RoundTest {

   /** Fixture initialization (common initialization
    *  for all tests). **/
   private interface Fixture {
      Round init(); 
   }
   /**
   Creates Round.
   */
   @DataPoint
    public static final Fixture RoundObject =
      new Fixture() {
         public Round init() {
            return new Round(1);
         }
      };

   /**
   Verifying the setLeader method works.
   @param fix Fixture to test
   */
   @Theory public void testSetLeader(Fixture fix) {
      Player p = new Player("Monica");
      Leader b = new Leader(p);
      Round r = fix.init();
      r.setLeader(b);
      assertEquals(r.getLeader(), b);
   }
}
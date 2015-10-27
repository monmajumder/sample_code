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
    public static final Fixture hostObject = 
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
      Player p = new Player("polly");
      Leader b = new Leader(p);
      Round r = fix.init();
      r.setLeader(b);
      assertEquals(r.leader, b);
   }
   
   /**
   Verifying that the setMissionaries method works.
   */
   @Theory public void testChangeLeader(Fixture fix){
      Player a = new Player("Andrew");
      Player c = new Player("Candace");
      Missionary an = new Missionary(a);
      Missionary ca = new Missionary(c);
      ArrayList<String> misn = new ArrayList<String>();
      misn.add(an.username);
      misn.add(ca.username);
      Round r = fix.init();
      r.setMissionaries(misn);
      assertEquals(r.missionaries, misn);
   }
   
   /**
   Verifying that the setVotes method works.
   */
   @Theory public void setVotes(Fixture fix){
      Player a = new Player("Andrew");
      Player c = new Player("Candace");
      HashMap<String, Boolean> votes = new HashMap<String, boolean>();
      votes.put(a.username, true);
      votes.put(b.username, false);
      Round r = fix.init();
      r.setVotes(votes);
      assertEquals(r.votes, votes);
   }
}
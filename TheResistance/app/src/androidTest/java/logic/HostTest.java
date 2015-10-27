package logic;

import com.resistance.theresistance.logic.*;
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
 * Testing Host.
 */
@RunWith(Theories.class)
public class HostTest {

   /** Fixture initialization (common initialization
    *  for all tests). **/
    private interface Fixture {
        Host init(); 
    }

    /**
     * Creates Host.
     */
    @DataPoint
    public static final Fixture hostObject = new Fixture() {
        public Host init() {
            Player p = new Player("Mindy");
            return new Host(p);
        }
    };

    /**
     * Verifies that the evictPlayer returns the player to be evicted.
     * @param fix Fixture to test
     */
    @Theory public void testEvictPlayer(Fixture fix){
        Host b = fix.init();
        Player m = new Player("Mindy");
        assertEquals(b.evict(m), m);
    }
}

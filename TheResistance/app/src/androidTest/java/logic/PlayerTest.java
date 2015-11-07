package logic;

import com.resistance.theresistance.logic.*;
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
 * Tests Player.
 * */
@RunWith(Theories.class)
public class PlayerTest {

    /**
     * Fixture initialization (common initialization for all tests)
     */
    private interface Fixture {
        Player init();
    }

    /**
    * Creates Player.
    */
    @DataPoint
    public static final Fixture playerObject = new Fixture() {
        public Player init() {
            return new Player("Mindy");
        }
    };

    /**
     * Verifying that the Player constuctor works
     * @param fix Fixture to test
     */
    @Theory
    public void testPlayer(Fixture fix) {
        Player p = fix.init();
        assertTrue(p.getUsername().equals("Mindy"));
        assertNull(p.getPlayerType());
    }

    /**
     * Verifies that the the setType method sets the Player's type
     * @param fix Fixture to test
     */
    @Theory
    public void testSetType(Fixture fix){
        Player p = fix.init();
        p.setType(Player.PlayerType.SPY);
        assert(p.getPlayerType().equals(Player.PlayerType.SPY));
        Player b = fix.init();
        b.setType(Player.PlayerType.RESISTOR);
        assertTrue(!b.getPlayerType().equals(Player.PlayerType.SPY));
        assertTrue(b.getPlayerType().equals(Player.PlayerType.RESISTOR));
    }

    /**
     * Verifying the vote for voting for missionary team.
     * @param fix Fixture to test
     */
    @Theory
    public void testVoteMissionaries(Fixture fix){
        Player p = fix.init();
        boolean vote = p.voteForMissionaries(true);
        assertEquals(vote, true);
    }

}

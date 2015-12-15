package logic;

import com.parse.ParseException;
import com.resistance.theresistance.logic.Round;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Testing Round.
 */
public class RoundTest {

    /**
     * Tests setting a leader.
     */
    @Test
    public void testSetLeader() {
        Round round = new Round();
        String leader = "Candace";
        round.setLeader(leader);
        try {
            round.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(leader,round.getLeader());
    }

    /**
     * Tests setting the missionaries.
     */
    @Test
    public void testSetMissionaries() {
        Round round = new Round();
        ArrayList<String> missionaries = new ArrayList();
        missionaries.add("Candace");
        missionaries.add("Monica");
        missionaries.add("Andrew");
        round.setMissionaries(missionaries);
        try {
            round.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(missionaries,round.getMissionaries());
    }

    /**
     * Tests setting the assentors.
     */
    @Test
    public void testSetAssentors() {
        Round round = new Round();
        ArrayList<String> assentors = new ArrayList();
        assentors.add("Candace");
        assentors.add("Monica");
        round.setAssentors(assentors);
        try {
            round.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(assentors,round.getAssentors());
    }

    /**
     * Tests setting the dissentors.
     */
    @Test
    public void testSetDissentors() {
        Round round = new Round();
        ArrayList<String> dissentors = new ArrayList();
        dissentors.add("Candace");
        dissentors.add("Monica");
        round.setDissentors(dissentors);
        try {
            round.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(dissentors,round.getDissentors());
    }

    /**
     * Test missionaries accepted.
     */
    @Test
    public void testMissionariesAccepted() {
        Round round = new Round();
        round.setMissionariesAccepted(false);
        assertFalse(round.getMissionariesAccepted());
    }

}

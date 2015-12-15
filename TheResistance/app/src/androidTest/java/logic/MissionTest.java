package logic;

import com.resistance.theresistance.logic.Mission;
import com.resistance.theresistance.logic.Round;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing Mission.
 */
public class MissionTest {

    /**
     * Testing get current mission leader.
     */
    @Test
    public void testGetCurrentMissionLeader() {
        Mission mission = new Mission();
        Round round1 = new Round();
        round1.setLeader("Andrew");
        Round round2 = new Round();
        round2.setLeader("Candace");
        List<Round> rounds = new ArrayList<>();
        rounds.add(round1);
        rounds.add(round2);
        mission.setRounds(rounds);

        assertEquals("Candace", mission.getCurrentMissionLeader());
    }

    /**
     * Testing getting a current round.
     */
    @Test
    public void testGetCurrentRound() {
        Mission mission = new Mission();
        Round round1 = new Round();
        Round round2 = new Round();
        List<Round> rounds = new ArrayList<>();
        rounds.add(round1);
        rounds.add(round2);
        mission.setRounds(rounds);

        assertEquals(round2, mission.getCurrentRound());
    }

}

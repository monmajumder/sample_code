package logic;

import com.parse.ParseException;
import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.Mission;
import com.resistance.theresistance.logic.Player;
import com.resistance.theresistance.logic.Round;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests Game.
 * */
public class GameTest {

    /**
     * Tests adding a player
     */
    @Test
    public void testAddPlayer() {
        Game game = new Game();
        Player player = new Player();

        game.addPlayer(player);
        try {
            player.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(player, game.getPlayers().get(0));
        assertEquals(1, game.getNumPlayers());
    }

    /**
     * Test get current mission.
     */
    @Test
    public void testGetCurrentMission() {
        Game game = new Game();
        Mission mission1 = new Mission();
        Mission mission2 = new Mission();

        List<Mission> missions = new ArrayList<>();
        missions.add(mission1);
        missions.add(mission2);
        game.setMissions(missions);

        try {
            mission1.save();
            mission2.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(mission2, game.getCurrentMission());
    }

    /**
     * Tests get current leader.
     */
    @Test
    public void testGetCurrentLeader() {
        Game game = new Game();
        Mission mission = new Mission();
        Round round = new Round();
        round.setLeader("Candace");

        ArrayList<Round> rounds = new ArrayList<>();
        rounds.add(round);
        mission.setRounds(rounds);

        ArrayList<Mission> missions = new ArrayList<>();
        missions.add(mission);
        game.setMissions(missions);

        try {
            round.save();
            mission.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals("Candace", game.getCurrentLeader());
    }

    /**
     * Tests get current mission number.
     */
    @Test
    public void testGetCurrentMissionNumber() {
        Game game = new Game();
        Mission mission1 = new Mission();
        Mission mission2 = new Mission();
        Mission mission3 = new Mission();

        ArrayList<Mission> missions = new ArrayList<>();
        missions.add(mission1);
        missions.add(mission2);
        missions.add(mission3);
        game.setMissions(missions);

        try {
            mission1.save();
            mission2.save();
            mission3.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(3, game.getCurrentMissionNumber());
    }
}

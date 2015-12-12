package logic;

import android.util.Log;

import com.resistance.theresistance.activities.GamePlayActivity;
import com.resistance.theresistance.logic.*;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.runners.Parameterized;

import com.parse.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Testing GameController.
 */
public class GameControllerTest {

    /**
     * Tests if checkHost works correctly.
     */
    @Test
    public void testCheckHost() {
        String playerName = UUID.randomUUID().toString();
        String gameName = UUID.randomUUID().toString();
        createUniqueGame(gameName, playerName);
        assertTrue(GameController.checkHost(gameName, playerName));
    }


    /**
     * Test when there are too few players to start.
     */
    @Test
    public void testCheckNotEnoughPlayers() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setNumPlayers(1);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertFalse(GameController.checkEnoughPlayers(gameName));
    }

    /**
     * Test when there are enough players to start.
     */
    @Test
    public void testCheckEnoughPlayers() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setNumPlayers(5);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertTrue(GameController.checkEnoughPlayers(gameName));
    }

    /**
     * Test if update players (returning array of player names) works.
     */
    @Test
    public void testUpdatePlayers() {
        String gameName = UUID.randomUUID().toString();
        String player1Name = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, player1Name);

        String player2Name = UUID.randomUUID().toString();
        Player player2 = new Player();
        player2.setUsername(player2Name);
        game.addPlayer(player2);

        try {
            player2.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }

        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add(player1Name);
        playerNames.add(player2Name);

        assertEquals(playerNames, GameController.updatePlayers(gameName));
    }

    /**
     * Test if checking that a game is not started works.
     */
    @Test
    public void testCheckNotStarted() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setGameState(Game.State.WAITING_FOR_PLAYERS);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertFalse(GameController.checkStarted(gameName));
    }

    /**
     * Test if checking that a game is started works.
     */
    @Test
    public void testCheckStarted() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setGameState(Game.State.MISSION_LEADER_CHOOSING);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertTrue(GameController.checkStarted(gameName));
    }

    /**
     * Tests if a player is a spy.
     */
    @Test
    public void testIsNotResistance() {
        String playerName = UUID.randomUUID().toString();
        Player player = new Player();
        player.setUsername(playerName);
        player.setPlayerType(Player.PlayerType.SPY);
        try {
            player.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertFalse(GameController.isResistance(playerName));
    }

    /**
     * Tests if a player is resistance.
     */
    @Test
    public void testIsResistance() {
        String playerName = UUID.randomUUID().toString();
        Player player = new Player();
        player.setUsername(playerName);
        player.setPlayerType(Player.PlayerType.RESISTOR);
        try {
            player.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertTrue(GameController.isResistance(playerName));
    }

    /**
     * Tests if a player is the leader of the most current round.
     */
    @Test
    public void testIsLeader() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);

        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertTrue(GameController.checkLeader(gameName, playerName));

    }

    /**
     * Tests if a player is not the leader.
     */
    @Test
    public void testIsNotLeader() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);

        Round round = new Round();
        round.setLeader(UUID.randomUUID().toString());
        List<Round> rounds = game.getCurrentMission().getRounds();
        rounds.add(round);
        game.getCurrentMission().setRounds(rounds);
        try {
            round.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertFalse(GameController.checkLeader(gameName, playerName));
    }

    /**
     * Tests if get number of missionaries required to go on a mission works.
     */
    @Test
    public void testGetMissionariesRequired() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setNumPlayers(5);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(2, GameController.getMissionariesRequired(gameName));
    }

    /**
     * Tests if a player is a missionary.
     */
    @Test
    public void testIsMissionary() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        Round round = game.getCurrentMission().getCurrentRound();

        ArrayList<String> missionaries = new ArrayList<>();
        missionaries.add(playerName);
        round.setMissionaries(missionaries);
        try {
            round.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertTrue(GameController.checkMissionary(gameName, playerName));
    }

    /**
     * Test when mission leader is done choosing.
     */
    @Test
    public void testMissionLeaderDoneChoosing() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setGameState(Game.State.VOTE_FOR_MISSIONARIES);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertTrue(GameController.checkMissionLeaderDoneChoosing(gameName));
    }

    /**
     * Tests get current mission.
     */
    @Test
    public void testGetCurrentMission() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);

        Mission newMission = new Mission();
        List<Mission> missions = game.getMissions();
        missions.add(newMission);
        game.setMissions(missions);
        try {
            newMission.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(newMission, GameController.getCurrentMission(gameName));
    }

    /**
     * Tests if chosen missionaries are added correctly.
     */
    @Test
    public void testAddChosenMissionaries() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);

        ArrayList<String> missionaries = new ArrayList<>();
        missionaries.add(playerName);
        GameController.addChosenMissionaries(gameName, missionaries);

        assertEquals(missionaries, GameController.getChosenMissionaries(gameName));
    }

    public void testAddVoteForMissionaries() {

    }

    /**
     * Test if everyone done voting.
     */
    @Test
    public void testIfEveryoneDoneVoting() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setGameState(Game.State.MISSIONARIES_VOTING);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(Game.State.MISSIONARIES_VOTING, GameController.ifEveryoneDoneVoting(gameName));
    }

    public void testAddPassFailForMission() {

    }

    /**
     * Check that missionaries are done voting.
     */
    @Test
    public void testIfMissionariesDoneVoting() {
        /**
        GamePlayActivity activity = new GamePlayActivity();
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setGameState(Game.State.MISSION_LEADER_CHOOSING);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(Game.State.MISSION_LEADER_CHOOSING, GameController.ifMissionariesDoneVoting(activity, gameName)); **/
    }

    /**
     * Tests if a game state is changed.
     */
    @Test
    public void testChangeState() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        GameController.changeState(gameName, Game.State.SPIES_WIN);
        try {
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(Game.State.SPIES_WIN, GameController.getState(gameName));
    }

    /**
     * Creates a game to be used in tests.
     * @param gameName Name of the game
     * @param playerName Player of the game
     * @return Game that was created
     */
    private static Game createUniqueGame(String gameName, String playerName) {

        Player player = new Player();
        Game game = new Game();
        Mission mission = new Mission();
        Round round = new Round();

        player.setUsername(playerName);
        game.setKeyword(gameName);
        game.addPlayer(player);
        game.setHost(playerName);
        round.setLeader(playerName);

        List<Round> rounds = new ArrayList<>();
        rounds.add(round);
        mission.setRounds(rounds);

        List<Mission> missions = new ArrayList<>();
        missions.add(mission);
        game.setMissions(missions);

        try {
            player.save();
            round.save();
            mission.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }

        return game;
    }
}
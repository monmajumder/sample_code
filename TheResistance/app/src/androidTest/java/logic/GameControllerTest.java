package logic;

import com.resistance.theresistance.logic.*;
import org.junit.Test;

import com.parse.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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

    /**
     * Tests when vote is added for missionary team and enough players vote no so new Round is created in the same mission.
     */
    @Test
    public void testAddVoteForMissionariesToNewRound() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);
        game.setGameState(Game.State.VOTE_FOR_MISSIONARIES);

        Player player1 = new Player();
        player1.setUsername("Candace");
        Player player2 = new Player();
        player2.setUsername("Monica");
        Player player3 = new Player();
        player3.setUsername("Andrew");
        Player player4 = new Player();
        player4.setUsername("Jenny");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        ArrayList<String> assentors = new ArrayList<>(Arrays.asList("Candace", "Monica"));
        ArrayList<String> dissentors = new ArrayList<>(Arrays.asList("Jenny", "Andrew"));
        game.getCurrentMission().getCurrentRound().setAssentors(assentors);
        game.getCurrentMission().getCurrentRound().setDissentors(dissentors);

        try {
            player1.save();
            player2.save();
            player3.save();
            player4.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }

        assertEquals(1, GameController.getGame(gameName).getMissions().size());
        assertEquals(1, GameController.getCurrentMission(gameName).getRounds().size());

        GameController.addVoteForMissionaries(false, gameName, playerName);
        assertEquals(Game.State.MISSION_LEADER_CHOOSING, GameController.getState(gameName));
        assertEquals(1, GameController.getGame(gameName).getMissions().size());
        assertEquals(2, GameController.getCurrentMission(gameName).getRounds().size());
    }

    /**
     * Tests when vote is added for missionary team and it is failed for the 5th time, proceed to new mission.
     */
    @Test
    public void testAddVoteForMissionariesToNewMission() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);

        Player player1 = new Player();
        player1.setUsername("Candace1");
        Player player2 = new Player();
        player2.setUsername("Monica1");
        Player player3 = new Player();
        player3.setUsername("Andrew1");
        Player player4 = new Player();
        player4.setUsername("Jenny1");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        Round round1 = new Round();
        round1.setMissionariesAccepted(false);
        round1.setLeader("Candace");
        Round round2 = new Round();
        round2.setMissionariesAccepted(false);
        round2.setLeader("Monica");
        Round round3 = new Round();
        round3.setMissionariesAccepted(false);
        Round round4 = new Round();
        round3.setLeader("Andrew");
        round4.setMissionariesAccepted(false);
        Round round5 = new Round();
        round4.setLeader("Jenny");
        round5.setMissionariesAccepted(false);
        round5.setLeader(playerName);

        ArrayList<String> assentors = new ArrayList<>(Arrays.asList("Candace", "Monica"));
        ArrayList<String> dissentors = new ArrayList<>(Arrays.asList("Jenny", "Andrew"));
        round5.setAssentors(assentors);
        round5.setDissentors(dissentors);

        List<Round> rounds = new ArrayList<>();
        rounds.add(round1);
        rounds.add(round2);
        rounds.add(round3);
        rounds.add(round4);
        rounds.add(round5);
        game.getCurrentMission().setRounds(rounds);

        try {
            player1.save();
            player2.save();
            player3.save();
            player4.save();
            round1.save();
            round2.save();
            round3.save();
            round4.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }

        assertEquals(1, GameController.getGame(gameName).getMissions().size());
        assertEquals(5, GameController.getCurrentMission(gameName).getRounds().size());

        GameController.addVoteForMissionaries(false, gameName, playerName);
        assertEquals(Game.State.MISSION_LEADER_CHOOSING, GameController.getState(gameName));
        assertEquals(2, GameController.getGame(gameName).getMissions().size());
        assertEquals(1, GameController.getCurrentMission(gameName).getRounds().size());
    }

    /**
     * Tests when vote is added for missionary team and enough players vote yes to proceed to missionary voting.
     */
    @Test
    public void testAddVoteForMissionariesToMissionaryVoting() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();
        Game game = createUniqueGame(gameName, playerName);

        Player player1 = new Player();
        player1.setUsername("Candace");
        Player player2 = new Player();
        player2.setUsername("Monica");
        Player player3 = new Player();
        player3.setUsername("Andrew");
        Player player4 = new Player();
        player4.setUsername("Jenny");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        ArrayList<String> assentors = new ArrayList<>(Arrays.asList("Candace", "Monica"));
        ArrayList<String> dissentors = new ArrayList<>(Arrays.asList("Jenny", "Andrew"));
        game.getCurrentMission().getCurrentRound().setAssentors(assentors);
        game.getCurrentMission().getCurrentRound().setDissentors(dissentors);

        try {
            player1.save();
            player2.save();
            player3.save();
            player4.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }

        GameController.addVoteForMissionaries(true, gameName, playerName);
        assertEquals(Game.State.MISSIONARIES_VOTING, GameController.getState(gameName));
    }
/*
    *//**
     * Test if everyone done voting.
     *//*
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
    }*/

    /**
     * Tests when a new mission is created after missionaries vote.
     */
    @Test
    public void testAddPassFailForMissionToNewMission() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();

        Game game = new Game();
        game.setKeyword(gameName);

        Player player = new Player();
        player.setUsername(playerName);
        Player player1 = new Player();
        player1.setUsername("Candace");
        Player player2 = new Player();
        player2.setUsername("Monica");
        Player player3 = new Player();
        player3.setUsername("Andrew");
        Player player4 = new Player();
        player4.setUsername("Jenny");
        game.addPlayer(player);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        Mission mission1 = new Mission();

        Round round = new Round();
        round.setLeader("Candace");
        ArrayList<String> missionaries = new ArrayList<>(Arrays.asList("Candace", playerName));
        round.setMissionaries(missionaries);
        List<Round> rounds = new ArrayList<>();
        rounds.add(round);
        mission1.setRounds(rounds);
        mission1.setFail(1);

        List<Mission> missions = new ArrayList<>();
        missions.add(mission1);
        game.setMissions(missions);

        try {
            player.save();
            player1.save();
            player2.save();
            player3.save();
            player4.save();
            mission1.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }

        assertEquals(1, GameController.getGame(gameName).getMissions().size());
        GameController.addPassFailForMission(false, gameName);
        assertEquals(2, GameController.getGame(gameName).getMissions().size());
        assertEquals(Game.State.MISSION_LEADER_CHOOSING, GameController.getState(gameName));
    }

    /**
     * Tests when the spies win.
     */
    @Test
    public void testAddPassFailForMissionToSpiesWin() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();

        Game game = new Game();
        game.setKeyword(gameName);

        Player player = new Player();
        player.setUsername(playerName);
        Player player1 = new Player();
        player1.setUsername("Candace");
        Player player2 = new Player();
        player2.setUsername("Monica");
        Player player3 = new Player();
        player3.setUsername("Andrew");
        Player player4 = new Player();
        player4.setUsername("Jenny");
        game.addPlayer(player);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        Mission mission1 = new Mission();
        mission1.setPassed(false);
        Mission mission2 = new Mission();
        mission2.setPassed(false);
        Mission mission3 = new Mission();

        Round round = new Round();
        round.setLeader("Candace");
        ArrayList<String> missionaries = new ArrayList<>(Arrays.asList("Candace", playerName));
        round.setMissionaries(missionaries);
        List<Round> rounds = new ArrayList<>();
        rounds.add(round);
        mission3.setRounds(rounds);
        mission3.setFail(1);

        List<Mission> missions = new ArrayList<>();
        missions.add(mission1);
        missions.add(mission2);
        missions.add(mission3);
        game.setMissions(missions);

        try {
            player.save();
            player1.save();
            player2.save();
            player3.save();
            player4.save();
            mission1.save();
            mission2.save();
            mission3.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }

        GameController.addPassFailForMission(false, gameName);
        assertEquals(Game.State.SPIES_WIN, GameController.getState(gameName));
    }

    /**
     * Tests when the Resistance wins.
     */
    @Test
    public void testAddPassFailForMissionToResistanceWin() {
        String gameName = UUID.randomUUID().toString();
        String playerName = UUID.randomUUID().toString();

        Game game = new Game();
        game.setKeyword(gameName);

        Player player = new Player();
        player.setUsername(playerName);
        Player player1 = new Player();
        player1.setUsername("Candace");
        Player player2 = new Player();
        player2.setUsername("Monica");
        Player player3 = new Player();
        player3.setUsername("Andrew");
        Player player4 = new Player();
        player4.setUsername("Jenny");
        game.addPlayer(player);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

        Mission mission1 = new Mission();
        mission1.setPassed(true);
        Mission mission2 = new Mission();
        mission2.setPassed(true);
        Mission mission3 = new Mission();

        Round round = new Round();
        round.setLeader("Candace");
        ArrayList<String> missionaries = new ArrayList<>(Arrays.asList("Candace", playerName));
        round.setMissionaries(missionaries);
        List<Round> rounds = new ArrayList<>();
        rounds.add(round);
        mission3.setRounds(rounds);
        mission3.setPass(1);

        List<Mission> missions = new ArrayList<>();
        missions.add(mission1);
        missions.add(mission2);
        missions.add(mission3);
        game.setMissions(missions);

        try {
            player.save();
            player1.save();
            player2.save();
            player3.save();
            player4.save();
            mission1.save();
            mission2.save();
            mission3.save();
            game.save();
        } catch (ParseException e) {
            assertTrue(false);
        }

        GameController.addPassFailForMission(true, gameName);
        assertEquals(Game.State.RESISTANCE_WINS, GameController.getState(gameName));
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
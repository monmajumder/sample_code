package com.resistance.theresistance.logic;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Game class that represents 1 game room. In charge of adding players.
 */
@ParseClassName("GameObject")
public class Game extends ParseObject {

    private String host;
    private String keyword;
    private int numPlayers;
    private ArrayList<Player> players;
    private List<Mission> missions;

    /**
     * Defines the states that a game can be in
     */
    public enum State {
        WAITING_FOR_PLAYERS, MISSION_LEADER_CHOOSING, VOTE_FOR_MISSIONARIES, MISSIONARIES_VOTING, RESISTANCE_WINS, SPIES_WIN
    }
    private State gameState;

    /**
     * Constructor
     */
    public Game() {
        super();
    }

    /**
     * Adds a Player to a Game's Player array and increments number counter
     * @param player Player object to be added
     */
    public void addPlayer(Player player) {
            int newNumPlayers = getNumPlayers() + 1;
            setNumPlayers(newNumPlayers);
            List<Player> playerList = getPlayers();
            if (playerList == null) {
                players = new ArrayList<>();
            } else {
                players = new ArrayList<>(getPlayers());
            }
            players.add(player);
            setPlayers(players);
    }

    /**
     * Gets the current mission.
     * @return Mission object that is the current mission
     */
    public Mission getCurrentMission() {
        missions = getMissions();
        return missions.get(missions.size()-1);
    }

    /**
     * Gets the previous mission
     * @return Mission object that is the previous mission
     */
    public Mission getPreviousMission() {
        missions = getMissions();
        return missions.get(missions.size()-2);
    }

    /**
     * Gets the name of the current mission leader.
     * @return Name of the current mission leader.
     */
    public String getCurrentLeader() {
        return getCurrentMission().getCurrentMissionLeader();
    }

    /**
     * Gets the number of the current mission.
     * @return Number of missions
     */
    public int getCurrentMissionNumber() {
        return getMissions().size();
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    /**
     * Gets the keyword for the game
     * @return Keyword
     */
    public String getKeyword() {
        return getString("Name");
    }

    /**
     * Sets the keyword for the game
     * @param keyword Unique keyword for the game
     */
    public void setKeyword(String keyword) {
        put("Name", keyword);
    }

    /**
     * Gets the number of players
     * @return Number of players
     */
    public int getNumPlayers() {
        return getInt("NumPlayers");
    }

    /**
     * Sets the number of players
     * @param num Number of players
     */
    public void setNumPlayers(int num) {
        put("NumPlayers", num);
    }

    /**
     * Gets the game state
     * @return Enum for game state
     */
    public State getGameState() {
        return State.valueOf(getString("State"));
    }

    /**
     * Sets the game state
     * @param state Enum for game state
     */
    public void setGameState(State state) {
        put("State", state.toString());
    }

    /**
     * Sets the host
     * @param hostName Name of player
     */
    public void setHost(String hostName) {
        put("Host",hostName);
    }

    /**
     * Gets the host
     * @return Name of player
     */
    public String getHost() {
        return getString("Host");
    }

    /**
     * Gets list of all players
     * @return List of player objects
     */
    public List<Player> getPlayers() {
        return getList("Players");
    }

    /**
     * Sets the list of players
     * @param players List of player objects
     */
    public void setPlayers(List<Player> players) {
        put("Players",players);
    }

    /**
     * Gets the list of missions
     * @return List of mission objects
     */
    public List<Mission> getMissions() {
        return getList("Missions");
    }

    /**
     * Sets the list of missions
     * @param missions List of mission objects
     */
    public void setMissions(List<Mission> missions) {
        put("Missions",missions);
    }

}
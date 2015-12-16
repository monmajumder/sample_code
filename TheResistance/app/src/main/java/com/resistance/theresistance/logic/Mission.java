package com.resistance.theresistance.logic;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Mission class is in charge of a single mission. In charge of keeping track how many people voted to pass or fail the mission.
 */
@ParseClassName("MissionObject")
public class Mission extends ParseObject {

    private boolean passed;//if the players voted to pass the mission
    private List<Round> rounds;
    private int pass; //number of players who voted to pass the mission
    private int fail; //number of players who voted to fail the mission

    /**
     * Constructor.
     */
    public Mission() {
        super();
    }

    /**
     * Returns the name of the current Mission Leader
     * @return Name of the current Mission leader
     */
    public String getCurrentMissionLeader() {
        return getCurrentRound().getLeader();
    }

    /**
     * Returns the current Round.
     * @return Round object that is the current round
     */
    public Round getCurrentRound() {
        rounds = getRounds();
        return rounds.get(rounds.size()-1);
    }

    /**
     * Returns the previous Round.
     * @return Round object that is the previous round.
     */
    public Round getPreviousRound() {
        rounds = getRounds();
        return rounds.get(rounds.size()-2);
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    /**
     * Gets the mission number
     * @return Mission number
     */
    public int getMissionNum() {
        return getInt("MissionNumber");
    }

    /**
     * Sets the mission number
     * @param missionNum New mission number
     */
    public void setMissionNum(int missionNum) {
        put("MissionNumber", missionNum);
    }

    /**
     * Gets the number of votes to pass the mission
     * @return Number of pass votes
     */
    public int getPass() {
        return getInt("Pass");
    }

    /**
     * Sets the number of votes to pass the mission
     * @param num Number of pass votes
     */
    public void setPass(int num) {
        put("Pass", num);
    }

    /**
     * Gets the number of votes to fail the mission
     * @return Number of fail votes
     */
    public int getFail() {
        return getInt("Fail");
    }

    /**
     * Sets the number of votes to fail the mission
     * @param num Number of fail votes
     */
    public void setFail(int num) {
        put("Fail", num);
    }

    /**
     * Gets the list of rounds in a mission
     * @return List of rounds
     */
    public List<Round> getRounds() {
        return getList("Rounds");
    }

    /**
     * Sets the list of rounds in a mission
     * @param rounds List of rounds
     */
    public void setRounds(List<Round> rounds) {
        put("Rounds", rounds);
    }

    /**
     * Gets whether or not a mission passed
     * @return True if a mission passed, false if it failed
     */
    public boolean getPassed() {
        return getBoolean("Passed");
    }

    /**
     * Sets whether or not a mission passed
     * @param passed True if a mission passed, false if it failed
     */
    public void setPassed(boolean passed) {
        put("Passed", passed);
    }

    /**
     * Gets the object ID for a mission object.
     * @return Id for a mission object
     */
    public String getId() {
        return getObjectId();
    }
}
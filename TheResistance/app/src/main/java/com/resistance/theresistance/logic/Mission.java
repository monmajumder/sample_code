package com.resistance.theresistance.logic;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Mission class is in charge of all things mission related.
 * Keeps track of the mission number, creates the Mission Leader,
 * keeps track of mission history, etc.
 * @author Group 11
 *
 */
@ParseClassName("MissionObject")
public class Mission extends ParseObject {

    private String objectId; //DELETE
    private int missionNum;
    private boolean succeeded;//if the players voted to pass the mission
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
     * Adds a vote to the total votes for Pass.
     */
    public void addPassVote() {
        int newNum = getPass() + 1;
        setPass(newNum);
    }

    /**
     * Adds a vote to the total votes for Fail.
     */
    public void addFailVote() {
        int newNum = getFail() + 1;
        setFail(newNum);
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

    //DELETE
    public String getId() {
        return getObjectId();
    }
}
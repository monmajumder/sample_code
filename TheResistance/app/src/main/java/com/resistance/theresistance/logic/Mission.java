package com.resistance.theresistance.logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Mission class is in charge of all things mission related.
 * Keeps track of the mission number, creates the Mission Leader,
 * keeps track of mission history, etc.
 * @author Group 11
 *
 */

public class Mission{

    private Leader missionLeader;
    private int missionNum;
    private boolean success;//if the players voted yes/no for the mission
    private boolean approved;//if the mission is approved
    private ArrayList<Round> stateHistory;
    private ArrayList<Missionary> missionaries;

   /**
    * Creates a mission object
    * @param missionLeader the object for the leader of the mission, in charge of choosing missionaries
    * @param missionNum the mission number
    * @param stateHistory keeps track of all players votes
    */
   public Mission(Leader missionLeader, int missionNum, ArrayList<Round> stateHistory){
       this.missionLeader = missionLeader;
       this.missionNum = missionNum;
       this.stateHistory = stateHistory;
       this.approved = false;
       this.success = false;
   }

    /**
     * Gets the mission number
     * @return missionNum the mission number
     */
    public int getMissionNumber(){
        return missionNum;
    }

    /**
     * Checks if the mission is approved
     * @return approved is true if the mission is approved, false if not
     */
    public boolean isApproved(){
        return approved;
    }

    /**
     * Generates the number of a random mission leader.
     * @param numPlayers the number of players in the game
     * @return newLeader the number of the new mission leader
     */
    public int createRandomMissionLeader(int numPlayers){
        Random rn = new Random();
        int newLeader = rn.nextInt(numPlayers);
        return newLeader;
    }

    /**
     * Changes the mission leader to a new player.
     * @param newLeader player that is the new mission leader
     */
    public void changeLeader(Leader newLeader){
        this.missionLeader = newLeader;
    }

}
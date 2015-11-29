package com.resistance.theresistance.logic;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
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

    private int missionNum;
    private boolean succeeded;//if the players voted yes/no for the mission
    private ArrayList<Round> rounds;
    private int yes; //number of players who voted yes
    private int no; //number of players who voted no

    /**
     * Constructor.
     */
    public Mission() {
        super();
    }

   /**
    * Creates a mission object
    * @param missionNum the mission number
    * @param rounds keeps track of all players votes
    */
   public Mission(int missionNum, ArrayList<Round> rounds){
       this.missionNum = missionNum;
       this.rounds = rounds;
       this.succeeded = false;
   }

    /**
     * Gets the mission number
     * @return missionNum the mission number
     */
    public int getMissionNumber(){
        return missionNum;
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


}
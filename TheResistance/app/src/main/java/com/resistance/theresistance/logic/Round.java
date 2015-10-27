package com.resistance.theresistance.logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Round class is in charge of storing each Round of
 * Mission. Purpose is to have an object that contains
 * all the information for each Mission round, that can be
 * stored in Mission more easily.
 * @author Group 11
 *
 */
public class Round{

	int mission;
	Leader leader;
	ArrayList<String> missionaries;//The current proposed Mission team of Missionaries
	HashMap<String, Boolean> votes;// Stores each Player's username and their votes
	
    /**
     * Creates Round object. A Round contains all of the
     * Player voting on whether to approve the Players in
     * each Mission team. If the team is not approved, a new Round
     * object is created, but the Mission is still the same.
     * @param mission, the mission number
     */
    public Round(int mission){

        this.mission = mission;
    }

    /**
     * Sets the new leader for the mission
     * @param newLeader the name of the new leader
     */
    public void setLeader(Leader newLeader){
    }

    /**
     * Sets new list of chosen missionaries
     * @param missionaries the new chosen missionaries to be set
     */
    public void setMissionaries(ArrayList<String> missionaries){
        this.missionaries = missionaries;
    }

    /**
     * Saves the player's names and their votes
     * @param votes the hashmap of player's and votes
     */
    public void setVotes(HashMap<String, Boolean> votes){
       this.votes = votes;
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    public Leader getLeader(){
        return this.leader;
    }
}


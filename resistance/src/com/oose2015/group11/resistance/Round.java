package com.oose2015.group11.resistance;

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

	String leader;
	ArrayList<String> missionaries;//The current proposed Mission team of Missionaries
	HashMap<String, Boolean> votes;// Stores each Player's username and their votes
	
/**
 * Creates Round object. A Round contains all of the
 * Player voting on whether to approve the Players in 
 * each Mission team. If the team is not approved, a new Round
 * object is created, but the Mission is still the same.
 */
public Round(){
	
}

/**
 * Sets the new leader for the mission
 * @param newLeader the name of the new leader
 */
public void setLeader(String newLeader){
}

/**
 * Sets new list of chosen missionaries
 * @param missionaries the new chosen missionaries to be set
 */
public void setMissionaries(ArrayList<String> missionaries){
	
}

/**
 * Saves the player's names and their votes
 * @param votes the hashmap of player's and votes
 */
public void setVotes(HashMap<String, Boolean> votes){
	
}
	
}
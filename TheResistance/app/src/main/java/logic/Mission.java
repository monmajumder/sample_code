package logic;

import java.util.ArrayList;

/**
 * Mission class is in charge of all things mission related.
 * Keeps track of the mission number, creates the Mission Leader,
 * keeps track of mission history, etc.
 * @author Group 11
 *
 */
public class Mission{

Leader missionLeader;
int missionNum;
boolean success;//if the players voted yes/no for the mission
boolean approved;//if the mission is approved (only if success == true)
ArrayList<Round> stateHistory;
ArrayList<Missionary> missionaries;

/**
Creates a mission object
@param missionLeader, the leader of the mission
@param missionNum the mission number
@param stateHistory, all the player's votes
*/
public Mission(Leader missionLeader, int missionNum, ArrayList<Round> stateHistory){
this.missionLeader = missionLeader;
this.missionNum = missionNum;
this.stateHistory = stateHistory;

}

/**
Gets the mission number
@return missionNum, the number of the mission
*/
public int getMissionNumber(){
   return missionNum;
}

/**
Checks if the mission is approved or not
@return approved true if approved, false if not approved
*/
public boolean isApproved(){
   return approved;
}

/**
   Generates a randomized mission leader
   @param numPlayers the number of players in the game
   */
   public void createRandomMissionLeader(int numPlayers){
   }

/**
Changes the mission Leader to a new player
@param newLeader is Player that will be the new
   mission Leader
*/
public void changeLeader(Leader newLeader){
   this.missionLeader = newLeader;
}

}
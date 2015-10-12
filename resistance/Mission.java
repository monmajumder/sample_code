package com.oose2015.group11.resistance;

public class Mission{

Player missionLeader;
int missionNum;
boolean success;//if the players voted yes/no for the mission
boolean approved;//if the mission is approved (only if success == true)
ArrayList<HashMap<Player, boolean>> stateHistory;
ArrayList<Missionary> missionaries;

/**
Creates a mission object
@param missionLeader, the leader of the mission
@param missionNum the mission number
@param stateHistory, all the player's votes
*/
public Mission(Player missionLeader, int missionNum, ArrayList<HashMap<Player, boolean>> stateHistory){
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
Changes the mission Leader to a new player
@param newLeader is Player that will be the new
   mission Leader
*/
public void changeLeader(Player newLeader){
   this.missionLeader = newLeader;
}

}
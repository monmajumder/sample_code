package com.resistance.theresistance.logic;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class is responsible for letting the Leader 
 * choose Missionaries. Has a Player object
 * since Leader is a Player with additional functionality,
 * but since the Leader is constantly changing, we can easily
 * change Player in this way. 
 * @author Group 11
 *
 */
public class Leader{

Player leader;

/**
Creates a Leader object using a player's fields
@param leader the player that is the leader
*/
public Leader(Player leader){
   this.leader = leader;
}

/**
Chooses the missionaries to go on the mission.
@param allPlayers, all the players in the game
@param numMiss the number of missionaries to choose
@return ArrayList of the missionaries that will be going on the mission
*/
public ArrayList<Missionary> chooseMissionaries(HashMap<String, Player> allPlayers,
   int numMiss){
   ArrayList<Missionary> choices = new ArrayList<Missionary>();
   //Have some kind of information from who the user clicks on to be passed here
   //Then we can process the click information
   //SOME KIND OF LOOP HERE
   choices.add(allPlayers.get("1"));
   //Checking to make sure they don't select the same person? or something
   //along those lines.
   if (allPlayers.get("2").equals(allPlayers.get("2"))){
      //throw another exception here
   }
   if (choices.size() != numMiss){
   //Throw some kind of exception here
   }
   
	return choices;
}

/**
Changes the mission Leader to a new player
@param player, the player the 
   Leader is being changed to
*/
public void changeLeader(Player newLeader){
   if (leader.equals(newLeader)){
      //throw exception of leader is already here
   }
   leader = newLeader;
   
}

}
package com.resistance.theresistance.logic;

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
@return true if valid choices, false otherwise
*/
public boolean chooseMissionaries(ArrayList<Player> allPlayers,
   int numMiss){
	return true;
}

/**
Changes the mission Leader to a new player
@param username, the username of the player the 
   Leader is being changed to
*/
public void changeLeader(String username){
}

}
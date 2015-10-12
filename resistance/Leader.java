package com.oose2015.group11.resistance;

import java.util.ArrayList;

public class Leader{

Player leader;

/**
Creates a Leader object using a player's fields
*/
public Leader(Player leader){
   this.leader = leader;
}

/**
Chooses the missionaries to go on the mission.
@param allPlayers, all the players in the game
@param numMiss the number of missionaries to choose
*/
public  chooseMissionaries(ArrayList<Player> allPlayers,
   int numMiss){
}

/**
Changes the mission Leader to a new player
@param username, the username of the player the 
   Leader is being changed to
*/
public void changeLeader(String username){
}

}
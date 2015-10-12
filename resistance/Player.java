package com.oose2015.group11.resistance;

public class Player{

String username;
String playerType;
boolean isHost;

/**
Creates a Player object
@param username, the player's username
@param playerType, "Spy" or "Resistor" for type
*/
public Player(String username, String playerType){
this.username = username;
this.playerType = "Spy"; //or Resistor depending on randomizer
}

/**
Returns if  player is a host or not
@return true if player is host, false otherwise
*/
public boolean isHost(){
   return true;
}

/**
Player votes for missionaries - votes yes/no for the
leader-chosen mission team
*/
public void voteForMissionaries(){
}

}
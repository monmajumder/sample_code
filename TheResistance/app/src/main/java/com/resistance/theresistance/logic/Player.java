package com.resistance.theresistance.logic;

/**
 * Player class is responsible for creating the Player object,
 * and letting Players vote
 * @author Group 11
 *
 */
public class Player{

   String username;
   String playerType;
   boolean isHost;

/**
Creates a Player object
@param username, the player's username
*/
   public Player(String username){
      this.username = username;
      isHost = false;
      //this.playerType = playerType; //spy or Resistor depending on randomizer
   }
   
/**
Sets the player's type
@param playerType, "Spy" or "Resistor" for type
*/
   public void setType(String playerType){
      if (playerType != "Spy" && playerType != "Resistor"){
         //throw some illlegal exception here
         
      }
      this.playerType = playerType;
   }


/**
Player votes for missionaries - votes yes/no (true/false) for the
leader-chosen mission team
*/
   public boolean voteForMissionaries(boolean vote){
      return vote;
   }
   
/**
Checks if the player is a host
@return true if host, false otherwise
*/
   public boolean isHost(){
      return isHost;
   }

}
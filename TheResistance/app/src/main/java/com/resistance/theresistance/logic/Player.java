package com.resistance.theresistance.logic;

/**
 * Player class is responsible for creating the Player object,
 * and letting Players vote
 * @author Group 11
 *
 */
public class Player{

<<<<<<< HEAD
   String username;
   String playerType;
   boolean isHost;
=======
String username;
Game.PlayerType playerType;
boolean isHost;
>>>>>>> 18833d2608f1e4e2f37852b420050c1729e018a7

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
<<<<<<< HEAD
   public void setType(String playerType){
      if (playerType != "Spy" && playerType != "Resistor"){
         //throw some illlegal exception here
         
      }
      this.playerType = playerType;
   }
=======
public Player(String username, Game.PlayerType playerType){
    this.username = username;
    this.playerType = playerType;
}
>>>>>>> 18833d2608f1e4e2f37852b420050c1729e018a7


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
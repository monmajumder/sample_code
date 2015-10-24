package logic;

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
@param playerType, "Spy" or "Resistor" for type
*/
public Player(String username, String playerType){
this.username = username;
this.playerType = "Spy"; //or Resistor depending on randomizer
}


/**
Player votes for missionaries - votes yes/no for the
leader-chosen mission team
*/
public void voteForMissionaries(){
}

}
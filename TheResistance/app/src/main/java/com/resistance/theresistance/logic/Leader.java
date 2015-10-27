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
     * Creates a Leader object using a player's fields
     * @param leader the player that is the leader
     */

    public Leader(Player leader){
        this.leader = leader;
    }

   /**
    * Chooses the missionaries to go on the mission.
    * @param allPlayers, an ArrayList of all the players in the game
    * @param numMiss the number of missionaries to choose
    * @return ArrayList of the missionaries that will be going on the mission
    */
   public ArrayList<Missionary> chooseMissionaries(ArrayList<Player> allPlayers, int numMiss) {
       //temporary variables
       ArrayList<Missionary> missionariesChosen = new ArrayList<Missionary>();
       return missionariesChosen;
   }

   /**
   Changes the mission Leader to a new player
   @param newLeader, the player the
   Leader is being changed to
   */
   public void changeLeader(Player newLeader){
      if (leader.equals(newLeader)){
         //throw exception of leader is already here
      }
      leader = newLeader;
   }

    //---------------------------------
    // Getter and setter methods
    //---------------------------------

    public Player getLeader() {
        return leader;
    }
}